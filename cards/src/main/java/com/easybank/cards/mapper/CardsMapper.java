package com.easybank.cards.mapper;

import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.entity.Cards;

public class CardsMapper {
    private CardsMapper(){}

    public static CardsDTO maptoCardsDTO(Cards cards,CardsDTO cardsDTO){
        cardsDTO.setCardNumber(cards.getCardNumber());
        cardsDTO.setCardType(cards.getCardType());
        cardsDTO.setMobileNumber(cards.getMobileNumber());
        cardsDTO.setTotalLimit(cards.getTotalLimit());
        cardsDTO.setAmountUsed(cards.getAmountUsed());
        cardsDTO.setAvailableAmount(cards.getAvailableAmount());
        return cardsDTO;
    }

    public static Cards maptoCards(CardsDTO cardsDTO,Cards cards){
        cards.setCardNumber(cardsDTO.getCardNumber());
        cards.setCardType(cardsDTO.getCardType());
        cards.setMobileNumber(cardsDTO.getMobileNumber());
        cards.setTotalLimit(cardsDTO.getTotalLimit());
        cards.setAmountUsed(cardsDTO.getAmountUsed());
        cards.setAvailableAmount(cardsDTO.getAvailableAmount());
        return cards;
    }
}
