package com.easybanks.accounts.controller;

import com.easybanks.accounts.constants.AccountsConstant;
import com.easybanks.accounts.dto.AccountsContactInfoDTO;
import com.easybanks.accounts.dto.CustomerDTO;
import com.easybanks.accounts.dto.ErrorDTO;
import com.easybanks.accounts.dto.ResponseDTO;
import com.easybanks.accounts.service.IAccountsService;
import com.easybanks.accounts.service.impl.AccountServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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

@Tag(
        name = "CURD REST APIs for Accounts in EasyBank",
        description = "CURD REST APIs  in EasyBank to CREATE,UPDATE,FETCH and DELETE account details")
@Validated
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);
    private IAccountsService accountsService;

    public AccountsController(AccountServiceImpl accountsService){
        this.accountsService=accountsService;
    }

    @Value("${build.version}")
    private String buildVersion;
    @Autowired
    private Environment environment;
    @Autowired
    private AccountsContactInfoDTO accountsContactInfoDTO;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EasyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> getAccounts(@Valid @RequestBody CustomerDTO customerDTO){

        accountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstant.STATUS_201,AccountsConstant.MESSAGE_201));
    }

    @Operation(summary = "Fetch Account REST API", description = "REST API to return  Customer & Account inside EasyBank")
    @ApiResponse(responseCode = "200", description = "HTTP Status Ok")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> getAccounts( @RequestParam(name = "mobileNumber")  @Pattern(regexp = "($|[0-9]{10})",message = "Invalid mobile number") String mobileNumber){

       CustomerDTO customerDTO= accountsService.fetchAccounts(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }


    @Operation(summary = "Update Account REST API", description = "REST API to Update existing Customer & Account inside EasyBank")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status Ok"),@ApiResponse(responseCode = "417", description = "Update operation failed",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateDetails(@Valid @RequestBody CustomerDTO customerDTO){
        boolean isUpdated = accountsService.updateAccountDetails(customerDTO);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDTO(AccountsConstant.STATUS_417,AccountsConstant.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete Account REST API", description = "REST API to Delete existing Customer & Account inside EasyBank")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status Ok"),@ApiResponse(responseCode = "417", description = "Delete operation failed")})
    @DeleteMapping("/delete")
    private ResponseEntity<ResponseDTO> deleteAccount( @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "Invalid mobile number!") String mobileNumber){
        boolean isDeleted=accountsService.deleteAnAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDTO(AccountsConstant.STATUS_417,AccountsConstant.MESSAGE_417_DELETE));
        }

    }

    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @ApiResponse(responseCode = "200",description = "REST API to get the build version")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){

        LOGGER.debug("getBuildInfo() method invoked");
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
        LOGGER.debug("getBuildInfoFallback() method invoked");
        return ResponseEntity.status(HttpStatus.OK).body("0.0");
    }


    @RateLimiter(name = "getJavaVersion",fallbackMethod = "getJavaVersionFallback")
    @ApiResponse(responseCode = "200",description = "REST API to get the java version")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable t){
        return ResponseEntity.status(HttpStatus.OK).body("JAVA 22");
    }

    @ApiResponse(responseCode = "200",description = "REST API to get the maven version")
    @GetMapping("/maven-version")
    public ResponseEntity<String> getMavenVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
    }

    @ApiResponse(responseCode = "200",description = "REST API to get the contact info")
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDTO> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDTO);
    }

}
