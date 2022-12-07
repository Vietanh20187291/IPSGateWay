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
    public String update(@ModelAttribute("configuration") Configuration configuration, Model model) throws IOException {
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
                            // Check for client disconfiguration
                            if(inFromClient.read() == -1){
                                log.info("Client Disconnected: "+ socket +" closing...");
                                socket.close();
                                this.configuration.setClientNumber(this.configuration.getClientNumber()-1);
                                break;
                            }
                            sentence_from_client = inFromClient.readLine();
                            sentence_to_client = sentence_from_client +" (Server accepted!)" + '\n';
                            outToClient.writeBytes(sentence_to_client);
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
//Log Level Test
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

    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeCLientDemo(@ModelAttribute("configuration") Configuration configuration, Model model) throws IOException {
        model.addAttribute("configuration", this.configuration);
        if(this.configuration.getRole() == false){
            socket.close();
            //new function ? base method ?
            this.configuration.setHostIp(null);
            this.configuration.setHostPort(null);
            this.configuration.setStatus(false);
            System.out.println("Close Client");
            log.info("Configuration To The Server Has Been Closed");
        }else {
            serverSocket.close();
            this.configuration.setVtsIp(null);
            this.configuration.setVtsPort(null);
            this.configuration.setStatus(false);
            this.configuration.setClientNumber(0);
            log.info("Server Has Been Shut Down");
        }
        if (this.configuration.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }
    @RequestMapping(value = "/clientDemo1", method = RequestMethod.POST)
    public String clientDemo1(Configuration configuration) throws UnknownHostException,
            IOException {
        String sentence_to_server;
        String sentence_from_server;
        Socket socket = new Socket(this.configuration.getVtsIp(), this.configuration.getVtsPort());
        sentence_to_server = "Hello World 1!";
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(sentence_to_server + '\n');
        sentence_from_server = inFromServer.readLine();
        System.out.println("FROM SERVER: " + sentence_from_server);
        return "redirect:edit";
    }
    @RequestMapping(value = "/clientDemo2", method = RequestMethod.POST)
    public String clientDemo2(Configuration configuration) throws UnknownHostException,
            IOException {
        String sentence_to_server;
        String sentence_from_server;
        Socket socket = new Socket(this.configuration.getVtsIp(), this.configuration.getVtsPort());
        sentence_to_server = "Hello World 2!";
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(sentence_to_server + '\n');
        sentence_from_server = inFromServer.readLine();
        System.out.println("FROM SERVER: " + sentence_from_server);
        return "redirect:edit";
    }
    @RequestMapping(value = "/closeClientDemo", method = RequestMethod.POST)
    public String closeConnection(@ModelAttribute("configuration") Configuration configuration, Model model) throws IOException {
        model.addAttribute("configuration", this.configuration);
            socket.close();
            System.out.println("Close Client Demo");
        if (this.configuration.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }

    @RequestMapping(value = "/editIpFail", method = RequestMethod.PUT)
    public String editIpFail(@ModelAttribute("configuration") Configuration configuration, Model model) throws IOException {
        model.addAttribute("configuration", this.configuration);
        this.configuration.setHostIp("55.12.34.53");
        return "redirect:edit";
    }
    @RequestMapping(value = "/editIpSuccess", method = RequestMethod.PUT)
    public String editIpSuccess(@ModelAttribute("configuration") Configuration configuration, Model model) throws IOException {
        model.addAttribute("configuration", this.configuration);
        this.configuration.setHostIp("10.145.48.96");
        return "redirect:edit";
    }

    public String getLocalHostAddress() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String str = ia.getHostAddress();
        return str;
    }

        /* HOST TCP/IP
        host = "10.145.48.96";
        port = 7777;
        ips gateway
        task-xxx
        */
}
