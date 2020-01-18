package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 07-Mar-18.
 */

public class Studyy {
    public Studyy(String upload_file, String description, String classes) {
    this.upload_file = upload_file;
    this.description = description;
    this.classes = classes;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    private String upload_file;
    private String description;
    private String classes;

}