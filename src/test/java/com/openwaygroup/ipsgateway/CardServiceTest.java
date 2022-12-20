package com.openwaygroup.ipsgateway;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.enumurate.service.impl.CardService;
import com.openwaygroup.ipsgateway.exception.CardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService; //class chính muốn test



    @Test
    void loadCardTest_sucessful() throws IOException, CardException {
        String path = "/wrongpath";
        ArrayList<Card> cards = new ArrayList<>();
        try {
            cards = cardService.loadCard("src/main/resources/inputCards");
            Assertions.assertEquals(23,cards.size());
        }catch (CardException e){

        }

//        Cannot read any file from path
    }

//    @Test
//    void loadCardTest_fail() throws IOException, CardException {
//        String path = "letiencao";
//        ArrayList<Card> cards = new ArrayList<>();
//        try {
//            cards = cardService.loadCard(path);
//        }catch (CardException e){
//            Assertions.assertEquals(100,e.getErrorCode());
//        }
//
////        Cannot read any file from path
//    }
}
