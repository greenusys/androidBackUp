package com.icosom.social.activity;

/**
 * Created by admin on 07-06-2018.
 */

public class Model_plan {
    String amount;
    String detail;
    String validity;
    String talktime;

    public Model_plan() {
        this.amount = "22";
        this.detail = "22";
        this.validity = "22";
        this.talktime = "22";
    }


    public Model_plan(String amount, String detail, String validity, String talktime) {
        this.amount = amount;
        this.detail = detail;
        this.validity = validity;
        this.talktime = talktime;
    }

    public String getAmounts() {
        return amount;
    }

    public void setAmounts(String amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getTalktime() {
        return talktime;
    }

    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }

}
