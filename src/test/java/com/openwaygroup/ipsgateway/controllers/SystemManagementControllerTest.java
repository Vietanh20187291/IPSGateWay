package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.entities.SystemInformation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SystemManagementControllerTest {
    @Autowired
    SystemInformation systemInformation;

    @Test
    void testImportServerFromInformationFile(){
        System.out.println(systemInformation.toString());
        assertNotNull(systemInformation);
    }

}