package com.icosom.social.model;

/**
 * Created by admin on 08-08-2018.
 */

public class Theme {

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    String img;

    public Theme(String img, String col) {
        this.img = img;
        this.col = col;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    String col;
}
