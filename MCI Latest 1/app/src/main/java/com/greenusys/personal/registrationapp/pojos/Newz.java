package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by G-112 on 28-02-2018.
 */

public class Newz {

    private String id;
    private String clss;
    private String title;
    private String news;
    private String time;

    public Newz(String id, String clss, String title, String news, String time)
    {
        this.id = id;
        this.clss = clss;
        this.title = title;
        this.time = time;
        this.news = news;
    }

    public String getId()
    {
        return id;
    }
    public String getClss()
    {
        return clss;
    }
    public String getTitle()
    {
        return title;
    }
    public String getTime()
    {
        return time;
    }
    public String getNews()
    {
        return news;
    }
}
