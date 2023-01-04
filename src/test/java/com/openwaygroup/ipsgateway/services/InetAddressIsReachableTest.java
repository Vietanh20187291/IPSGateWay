package com.openwaygroup.ipsgateway.services;

import com.openwaygroup.ipsgateway.controllers.ConfigurationController;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.entities.Connection;
import com.openwaygroup.ipsgateway.entities.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.ServerSocket;

import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@ContextConfiguration(classes = {ConfigurationController.class, Configuration.class, Connection.class, InetAddressIsReachable.class, Log.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class InetAddressIsReachableTest {
    @Autowired
    ConfigurationController configurationController;
    @Test
    void pingCheckIsReachable() throws IOException {
        String host =  configurationController.getLocalHostAddress();
        new ServerSocket(90);
        try {
            addressReachable(host, 90, 2000);
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping successful for host: " + host);
        } catch (Exception e) {
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping failed for host: " + host);
        }
    }
}