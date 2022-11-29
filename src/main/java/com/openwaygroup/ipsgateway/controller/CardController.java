package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Card;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Component
@Controller
@RequestMapping("/card")
//@RestController(value = "/")

public class CardController {
    public Socket socket;

    public ServerSocket serverSocket;

    @Autowired
    public static Card card;
    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("card")
    public ArrayList<Card> loadCard(@RequestParam(name = "data") MultipartFile[] multipartFiles) throws IOException {
        ArrayList<Card> listCard = new ArrayList<Card>();
        for (MultipartFile multipartFile : multipartFiles) {
            InputStream inputStream = multipartFile.getInputStream();
            Card card = cardService.loadCard(inputStream);
            listCard.add(card);
        }
        return listCard;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws IOException {
        try {
            File file = new File("src/main/resources/inputCard");
            ArrayList<Card> listCard = new ArrayList<Card>();
            for(File f : file.listFiles()){
//                System.out.println("File name = " + f.getName());
                FileInputStream fileInputStream = new FileInputStream(f);
                Card newCard = cardService.loadCard(fileInputStream);
                listCard.add(newCard);
            }
            model.addAttribute("listCard",listCard);
//            FileInputStream file = new FileInputStream("src/main/resources/card1.yaml");
//            card = cardService.loadCard(file);
//            Card card1 = card;
//            card1.setPinBlock("mcmcm");
//            card.toString();
//            ArrayList<Card> listCard = new ArrayList<Card>();
//            listCard.add(card);
//            listCard.add(card1);
//            model.addAttribute("listCard",listCard);
//            model.addAttribute("card", card);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "card";
    }
//    @RequestMapping(method = RequestMethod.PUT)
//    public String update(@ModelAttribute("connection") Connection connection, Model model) throws IOException {
//        //model.addAttribute("connection", connection);
//        System.out.println("------------------------------");
//        System.out.println("Socket status before : " + connection.getStatus());
//
//      /*  if(edit.getStatus()){
//            closeConnection(edit);
//        }else{*/
//        if(!connection.getRole()){
//            //Client
//            initializeClient(connection);
//            /*System.out.println("Client");*/
//            System.out.println( connection.getHostIp());
//            System.out.println( connection.getHostPort());
//        }else{
//            //Server
//            initializeServer(connection);
//            /*   System.out.println("Server");*/
//            System.out.println( connection.getVtsIp());
//            System.out.println( connection.getVtsPort());
//        }
//        System.out.println("Socket status after : " + connection.getStatus());
//        /* }*/
//            /*while (true){
//
//            }*/
//        if (connection.getStatus()){
//            return "redirect:communication";
//        }else{
//            return "edit";
//        }
//
//    }

}
