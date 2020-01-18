package com.icosom.social.pager_adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.icosom.social.fragment.HowToOneFragment;
import com.icosom.social.fragment.HowToThreeFragment;
import com.icosom.social.fragment.HowToTwoFragment;


public class HowToPagerAdapter extends FragmentPagerAdapter
{
    public HowToPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return new HowToOneFragment();
        }
        else if (position==1)
        {
            return new HowToTwoFragment();
        }
        else
        {
            return new HowToThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}