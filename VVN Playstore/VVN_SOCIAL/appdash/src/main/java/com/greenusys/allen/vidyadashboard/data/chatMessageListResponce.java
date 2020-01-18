package com.greenusys.allen.vidyadashboard.data;


import com.google.gson.annotations.SerializedName;
import com.greenusys.allen.vidyadashboard.model.messageListModel;

import java.util.ArrayList;

/**
 * Created by Abhishek Bisht on 8/23/2017.
 */

public class chatMessageListResponce {


    @SerializedName("receiver")
    private ArrayList<messageListModel> Data;

    public ArrayList<messageListModel> getData() {
        return Data;
    }

    public void setData(ArrayList<messageListModel> data) {
        Data = data;
    }
}
