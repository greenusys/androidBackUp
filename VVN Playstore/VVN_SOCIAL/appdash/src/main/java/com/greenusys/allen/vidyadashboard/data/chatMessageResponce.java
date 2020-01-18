package com.greenusys.allen.vidyadashboard.data;


import com.greenusys.allen.vidyadashboard.model.messageModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class chatMessageResponce {

    @SerializedName("result")
    private ArrayList<messageModel> Data;

    public ArrayList<messageModel> getData() {
        return Data;
    }

    public void setData(ArrayList<messageModel> data) {
        Data = data;
    }
}


