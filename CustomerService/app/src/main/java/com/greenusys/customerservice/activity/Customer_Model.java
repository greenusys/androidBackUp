package com.greenusys.customerservice.activity;

/**
 * Created by admin on 01-06-2018.
 */
public class Customer_Model {
    String QueryId;
    String CustomerQuery;
    String CustomerName;
    String PostedOn;
    String EnggName;
    String EnggPhone;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    String Status;

    public String getEnggName() {
        return EnggName;
    }

    public void setEnggName(String enggName) {
        EnggName = enggName;
    }

    public String getEnggPhone() {
        return EnggPhone;
    }

    public void setEnggPhone(String enggPhone) {
        EnggPhone = enggPhone;
    }

    public Customer_Model(String queryId, String customerQuery, String customerName, String postedOn, String enggName, String enggPhone,String status) {

        QueryId = queryId;
        CustomerQuery = customerQuery;
        CustomerName = customerName;
        PostedOn = postedOn;
        EnggName = enggName;
        EnggPhone = enggPhone;
        Status = status;
    }

    public String getQueryId() {
        return QueryId;
    }

    public void setQueryId(String queryId) {
        QueryId = queryId;
    }

    public String getCustomerQuery() {
        return CustomerQuery;
    }

    public void setCustomerQuery(String customerQuery) {
        CustomerQuery = customerQuery;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPostedOn() {
        return PostedOn;
    }

    public void setPostedOn(String postedOn) {
        PostedOn = postedOn;
    }


}