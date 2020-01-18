package com.icosom.social.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TagModel implements Parcelable {
    private String tagFirstName;
    private String tagLastName;
    private String tagId;

    public TagModel(String tagFirstName, String tagLastName, String tagId)
    {
        this.tagFirstName = tagFirstName;
        this.tagLastName = tagLastName;
        this.tagId = tagId;
    }

    public String getTagFirstName() {
        return tagFirstName;
    }

    public void setTagFirstName(String tagFirstName) {
        this.tagFirstName = tagFirstName;
    }

    public String getTagLastName() {
        return tagLastName;
    }

    public void setTagLastName(String tagLastName) {
        this.tagLastName = tagLastName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tagFirstName);
        dest.writeString(this.tagLastName);
        dest.writeString(this.tagId);
    }

    protected TagModel(Parcel in) {
        this.tagFirstName = in.readString();
        this.tagLastName = in.readString();
        this.tagId = in.readString();
    }

    public static final Parcelable.Creator<TagModel> CREATOR = new Parcelable.Creator<TagModel>() {
        @Override
        public TagModel createFromParcel(Parcel source) {
            return new TagModel(source);
        }

        @Override
        public TagModel[] newArray(int size) {
            return new TagModel[size];
        }
    };
}