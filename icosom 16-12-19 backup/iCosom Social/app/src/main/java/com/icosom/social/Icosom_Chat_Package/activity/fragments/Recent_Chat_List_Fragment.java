package com.icosom.social.Icosom_Chat_Package.activity.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.airbnb.lottie.LottieAnimationView;
import com.icosom.social.Icosom_Chat_Package.activity.Adapter.Recent_Chat_List_Adapter;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.Chat_Main_Activity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Friend_List_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Recent_Chat_Model;
import com.icosom.social.Icosom_Chat_Package.activity.View_Model.Fetch_Recent_Chat_List_VM;
import com.icosom.social.R;
import com.icosom.social.utility.AppController;

import java.util.ArrayList;


public class Recent_Chat_List_Fragment extends Fragment {


    private AppController appController;
    private RecyclerView recyclerView;
    private SearchView search;
    private LinearLayoutManager layoutManager;
    LinearLayout no_friend_layout, main_layout, loading_layout, search_layout;
    LottieAnimationView loading_anim;
    private Recent_Chat_List_Adapter adapter;
    private ArrayList<Friend_List_Model> friend_list_models = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("third");

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_recent_chat_list_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        appController = (AppController) getContext().getApplicationContext();
        recyclerView = view.findViewById(R.id.rv_search);//recycler view
        search = view.findViewById(R.id.search_view);
        search_layout = view.findViewById(R.id.search_layout);
        main_layout = view.findViewById(R.id.main_layout);
        no_friend_layout = view.findViewById(R.id.no_friend_layout);
        loading_layout = view.findViewById(R.id.loading_layout);
        loading_anim = view.findViewById(R.id.loading_anim);

        loading_layout.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Fetch_Recent_Chat_List_VM Viewmodel = ViewModelProviders.of(this).get(Fetch_Recent_Chat_List_VM.class);


        Viewmodel.getRecentChat(Chat_Main_Activity.userID).observe(this, new Observer<ArrayList<Recent_Chat_Model>>() {
            @Override
            public void onChanged(ArrayList<Recent_Chat_Model> todo_list) {

              //set animation on recycler view
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_left_to_right);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();


                if(todo_list!=null) {
                    adapter = new Recent_Chat_List_Adapter(getContext(), todo_list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    loading_layout.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);
                    no_friend_layout.setVisibility(View.GONE);
                }
                else
                {
                   loading_layout.setVisibility(View.GONE);
                    main_layout.setVisibility(View.GONE);
                    no_friend_layout.setVisibility(View.VISIBLE);
                }


            }
        });


        //fetch_FriendList(Chat_Main_Activity.userID);
        search_text_listener();


    }

    private void search_text_listener() {
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();//open search box
            }
        });

        search.setQueryHint("Search Friends");
        search.setMaxWidth(Integer.MAX_VALUE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }


}