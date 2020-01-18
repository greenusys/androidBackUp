package com.greenusys.personal.registrationapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.pojos.HomeGrid;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Staff__HomeFragment extends Fragment {

    private static int currentPge = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.image_one,R.drawable.image_two,R.drawable.image_three};

    private static ViewPager carouselVp;

    private static CirclePageIndicator circleIndicator;

    private static final ArrayList<Integer> imagesList = new ArrayList<>();

    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    public Staff__HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_staff_home, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.home_rv);
        mAdapter = new HomeAdapter(getActivity(),getGridObjectList());

        //mRecyclerView.setHasFixedSize(true);

       // mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFocusable(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mRecyclerView.setAdapter(mAdapter);

        init(rootView);
        return rootView;
    }

    private List<HomeGrid> getGridObjectList()
    {
        List<HomeGrid> homeGridList = new ArrayList<>();

        homeGridList.add(new HomeGrid(R.mipmap.course,"Checking Assignment"));
       // homeGridList.add(new HomeGrid(R.mipmap.test,"News"));
        homeGridList.add(new HomeGrid(R.mipmap.time,"Uploading Assignment"));
        homeGridList.add(new HomeGrid(R.mipmap.galery,"Self Attendence Check"));
        homeGridList.add(new HomeGrid(R.mipmap.test,"Finishing Chapter On Time"));
        homeGridList.add(new HomeGrid(R.mipmap.test,"Time Table"));
        homeGridList.add(new HomeGrid(R.mipmap.test,"For Every New Admission"));
        homeGridList.add(new HomeGrid(R.mipmap.test,"Completing Telecalling Target of Month"));

      //  homeGridList.add(new HomeGrid(R.mipmap.test,"Reward"));
        //homeGridList.add(new HomeGrid(R.mipmap.test,"Attendance"));
      //  homeGridList.add(new HomeGrid(R.mipmap.test,"assignment"));


        return homeGridList;

    }

    private void init(View rootView)
    {


        for(int i=0; i<IMAGES.length; i++) {

            if(imagesList.size() != IMAGES.length) {
                imagesList.add(IMAGES[i]);
            }
        }


        carouselVp = (ViewPager)rootView.findViewById(R.id.carousel_vp);

        carouselVp.setAdapter(new SlidingImagesAdapter(getActivity(),imagesList));

        circleIndicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);

        circleIndicator.setViewPager(carouselVp);

        final float density = getResources().getDisplayMetrics().density;

        circleIndicator.setRadius(5*density);

        NUM_PAGES = IMAGES.length;

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPge == NUM_PAGES)
                {
                    currentPge = 0;
                }

                carouselVp.setCurrentItem(currentPge++,true);
            }
        };

        Timer swipeTimer = new Timer();

        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },3000, 3000);

        circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                currentPge = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}
