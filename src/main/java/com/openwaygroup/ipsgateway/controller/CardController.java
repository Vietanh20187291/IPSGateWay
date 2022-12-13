package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.service.ICardService;
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
    private final ICardService cardService;

    private static ArrayList<Card> listCard;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws IOException {
        listCard = cardService.loadCard("src/main/resources/inputCard");
        System.out.println("Add card to attribute");
            model.addAttribute("listCard",listCard);
        System.out.println("Render card/index.html");
        return "card/index";
    }

    @GetMapping("/{id}")
            public String getById(@PathVariable String id, Model model) throws IOException {
        if(listCard == null){
            listCard = cardService.loadCard("src/main/resources/inputCard");
        }

        Card card = cardService.getById(listCard,id);
        if(cardService.getById(listCard,id)== null){
            return "card/cardnotfound";
        }
        System.out.println("hay"+card.toString());
        model.addAttribute("card",card);
        return "card/getbyid";
    }


}

