package com.icosom.social.model;

import java.util.ArrayList;

public class FeedModel
{
    private String postId;
    private String postType;
    private String postUserId;
    private String postTotalLikes;
    private String postTotalDislikes;
    private String postTotalComments;
    private String postTotalShares;
    private String postDescription;
    private String postImgFlag;
    private ArrayList<String> postFileLists;
    private Boolean postIsShared;
    private String postTime;
    private String postUserFirstName;
    private String postUserLastName;
    private String postUserProfilePicture;
    private Boolean postIsTagged;
    private String postUserGender;

    private Boolean isMyLike;
    private Boolean isMyDislike;
    private Boolean isMyComment;
    private Boolean isMyShare;

    private String sharePostId;
    private String sharePostUserId;
    private String sharePostDescription;
    private String sharePostImgFlag;
    private ArrayList<String> sharePostFileLists;
    private String sharePostTime;
    private String sharePostUserFirstName;
    private String sharePostUserLastName;
    private String sharePostUserProfilePicture;
    private String sharePostUserGender;

    public String getPost_link() {
        return post_link;
    }

    public void setPost_link(String post_link) {
        this.post_link = post_link;
    }

    private String post_link;

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    private String device_token;

    private String tagCount;
    private ArrayList<TagModel> tagList;

    private ArrayList<LikeDislikeModel> postLikesList;
    private ArrayList<LikeDislikeModel> postDislikesList;
   /* */
    public FeedModel(String post_link,String postId, String postType, String postUserId, String postTotalLikes,
                     String postTotalDislikes, String postTotalComments, String postTotalShares,
                     String postDescription, String postImgFlag, ArrayList<String> postFileLists,
                     Boolean postIsShared, String postTime, String postUserFirstName,
                     String postUserLastName, String postUserProfilePicture, Boolean postIsTagged,
                     String postUserGender,
                     Boolean isMyLike, Boolean isMyDislike, Boolean isMyComment, Boolean isMyShare,
                     String sharePostId, String sharePostUserId, String sharePostDescription,
                     String sharePostImgFlag, ArrayList<String> sharePostFileLists,
                     String sharePostTime, String sharePostUserFirstName,
                     String sharePostUserLastName, String sharePostUserProfilePicture,
                     String sharePostUserGender,
                     String tagCount, ArrayList<TagModel> tagList,
                     ArrayList<LikeDislikeModel> postLikesList,
                     ArrayList<LikeDislikeModel> postDislikesList,
                     String device_token


    )
    {
        this.post_link = post_link;
        this.postId = postId;
        this.postType = postType;
        this.postUserId = postUserId;
        this.postTotalLikes = postTotalLikes;
        this.postTotalDislikes = postTotalDislikes;
        this.postTotalComments = postTotalComments;
        this.postTotalShares = postTotalShares;
        this.postDescription = postDescription;
        this.postImgFlag = postImgFlag;
        this.postFileLists = postFileLists;
        this.postIsShared = postIsShared;
        this.postTime = postTime;
        this.postUserFirstName = postUserFirstName;
        this.postUserLastName = postUserLastName;
        this.postUserProfilePicture = postUserProfilePicture;
        this.postIsTagged = postIsTagged;
        this.postUserGender = postUserGender;
        this.isMyLike = isMyLike;
        this.isMyDislike = isMyDislike;
        this.isMyComment = isMyComment;
        this.isMyShare = isMyShare;
        this.sharePostId = sharePostId;
        this.sharePostUserId = sharePostUserId;
        this.sharePostDescription = sharePostDescription;
        this.sharePostImgFlag = sharePostImgFlag;
        this.sharePostFileLists = sharePostFileLists;
        this.sharePostTime = sharePostTime;
        this.sharePostUserFirstName = sharePostUserFirstName;
        this.sharePostUserLastName = sharePostUserLastName;
        this.sharePostUserProfilePicture = sharePostUserProfilePicture;
        this.sharePostUserGender = sharePostUserGender;
        this.tagCount = tagCount;
        this.tagList = tagList;
        this.postLikesList = postLikesList;
        this.postDislikesList = postDislikesList;
        this.device_token = device_token;

    }

    public ArrayList<LikeDislikeModel> getPostLikesList() {
        return postLikesList;
    }

    public void setPostLikesList(ArrayList<LikeDislikeModel> postLikesList) {
        this.postLikesList = postLikesList;
    }

    public ArrayList<LikeDislikeModel> getPostDislikesList() {
        return postDislikesList;
    }

    public void setPostDislikesList(ArrayList<LikeDislikeModel> postDislikesList) {
        this.postDislikesList = postDislikesList;
    }

    public String getPostUserGender() {
        return postUserGender;
    }

    public void setPostUserGender(String postUserGender) {
        this.postUserGender = postUserGender;
    }

    public String getSharePostUserGender() {
        return sharePostUserGender;
    }

    public void setSharePostUserGender(String sharePostUserGender) {
        this.sharePostUserGender = sharePostUserGender;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {
        this.postUserId = postUserId;
    }

    public String getPostTotalLikes() {
        return postTotalLikes;
    }

    public void setPostTotalLikes(String postTotalLikes) {
        this.postTotalLikes = postTotalLikes;
    }

    public String getPostTotalDislikes() {
        return postTotalDislikes;
    }

    public void setPostTotalDislikes(String postTotalDislikes) {
        this.postTotalDislikes = postTotalDislikes;
    }

    public String getPostTotalComments() {
        return postTotalComments;
    }

    public void setPostTotalComments(String postTotalComments) {
        this.postTotalComments = postTotalComments;
    }

    public String getPostTotalShares() {
        return postTotalShares;
    }

    public void setPostTotalShares(String postTotalShares) {
        this.postTotalShares = postTotalShares;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImgFlag() {
        return postImgFlag;
    }

    public void setPostImgFlag(String postImgFlag) {
        this.postImgFlag = postImgFlag;
    }

    public ArrayList<String> getPostFileLists() {
        return postFileLists;
    }

    public void setPostFileLists(ArrayList<String> postFileLists) {
        this.postFileLists = postFileLists;
    }

    public Boolean getPostIsShared() {
        return postIsShared;
    }

    public void setPostIsShared(Boolean postIsShared) {
        this.postIsShared = postIsShared;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostUserFirstName() {
        return postUserFirstName;
    }

    public void setPostUserFirstName(String postUserFirstName) {
        this.postUserFirstName = postUserFirstName;
    }

    public String getPostUserLastName() {
        return postUserLastName;
    }

    public void setPostUserLastName(String postUserLastName) {
        this.postUserLastName = postUserLastName;
    }

    public String getPostUserProfilePicture() {
        return postUserProfilePicture;
    }

    public void setPostUserProfilePicture(String postUserProfilePicture) {
        this.postUserProfilePicture = postUserProfilePicture;
    }

    public Boolean getPostIsTagged() {
        return postIsTagged;
    }

    public void setPostIsTagged(Boolean postIsTagged) {
        this.postIsTagged = postIsTagged;
    }

    public Boolean getMyLike() {
        return isMyLike;
    }

    public void setMyLike(Boolean myLike) {
        isMyLike = myLike;
    }

    public Boolean getMyDislike() {
        return isMyDislike;
    }

    public void setMyDislike(Boolean myDislike) {
        isMyDislike = myDislike;
    }

    public Boolean getMyComment() {
        return isMyComment;
    }

    public void setMyComment(Boolean myComment) {
        isMyComment = myComment;
    }

    public Boolean getMyShare() {
        return isMyShare;
    }

    public void setMyShare(Boolean myShare) {
        isMyShare = myShare;
    }

    public String getSharePostId() {
        return sharePostId;
    }

    public void setSharePostId(String sharePostId) {
        this.sharePostId = sharePostId;
    }

    public String getSharePostUserId() {
        return sharePostUserId;
    }

    public void setSharePostUserId(String sharePostUserId) {
        this.sharePostUserId = sharePostUserId;
    }

    public String getSharePostDescription() {
        return sharePostDescription;
    }

    public void setSharePostDescription(String sharePostDescription) {
        this.sharePostDescription = sharePostDescription;
    }

    public String getSharePostImgFlag() {
        return sharePostImgFlag;
    }

    public void setSharePostImgFlag(String sharePostImgFlag) {
        this.sharePostImgFlag = sharePostImgFlag;
    }

    public ArrayList<String> getSharePostFileLists() {
        return sharePostFileLists;
    }

    public void setSharePostFileLists(ArrayList<String> sharePostFileLists) {
        this.sharePostFileLists = sharePostFileLists;
    }

    public String getSharePostTime() {
        return sharePostTime;
    }

    public void setSharePostTime(String sharePostTime) {
        this.sharePostTime = sharePostTime;
    }

    public String getSharePostUserFirstName() {
        return sharePostUserFirstName;
    }

    public void setSharePostUserFirstName(String sharePostUserFirstName) {
        this.sharePostUserFirstName = sharePostUserFirstName;
    }

    public String getSharePostUserLastName() {
        return sharePostUserLastName;
    }

    public void setSharePostUserLastName(String sharePostUserLastName) {
        this.sharePostUserLastName = sharePostUserLastName;
    }

    public String getSharePostUserProfilePicture() {
        return sharePostUserProfilePicture;
    }

    public void setSharePostUserProfilePicture(String sharePostUserProfilePicture) {
        this.sharePostUserProfilePicture = sharePostUserProfilePicture;
    }

    public String getTagCount() {
        return tagCount;
    }

    public void setTagCount(String tagCount) {
        this.tagCount = tagCount;
    }

    public ArrayList<TagModel> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<TagModel> tagList) {
        this.tagList = tagList;
    }
}