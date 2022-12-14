package com.openwaygroup.ipsgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;

@Configuration
public class TcpConnectionService {
  /*  @Autowired
    public Configuration configuration;
*/
    @EventListener
    public void opens(TcpConnectionOpenEvent event) {
       /* this.configuration.setClientNumber(this.configuration.getClientNumber()+1);*/
        System.out.println("Connected! with event=" + event.getConnectionId());
    }
    @EventListener
    public void closes(TcpConnectionCloseEvent event){
        System.out.println("Disconnected! with event=" + event.getConnectionId()) ;
    }
    
    

}
