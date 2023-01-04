package com.openwaygroup.ipsgateway.services;


import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class TcpConnectionService {
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired
    public com.openwaygroup.ipsgateway.entities.Configuration configuration;
    @EventListener
    public void opens(TcpConnectionOpenEvent event) {
        configuration.getConnection().setClientNumber(this.configuration.getConnection().getClientNumber()+1);
        log.info("Client Connected: " + event.getConnectionId());
    }
    @EventListener
    public void closes(TcpConnectionCloseEvent event){
        configuration.getConnection().setClientNumber(this.configuration.getConnection().getClientNumber()-1);
        log.info("Client Disconnected: " + event.getConnectionId());
    }
    
    

}
