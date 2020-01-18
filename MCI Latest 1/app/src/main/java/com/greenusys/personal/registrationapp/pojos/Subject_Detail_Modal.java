package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 15-Mar-18.
 */

public class Subject_Detail_Modal {



    private String subject_id;
    private String upload_file;
    private String description;


    public Subject_Detail_Modal(String subject_id, String upload_file, String description) {
        this.subject_id = subject_id;
        this.upload_file = upload_file;
        this.description = description;
    }




    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getUpload_file() {
        return upload_file;
    }

    public void setUpload_file(String upload_file) {
        this.upload_file = upload_file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }






}