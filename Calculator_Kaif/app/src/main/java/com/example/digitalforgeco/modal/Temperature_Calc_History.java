package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Temperature_Calc_History {


    @PrimaryKey(autoGenerate = true)
    private int id;

    String date;
    String farhenheit;
    String celsius;
    String kelvin;

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

    public String getFarhenheit() {
        return farhenheit;
    }

    public void setFarhenheit(String farhenheit) {
        this.farhenheit = farhenheit;
    }

    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }

    public String getKelvin() {
        return kelvin;
    }

    public void setKelvin(String kelvin) {
        this.kelvin = kelvin;
    }



    public Temperature_Calc_History(String date, String farhenheit, String celsius, String kelvin) {
        this.date = date;
        this.farhenheit = farhenheit;
        this.celsius = celsius;
        this.kelvin = kelvin;
    }








}
