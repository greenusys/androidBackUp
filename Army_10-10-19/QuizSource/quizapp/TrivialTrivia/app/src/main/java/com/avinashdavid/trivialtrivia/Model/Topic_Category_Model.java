package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Topic_Category_Model {
    private String topic_id;
    private String topic_name;
    private String date;

    public Topic_Category_Model(String topic_id, String topic_name, String date) {
        this.topic_id = topic_id;
        this.topic_name = topic_name;
        this.date = date;
    }



    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }













}
