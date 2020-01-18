package com.icosom.social.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Greenusys on 25-07-2019.
 */

public class Videostore_model {

    String name;
    private ArrayList<String> postFileLists;
    private String user_profile_name;
    private String user_post_date;
    private String profilepic;

    public Videostore_model(String name, ArrayList<String> postFileLists, String user_profile_name, String user_post_date, String profilepic) {
        this.name = name;
        this.postFileLists = postFileLists;
        this.user_profile_name = user_profile_name;
        this.user_post_date = user_post_date;
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPostFileLists() {
        return postFileLists;
    }

    public void setPostFileLists(ArrayList<String> postFileLists) {
        this.postFileLists = postFileLists;
    }

    public String getUser_profile_name() {
        return user_profile_name;
    }

    public void setUser_profile_name(String user_profile_name) {
        this.user_profile_name = user_profile_name;
    }

    public String getUser_post_date() {
        return user_post_date;
    }

    public void setUser_post_date(String user_post_date) {
        this.user_post_date = user_post_date;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}














   /* public Videostore_model(String name, ArrayList<String> postFileLists,String user_profile_name,String user_post_date, String profilepic) {
        this.name = name;
        this.postFileLists = postFileLists;
        this.profilepic = profilepic;
        this.user_profile_name = user_profile_name;
        this.user_post_date = user_post_date;
    }

    public String getUser_profile_name() {
        return user_profile_name;
    }

    public String getUser_post_date() {
        return user_post_date;
    }

    public String getProfilepic() {
        return profilepic;
    }



    public ArrayList<String> getPostFileLists() {
        return postFileLists;
    }







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
*/