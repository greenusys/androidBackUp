package com.icosom.social.model;

public class SearchUserModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String profile;
    private String cover;

    public SearchUserModel(String userId, String firstName, String lastName, String profile, String cover) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile = profile;
        this.cover = cover;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}