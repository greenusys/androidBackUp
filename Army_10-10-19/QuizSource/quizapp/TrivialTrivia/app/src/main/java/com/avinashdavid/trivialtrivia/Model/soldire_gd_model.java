package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class soldire_gd_model {

    private String name, date,link;

    public soldire_gd_model(String name, String date, String link) {
        this.name = name;
        this.date = date;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
