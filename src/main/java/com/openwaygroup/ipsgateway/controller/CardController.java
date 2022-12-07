package com.openwaygroup.ipsgateway.controller;

import com.openwaygroup.ipsgateway.entities.Card;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private static ArrayList<Card> listCard;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("card")
    public ArrayList<Card> loadCard(@RequestParam(name = "data") String path) throws IOException {
        ArrayList<Card> list = new ArrayList<Card>();
        File file = new File(path);

        for(File f : file.listFiles()) {
            FileInputStream fileInputStream = new FileInputStream(f);
            Card newCard = cardService.loadCard(fileInputStream);
            list.add(newCard);
        }
        return list;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws IOException {
             listCard = loadCard("src/main/resources/inputCard");
            model.addAttribute("listCard",listCard);
        return "card/index";
    }

    @GetMapping("/getbyid/{id}")
            public String getById(@ModelAttribute("card") Card card, Model model) throws IOException {
        if(listCard == null){
            listCard = loadCard("src/main/resources/inputCard");
        }
        model.addAttribute("listCard",listCard);
        return "card/getbyid";
    }


}

