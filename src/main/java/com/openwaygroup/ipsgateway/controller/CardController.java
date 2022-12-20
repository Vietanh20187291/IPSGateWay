package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.enumurate.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Component
@Controller
@RequestMapping("/cards")
//@RestController(value = "/")

public class CardController {
    public Socket socket;

    public ServerSocket serverSocket;

    @Autowired
    public static Card card;

    private static String path = "src/main/resources/inputCards";
    private final ICardService cardService;

    private static ArrayList<Card> listCard;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws Exception {
        listCard = cardService.loadCard(path);
        System.out.println("Add card to attribute");
            model.addAttribute("listCard",listCard);
        System.out.println("Render card/index.html");
        return "card/index";
    }
   // @RequestMapping(method=RequestMethod.PUT)
    @PutMapping("/{id}")
    public String update(){
        return "cards";
    }

//    @GetMapping("/{id}")
//            public String getById(@PathVariable String id, Model model) throws Exception {
//        if(listCard == null){
//            listCard = cardService.loadCard(path);
//        }
//
//        Card card = cardService.getById(listCard,id);
//        System.out.println("Getting card by id");
//        if(cardService.getById(listCard,id)== null){
//            System.out.println("Cannot find cards");
//            return "card/cardnotfound";
//        }
//        model.addAttribute("card",card);
//        System.out.println("-----------------");
//        System.out.println("render card by id");
//        return "card/getbyid";
//    }
    @GetMapping("/{id}")
    public String edit(@PathVariable String id, Model model) throws Exception {
        if(listCard == null){
            listCard = cardService.loadCard(path);
        }

        Card card = cardService.getById(listCard,id);
        System.out.println("Getting card by id");
        if(cardService.getById(listCard,id)== null){
            System.out.println("Cannot find cards");
            return "card/cardnotfound";
        }
        model.addAttribute("card",card);
        System.out.println("-----------------");
        System.out.println("render card get by id");
        return "card/getbyid";
    }
    @GetMapping("/add")
    public String add(Model model) throws Exception {
        if(listCard == null){
            listCard = cardService.loadCard(path);
        }


        model.addAttribute("listCard",listCard);
        System.out.println("model.addAttribute");
        System.out.println("-----------------");
        System.out.println("render card add");
        return "card/add";
    }


}

