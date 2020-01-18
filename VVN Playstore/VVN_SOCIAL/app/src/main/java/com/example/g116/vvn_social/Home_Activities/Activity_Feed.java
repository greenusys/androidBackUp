package com.example.g116.vvn_social.Home_Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.g116.vvn_social.Adapter.Home_Post_Adapter;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Modal.ModelFeed;

import java.util.ArrayList;

public class Activity_Feed extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    Home_Post_Adapter homePostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



    }
}
