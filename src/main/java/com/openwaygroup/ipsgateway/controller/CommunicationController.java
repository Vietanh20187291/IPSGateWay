package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.Connection;
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
@RequestMapping("/communication")
public class CommunicationController {
    public Socket socket;
    public ServerSocket serverSocket;
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
   @Autowired
    public Connection connection = Connection.getInstance();

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("connection") Connection connection, Model model) {
        model.addAttribute("connection", this.connection);
        return "communication";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("connection") Connection connection, Model model) throws UnknownHostException {
        model.addAttribute("connection", this.connection);
        this.connection.setVtsIp(getLocalHostAddress());
        System.out.println("------------------------------");
        System.out.println("socket information : " + socket);
        System.out.println("status in view edit: " + this.connection.getStatus());
        System.out.println("Host IP : " + this.connection.getHostIp());
        return "connection";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        System.out.println("------------------------------");
        System.out.println("Socket status before : " + connection.getStatus());
            if(!connection.getRole()){
                //Client
                initializeClient(connection);
                System.out.println(connection.getHostIp());
                System.out.println(connection.getHostPort());
            }else{
                //Server
                initializeServer(connection);
               /* clientDemo(this.connection);*/
                System.out.println(this.connection.getVtsIp());
                System.out.println(this.connection.getVtsPort());
            }
        System.out.println("Socket status after : " + this.connection.getStatus());

        if (this.connection.getStatus()){
            return "redirect:communication";
        }else{
            return "redirect:communication/edit";
        }

    }

    public boolean initializeClient(Connection connection) {
        System.out.println("------------------------------");
        try {
            socket = new Socket(connection.getHostIp(), connection.getHostPort());
            this.connection.setStatus(true);
            this.connection.setRole(false);
            this.connection.setHostIp(connection.getHostIp());
            this.connection.setHostPort(connection.getHostPort());
            System.out.println("Initialize Client-Side Success");
            System.out.println(this.connection.getStatus());
            log.info("Connect To Server Successfully");
            log.info(String.valueOf(socket));
            //Thread check conection status
            new Thread(() -> {
                try {
                    this.connection.checkConnection();
                } catch (IOException e) {
                   /* throw new RuntimeException(e);*/
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            return true;
        } catch (Exception e) {
            connection.setStatus(false);
            System.out.println("Initialize Client-Side Fail");
            log.info("Connect To Server Failed");
            return false;
        }
    }

    public void handlerConnection() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
               log.info("Client accepted: " + socket);
                this.connection.setClientNumber(this.connection.getClientNumber()+1);
                new Thread(() -> {
                    try {
                        String sentence_from_client;
                        String sentence_to_client;
                        InputStream is = socket.getInputStream();
                        OutputStream os = socket.getOutputStream();
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(is));
                        DataOutputStream outToClient = new DataOutputStream(os);
                        while (!serverSocket.isClosed()) {
                            // Check for client disconnection
                            if(inFromClient.read() == -1){
                                log.info("Client disconnected: "+ socket +" closing...");
                                socket.close();
                                this.connection.setClientNumber(this.connection.getClientNumber()-1);
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
                System.err.println(" Connection Error: " + e);
            }
        }
    }

    public boolean initializeServer(Connection connection) throws IOException {
        try {
            System.out.println("Binding to port " + connection.getVtsPort() + ", please wait  ...");
            serverSocket = new ServerSocket(connection.getVtsPort());
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");
            //
            this.connection.setStatus(true);
            this.connection.setRole(true);
            this.connection.setVtsIp(getLocalHostAddress());
            this.connection.setVtsPort(connection.getVtsPort());
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
            e1.printStackTrace();
            return false;
        }
    }
    @RequestMapping(value = "/clientDemo1", method = RequestMethod.POST)
    public String clientDemo1(Connection connection) throws UnknownHostException,
            IOException {
        String sentence_to_server;
        String sentence_from_server;
        Socket socket = new Socket(this.connection.getVtsIp(), this.connection.getVtsPort());
        sentence_to_server = "Hello World 1!";
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(sentence_to_server + '\n');
        sentence_from_server = inFromServer.readLine();
        System.out.println("FROM SERVER: " + sentence_from_server);
        return "redirect:edit";
    }
    @RequestMapping(value = "/clientDemo2", method = RequestMethod.POST)
    public String clientDemo2(Connection connection) throws UnknownHostException,
            IOException {
        String sentence_to_server;
        String sentence_from_server;
        Socket socket = new Socket(this.connection.getVtsIp(), this.connection.getVtsPort());
        sentence_to_server = "Hello World 2!";
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer.writeBytes(sentence_to_server + '\n');
        sentence_from_server = inFromServer.readLine();
        System.out.println("FROM SERVER: " + sentence_from_server);
        return "redirect:edit";
    }
    @RequestMapping(value = "/closeClientDemo", method = RequestMethod.POST)
    public String closeConnection(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        model.addAttribute("connection", this.connection);
            socket.close();
            System.out.println("Close Client Demo");
        if (this.connection.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }
    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeCLientDemo(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        model.addAttribute("connection", this.connection);
        if(this.connection.getRole() == false){
            socket.close();
            //new function ? base method ?
            this.connection.setHostIp(null);
            this.connection.setHostPort(null);
            this.connection.setStatus(false);
            System.out.println("Close Client");
            log.info("Connection To The Server Has Been Closed");
        }else {
            /*socket.close();*/
            serverSocket.close();
            this.connection.setVtsIp(null);
            this.connection.setVtsPort(null);
            this.connection.setStatus(false);
            System.out.println("Close Server");
            log.info("Server Has Been Shut Down");
        }
        if (this.connection.getStatus()){
            return "redirect:edit";
        }else{
            return "redirect:";
        }
    }

    @RequestMapping(value = "/editIpFail", method = RequestMethod.PUT)
    public String editIpFail(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        model.addAttribute("connection", this.connection);
        this.connection.setHostIp("55.12.34.53");
        return "redirect:edit";
    }
    @RequestMapping(value = "/editIpSuccess", method = RequestMethod.PUT)
    public String editIpSuccess(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        model.addAttribute("connection", this.connection);
        this.connection.setHostIp("10.145.48.96");
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
