package com.openwaygroup.ipsgateway.entities;

public class Protocol {
    private String vtsIp;
    private String hostIp;
    private Integer vtsPort;
    private Integer hostPort;
    private boolean role;


    public Integer getTimeout() {
        return timeout;
    }
    private Integer timeout = 5000;

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
    public Protocol() {

    }
    public Protocol(String vtsIp, Integer vtsPort, String hostIp, Integer hostPort, boolean role) {
        this.vtsIp = vtsIp;
        this.hostIp = hostIp;
        this.vtsPort = vtsPort;
        this.hostPort = hostPort;
        this.role = role;
    }

    public Protocol(String vtsIp, Integer vtsPort, boolean role) {
        this.vtsIp = vtsIp;
        this.vtsPort = vtsPort;
        this.role = role;
    }

}
