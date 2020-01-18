package com.greenusys.allen.vidyadashboard.model;

/**
 * Created by Allen on 9/4/2017.
 */
public class ChatItem {
    String profileImageUrl;
    String profileName;
    int profileImage;
    String profile_id;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
}
