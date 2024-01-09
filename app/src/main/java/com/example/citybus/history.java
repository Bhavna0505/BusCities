package com.example.citybus;

public class history
{
    String TICKET_ID;
    String PASSENGER_NAME;
    String PASSENGER_AGE;
    String BUS_NUMBER;
    String SOURCE;
    String DESTINATION;
    String VIA;
    String DATE;
    String TIME;
    int SEAT_NUMBER;
    int TOTAL_FARE;
    String PAYMENT_STATUS;
    String EMAIL;
    String QUOTA;
    String SEAT_PREFERENCE;

    public history() {
    }

    public history(String TICKET_ID, String PASSENGER_NAME, String PASSENGER_AGE, String BUS_NUMBER, String SOURCE, String DESTINATION, String VIA, String DATE, String TIME, int SEAT_NUMBER, int TOTAL_FARE, String PAYMENT_STATUS, String EMAIL, String QUOTA, String SEAT_PREFERENCE) {
        this.TICKET_ID = TICKET_ID;
        this.PASSENGER_NAME = PASSENGER_NAME;
        this.PASSENGER_AGE = PASSENGER_AGE;
        this.BUS_NUMBER = BUS_NUMBER;
        this.SOURCE = SOURCE;
        this.DESTINATION = DESTINATION;
        this.VIA = VIA;
        this.DATE = DATE;
        this.TIME = TIME;
        this.SEAT_NUMBER = SEAT_NUMBER;
        this.TOTAL_FARE = TOTAL_FARE;
        this.PAYMENT_STATUS = PAYMENT_STATUS;
        this.EMAIL = EMAIL;
        this.QUOTA = QUOTA;
        this.SEAT_PREFERENCE = SEAT_PREFERENCE;
    }

    public String getQUOTA() {
        return QUOTA;
    }

    public void setQUOTA(String QUOTA) {
        this.QUOTA = QUOTA;
    }

    public String getSEAT_PREFERENCE() {
        return SEAT_PREFERENCE;
    }

    public void setSEAT_PREFERENCE(String SEAT_PREFERENCE) {
        this.SEAT_PREFERENCE = SEAT_PREFERENCE;
    }

    public String getTICKET_ID() {
        return TICKET_ID;
    }

    public void setTICKET_ID(String TICKET_ID) {
        this.TICKET_ID = TICKET_ID;
    }

    public String getPASSENGER_NAME() {
        return PASSENGER_NAME;
    }

    public void setPASSENGER_NAME(String PASSENGER_NAME) {
        this.PASSENGER_NAME = PASSENGER_NAME;
    }

    public String getPASSENGER_AGE() {
        return PASSENGER_AGE;
    }

    public void setPASSENGER_AGE(String PASSENGER_AGE) {
        this.PASSENGER_AGE = PASSENGER_AGE;
    }

    public String getBUS_NUMBER() {
        return BUS_NUMBER;
    }

    public void setBUS_NUMBER(String BUS_NUMBER) {
        this.BUS_NUMBER = BUS_NUMBER;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public void setDESTINATION(String DESTINATION) {
        this.DESTINATION = DESTINATION;
    }

    public String getVIA() {
        return VIA;
    }

    public void setVIA(String VIA) {
        this.VIA = VIA;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public int getSEAT_NUMBER() {
        return SEAT_NUMBER;
    }

    public void setSEAT_NUMBER(int SEAT_NUMBER) {
        this.SEAT_NUMBER = SEAT_NUMBER;
    }

    public int getTOTAL_FARE() {
        return TOTAL_FARE;
    }

    public void setTOTAL_FARE(int TOTAL_FARE) {
        this.TOTAL_FARE = TOTAL_FARE;
    }

    public String getPAYMENT_STATUS() {
        return PAYMENT_STATUS;
    }

    public void setPAYMENT_STATUS(String PAYMENT_STATUS) {
        this.PAYMENT_STATUS = PAYMENT_STATUS;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
}
