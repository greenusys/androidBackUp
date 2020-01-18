package com.example.currencyconverter.modal;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class History implements Serializable {



    @PrimaryKey(autoGenerate = true)
    private int id;

    public History(String country_name, String country_code2_letter, String country_code3_letter, @NonNull byte[] country_image) {
        this.country_name = country_name;
        this.country_code2_letter = country_code2_letter;
        this.country_code3_letter = country_code3_letter;
        this.country_image = country_image;
    }

    String country_name;
    String country_code2_letter;
    String country_code3_letter;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] country_image;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code2_letter() {
        return country_code2_letter;
    }

    public void setCountry_code2_letter(String country_code2_letter) {
        this.country_code2_letter = country_code2_letter;
    }

    public String getCountry_code3_letter() {
        return country_code3_letter;
    }

    public void setCountry_code3_letter(String country_code3_letter) {
        this.country_code3_letter = country_code3_letter;
    }

    @NonNull
    public byte[] getCountry_image() {
        return country_image;
    }

    public void setCountry_image(@NonNull byte[] country_image) {
        this.country_image = country_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
