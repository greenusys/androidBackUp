package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Rally_model  {

    private String name, date,link,state,regsion;

    public Rally_model(String name, String date, String state, String regsion, String link) {
        this.name = name;
        this.date = date;
        this.state = state;
        this.regsion = regsion;
        this.link = link;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegsion() {
        return regsion;
    }

    public void setRegsion(String regsion) {
        this.regsion = regsion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
