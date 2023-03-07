package com.openwaygroup.ipsgateway.enumurate;

public enum transactionManagementEnums {

    FIELD1("PBM", "Primary Bitmap",16,16),
    FIELD2("SBM", "Secondary Bitmap",16,16),
//    FIELD3("F002", "Primary Account Number",13,19),
    FIELD4("F003", "Processing Code",6,6),
    FIELD5("F004", "Transaction Amount",12,12),
    FIELD6("F005", "Settlement Amount",12,12),
    FIELD7("F006", "Cardholder Billing Amount",12,12),
    FIELD8("F007", "Transmission Date and Time ",10,10),
    FIELD9("F009", "Settlement Conversion Rate",8,8),
    FIELD10("F010", "Cardholder Billing Conversion Rate ",8,8),
    FIELD11("F011", "System Trace Audit Number",6,6),
    FIELD12("F012", "Local Transaction Time ",6,6),
    FIELD13("F013", "Local Transaction Date",4,4),
//    FIELD14("F014", "Expiration Date",4,4),
    FIELD15("F015", "Settlement Date",4,4),
    FIELD16("F018", "Merchant Category Code",4,4),
    FIELD17("F019", "Acquiring Institution Country Code",3,3),
    FIELD18("F022", "Point-of-Service Entry Mode",3,3),
//    FIELD19("F023", "Card Sequence Number",3,3),
    FIELD20("F025", "Point-of-Service Condition Code",2,2),
    FIELD21("F026", "Point-of-Service PIN Capture Code",2,2),
    FIELD22("F032", "Acquiring Instititution Code",1,11),
//    FIELD23("F035", "Track-2 Data",1,37),
    FIELD24("F036", "Track-3 Data",1,104),
    FIELD25("F037", "Retrieval reference number",12,12),
    FIELD26("F038", "Authorization identification response",6,6),
    FIELD27("F039", "Response Code",2,2),
    FIELD28("F041", "Card Acceptor Terminal Identification",8,8),
    FIELD29("F042", "Card Acceptor Identification Code",15,15),
    FIELD30("F043", "Card Acceptor Name/Location",40,40),
//    FIELD31("F045", "Track-1 Data",10,79),
    FIELD32("F048", "Additional private data",0,999),
    FIELD33("F049", "Currency Code, Transaction",3,3),
    FIELD34("F050", "Settlement Currency Code",3,3),
    FIELD35("F051", "Cardholder Billing Currency Code",3,3),
//    FIELD36("F052", "Personal Identification Number (PIN) Data",16,16),
    FIELD37("F054", "Additional amount",0,120),
    FIELD38("F055", "Chip Data",0,255),
    FIELD39("F060", "Self – defined Field",0,60),
    FIELD40("F062", "Service Code",0,10),
    FIELD41("F063", "Transaction Reference Number",0,16),
    FIELD42("F070", "Network Management Information Code",3,3),
    FIELD43("F090", "Original Data Elements",42,42),
    FIELD44("F100", "Receiving Institution Identification Code",0,11),
    FIELD45("F102", "Account Identification 1",0,28),
    FIELD46("F103", "Account Identification 2",0,28),
    FIELD47("F104", "Content transfer",0,210),
    FIELD48("F105", "New PIN Block",0,999),
    FIELD49("F120", "Beneficial Cardholder Or Account Holder Information",0,70),
    FIELD50("F128", "Message Authentication Code",16,16);
    private String fieldId;
    private String name;
    private int minLength;
    private int maxLength;

    transactionManagementEnums() {
    }

    transactionManagementEnums(String fieldId, String name, int minLength, int maxLength) {
        this.fieldId = fieldId;
        this.name = name;
        this.minLength = minLength;
        this.maxLength = maxLength;
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

    @Override
    public String toString() {
        return "transactionManagementEnums{" +
                "fieldId='" + fieldId + '\'' +
                ", name='" + name + '\'' +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                '}';
    }
}
