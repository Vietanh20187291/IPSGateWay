package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "log")
@PropertySource(value = "classpath:configuration.yml", factory = YamlPropertySourceFactory.class)
public class Log {
    @Getter
    @Setter
    private String logLevel;
    @Getter
    @Setter
    private Integer timeout = 3000;
}
