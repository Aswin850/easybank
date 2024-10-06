package com.easybank.loans.services.impl;

import com.easybank.loans.constant.LoansConstant;
import com.easybank.loans.dto.LoanDTO;
import com.easybank.loans.entity.Loans;
import com.easybank.loans.exception.LoanAlreadyExistsException;
import com.easybank.loans.exception.ResourceNotFoundException;
import com.easybank.loans.mapper.LoansMapper;
import com.easybank.loans.repository.LoansRepository;
import com.easybank.loans.services.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements ILoansService {
    private LoansRepository loansRepository;

    @Autowired
    public LoansServiceImpl(LoansRepository loansRepository){
        this.loansRepository=loansRepository;
    }
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans=loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
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

}
