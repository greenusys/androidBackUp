package com.greenusys.personal.registrationapp.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by personal on 2/20/2018.
 */

public class Category_staff_Adapter extends FragmentPagerAdapter {

    private Context mContext;

    public Category_staff_Adapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            return new Staff__HomeFragment();
        } else if(position == 1)
        {
            return new NewsFragment();
        }
        else if(position == 2)
        {
            return new TalkToMdFragment();
        }
        else
        {
            return new Student();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0)
        {
            return "Home";
        }
        else if(position == 1)
        {
            return "News";
        }
        else if(position == 2)
        {
            return "Talk to director";
        }
        else
        {
            return "Student";
        }
    }
}
