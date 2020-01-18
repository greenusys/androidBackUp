package com.example.digitalforgeco.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tracking_History_Modal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String date;
    String tracking_no;
    String completed;//return yes or no
    String json_data;


    public Tracking_History_Modal(String date, String tracking_no, String completed, String json_data) {
        this.date = date;
        this.tracking_no = tracking_no;
        this.completed = completed;
        this.json_data = json_data;
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

    public String getTracking_no() {
        return tracking_no;
    }

    public void setTracking_no(String tracking_no) {
        this.tracking_no = tracking_no;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }





}
