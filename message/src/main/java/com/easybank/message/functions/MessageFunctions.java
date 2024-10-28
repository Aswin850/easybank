package com.easybank.message.functions;

import com.easybank.message.dto.AccountsMessageDto;
import com.easybank.message.dto.LoansMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class MessageFunctions {

    public static final Logger LOGGER= LoggerFactory.getLogger(MessageFunctions.class);


    //In the function email we are trying to return the implementation logic for the
    // functional interface
    @Bean
    public Function<AccountsMessageDto,AccountsMessageDto> email(){
        return accountsMessageDto ->{
            LOGGER.info("Sending email with the details {}",accountsMessageDto);
            return accountsMessageDto;
        };
    }


    @Bean
    public Function<AccountsMessageDto,Long> sms(){
        return accountsMessageDto -> {
            LOGGER.info("Sending sms with the details {}",accountsMessageDto.accountNumber());
            return accountsMessageDto.accountNumber();
        };
    }


   @Bean
    public Function<LoansMessageDto,LoansMessageDto> loansEmailConfirm(){
        return loansMessageDto -> {
            LOGGER.info("Sending email confirmation for new loan accounts with the details {}",loansMessageDto);
            return loansMessageDto;
        };
   }

   @Bean
    public Function<LoansMessageDto,String> loansSmsConfirm(){
        return loansMessageDto -> {
            LOGGER.info("Sending sms confirmation for new loan accounts with the details {}",loansMessageDto.loanNumber());
            return loansMessageDto.loanNumber();
        };
   }

}
