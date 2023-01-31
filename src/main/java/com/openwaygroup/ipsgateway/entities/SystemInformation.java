package com.openwaygroup.ipsgateway.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ToString
@Configuration
@ConfigurationProperties(prefix="system-information")
@PropertySource(value = "classpath:systemInformation.yml", factory = YamlPropertySourceFactory.class)
public class SystemInformation {
    @Getter
    @Setter
    private List<KeyManagement> systemInformation;

    public SystemInformation(List<KeyManagement> systemInformation) {
        this.systemInformation = systemInformation;
    }
    public SystemInformation() {
    }
}
