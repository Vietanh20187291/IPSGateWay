package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;

@Component
@org.springframework.context.annotation.Configuration
@EnableIntegration
@Controller
@RequestMapping("/connection")
public class ConnectionController {
    Socket socket;
    Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired
    IntegrationFlowContext integrationFlowContext;
    @Lazy
    @Autowired
    ConfigurationController configurationController;
    @Autowired
    public Configuration configuration;
    @Autowired
    private ApplicationEventPublisher publisher;
    AbstractConnectionFactory serverCf;
    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("configuration") Configuration config,Model model) throws UnknownHostException {
        model.addAttribute("configuration", configuration); //Đổ ra view
        configuration.getConnection().setIpsIp(configurationController.getLocalHostAddress());
        return "connection";
    }
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@ModelAttribute("configuration") Configuration config) throws IOException {
        YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
            if(!config.getConnection().isRole()){
                //Client
                configuration.getConnection().setHostIp(config.getConnection().getHostIp());
                configuration.getConnection().setHostPort(config.getConnection().getHostPort());
                configuration.getLogger().setTimeout(config.getLogger().getTimeout());
                initializeClient();
            }else{
                //Server
                configuration.getConnection().setIpsIp(configurationController.getLocalHostAddress());
                configuration.getConnection().setIpsPort(config.getConnection().getIpsPort());
                configuration.getLogger().setTimeout(config.getLogger().getTimeout());
                initializeServer();
            }

        if (configuration.getConnection().isStatus()){
            //Change Log Level
            String loggerLevel = config.getLogger().getLogLevel();
            configurationController.setLogLevel(loggerLevel);
            //Save Config to YAML File
            configurationController.saveParamChanges();
            return "redirect:/configuration";
        }else{
            return "redirect:/connection";
        }
    }
    public void initializeClient() {
        try {
            socket = new Socket(configuration.getConnection().getHostIp(), configuration.getConnection().getHostPort());
            configuration.getConnection().setStatus(true);
            configuration.getConnection().setRole(false);
            log.info("Connect To Server Successfully");
            log.info(String.valueOf(socket));
            //Thread check connection status
            new Thread(() -> {
                try {
                    configuration.checkConnection();
                } catch (IOException e) {
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            configuration.getConnection().setStatus(false);
            log.info("Connect To Server Failed");
        }
    }
    public void initializeServer() {
        try{
            commandServerFlow();
            configuration.getConnection().setStatus(true);
            configuration.getConnection().setRole(true);
        } catch (Exception e) {
            log.info(String.valueOf(e));
            configuration.getConnection().setStatus(false);
            log.info("Server Has Failed To Start");
        }
    }
    public IntegrationFlow commandServerFlow() throws UnknownHostException {
        IntegrationFlow integrationFlow =  IntegrationFlows.from((serverConnectionFactory()))
                .transform(Transformers.objectToString())
                .log(m -> "payload=" + m.getPayload())
                .get();
        this.integrationFlowContext.registration(integrationFlow)
                .id("serverConnectionFactory.flow")
                .register();
        return integrationFlow;
    }
    public TcpInboundGateway serverConnectionFactory() throws UnknownHostException {
        serverCf = Tcp.netServer(configuration.getConnection().getIpsPort()).get();
        log.info("Server Started: " +  configurationController.getLocalHostAddress() +" Port: "+ serverCf.getPort() );
        log.info("Waiting For A Client ...");
        serverCf.setApplicationEventPublisher(publisher);
        return Tcp.inboundGateway(serverCf).get();
    }
    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeConnection() throws Exception {
        if(!this.configuration.getConnection().isRole()){
            socket.close();
            configuration.getConnection().setStatus(false);
            configuration.getConnection().setHostIp(null);
            log.info("Connection To The Server Has Been Closed");
        }else {
            integrationFlowContext.remove("serverConnectionFactory.flow");
            configuration.getConnection().setStatus(false);
            configuration.getConnection().setClientNumber(0);
            log.info("Server Has Been Shut Down");
        }
        if (configuration.getConnection().isStatus()){
            log.info("Close Connection Failed");
            return "redirect:/connection";
        }else{
            return "redirect:/configuration";
        }
    }
        /* HOST TCP/IP
        host = "10.145.48.96";
        port = 7777;
        */
}
