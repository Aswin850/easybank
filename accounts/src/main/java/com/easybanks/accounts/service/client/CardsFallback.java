package com.easybanks.accounts.service.client;

import com.easybanks.accounts.dto.CardsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{

    @Override
    public ResponseEntity<CardsDTO> fetchCardsDetails(String correlationID, String mobileNumber) {
        return null;
    }
}
