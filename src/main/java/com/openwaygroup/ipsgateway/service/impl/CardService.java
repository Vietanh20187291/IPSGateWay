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
        String f002 = (String) yamlMap.get("F002");
        String f014 = (String) yamlMap.get("F014");
        String f023= (String) yamlMap.get("F023");

        HashMap f035 = (HashMap) yamlMap.get("F035");
        String f035_04 = (String) f035.get("04");
        String f035_05 = (String) f035.get("05");
        String f035_06 = (String) f035.get("06");
        String f035_07 = (String) f035.get("07");
        String f035_08 = (String) f035.get("08");


        String f045= (String) yamlMap.get("F045");

        HashMap f045_subs = (HashMap) yamlMap.get("F045 Subs");
        String f045_01 = (String) f035.get("01");
        String f045_02 = (String) f035.get("02");
        String f045_04 = (String) f035.get("04");
        String f045_06 = (String) f035.get("06");
        String f045_07 = (String) f035.get("07");
        String f045_08 = (String) f035.get("08");
        String f045_09 = (String) f035.get("09");
        String f045_10 = (String) f035.get("10");
        String f045_11 = (String) f035.get("11");
        String f045_12_2 = (String) f035.get("12.2");

        String f052= (String) yamlMap.get("F052");

        HashMap f053 = (HashMap) yamlMap.get("F053");
        String f053_03 = (String) f053.get("03");
        String f053_04 = (String) f053.get("04");
        String f053_05 = (String) f053.get("05");
        String f053_06 = (String) f053.get("06");
        String f053_07 = (String) f053.get("07");
        String f053_08 = (String) f053.get("08");
        String f053_09 = (String) f053.get("09");
        String f053_10 = (String) f053.get("10");
        String f053_11 = (String) f053.get("11");
        String f053_12 = (String) f053.get("12");
        String f053_13 = (String) f053.get("13");


        HashMap f055 = (HashMap) yamlMap.get("F055");
//        System.out.println(f055);
//        HashMap f055_07 = new HashMap<>();
//        try {
//             f055_07 = (HashMap) f055.get("7");
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        String f055_07_03 = (String) f055_07.get("03");
//        String f055_07_04 = (String) f055_07.get("04");
//        String f055_07_05 = (String) f055_07.get("05");
//        String f055_07_06 = (String) f055_07.get("06");
//        String f055_07_07 = (String) f055_07.get("07");
//        String f055_07_08 = (String) f055_07.get("08");
//        String f055_07_09 = (String) f055_07.get("09");
//        String f055_07_10 = (String) f055_07.get("10");


        //Nhap
        String f055_07_03 = "m";
        String f055_07_04 = "m";
        String f055_07_05 = "m";
        String f055_07_06 = "m";
        String f055_07_07 = "m";
        String f055_07_08 = "m";
        String f055_07_09 = "m";
        String f055_07_10 = "m";
        //Nhap

        String f055_21 = (String) f055.get("21");
        String f055_22 = (String) f055.get("22");
        String f055_23_08 = (String) f055.get("23.08");

        String f123_11 = (String) yamlMap.get("F123.11");

        HashMap f125 = (HashMap) yamlMap.get("F125");
        String f125_025 = (String) f053.get("025");
        String f125_034 = (String) f053.get("034");
        String f125_048 = (String) f053.get("048");
        String f125_057 = (String) f053.get("057");

        String f126_10 = (String) yamlMap.get("F126.10");
        String f134 = (String) yamlMap.get("F134");
        String f135 = (String) yamlMap.get("F135");
        String f136 = (String) yamlMap.get("F136");
        String f137 = (String) yamlMap.get("F137");
        String f138 = (String) yamlMap.get("F138");
        String f152 = (String) yamlMap.get("F152");





//        if (StringUtils.hasText(cardNumber) || StringUtils.hasText(expiryDate) || StringUtils.hasText(track1)
//                || StringUtils.hasText(track2) || StringUtils.hasText(chipData)) {
//            throw new NullPointerException("203");
//        }

            Card card = new Card(f002, f014, f023, f035_04, f035_05, f035_06, f035_07, f035_08, f045, f045_01, f045_02, f045_04, f045_06, f045_07, f045_08, f045_09, f045_10, f045_11, f045_12_2, f052, f053_03, f053_04, f053_05, f053_06, f053_07, f053_08, f053_09, f053_10, f053_11, f053_12, f053_13, f055_07_03, f055_07_04, f055_07_05, f055_07_06, f055_07_07, f055_07_08, f055_07_09, f055_07_10, f055_21, f055_22, f055_23_08, f123_11, f125_025, f125_034, f125_048, f125_057, f126_10, f134, f135, f136, f137, f138, f152);
            card.toString();
        System.out.println("You are at Card Service");
            return card;


    }
}
