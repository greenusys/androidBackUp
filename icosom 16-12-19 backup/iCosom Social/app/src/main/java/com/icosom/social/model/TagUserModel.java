package com.icosom.social.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TagUserModel implements Parcelable {
    String userId;
    String name;
    String profile;
    String cover;

    public TagUserModel(String userId, String name, String profile, String cover)
    {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.cover = cover;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.profile);
        dest.writeString(this.cover);
    }

    protected TagUserModel(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.profile = in.readString();
        this.cover = in.readString();
    }

    public static final Parcelable.Creator<TagUserModel> CREATOR = new Parcelable.Creator<TagUserModel>() {
        @Override
        public TagUserModel createFromParcel(Parcel source) {
            return new TagUserModel(source);
        }

        @Override
        public TagUserModel[] newArray(int size) {
            return new TagUserModel[size];
        }
    };
}