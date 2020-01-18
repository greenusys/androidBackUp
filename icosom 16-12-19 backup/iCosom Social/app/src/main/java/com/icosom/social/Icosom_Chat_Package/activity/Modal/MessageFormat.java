package com.icosom.social.Icosom_Chat_Package.activity.Modal;

public class MessageFormat {

    private String type;
    private String text;
    private String socket_sender_id;
    private String socket_receiver_id;
    private String sender_id;
    private String reciever_id;
    private String message_id;
    private String date;
    private String time;

    public MessageFormat(String message_id, String type, String text, String socket_sender_id, String socket_receiver_id, String sender_id, String reciever_id, String message_date, String time) {
        this.message_id = message_id;
        this.type = type;
        this.text = text;
        this.socket_sender_id = socket_sender_id;
        this.socket_receiver_id = socket_receiver_id;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.date = message_date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getMessage_date() {
        return date;
    }

    public void setMessage_date(String message_date) {
        this.date = message_date;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSocket_sender_id() {
        return socket_sender_id;
    }

    public void setSocket_sender_id(String socket_sender_id) {
        this.socket_sender_id = socket_sender_id;
    }

    public String getSocket_receiver_id() {
        return socket_receiver_id;
    }

    public void setSocket_receiver_id(String socket_receiver_id) {
        this.socket_receiver_id = socket_receiver_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }
}
