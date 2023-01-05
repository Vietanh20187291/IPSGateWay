package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ToString
@PropertySource(value = "classpath:systemInformation.yml", factory = YamlPropertySourceFactory.class)
public class Key {
    @Getter
    @Setter
    private Component component1;
    @Getter
    @Setter
    private Component component2;
    @Getter
    @Setter
    private Component component3;
    @Getter
    @Setter
    private String combined;
    @Getter
    @Setter
    private String kcv;
}
