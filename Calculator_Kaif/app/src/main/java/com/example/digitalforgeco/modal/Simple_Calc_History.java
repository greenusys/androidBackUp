package com.example.digitalforgeco.modal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Simple_Calc_History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String date;
    String data;



    public Simple_Calc_History(String date, String data) {
        this.date = date;
        this.data = data;
    }


    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
