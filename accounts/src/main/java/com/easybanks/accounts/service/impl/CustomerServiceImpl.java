package com.easybanks.accounts.service.impl;

import com.easybanks.accounts.dto.AccountsDTO;
import com.easybanks.accounts.dto.CardsDTO;
import com.easybanks.accounts.dto.CustomerDetailsDTO;
import com.easybanks.accounts.dto.LoanDTO;
import com.easybanks.accounts.entities.Accounts;
import com.easybanks.accounts.exception.ResourceNotFoundException;
import com.easybanks.accounts.repositories.AccountsRepo;
import com.easybanks.accounts.repositories.CustomerRepo;
import com.easybanks.accounts.entities.Customer;
import com.easybanks.accounts.mapper.AccountsMapper;
import com.easybanks.accounts.mapper.CustomerMapper;
import com.easybanks.accounts.service.ICustomerService;
import com.easybanks.accounts.service.client.CardsFeignClient;
import com.easybanks.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private AccountsRepo accountsRepo;
    private CustomerRepo customerRepo;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;

    @Override
    public CustomerDetailsDTO getCustomerDetail(String mobileNumber, String correlationID) {

        Customer customer=customerRepo.findBymobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        Accounts accounts=accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Accounts","CustomerID",customer.getCustomerId().toString()));

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.customerDetailsDTO(customer,new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts,new AccountsDTO()));

        ResponseEntity<LoanDTO> loanDTOResponseEntity = loansFeignClient.fetchLoansDetails(correlationID,mobileNumber);
        if(null!= loanDTOResponseEntity){
            customerDetailsDTO.setLoanDTO(loanDTOResponseEntity.getBody());
        }

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.fetchCardsDetails(correlationID,mobileNumber);
        if(null!= cardsDTOResponseEntity){
            customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());
        }
        return customerDetailsDTO;
    }
}
