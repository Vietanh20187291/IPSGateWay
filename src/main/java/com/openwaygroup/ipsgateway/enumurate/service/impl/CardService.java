package com.openwaygroup.ipsgateway.enumurate.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.entities.card.Field;
import com.openwaygroup.ipsgateway.enumurate.CardEnum;
import com.openwaygroup.ipsgateway.enumurate.service.ICardService;
import com.openwaygroup.ipsgateway.exception.CardException;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.sort;


@Service
public class CardService implements ICardService {

    @Override
    public ArrayList<Card> loadCard(String path) throws IOException, CardException {


        ArrayList<Card> listCard = new ArrayList<Card>();
        File file = null;
        try {
            file = new File(path);

        } catch (Exception e) {
            System.out.println("Error when load card from path in CardService " + e.getMessage());
        }

        System.out.println("-------CardService.loadCard()--------");
        System.out.println("Reading Yaml File from path");
        if (file.listFiles().length == 0) {
            System.out.println("Error in CardService: Cannot read any file from path");
            throw new CardException("Cannot read any file from path");
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
                List<String> requiredFields = List.of(new String[]{"F002", "F014", "F035", "F053.13", "F126.10", "F053.08"});
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

    public boolean editCard(Card card) throws Exception{
        if(card!=null){
            String id = card.getByFieldId("F002").getValue();
            if(deleteCard(id)){
                if(addCard(card)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean addCard(Card card) throws Exception{
        if(card!=null){
            ObjectMapper objectMapper = new YAMLMapper();
            String idCard = card.getByFieldId("F002").getValue();
            Map<String, String> map = new HashMap<String, String>();
            for(int i = 0; i<card.getListField().size();i++){
                String fieldId = card.getListField().get(i).getFieldId();
                String value = card.getByFieldId(fieldId).getValue();
                map.put(fieldId,value);
            }
            objectMapper.writeValue(new File("src/main/resources/inputCards/"+idCard+".yaml"), map);

            return true;
        }
        return false;
    }


    public boolean deleteCard(String id) throws Exception {
        System.out.println("Start delete card");

        try {

            File file = new File("src\\main\\resources\\inputCards\\" + id + ".yaml");

            if (file.delete()) {
                System.out.println("Card deleted successfully");
                return true;
            } else {
                System.out.println("Failed to delete the card");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
