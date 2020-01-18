package com.greenusys.customerservice.activity;

/**
 * Created by admin on 06-Sep-18.
 */

public class QutactionModel {

    String Number;
    String hname;
    String Rate;
    String Quanty;
    String Amount;
    String Dec;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDec() {
        return Dec;
    }

    public void setDec(String dec) {
        Dec = dec;
    }

    public QutactionModel(String number, String hname, String rate, String quanty, String amount, String Dec, String img) {
        this.Number = number;
        this.hname = hname;
        this.Rate = rate;
        this.Quanty = quanty;
        this.Amount = amount;
        this.Dec = Dec;
        this.img = img;

    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getQuanty() {
        return Quanty;
    }

    public void setQuanty(String quanty) {
        Quanty = quanty;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
