package com.openwaygroup.ipsgateway.entities;

import org.springframework.stereotype.Component;

@Component
public class Logger {

    private String name;
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Logger() {
    }

    public Logger(String name, String level) {
        this.name = name;
        this.level = level;
    }

}
