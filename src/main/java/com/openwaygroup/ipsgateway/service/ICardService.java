package com.openwaygroup.ipsgateway.service;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.exception.CardException;

import java.io.IOException;
import java.util.ArrayList;

public interface ICardService {
    ArrayList<Card> loadCard(String path) throws IOException, CardException;

    Card getById(ArrayList<Card> listCard, String id) throws IOException;

    String addCard(Card card) throws Exception;

    String editCard(Card card) throws Exception;

    boolean deleteCard(String id) throws Exception;

}
