package com.openwaygroup.ipsgateway.enumurate.service;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.exception.CardException;

import java.io.IOException;
import java.util.ArrayList;

public interface ICardService {
   ArrayList<Card> loadCard(String path) throws IOException, CardException;
    Card getById( ArrayList<Card> listCard, String id) throws IOException;
}
