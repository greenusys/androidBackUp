package com.avinashdavid.trivialtrivia.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Dashboard;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.StartQuiz;
import com.avinashdavid.trivialtrivia.URL;
import com.avinashdavid.trivialtrivia.all_quiz_category;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityInstructions extends AppCompatActivity {
    String subject_id2 = "", subject_value = "";

    RecyclerView rv_gd;
    AppController appController;
    All_Quiz_adapter_Subjects solider_gd_adapter;
    All_Quiz_adapter_Topic all_quiz_adapter_topic;

    Subject_Category_Model all_quiz_model;
    Topic_Category_Model topic_category_model;


    ArrayAdapter<String> arrayAdapter;
    MaterialBetterSpinner materialDesignSpinner;

    // String subject_id = "", topic_id = "", quiz_name = "";
    TextView topictext;


    List<Subject_Category_Model> soldire_gd_models = new ArrayList<>();
    List<Topic_Category_Model> topicCategoryModels = new ArrayList<>();
    String id = "";

    public static int total_question;

    //public static final int INDEX_CATEGORY = 0;
    public static final int INDEX_QUESTION = 0;
    public static final int INDEX_CHOICE_1 = 1;
    public static final int INDEX_CHOICE_2 = 2;
    public static final int INDEX_CHOICE_3 = 3;
    public static final int INDEX_CHOICE_4 = 4;

    public static ArrayList<String>     displayList = new ArrayList<>();


    public static final String KEY_ALL_QUESTIONS = "questions";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_QUESTION = "que";
    public static final String KEY_CHOICES = "choices";
    public static final String KEY_CORRECTANSWER = "ans";

    public static ArrayList<IndividualQuestion> mALLIndividualQuestions = new ArrayList<IndividualQuestion>();

    private ArrayList<IndividualQuestion> mCurrentSetOfQuestions;

    String quiz_id, quiz_name, quiz_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //FragmentGameplayInstructions fragmentGameplayInstructions = new FragmentGameplayInstructions();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.container_instructions, fragmentGameplayInstructions).commit();




        if (getIntent().getStringExtra("id") != null)
            quiz_id = getIntent().getStringExtra("id");


        if (getIntent().getStringExtra("quiz_name") != null)
            quiz_name = getIntent().getStringExtra("quiz_name");


        rv_gd = (RecyclerView) findViewById(R.id.rv);
        appController = (AppController) getApplicationContext();

        //Button star_quiz=findViewById(R.id.star_quiz);
        /*star_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             System.out.println("idkaifafter"+subject_id2);
                System.out.println("valuekaif"+subject_value);
            }
        });*/

    }


    public ArrayList<IndividualQuestion> quiz(View view) {

        final ProgressDialog pdLoading = new ProgressDialog(ActivityInstructions.this);

        appController = (AppController) getApplicationContext();

        RequestBody body = null;
        Request request = null;


        if (getIntent().getStringExtra("id") != null && getIntent().getStringExtra("category") != null) {
            quiz_id = getIntent().getStringExtra("id");
            quiz_category = getIntent().getStringExtra("category");


        }


        if (getIntent().getStringExtra("quiz_name") != null && getIntent().getStringExtra("category") != null) {
            quiz_name = getIntent().getStringExtra("quiz_name");
            quiz_category = getIntent().getStringExtra("category");


        }

        System.out.println("Quizzz " + quiz_category);
        System.out.println("Quizzz " + quiz_name);


        if (quiz_category.equals("subject"))
        {


            body = new FormBody.Builder().add("subject", quiz_id).build();

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbysubject.php").post(body).build();

        } else if (quiz_category.equals("topic")) {

            body = new FormBody.Builder().add("topic", quiz_id).build();

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbytopic.php").post(body).build();
        } else if (quiz_category.equals("section")) {
            body = new FormBody.Builder().add("section", quiz_id).build();


            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbysection.php").post(body).build();

        } else if (quiz_category.equals("name")) {
            //  body = new FormBody.Builder().add("section", quiz_id).build();
            body = new FormBody.Builder().add("name", quiz_name).build();


            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_byname.php").post(body).build();

        }

        pdLoading.setMessage("Loading...");
        pdLoading.show();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ActivityInstructions.this.runOnUiThread(new Runnable() {
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
                        ActivityInstructions.this.runOnUiThread(new Runnable() {
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

                                            System.out.println("question_id" + thisQuestion.getString("question_id"));


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


                                            String choicesList[] = new String[5];

                                            choicesList[0] = thisQuestion.getString("A");
                                            choicesList[1] = thisQuestion.getString("B");
                                            choicesList[2] = thisQuestion.getString("C");
                                            choicesList[3] = thisQuestion.getString("D");



                                            int correctAnswer = thisQuestion.getInt(KEY_CORRECTANSWER);

                                            IndividualQuestion individualQuestion = new IndividualQuestion(i, category, question, choicesList, correctAnswer);
                                            mALLIndividualQuestions.add(i, individualQuestion);



                              // if(displayList!=null)
                                    // displayList.clear();



                                            // displayList.add(INDEX_CATEGORY, IndividualQuestion.categoryList.get(thisQuestion.category));
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




                                         ArrayList<String> mCurrentDisplayQuestion= ActivityInstructions.displayList;

                                        for(int i=0;i<mCurrentDisplayQuestion.size();i++)
                                        {
                                            System.out.println("sayed---"+mCurrentDisplayQuestion.get(i));
                                        }


                                        Intent i = new Intent(ActivityInstructions.this, ActivityQuiz.class);

                                        startActivity(i);


                                    } else {
                                        Toast.makeText(appController, "No Topic Found", Toast.LENGTH_SHORT).show();
                                        // Toast.makeText(appController, subject_id2, Toast.LENGTH_SHORT).show();

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

    }


    @Override
    protected void onPause() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityInstructions.this, Dashboard.class);
        startActivity(intent);
    }
}
