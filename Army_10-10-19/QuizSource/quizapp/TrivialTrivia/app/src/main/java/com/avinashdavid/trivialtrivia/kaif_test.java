package com.avinashdavid.trivialtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class kaif_test extends AppCompatActivity {

    private RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaif_test);




        MyRequestQueue = Volley.newRequestQueue(this);

        //  loadHeroList();

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  loadHeroList();




            }
        });


    }


    private void loadHeroList() {


        String url = "http://4army.in/indianarmy/androidapi/apifetch_quizbysubject.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.


                try {
                    final JSONObject ja = new JSONObject(response);

                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        kaif_test.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        //  JSONArray aaa = ja.getJSONArray("data");
                                        JSONArray questionsArray = ja.getJSONArray("questions");

                                        Log.e("dsf", "sd" + questionsArray);
                                        for (int i = 0; i < questionsArray.length(); i++) {


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

                                            //String question = thisQuestion.getString(KEY_QUESTION);

                                            //JSONArray choiceArray = thisQuestion.getJSONArray(KEY_CHOICES);

                                            String choicesList[] = new String[4];

                                            choicesList[0] = thisQuestion.getString("A");
                                            choicesList[1] = thisQuestion.getString("B");
                                            choicesList[2] = thisQuestion.getString("C");
                                            choicesList[3] = thisQuestion.getString("D");


                                            //String[] choicesList = new String[choiceArray.length()];
                                            //for (int l=0; l<choiceArray.length(); l++){
                                            //   choicesList[l] = choiceArray.getString(l);
                                            //}

                                            // int correctAnswer = thisQuestion.getInt(KEY_CORRECTANSWER);

                                            // IndividualQuestion individualQuestion = new IndividualQuestion(i, category,question,choicesList,correctAnswer);
                                            // mALLIndividualQuestions.add(i,individualQuestion);

                                        }


                                        //Intent intent=new Intent(StartQuiz.this,ActivityWelcomePage.class);
                                        // startActivity(intent);


                                    } else {
                                        // Toast.makeText(appController, "No Subject Found", Toast.LENGTH_SHORT).show();
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


        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Toast.makeText(kaif_test.this, "error", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("subject", "2"); //Add the data you'd like to send to the server.
                MyData.put("topic", "2"); //Add the data you'd like to send to the server.

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);


    return ;
}









}
