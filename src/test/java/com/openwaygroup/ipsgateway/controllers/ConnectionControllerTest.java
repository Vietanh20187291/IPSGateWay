package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.entities.Connection;
import com.openwaygroup.ipsgateway.entities.Log;
import com.openwaygroup.ipsgateway.services.InetAddressIsReachable;
import com.openwaygroup.ipsgateway.services.TcpConnectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ConnectionController.class,ConfigurationController.class, Configuration.class, Connection.class, Log.class, TcpConnectionService.class, InetAddressIsReachable.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({TestConfig.class})
class ConnectionControllerTest {
    @Autowired
    private Configuration configuration;
    Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired
    ConnectionController connectionController;
    @Autowired
    ConfigurationController configurationController;

    void setConfigurationMockHost() throws IOException {
        String localHost =  configurationController.getLocalHostAddress();
        configuration.getConnection().setHostIp(localHost);
        configuration.getConnection().setHostPort(9999);
        configuration.getConnection().setRole(false);
    }
    void setConfigurationMockIps() throws UnknownHostException {
        String localHost =  configurationController.getLocalHostAddress();
        configuration.getConnection().setIpsIp(localHost);
        configuration.getConnection().setIpsPort(7777);
        configuration.getConnection().setRole(true);
    }
    Socket clientDemo() throws UnknownHostException,
            IOException {
        return new Socket(configuration.getConnection().getIpsIp(), configuration.getConnection().getIpsPort());
    }
    @Test
    void testConnectionInitializeClient() throws Exception {
        boolean expectedResult = true;
        setConfigurationMockHost();
        connectionController.update(configuration);
        boolean actualResult = configuration.getConnection().isStatus();
        assertEquals(expectedResult,actualResult);
    }
    @Test
    void testConnectionInitializeServer() throws Exception {
        boolean expectedResult = true;
        setConfigurationMockIps();
        connectionController.update(configuration);
        boolean actualResult = configuration.getConnection().isStatus();
        assertEquals(expectedResult,actualResult);
        connectionController.closeConnection();
    }
    @Test
    void testClientLostConnectionToServer() throws Exception {
        boolean expectedResult  = false;
        setConfigurationMockHost();
        connectionController.update(configuration);
        Thread.sleep(10000);
        configuration.getConnection().setHostIp("55.12.34.53");
        Thread.sleep(10000);
        boolean actualResult = configuration.getConnection().isStatus();
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void testClientReconnectToServer() throws Exception {
        boolean expectedResult  = true;
        String localHost =  configurationController.getLocalHostAddress();
        setConfigurationMockHost();
        connectionController.update(configuration);
        Thread.sleep(15000);
        configuration.getConnection().setHostIp("55.12.34.53");
        Thread.sleep(15000);
        configuration.getConnection().setHostIp(localHost);
        Thread.sleep(15000);
        boolean actualResult = configuration.getConnection().isStatus();
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void testIpsServerHasMultipleConnection() throws Exception {
        Integer expectedResult  = 3;
        setConfigurationMockIps();
        connectionController.update(configuration);
        Socket socket1 = clientDemo();
        Thread.sleep(5000);
        Socket socket2 = clientDemo();
        Thread.sleep(5000);
        Socket socket3 = clientDemo();
        Thread.sleep(5000);
        Integer actualResult = configuration.getConnection().getClientNumber();
       assertEquals(expectedResult, actualResult);
       connectionController.closeConnection();
    }

    @Test
    void testClientDisconnectFromIpsServer() throws Exception {
        Integer expectedResult  = 2;
        setConfigurationMockIps();
        connectionController.update(configuration);
        Socket socket1 = clientDemo();
        Thread.sleep(5000);
        Socket socket2 = clientDemo();
        Thread.sleep(5000);
        Socket socket3 = clientDemo();
        Thread.sleep(5000);
        socket1.close();
        Thread.sleep(5000);
        Integer actualResult = configuration.getConnection().getClientNumber();
        assertEquals(expectedResult, actualResult);
        connectionController.closeConnection();
    }

    @Test
    void testShutDownServer() throws Exception {
        boolean expectedResult = false;
        setConfigurationMockIps();
        connectionController.update(configuration);
        connectionController.closeConnection();
        boolean actualResult = configuration.getConnection().isStatus();

    }
    @Test
    void testCloseConnectionToServer() throws Exception {
        boolean expectedResult = false;
        setConfigurationMockHost();
        connectionController.update(configuration);
        connectionController.closeConnection();
        boolean actualResult = configuration.getConnection().isStatus();
        assertEquals(expectedResult,actualResult);
    }
}
