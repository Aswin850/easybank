package com.easybank.cards.service.impl;

import com.easybank.cards.constant.CardsConstants;
import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.entity.Cards;
import com.easybank.cards.exception.CardAlreadyExistsException;
import com.easybank.cards.exception.ResourceNotFoundException;
import com.easybank.cards.mapper.CardsMapper;
import com.easybank.cards.repository.CardsRepository;
import com.easybank.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardsService implements ICardsService {
    private CardsRepository cardsRepository;

    @Autowired
    public  CardsService(CardsRepository cardsRepository){
        this.cardsRepository=cardsRepository;
    }

    @Override
    public void createNewCardAccount(String mobileNumber) {
        Optional<Cards> card =cardsRepository.findByMobileNumber(mobileNumber);
        if(card.isPresent()){
            throw  new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createCardAccount(mobileNumber));
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
