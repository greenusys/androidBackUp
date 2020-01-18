package com.avinashdavid.trivialtrivia;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.avinashdavid.trivialtrivia.Adapter.Quiz_Subject_List_Adapter;
import com.avinashdavid.trivialtrivia.Model.Name_Wise_Quiz;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Quiz_Subject_List extends AppCompatActivity {

    String subject="";
    String quizname="";
    String category="";
    TextView name;
    RecyclerView recyclerView;
    AppController appController;
    List<Name_Wise_Quiz> name_wise_quizs_array = new ArrayList<>();
    List<Name_Wise_Quiz> result = new ArrayList<Name_Wise_Quiz>();
    Quiz_Subject_List_Adapter quiz_subject_list_adapter;
    RelativeLayout anim_layout;
    LinearLayout data_layout;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__subject__list);

        anim_layout=findViewById(R.id.anim_layout);
        data_layout=findViewById(R.id.data_layout);
        lottieAnimationView=findViewById(R.id.lotti);


        recyclerView=findViewById(R.id.rv);
        //name=findViewById(R.id.name);
        appController=(AppController)getApplicationContext();
        quiz_subject_list_adapter=new Quiz_Subject_List_Adapter(this,result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(quiz_subject_list_adapter);


        if(getIntent().getStringExtra("id")!=null) {
            subject = getIntent().getStringExtra("id");
            System.out.println("quiz_subject_list_subject_id"+subject);

        }
        if(getIntent().getStringExtra("name")!=null) {
            quizname = getIntent().getStringExtra("name");
           // name.setText(quizname+" QUIZ");

            getSupportActionBar().setTitle(quizname+" Quizzes");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    if(getIntent().getStringExtra("category")!=null) {
    category = getIntent().getStringExtra("category");


        }

        fetch_subject_name(subject);



    }

    private void fetch_subject_name(final String subject_id) {

        anim_layout.setVisibility(View.VISIBLE);
        data_layout.setVisibility(View.GONE);
        lottieAnimationView.playAnimation();

        Request request = null;
        RequestBody requestBody = null;



        if(category.equals("subject")) {
            requestBody = new FormBody.Builder().add("subject", subject_id).build();

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbysubject.php").post(requestBody).build();

        }
 if(category.equals("topic")) {
     requestBody = new FormBody.Builder().add("topic", subject_id).build();

     request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbytopic.php").post(requestBody).build();

 }
 if(category.equals("section")) {
     requestBody = new FormBody.Builder().add("section", subject_id).build();

     request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbysection.php").post(requestBody).build();

 }



        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Quiz_Subject_List.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                        anim_layout.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Quiz_Subject_List.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        JSONArray aaa = ja.getJSONArray("message");


                                        for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);
                                            Name_Wise_Quiz  name_wise_quiz = new Name_Wise_Quiz(item.getString("name"));
                                            name_wise_quizs_array.add(name_wise_quiz);
                                        }

                                        quiz_subject_list_adapter.notifyDataSetChanged();


                                        Set<String> titles = new HashSet<String>();


                                        for( Name_Wise_Quiz item : name_wise_quizs_array ) {
                                            if( titles.add( item.getName() )) {
                                                result.add( item );
                                            }
                                        }



                                    } else {


                                    }

                                 //  pdLoading.dismiss();
                                    anim_layout.setVisibility(View.GONE);
                                    data_layout.setVisibility(View.VISIBLE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    anim_layout.setVisibility(View.GONE);

                                }
                            }
                        });
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }



}
