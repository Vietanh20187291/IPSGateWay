package com.openwaygroup.ipsgateway.exception;

import com.openwaygroup.ipsgateway.entities.card.Card;

public class CardException extends Exception{
//    private int errorCode;
    public CardException(String message){
        super(message);
//        this.errorCode = errorCode;
    }

//    public CardException(String message, Throwable cause, int errorCode) {
//        super(message, cause);
//        this.errorCode = errorCode;
//    }

//    public static void main(String[] args) throws CardException {
////        CardException cardException = new CardException("wrongpath");
//        Card card = null;
//        if(card == null){
//            throw new CardException("hihi",new Throwable(),100);
//        }
//
//    }


}
