package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import com.openwaygroup.ipsgateway.services.YamlPropertySourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/configuration")
@Controller
@Component
public class ConfigurationController {
    @Autowired
    public Configuration configuration;
    Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Lazy
    @Autowired
    ConnectionController connectionController;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("configuration") Configuration config, Model model) throws UnknownHostException {
        model.addAttribute("configuration", configuration);
        configuration.getConnection().setIpsIp(getLocalHostAddress());
        return "configuration";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("configuration") Configuration config, Model model) throws UnknownHostException {
        model.addAttribute("configuration", configuration);
        configuration.getConnection().setIpsIp(getLocalHostAddress());
        return "configurationEdit";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@ModelAttribute("configuration") Configuration config) throws IOException {
        YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
        configuration.getConnection().setHostIp(config.getConnection().getHostIp());
        configuration.getConnection().setHostPort(config.getConnection().getHostPort());
        configuration.getConnection().setIpsIp(getLocalHostAddress());
        configuration.getConnection().setIpsPort(config.getConnection().getIpsPort());
        configuration.getConnection().setRole(config.getConnection().isRole());
        configuration.getLogger().setTimeout(config.getLogger().getTimeout());
        //Change Log Level
        String loggerLevel = config.getLogger().getLogLevel();
        setLogLevel(loggerLevel);
        //Save Config to YAML File
        saveParamChanges();
        return "redirect:/configuration";
    }
    public void setLogLevel(String loggerLevel) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
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
        configuration.getLogger().setLogLevel(loggerLevel);
        log.info("Logger Level Set As " + loggerLevel);
    }
    //Save configuration
    public void saveParamChanges() {
        try {
            //YAML Format Options
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            // Create and set properties into YAML object
            Yaml yaml = new Yaml(options);
            Map<String, LinkedHashMap> dataMap = new LinkedHashMap<>();
            LinkedHashMap<String, Object> connection = new LinkedHashMap<>();
            LinkedHashMap<String, Object> log = new LinkedHashMap<>();
            dataMap.put("connection", connection);
            dataMap.put("log", log);
            connection.put("hostIp", configuration.getConnection().getHostIp());
            connection.put("hostPort", configuration.getConnection().getHostPort());
            connection.put("ipsPort", configuration.getConnection().getIpsPort());
            connection.put("role", configuration.getConnection().isRole());
            log.put("timeout", configuration.getLogger().getTimeout());
            log.put("logLevel", configuration.getLogger().getLogLevel());
            Writer writer = new FileWriter("src/main/resources/configuration.yml");
            yaml.dump(dataMap,writer);
            writer.close();
            this.log.info("Configuration Param Saved To File");
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public String getLocalHostAddress() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String str = ia.getHostAddress();
        return str;
    }
}
