package com.icosom.social.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.icosom.social.fragment.TransactionHistoryFragment;

public class PassbookPagerAdapter extends FragmentStatePagerAdapter {

    public PassbookPagerAdapter(FragmentManager fm)//, int NumOfTabs)
    {
        super(fm);
        //this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TransactionHistoryFragment tab1 = new TransactionHistoryFragment();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
