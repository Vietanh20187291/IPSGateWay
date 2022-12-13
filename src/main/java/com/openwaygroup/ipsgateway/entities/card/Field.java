package com.openwaygroup.ipsgateway.entities.card;

public class Field {
    private String fieldId;
    private String name;
    private String description;
    private String value;


    public Field() {
    }

    public Field(String fieldId, String name, String description, String value) {
        this.fieldId = fieldId;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldId='" + fieldId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
