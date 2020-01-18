package com.icosom.social.Talent_Show_Package.Modal;

import java.util.ArrayList;

public class Post_Link_Model
{

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public ArrayList<String> files;

    @Override
    public String toString() {
        return "Post_Link_Model{" +
                "files=" + files +
                '}';
    }
}