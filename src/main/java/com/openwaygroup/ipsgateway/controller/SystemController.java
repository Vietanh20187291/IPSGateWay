package com.openwaygroup.ipsgateway.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Connection;
import com.openwaygroup.ipsgateway.entities.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.UnknownHostException;

@Component
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    public Connection connection = Connection.getInstance();
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @RequestMapping(method = RequestMethod.GET)
    public String system(@ModelAttribute("logger") Logger logger, Model model) throws UnknownHostException {
        model.addAttribute("connection", this.connection);
        model.addAttribute("logger", logger);
        System.out.println("------------------------------");
        return "system";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String changeLogLevel(@ModelAttribute("logger") Logger logger, Model model){
        model.addAttribute("logger", logger);
        String loggerName = logger.getName();
        String loggerLevel = logger.getLevel();

        ch.qos.logback.classic.Logger root = getLogger(loggerName);
        setLogLevel(loggerLevel, root);
        return "redirect:system";
    }

    private ch.qos.logback.classic.Logger getLogger(String loggerName) {
        ch.qos.logback.classic.Logger root;
        if (loggerName != null && !loggerName.isEmpty())
            root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        else
            root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        return root;
    }

    public void setLogLevel(String loggerLevel, ch.qos.logback.classic.Logger root) {
        if ("info".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.INFO);
        } else if ("debug".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.DEBUG);
        } else if ("warn".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.WARN);
        } else if ("trace".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.TRACE);
        } else if ("error".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.ERROR);
        } else if ("off".equalsIgnoreCase(loggerLevel)) {
            root.setLevel(ch.qos.logback.classic.Level.OFF);
        } else {
            root.setLevel(ch.qos.logback.classic.Level.ALL);
            loggerLevel = "all";
        }
        System.out.println("logger level set as " + loggerLevel);
    }

    @RequestMapping (value ="/message", method = RequestMethod.POST)
    public String getMessage(){
        log.info("[getMessage] info message");
        log.warn("[getMessage] warn message");
        log.error("[getMessage] error message");
        log.debug("[getMessage] debug message");
        log.trace("[getMessage] trace message");
        return "redirect:";
    }
    /* Log level: FATAL, ERROR, WARN, INFO, DEBUG, TRACE */
}
