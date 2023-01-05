package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix="system-information")
@PropertySource(value = "classpath:systemInformation.yml", factory = YamlPropertySourceFactory.class)
@ToString
public class SystemInformation {
    @Getter
    @Setter
    private List<KeyManagement> systemInformation;
    @Getter
    @Setter
    private KeyManagement keyManagement;
    public SystemInformation() {
    }
}
