package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Volume_Calc_History {


    @PrimaryKey(autoGenerate = true)
    private int id;


    String date;
    String length;
    String width;
    String height;
    String title_plus_result;


    public Volume_Calc_History(String date, String length, String width, String height, String title_plus_result) {
        this.date = date;
        this.length = length;
        this.width = width;
        this.height = height;
        this.title_plus_result = title_plus_result;
    }



    public String getTitle_plus_result() {
        return title_plus_result;
    }

    public void setTitle_plus_result(String title_plus_result) {
        this.title_plus_result = title_plus_result;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }













}
