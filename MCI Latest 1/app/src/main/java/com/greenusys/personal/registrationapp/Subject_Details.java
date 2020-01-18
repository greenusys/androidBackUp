package com.greenusys.personal.registrationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.Adapter.Subject_Category_Adapter;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.fragments.HomeAdapter;
import com.greenusys.personal.registrationapp.pojos.HomeGrid;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Subject_Details extends AppCompatActivity {
    private AppController appController;
    Subject_Modal subject_modal;

    ArrayList<Subject_Modal> subjects_list= new ArrayList<>();
    Subject_Category_Adapter subject_adapter;
    RecyclerView mRecyclerView;
    String sub_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__details);


if(getIntent().getStringExtra("sub_id")!=null)
 sub_id=getIntent().getStringExtra("sub_id");



        mRecyclerView = (RecyclerView)findViewById(R.id.subject_rv);
        subject_adapter = new Subject_Category_Adapter(sub_id,getApplicationContext(),getGridObjectList());

        //mRecyclerView.setHasFixedSize(true);

        // mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFocusable(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        mRecyclerView.setAdapter(subject_adapter);





    }



    private List<HomeGrid> getGridObjectList()
    {
        List<HomeGrid> homeGridList = new ArrayList<>();

        homeGridList.add(new HomeGrid(R.mipmap.course,"Study Notes"));
        homeGridList.add(new HomeGrid(R.mipmap.course,"Online Test"));
        homeGridList.add(new HomeGrid(R.mipmap.course,"Online Videos"));




        return homeGridList;

    }
}
