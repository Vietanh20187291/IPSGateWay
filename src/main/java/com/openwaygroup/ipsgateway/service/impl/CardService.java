package com.openwaygroup.ipsgateway.service.impl;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.entities.card.Field;
import com.openwaygroup.ipsgateway.enumurate.CardEnum;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Service
public class CardService implements ICardService {


    public ArrayList<Card> loadCard (String path) throws IOException {
            ArrayList<Card> listCard = new ArrayList<Card>();
            File file = new File(path);
            System.out.println("-------CardService.loadCard()--------");
            System.out.println("Reading Yaml File from path");
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
                listCard.add(card);
            }

            System.out.println("Return list card to controller");
            return listCard;

        }

        @Override
        public Card getById (ArrayList < Card > listCard, String cardId) throws IOException {
            for (Card card : listCard) {
                if(card.getByFieldId("F002").getValue().equals(cardId)){
                    return card;
                }
            }
            return null;
        }




}
