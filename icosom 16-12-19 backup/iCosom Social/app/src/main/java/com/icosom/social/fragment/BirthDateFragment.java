package com.icosom.social.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.activity.LoginActivity;
import com.icosom.social.activity.WebActivity;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.RequestBody;

public class BirthDateFragment extends Fragment {


    CommonFunctions urlHelper;
    EditText edt_password;
    EditText edt_confirmPassword;
    AppController appController;
    ProgressBar pb_next;
    TextView txt_next;
    LinearLayout lay_next;
    String type;
    Calendar calendar;
    Boolean showPassword = false;
    Boolean showConfirmPassword = false;
    String firstName,lastName,email_phone,email_number_type,mobile_OTP;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_birth_date, container, false);

        appController = (AppController) getActivity().getApplicationContext();
        urlHelper = new CommonFunctions();
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();


        edt_password = v.findViewById(R.id.edt_password);
        edt_confirmPassword = v.findViewById(R.id.edt_confirmPassword);
        pb_next = v.findViewById(R.id.pb_next);
        txt_next = v.findViewById(R.id.txt_next);
        lay_next = v.findViewById(R.id.lay_next);

        pb_next.setVisibility(View.GONE);
        txt_next.setVisibility(View.VISIBLE);
        lay_next.setClickable(true);

        firstName = getArguments().getString("firstName");
        lastName = getArguments().getString("lastName");
        email_phone = getArguments().getString("email_number");
        email_number_type = getArguments().getString("email_number_type");
        type = getArguments().getString("type");
        mobile_OTP = getArguments().getString("mobile_OTP");

        calendar = Calendar.getInstance();

        lay_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_next.setVisibility(View.VISIBLE);
                String pass=edt_password.getText().toString();
                String Cpass = edt_confirmPassword.getText().toString();

                if (edt_password.getText().toString().isEmpty()) {
                    edt_password.setError("Enter Password");
                    pb_next.setVisibility(View.GONE);
                    return;
                } else if (edt_confirmPassword.getText().toString().isEmpty()) {
                    edt_confirmPassword.setError("Enter Confirm Password");
                    pb_next.setVisibility(View.GONE);
                    return;
                } else  {
                    if (pass.equalsIgnoreCase(Cpass)) {
                        new BirthDateFragment.SignIn1().execute(firstName, lastName, email_phone, email_number_type, mobile_OTP,pass,Cpass );
                    }
                    else {
                        edt_confirmPassword.setError("Password and confirm passsword doesnt match");
                        pb_next.setVisibility(View.GONE);
                    }
                }


            }
        });


        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_password.getRight() - edt_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showPassword) {
                            edt_password.setTransformationMethod(new PasswordTransformationMethod());
                            edt_password.setSelected(false);
                        } else {
                            edt_password.setTransformationMethod(new HideReturnsTransformationMethod());
                            edt_password.setSelected(true);
                        }
                        showPassword = !showPassword;
                        return true;
                    }
                }
                return false;
            }
        });

        edt_confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_confirmPassword.getRight() - edt_confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showConfirmPassword) {
                            edt_confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                            edt_confirmPassword.setSelected(false);
                        } else {
                            edt_confirmPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                            edt_confirmPassword.setSelected(true);
                        }
                        showConfirmPassword = !showConfirmPassword;
                        return true;
                    }
                }
                return false;
            }
        });

        ((TextView) v.findViewById(R.id.txt_termsConditions)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WebActivity.class).
                        putExtra("url", "https://greenusys.website/icosom/social/main/termsOfUse.php").
                        putExtra("title1", "Terms").
                        putExtra("title2", "Conditions"));
            }
        });

        return v;
    }

    /* private class SignIn1 extends AsyncTask<String, Void, String> {

         private String key_a = "data-source";
         private String value_a = "android";
         private String key_b = "firstName";
         private String value_b = "";
         private String key_c = "lastName";
         private String value_c = "";
         private String key_d = "email_number";
         private String value_d = "";
         private String key_e = "email_number_type";
         private String value_e = "";
         private String key_f = "mobile_otp";
         private String value_f = "";
         private String key_g = "password";
         private String value_g = "";
         private String key_h = "confirm_password";
         private String value_h = "";
         private String key_i = "email_verified";
         private String value_i = "1";
         private String key_j = "otp_verfied";
         private String value_j = "0";

         private String response;
         private RequestBody body;
         private String url = urlHelper.register_step3;

         @Override
         protected String doInBackground(String... strings) {
             value_b = strings[0];
             value_c = strings[1];
             value_d = strings[2];
             value_e = strings[3];
             value_f = strings[4];
             value_g = strings[5];
             value_h = strings[6];


             Log.e("back", "doInBackground: " + value_a + value_c + value_d+value_e+value_f+value_g+value_h+value_i+value_j);
             body = RequestBuilder.tenParameter(
                     key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d,key_e, value_e, key_f, value_f, key_g, value_g, key_h, value_h,key_i, value_i, key_j, value_j);

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
                     Log.e("RESSS", "onPostExecute: " + response);
                     JSONObject rootObject = new JSONObject(response);
                     String status = rootObject.getString("status");
                     if (status.equalsIgnoreCase("1")) {
                         Intent i = new Intent(getActivity(), LoginActivity.class);
                         startActivity(i);
                         ((Activity) getActivity()).overridePendingTransition(0,0);
                     }
                     else {
                         Toast.makeText(getActivity(), ""+rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                     }


                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }
     }*/
    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "firstName";
        private String value_b = "";
        private String key_c = "lastName";
        private String value_c = "";
        private String key_d = "email_number";
        private String value_d = "";
        private String key_e = "email_number_type";
        private String value_e = "";
        private String key_f = "mobile_otp";
        private String value_f = "";
        private String key_g = "password";
        private String value_g = "";
        private String key_h = "confirm_password";
        private String value_h = "";
        private String key_i = "email_verified";
        private String value_i = "1";
        private String key_j = "otp_verfied";
        private String value_j = "0";

        private String response;
        private RequestBody body;
        private String url = urlHelper.register_step3;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_c = strings[1];
            value_d = strings[2];
            value_e = strings[3];
            value_f = strings[4];
            value_g = strings[5];
            value_h = strings[6];


            Log.e("back", "doInBackground: " + value_a + value_c + value_d+value_e+value_f+value_g+value_h+value_i+value_j);
            body = RequestBuilder.tenParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d,key_e, value_e, key_f, value_f, key_g, value_g, key_h, value_h,key_i, value_i, key_j, value_j);

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
                    Log.e("RESSS", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String status = rootObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "You are successfully register", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        ((Activity) getActivity()).overridePendingTransition(0,0);
                    }
                    else {
                        Toast.makeText(getActivity(), ""+rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    pb_next.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}