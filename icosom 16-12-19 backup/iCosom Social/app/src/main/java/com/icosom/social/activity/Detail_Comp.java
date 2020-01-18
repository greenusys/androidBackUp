package com.icosom.social.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class Detail_Comp extends AppCompatActivity {

    CommonFunctions urlHelper;
    AppController appController;
    SharedPreferences sp;
    TextView comp_name, rules, s_date, l_date, first, second, third;
    String id;
    String comp_name2;
    Button part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__comp);

        comp_name2 = getIntent().getStringExtra("comp_name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(comp_name2);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        appController = (AppController) getApplication();
        urlHelper = new CommonFunctions();
        comp_name = findViewById(R.id.text_comp_name);
        rules = findViewById(R.id.text_rules);
        s_date = findViewById(R.id.text_s_date);
        l_date = findViewById(R.id.text_l_date);
        first = findViewById(R.id.text_first_prize);
        second = findViewById(R.id.text_second_prize);
        third = findViewById(R.id.text_third_prize);
        id = getIntent().getStringExtra("id");

        setData();
        part = findViewById(R.id.participate_button);
        part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new Detail_Comp.SignIn().execute();
                msg();

            }
        });
    }

    private void msg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("To participate .visit Icosom Website \n https://icosom.com/social/main/")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(Detail_Comp.this, MainActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Icosom Talent");
        alert.show();

    }

    private void setData() {
        new Detail_Comp.SignIn1().execute();
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "userId";
        private String value_b = sp.getString("userId", "");
        private String key_c = "comp_id";
        private String value_c = id;


        private String response;
        private RequestBody body;
        private String url = urlHelper.talent_2;

        @Override
        protected String doInBackground(String... strings) {


            Log.e("back", "doInBackground: " + value_a);
            body = RequestBuilder.threeParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("talent", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("talent", "onPostExecute: " + response);
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        comp_name.setText(jsonObject.getString("comp_name"));
                        rules.setText("Rules  : " + jsonObject.getString("rules"));
                        s_date.setText("Start Date : " + jsonObject.getString("s_date"));
                        l_date.setText("Last Date : " + jsonObject.getString("l_date"));
                        first.setText("First Prize : " + jsonObject.getString("first_prize"));
                        second.setText("Second Prize : " + jsonObject.getString("second_prize"));
                        third.setText("Third Prize : " + jsonObject.getString("third_prize"));


                    }
                   /* String status = rootObject.getString("status");*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SignIn extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "userId";
        private String value_b = sp.getString("userId", "");
        private String key_c = "comp_id";
        private String value_c = id;


        private String response;
        private RequestBody body;
        private String url = urlHelper.talent_3;

        @Override
        protected String doInBackground(String... strings) {


            Log.e("back", "doInBackground: " + value_a);
            body = RequestBuilder.threeParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("talent", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("talent", "onPostExecute: " + response);


                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(Detail_Comp.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();



                   /* String status = rootObject.getString("status");*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

