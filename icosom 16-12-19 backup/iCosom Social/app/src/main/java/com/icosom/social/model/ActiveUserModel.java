package com.icosom.social.model;

public class ActiveUserModel
{
    String friendsId;
    String friendsFirstName;
    String friendsLastName;
    String friendsProfilePic;
    String friendsActiveStatus;

    public ActiveUserModel(String friendsId, String friendsFirstName, String friendsLastName,
                           String friendsProfilePic, String friendsActiveStatus)
    {
        this.friendsId = friendsId;
        this.friendsFirstName = friendsFirstName;
        this.friendsLastName = friendsLastName;
        this.friendsProfilePic = friendsProfilePic;
        this.friendsActiveStatus = friendsActiveStatus;
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

    public String getFriendsProfilePic() {
        return friendsProfilePic;
    }

    public void setFriendsProfilePic(String friendsProfilePic) {
        this.friendsProfilePic = friendsProfilePic;
    }

    public String getFriendsActiveStatus() {
        return friendsActiveStatus;
    }

    public void setFriendsActiveStatus(String friendsActiveStatus) {
        this.friendsActiveStatus = friendsActiveStatus;
    }
}