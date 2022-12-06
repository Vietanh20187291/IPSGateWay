package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ConfigurationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    public Configuration configuration = Configuration.getInstance();
    @Test
    void testConnectionInitializeClient() throws IOException {
        boolean expectedResult = true;
        ConfigurationController configurationController = new ConfigurationController();
      /*  Configuration connection = new Configuration(communicationController.getLocalHostAddress(), 9999,"4.2.2.2", 53,false);*/
        this.configuration.setHostIp("4.2.2.2");
        this.configuration.setHostPort(53);
        this.configuration.setRole(false);
        boolean actualResult = configurationController.initializeClient(configuration);
        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    void testConnectionInitializeServer() throws IOException{
        boolean expectedResult = true;
        ConfigurationController configurationController = new ConfigurationController();
       /* Configuration connection = new Configuration(communicationController.getLocalHostAddress(),9999,true);*/
        this.configuration.setVtsIp(configurationController.getLocalHostAddress());
        this.configuration.setVtsPort(9999);
        this.configuration.setRole(true);
        boolean actualResult = configurationController.initializeServer(configuration);
        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }

  /*  @Test
    void testConnectToServer() throws IOException{
        String expectedResult = '';

        String sentence_to_server;
        String sentence_from_server;
        Socket socket = new Socket(this.connection.getVtsIp(), this.connection.getVtsPort());

        sentence_to_server = "Hello World 2!";
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(sentence_to_server + '\n');
        sentence_from_server = inFromServer.readLine();
        System.out.println("FROM SERVER: " + sentence_from_server);

        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }*/

    @Test
    void testCommunicationIndex() throws Exception{
        this.mockMvc
                .perform(get("/configuration"))
                .andExpect(status().isOk())
                .andExpect(view().name("communication"))
                .andExpect(model().attributeExists("edit"));
    }

    @Test
    void connect() throws Exception{
        this.mockMvc
                .perform(post("/connect"));
    }

    @Test
   /* @SpringBootTest(classes={com.openwaygroup.ipsgateway.controller.class})*/
    void testConnectionLostClient() throws IOException, InterruptedException {
        Socket socket;
        Configuration configuration = Configuration.getInstance();
        configuration.setHostIp("4.2.2.2");
        configuration.setHostPort(53);
        configuration.setRole(false);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedResult  = "------------------------------\nInitializeClient Success\n"
                + "true\n"
                + "Sending Ping Request to /55.12.34.53\n"
                + "Host is NOT reachable\n"
                + "Trying to reconnect\n";

        ConfigurationController configurationController = new ConfigurationController();
        configurationController.initializeClient(configuration);
        Thread.sleep(30000);
        configuration.setHostIp("55.12.34.53");
        Thread.sleep(30000);
        configuration.setHostIp("4.2.2.2");
        Thread.sleep(30000);
        assertEquals(expectedResult, outContent.toString());
   /*     assertNotEquals(expectedResult, outContent.toString());*/
    }

    private void assertNotEquals(String expectedResult, String s) {
    }

}
