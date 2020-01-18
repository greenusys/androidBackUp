package com.example.sunrise.Modal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class History_Modal {


    @NonNull
    @PrimaryKey
    String address;//city name and country name
    String sunrise_time;
    String sunset_time;

    boolean item_selected;


    public History_Modal(@NonNull String address, String sunrise_time, String sunset_time) {
        this.address = address;
        this.sunrise_time = sunrise_time;
        this.sunset_time = sunset_time;
    }



    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public String getSunrise_time() {
        return sunrise_time;
    }

    public void setSunrise_time(String sunrise_time) {
        this.sunrise_time = sunrise_time;
    }

    public String getSunset_time() {
        return sunset_time;
    }

    public void setSunset_time(String sunset_time) {
        this.sunset_time = sunset_time;
    }



    public boolean isItem_selected() {
        return item_selected;
    }

    public void setItem_selected(boolean item_selected) {
        this.item_selected = item_selected;
    }





}
