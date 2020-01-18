package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Current_Affair_model {
    private String details;
    private String name;
    private String date;
    private String link;
    private String current_affair;

    public Current_Affair_model(String details, String name, String date, String link, String current_affair) {
        this.details = details;
        this.name = name;
        this.date = date;
        this.link = link;
        this.current_affair = current_affair;
    }

    public Current_Affair_model(String details) {
        this.details = details;
    }

    public Current_Affair_model() {

    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCurrent_affair() {
        return current_affair;
    }

    public void setCurrent_affair(String current_affair) {
        this.current_affair = current_affair;
    }
}
