package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.Socket;


import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@Component
@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "configuration")
@PropertySource(value = "classpath:configuration.yml", factory = YamlPropertySourceFactory.class)
public class Configuration {
        private String ipsIp;
        private String hostIp;
        private Integer ipsPort;
        private Integer hostPort;
        private boolean role;

        public String getIpsIp() {
            return ipsIp;
        }

        public void setIpsIp(String ipsIp) {
            this.ipsIp = ipsIp;
        }

        public String getHostIp() {
            return hostIp;
        }

        public void setHostIp(String hostIp) {
            this.hostIp = hostIp;
        }

        public Integer getIpsPort() {
            return ipsPort;
        }

        public void setIpsPort(Integer ipsPort) {
            this.ipsPort = ipsPort;
        }

        public Integer getHostPort() {
            return hostPort;
        }

        public void setHostPort(Integer hostPort) {
            this.hostPort = hostPort;
        }

        public boolean getRole() { return role; }

        public void setRole(boolean role) { this.role = role; }



        private String logLevel;
        private Integer timeout = 3000;
        public Integer getTimeout() {
            return timeout;
        }
        public String getLogLevel() {
            return logLevel;
        }
        public void setLogLevel(String logLevel) {
            this.logLevel = logLevel;
        }
        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }


    private Integer clientNumber = 0;
    private boolean status;
    public boolean getStatus() {return status;}
    public void setStatus(boolean status) {this.status = status;}
    public Integer getClientNumber() { return clientNumber; }
    public void setClientNumber(Integer clientNumber) { this.clientNumber = clientNumber; }
    public Configuration(){
    }

    private Configuration(String ipsIp, Integer ipsPort, String hostIp, Integer hostPort, boolean role, String logLevel, Integer timeout) {
        this.ipsIp = ipsIp;
        this.hostIp = hostIp;
        this.ipsPort = ipsPort;
        this.hostPort = hostPort;
        this.role = role;
        this.logLevel = logLevel;
        this.timeout = timeout;
    }

    //Check Client connection to Server
    public void checkConnection() throws IOException, InterruptedException {
        org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
        Socket socket;
        while(hostIp != null){
            status = addressReachable(hostIp, hostPort, timeout);
            if(status) {
                System.out.println("Host is reachable");
                Thread.sleep(4000);
            }else{
                while(!status){
                    try {
                        log.info("Host is unable to reach");
                        log.info("Reconnecting...");
                        socket = new Socket(hostIp, hostPort);
                        status = true;
                    } catch (Exception e){
                        log.info("Can't connect...");
                        status = false;
                    }
                }
            }

        }
    }


}
