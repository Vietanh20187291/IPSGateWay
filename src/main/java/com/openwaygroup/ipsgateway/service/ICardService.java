package com.openwaygroup.ipsgateway.service;

import com.openwaygroup.ipsgateway.entities.Card;

import java.io.IOException;
import java.io.InputStream;

public interface ICardService {
    Card loadCard(InputStream inputStream) throws IOException;
}
