package com.easybank.cards.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CardsDTO {

    @NotEmpty(message = "Mobile number can not be empty or null")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number. Please check!")
    private String mobileNumber;

    @NotEmpty(message = "Card number can not be empty or null")
    private String cardNumber;

    @NotEmpty(message = "Card type can not be empty or null")
    private String cardType;

    @NotEmpty(message = "Card limit can not be empty or null")
    @PositiveOrZero(message = "Card limit should be grater than or equal to zero!")
    private int totalLimit;

    @NotEmpty(message = "Amount used can not be empty or null")
    @PositiveOrZero(message = "Card limit should be grater than or equal to zero!")
    private int amountUsed;

    @NotEmpty(message = "Available amount can not be empty or null")
    @PositiveOrZero(message = "Card limit should be grater than or equal to zero!")
    private int availableAmount;
}
