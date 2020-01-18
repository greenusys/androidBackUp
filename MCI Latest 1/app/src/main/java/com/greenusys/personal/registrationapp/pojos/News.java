package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by personal on 2/22/2018.
 */

public class News {

    private String id;
    private String title;

    public News(String id, String title, String news, String time) {
        this.id = id;
        this.title = title;
        this.news = news;
        this.time = time;
    }

    private String news;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }




}
