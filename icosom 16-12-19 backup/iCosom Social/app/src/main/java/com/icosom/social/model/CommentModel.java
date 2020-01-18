package com.icosom.social.model;

public class CommentModel
{
    private String commentId;
    private String userId;
    private String comment;
    private String activityTime;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String device_token;

    public CommentModel(String device_token,String commentId, String userId, String comment, String activityTime, String profilePicture, String firstName, String lastName)
    {
        this.commentId = commentId;
        this.userId = userId;
        this.comment = comment;
        this.activityTime = activityTime;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.device_token = device_token;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
}