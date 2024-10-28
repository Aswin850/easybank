package com.easybank.loans.controller;

import com.easybank.loans.constant.LoansConstant;
import com.easybank.loans.dto.ErrorDTO;
import com.easybank.loans.dto.LoanDTO;
import com.easybank.loans.dto.LoansContactInfoDTO;
import com.easybank.loans.dto.ResponseDTO;
import com.easybank.loans.services.ILoansService;
import com.easybank.loans.services.impl.LoansServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/loans",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details")
public class LoansController {

    private static final Logger LOGGER= LoggerFactory.getLogger(LoansController.class);

   private ILoansService loansService;

   //There are 3 ways by which we can get the data form the properties file
    // 1) Using @Value("${property_key}")
    //2) Using the instanse of Enviroment variable and getProperty()
    //3) Using @ConfigutationProperty(prefix="")

    @Value("${loans.message}")
    private String message;

    @Autowired
    private LoansContactInfoDTO loansContactInfoDTO;

    @Autowired
    public LoansController(LoansServiceImpl loansService){
       this.loansService=loansService;
   }

   @Operation(summary = "Create Loan REST API",description = "REST API to create new loan inside EazyBank")
   @ApiResponses({@ApiResponse(responseCode = "200",description = "HTTP Status CREATED"),@ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
   @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoanAccounts( @RequestParam(name = "mobileNumber") @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number, Please enter a valid one") String mobileNumber){
       loansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(LoansConstant.STATUS_201,LoansConstant.MESSAGE_201));
    }


    @ApiResponses({@ApiResponse(responseCode = "201",description = "HTTP Status OK"),@ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @Operation(summary = "Fetch Loan REST API",description = "REST API to fetch loan details inside EazyBank")
    @GetMapping("/fetch")
    public ResponseEntity<LoanDTO> fetchLoansDetails(@RequestHeader(name = "easybank_correlation_id") String correlationID, @RequestParam(name = "mobileNumber") @Pattern(regexp ="^[0-9]{10}$" ,message = "Invalid mobile number, Please enter a valid one") String mobileNumber){

        LOGGER.debug("fetchLoansDetails method start");
        LoanDTO loanDTO=loansService.getLoanDetails(mobileNumber);
        LOGGER.debug("fetchLoansDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(loanDTO);

    }


    @ApiResponses({@ApiResponse(responseCode = "201",description = "HTTP Status OK"),@ApiResponse(responseCode = "417",description = "Delete operation failed"),@ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @Operation(summary = "Delete Loan REST API",description = "REST API to delete loan details inside EazyBank")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoanAcconts( @RequestParam(name = "mobileNumber") @Pattern(regexp ="^[0-9]{10}" ,message = "Invalid mobile number, Please enter a valid one") String mobileNumber){
        boolean checkIsDeleted=loansService.deleteLoan(mobileNumber);
        if(checkIsDeleted){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponseDTO(LoansConstant.STATUS_201,LoansConstant.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoansConstant.STATUS_417, LoansConstant.MESSAGE_417_UPDATE));
        }

    }

    @ApiResponses({@ApiResponse(responseCode = "201",description = "HTTP Status OK"),@ApiResponse(responseCode = "417",description = "Update operation failed"),@ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @Operation(summary = "Update Loan REST API",description = "REST API to update loan details inside EazyBank")
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoanAcconts(@Valid @RequestBody LoanDTO loanDTO){
       boolean checkIsUpdated=loansService.updateLoanDetails(loanDTO);
       if(checkIsUpdated){
         return ResponseEntity.status(HttpStatus.OK)
                   .body(new ResponseDTO(LoansConstant.STATUS_201,LoansConstant.MESSAGE_200));
       }else {
         return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                   .body(new ResponseDTO(LoansConstant.STATUS_417, LoansConstant.MESSAGE_417_DELETE));
       }
    }

    @GetMapping("/message")
    public ResponseEntity<String> getMessage(){
       return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @ApiResponse(responseCode = "200",description = "REST API to get the contact info")
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDTO> getContactInfo(){
        LOGGER.debug("Invoked Loans contact-info API");
       return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDTO);
    }


}
