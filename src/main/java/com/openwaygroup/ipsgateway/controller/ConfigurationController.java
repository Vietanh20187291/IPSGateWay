package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Configuration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;

@Component
@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
    public Socket socket;
    public ServerSocket serverSocket;
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
   @Autowired
    public Configuration configuration = Configuration.getInstance();

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("configuration") Configuration configuration, Model model) {
        model.addAttribute("configuration", this.configuration);
        return "configuration";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("configuration") Configuration configuration, Model model) throws UnknownHostException {
        model.addAttribute("configuration", this.configuration);
        this.configuration.setVtsIp(getLocalHostAddress());
        System.out.println("------------------------------");
        return "configurationEdit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public String update(@ModelAttribute("configuration") Configuration configuration) throws IOException {
        System.out.println("------------------------------");
            if(!configuration.getRole()){
                //Client
                initializeClient(configuration);
                System.out.println(this.configuration.getHostIp());
                System.out.println(this.configuration.getHostPort());
            }else{
                //Server
                initializeServer(configuration);
                System.out.println(this.configuration.getVtsIp());
                System.out.println(this.configuration.getVtsPort());
            }
            //Change Log Level
        String loggerLevel = configuration.getLogLevel();
        setLogLevel(loggerLevel);

        if (this.configuration.getStatus()){
            return "redirect:/configuration";
        }else{
            return "redirect:/configuration/edit";
        }

    }

    public boolean initializeClient(Configuration configuration) {
        System.out.println("------------------------------");
        try {
            socket = new Socket(configuration.getHostIp(), configuration.getHostPort());
            this.configuration.setStatus(true);
            this.configuration.setRole(false);
            this.configuration.setHostIp(configuration.getHostIp());
            this.configuration.setHostPort(configuration.getHostPort());
            log.info("Connect To Server Successfully");
            log.info(String.valueOf(socket));
            //Thread check connection status
            new Thread(() -> {
                try {
                    this.configuration.checkConnection();
                } catch (IOException e) {
                   /* throw new RuntimeException(e);*/
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            return true;
        } catch (Exception e) {
            configuration.setStatus(false);
            log.info("Connect To Server Failed");
            return false;
        }
    }

    public void handlerConnection() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                log.info("Client Accepted: " + socket);
                this.configuration.setClientNumber(this.configuration.getClientNumber()+1);
                new Thread(() -> {
                    try {
                        String sentence_from_client;
                        String sentence_to_client;
                        InputStream is = socket.getInputStream();
                        OutputStream os = socket.getOutputStream();
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(is));
                        DataOutputStream outToClient = new DataOutputStream(os);
                        while (!serverSocket.isClosed()) {
                            // Check for client disconnected
                            if(inFromClient.read() == -1){
                                this.configuration.setClientNumber(this.configuration.getClientNumber()-1);
                                log.info("Client Disconnected: "+ socket +" closing...");
                                socket.close();
                                break;
                            }
                            /*sentence_from_client = inFromClient.readLine();
                            sentence_to_client = sentence_from_client +" (Server accepted!)" + '\n';
                            outToClient.writeBytes(sentence_to_client);*/
                        }
                    } catch (IOException e) {
                        /*  throw new RuntimeException(e);*/
                    }
                }).start();
            } catch (IOException e) {
                log.info("Configuration Error: " + e); //log.error ???
            }
        }
    }

    public boolean initializeServer(Configuration configuration) throws IOException {
        try {
            log.info("Binding To Port " + configuration.getVtsPort() + ", Please Wait  ...");
            serverSocket = new ServerSocket(configuration.getVtsPort());
            log.info("Server Started: " + serverSocket);
            log.info("Waiting For A Client ...");
            //
            this.configuration.setStatus(true);
            this.configuration.setRole(true);
            this.configuration.setVtsIp(getLocalHostAddress());
            this.configuration.setVtsPort(configuration.getVtsPort());
            //
            new Thread(() -> {
                try {
                    handlerConnection();
                } catch (IOException e) {
                    /*  throw new RuntimeException(e);*/
                }
            }).start();
            return true;
        } catch (IOException e1) {
            configuration.setStatus(false);
            log.info("Binding Failed");
            e1.printStackTrace();
            return false;
        }
    }

    public void setLogLevel(String loggerLevel) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
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
        log.trace("Logger Level Set As " + loggerLevel);
    }

    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeConnection() throws IOException {
        if(this.configuration.getRole() == false){
            socket.close();
            //new function ? base method ?
            this.configuration.setHostIp(null);
            this.configuration.setHostPort(null);
            this.configuration.setStatus(false);
            log.info("Configuration To The Server Has Been Closed");
        }else {
            serverSocket.close();
            this.configuration.setVtsIp(null);
            this.configuration.setVtsPort(null);
            this.configuration.setStatus(false);
            this.configuration.setRole(false);
            this.configuration.setClientNumber(0);
            log.info("Server Has Been Shut Down");
        }
        if (this.configuration.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }

    public String getLocalHostAddress() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String str = ia.getHostAddress();
        return str;
    }

        /* HOST TCP/IP
        host = "10.145.48.96";
        port = 7777;
        */
}
