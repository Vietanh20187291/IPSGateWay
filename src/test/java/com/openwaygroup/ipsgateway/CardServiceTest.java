package com.openwaygroup.ipsgateway;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.service.impl.CardService;
import com.openwaygroup.ipsgateway.exception.CardException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.junit.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(CardService.class)
public class CardServiceTest {
    @InjectMocks
         CardService cardService; //class want to test



    @Test
    void loadCardTest_wrongPath() throws IOException, CardException {
        String path = "/wrong-path";
        ArrayList<Card> cards = new ArrayList<>();
//        try {
//            cards = cardService.loadCard("src/main/resources/Cards");
//            Assertions.assertEquals(23, cards.size());
//        } catch (CardException e) {
//
//        }

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
