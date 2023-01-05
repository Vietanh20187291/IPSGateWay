package com.openwaygroup.ipsgateway.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EncryptControllerTest {

    @Autowired
    EncryptController encryptController;

    @Test
    void testGetKcv() throws GeneralSecurityException {
        encryptController.getKcv();
    }
}