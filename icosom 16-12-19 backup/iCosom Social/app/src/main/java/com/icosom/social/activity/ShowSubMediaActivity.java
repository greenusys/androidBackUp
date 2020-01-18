package com.icosom.social.activity;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.icosom.social.R;
import com.icosom.social.pager_adapter.ShowSubMediaPagerAdapter;

import java.util.ArrayList;

public class ShowSubMediaActivity extends AppCompatActivity
{
    ViewPager vp_showSubMedia;
    ArrayList<String> imgPath;
    Boolean isVideo;
    Boolean from_singing;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub_media);
        imgPath = new ArrayList<String>(getIntent().getStringArrayListExtra("imgs"));

        System.out.println("katrina"+imgPath.get(0));

        isVideo = getIntent().getBooleanExtra("isVideos", false);
        pos = getIntent().getIntExtra("position", 0);
        from_singing = getIntent().getBooleanExtra("from_singing", false);

        vp_showSubMedia = findViewById(R.id.vp_showSubMedia);
        vp_showSubMedia.setAdapter(new ShowSubMediaPagerAdapter(getBaseContext(), imgPath, isVideo,from_singing));

        vp_showSubMedia.setCurrentItem(pos);
    }



}