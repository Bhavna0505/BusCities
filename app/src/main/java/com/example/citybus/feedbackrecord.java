package com.example.citybus;

public class feedbackrecord
{
    String FEEDBACK;
    String ISSUE_TYPE;
    String MOBILE;
    String NAME;
    String EMAIL_ID;


    public feedbackrecord() {
    }

    public feedbackrecord(String FEEDBACK, String ISSUE_TYPE, String MOBILE, String NAME, String EMAIL_ID) {
        this.FEEDBACK = FEEDBACK;
        this.ISSUE_TYPE = ISSUE_TYPE;
        this.MOBILE = MOBILE;
        this.NAME = NAME;
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getFEEDBACK() {
        return FEEDBACK;
    }

    public void setFEEDBACK(String FEEDBACK) {
        this.FEEDBACK = FEEDBACK;
    }

    public String getISSUE_TYPE() {
        return ISSUE_TYPE;
    }

    public void setISSUE_TYPE(String ISSUE_TYPE) {
        this.ISSUE_TYPE = ISSUE_TYPE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
