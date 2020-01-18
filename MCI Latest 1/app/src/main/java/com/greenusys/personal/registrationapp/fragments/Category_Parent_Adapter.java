package com.greenusys.personal.registrationapp.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by personal on 2/20/2018.
 */

public class Category_Parent_Adapter extends FragmentPagerAdapter {

    private Context mContext;

    public Category_Parent_Adapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            return new Parent__HomeFragment();
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

            //return new Student();
            return new Parent();
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
            return "Parent";
        }
    }
}
