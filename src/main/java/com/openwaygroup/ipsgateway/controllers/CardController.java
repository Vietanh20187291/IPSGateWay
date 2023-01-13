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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
@Controller
@RequestMapping("/cards")
//@RestController(value = "/")

public class CardController {
    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);
    @Autowired
    public static Card card;

    private static String path = "src/main/resources/inputCards";
    private static ArrayList<Card> listCard;
    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("card") Card card, Model model) throws Exception {
        listCard = cardService.loadCard(path);
        log.info("Add card to attribute");
        model.addAttribute("listCard", listCard);
        log.info("Render card/index.html");
        return "card/index";
    }

    // @RequestMapping(method=RequestMethod.PUT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Card edit(HttpServletRequest hsr) throws Exception {
//        List<Card> card = hsr.getP("cardFields");
        String a = hsr.getParameter("cardFields");

//        Card cardEditted = cardFields.;
//        for (int i = 1; i < cardFields.length; i++) {
//            log.info(cardFields[i].getFieldId());
//            log.info(cardFields[i].getValue());
//
//        }
//        cardFields.get(0);
//        for(int i = 0; i<cardFields.toArray().length;i++){
//            cardEditted.getByFieldId((String) cardFields.get(0).toString());
//        }
//        return cardService.editCard(card);
        return null;
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
//        Card card = listCard.get(0);
//        List<Field> listFields= card.getListField();
//        for(Field field : listFields){
//            field.setValue("");
//        }
//            //clear data

        model.addAttribute("card", listCard.get(0));
        log.info("-----------------");
        log.info("render card add");
        return "card/add";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Card card,
                       BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {
//        log.info(card.getFieldById("F002").getValue());
//        log.info(card.getFieldById("F014").getValue());
//        if (result.hasErrors()) {
//            card.setId(id);
//            return "card/getbyid";
//        }
        boolean editCard = cardService.editCard(card);
        log.info("edit card: "+editCard);
        if(editCard) {
            redirectAttributes.addFlashAttribute("message", " Card Edited Successfully!");
        }else{
            redirectAttributes.addFlashAttribute("message", " Failed to add card!");

        }
        return "redirect:/cards";
    }
    @PostMapping("/add")
    public String add(Card card,
                       BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {
 //       log.info(card.toString());
//        log.info(card.getFieldById("F002").getValue());
//        log.info(card.getFieldById("F014").getValue());
//        if (result.hasErrors()) {
//            card.setId(id);
//            return "card/getbyid";
//        }

        boolean addCard = cardService.addCard(card);
        log.info("add card: "+addCard);
        if(addCard) {
            redirectAttributes.addFlashAttribute("message", " Card Added Successfully!");
            return "card/add";
        }else{
            redirectAttributes.addFlashAttribute("message", " Failed to add card!");

        }
        return "redirect:/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) throws Exception {
        boolean deleteCard = cardService.deleteCard(id);
        log.info("delete card: "+deleteCard);
        if(deleteCard) {
            redirectAttributes.addFlashAttribute("message", " Card Deleted Successfully!");
        }else{
            redirectAttributes.addFlashAttribute("message", " Failed to add card!");

        }
        return "redirect:/cards";

    }


}

