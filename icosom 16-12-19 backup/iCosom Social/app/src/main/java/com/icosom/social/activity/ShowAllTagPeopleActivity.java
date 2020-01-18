package com.icosom.social.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icosom.social.R;
import com.icosom.social.model.TagModel;
import com.icosom.social.recycler_adapter.ShowAllTagPeopleRecyclerAdapter;

import java.util.ArrayList;

public class ShowAllTagPeopleActivity extends AppCompatActivity
{
    RecyclerView rv_showAllTagPerson;
    RecyclerView.LayoutManager layoutManager;
    ShowAllTagPeopleRecyclerAdapter adapter;
    ArrayList<TagModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_tag_people);

        models = getIntent().getParcelableArrayListExtra("tags");

        rv_showAllTagPerson = findViewById(R.id.rv_showAllTagPerson);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new ShowAllTagPeopleRecyclerAdapter(getBaseContext(), models);

        rv_showAllTagPerson.setLayoutManager(layoutManager);
        rv_showAllTagPerson.setAdapter(adapter);
    }
}