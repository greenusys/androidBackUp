package com.greenusys.allen.vidyadashboard.model;

/**
 * Created by Allen on 9/8/2017.
 */
public class Chat {
    private int image;
    private int id;
    private int sender_id;
    private String sender_type;
    private int receiver_id;
    private String receiver_type;
    private String message;
    private int status;
    private int user_type;

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setSender_type(String sender_type) {
        this.sender_type = sender_type;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setReceiver_type(String receiver_type) {
        this.receiver_type = receiver_type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getId() {
        return id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public String getSender_type() {
        return sender_type;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public String getReceiver_type() {
        return receiver_type;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public int getUser_type() {
        return user_type;
    }
}
