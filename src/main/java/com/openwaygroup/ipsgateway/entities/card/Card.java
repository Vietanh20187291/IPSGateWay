package com.openwaygroup.ipsgateway.entities.card;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class Card {
    //su dung map

    private List<Field> listField;

    public Card() {
    }

    public List<Field> getListField() {
        return listField;
    }

    public void setListField(List<Field> listField) {
        this.listField = listField;
    }

    @Override
    public String toString() {

        return "Card{" +
                "listField=" + listField +
                '}';
    }
}
