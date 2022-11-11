package com.openwaygroup.ipsgateway.controller;


import com.openwaygroup.ipsgateway.entities.Protocol;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;



@Controller
public class CommunicationController {
    @GetMapping("/communication")
    public String communication(@ModelAttribute("protocol") Protocol protocol, Model model){
        model.addAttribute("protocol", new Protocol());
        return "communication";
    }
    @PostMapping("/communication")
    public String connect(@ModelAttribute("protocol") Protocol protocol, Model model) throws IOException {
        model.addAttribute("protocol", protocol);
        if(!protocol.getRole()){
            //Client
            initializeClient(protocol);
        }else{
            //Server
            initializeServer(protocol);
        System.out.println("Server");
        }
        System.out.println( protocol.getHostIp());
        System.out.println( protocol.getHostPort());

        return "communication";
    }

    public boolean initializeClient(Protocol protocol) throws IOException {
        try {
            Socket socket = new Socket(protocol.getHostIp(), protocol.getHostPort());
            socket.close();
            System.out.println(true);
            return true;
        } catch (IOException e) {
            System.out.println(false);
            return false;
        }
    }

    public boolean initializeServer(Protocol protocol) throws IOException {
     /*   try {
            Socket socket = new Socket(protocol.getVtsIp(), protocol.getVtsPort());
            socket.close();
            System.out.println(true);
            return true;
        } catch (IOException e) {
            System.out.println(false);
            return false;
        }*/
        return true;
    }
        /* HOST TCP/IP
        host = "10.145.48.96";
        port = 7777;
        */
}
