package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Protocol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommunicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void initializeClient() throws IOException {
      /*  boolean expectedResult = true;
        Protocol protocol = new Protocol("4.2.2.2", 53, "192.168.242.1", 9999,false);

        CommunicationController communicationController = new CommunicationController();
        boolean actualResult = communicationController.initializeClient(protocol);
        assertEquals(expectedResult,actualResult);*/
    }

    void initializeServer() throws IOException{
      /*  boolean expectedResult = true;
        Protocol protocol = new Protocol("4.2.2.2", 53,true);
        CommunicationController communicationController = new CommunicationController();
        boolean actualResult = communicationController.initializeServer(protocol);
        assertEquals(expectedResult,actualResult);*/
    }

    @Test
    void connect() throws Exception{

    }




}