package com.openwaygroup.ipsgateway.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ToString
public class SystemInformation {
    @Getter
    @Setter
    private Map<Integer,KeyManagement> systemInformation;
}
