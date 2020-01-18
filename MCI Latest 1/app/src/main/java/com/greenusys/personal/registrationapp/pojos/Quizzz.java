package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 07-Mar-18.
 */

public class Quizzz {


    public Quizzz(String test_id, String test_title, String test_time) {
        this.test_id = test_id;
        this.test_title = test_title;
        this.test_time = test_time;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_title() {
        return test_title;
    }

    public void setTest_title(String test_title) {
        this.test_title = test_title;
    }

    private String test_id;
    private String test_title;

    public String getTest_time() {
        return test_time;
    }

    public void setTest_time(String test_time) {
        this.test_time = test_time;
    }

    private String test_time;
}
