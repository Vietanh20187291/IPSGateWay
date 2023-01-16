package com.openwaygroup.ipsgateway.entities.card;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

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

    public Field getFieldById(String fieldId) {
        List<Field> fields = this.getListField();
        for (Field field : fields) {
            if (field.getFieldId().equals(fieldId)) {
                return field;
            }
        }
        return null;
    }
    public boolean isNull(){
        List<Field> fields = this.getListField();
        if(fields == null){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        String value = "";
        List<Field> fields = this.getListField();
        if(fields == null){
            value = "Card {null}";
        }

        else {


            value = "Card {";
            for (Field field : fields) {
                value += field.toString();
            }
            value += "}";
        }

        return value;
    }
}
