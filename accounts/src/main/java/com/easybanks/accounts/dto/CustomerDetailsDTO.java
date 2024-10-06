package com.easybanks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Customer Details Schema",
        description = "Schema to hold customer, Account, Cards, and Loans information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDetailsDTO {
    @Schema(description = "Name of customer", example = "Example User")
    @NotEmpty(message = "Name can not be null or empty!")
    @Size(min = 5, max = 30,message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(description = "Email address of user",example="exampleUser@gmail.com")
    @NotEmpty(message = "Email can not be null or empty!")
    @Email(message = "Invalid email value")
    private String email;

    @Schema(description = "Phone number of User")
    @Pattern(regexp = "(^$|[0-9]{10})", message ="Invalid phone number value" )
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDTO accountsDTO;

    @Schema(description = "Card details of the customer")
    private CardsDTO cardsDTO;

    @Schema(description = "Loan details of the customer")
    private LoanDTO loanDTO;

}
