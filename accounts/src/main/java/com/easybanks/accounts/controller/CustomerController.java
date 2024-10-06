package com.easybanks.accounts.controller;

import com.easybanks.accounts.dto.CustomerDetailsDTO;
import com.easybanks.accounts.service.ICustomerService;
import com.easybanks.accounts.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Customer Details in EasyBank",
        description = "REST APIs in EasyBank to customer details")
@Validated
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerController.class);
    private ICustomerService iCustomerService;

    @Autowired
    public  CustomerController(CustomerServiceImpl iCustomerService){
        this.iCustomerService=iCustomerService;
    }

    @Operation(summary = "Fetch Customer Details REST API", description = "REST API to return  Customer, Account, Cards,and Loans inside EasyBank")
    @ApiResponse(responseCode = "200", description = "HTTP Status Ok")
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(@RequestParam(name = "mobileNumber")  @Pattern(regexp = "($|[0-9]{10})",message = "Invalid mobile number") String mobileNumber,
                                                                   @RequestHeader(name = "easybank_correlation_id") String correlationId){

        LOGGER.debug("easyBank-correlation_id found :{}",correlationId);
        CustomerDetailsDTO customerDetailsDTO= iCustomerService.getCustomerDetail(mobileNumber,correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDTO);
    }

}
