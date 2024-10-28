package com.easybank.loans.services.impl;

import com.easybank.loans.constant.LoansConstant;
import com.easybank.loans.dto.LoanDTO;
import com.easybank.loans.dto.LoansMessageDTO;
import com.easybank.loans.entity.Loans;
import com.easybank.loans.exception.LoanAlreadyExistsException;
import com.easybank.loans.exception.ResourceNotFoundException;
import com.easybank.loans.mapper.LoansMapper;
import com.easybank.loans.repository.LoansRepository;
import com.easybank.loans.services.ILoansService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {
    public static final Logger LOGGER= LoggerFactory.getLogger(LoansServiceImpl.class);

    private LoansRepository loansRepository;
    private StreamBridge streamBridge;

    public void createLoan(String mobileNumber) {
        Optional<Loans> loans=loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        Loans savedLoan= loansRepository.save(createNewLoan(mobileNumber));
        sendConformation(savedLoan);
    }

    private void sendConformation(Loans savedLoan) {
        var loansMessageDto = new LoansMessageDTO(savedLoan.getLoanNumber(),savedLoan.getMobileNumber(),savedLoan.getLoanType(),savedLoan.getTotalLoan());
        var result = streamBridge.send("loansConformation-out-0",loansMessageDto);
        LOGGER.info("Confirmation message triggered with details {} - {}",loansMessageDto,result);
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan= new Loans();
        long accountNumber=1000000000+ new Random().nextInt(0,900000000);
        newLoan.setLoanNumber(Long.toString(accountNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstant.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstant.NEW_LOAN_LIMIT);
        newLoan.setOutstandingAmount(LoansConstant.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        return newLoan;
    }


    @Override
    public LoanDTO getLoanDetails(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Loans","mobileNumber",mobileNumber));
        LoanDTO loanDTO = LoansMapper.mapToLoansDTO(loans,new LoanDTO());
        return loanDTO;
    }

    @Override
    public boolean updateLoanDetails(LoanDTO loanDTO) {
        Loans loans=loansRepository.findByLoanNumber(loanDTO.getLoanNumber())
                .orElseThrow(()->new ResourceNotFoundException("Loans","loan number", loanDTO.getLoanNumber()));

        LoansMapper.mapToLoan(loanDTO,loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Loans","mobile number",mobileNumber));
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(String loanAccountNumber) {
        boolean isUpdated=false;
        if(loanAccountNumber!=null){
            Loans loans = loansRepository.findByLoanNumber(loanAccountNumber)
                    .orElseThrow(()->new ResourceNotFoundException("Loans","loan number", loanAccountNumber));

            loans.setCommunicationSw(true);
            loansRepository.save(loans);
            isUpdated=true;

        }
        return isUpdated;
    }


}
