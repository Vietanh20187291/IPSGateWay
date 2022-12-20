package com.openwaygroup.ipsgateway.enumurate;

public enum CardEnum {
    FIELD1("F002","PAN","Primary Account Number - Fields 2 and 35.1"),
    FIELD2("F014","EXPDATE","Expiration Date - Fields 14, 35.3 and 45.6 (yymm)"),
    FIELD3("F023","CARD SEQ NUM","Card Sequence Number - Field 23"),
    FIELD4("F035","TRACK2 DATA","Track2 Data - Field 35"),
    FIELD5("F035.04","SVCCODE","Service Code - Fields 35.4"),
    FIELD6("F035.05","API","Account Parameter Index - Fields 35.5 (Usage 2)"),
    FIELD7("F035.06","API COUNTER","API Counter-Field 35.6 (Usage 4)"),
    FIELD8("F035.07","MSD VERIFICATION VAL","MSD Verification Value - Field 35.7 (Usage 3)"),
    FIELD9("F035.08","MST VERIFICATION VAL","MST Verification Value - Field 35.8 (Usage 4)"),
    FIELD10("F045","TRACK1 DATA","Track1 Data - Field 45"),
    FIELD11("F045.01","FORMAT CODE","Track1 Format Code - Field 45.1"),

    FIELD12("F045.04","CARD HOLDER NAME","Track1 Card Holder Name - Field 45.4"),
    FIELD13("F045.06","Expiration Date","Expiration Date - Field 45.6 (yymm)"),
    FIELD14("F045.07","Service Code","Service Code - Field 45.7"),
    FIELD15("F045.08","PIN Verification Code","PIN Verification Code - Field 45.8"),
    FIELD16("F045.09","DISCODE","Track1 Discretion Code - Field 45.9"),
    FIELD17("F045.10","TRACK1 ATC","Application Transaction Counter - Field 45.10 (Usage 4)"),
    FIELD18("F045.11","TRACK1 MST VERIFICATION","MST Verification Value - Field 45.10 (Usage 4)"),
    FIELD19("F045.12.2","TRACK1 CVV","CVV - Field 45.12.2 (Usage 4)"),
    FIELD20("F052","PIN DATA","Personal Identification Number - Field 52"),
    FIELD21("F053.03","KEYM","Key Management - Field 53.03"),
    FIELD22("F053.04","KEYSETID","Key-Set Identifier - Field 53.04"),
    FIELD23("F053.05","DEVICEID","Device ID and Transaction Counter - Field 53.05"),
    FIELD24("F053.06","ALGORITHM","Algorithm - Field 53.06"),
    FIELD25("F053.07","ZONEKEYINDEX","Zone Key Index - Field 53.07"),
    FIELD26("F053.08","PINBLOCK","Pin Block Format Code - Field 53.08"),
    FIELD27("F053.09","ENCPINDATA","Encrypted PIN Data - Field 53.09"),
    FIELD28("F053.10","ENCPAN","Encrypted PAN - Field 53.10"),
    FIELD29("F053.11","ENCNAME","Encrypted Cardholder Name - Field 53.11"),
    FIELD30("F053.12","ENCTRACK1DD","Encrypted Track1 Discretionary Data - Field 53.12"),
    FIELD31("F053.13","ENCTRACK2DD","Encrypted Track2 Discretionary Data - Field 53.13"),
    FIELD32("F055.07.03","LENGTH INDICATOR","Length Indicator"),
    FIELD33("F055.07.04","CVN","Cryptogram Version Number"),
    FIELD34("F055.07.05","DKI","Derivation Key Index"),
    FIELD35("F055.07.06","CVR","Card Verification Results"),
    FIELD36("F055.07.07","DWP ID","Digital Wallet Provider ID"),
    FIELD38("F055.07.08","DERIVATION DATA","Derivation Data"),
    FIELD39("F055.07.09","IDD FORMAT","IDD format"),
    FIELD40("F055.07.10","IDD","IDD"),
    FIELD41("F055.21","FORM FACTOR","Form Factor Indicator - Field 55.21 - Tag 9F6E (Chip)"),
    FIELD42("F055.22","CUSTOMER EXCUSIVE DATA","Customer Exclusive Data - Field 55.22 - Tag 9F7C (Chip)"),
    FIELD43("F055.23.08","DEDI FILE NAME","Dedicated File Name - Tag 84 (Chip)"),
    FIELD44("F123.11","TOKEN","Token - Field 123.11"),
    FIELD45("F125.025","MICRDATA1","MICR Data Segment - Field 125.2 (POS Check)"),
    FIELD46("F125.034","MICRDATA2","MICR Data Segment - Field 125.3 (POS Check)"),
    FIELD47("F125.048","MICRDATA3","MICR Data Segment - Field 125.4 (POS Check)"),
    FIELD48("F125.057","MICRDATA4","MICR Data Segment - Field 125.5 (POS Check)"),
    FIELD49("F126.10","CV22 Auth Data","CV22 Auth Data"),
    FIELD50("F134","GENERIC EMV APPL DATA","Generic EMV Issuer Application Data - Field 134 - Tag 9F10 (Chip/GENERIC format)"),
    FIELD51("F135","ISS DISCRE DATA","Issuer Discretionary Data - Field 135 - Tag 9F10 (Chip)"),
    FIELD52("F136","ARQC","Cryptogram ARQC - Field 136 - Tag 9F26 (Chip)"),
    FIELD53("F137","APPL TXN CNTR","Application Transaction Counter - Field 137 - Tag 9F36 (Chip)"),
    FIELD54("F138","APPL INTRCHG PROF","Application Interchange Profile - Field 138 - Tag 82 (Chip)"),
    FIELD55("F152","SECONDARY PIN DATA","Secondary PIN Data - Field 152 - Tag CO (Chip)");
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
