package com.icosom.social.model;

public class FriendRequestModel
{
    String friendsId;
    String friendsFirstName;
    String friendsLastName;
    String friendsProfile;

    public FriendRequestModel(String friendsId, String friendsFirstName, String friendsLastName,
                              String friendsProfile)
    {
        this.friendsId = friendsId;
        this.friendsFirstName = friendsFirstName;
        this.friendsLastName = friendsLastName;
        this.friendsProfile = friendsProfile;
    }

    public String getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(String friendsId) {
        this.friendsId = friendsId;
    }

    public String getFriendsFirstName() {
        return friendsFirstName;
    }

    public void setFriendsFirstName(String friendsFirstName) {
        this.friendsFirstName = friendsFirstName;
    }

    public String getFriendsLastName() {
        return friendsLastName;
    }

    public void setFriendsLastName(String friendsLastName) {
        this.friendsLastName = friendsLastName;
    }

    public String getFriendsProfile() {
        return friendsProfile;
    }

    public void setFriendsProfile(String friendsProfile) {
        this.friendsProfile = friendsProfile;
    }
}