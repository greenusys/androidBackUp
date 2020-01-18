package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.Login_Register_Package.Login;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.avinashdavid.trivialtrivia.UI.ActivityQuiz;
import com.avinashdavid.trivialtrivia.UI.ActivityWelcomePage;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;
import com.avinashdavid.trivialtrivia.questions.QuestionsHandling;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class StartQuiz extends AppCompatActivity {

    //String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

    RecyclerView rv_gd;
    AppController appController;
    All_Quiz_adapter_Subjects solider_gd_adapter;
    All_Quiz_adapter_Topic all_quiz_adapter_topic;

    Subject_Category_Model all_quiz_model;
    Topic_Category_Model topic_category_model;


    ArrayAdapter<String> arrayAdapter;
    MaterialBetterSpinner materialDesignSpinner;

    String subject_id = "", topic_id = "", quiz_name = "";
    TextView topictext;


    List<Subject_Category_Model> soldire_gd_models = new ArrayList<>();
    List<Topic_Category_Model> topicCategoryModels = new ArrayList<>();
    String id = "";

    public static int total_question;

    public static final int INDEX_CATEGORY = 0;
    public static final int INDEX_QUESTION = 0;
    public static final int INDEX_CHOICE_1 = 1;
    public static final int INDEX_CHOICE_2 = 2;
    public static final int INDEX_CHOICE_3 = 3;
    public static final int INDEX_CHOICE_4 = 4;

    public static ArrayList<String> displayList;


    public static final String KEY_ALL_QUESTIONS = "questions";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_QUESTION = "que";
    public static final String KEY_CHOICES = "choices";
    public static final String KEY_CORRECTANSWER = "ans";

    public static ArrayList<IndividualQuestion> mALLIndividualQuestions = new ArrayList<IndividualQuestion>();

    private ArrayList<IndividualQuestion> mCurrentSetOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startquiz);

        getSupportActionBar().setTitle("Start Quiz");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getStringExtra("subject_id") != null && getIntent().getStringExtra("topic_id") != null) {
            subject_id = getIntent().getStringExtra("subject_id");
            topic_id = getIntent().getStringExtra("topic_id");
        }


        if (getIntent().getStringExtra("quiz_name") != null)
            quiz_name = getIntent().getStringExtra("quiz_name");

//        Toast.makeText(appController, "quiz name"+quiz_name, Toast.LENGTH_SHORT).show();


        System.out.println("subid" + subject_id + "topic" + topic_id);


        rv_gd = (RecyclerView) findViewById(R.id.rv);
        appController = (AppController) getApplicationContext();


    }
/*
    public ArrayList<IndividualQuestion> quiz() {

        final ProgressDialog pdLoading = new ProgressDialog(StartQuiz.this);

        appController = (AppController) getApplicationContext();

        RequestBody body = null;
        Request request = null;

        if (quiz_name.equals("quiz_category")) {

            body = new FormBody.Builder().add("subject", subject_id).build();

            request = new Request.Builder().url(URL.quiz_by_subject_and_topic).post(body).build();

        } else if (quiz_name.equals("allquiz")) {


            request = new Request.Builder().url(URL.allquiz).build();

        } else {
            Toast.makeText(appController, "api problem", Toast.LENGTH_SHORT).show();
        }


        pdLoading.setMessage("Loading...");
        pdLoading.show();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                StartQuiz.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
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
                        StartQuiz.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");

                                    if (mALLIndividualQuestions != null) {
                                        mALLIndividualQuestions.clear();
                                    }

                                    if (displayList != null)
                                        displayList.clear();

                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        //  JSONArray aaa = ja.getJSONArray("data");
                                        JSONArray questionsArray = ja.getJSONArray("message");

                                        //total_question=Integer.valueOf(ja.getString("total_question"));

                                        total_question = questionsArray.length();


                                        System.out.println("totalkaif" + total_question);

                                        Log.e("dsf", "sd" + questionsArray);
                                        for (int i = 0; i < questionsArray.length(); i++) {

                                            JSONObject thisQuestion = questionsArray.getJSONObject(i);

                                            //Log.d("total_question", thisQuestion.getString("total_question"));


                                            Log.d("question_id", thisQuestion.getString("question_id"));
                                            Log.d("subject_id", thisQuestion.getString("subject_id"));
                                            Log.d("topic_id", thisQuestion.getString("topic_id"));
                                            Log.d("section_id", thisQuestion.getString("section_id"));
                                            Log.d("que", thisQuestion.getString("que"));
                                            Log.d("ans", thisQuestion.getString("ans"));
                                            Log.d("A", thisQuestion.getString("A"));
                                            Log.d("B", thisQuestion.getString("B"));
                                            Log.d("C", thisQuestion.getString("C"));
                                            Log.d("D", thisQuestion.getString("D"));


                                            String category = "kaif";

                                            String question = thisQuestion.getString(KEY_QUESTION);


                                            String choicesList[] = new String[4];

                                            choicesList[0] = thisQuestion.getString("A");
                                            choicesList[1] = thisQuestion.getString("B");
                                            choicesList[2] = thisQuestion.getString("C");
                                            choicesList[3] = thisQuestion.getString("D");


                                            int correctAnswer = thisQuestion.getInt(KEY_CORRECTANSWER);

                                            IndividualQuestion individualQuestion = new IndividualQuestion(i, category, question, choicesList, correctAnswer);


                                            mALLIndividualQuestions.add(i, individualQuestion);



                               *//* if(displayList!=null)
                                     displayList.clear();*//*

                                            displayList = new ArrayList<>();
                                            //  displayList.add(INDEX_CATEGORY, IndividualQuestion.categoryList.get(thisQuestion.category));
                                            displayList.add(INDEX_QUESTION, individualQuestion.question);
                                            displayList.add(INDEX_CHOICE_1, individualQuestion.choicesList[0]);
                                            displayList.add(INDEX_CHOICE_2, individualQuestion.choicesList[1]);
                                            displayList.add(INDEX_CHOICE_3, individualQuestion.choicesList[2]);
                                            displayList.add(INDEX_CHOICE_4, individualQuestion.choicesList[3]);

                                            //   System.out.println("Categorykaif  "+IndividualQuestion.categoryList.get(individualQuestion.category));
                                            System.out.println("questionkaif  " + individualQuestion.question);
                                            System.out.println("Akaif  " + individualQuestion.choicesList[0]);
                                            System.out.println("A  " + individualQuestion.choicesList[1]);
                                            System.out.println("A  " + individualQuestion.choicesList[2]);
                                            System.out.println("A  " + individualQuestion.choicesList[3]);


                                            //return displayList;


                                        }


                                        System.out.println("datakaif2 " + mALLIndividualQuestions);

                                        ActivityQuiz activityQuiz = new ActivityQuiz();
                                        activityQuiz.kaif(mALLIndividualQuestions);

                                        Intent i = new Intent(StartQuiz.this, ActivityWelcomePage.class);

                                        startActivity(i);


                                    } else {
                                        Toast.makeText(appController, "No Topic Found", Toast.LENGTH_SHORT).show();
                                        // Intent intent=new Intent(Login.this, Login.class);
                                        //startActivity(intent);
                                    }
                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
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


        return mALLIndividualQuestions;

    }*/


   /* Intent i = new Intent(StartQuiz.this, ActivityWelcomePage.class);

    startActivity(i);*/


}
