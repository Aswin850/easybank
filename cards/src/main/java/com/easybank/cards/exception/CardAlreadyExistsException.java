package com.easybank.cards.exception;

public class CardAlreadyExistsException extends RuntimeException{
    public CardAlreadyExistsException(String message){
        super(message);
    }
}
