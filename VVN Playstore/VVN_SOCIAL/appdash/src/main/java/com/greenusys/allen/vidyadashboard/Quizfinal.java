package com.greenusys.allen.vidyadashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quizfinal extends AppCompatActivity {
    int reds=1;
    int tls=0;
    int nmm =0;
    int totalnumber = 0;
    int number,btpoint =6;
    private DatabaseHelper_Dash databaseHelper;
    private final AppCompatActivity activity = Quizfinal.this;
    List<String> ques;
    List<String> ans;
    List<String> c1;
    List<String> c2;
    List<String> c3;
    List<String> c4;

    String url = "https://vvn.city/apps/jain/quiz.php";
    String urls = "https://vvn.city/apps/jain/ques.php";
    String urlss = "https://vvn.city/apps/jain/score.php";
    TextView nq, qu;
    int num = 1;
    String help;
    int score = 0;
    int start = 1;
    int t = 1;

    int duration, total_question, per_marks, negative;
    String emails;
    String value;
    int  i;
    ProgressDialog pd;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    int j = 0;

    String answer;
    Button r1, r2, r3, r4;
    TextView tv;

    String a, b, c, d;
    ImageView next;
    Edit edit;
    Button b1, finsh;
    CountDownTimer Counter1;
    String type1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizfinal);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        type1 = (String) extras.get("type");
        databaseHelper = new DatabaseHelper_Dash(activity);
        progress();
        edit = new Edit();
        edit = databaseHelper.getUser();
        emails = edit.getEmail();
        ques = new ArrayList<>();
        ans = new ArrayList<>();
        c1 = new ArrayList<>();
        c2 = new ArrayList<>();
        c3 = new ArrayList<>();
        c4 = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("DA", "onResponse: " + response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Log.e("DA", "onResponse: " + jsonObject);

                    a = jsonObject.getString("duration");
                    b = jsonObject.getString("total_question");
                    c = jsonObject.getString("per_mark");
                    d = jsonObject.getString("negative_marks");
                    duration = Integer.parseInt(a);
                    total_question = Integer.parseInt(b);
                    per_marks = Integer.parseInt(c);
                    negative = Integer.parseInt(d);

                    duration=duration*1000;
                    totalnumber = total_question*per_marks;
                    Log.e("QA", "onResponse: "+duration );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quizfinal.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", type1);
                return params;

            }
        };


        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);


        StringRequest stringRequests = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrays = new JSONArray(response);
                    Log.e("DA", "onResponse: " + response);
                    for(int i = 0 ; i<jsonArrays.length();i++) {
                        JSONObject jsonObject = jsonArrays.getJSONObject(i);


                        Log.e("DA", "onResponse: " + jsonObject);
                        String que, an, o1, o2, o3, o4;
                        que = jsonObject.getString("question");
                        o1 = jsonObject.getString("a");
                        o2 = jsonObject.getString("b");
                        o3 = jsonObject.getString("c");
                        o4 = jsonObject.getString("d");
                        an = jsonObject.getString("answer");
                        ques.add(que);
                        ans.add(an);
                        c1.add(o1);
                        c2.add(o2);
                        c3.add(o3);
                        c4.add(o4);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quizfinal.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("type", type1);
                return params;
            }
        };


        MySingleton.getInstance(getApplication()).addToRequestque(stringRequests);

        qu = (TextView) findViewById(R.id.question);
        b1 = (Button) findViewById(R.id.next);
        r1 = (Button) findViewById(R.id.c1);
        r2 = (Button) findViewById(R.id.c2);
        r3 = (Button) findViewById(R.id.c3);
        r4 = (Button) findViewById(R.id.c4);
        nq = (TextView) findViewById(R.id.question_no);
        qu.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r3.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        nq.setVisibility(View.GONE);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint =1;
                r1.setBackgroundResource(R.drawable.select);
                r2.setBackgroundResource(R.drawable.buttonq);
                r3.setBackgroundResource(R.drawable.buttonq);
                r4.setBackgroundResource(R.drawable.buttonq);


            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint =2;
                r1.setBackgroundResource(R.drawable.buttonq);
                r2.setBackgroundResource(R.drawable.select);
                r3.setBackgroundResource(R.drawable.buttonq);
                r4.setBackgroundResource(R.drawable.buttonq);


            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint =3;
                r1.setBackgroundResource(R.drawable.buttonq);
                r2.setBackgroundResource(R.drawable.buttonq);
                r3.setBackgroundResource(R.drawable.select);
                r4.setBackgroundResource(R.drawable.buttonq);


            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btpoint =4;
                r1.setBackgroundResource(R.drawable.buttonq);
                r2.setBackgroundResource(R.drawable.buttonq);
                r3.setBackgroundResource(R.drawable.buttonq);
                r4.setBackgroundResource(R.drawable.select);


            }
        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders//LoaderManager loaderManager = getLoaderManager();


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  pre.setVisibility(View.VISIBLE);
                    qu.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r3.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.VISIBLE);
                    nq.setVisibility(View.VISIBLE);

                    if (j < total_question){
                        b1.setText("NEXT >>>");
                    }
                    num = 1;
                    if (start == 1) {

                        //   tv=(TextView)findViewById(R.id.timer);


                        Counter1=new CountDownTimer(duration, 1000) {

                            public void onTick(long millisUntilFinished) {
                                // tv.setText("" + millisUntilFinished / 1000);
                                nq.setText("QUESTION #" + number + "   "+"TIME LEFT : " + "" + millisUntilFinished / 1000);
                            }

                            public void onFinish() {



                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Quizfinal.this);
                                builder1.setMessage("Time Out!" + ".....Your score is "+tls + " out of "+ totalnumber);
                                builder1.setCancelable(false);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                                next();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                if (!(isFinishing()))
                                {
                                    alert11.show();}

                            }
                        };
                        Counter1.start();

                        //bl.setText("NEXt");
                        //  b.setVisibility(View.GONE);
                        updated();
                        start++;
                    }
                    else if(j>=total_question){
                        point();
                        done();
                    }
                    else {

                        updated();
                        int fin = total_question;
                        point();
                        if(j>=fin){
                            b1.setText("FINISH >>>");
                        }

                    }

                }
            });


        } else {
            help = "No Internet Connectivity";
            error();
        }
    }

    private void point() {
        int xxx = j-1;
        String done =ans.get(xxx);
        if(done.equals("A")){
            nmm = 1;


        }
        if(done.equals("B")){
            nmm = 2;

        }
        if(done.equals("C")){
            nmm = 3;

        }
        if(done.equals("D")){
            nmm = 4;

        }

        if(btpoint == nmm){
            tls = tls+per_marks;
            reds = reds+1;
        }
        else {
            tls = tls - negative;
        }
        btpoint =6;
        xxx++;
    }

    private void complete() {
        Intent i = new Intent(Quizfinal.this, ServerList.class);
        i.putExtra("key", score);
        startActivityForResult(i, 1);
        startActivity(i);
        finish();

    }
    private void done() {
        Counter1.cancel();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Quizfinal.this);
        builder1.setMessage("You have successfully completed your quiz.....Your score is "+tls + " out of "+ totalnumber);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        next();


                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void error() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Quizfinal.this);
        builder1.setMessage(help);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        Intent i = new Intent(Quizfinal.this, Quizfinal.class);
                        startActivity(i);
                        finish();
                        t = 1;

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void updated() {
        color();
        num++;
        number = j + 1;
        if (j < total_question) {


            qu.setText(ques.get(j));
            r1.setText(c1.get(j));
            r2.setText(c2.get(j));
            r3.setText(c3.get(j));
            r4.setText(c4.get(j));
            answer = ans.get(j);
            answer = answer.toUpperCase();
        }
        if(j>=total_question){
            done();
        }
        j++;
        start ++;
    }

    private void color() {
        r1.setBackgroundResource(R.drawable.buttonq);
        r2.setBackgroundResource(R.drawable.buttonq);
        r3.setBackgroundResource(R.drawable.buttonq);
        r4.setBackgroundResource(R.drawable.buttonq);

    }

    private void next() {
        Counter1.cancel();
        StringRequest stringRequestss = new StringRequest(Request.Method.POST, urlss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrays = new JSONArray(response);
                    Log.e("DA", "onResponse: " + response);
                   /* for(int i = 0 ; i<jsonArrays.length();i++) {
                        JSONObject jsonObject = jsonArrays.getJSONObject(i);

                        //  Toast.makeText(Test.this, c1.get(i), Toast.LENGTH_LONG).show();
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent ib8 = new Intent(Quizfinal.this, ServerList.class);
                startActivity(ib8);
                finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quizfinal.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", emails);
                params.put("marks",""+ tls);
                params.put("reward", ""+reds);
                return params;
            }
        };


        MySingleton.getInstance(getApplication()).addToRequestque(stringRequestss);
    }


    public void progress()
    {
        pd = new ProgressDialog(Quizfinal.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setTitle("Please Wait");
        pd.setMessage("Loading.........");

        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(false);

        pd.show();

        // Set the progress status zero on each button click
        progressStatus = 0;

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Update the progress status
                            pd.setProgress(progressStatus);

                        }
                    });
                }
            }
        }).start(); // Start the operation
    }
}








