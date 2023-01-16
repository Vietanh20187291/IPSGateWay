package com.openwaygroup.ipsgateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.entities.card.Field;
import com.openwaygroup.ipsgateway.enumurate.cardEnums.FieldsEnum;
import com.openwaygroup.ipsgateway.enumurate.cardEnums.RequiredFields;
import com.openwaygroup.ipsgateway.service.ICardService;
import com.openwaygroup.ipsgateway.exception.CardException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.*;


@Service
public class CardService implements ICardService {
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);

    @Override
    public ArrayList<Card> loadCard(String path) throws IOException, CardException {


        ArrayList<Card> listCard = new ArrayList<Card>();
        File file = null;
        try {
            file = new File(path);

        } catch (Exception e) {
            log.info("Error when load card from path in CardService " + e.getMessage());
        }

        log.info("-------CardService.loadCard()--------");
        log.info("Reading Yaml File from path");
        if (file.listFiles().length == 0) {
            log.info("Error in CardService: Cannot read any file from path");
            throw new CardException("Cannot read any file from path");
        } else {
            // not empty

            for (File f : file.listFiles()) {
                FileInputStream fileInputStream = new FileInputStream(f);
                Yaml yaml = new Yaml();
                HashMap yamlMap = yaml.load(fileInputStream);
                // Access HashMaps and ArrayList by key(s)
                EnumSet<FieldsEnum> fieldsEnums = EnumSet.allOf(FieldsEnum.class);
                Card card = new Card();
                List<Field> fields = new ArrayList<>();
                for (FieldsEnum fe : fieldsEnums) {
//            Access HashMaps and ArrayList by key(s)
                    String fieldValue = (String) yamlMap.get(fe.getFieldId());
//            log.info("Card = " + field);
                    Field field = new Field();
                    field.setFieldId(fe.getFieldId());
                    field.setDescription(fe.getDescription());
                    field.setName(fe.getName());
                    field.setValue(fieldValue);
                    fields.add(field);


                }

                card.setListField(fields);

                EnumSet<RequiredFields> listRequiredFields = EnumSet.allOf(RequiredFields.class);

                    for (RequiredFields requiredFields : listRequiredFields) {
                        String field = requiredFields.getField();
                    try {
                        card.getFieldById(field).setOptional(false);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }
                listCard.add(card);
                fileInputStream.close();
            }

            log.info("Return list card to controllers");
        }


        return listCard;

    }

    @Override
    public Card getById(ArrayList<Card> listCard, String cardId) throws IOException {
        for (Card card : listCard) {
            if (card.getFieldById("F002").getValue().equals(cardId)) {
                return card;
            }
        }

        return null;
    }

    public boolean editCard(Card card) throws Exception {
        if (card != null) {
            String id = card.getFieldById("F002").getValue();
            if (deleteCard(id)) {
                if (addCard(card)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addCard(Card card) throws Exception {
        if (card != null) {
            ObjectMapper objectMapper = new YAMLMapper();
            String idCard = card.getFieldById("F002").getValue();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < card.getListField().size(); i++) {
                String fieldId = card.getListField().get(i).getFieldId();
                String value = card.getFieldById(fieldId).getValue();
                map.put(fieldId, value);
            }
            objectMapper.writeValue(new File("src/main/resources/inputCards/" + idCard + ".yaml"), map);

            return true;
        }
        return false;
    }

    public boolean deleteCard(String id) throws Exception {
        log.info("Start delete card");

        try {

          File file = new File("src\\main\\resources\\inputCards\\" + id + ".yaml");

            try{
                log.info(file.getName());
                if(file.exists()){
                    log.info("Found File");
                }
                //Copy file to deletedCard folder
                log.info("here");
                File deletedFile = new File("src\\main\\resources\\deletedCards\\"+file.getName());
                log.info(deletedFile.getName());
                try {
                    Files.copy(file.toPath(),deletedFile.toPath());
                }catch (Exception e){
                    log.info(e.getMessage());

                }
                file.delete();
                if(!file.exists()){
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.info(e.getMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return false;
    }

}