package com.greenusys.allen.vidyadashboard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhishek Bisht on 8/18/2017.
 */

public class messageModel {
    @SerializedName("id")
    private int id;
    @SerializedName("sender_id")
    private int sender_id;
    @SerializedName("sender_type")

    private String sender_type;
    @SerializedName("reciver_id")
    private int reciever_id;
    @SerializedName("reciver_type")
    private String reciever_type;
    @SerializedName("message")
    private String message;
    @SerializedName("read_status")
    private int status;

    @SerializedName("user_type")
    private int user_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_type() {
        return sender_type;
    }

    public void setSender_type(String sender_type) {
        this.sender_type = sender_type;
    }

    public int getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(int reciever_id) {
        this.reciever_id = reciever_id;
    }

    public String getReciever_type() {
        return reciever_type;
    }

    public void setReciever_type(String reciever_type) {
        this.reciever_type = reciever_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserType() {
        return user_type;
    }

    public void setUserType(int user_type) {
        this.user_type = user_type;
    }
}
