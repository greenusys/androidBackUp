package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 15-Mar-18.
 */

public class Video_Modal {


    private String video;
    private String description;

    public Video_Modal(String video, String description) {
        this.video = video;
        this.description = description;
    }




    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
