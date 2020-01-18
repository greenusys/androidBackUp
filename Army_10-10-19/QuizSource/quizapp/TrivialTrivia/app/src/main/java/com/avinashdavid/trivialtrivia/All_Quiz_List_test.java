package com.avinashdavid.trivialtrivia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;

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

public class All_Quiz_List_test extends AppCompatActivity {

    private AppController appController;
    public static final String KEY_ALL_QUESTIONS = "questions";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_QUESTION = "que";
    public static final String KEY_CHOICES = "choices";
    public static final String KEY_CORRECTANSWER = "ans";
    public static ArrayList<IndividualQuestion> mALLIndividualQuestions  = new ArrayList<IndividualQuestion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__quiz__list_test);



    /* ArrayList<IndividualQuestion> kaif=makeOrReturnMasterQuestionList_kaif();
*/



        makeOrReturnMasterQuestionList_kaif();

        System.out.println("Datakaif1 "+mALLIndividualQuestions);


    }






    public  ArrayList<IndividualQuestion> makeOrReturnMasterQuestionList_kaif()
    {



        appController = (AppController) getApplicationContext();

        RequestBody body = new FormBody.Builder().add("subject", "2").add("topic","2").build();

        Request request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_quizbysubject.php").post(body).build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                All_Quiz_List_test.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);

                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        All_Quiz_List_test.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        //  JSONArray aaa = ja.getJSONArray("data");
                                        JSONArray questionsArray = ja.getJSONArray("questions");

                                        Log.e("dsf","sd"+questionsArray);
                                        for (int i = 0; i < questionsArray.length(); i++)
                                        {

                                            JSONObject thisQuestion = questionsArray.getJSONObject(i);

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


                                            //  String category = thisQuestion.getString(KEY_CATEGORY);
                                            String category = "kaif";

                                            String question = thisQuestion.getString(KEY_QUESTION);

                                            //JSONArray choiceArray = thisQuestion.getJSONArray(KEY_CHOICES);

                                            String choicesList[]=new String[4];

                                            choicesList[0]=thisQuestion.getString("A");
                                            choicesList[1]=thisQuestion.getString("B");
                                            choicesList[2]=thisQuestion.getString("C");
                                            choicesList[3]=thisQuestion.getString("D");


                                            // String[] choicesList = new String[choiceArray.length()];
                                            //for (int l=0; l<choiceArray.length(); l++){
                                             //   choicesList[l] = choiceArray.getString(l);
                                            //}

                                            int correctAnswer = thisQuestion.getInt(KEY_CORRECTANSWER);

                                            IndividualQuestion individualQuestion = new IndividualQuestion(i, category,question,choicesList,correctAnswer);
                                            mALLIndividualQuestions.add(i,individualQuestion);

                                        }







                                        System.out.println("datakaif2 "+ mALLIndividualQuestions);


                                        Intent i=new Intent(All_Quiz_List_test.this,kaif_test.class);
                                        startActivity(i);








                                    }
                                    else
                                    {
                                        Toast.makeText(appController, "No Subject Found", Toast.LENGTH_SHORT).show();
                                        // Intent intent=new Intent(Login.this, Login.class);
                                        //startActivity(intent);
                                    }
                                    //  pdLoading.dismiss();
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



    public  void kaif(View view)

    {

      //  ArrayList<IndividualQuestion> kaif=makeOrReturnMasterQuestionList_kaif();


        System.out.println("size"+ mALLIndividualQuestions.size());
        System.out.println("datakaif"+ mALLIndividualQuestions);



    }






}
