package com.openwaygroup.ipsgateway.service.impl;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.entities.card.Field;
import com.openwaygroup.ipsgateway.enumurate.CardEnum;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Service
public class CardService implements ICardService {


    public ArrayList<Card> loadCard(String path) throws IOException {


        ArrayList<Card> listCard = new ArrayList<Card>();
        File file = null;
        try {
            file = new File(path);

        } catch (Exception e) {
            System.out.println("Error when load card from path in CardService " + e.getMessage());
        }

        System.out.println("-------CardService.loadCard()--------");
        System.out.println("Reading Yaml File from path");
        if (file.length() == 0) {
            System.out.println("Error in CardService: File list card is null");
        } else {
            // not empty

            for (File f : file.listFiles()) {
                FileInputStream fileInputStream = new FileInputStream(f);

                System.out.println("Set parameter for each card");
                Yaml yaml = new Yaml();
                HashMap yamlMap = yaml.load(fileInputStream);
                // Access HashMaps and ArrayList by key(s)
                EnumSet<CardEnum> cardEnums = EnumSet.allOf(CardEnum.class);
                Card card = new Card();
                List<Field> fields = new ArrayList<>();
                for (CardEnum c : cardEnums) {
//            // Access HashMaps and ArrayList by key(s)
                    String fieldValue = (String) yamlMap.get(c.getFieldId());
//            System.out.println("Card = " + field);
                    Field field = new Field();
                    field.setFieldId(c.getFieldId());
                    field.setDescription(c.getDescription());
                    field.setName(c.getName());
                    field.setValue(fieldValue);
                    fields.add(field);


                }

                card.setListField(fields);
                List<String> requiredFields = List.of(new String[]{"F002", "F014", "F035", "F053.13", "F126.10", "F045.08", "F053.08"});
                for (String field : requiredFields) {
                    try {
                        card.getByFieldId(field).setOptional(false);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                listCard.add(card);
            }

            System.out.println("Return list card to controller");
        }
        return listCard;

    }

    @Override
    public Card getById(ArrayList<Card> listCard, String cardId) throws IOException {
        for (Card card : listCard) {
            if (card.getByFieldId("F002").getValue().equals(cardId)) {
                return card;
            }
        }
        return null;
    }


}
