package com.openwaygroup.ipsgateway.entities;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Component
public class Connection extends Thread{

    private static Connection instance;
    private String vtsIp;

    private String hostIp;

    private Integer vtsPort;

    private Integer hostPort;

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
        InetAddress inet;
        while(hostIp != null){
            inet = InetAddress.getByName(hostIp);
            System.out.println("Sending Ping Request to " + inet);
            System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
            Thread.sleep(4000);
        }

    }
}
