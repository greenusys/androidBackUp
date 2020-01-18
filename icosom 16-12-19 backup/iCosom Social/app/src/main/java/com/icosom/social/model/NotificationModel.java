package com.icosom.social.model;

public class NotificationModel
{
    private String notificationId;
    private String notifyDate;
    private String readStatus;
    private String notifyToUser;
    private String postId;
    private String action;
    private String notifee;
    private String firstName;
    private String lastName;
    private String profilePicture;

    public NotificationModel(String notificationId, String notifyDate, String readStatus, String notifyToUser, String postId, String action, String notifee, String firstName, String lastName, String profilePicture)
    {
        this.notificationId = notificationId;
        this.notifyDate = notifyDate;
        this.readStatus = readStatus;
        this.notifyToUser = notifyToUser;
        this.postId = postId;
        this.action = action;
        this.notifee = notifee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getNotifyToUser() {
        return notifyToUser;
    }

    public void setNotifyToUser(String notifyToUser) {
        this.notifyToUser = notifyToUser;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNotifee() {
        return notifee;
    }

    public void setNotifee(String notifee) {
        this.notifee = notifee;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}