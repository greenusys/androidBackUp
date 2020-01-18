package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Length_Calc_History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String date;
    String meters;
    String centimeter;
    String mm;
    String feet;
    String inches;

    public Length_Calc_History(String date, String meters, String centimeter, String mm, String feet, String inches) {
        this.date = date;
        this.meters = meters;
        this.centimeter = centimeter;
        this.mm = mm;
        this.feet = feet;
        this.inches = inches;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }

    public String getCentimeter() {
        return centimeter;
    }

    public void setCentimeter(String centimeter) {
        this.centimeter = centimeter;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public String getInches() {
        return inches;
    }

    public void setInches(String inches) {
        this.inches = inches;
    }



}
