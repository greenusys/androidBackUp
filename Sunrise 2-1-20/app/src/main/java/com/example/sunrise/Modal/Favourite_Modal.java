package com.example.sunrise.Modal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Favourite_Modal {


    @NonNull
    @PrimaryKey
    String latlong;

    String address;//city name and country name
    private boolean item_selected;


    public Favourite_Modal(String address, String latlong) {
        this.address = address;
        this.latlong = latlong;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }



    public boolean isItem_selected() {
        return item_selected;
    }

    public void setItem_selected(boolean item_selected) {
        this.item_selected = item_selected;
    }



}
