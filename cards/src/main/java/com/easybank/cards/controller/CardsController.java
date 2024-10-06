package com.easybank.cards.controller;

import com.easybank.cards.constant.CardsConstants;
import com.easybank.cards.dto.CardsContactInfoDto;
import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.dto.ResponseDTO;
import com.easybank.cards.service.ICardsService;
import com.easybank.cards.service.impl.CardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@Validated
public class CardsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardsController.class);
    private ICardsService cardsService;

    @Autowired
    public CardsController(CardsService cardsService){
        this.cardsService=cardsService;
    }

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCardAccount(@RequestParam(name = "mobileNumber") @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number. Please check!") String mobileNumber){
        cardsService.createNewCardAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDTO> fetchCardsDetails( @RequestHeader(name = "easybank_correlation_id") String correlationID,@RequestParam(name = "mobileNumber") @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number. Please check!") String mobileNumber){

        LOGGER.debug("easyBank-correlation_id found :{}",correlationID);
        CardsDTO cardsDTO=cardsService.fetchCardDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCardDetails(@Valid @RequestBody CardsDTO cardsDTO){
        boolean checkIsUpdated = cardsService.updateCardDetails(cardsDTO);
        if(checkIsUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCardDetails(@RequestParam(name = "mobileNumber") @Pattern(regexp = "^[0-9]{10}$",message = "Invalid mobile number. Please check!") String mobileNumber){
        boolean checkIsDeleted=cardsService.deleteCardDetails(mobileNumber);
        if(checkIsDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
        }
    }
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }
}
