package com.icosom.social.Talent_Show_Package.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Adapter.GK_Adapter;
import com.icosom.social.Talent_Show_Package.Adapter.Singing_Adapter;
import com.icosom.social.Talent_Show_Package.Modal.Post_Link_Model;
import com.icosom.social.Talent_Show_Package.Modal.Put_Rating_Modal;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;
import com.icosom.social.Talent_Show_Package.Modal.Voting_Modal;
import com.icosom.social.Talent_Show_Package.View_Model.Fetch_Talent_Single_Post_VM;
import com.icosom.social.Talent_Show_Package.View_Model.Put_Rating_View_Modal;
import com.icosom.social.activity.LoginActivity;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.SplashActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Talent_Show_Single_Post extends AppCompatActivity {

    public static ArrayList<String> format_list = new ArrayList<>();
    ArrayList<Voting_Modal> voting_list = new ArrayList<>();
    ArrayList<Post_Link_Model> file_list = new ArrayList<>();
    Singing_Adapter adapter;
    GK_Adapter adapter1;
    RecyclerView recyclerView;
    boolean from_single_post = true;
    private Fetch_Talent_Single_Post_VM viewmodel;
    private String tal_id;
    private ArrayList<Singing_Model.Singing_Model_List> singing_list;
    private Put_Rating_View_Modal put_modal;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent__show__single__post);

        viewmodel = ViewModelProviders.of(this).get(Fetch_Talent_Single_Post_VM.class);
        put_modal = ViewModelProviders.of(this).get(Put_Rating_View_Modal.class);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());




        //if not logged in
        if(sp.getString("userId", "").equals(""))
        {
            startActivity(new Intent(Talent_Show_Single_Post.this, LoginActivity.class));
        }

        else {
            Intent intent = getIntent();
            if (intent != null && intent.getData() != null) {

                Uri uri=intent.getData();



                 String tal_id=uri.getQueryParameter("tal_id");
                 String user_id=uri.getQueryParameter("user_id");
                 final String video=uri.getQueryParameter("video");

                System.out.println("tal_id"+tal_id);
                System.out.println("user_id"+user_id);
                System.out.println("video"+video);




                viewmodel.get_singel_Post(tal_id, user_id).observe(this, new Observer<ArrayList<Singing_Model.Singing_Model_List>>() {
                    @Override
                    public void onChanged(ArrayList<Singing_Model.Singing_Model_List> singing_list2) {

                        System.out.println("data" + singing_list2.size());

                        singing_list = singing_list2;

                        for (int i = 0; i < singing_list2.size(); i++) {
                            Voting_Modal modal = new Voting_Modal();

                            modal.setTootal_Good(singing_list2.get(i).getGood());
                            modal.setTootal_V_Good(singing_list2.get(i).getVgood());
                            modal.setTotal_Bad(singing_list2.get(i).getBad());
                            modal.setTotal_V_Bad(singing_list2.get(i).getVbad());


                            if (singing_list2.get(i).getRanks().equals("good")) {
                                modal.setGood_Rank(true);

                            } else if (singing_list2.get(i).getRanks().equals("vgood")) {
                                modal.setV_Good_Rank(true);
                            } else if (singing_list2.get(i).getRanks().equals("bad")) {
                                modal.setBad_Rank(true);

                            } else if (singing_list2.get(i).getRanks().equals("vbad")) {
                                modal.setV_Bad_Rank(true);
                            }
                            voting_list.add(modal);
                        }


                        System.out.println("kaif_check" + video);
                        if (video.equals("video"))
                            setUp_Audio_Video_Data();
                        else
                            setUp_Image_Data();


                    }


                });

            }

        }
    }


    //only for audio and video
    private void setUp_Audio_Video_Data() {

        System.out.println("Audio_Video_Called");
        for (int i = 0; i < singing_list.size(); i++) {
            ArrayList<String> postFileLinks = new ArrayList<>();

            for (String str : singing_list.get(i).getData().split(",")) {

                //if(!str.equals(""))
                postFileLinks.add(str);
            }


            Post_Link_Model model = new Post_Link_Model();
            model.setFiles(postFileLinks);
            file_list.add(model);

        }


        for (int position = 0; position < file_list.size(); position++) {
            if (!file_list.get(position).getFiles().get(0).equals("")) {
                int length = file_list.get(position).getFiles().get(0).length();
                String str = file_list.get(position).getFiles().get(0);

                String extention = str.substring(length - 4);
                System.out.println("extention");

                if (extention.equals(".mp3") || extention.equals(".m4a") || extention.equals(".pcm") ||
                        extention.equals(".3gp") || extention.equals(".wav") || extention.equals(".ogg"))
                    format_list.add("music");

                else
                    format_list.add("video");
            } else
                format_list.add("empty");

            System.out.println("format_list" + format_list.get(position).toString());
        }


        System.out.println("file_list" + file_list);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Singing_Adapter(from_single_post, this, getApplicationContext(), voting_list, singing_list, file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        for (int i = 0; i < format_list.size(); i++)
            System.out.println("format_kaif" + format_list.get(i));


    }


    public void put_rating(String tal_id, String rating) {

        System.out.println("put_rating_method_" + rating);

        put_modal.put_Rating(MainActivity.user_id, tal_id, rating).observe(this, new Observer<Put_Rating_Modal>() {
            @Override
            public void onChanged(@Nullable Put_Rating_Modal s) {

            }
        });

    }


    //only for image
    private void setUp_Image_Data() {

        System.out.println("image_called");

        for (int i = 0; i < singing_list.size(); i++) {
            ArrayList<String> postFileLinks = new ArrayList<>();

            for (String str : singing_list.get(i).getData().split(",")) {
                //if(!str.equals(""))
                postFileLinks.add(str);
            }

            Post_Link_Model model = new Post_Link_Model();
            model.setFiles(postFileLinks);
            file_list.add(model);
        }
        recyclerView = findViewById(R.id.recycler_view);
        adapter1 = new GK_Adapter(true, this, getApplicationContext(), voting_list, singing_list, file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Talent.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

    public void back_activity(View view) {
            startActivity(new Intent(getApplicationContext(), Talent.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));


    }
}
