package com.openwaygroup.ipsgateway.enumurate.cardEnums;

import com.openwaygroup.ipsgateway.entities.card.Field;

import java.util.Arrays;
import java.util.List;

public enum RequiredFields {

    REQUIRED_FIELDS1("F002"),
    REQUIRED_FIELDS2("F014"),
    REQUIRED_FIELDS3("F035"),
    REQUIRED_FIELDS4("F053.13"),
    REQUIRED_FIELDS5("F126.10"),
    REQUIRED_FIELDS6("F053.08");
    private String field;
    RequiredFields(String field) {
        this.field= field;
    }

    public String getField() {
        return field;
    }

}
