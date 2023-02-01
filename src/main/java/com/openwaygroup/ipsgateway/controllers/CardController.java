package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController(value = "/")
public class CardController {

    @PostMapping("card")
    public ResponseEntity<Card> test(@RequestParam(name = "data") MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
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
        String pinBlock = (String)database.get("pinBlock");
        String track1Structure = (String)database.get("track1Structure");
        String track2Structure = (String)database.get("track2Structure");
        Card card = new Card(cardNumber,expiryDate,track1,track2,chipData,pinBlock,track1Structure,track2Structure);
        System.out.println(card.toString());
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}
