package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by admin on 15-Mar-18.
 */

public class Course {

    private String courseName;
    private String coursePfdName;

    public Course(String courseName, String coursePfdName)
    {
        this.courseName = courseName;
        this.coursePfdName = coursePfdName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public void setCoursePfdName(String coursePfdName)
    {
        this.coursePfdName = coursePfdName;
    }

    public String getCourseName()
    {
        return courseName;
    }
    public String getCoursePfdName()
    {
        return coursePfdName;
    }
}
