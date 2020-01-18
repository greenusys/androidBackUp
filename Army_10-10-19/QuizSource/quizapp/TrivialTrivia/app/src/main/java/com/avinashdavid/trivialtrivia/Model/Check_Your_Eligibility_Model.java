package com.avinashdavid.trivialtrivia.Model;

/**
 * Created by Greenusys on 15-03-2019.
 */

public class Check_Your_Eligibility_Model {

    private String category;
    private String title;
    private String qualification;
    private String age;

    public Check_Your_Eligibility_Model(String category, String title, String qualification, String age) {
        this.category = category;
        this.title = title;
        this.qualification = qualification;
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void getTitle(String title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
