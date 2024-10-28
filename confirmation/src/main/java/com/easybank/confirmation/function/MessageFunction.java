package com.easybank.confirmation.function;

import com.easybank.confirmation.dto.CardMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MessageFunction {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageFunction.class);

    @Bean
    public Function<CardMessageDto,CardMessageDto> sms(){
        return cardMessageDto -> {
            LOGGER.info("Sending new card account creation confirmation message Card details {}",cardMessageDto);
            return cardMessageDto;
        };
    }
}
