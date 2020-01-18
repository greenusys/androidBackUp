package com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Display_Photos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.example.g116.vvn_social.Adapter.GalleryAdapter;
import com.example.g116.vvn_social.Modal.Image;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;

import java.util.ArrayList;


public class Show_All_Photos extends AppCompatActivity {


    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_photos);


        getSupportActionBar().setTitle("Uploaded Photos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appController = (AppController) getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        images = new ArrayList<>();
     //   if(getIntent().getStringExtra("images_list")!=null)
             images = (ArrayList<Image>) getIntent().getSerializableExtra("images_list");

             Log.e("image_list","skfj"+images.size());

        pDialog = new ProgressDialog(this);

        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if(images.size()!=0)
        mAdapter.notifyDataSetChanged();
        else
            setContentView(R.layout.not_photos_found_layout);


         recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }








}