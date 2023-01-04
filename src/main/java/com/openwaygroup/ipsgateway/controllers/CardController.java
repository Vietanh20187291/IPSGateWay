package com.openwaygroup.ipsgateway.controllers;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.enumurate.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
@Controller
@RequestMapping("/cards")
//@RestController(value = "/")

public class CardController {

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
        System.out.println("Add card to attribute");
        model.addAttribute("listCard", listCard);
        System.out.println("Render card/index.html");
        return "card/index";
    }

    // @RequestMapping(method=RequestMethod.PUT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Card edit(HttpServletRequest hsr) throws Exception {
//        List<Card> card = hsr.getP("cardFields");
        String a = hsr.getParameter("cardFields");
        System.out.println(a + "hufhufuuf");

//        Card cardEditted = cardFields.;
//        for (int i = 1; i < cardFields.length; i++) {
//            System.out.println(cardFields[i].getFieldId());
//            System.out.println(cardFields[i].getValue());
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
        System.out.println("Getting card by id");
        if (cardService.getById(listCard, id) == null) {
            System.out.println("Cannot found cards");
            return "card/cardnotfound";
        }

        model.addAttribute("card", card);
        System.out.println("-----------------");
        System.out.println("render card get by id");
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

        model.addAttribute("Card", listCard.get(0));
        System.out.println("model.addAttribute");
        System.out.println("-----------------");
        System.out.println("render card add");
        return "card/add";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Card card,
                       BindingResult result, Model model) throws Exception {
//        System.out.println(card.getFieldById("F002").getValue());
//        System.out.println(card.getFieldById("F014").getValue());
//        if (result.hasErrors()) {
//            card.setId(id);
//            return "card/getbyid";
//        }
        boolean editCard = cardService.editCard(card);
        System.out.println(editCard);
        return "redirect:/cards";
    }
    @PostMapping("/add")
    public String add(Card card,
                       BindingResult result, Model model) throws Exception {
 //       System.out.println(card.toString());
//        System.out.println(card.getFieldById("F002").getValue());
//        System.out.println(card.getFieldById("F014").getValue());
//        if (result.hasErrors()) {
//            card.setId(id);
//            return "card/getbyid";
//        }
        boolean addCard = cardService.addCard(card);
        System.out.println(addCard);
        return "redirect:/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) throws Exception {
        System.out.println("here");
        boolean deleteCard = cardService.deleteCard(id);
        System.out.println(deleteCard);
        return "redirect:/cards";
    }


}

