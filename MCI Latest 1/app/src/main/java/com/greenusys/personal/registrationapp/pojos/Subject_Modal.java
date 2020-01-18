package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 15-Mar-18.
 */

public class Subject_Modal {

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

    private String subject_id;
    private String subject_name;

    public Subject_Modal(String subject_id, String subject_name) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
    }



}


