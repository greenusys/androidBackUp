package com.greenusys.customerservice.activity;

/**
 * Created by admin on 01-06-2018.
 */

public class EnginerModel { 
    private String name;
    private String phone;
    private String address;
    private String id;
    String queryId;
    String query;
    String date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public EnginerModel(String name, String phone, String address, String id, String queryId, String query, String date ,String status) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.queryId = queryId;
        this.query = query;
        this.date = date;
        this.status = status;
    }






}