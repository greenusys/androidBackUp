package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Weight_Calc_History {


    @PrimaryKey(autoGenerate = true)
    private int id;



    String date;
    String kg;
    String stones;
    String lbs_pound;
    String oz;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getStones() {
        return stones;
    }

    public void setStones(String stones) {
        this.stones = stones;
    }

    public String getLbs_pound() {
        return lbs_pound;
    }

    public void setLbs_pound(String lbs_pound) {
        this.lbs_pound = lbs_pound;
    }

    public String getOz() {
        return oz;
    }

    public void setOz(String oz) {
        this.oz = oz;
    }


    public Weight_Calc_History(String date,String kg, String stones, String lbs_pound, String oz) {
        this.date = date;
        this.kg = kg;
        this.stones = stones;
        this.lbs_pound = lbs_pound;
        this.oz = oz;
    }













}
