package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class SystemControllerTest {

    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);

    @Test
    void changeLogLevel() {
        log.info("[getMessage] info message");
        log.warn("[getMessage] warn message");
        log.error("[getMessage] error message");
        log.debug("[getMessage] debug message");
        log.trace("[getMessage] trace message");
    }

}