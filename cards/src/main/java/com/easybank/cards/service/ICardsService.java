package com.easybank.cards.service;

import com.easybank.cards.dto.CardsDTO;

public interface ICardsService {
    void createNewCardAccount(String mobileNumber);
    CardsDTO fetchCardDetails(String mobileNumber);
    boolean updateCardDetails(CardsDTO cardsDTO);
    boolean deleteCardDetails(String mobileNumber);
}
