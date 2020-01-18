package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Distance_Calc_History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String date;
    String miles;
    String km;
    String meters;
    String feet;
    String inches;

    public Distance_Calc_History(String date, String miles, String km, String meters, String feet, String inches) {
        this.date = date;
        this.miles = miles;
        this.km = km;
        this.meters = meters;
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

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
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
