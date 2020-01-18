package com.example.g116.vvn_social.Modal;

public class SearchUserModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String profile;
    private String user_type;



    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }



    public SearchUserModel(String userId, String firstName, String lastName, String profile, String user_type) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile = profile;
        this.user_type = user_type;

    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}