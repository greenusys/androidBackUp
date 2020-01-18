package com.icosom.social.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 09-04-2018.
 */

public class LikeDislikeModel implements Parcelable {
    private String firstName;
    private String lasttName;
    private String userId;
    private String profilePicture;

    public LikeDislikeModel() {
    }

    protected LikeDislikeModel(Parcel in) {
        firstName = in.readString();
        lasttName = in.readString();
        userId = in.readString();
        profilePicture = in.readString();
    }

    public static final Creator<LikeDislikeModel> CREATOR = new Creator<LikeDislikeModel>() {
        @Override
        public LikeDislikeModel createFromParcel(Parcel in) {
            return new LikeDislikeModel(in);
        }

        @Override
        public LikeDislikeModel[] newArray(int size) {
            return new LikeDislikeModel[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasttName() {
        return lasttName;
    }

    public void setLasttName(String lasttName) {
        this.lasttName = lasttName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lasttName);
        dest.writeString(userId);
        dest.writeString(profilePicture);
    }
}
