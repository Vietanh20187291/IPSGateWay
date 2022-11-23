package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Connection;
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
   @Autowired
    public Connection connection = Connection.getInstance();

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("connection") Connection connection, Model model) throws IOException, InterruptedException {
        model.addAttribute("connection", this.connection);
        new Thread(() -> {
            try {
                this.connection.checkConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return "communication";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("connection") Connection connection, Model model) throws UnknownHostException {
        model.addAttribute("connection", this.connection);
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
                System.out.println( connection.getVtsIp());
                System.out.println( connection.getVtsPort());
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
            this.connection.setHostIp(connection.getHostIp());
            this.connection.setHostPort(connection.getHostPort());
            System.out.println("InitializeClient Success");
            System.out.println(this.connection.getStatus());
            System.out.println("Socket status init success " + socket);
            return true;
        } catch (Exception e) {
            connection.setStatus(false);
            System.out.println("InitializeClient Fail");
            return false;
        }
    }

    public boolean initializeServer(Connection connection) throws IOException {
    try {
        System.out.println("Server is started");
        serverSocket= new ServerSocket(connection.getVtsPort());
        System.out.println("Server is waiting");
        clientDemo(connection);
        socket=serverSocket.accept();
        System.out.println("Client connected");
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String str=reader.readLine();
        System.out.println("Client data: "+str);
        return true;
        }catch (IOException e){
        System.out.println("Server is down");
        return false;
        }
    }

    public void clientDemo(Connection connection) throws UnknownHostException,
            IOException {
        Socket socket = new Socket(connection.getVtsIp(), connection.getVtsPort());
        OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream());
        os.write("Hello World!");
        os.flush();
        socket.close();
    }
    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    public String closeConnection(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        model.addAttribute("connection", this.connection);
        if(this.connection.getRole() == false){
            socket.close();
            //new function ? base method ?
            this.connection.setHostIp(null);
            this.connection.setHostPort(null);
            this.connection.setStatus(false);
            System.out.println("Close Client");
        }else {
            socket.close();
            serverSocket.close();
            this.connection.setVtsIp(null);
            this.connection.setVtsPort(null);
            this.connection.setStatus(false);
            System.out.println("Close Server");
        }
        if (this.connection.getStatus()){
            return "edit";
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
        ips gateway
        task-xxx
        */
}
