package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class Course_detials extends AppCompatActivity {

    private AppController appController;
    UrlHelper urlhelper;
    Button forgot;
    String emails;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detials);
        appController = (AppController) getApplicationContext();

        new Course_detials.SignIn1().execute();
    }
    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "classId";
        private String value_b = urlhelper.classId ;
        private String response;
        private RequestBody body;
        private String url = urlhelper.course;
        private String urls = urlhelper.cour;

        @Override
        protected String doInBackground(String... strings) {

            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.twoParameter(
                    key_a, value_a, key_b, value_b);
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
                Log.e("RESSS", "onPostExecute: " + response);
                try {
                    int i= 0;
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("RESSS", "onPostExecute: " + jsonArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
