package com.openwaygroup.ipsgateway.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwaygroup.ipsgateway.entities.*;
import com.openwaygroup.ipsgateway.services.InetAddressIsReachable;
import com.openwaygroup.ipsgateway.services.TcpConnectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = {SystemManagementController.class,SystemInformation.class, KeyManagement.class, Key.class, Component.class,Configuration.class, Connection.class,Log.class})
@SpringBootTest
class SystemManagementControllerTest {
    @Autowired
    SystemInformation systemInformation;
    @Autowired
    SystemManagementController systemManagementController;
    KeyManagement keyManagement = new KeyManagement();
    Key zpk = new Key();
    Key tcmk = new Key();
    Component component1 = new Component();
    Component component2 = new Component();
    Component component3 = new Component();
    void setGroupKey(){
        keyManagement.setId(99);
        component1.setData("BCD66B9B01A1CE8313AE984025204FE9");
        component2.setData("BCD66B9B01A1CE8313AE984025204FE9");
        component3.setData("BCD66B9B01A1CE8313AE984025204FE9");
        component1.setKcv("C8F1D1");
        component2.setKcv("C8F1D1");
        component3.setKcv("C8F1D1");
        zpk.setComponent1(component1);
        zpk.setComponent2(component2);
        zpk.setComponent3(component3);
        tcmk.setComponent1(component1);
        tcmk.setComponent2(component2);
        tcmk.setComponent3(component3);
        zpk.setCombined("BCD66B9B01A1CE8313AE984025204FE9");
        zpk.setKcv("C8F1D1");
        tcmk.setCombined("BCD66B9B01A1CE8313AE984025204FE9");
        tcmk.setKcv("C8F1D1");
    }
    @Test
    void testImportServerFromInformationFile(){
        System.out.println(systemInformation.toString());
        assertNotNull(systemInformation);
    }

    @Test
    void testCreateKeyGroup(){
        setGroupKey();
        systemInformation.getSystemInformation().add(keyManagement);
        boolean actualResult = false;
        for (KeyManagement keyMan : systemInformation.getSystemInformation()) {
            if(keyManagement.getId() == keyMan.getId()){
                actualResult = true;
            }
        }
        assertTrue(actualResult);
    }

}