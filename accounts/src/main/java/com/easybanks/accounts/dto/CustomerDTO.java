package com.easybanks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Customer Schema",
        description = "Schema to hold customer and Account information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

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
}
