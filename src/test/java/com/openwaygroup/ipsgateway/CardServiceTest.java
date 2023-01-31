package com.openwaygroup.ipsgateway;

import com.openwaygroup.ipsgateway.entities.card.Card;
import com.openwaygroup.ipsgateway.service.impl.CardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    CardService cardService; // class want to test

    org.slf4j.Logger log = LoggerFactory.getLogger(IpsGatewayApplication.class);

    @Test
    void checkInputValue_invalidLengthF002() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F002").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for F002 must has between 13 and 19 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_invalidLengthF014() throws IOException{
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F014").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for F014 must has 4 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_invalidLengthF035() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F035").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for F035 must has between 22 and 100 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_invalidLengthF045() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F045").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for F045 must has between 26 and 100 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_invalidLengthF052() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F052").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for F052 must has between 4 and 12 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_invalidLengthCVV2() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("CVV2").value = "1";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "The data for CVV2 must has 3 characters";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));

    }
    @Test
    void checkInputValue_insertNullToF002() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F002").value = "";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "F002 cannot be null";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }
    @Test
    void checkInputValue_insertNullToF014() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F014").value = "";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "F014 cannot be null";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }
    @Test
    void checkInputValue_insertNullToF035() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F035").value = "";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "F035 cannot be null";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }
    @Test
    void checkInputValue_insertNullToF045() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F045").value = "";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "F045 cannot be null";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }
    @Test
    void checkInputValue_insertNullToCVV2() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("CVV2").value = "";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "CVV2 cannot be null";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_wrongFormatF035() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F002").value = "123456789012345";
        cardFailed.getFieldById("F014").value = "2707";
        cardFailed.getFieldById("F035").value = "12345678901234582707601100002790000012";
        cardFailed.getFieldById("F045").value = "B123456789012345^TUYEN/NGUYEN KIM^2707601100000000000000279000000";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "Format for F035 is wrong";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_wrongFormatF034() throws IOException {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardFailed.getFieldById("F002").value = "123456789012345";
        cardFailed.getFieldById("F014").value = "2707";
        cardFailed.getFieldById("F035").value = "123456789012345=2707601100002790000012";
        cardFailed.getFieldById("F045").value = "B123456789012345TUYEN/NGUYEN KIM^2707601100000000000000279000000";
        String messageActual = cardService.checkInputValue(cardFailed);
        String messageExpected = "Format for F045 is wrong";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void addCard_existedPanNumber() throws Exception {
        Card cardFailed = cardService.loadCard("src/main/resources/inputCards").get(0);
        String messageActual = cardService.addCard(cardFailed);
        String messageExpected = "Pan Number has been used (F002)";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void checkInputValue_success() throws IOException {
        Card cardSuccess = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardSuccess.getFieldById("F002").value = "123456789012345";
        cardSuccess.getFieldById("F014").value = "2707";
        cardSuccess.getFieldById("F035").value = "123456789012345=2707601100002790000012";
        cardSuccess.getFieldById("F045").value = "B123456789012345^TUYEN/NGUYEN KIM^2707601100000000000000279000000";
        String messageActual = cardService.checkInputValue(cardSuccess);
        String messageExpected = "success";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void addCard_success() throws Exception {
        Card cardSuccess = cardService.loadCard("src/main/resources/inputCards").get(0);
        int randomNumber = (int) (Math.random() * 100001);
        String randomPanNumber = "1234567890" + randomNumber;
        // Generate Random Pan Number
        cardSuccess.getFieldById("F002").value = randomPanNumber;
        cardSuccess.getFieldById("F014").value = "2707";
        cardSuccess.getFieldById("F035").value = randomPanNumber + "=2707601100002790000012";
        cardSuccess.getFieldById("F045").value = "B" + randomPanNumber
                + "^TUYEN/NGUYEN KIM^2707601100000000000000279000000";
        String messageActual = cardService.addCard(cardSuccess);
        String messageExpected = "success";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void editCard_panNumberNotExisted() throws Exception {
        Card cardFail = cardService.loadCard("src/main/resources/inputCards").get(0);
        int randomNumber = (int) (Math.random() * 100001);
        String randomPanNumber = "1234567890" + randomNumber;
        cardFail.getFieldById("F002").value = randomPanNumber;
        cardFail.getFieldById("F014").value = "2707";
        cardFail.getFieldById("F035").value = randomPanNumber + "=2707601100002790000012";
        cardFail.getFieldById("F045").value = "B" + randomPanNumber
                + "^TUYEN/NGUYEN KIM^2707601100000000000000279000000";
        String messageActual = cardService.editCard(cardFail);
        System.out.println(messageActual);
        String messageExpected = "Pan Number to edit is not exist";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

    @Test
    void editCard_success() throws Exception {
        Card cardSuccess = cardService.loadCard("src/main/resources/inputCards").get(0);
        cardSuccess.getFieldById("F014").value = "2707";
        String messageActual = cardService.editCard(cardSuccess);
        String messageExpected = "success";
        log.info("messageExpected: " + messageExpected);
        log.info("messageActual: " + messageActual);
        Assertions.assertTrue(messageExpected.equals(messageActual));
    }

}
