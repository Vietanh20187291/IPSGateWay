package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@Component
@ConfigurationProperties("configuration") // prefix configuration, find configuration.* values
@PropertySource(value = "classpath:configuration.yaml", factory = YamlPropertySourceFactory.class)
public class Configuration {

    private static Configuration instance;
    private String vtsIp;
    @Value("${configuration.hostIp}")
    private String hostIp;
    @Value("${configuration.vtsPort}")
    private Integer vtsPort;
    @Value("${configuration.hostPort}")
    private Integer hostPort;
    private Integer clientNumber = 0;
    @Value("${configuration.logLevel}")
    private String logLevel;
    @Value("${configuration.role}")
    private boolean role;
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getVtsIp() {
        return vtsIp;
    }

    public void setVtsIp(String vtsIp) {
        this.vtsIp = vtsIp;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getVtsPort() {
        return vtsPort;
    }

    public Integer getClientNumber() { return clientNumber; }
    public String getLogLevel() {
        return logLevel;
    }

    public void setVtsPort(Integer vtsPort) {
        this.vtsPort = vtsPort;
    }

    public Integer getHostPort() {
        return hostPort;
    }

    public void setHostPort(Integer hostPort) {
        this.hostPort = hostPort;
    }

    public boolean getRole() { return role; }

    public void setRole(boolean role) { this.role = role; }
    public void setClientNumber(Integer clientNumber) { this.clientNumber = clientNumber; }
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public static Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    private Configuration(){
    }

    private Configuration(String vtsIp, Integer vtsPort, String hostIp, Integer hostPort, boolean role) {
        this.vtsIp = vtsIp;
        this.hostIp = hostIp;
        this.vtsPort = vtsPort;
        this.hostPort = hostPort;
        this.role = role;
    }

    private Configuration(String vtsIp, Integer vtsPort, boolean role) {
        this.vtsIp = vtsIp;
        this.vtsPort = vtsPort;
        this.role = role;
    }

    private Configuration(String vtsIp, Integer vtsPort) {
        this.vtsIp = vtsIp;
        this.vtsPort = vtsPort;
    }

    private Configuration(String logLevel){
        this.logLevel = logLevel;
    }

    public void checkConnection() throws IOException, InterruptedException {
        Socket socket;
        while(hostIp != null){
            status = addressReachable(hostIp, hostPort, 3000);
            if(status) {
                System.out.println("Host is reachable");
                Thread.sleep(4000);
            }else{
                while(!status){
                    try {
                        System.out.println("Reconnecting...");
                        socket = new Socket(hostIp, hostPort);
                        status = true;
                    } catch (Exception e){
                        System.out.println("Can't connect...");
                        status = false;
                    }
                }
            }

        }
    }
}
