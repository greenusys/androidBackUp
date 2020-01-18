package com.example.g116.vvn_social.Modal;

import java.util.ArrayList;

public class Home_Post_Model {



    String post_id;
    String full_name;
    String total_views;
    String totalLikes;
    String totalDislikes;
    String totalComments;
    String totalShares;
    String postTime;
    String post_pic;
     String user_type;
    String education_detail;;
    String post_user_id;

    String user_profile_pic;
    String post_status;
    ArrayList<String> image_list;

    public ArrayList<String> getMylikelist() {
        return mylikelist;
    }

    public void setMylikelist(ArrayList<String> mylikelist) {
        this.mylikelist = mylikelist;
    }

    ArrayList<String> mylikelist;
    private Boolean isMyLike;
    private Boolean isMyDislike;
    Boolean isView;
    String Post_User_Name;

    public String getEducation_detail() {
        return education_detail;
    }

    public void setEducation_detail(String education_detail) {
        this.education_detail = education_detail;
    }



    public Boolean getView() {
        return isView;
    }

    public void setView(Boolean view) {
        isView = view;
    }



    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPost_User_Name() {
        return Post_User_Name;
    }

    public void setPost_User_Name(String post_User_Name) {
        Post_User_Name = post_User_Name;
    }



    public String getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(String post_user_id) {
        this.post_user_id = post_user_id;
    }



    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
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


    public ArrayList<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<String> image_list) {
        this.image_list = image_list;
    }



    public String getUser_profile_pic() {
        return user_profile_pic;
    }

    public void setUser_profile_pic(String user_profile_pic) {
        this.user_profile_pic = user_profile_pic;
    }





    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }



    public String getTotal_views() {
        return total_views;
    }

    public void setTotal_views(String total_views) {
        this.total_views = total_views;
    }

    public String getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getTotalDislikes() {
        return totalDislikes;
    }

    public void setTotalDislikes(String totalDislikes) {
        this.totalDislikes = totalDislikes;
    }

    public String getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(String totalComments) {
        this.totalComments = totalComments;
    }

    public String getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPost_pic() {
        return post_pic;
    }

    public void setPost_pic(String post_pic) {
        this.post_pic = post_pic;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }




}
