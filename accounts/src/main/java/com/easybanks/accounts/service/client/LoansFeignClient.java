package com.easybanks.accounts.service.client;

import com.easybanks.accounts.dto.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient( name = "loans",fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/loans/fetch",consumes = "application/json")
    public ResponseEntity<LoanDTO> fetchLoansDetails(@RequestHeader(name = "easybank_correlation_id") String correlationID, @RequestParam String mobileNumber);
}
