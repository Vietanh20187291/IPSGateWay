package com.openwaygroup.ipsgateway.service.impl;

import com.openwaygroup.ipsgateway.entities.Card;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Service
public class CardService implements ICardService {
    @Override
    public Card loadCard(InputStream inputStream) throws IOException {

        Yaml yaml = new Yaml();
        HashMap yamlMap = yaml.load(inputStream);
        // Access HashMaps and ArrayList by key(s)
        HashMap physical = (HashMap) yamlMap.get("physical");
        String cardNumber = (String) physical.get("cardNumber");
        String expiryDate = (String) physical.get("expiryDate");
        String track1 = (String) physical.get("track1");
        String track2 = (String) physical.get("track2");
        String chipData = (String) physical.get("chipData");

        HashMap database = (HashMap) yamlMap.get("database");
        String pinBlock = (String) database.get("pinBlock");
        String track1Structure = (String) database.get("track1Structure");
        String track2Structure = (String) database.get("track2Structure");

//        if (StringUtils.hasText(cardNumber) || StringUtils.hasText(expiryDate) || StringUtils.hasText(track1)
//                || StringUtils.hasText(track2) || StringUtils.hasText(chipData)) {
//            throw new NullPointerException("203");
//        }
        Card card = new Card(cardNumber, expiryDate, track1, track2, chipData, pinBlock, track1Structure, track2Structure);
        System.out.println(card.toString());

        return card;
    }
}
