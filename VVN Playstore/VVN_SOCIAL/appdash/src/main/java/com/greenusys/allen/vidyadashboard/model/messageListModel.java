package com.greenusys.allen.vidyadashboard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhishek Bisht on 8/18/2017.
 */

public class messageListModel {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("receiver_name")
    private String receiver_name;
    @SerializedName("user_type")
    private String user_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
