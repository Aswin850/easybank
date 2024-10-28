package com.easybank.loans.function;

import com.easybank.loans.services.impl.LoansServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LoansMessageFunction {
    public static final Logger LOGGER= LoggerFactory.getLogger(LoansMessageFunction.class);

    @Bean
    public Consumer<String> updateCommunication(LoansServiceImpl loansService){
        return accountNumber -> {
            LOGGER.info("Updating loans Communication confirmation");
            boolean isUpdated = loansService.updateCommunicationStatus(accountNumber);
            if(isUpdated){
                LOGGER.info("Successfully updated loans Communication confirmation status.");
            }
        };
    }
}
