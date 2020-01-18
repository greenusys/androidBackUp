package com.avinashdavid.trivialtrivia.Model;

//import android.widget.String;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Check_Rally_Model {
    public String sn, state_name, district_name, category_name, venue, app_start_date, app_end_date, rally_start_date, rally_end_date;

    public Check_Rally_Model(String sn, String state_name, String district_name, String category_name, String venue, String app_start_date, String app_end_date, String rally_start_date, String rally_end_date) {
        this.sn = sn;
        this.state_name = state_name;
        this.district_name = district_name;
        this.category_name = category_name;
        this.venue = venue;
        this.app_start_date = app_start_date;
        this.app_end_date = app_end_date;
        this.rally_start_date = rally_start_date;
        this.rally_end_date = rally_end_date;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getApp_start_date() {
        return app_start_date;
    }

    public void setApp_start_date(String app_start_date) {
        this.app_start_date = app_start_date;
    }

    public String getApp_end_date() {
        return app_end_date;
    }

    public void setApp_end_date(String app_end_date) {
        this.app_end_date = app_end_date;
    }

    public String getRally_start_date() {
        return rally_start_date;
    }

    public void setRally_start_date(String rally_start_date) {
        this.rally_start_date = rally_start_date;
    }

    public String getRally_end_date() {
        return rally_end_date;
    }

    public void setRally_end_date(String rally_end_date) {
        this.rally_end_date = rally_end_date;
    }

  

} 