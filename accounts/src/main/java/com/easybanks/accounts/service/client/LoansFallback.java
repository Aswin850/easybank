package com.easybanks.accounts.service.client;

import com.easybanks.accounts.dto.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoanDTO> fetchLoansDetails(String correlationID, String mobileNumber) {
        return null;
    }
}
