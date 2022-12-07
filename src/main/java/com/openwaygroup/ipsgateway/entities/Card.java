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

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
//@Entity
public class Card {
    @Id
    @GeneratedValue

    private Integer id;
    private String f002;
    private String f014;
    private String f023;
    private String f035_04;
    private String f035_05;
    private String f035_06;
    private String f035_07;
    private String f035_08;
    private String f045;
    private String f045_01;
    private String f045_02;
    private String f045_04;
    private String f045_06;
    private String f045_07;
    private String f045_08;
    private String f045_09;
    private String f045_10;
    private String f045_11;
    private String f045_12_2;
    private String f052;
    private String f053_03;
    private String f053_04;
    private String f053_05;
    private String f053_06;
    private String f053_07;
    private String f053_08;
    private String f053_09;
    private String f053_10;
    private String f053_11;
    private String f053_12;
    private String f053_13;
    private String f055_07_03;
    private String f055_07_04;
    private String f055_07_05;
    private String f055_07_06;
    private String f055_07_07;
    private String f055_07_08;
    private String f055_07_09;
    private String f055_07_10;
    private String f055_21;
    private String f055_22;
    private String f055_23_08;
    private String f123_11;
    private String f125_025;
    private String f125_034;
    private String f125_048;
    private String f125_057;
    private String f126_10;
    private String f134;
    private String f135;
    private String f136;
    private String f137;
    private String f138;
    private String f152;

    public Card() {
    }

    public Card(String f002, String f014, String f023, String f035_04, String f035_05, String f035_06, String f035_07, String f035_08, String f045, String f045_01, String f045_02, String f045_04, String f045_06, String f045_07, String f045_08, String f045_09, String f045_10, String f045_11, String f045_12_2, String f052, String f053_03, String f053_04, String f053_05, String f053_06, String f053_07, String f053_08, String f053_09, String f053_10, String f053_11, String f053_12, String f053_13, String f055_07_03, String f055_07_04, String f055_07_05, String f055_07_06, String f055_07_07, String f055_07_08, String f055_07_09, String f055_07_10, String f055_21, String f055_22, String f055_23_08, String f123_11, String f125_025, String f125_034, String f125_048, String f125_057, String f126_10, String f134, String f135, String f136, String f137, String f138, String f152) {
        this.f002 = f002;
        this.f014 = f014;
        this.f023 = f023;
        this.f035_04 = f035_04;
        this.f035_05 = f035_05;
        this.f035_06 = f035_06;
        this.f035_07 = f035_07;
        this.f035_08 = f035_08;
        this.f045 = f045;
        this.f045_01 = f045_01;
        this.f045_02 = f045_02;
        this.f045_04 = f045_04;
        this.f045_06 = f045_06;
        this.f045_07 = f045_07;
        this.f045_08 = f045_08;
        this.f045_09 = f045_09;
        this.f045_10 = f045_10;
        this.f045_11 = f045_11;
        this.f045_12_2 = f045_12_2;
        this.f052 = f052;
        this.f053_03 = f053_03;
        this.f053_04 = f053_04;
        this.f053_05 = f053_05;
        this.f053_06 = f053_06;
        this.f053_07 = f053_07;
        this.f053_08 = f053_08;
        this.f053_09 = f053_09;
        this.f053_10 = f053_10;
        this.f053_11 = f053_11;
        this.f053_12 = f053_12;
        this.f053_13 = f053_13;
        this.f055_07_03 = f055_07_03;
        this.f055_07_04 = f055_07_04;
        this.f055_07_05 = f055_07_05;
        this.f055_07_06 = f055_07_06;
        this.f055_07_07 = f055_07_07;
        this.f055_07_08 = f055_07_08;
        this.f055_07_09 = f055_07_09;
        this.f055_07_10 = f055_07_10;
        this.f055_21 = f055_21;
        this.f055_22 = f055_22;
        this.f055_23_08 = f055_23_08;
        this.f123_11 = f123_11;
        this.f125_025 = f125_025;
        this.f125_034 = f125_034;
        this.f125_048 = f125_048;
        this.f125_057 = f125_057;
        this.f126_10 = f126_10;
        this.f134 = f134;
        this.f135 = f135;
        this.f136 = f136;
        this.f137 = f137;
        this.f138 = f138;
        this.f152 = f152;
    }

    public Integer getId() {
        return id;
    }

    public String getF002() {
        return f002;
    }

    public String getF014() {
        return f014;
    }

    public String getF023() {
        return f023;
    }

    public String getF035_04() {
        return f035_04;
    }

    public String getF035_05() {
        return f035_05;
    }

    public String getF035_06() {
        return f035_06;
    }

    public String getF035_07() {
        return f035_07;
    }

    public String getF035_08() {
        return f035_08;
    }

    public String getF045() {
        return f045;
    }

    public String getF045_01() {
        return f045_01;
    }

    public String getF045_02() {
        return f045_02;
    }

    public String getF045_04() {
        return f045_04;
    }

    public String getF045_06() {
        return f045_06;
    }

    public String getF045_07() {
        return f045_07;
    }

    public String getF045_08() {
        return f045_08;
    }

    public String getF045_09() {
        return f045_09;
    }

    public String getF045_10() {
        return f045_10;
    }

    public String getF045_11() {
        return f045_11;
    }

    public String getF045_12_2() {
        return f045_12_2;
    }

    public String getF052() {
        return f052;
    }

    public String getF053_03() {
        return f053_03;
    }

    public String getF053_04() {
        return f053_04;
    }

    public String getF053_05() {
        return f053_05;
    }

    public String getF053_06() {
        return f053_06;
    }

    public String getF053_07() {
        return f053_07;
    }

    public String getF053_08() {
        return f053_08;
    }

    public String getF053_09() {
        return f053_09;
    }

    public String getF053_10() {
        return f053_10;
    }

    public String getF053_11() {
        return f053_11;
    }

    public String getF053_12() {
        return f053_12;
    }

    public String getF053_13() {
        return f053_13;
    }

    public String getF055_07_03() {
        return f055_07_03;
    }

    public String getF055_07_04() {
        return f055_07_04;
    }

    public String getF055_07_05() {
        return f055_07_05;
    }

    public String getF055_07_06() {
        return f055_07_06;
    }

    public String getF055_07_07() {
        return f055_07_07;
    }

    public String getF055_07_08() {
        return f055_07_08;
    }

    public String getF055_07_09() {
        return f055_07_09;
    }

    public String getF055_07_10() {
        return f055_07_10;
    }

    public String getF055_21() {
        return f055_21;
    }

    public String getF055_22() {
        return f055_22;
    }

    public String getF055_23_08() {
        return f055_23_08;
    }

    public String getF123_11() {
        return f123_11;
    }

    public String getF125_025() {
        return f125_025;
    }

    public String getF125_034() {
        return f125_034;
    }

    public String getF125_048() {
        return f125_048;
    }

    public String getF125_057() {
        return f125_057;
    }

    public String getF126_10() {
        return f126_10;
    }

    public String getF134() {
        return f134;
    }

    public String getF135() {
        return f135;
    }

    public String getF136() {
        return f136;
    }

    public String getF137() {
        return f137;
    }

    public String getF138() {
        return f138;
    }

    public String getF152() {
        return f152;
    }

    public void setF002(String f002) {
        this.f002 = f002;
    }

    public void setF014(String f014) {
        this.f014 = f014;
    }

    public void setF023(String f023) {
        this.f023 = f023;
    }

    public void setF035_04(String f035_04) {
        this.f035_04 = f035_04;
    }

    public void setF035_05(String f035_05) {
        this.f035_05 = f035_05;
    }

    public void setF035_06(String f035_06) {
        this.f035_06 = f035_06;
    }

    public void setF035_07(String f035_07) {
        this.f035_07 = f035_07;
    }

    public void setF035_08(String f035_08) {
        this.f035_08 = f035_08;
    }

    public void setF045(String f045) {
        this.f045 = f045;
    }

    public void setF045_01(String f045_01) {
        this.f045_01 = f045_01;
    }

    public void setF045_02(String f045_02) {
        this.f045_02 = f045_02;
    }

    public void setF045_04(String f045_04) {
        this.f045_04 = f045_04;
    }

    public void setF045_06(String f045_06) {
        this.f045_06 = f045_06;
    }

    public void setF045_07(String f045_07) {
        this.f045_07 = f045_07;
    }

    public void setF045_08(String f045_08) {
        this.f045_08 = f045_08;
    }

    public void setF045_09(String f045_09) {
        this.f045_09 = f045_09;
    }

    public void setF045_10(String f045_10) {
        this.f045_10 = f045_10;
    }

    public void setF045_11(String f045_11) {
        this.f045_11 = f045_11;
    }

    public void setF045_12_2(String f045_12_2) {
        this.f045_12_2 = f045_12_2;
    }

    public void setF052(String f052) {
        this.f052 = f052;
    }

    public void setF053_03(String f053_03) {
        this.f053_03 = f053_03;
    }

    public void setF053_04(String f053_04) {
        this.f053_04 = f053_04;
    }

    public void setF053_05(String f053_05) {
        this.f053_05 = f053_05;
    }

    public void setF053_06(String f053_06) {
        this.f053_06 = f053_06;
    }

    public void setF053_07(String f053_07) {
        this.f053_07 = f053_07;
    }

    public void setF053_08(String f053_08) {
        this.f053_08 = f053_08;
    }

    public void setF053_09(String f053_09) {
        this.f053_09 = f053_09;
    }

    public void setF053_10(String f053_10) {
        this.f053_10 = f053_10;
    }

    public void setF053_11(String f053_11) {
        this.f053_11 = f053_11;
    }

    public void setF053_12(String f053_12) {
        this.f053_12 = f053_12;
    }

    public void setF053_13(String f053_13) {
        this.f053_13 = f053_13;
    }

    public void setF055_07_03(String f055_07_03) {
        this.f055_07_03 = f055_07_03;
    }

    public void setF055_07_04(String f055_07_04) {
        this.f055_07_04 = f055_07_04;
    }

    public void setF055_07_05(String f055_07_05) {
        this.f055_07_05 = f055_07_05;
    }

    public void setF055_07_06(String f055_07_06) {
        this.f055_07_06 = f055_07_06;
    }

    public void setF055_07_07(String f055_07_07) {
        this.f055_07_07 = f055_07_07;
    }

    public void setF055_07_08(String f055_07_08) {
        this.f055_07_08 = f055_07_08;
    }

    public void setF055_07_09(String f055_07_09) {
        this.f055_07_09 = f055_07_09;
    }

    public void setF055_07_10(String f055_07_10) {
        this.f055_07_10 = f055_07_10;
    }

    public void setF055_21(String f055_21) {
        this.f055_21 = f055_21;
    }

    public void setF055_22(String f055_22) {
        this.f055_22 = f055_22;
    }

    public void setF055_23_08(String f055_23_08) {
        this.f055_23_08 = f055_23_08;
    }

    public void setF123_11(String f123_11) {
        this.f123_11 = f123_11;
    }

    public void setF125_025(String f125_025) {
        this.f125_025 = f125_025;
    }

    public void setF125_034(String f125_034) {
        this.f125_034 = f125_034;
    }

    public void setF125_048(String f125_048) {
        this.f125_048 = f125_048;
    }

    public void setF125_057(String f125_057) {
        this.f125_057 = f125_057;
    }

    public void setF126_10(String f126_10) {
        this.f126_10 = f126_10;
    }

    public void setF134(String f134) {
        this.f134 = f134;
    }

    public void setF135(String f135) {
        this.f135 = f135;
    }

    public void setF136(String f136) {
        this.f136 = f136;
    }

    public void setF137(String f137) {
        this.f137 = f137;
    }

    public void setF138(String f138) {
        this.f138 = f138;
    }

    public void setF152(String f152) {
        this.f152 = f152;
    }

    @Override
    public String toString() {
        return "Card{" +
                "f002='" + f002 + '\'' +
                ", f014='" + f014 + '\'' +
                ", f023='" + f023 + '\'' +
                ", f035_04='" + f035_04 + '\'' +
                ", f035_05='" + f035_05 + '\'' +
                ", f035_06='" + f035_06 + '\'' +
                ", f035_07='" + f035_07 + '\'' +
                ", f035_08='" + f035_08 + '\'' +
                ", f045='" + f045 + '\'' +
                ", f045_01='" + f045_01 + '\'' +
                ", f045_02='" + f045_02 + '\'' +
                ", f045_04='" + f045_04 + '\'' +
                ", f045_06='" + f045_06 + '\'' +
                ", f045_07='" + f045_07 + '\'' +
                ", f045_08='" + f045_08 + '\'' +
                ", f045_09='" + f045_09 + '\'' +
                ", f045_10='" + f045_10 + '\'' +
                ", f045_11='" + f045_11 + '\'' +
                ", f045_12_2='" + f045_12_2 + '\'' +
                ", f052='" + f052 + '\'' +
                ", f053_03='" + f053_03 + '\'' +
                ", f053_04='" + f053_04 + '\'' +
                ", f053_05='" + f053_05 + '\'' +
                ", f053_06='" + f053_06 + '\'' +
                ", f053_07='" + f053_07 + '\'' +
                ", f053_08='" + f053_08 + '\'' +
                ", f053_09='" + f053_09 + '\'' +
                ", f053_10='" + f053_10 + '\'' +
                ", f053_11='" + f053_11 + '\'' +
                ", f053_12='" + f053_12 + '\'' +
                ", f053_13='" + f053_13 + '\'' +
                ", f055_07_03='" + f055_07_03 + '\'' +
                ", f055_07_04='" + f055_07_04 + '\'' +
                ", f055_07_05='" + f055_07_05 + '\'' +
                ", f055_07_06='" + f055_07_06 + '\'' +
                ", f055_07_07='" + f055_07_07 + '\'' +
                ", f055_07_08='" + f055_07_08 + '\'' +
                ", f055_07_09='" + f055_07_09 + '\'' +
                ", f055_07_10='" + f055_07_10 + '\'' +
                ", f055_21='" + f055_21 + '\'' +
                ", f055_22='" + f055_22 + '\'' +
                ", f055_23_08='" + f055_23_08 + '\'' +
                ", f123_11='" + f123_11 + '\'' +
                ", f125_025='" + f125_025 + '\'' +
                ", f125_034='" + f125_034 + '\'' +
                ", f125_048='" + f125_048 + '\'' +
                ", f125_057='" + f125_057 + '\'' +
                ", f126_10='" + f126_10 + '\'' +
                ", f134='" + f134 + '\'' +
                ", f135='" + f135 + '\'' +
                ", f136='" + f136 + '\'' +
                ", f137='" + f137 + '\'' +
                ", f138='" + f138 + '\'' +
                ", f152='" + f152 + '\'' +
                '}';
    }
}
