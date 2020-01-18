package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Subject_Category_Model {

    private String subject_id;

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String subject_name;
    private String date;

    public Subject_Category_Model(String subject_id, String subject_name, String date) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.date = date;
    }






}
