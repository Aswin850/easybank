package com.easybanks.accounts.function;

import com.easybanks.accounts.service.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AccountsFunctions {
    public static final Logger LOGGER= LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(AccountServiceImpl accountService){
        return accountNumber -> {
            LOGGER.info("Updating Communication status for the account number {}",accountNumber);
            accountService.updateCommunicationStatus(accountNumber);
        };
    }

}
