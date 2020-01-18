package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 15-Mar-18.
 */

public class Subject_Detail_Data_Modal {

    private String upload_file;
    private String description;

    public Subject_Detail_Data_Modal(String upload_file, String description) {
        this.upload_file = upload_file;
        this.description = description;
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
