package com.easybanks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Accounts Schema",
        description = "Schema to hold customer and Account information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountsDTO {

    @Schema(description = "Account number of customer")
    @NotEmpty(message = "Account Number can not be a null or empty")
    @Pattern(regexp  = "(^$|[0-9]{10})" ,message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type of customer",example = "Saving")
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(description = "Account type of customer")
    @NotEmpty(message = "Branch Address can not be a null or empty")
    private String branchAddress;
}
