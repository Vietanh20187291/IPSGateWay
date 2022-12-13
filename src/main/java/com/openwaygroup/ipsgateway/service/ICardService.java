package com.openwaygroup.ipsgateway.service;

import com.openwaygroup.ipsgateway.entities.card.Card;
import java.io.IOException;
import java.util.ArrayList;

public interface ICardService {
   ArrayList<Card> loadCard(String path) throws IOException;
    Card getById( ArrayList<Card> listCard, String id) throws IOException;
}
