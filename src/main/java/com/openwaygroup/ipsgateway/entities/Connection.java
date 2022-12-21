package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "connection")
@PropertySource(value = "classpath:configuration.yml", factory = YamlPropertySourceFactory.class)
public class Connection {
    @Getter
    @Setter
    private String ipsIp;
    @Getter
    @Setter
    private String hostIp;
    @Getter
    @Setter
    private Integer ipsPort;
    @Getter
    @Setter
    private Integer hostPort;
    @Getter
    @Setter
    private boolean role;
    @Getter
    @Setter
    private Integer clientNumber = 0;
    @Getter
    @Setter
    private boolean status;
}
