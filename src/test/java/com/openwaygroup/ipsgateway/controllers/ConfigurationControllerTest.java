package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.entities.Connection;
import com.openwaygroup.ipsgateway.entities.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ConnectionController.class,ConfigurationController.class, Configuration.class, Connection.class, Log.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ConfigurationControllerTest {
    @Autowired
    private Configuration configuration;
    Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired
    ConfigurationController configurationController;

    @Test
    void testChangeLogLevel() {
        configurationController.setLogLevel("info");
        log.info("[getMessage] info message");
        log.warn("[getMessage] warn message");
        log.error("[getMessage] error message");
        log.debug("[getMessage] debug message");
        log.trace("[getMessage] trace message");
    }
    @Test
    void testImportServerFromConfigurationFile(){
        assertNotNull(configuration.getConnection().getIpsPort());
        assertNotNull(configuration.getConnection().isRole());
        assertNotNull(configuration.getLogger().getLogLevel());
    }

    @Test
    void testImportClientFromConfigurationFile(){
        assertNotNull(configuration.getConnection().getHostIp());
        assertNotNull(configuration.getConnection().getHostPort());
        assertNotNull(configuration.getConnection().isRole());
        assertNotNull(configuration.getLogger().getLogLevel());
    }

}
