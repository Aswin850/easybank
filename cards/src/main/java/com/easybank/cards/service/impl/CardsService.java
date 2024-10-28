package com.easybank.cards.service.impl;

import com.easybank.cards.constant.CardsConstants;
import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.dto.CardsMessageDTO;
import com.easybank.cards.entity.Cards;
import com.easybank.cards.exception.CardAlreadyExistsException;
import com.easybank.cards.exception.ResourceNotFoundException;
import com.easybank.cards.mapper.CardsMapper;
import com.easybank.cards.repository.CardsRepository;
import com.easybank.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsService implements ICardsService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CardsService.class);

    private CardsRepository cardsRepository;
    private StreamBridge streamBridge;



    @Override
    public void createNewCardAccount(String mobileNumber) {
        Optional<Cards> card =cardsRepository.findByMobileNumber(mobileNumber);
        if(card.isPresent()){
            throw  new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        Cards cardsSaved= cardsRepository.save(createCardAccount(mobileNumber));
        sendConfirmation(cardsSaved);
    }

    private void sendConfirmation(Cards cardsSaved) {
        var cardsMessageDto=new CardsMessageDTO(cardsSaved.getCardNumber(),cardsSaved.getMobileNumber(),cardsSaved.getCardType(),cardsSaved.getAvailableAmount());
        LOGGER.info("Sending account creation confirmation message with details {}",cardsMessageDto);
        var result= streamBridge.send("send-confirmation",cardsMessageDto);
        LOGGER.info("Sending account creation confirmation triggered {}",result);

    }


    private Cards createCardAccount(String mobileNumber) {
        Cards card=new Cards();
        Long cardNumber=1000000000+(new Random().nextLong(99999999,999999999));
        card.setCardNumber(Long.toString(cardNumber));
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        card.setMobileNumber(mobileNumber);
        return card;
    }

    @Override
    public CardsDTO fetchCardDetails(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Cards","mobile number",mobileNumber));
        CardsDTO cardsDTO= CardsMapper.maptoCardsDTO(cards,new CardsDTO());
        return cardsDTO;
    }

    @Override
    public boolean updateCardDetails(CardsDTO cardsDTO) {
        Cards card=cardsRepository.findByCardNumber(cardsDTO.getCardNumber()).orElseThrow(()->new ResourceNotFoundException("Cards","card number",cardsDTO.getCardNumber()));
        CardsMapper.maptoCards(cardsDTO,card);
        cardsRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCardDetails(String mobileNumber) {
        Cards card=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Cards","mobile number",mobileNumber));
        cardsRepository.deleteById(card.getCardId());
        return true;
    }


}
