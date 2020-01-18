package com.icosom.social.Icosom_Chat_Package.activity.Modal;

import java.io.Serializable;
import java.util.ArrayList;

public class Delete_Msg_Model
{
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delete_Msg_Model{" +
                "status='" + status + '\'' +
                '}';
    }

    String status;


}