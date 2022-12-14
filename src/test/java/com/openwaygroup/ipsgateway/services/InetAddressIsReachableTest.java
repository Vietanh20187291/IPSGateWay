package com.openwaygroup.ipsgateway.services;

import com.openwaygroup.ipsgateway.controller.ConfigurationController;
import com.openwaygroup.ipsgateway.entities.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.ServerSocket;

import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@ContextConfiguration(classes = {ConfigurationController.class, Configuration.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class InetAddressIsReachableTest {
    /*
     * Address: www.google.com
     * port: 80 or 443
     * timeout: 2000 (in milliseconds)
     */
    @Autowired
  ConfigurationController configurationController;


    @Test
    void pingCheckIsReachable() throws IOException {
        String host =  configurationController.getLocalHostAddress();

        ServerSocket serverSocket = new ServerSocket(90);
        try {
            addressReachable(host, 90, 2000);
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping successful for host: " + host);
        } catch (Exception e) {
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping failed for host: " + host);
        }
    }
}