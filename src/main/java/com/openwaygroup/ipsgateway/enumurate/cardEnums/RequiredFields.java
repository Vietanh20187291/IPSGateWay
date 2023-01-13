package com.openwaygroup.ipsgateway.enumurate.cardEnums;

import com.openwaygroup.ipsgateway.entities.card.Field;

import java.util.Arrays;
import java.util.List;

public enum RequiredFields {

    REQUIRED_FIELDS1("F002"),
    REQUIRED_FIELDS2("F014"),
    REQUIRED_FIELDS3("F035"),
    REQUIRED_FIELDS4("F045"),
    REQUIRED_FIELDS5("F052"),
    REQUIRED_FIELDS6("CVV2"),
    REQUIRED_FIELDS7("F053.08");
    private String field;
    RequiredFields(String field) {
        this.field= field;
    }

    public String getField() {
        return field;
    }

}
