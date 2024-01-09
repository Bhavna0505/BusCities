package com.example.citybus;

public class smartapplicationsRV
{   String NAME;
    String DOB;
    String AADHAR;
    String ADDRESS;
    String CONTACT_NO;
    String APPLICATION_STATUS;
    String PAYMENT_STATUS;
    String POSTAL_STATUS;
    String CARD_NO;
    String ORDER_ID;

    public smartapplicationsRV() {
    }

    public smartapplicationsRV(String NAME, String DOB, String AADHAR, String ADDRESS, String CONTACT_NO, String APPLICATION_STATUS, String PAYMENT_STATUS, String POSTAL_STATUS, String CARD_NO, String ORDER_ID) {
        this.NAME = NAME;
        this.DOB = DOB;
        this.AADHAR = AADHAR;
        this.ADDRESS = ADDRESS;
        this.CONTACT_NO = CONTACT_NO;
        this.APPLICATION_STATUS = APPLICATION_STATUS;
        this.PAYMENT_STATUS = PAYMENT_STATUS;
        this.POSTAL_STATUS = POSTAL_STATUS;
        this.CARD_NO = CARD_NO;
        this.ORDER_ID = ORDER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAADHAR() {
        return AADHAR;
    }

    public void setAADHAR(String AADHAR) {
        this.AADHAR = AADHAR;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCONTACT_NO() {
        return CONTACT_NO;
    }

    public void setCONTACT_NO(String CONTACT_NO) {
        this.CONTACT_NO = CONTACT_NO;
    }

    public String getAPPLICATION_STATUS() {
        return APPLICATION_STATUS;
    }

    public void setAPPLICATION_STATUS(String APPLICATION_STATUS) {
        this.APPLICATION_STATUS = APPLICATION_STATUS;
    }

    public String getPAYMENT_STATUS() {
        return PAYMENT_STATUS;
    }

    public void setPAYMENT_STATUS(String PAYMENT_STATUS) {
        this.PAYMENT_STATUS = PAYMENT_STATUS;
    }

    public String getPOSTAL_STATUS() {
        return POSTAL_STATUS;
    }

    public void setPOSTAL_STATUS(String POSTAL_STATUS) {
        this.POSTAL_STATUS = POSTAL_STATUS;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }
}
