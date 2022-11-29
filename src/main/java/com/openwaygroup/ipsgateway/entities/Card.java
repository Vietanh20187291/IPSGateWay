package com.openwaygroup.ipsgateway.entities;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
//@Entity
public class Card {
    private String cardNumber;
    private String expiryDate;
    private String track1;
    private String track2;
    private String chipData;

    private String pinBlock;
    private String track1Structure;
    private String track2Structure;

    public Card() {
    }

    public Card(String cardNumber, String expiryDate, String track1, String track2, String chipData, String pinBlock, String track1Structure, String track2Structure) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.track1 = track1;
        this.track2 = track2;
        this.chipData = chipData;
        this.pinBlock = pinBlock;
        this.track1Structure = track1Structure;
        this.track2Structure = track2Structure;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTrack1() {
        return track1;
    }

    public void setTrack1(String track1) {
        this.track1 = track1;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getChipData() {
        return chipData;
    }

    public void setChipData(String chipData) {
        this.chipData = chipData;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock(String pinBlock) {
        this.pinBlock = pinBlock;
    }

    public String getTrack1Structure() {
        return track1Structure;
    }

    public void setTrack1Structure(String track1Structure) {
        this.track1Structure = track1Structure;
    }

    public String getTrack2Structure() {
        return track2Structure;
    }

    public void setTrack2Structure(String track2Structure) {
        this.track2Structure = track2Structure;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", track1='" + track1 + '\'' +
                ", track2='" + track2 + '\'' +
                ", chipData='" + chipData + '\'' +
                ", pinBlock='" + pinBlock + '\'' +
                ", track1Structure='" + track1Structure + '\'' +
                ", track2Structure='" + track2Structure + '\'' +
                '}';
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("ddMM");

    }
}
