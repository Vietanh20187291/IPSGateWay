package com.openwaygroup.ipsgateway.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
@Configuration
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
}
