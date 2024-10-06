package com.easybank.loans.services;

import com.easybank.loans.dto.LoanDTO;
import com.easybank.loans.entity.Loans;

import java.util.Optional;

public interface ILoansService {
    void createLoan(String mobileNumber);
    LoanDTO getLoanDetails(String mobileNumber);
    boolean updateLoanDetails(LoanDTO loanDTO);
    boolean deleteLoan(String mobileNumber);
}
