package com.icosom.social.fragment;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icosom.social.Adapter.LikeDislikePagerAdapter;
import com.icosom.social.R;
import com.icosom.social.model.LikeDislikeModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeDislikeFragment extends Fragment {

    public ArrayList<LikeDislikeModel> likesList;
    public ArrayList<LikeDislikeModel> dislikesList;

    public LikeDislikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_like_dislike, container, false);
        likesList =  getArguments().getParcelableArrayList("likesList");
        dislikesList =  getArguments().getParcelableArrayList("dislikesList");

        if (likesList.size() != 0){
            System.out.println("LikeDislikeFragment: " + likesList.get(0).getFirstName());
        }
        if (dislikesList.size() != 0){
            System.out.println("LikeDislikeFragment: " + dislikesList.get(0).getFirstName());

        }

        TabLayout tabLayout = v.findViewById(R.id.tab_layout_like_dislike);
        ViewPager viewPager = v.findViewById(R.id.pager_like_dislike);

        final LikeDislikePagerAdapter adapter = new LikeDislikePagerAdapter(getFragmentManager(), likesList, dislikesList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Likes");
        tabLayout.getTabAt(1).setText("Dislikes");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        return  v;
    }

}
