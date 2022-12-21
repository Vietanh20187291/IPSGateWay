package com.openwaygroup.ipsgateway.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class TestConfig {
    @Bean
    public ServerSocket server() throws IOException {
        return new ServerSocket(9999);
    }
}