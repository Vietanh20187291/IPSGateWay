package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "classpath:systemInformation.yml", factory = YamlPropertySourceFactory.class)
@ToString
public class KeyManagement {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private Key zpk;
    @Getter
    @Setter
    private Key tcmk;
    public KeyManagement(){

    }
}
