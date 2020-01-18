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
public class DislikesFragment extends Fragment {
    RecyclerView rv_dislikes;
    RecyclerView.LayoutManager llm;
    LikeDislikeRecyclerAdapter adapter;


    public DislikesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dislikes, container, false);
        // Inflate the layout for this fragment
        ArrayList<LikeDislikeModel> dislikesList = getArguments().getParcelableArrayList("dislikeList");
        System.out.println("DislikeList: " + dislikesList);
        rv_dislikes = v.findViewById(R.id.rv_dislikes);
        llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_dislikes.setLayoutManager(llm);
        adapter = new LikeDislikeRecyclerAdapter(getContext(), dislikesList);
        rv_dislikes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

}
