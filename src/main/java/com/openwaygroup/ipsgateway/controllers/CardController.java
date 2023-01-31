package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.IpsGatewayApplication;
import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.service.ICardService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;

@Component
@Controller
@RequestMapping("/cards")
//@RestController(value = "/")

public class CardController {
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired

    private static String path = "src/main/resources/inputCards";
    private static ArrayList<Card> listCard;
    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws Exception {
        listCard = cardService.loadCard(path);
        if(listCard.isEmpty()){
            model.addAttribute("message","List Card is null");
            model.addAttribute("messageType","error");
            return "card/index";
        }
        log.info("Add card to attribute");
        model.addAttribute("listCard", listCard);
        log.info("Render card/index.html");
        return "card/index";
    }



    @GetMapping("/{id}")
    public String edit(@PathVariable String id, Model model) throws Exception {
        if (listCard == null) {
            listCard = cardService.loadCard(path);
        }

        Card card = cardService.getById(listCard, id);
        log.info("Getting card by id");
        if (cardService.getById(listCard, id) == null) {
            log.info("Cannot found cards");
            return "card/cardnotfound";
        }

        model.addAttribute("card", card);
        log.info("-----------------");
        log.info("render card get by id");
        return "card/getbyid";
    }

    @GetMapping("/add")
    public String add(Model model) throws Exception {
            if (listCard == null) {
                listCard = cardService.loadCard(path);
            }
                Card card = cardService.getSampleCard();
                model.addAttribute("card", card);
        log.info("-----------------");
        log.info("render card add");
        return "card/add";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Card card,
                       BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {
        String editCard = cardService.editCard(card);
        log.info("edit card: "+editCard);
        if(!editCard.equals("success")) {
            model.addAttribute("message",editCard);
            model.addAttribute("messageType","error");
            return "card/getbyid";
        }else{
            redirectAttributes.addFlashAttribute("message", " Card Edited Successfully ("+card.getFieldById("F002").value+")");
            redirectAttributes.addFlashAttribute("messageType","success");

        }
        return "redirect:/cards";
    }
    @PostMapping("/add")
    public String add(Card card,
                       BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {


        String addCard = cardService.addCard(card);
        log.info("add card: "+addCard);
        if(!addCard.equals("success")) {
            model.addAttribute("message",addCard);
            model.addAttribute("messageType","error");
            model.addAttribute("invalidData",true);
           return "card/add";
        }else{
            redirectAttributes.addFlashAttribute("message", " Card Added Successfully ("+card.getFieldById("F002").value+")");
            redirectAttributes.addFlashAttribute("messageType","success");

        }
        return "redirect:/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) throws Exception {
        boolean deleteCard = cardService.deleteCard(id);
        log.info("delete card: "+deleteCard);
        if(deleteCard) {
            redirectAttributes.addFlashAttribute("message", " Card Deleted Successfully ("+id+")");
            redirectAttributes.addFlashAttribute("messageType","success");
        }else{
            redirectAttributes.addFlashAttribute("message", " Failed to add card!");
            redirectAttributes.addFlashAttribute("messageType","error");

        }
        return "redirect:/cards";

    }


}

