package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.Socket;
import java.lang.System;
import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@Component
@ConfigurationProperties(prefix = "configuration")
@PropertySource(value = "classpath:configuration.yml", factory = YamlPropertySourceFactory.class)
public class Configuration {
    @Autowired
    @Getter
    @Setter
    private Connection connection;
    @Autowired
    @Getter
    @Setter
    private Log logger;
    public Configuration(){
    }
    //Check Client connection to Server
    public void checkConnection() throws IOException, InterruptedException {
        org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
        while(connection.getHostIp() != null){
            connection.setStatus(addressReachable(connection.getHostIp(), connection.getHostPort(), logger.getTimeout()));
            if(connection.isStatus()) {
                System.out.println("Host is reachable");
                Thread.sleep(4000);
            }else{
                while(!connection.isStatus()){
                    try {
                        log.info("Host is unable to reach");
                        log.info("Reconnecting...");
                        new Socket(connection.getHostIp(), connection.getHostPort());
                        connection.setStatus(true);
                    } catch (Exception e){
                        log.info("Can't connect...");
                        connection.setStatus(false);
                    }
                }
            }
        }

    }


}
