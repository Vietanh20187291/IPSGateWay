package com.openwaygroup.ipsgateway.enumurate;

public enum CardEnum {
    FIELD1("F002","PAN","Primary Account Number - Fields 2 and 35.1"),
    FIELD2("F014","PAN1","Primary Account Number - Fields 2 and 35.122"),
    FIELD3("F023","PAN1","Primary Account Number - Fields 2 and 35.133");
    private String fieldId;
    private String name;
    private String description;
//    private String value;


    CardEnum(String fieldId,String name,String description){
        this.fieldId = fieldId;
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "CardEnum{" +
                "fieldId='" + fieldId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
