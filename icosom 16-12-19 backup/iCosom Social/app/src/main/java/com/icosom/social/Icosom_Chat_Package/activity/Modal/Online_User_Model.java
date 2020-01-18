package com.icosom.social.Icosom_Chat_Package.activity.Modal;

public class Online_User_Model {


    String sender_socket_id;
    String sender_user_id;
    String sender_user_name;

    public Online_User_Model(String sender_socket_id, String sender_user_id, String sender_user_name) {
        this.sender_socket_id = sender_socket_id;
        this.sender_user_id = sender_user_id;
        this.sender_user_name = sender_user_name;
    }

    public String getSender_socket_id() {
        return sender_socket_id;
    }

    public String getSender_user_id() {
        return sender_user_id;
    }


    public String getSender_user_name() {
        return sender_user_name;
    }




    @Override
    public String toString() {
        return "Online_User_Model{" +
                "sender_socket_id='" + sender_socket_id + '\'' +
                ", sender_user_id='" + sender_user_id + '\'' +
                ", sender_user_name='" + sender_user_name + '\'' +
                '}';
    }


}
