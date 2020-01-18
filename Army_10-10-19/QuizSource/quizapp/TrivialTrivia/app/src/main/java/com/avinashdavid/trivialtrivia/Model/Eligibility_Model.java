package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Eligibility_Model {
    private String details;
    private String name;
    private String date;
    private String qualification;
    private String physical;
    private String written;

    public Eligibility_Model(String details, String name, String date, String qualification, String physical, String written) {
        this.details = details;
        this.name = name;
        this.date = date;
        this.qualification = qualification;
        this.physical = physical;
        this.written = written;
    }

    public Eligibility_Model(String details, String string, String string1, String string2, String string3) {
    }

    public Eligibility_Model(String details) {
        this.details = details;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }

    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
    }







}
