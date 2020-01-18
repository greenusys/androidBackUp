package com.example.g116.vvn_social.User_Profile_Dashboard_Activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.g116.vvn_social.Adapter.ShowFriendListRecyclerAdapter;

import com.example.g116.vvn_social.Modal.Friend_List_Model;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import java.util.ArrayList;

public class Show_All_Friends_List extends AppCompatActivity {

    private ArrayList<Friend_List_Model> friend_list;
    SwipeRefreshLayout srl_showFriendRequest;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ShowFriendListRecyclerAdapter showFriendListRecyclerAdapter;
    AppController appController;
    String title="VVN CITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all__friends__list);

        friend_list = new ArrayList<>();
        friend_list = (ArrayList<Friend_List_Model>) getIntent().getSerializableExtra("friend_list");
        title = getIntent().getStringExtra("title");

        this.getSupportActionBar().setTitle(title);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        Log.e("friend_list","skfj"+friend_list.size());


        appController = (AppController)getApplicationContext();
        recyclerView = findViewById(R.id.rv_showFriendList);//recycler view
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showFriendListRecyclerAdapter = new ShowFriendListRecyclerAdapter(getApplicationContext(),friend_list);
        recyclerView.setAdapter(showFriendListRecyclerAdapter);
        showFriendListRecyclerAdapter.notifyDataSetChanged();

    }
}
