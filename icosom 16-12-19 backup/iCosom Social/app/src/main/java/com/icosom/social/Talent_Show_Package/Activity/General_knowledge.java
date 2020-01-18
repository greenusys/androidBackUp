package com.icosom.social.Talent_Show_Package.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Adapter.GK_Adapter;
import com.icosom.social.Talent_Show_Package.Modal.Post_Link_Model;
import com.icosom.social.Talent_Show_Package.Modal.Put_Rating_Modal;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;
import com.icosom.social.Talent_Show_Package.Modal.Voting_Modal;
import com.icosom.social.Talent_Show_Package.View_Model.Put_Rating_View_Modal;
import com.icosom.social.Talent_Show_Package.View_Model.Singing_Talent_Fetch_Post_VM;
import com.icosom.social.activity.MainActivity;

import java.util.ArrayList;

public class General_knowledge extends AppCompatActivity {


    String value="";
    TextView title_text;
    GK_Adapter adapter;
    RecyclerView recyclerView;
    private FloatingActionButton gallery_fab;
    ArrayList<Post_Link_Model> file_list = new ArrayList<>();
    ArrayList<Voting_Modal> voting_list = new ArrayList<>();



    private ArrayList<Singing_Model.Singing_Model_List> singing_list;
    private ArrayList<Singing_Model.Singing_Model_List> singing_list_original;
    SwipeRefreshLayout swipe_ref;
    Singing_Talent_Fetch_Post_VM Viewmodel;
    boolean from_swipe;
    FrameLayout data_layout;
    LinearLayout no_data_found_layout;
    private Put_Rating_View_Modal put_modal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk__add__images);

        value=getIntent().getStringExtra("value");

        initViews();
        fetch_Singin_Posts(from_swipe);
        swiperefresh_Listener();

    }

    private void initViews() {

        Viewmodel = ViewModelProviders.of(this).get(Singing_Talent_Fetch_Post_VM.class);
        put_modal = ViewModelProviders.of(this).get(Put_Rating_View_Modal.class);

        recyclerView=findViewById(R.id.recycler_view);
        swipe_ref=findViewById(R.id.swipe_ref);
        title_text=findViewById(R.id.title_text);
        gallery_fab=findViewById(R.id.gallery_fab);

        no_data_found_layout=findViewById(R.id.no_data_found_layout);
        data_layout=findViewById(R.id.data_layout);

    }

    private void fetch_Singin_Posts(boolean from_swipe) {

        Viewmodel.getSinginPost(value, MainActivity.user_id,from_swipe).observe(this, new Observer<ArrayList<Singing_Model.Singing_Model_List>>() {
            @Override
            public void onChanged(ArrayList<Singing_Model.Singing_Model_List> singing_list2) {

                final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_left_to_right);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();

                singing_list = singing_list2;
                setDataToRecyclerView();



                if(singing_list.size()>0) {
                    gone_no_data_layout();
                    setDataToRecyclerView();

                }
                else
                    visible_no_data_layout();

                System.out.println("sallu"+singing_list.size());
                for (int i = 0; i < singing_list2.size(); i++) {

                    Voting_Modal modal = new Voting_Modal();

                    modal.setTootal_Good(singing_list2.get(i).getGood());
                    modal.setTootal_V_Good(singing_list2.get(i).getVgood());
                    modal.setTotal_Bad(singing_list2.get(i).getBad());
                    modal.setTotal_V_Bad(singing_list2.get(i).getVbad());


                    if (singing_list2.get(i).getRanks().equals("good")) {
                        modal.setGood_Rank(true);

                    }


                    else if (singing_list2.get(i).getRanks().equals("vgood")) {
                        modal.setV_Good_Rank(true);
                    }


                    else if (singing_list2.get(i).getRanks().equals("bad")) {
                        modal.setBad_Rank(true);

                    }


                    else if (singing_list2.get(i).getRanks().equals("vbad")) {
                        modal.setV_Bad_Rank(true);
                    }
                    voting_list.add(modal);


                }

            }
        });





    }

    private void visible_no_data_layout()
    {
        no_data_found_layout.setVisibility(View.VISIBLE);
        data_layout.setVisibility(View.GONE);
    }


    private void gone_no_data_layout()
    {
        no_data_found_layout.setVisibility(View.GONE);
        data_layout.setVisibility(View.VISIBLE);
    }


    private void setDataToRecyclerView() {
        for(int i=0;i<singing_list.size();i++)
        {
            ArrayList<String> postFileLinks = new ArrayList<>();

            for (String str : singing_list.get(i).getData().split(",")) {
                //if(!str.equals(""))
                postFileLinks.add(str);
            }

            Post_Link_Model model=new Post_Link_Model();
            model.setFiles(postFileLinks);
            file_list.add(model);


        }

        System.out.println("file_list"+file_list);
        adapter=new GK_Adapter(false,this, getApplicationContext(),voting_list,singing_list,file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipe_ref.setRefreshing(false);
    }


    private void swiperefresh_Listener() {
        swipe_ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(singing_list!=null ) {
                    file_list.clear();
                    singing_list.clear();
                }

                from_swipe=true;
                fetch_Singin_Posts(from_swipe);

            }
        });
    }

    public void upload_gk_video(View view) {

        startActivity(new Intent(getApplicationContext(),GK_Add_Image.class).putExtra("value","comedy"));

    }

    public void put_rating(String tal_id, String rating) {

        MediaPlayer.create(getApplicationContext(), R.raw.beep3).start();

        System.out.println("put_rating_method_" + rating);

        put_modal.put_Rating(MainActivity.user_id, tal_id, rating).observe(this, new Observer<Put_Rating_Modal>() {
            @Override
            public void onChanged(@Nullable Put_Rating_Modal s) {

            }
        });

    }

    public void share_Post(String url) {

        System.out.println("me_link"+url);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //String shareBodyText = "Your shearing message goes here";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Icosom ");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    public void back_activity(View view) {
        startActivity(new Intent(getApplicationContext(), Talent.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }
}
