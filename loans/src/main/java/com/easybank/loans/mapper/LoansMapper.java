package com.easybank.loans.mapper;


import com.easybank.loans.dto.LoanDTO;
import com.easybank.loans.entity.Loans;

public class LoansMapper {

    public static LoanDTO mapToLoansDTO(Loans loans,LoanDTO loanDTO){
        loanDTO.setMobileNumber(loans.getMobileNumber());
        loanDTO.setLoanNumber(loans.getLoanNumber());
        loanDTO.setLoanType(loans.getLoanType());
        loanDTO.setTotalLoan(loans.getTotalLoan());
        loanDTO.setAmountPaid(loans.getAmountPaid());
        loanDTO.setOutstandingAmount(loans.getOutstandingAmount());
        return loanDTO;
    }

    public static Loans mapToLoan(LoanDTO loanDTO,Loans loan){
        loan.setMobileNumber(loanDTO.getMobileNumber());
        loan.setLoanNumber(loanDTO.getLoanNumber());
        loan.setLoanType(loanDTO.getLoanType());
        loan.setTotalLoan(loanDTO.getTotalLoan());
        loan.setAmountPaid(loanDTO.getAmountPaid());
        loan.setOutstandingAmount(loanDTO.getOutstandingAmount());
        return loan;
    }


}
