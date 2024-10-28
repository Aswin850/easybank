package com.easybank.confirmation.dto;

public record CardMessageDto(String cardNumber,String mobileNumber,String cardType,int availableAmount) {
}
