package com.openwaygroup.ipsgateway.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwaygroup.ipsgateway.entities.*;
import com.openwaygroup.ipsgateway.services.InetAddressIsReachable;
import com.openwaygroup.ipsgateway.services.TcpConnectionService;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
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
    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;
    @Mock
    RedirectAttributes redirectAttributes;
    void setGroupKey(){
        keyManagement.setId(9999);
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
        keyManagement.setZpk(zpk);
        keyManagement.setTcmk(tcmk);
    }

    @Test
    void testCreateKeyGroup(){
        setGroupKey();
        systemManagementController.store(keyManagement,redirectAttributes,bindingResult,model);
        boolean actualResult = false;
        for (KeyManagement keyMan : systemInformation.getSystemInformation()) {
            if(Objects.equals(keyManagement.getId(), keyMan.getId())){
                actualResult = true;
            }
        }
        assertTrue(actualResult);
    }

}