package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class Report extends AppCompatActivity {
    CommonFunctions urlHelper;
    AppController appController;
    EditText comment,report;
    String squestion,scomment;
    TextView sub;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String user_id;
    private String url;
    ProgressBar progressBar;
    int i =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        squestion = getIntent().getStringExtra("customerMobile");
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        user_id = sp.getString("userId", "");
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        progressBar = findViewById(R.id.transfer2);
        comment = findViewById(R.id.icomment);
        report = findViewById(R.id.iquestion);
       
        sub = findViewById(R.id.isubmit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // squestion = comment.getText().toString();
                squestion = report.getText().toString();
                scomment = comment.getText().toString();
               

                url = urlHelper.Report;
                Log.e("talent", "onPostExecute: " + url);
                setData();
            }
        });


        //setData();

    }



    private void setData() {
        if(i==0) {
            new Report.SignIn1().execute(user_id,squestion,scomment);
            progressBar.setVisibility(View.VISIBLE);
            i=1;
        }
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "userId";
        private String value_b = "";
        private String key_c = "question";
        private String value_c = "";
        private String key_d = "comment";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.Report;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_c = strings[1];
            value_d = strings[2];

            Log.e("back", "doInBackground: " + value_a + value_c + value_d);
            body = com.icosom.social.utility.RequestBuilder.fourParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);

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
                try {
                    Log.e("Report", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String statuss = rootObject.getString("status");

                    if (statuss.equalsIgnoreCase("1")) {

                        Toast.makeText(Report.this, "SUCCESSFULL SENT" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Report.this, MainActivity.class));

                    }
                    if (statuss.equalsIgnoreCase("0")) {
                        Toast.makeText(Report.this, "UNSUCCESS" , Toast.LENGTH_SHORT).show();

                    }
                   
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Report.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(Report.this, MainActivity.class));
    }
}


