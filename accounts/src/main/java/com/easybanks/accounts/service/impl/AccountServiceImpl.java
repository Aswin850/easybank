package com.easybanks.accounts.service.impl;

import com.easybanks.accounts.constants.AccountsConstant;
import com.easybanks.accounts.dto.AccountsMessageDTO;
import com.easybanks.accounts.entities.Accounts;
import com.easybanks.accounts.exception.CustomerAlreadyExitsException;
import com.easybanks.accounts.exception.ResourceNotFoundException;
import com.easybanks.accounts.repositories.AccountsRepo;
import com.easybanks.accounts.repositories.CustomerRepo;
import com.easybanks.accounts.service.IAccountsService;
import com.easybanks.accounts.dto.AccountsDTO;
import com.easybanks.accounts.dto.CustomerDTO;
import com.easybanks.accounts.entities.Customer;
import com.easybanks.accounts.mapper.AccountsMapper;
import com.easybanks.accounts.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {
    public static final Logger LOGGER= LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountsRepo accountsRepo;
    private CustomerRepo customerRepo;
    private StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDTO customerDTO) throws CustomerAlreadyExitsException {
       Optional<Customer> customerCheck=customerRepo.findBymobileNumber(customerDTO.getMobileNumber());
        if(customerCheck.isPresent()){
            throw new CustomerAlreadyExitsException("Customer already registered with the give Mobile no:"+customerDTO.getMobileNumber()+" Please check the give mobile number");
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDTO,new Customer());
        //save customer
        Customer savedCustomer= customerRepo.save(customer);
       //save account
        Accounts savedAccount = accountsRepo.save(createNewAccounts(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);

    }

    private void sendCommunication(Accounts savedAccount, Customer savedCustomer) {
       var accountsMessageDTO = new AccountsMessageDTO(savedAccount.getAccountNumber(),savedCustomer.getName(),savedCustomer.getEmail(),savedCustomer.getMobileNumber());
       LOGGER.info("Sending Communication request for the details{}",accountsMessageDTO);
       var result= streamBridge.send("sendCommunication-out-0",accountsMessageDTO);
       LOGGER.info("Is the Communication request successfully processed? :{}",result);
    }

    /*
    *
    * @param   customer
    * @return  Account
    *
    * */
    private Accounts createNewAccounts(Customer customer){
        Accounts account=new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long accountNumber=1000000000L +new Random().nextInt(900000000);

        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountsConstant.SAVINGS);
        account.setBranchAddress(AccountsConstant.ADDRESS);
        return account;
    }


    @Override
    public CustomerDTO fetchAccounts(String mobileNumber) throws ResourceNotFoundException {
        Customer customer=customerRepo.findBymobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        Accounts accounts=accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Accounts","CustomerID",customer.getCustomerId().toString()));

        CustomerDTO customerDTO=CustomerMapper.mapToCustomerDto(customer,new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts,new AccountsDTO()));
        return customerDTO;
    }


    @Override
    public boolean updateAccountDetails(CustomerDTO customerDTO) throws ResourceNotFoundException{
        boolean isUpdated=false;
        AccountsDTO accountsDTO=customerDTO.getAccountsDTO();

        if(accountsDTO!=null){
            Accounts accounts= accountsRepo.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","CustomerID",accountsDTO.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(customerDTO.getAccountsDTO(),accounts);
            accounts=accountsRepo.save(accounts);

            Long customerID=accounts.getCustomerId();
            Customer customer=customerRepo.findById(customerID).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","mobileNumber",customerID.toString()));

            CustomerMapper.mapToCustomer(customerDTO,customer);
            customerRepo.save(customer);
            isUpdated= true;
        }
        return isUpdated;
    }

    @Transactional
    @Override
    public boolean deleteAnAccount(String mobileNumber) {
        Customer customer=customerRepo.findBymobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber));
        //Since this method is written by us we have to give @Transactional
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated=false;
        if(accountNumber!=null){
            var account = accountsRepo.findById(accountNumber).orElseThrow(()->new ResourceNotFoundException("Accounts","Account Number",String.valueOf(accountNumber)));
            account.setCommunicationSw(true);
            accountsRepo.save(account);
            isUpdated=true;
        }
        return isUpdated;
    }


}

