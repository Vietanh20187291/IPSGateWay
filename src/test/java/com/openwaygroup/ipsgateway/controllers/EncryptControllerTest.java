package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ContextConfiguration(classes = {EncryptController.class,Log.class})
@SpringBootTest
class EncryptControllerTest {

    @Autowired
    EncryptController encryptController;

    @Test
    void testGetKcv() throws GeneralSecurityException {
         String key = "BCD66B9B01A1CE8313AE984025204FE9";
        ResponseEntity<String> responseEntity = encryptController.getKcv(key);
        assertEquals(responseEntity.getStatusCodeValue(),200);
        assertEquals(responseEntity.getBody(),"C8F1D1");
    }

}