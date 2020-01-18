package com.greenusys.personal.registrationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class Quiz extends AppCompatActivity {
    TextView mTextField;
    // ImageView ivGifClock;
    ImageView imgback;
    private AppController appController;
    UrlHelper urlhelper;
    Animation animation;
    String testId;
    List<String> ques;
    List<String> ans;
    List<String> c1;
    List<String> c2;
    List<String> c3;
    List<String> c4;
    int nmm, btpoint;
    CountDownTimer Counter1;
    int j = 0, start = 0;
    long duration;
    int totalmarks, permarks = 5, obtain_marks;
    int total_question = 0;
    Button op1, op2, op3, op4, next;
    TextView question, question_no;
String dur_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        testId = (String) extras.get("id");
        dur_time =  (String) extras.get("time");

//testId=String.valueOf(88*100000);


        duration = Long.parseLong(dur_time);
        duration = duration*1000;
        Log.e("durr", "onCreate: "+duration );

        ques = new ArrayList<>();
        ans = new ArrayList<>();
        c1 = new ArrayList<>();
        c2 = new ArrayList<>();
        c3 = new ArrayList<>();
        c4 = new ArrayList<>();
        question = (TextView) findViewById(R.id.question);
        next = (Button) findViewById(R.id.next);
        op1 = (Button) findViewById(R.id.c1);
        op2 = (Button) findViewById(R.id.c2);
        op3 = (Button) findViewById(R.id.c3);
        op4 = (Button) findViewById(R.id.c4);
        question_no = (TextView) findViewById(R.id.question_no);
        appController = (AppController) getApplicationContext();

        new Quiz.SignIn1().execute(testId);
        mTextField = findViewById(R.id.time_quiz);
        imgback = findViewById(R.id.imgbackss);
        // ivGifClock = findViewById(R.id.ivGifClock);
        //  ivGifClock.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        mTextField.setVisibility(View.GONE);
        op1.setVisibility(View.GONE);
        op2.setVisibility(View.GONE);
        op3.setVisibility(View.GONE);
        op4.setVisibility(View.GONE);
        question_no.setVisibility(View.GONE);
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint = 1;
                op1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select));//.drawable.select);
                op2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));


            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint = 2;
                op1.setBackground(getResources().getDrawable(R.drawable.button_background));
                op2.setBackground(getResources().getDrawable(R.drawable.select));
                op3.setBackground(getResources().getDrawable(R.drawable.button_background));
                op4.setBackground(getResources().getDrawable(R.drawable.button_background));


            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint = 3;
                op1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select));
                op4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));


            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint = 4;
                op1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                op4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select));


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total_question == 0) {
                    bui("You have already giving this  quiz");
                }
                //   ivGifClock.setVisibility(View.VISIBLE);
                else {
                    op1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                    op2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                    op3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                    op4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_background));
                    mTextField.setVisibility(View.VISIBLE);
                    animation = AnimationUtils.loadAnimation(Quiz.this, R.anim.shake);
                    //  ivGifClock.startAnimation(animation);
                    if (start == 0) {
                        new CountDownTimer(duration, 1000) {

                            public void onTick(long millisUntilFinished) {
                                long divisor = millisUntilFinished / 1000;

                                int quotient = (int) (divisor / 60);
                                long remainder = divisor % 60;

                                mTextField.setText(quotient + "min" + remainder + "sec");
                            }

                            public void onFinish() {
                                mTextField.setText("done!");
                            }
                        }.start();

                    }
                    imgback.setVisibility(View.GONE);
                    question.setVisibility(View.VISIBLE);
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    question_no.setVisibility(View.VISIBLE);
                    next.setText("Next >>>");


                    if (j < total_question) {
                        if (j >= 1) {
                            point();
                        }
                        updated();
                    } else if (j >= total_question) {
                        if (total_question != 0) {
                            point();
                            done();
                            next.setText("FINISH >>>");
                        }
                    }

                    start++;
                }
            } });


    }

    private class SignIn extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "testId";
        private String value_b = testId;
        private String key_c = "userId";
        private String value_c = urlhelper.userId;
        private String key_d = "classId";
        private String value_d = urlhelper.classId;
        private String key_e = "obtainMarks";
        int mar = obtain_marks * 5;
        private String value_e = "" + mar;
        private String key_f = "totalMarks";
        private String value_f = "" + totalmarks;
        private String response;
        private RequestBody body;
        private String url = urlhelper.onlineResult;

        @Override
        protected String doInBackground(String... strings) {


            Log.e("back", "doInBackground: " + value_f + "ff" + value_e);
            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.sixParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e, key_f, value_f);
            Log.e("back11111", "doInBackground: " + body);
            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }


        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {

            }

        }
    }


    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "testId";
        private String value_b;
        private String key_c = "userId";
        private String value_c = urlhelper.userId;
        private String key_d = "classId";
        private String value_d = urlhelper.classId;
        private String response;
        private RequestBody body;
        private String url = urlhelper.quiz_question;

        @Override
        protected String doInBackground(String... strings) {

            value_b = strings[0];

            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.fourParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);
            Log.e("back11111", "doInBackground: " + body);
            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }


        @Override
        protected void onPostExecute(String s) {
            total_question = 0;

            if (s != null) {

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("RESSS", "onPostExecute: " + response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        total_question = jsonArray.length();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String que, an, o1, o2, o3, o4;
                        que = jsonObject.getString("question");
                        o1 = jsonObject.getString("ans1");
                        o2 = jsonObject.getString("ans2");
                        o3 = jsonObject.getString("ans3");
                        o4 = jsonObject.getString("ans4");
                        an = jsonObject.getString("correct_ans");
                        ques.add(que);
                        ans.add(an);
                        c1.add(o1);
                        c2.add(o2);
                        c3.add(o3);
                        c4.add(o4);
                     //  String duration = jsonObject.getString("test_time");

                       // Log.e("dur", "onPostExecute: " + duration);
                    /*    duration = duration * 1000;
                        Log.e("dur", "onPostExecute: " + duration);*/
                        totalmarks = total_question * permarks;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


//    private void setData() {
//
//        question.setText(ques.get(0));
//        op1.setText(c1.get(0));
//        op2.setText(c2.get(0));
//        op3.setText(c3.get(0));
//        op4.setText(c4.get(0));
//    }

    private void bui(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Quiz.this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent i = new Intent(Quiz.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void updated() {

        if (total_question != 0) {
            // num++;
            int number = j + 1;

            question_no.setText("Question : " + number);

            question.setText(ques.get(j));
            op1.setText(c1.get(j));
            op2.setText(c2.get(j));
            op3.setText(c3.get(j));

            op4.setText(c4.get(j));
            j++;
            if (j >= total_question) {
                //   point();
                next.setText("FINISH >>>");

            }
            // answer = ans.get(j);
            // answer = answer.toUpperCase();


        }
       else{
         bui("You have already giving this  quiz");
        }
        //start ++;
    }

    private void complete() {
        new Quiz.SignIn().execute();
        Intent i = new Intent(Quiz.this, MainActivity.class);
        startActivity(i);
        finish();

    }

    private void done() {
        // CountDownTimer.cancel();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Quiz.this);
        builder1.setMessage("You have successfully completed your quiz");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        complete();


                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void doness() {
        // CountDownTimer.cancel();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Quiz.this);
        builder1.setMessage("You have already giving this  quiz");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        Intent i = new Intent(Quiz.this, MainActivity.class);
                        startActivity(i);
                        finish();


                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void point() {
        if (total_question != 0) {
        int xxx = j - 1;
        String done = ans.get(xxx);
        if (done.equals("1")) {
            nmm = 1;


        }
        if (done.equals("2")) {
            nmm = 2;

        }
        if (done.equals("3")) {
            nmm = 3;

        }
        if (done.equals("4")) {
            nmm = 4;

        }
        Log.e("marks", "point: " + nmm + xxx);
        if (btpoint == nmm) {
            obtain_marks = obtain_marks + 1;
            Log.e("marks", "point: " + obtain_marks);
        }

        btpoint = 6;
        xxx++;
    }}
}
