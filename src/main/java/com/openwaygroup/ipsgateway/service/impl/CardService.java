package com.openwaygroup.ipsgateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.entities.card.Field;
import com.openwaygroup.ipsgateway.enumurate.cardEnums.FieldsEnum;
import com.openwaygroup.ipsgateway.enumurate.cardEnums.RequiredFields;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


@Service
public class CardService implements ICardService {
    private static final String path = "src/main/resources/inputCards";
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);

    @Override
    public ArrayList<Card> loadCard(String path) throws IOException, NullPointerException {
        ArrayList<Card> listCard = new ArrayList<Card>();
        File file = null;
        try {
            file = new File(path);
        } catch (Exception e) {
            log.info("Error when load card from path in CardService " + e.getMessage());
        }
        log.info("-------CardService.loadCard()--------");
        log.info("Reading Yaml File from path");
        if (file.listFiles() == null) {
            log.info("Error in CardService: Cannot read any file from path");
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
    public Card getSampleCard() {

        EnumSet<FieldsEnum> fieldsEnums = EnumSet.allOf(FieldsEnum.class);
        Card card = new Card();
        List<Field> fields = new ArrayList<>();
        for (FieldsEnum fe : fieldsEnums) {
//            Access HashMaps and ArrayList by key(s)
            Field field = new Field();
            field.setFieldId(fe.getFieldId());
            field.setDescription(fe.getDescription());
            field.setName(fe.getName());
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
        return card;
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

    public String editCard(Card card) throws Exception {
        if (card != null) {
            if (existedPanNumber(card.getFieldById("F002").value)) {
                String checkInputValue = checkInputValue(card);
                if (checkInputValue.equals("success")) {
                    String id = card.getFieldById("F002").getValue();
                    if (deleteCard(id)) {
                        if (addCard(card).equals("success")) {
                            return "success";
                        }
                    } else {
                        return "Error when deleting card";

                    }
                } else {
                    return checkInputValue;

                }
            } else {
                return "Pan Number to edit is not exist";
            }
        }
        return "Card cannot be null";
    }

    public String addCard(Card card) throws Exception {
        if (card != null) {
            if (!existedPanNumber(card.getFieldById("F002").value)) {
                String checkInputValue = checkInputValue(card);
                if (checkInputValue.equals("success")) {
                    ObjectMapper objectMapper = new YAMLMapper();
                    String idCard = card.getFieldById("F002").getValue();
                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 0; i < card.getListField().size(); i++) {
                        String fieldId = card.getListField().get(i).getFieldId();
                        String value = card.getFieldById(fieldId).getValue();
                        map.put(fieldId, value);
                    }
                    objectMapper.writeValue(new File("src/main/resources/inputCards/" + idCard + ".yaml"), map);

                    return "success";

                } else {
                    return checkInputValue;
                }
            } else {
                return "Pan Number has been used (F002)";

            }
        }
        return "Card cannot be null";
    }


    public String checkInputValue(Card card) {
        EnumSet<FieldsEnum> fieldsEnums = EnumSet.allOf(FieldsEnum.class);

        for (FieldsEnum fieldsEnum : fieldsEnums) {
            Field field = card.getFieldById(fieldsEnum.getFieldId());
            if (field.value.length() > fieldsEnum.getMaxLength() || field.value.length() < fieldsEnum.getMinLength()) {
                boolean isRequiredField = isRequiredField(field);
                if (field.value.length() == 0 && !isRequiredField) {
                } else if (field.value.length() == 0 && isRequiredField) {
                    return field.getFieldId() + " cannot be null";
                } else {
                    if (fieldsEnum.getMinLength() == fieldsEnum.getMaxLength()) {
                        return "The data for " + fieldsEnum.getFieldId() + " must has " + fieldsEnum.getMinLength() + " characters";
                    }
                    return "The data for " + fieldsEnum.getFieldId() + " must has between " + fieldsEnum.getMinLength() + " and " + fieldsEnum.getMaxLength() + " characters";
                }
            }

        }

        String checkField35 = checkField35(card);
        String checkField45 = checkField45(card);
        if (!checkField35.equals("success")) {
            return checkField35;
        }
        if (!checkField45.equals("success")) {
            return checkField45;
        }
        return "success";

    }

    public boolean existedPanNumber(String id) throws IOException {
        ArrayList<Card> listCard = loadCard(path);
        for (Card card : listCard) {
            if (card.getFieldById("F002").value.equals(id)) {
                return true;

            }
        }
        return false;

    }

    public String checkField35(Card card) {
        String field35 = card.getFieldById("F035").value;
        int count = 0;
        int field35_break = 0;
        for (int i = 0; i < field35.length(); i++) {
            if (field35.charAt(i) == '=' || field35.charAt(i) == 'D') {
                count++;
                field35_break = i;
            }
        }
        if (count == 1) {
            String f035_01 = field35.substring(0, field35_break);
            String f035_02 = field35.substring(field35_break + 1, field35_break + 5);
            String f035_03 = field35.substring(field35_break + 5, field35_break + 8);
            String f035_04 = field35.substring(field35_break + 8, field35_break + 9);
            String f035_05 = field35.substring(field35_break + 9);
            if (!f035_01.equals(card.getFieldById("F002").value)) {
                return "F035.01 must be the same as Pan Number";
            } else if (!f035_02.equals(card.getFieldById("F014").value)) {
                return "F035.02 must be the same as Expiration Date";
            } else {
                return "success";
            }
        }

        return "Format for F035 is wrong";
    }

    public String checkField45(Card card) {
        String field45 = card.getFieldById("F045").value;
        int count = 0;
        for (int i = 0; i < field45.length(); i++) {
            if (field45.charAt(i) == '^') {
                count++;
            }
        }
        if (count == 2) {
            String f045_01 = field45.substring(0, 1);
            String f045_02 = field45.substring(1, field45.indexOf('^'));
            String f045_03 = field45.substring(field45.indexOf('^') + 1, field45.lastIndexOf('^'));
            String f045_04 = field45.substring(field45.lastIndexOf('^') + 1, field45.lastIndexOf('^') + 5);
            String f045_05 = field45.substring(field45.lastIndexOf('^') + 5, field45.lastIndexOf('^') + 8);
            String f045_06 = field45.substring(field45.lastIndexOf('^') + 8, field45.lastIndexOf('^') + 9);
            String f045_07 = field45.substring(field45.lastIndexOf('^') + 9);

            if (!f045_02.equals(card.getFieldById("F002").value)) {
                return "F045.02 must be the same as Pan Number";
            } else if (!f045_04.equals(card.getFieldById("F014").value)) {
                return "F045.04 must be the same as Expiration Date";
            } else {
                return "success";
            }
        }

        return "Format for F045 is wrong";
    }

    public boolean isRequiredField(Field field) {
        EnumSet<RequiredFields> requiredFields = EnumSet.allOf(RequiredFields.class);
        for (RequiredFields requiredField : requiredFields) {
            if (field.getFieldId().equals(requiredField.getField())) {
                return true;
            }
        }
        return false;
    }

    public String checkRequiredField(Card card) {
        EnumSet<RequiredFields> requiredFields = EnumSet.allOf(RequiredFields.class);
        List<Field> listField = card.getListField();
        for (Field field : listField) {
            for (RequiredFields requiredField : requiredFields) {
                if (field.getFieldId().equals(requiredField.getField())) {
                    if (field.value.length() == 0) {
                        return field.getFieldId() + " cannot be null";
                    }
                }
            }
        }
        return "success";
    }


    public boolean deleteCard(String id) throws Exception {
        log.info("Start delete card");

        try {

            File file = new File("src\\main\\resources\\inputCards\\" + id + ".yaml");

            try {
                log.info(file.getName());
                if (file.exists()) {
                    log.info("Found File");
                }
                //Copy file to deletedCard folder
                File deletedFile = new File("src\\main\\resources\\deletedCards\\" + file.getName());
                log.info(deletedFile.getName());
                try {
                    Files.copy(file.toPath(), deletedFile.toPath());
                } catch (Exception e) {
                    log.info(e.getMessage());

                }
                file.delete();
                if (!file.exists()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info(e.getMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return false;
    }

}
