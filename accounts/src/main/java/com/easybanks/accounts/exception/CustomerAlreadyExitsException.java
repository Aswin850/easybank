package com.easybanks.accounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExitsException extends RuntimeException{
    public CustomerAlreadyExitsException(String message){
        super(message);
    }
}
