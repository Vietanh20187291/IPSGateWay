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
    public static Connection connection;

   /* private static Connection instance = new Connection();*/

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("connection") Connection connection, Model model){

        model.addAttribute("connection", connection);
        return "communication";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute("connection") Connection connection, Model model) throws UnknownHostException {
        /*if(edit.){
            model.addAttribute("edit", new Connection(getLocalHostAddress(),9999));
        }*/

        System.out.println("------------------------------");
        System.out.println("socket information : " + socket);
        System.out.println("edit status : " + connection.getStatus());
        System.out.println("Host IP : " + connection.getHostIp());
        return "connection";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
        //model.addAttribute("connection", connection);
        System.out.println("------------------------------");
        System.out.println("Socket status before : " + connection.getStatus());

      /*  if(edit.getStatus()){
            closeConnection(edit);
        }else{*/
            if(!connection.getRole()){
                //Client
                initializeClient(connection);
                /*System.out.println("Client");*/
                System.out.println( connection.getHostIp());
                System.out.println( connection.getHostPort());
            }else{
                //Server
                initializeServer(connection);
             /*   System.out.println("Server");*/
                System.out.println( connection.getVtsIp());
                System.out.println( connection.getVtsPort());
            }
        System.out.println("Socket status after : " + connection.getStatus());
       /* }*/
            /*while (true){

            }*/
        if (connection.getStatus()){
            return "redirect:communication";
        }else{
            return "edit";
        }

    }

    public boolean initializeClient(Connection connection) {
        System.out.println("------------------------------");
        try {
            socket = new Socket(connection.getHostIp(), connection.getHostPort());
            connection.setStatus(true);
            /*socket.close();*/
            System.out.println("InitializeClient Success ");
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
    /*    socket.close();
        serverSocket.close();*/
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
    @RequestMapping(value = "/connection/close", method = RequestMethod.POST)
    public void closeConnection(Connection connection) throws IOException {
        if(connection.getRole() == false){
            socket.close();
            connection.setStatus(false);
            System.out.println("Close Client");
        }else {
            socket.close();
            serverSocket.close();
            connection.setStatus(false);
            System.out.println("Close Server");
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
