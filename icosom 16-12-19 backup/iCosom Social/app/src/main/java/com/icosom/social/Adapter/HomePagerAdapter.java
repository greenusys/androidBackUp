package com.icosom.social.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.icosom.social.Main_trans;
import com.icosom.social.fragment.AtomsFragment;
import com.icosom.social.fragment.RechargeFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public HomePagerAdapter(FragmentManager fm)//, int NumOfTabs)
    {
        super(fm);
        //this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RechargeFragment tab1 = new RechargeFragment();
                return tab1;
            case 1:
                AtomsFragment tab2 = new AtomsFragment();
                return tab2;
            case 2:
                Main_trans tab3 = new Main_trans();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3 ;
    }
}
