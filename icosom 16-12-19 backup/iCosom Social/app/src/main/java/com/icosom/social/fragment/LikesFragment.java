package com.icosom.social.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icosom.social.R;
import com.icosom.social.model.LikeDislikeModel;
import com.icosom.social.recycler_adapter.LikeDislikeRecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikesFragment extends Fragment {
    RecyclerView rv_likes;
    RecyclerView.LayoutManager llm;
    LikeDislikeRecyclerAdapter adapter;


    public LikesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_likes, container, false);
        // Inflate the layout for this fragment
        ArrayList<LikeDislikeModel> likesList =  getArguments().getParcelableArrayList("likeList");
        System.out.println("LikeList: " + likesList);
        rv_likes = v.findViewById(R.id.rv_likes);
        llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_likes.setLayoutManager(llm);
        adapter = new LikeDislikeRecyclerAdapter(getContext(), likesList);
        rv_likes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

}
