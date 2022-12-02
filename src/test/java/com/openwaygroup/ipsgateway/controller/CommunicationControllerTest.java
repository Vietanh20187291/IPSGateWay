package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Connection;
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
class CommunicationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    public Connection connection = Connection.getInstance();
    @Test
    void testConnectionInitializeClient() throws IOException {
        boolean expectedResult = true;
        CommunicationController communicationController = new CommunicationController();
      /*  Connection connection = new Connection(communicationController.getLocalHostAddress(), 9999,"4.2.2.2", 53,false);*/
        this.connection.setHostIp("4.2.2.2");
        this.connection.setHostPort(53);
        this.connection.setRole(false);
        boolean actualResult = communicationController.initializeClient(connection);
        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    void testConnectionInitializeServer() throws IOException{
        boolean expectedResult = true;
        CommunicationController communicationController = new CommunicationController();
       /* Connection connection = new Connection(communicationController.getLocalHostAddress(),9999,true);*/
        this.connection.setVtsIp(communicationController.getLocalHostAddress());
        this.connection.setVtsPort(9999);
        this.connection.setRole(true);
        boolean actualResult = communicationController.initializeServer(connection);
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
                .perform(get("/communication"))
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
        Connection connection = Connection.getInstance();
        connection.setHostIp("4.2.2.2");
        connection.setHostPort(53);
        connection.setRole(false);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedResult  = "------------------------------\nInitializeClient Success\n"
                + "true\n"
                + "Sending Ping Request to /55.12.34.53\n"
                + "Host is NOT reachable\n"
                + "Trying to reconnect\n";

        CommunicationController communicationController = new CommunicationController();
        communicationController.initializeClient(connection);
        Thread.sleep(30000);
        connection.setHostIp("55.12.34.53");
        Thread.sleep(30000);
        connection.setHostIp("4.2.2.2");
        Thread.sleep(30000);
        assertEquals(expectedResult, outContent.toString());
   /*     assertNotEquals(expectedResult, outContent.toString());*/
    }

    private void assertNotEquals(String expectedResult, String s) {
    }

}
