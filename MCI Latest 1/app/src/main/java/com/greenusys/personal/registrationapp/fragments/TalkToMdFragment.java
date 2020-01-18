package com.greenusys.personal.registrationapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

/**
 * Created by personal on 2/20/2018.
 */

public class TalkToMdFragment extends Fragment {

    Button sendButtom;
    EditText name, email, number, message;
    UrlHelper urlHelper;
    AppController appController;

    public TalkToMdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.talk_to_md, container, false);
        appController = (AppController) getContext().getApplicationContext();
        name = rootView.findViewById(R.id.name_edit_text);
        email = rootView.findViewById(R.id.email_edit_text);
        number = rootView.findViewById(R.id.number_edit_text);
        message = rootView.findViewById(R.id.message_edit_text);
        urlHelper = new UrlHelper();

        sendButtom = (Button) rootView.findViewById(R.id.send_button);

        sendButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String names = name.getText().toString();
                String emails = email.getText().toString();
                String numbers = number.getText().toString();
                String messages = message.getText().toString();
                if (emails.length() == 0) {
                    email.setError("Email is not entered");
                    email.requestFocus();
                } else if (names.length() == 0) {
                    name.setError("Password is not entered");
                    name.requestFocus();
                } else if (numbers.length() == 0) {
                    number.setError("Number is not entered");
                    number.requestFocus();
                } else if (messages.length() == 0) {
                    message.setError("Message is not entered");
                    message.requestFocus();
                } else {
                    new TalkToMdFragment.SignIn().execute(names,emails,numbers,messages);
                /*Intent in = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(in);
                finish();*/
                }
            }
        });
        return rootView;
    }


    private class SignIn extends AsyncTask<String, Void, String> {

        private String key_a = "name";
        private String value_a = "";
        private String key_b = "email";
        private String value_b = "";
        private String key_c = "number";
        private String value_c = "";
        private String key_d = "message";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.talkMd;

        @Override
        protected String doInBackground(String... strings) {

            value_a = strings[0];
            value_b = strings[1];
            value_c = strings[2];
            value_d = strings[3];
            Log.e("back", "doInBackground: " + value_a + value_b);
            body = RequestBuilder.fourParameter(
                    key_a, value_a, key_b, value_b,key_c, value_c, key_d, value_d);
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

            if (s != null) {
                try {

                    JSONObject jsonObject = new JSONObject(response);


                    String CODE = jsonObject.getString("status");
                    Log.e("RES", "onPostExecute: " + CODE + s);
                    Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    name.setText("");
                    number.setText("");
                    message.setText("");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}