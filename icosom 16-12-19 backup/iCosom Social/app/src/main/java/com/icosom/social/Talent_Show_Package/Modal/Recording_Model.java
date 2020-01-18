package com.icosom.social.Talent_Show_Package.Modal;

/**
 * Created by Manish on 10/3/2017.
 */

public class Recording_Model {

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    String fileName;

    public String getAudio_name() {
        return audio_name;
    }

    public void setAudio_name(String audio_name) {
        this.audio_name = audio_name;
    }

    String audio_name;
    boolean isPlaying = false;




}
