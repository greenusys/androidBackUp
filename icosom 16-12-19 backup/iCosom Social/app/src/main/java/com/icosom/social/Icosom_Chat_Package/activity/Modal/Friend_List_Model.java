package com.icosom.social.Icosom_Chat_Package.activity.Modal;

import java.io.Serializable;
import java.util.ArrayList;

public class Friend_List_Model  implements Serializable
{
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    public ArrayList<Friend_List_Model2> data;


    public class  Friend_List_Model2
    {


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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        String firstName;
        String lastName;
        String id;
        String profilePicture;

    }





}