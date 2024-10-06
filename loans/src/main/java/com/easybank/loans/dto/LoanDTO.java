package com.easybank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(name = "Loans",description = "Schema to hold Loan information")
public class LoanDTO {

    @Schema(description = "Mobile Number of Customer",example = "9036****47")
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number.Please enter a valid one")
    private String mobileNumber;

    @Schema(description = "Loan Number of the customer",example = "548732457654")
    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid loan number.Please enter a valid one")
    private String loanNumber;

    @Schema(description = "Type of the loan",example = "Car loan")
    @NotEmpty(message = "Loan type can not be a null or empty")
    private String loanType;

    @Schema(description = "Total loan amount should be greater than zero")
    @Positive(message = "Total loan amount should be greater than zero")
    @NotEmpty(message = "Total Loan can not be a null or empty")
    private int totalLoan;

    @Schema(description = "Loan amount paid should be greater than or equal zero")
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @NotEmpty(message = "Amount paid can not be a null or empty")
    private int amountPaid;

    @Schema(description = "Outstanding amount paid should be greater than or equal zero")
    @PositiveOrZero(message = "Outstanding amount paid should be equal or greater than zero")
    @NotEmpty(message = "Outstanding amount can not be a null or empty") @NotEmpty
    private int outstandingAmount;

}
