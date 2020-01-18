package com.icosom.social.Talent_Show_Package.Modal;

import com.icosom.social.Icosom_Chat_Package.activity.Modal.Friend_List_Model;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Singing_Model
{

   public String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Singing_Model_List> data;


    public class  Singing_Model_List
    {

        String tal_id;
        String user_id;
        String text_post;
        String timestamp;
        String data;//video or mp3
        String firstName;
        String lastName;
        String profilePicture;
        String good;
        String vgood;
        String bad;
        String vbad;
        String ranks;
        String url;
        String file_name;//audio file name

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }



        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }



        public String getRanks() {
            return ranks;
        }

        public void setRanks(String ranks) {
            this.ranks = ranks;
        }



        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getVgood() {
            return vgood;
        }

        public void setVgood(String vgood) {
            this.vgood = vgood;
        }

        public String getBad() {
            return bad;
        }

        public void setBad(String bad) {
            this.bad = bad;
        }

        public String getVbad() {
            return vbad;
        }

        public void setVbad(String vbad) {
            this.vbad = vbad;
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

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getTal_id() {
            return tal_id;
        }

        public void setTal_id(String tal_id) {
            this.tal_id = tal_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getText_post() {
            return text_post;
        }

        public void setText_post(String text_post) {
            this.text_post = text_post;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }




}