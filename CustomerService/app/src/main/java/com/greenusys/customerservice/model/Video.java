package com.greenusys.customerservice.model;

/**
 * Created by admin on 06-Mar-18.
 */

public class Video {
    public Video(String classes, String video, String description) {
        this.classes = classes;
        this.video = video;
        this.description = description;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    private String classes;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    private String video;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


}