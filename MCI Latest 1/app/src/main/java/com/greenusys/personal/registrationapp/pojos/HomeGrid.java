package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by personal on 2/21/2018.
 */

public class HomeGrid {

    private int imageSource;
    private String imageDescription;

    public HomeGrid(int imageSource, String imageDescription)
    {
        this.imageDescription = imageDescription;
        this.imageSource = imageSource;
    }

    public int getImageSource()
    {
        return imageSource;
    }

    public String getImageDescription()
    {
        return imageDescription;
    }

}
