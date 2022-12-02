package com.openwaygroup.ipsgateway.entities;

import com.openwaygroup.ipsgateway.controller.CommunicationController;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;

@Component
public class Connection{

    private static Connection instance;
    private String vtsIp;

    private String hostIp;

    private Integer vtsPort;

    private Integer hostPort;
    private Integer clientNumber = 0;

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

    public static Connection getInstance(){
        if(instance == null){
            instance = new Connection();
        }
        return instance;
    }

    private Connection(){
    }

    private Connection(String vtsIp, Integer vtsPort, String hostIp, Integer hostPort, boolean role) {
        this.vtsIp = vtsIp;
        this.hostIp = hostIp;
        this.vtsPort = vtsPort;
        this.hostPort = hostPort;
        this.role = role;
    }

    private Connection(String vtsIp, Integer vtsPort, boolean role) {
        this.vtsIp = vtsIp;
        this.vtsPort = vtsPort;
        this.role = role;
    }

    private Connection(String vtsIp, Integer vtsPort) {
        this.vtsIp = vtsIp;
        this.vtsPort = vtsPort;
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
