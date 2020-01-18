package com.example.g116.vvn_social.Modal;

/**
 * Created by Greenusys on 20-07-2019.
 */

public class Mess_model {

    public String text;
    public String sender;
    public String recvier;

    public  String self_message;
    public  String friend_message;

    public  String self_message_time;
    public  String friend_message_time;

    public String getSelf_id() {
        return self_id;
    }

    public void setSelf_id(String self_id) {
        this.self_id = self_id;
    }

    public String getFrnd_id() {
        return frnd_id;
    }

    public void setFrnd_id(String frnd_id) {
        this.frnd_id = frnd_id;
    }

    public  String self_id;
    public  String frnd_id;

    public Mess_model() {

    }

    public String getSelf_message() {
        return self_message;
    }

    public void setSelf_message(String self_message) {
        this.self_message = self_message;
    }

    public String getFriend_message() {
        return friend_message;
    }

    public void setFriend_message(String friend_message) {
        this.friend_message = friend_message;
    }

    public String getSelf_message_time() {
        return self_message_time;
    }

    public void setSelf_message_time(String self_message_time) {
        this.self_message_time = self_message_time;
    }

    public String getFriend_message_time() {
        return friend_message_time;
    }

    public void setFriend_message_time(String friend_message_time) {
        this.friend_message_time = friend_message_time;
    }



    public Mess_model(String text, String sender, String recvier) {
        this.text = text;
        this.sender = sender;
        this.recvier = recvier;
    }

    public Mess_model(String message) {
        this.text = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecvier() {
        return recvier;
    }

    public void setRecvier(String recvier) {
        this.recvier = recvier;
    }
}



