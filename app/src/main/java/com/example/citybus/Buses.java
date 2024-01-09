package com.example.citybus;

public class Buses
{
    String FROM;
    String TO;
    String Via;
    String NO;
    String TIME;
    int SEAT;
    int price;
    int HANDI;
    int SENIOR;

    public Buses() {    }

    public Buses(String FROM, String NO, String TO, String Via, String TIME, int SEAT, int price,int HANDI,int SENIOR)
    {
        this.FROM = FROM;
        this.NO = NO;
        this.TO = TO;
        this.Via = Via;
        this.TIME = TIME;
        this.SEAT = SEAT;
        this.price = price;
        this.HANDI = HANDI;
        this.SENIOR = SENIOR;
    }

    public int getHANDI() {
        return HANDI;
    }

    public void setHANDI(int HANDI) {
        this.HANDI = HANDI;
    }

    public int getSENIOR() {
        return SENIOR;
    }

    public void setSENIOR(int SENIOR) {
        this.SENIOR = SENIOR;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public int getSEAT() {
        return SEAT;
    }

    public void setSEAT(int SEAT) {
        this.SEAT = SEAT;
    }

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public String getNO() { return NO; }

    public void setNO(String NO) { this.NO = NO; }

    public String getTO() {
        return TO;
    }

    public void setTO(String TO) {
        this.TO = TO;
    }

    public String getVia() {
        return Via;
    }

    public void setVia(String Via) {
        this.Via = Via;
    }
}
