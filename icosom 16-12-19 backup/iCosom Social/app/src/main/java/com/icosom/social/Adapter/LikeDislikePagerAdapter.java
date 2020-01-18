package com.icosom.social.Adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.icosom.social.fragment.DislikesFragment;
import com.icosom.social.fragment.LikesFragment;
import com.icosom.social.model.LikeDislikeModel;

import java.util.ArrayList;

public class LikeDislikePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private  ArrayList<LikeDislikeModel> likesList;
    private  ArrayList<LikeDislikeModel> dislikesList;


    public LikeDislikePagerAdapter(FragmentManager fm,  ArrayList<LikeDislikeModel> likesList,  ArrayList<LikeDislikeModel> dislikesList)//, int NumOfTabs)
    {
        super(fm);
        this.likesList = likesList;
        this.dislikesList = dislikesList;
        //this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LikesFragment tab1 = new LikesFragment();
                Bundle argsLike = new Bundle();
                argsLike.putParcelableArrayList("likeList", likesList);
                tab1.setArguments(argsLike);

                return tab1;
            case 1:
                DislikesFragment tab2 = new DislikesFragment();
                Bundle argsDislike = new Bundle();
                argsDislike.putParcelableArrayList("dislikeList", dislikesList);
                tab2.setArguments(argsDislike);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
