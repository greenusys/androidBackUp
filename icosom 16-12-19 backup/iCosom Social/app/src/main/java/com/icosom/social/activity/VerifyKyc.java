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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerifyKyc extends AppCompatActivity {
    String dataa;
    int i =0;
    SharedPreferences sp;
    AppController appController;
    String selfId;
    LinearLayout otp,datas;
    EditText edts,kyc_otp;
    String otps;
    LinearLayout kyc_next,btn_finish;
    String uid,name,yob,gender,street,location,post_office,district,sub_district,state,pinCode,dob,mobile;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyKyc.this, MainActivity.class));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_kyc);

        appController = (AppController) getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        selfId = sp.getString("userId", "1");
        //dataa = getIntent().getStringExtra("userId");
      //  Toast.makeText(this, ""+dataa, Toast.LENGTH_SHORT).show();
        Log.e("kyc", "onCreate: "+dataa );
        Log.e("selfId", "selfId: "+selfId );
        JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(selfId);
            JSONObject jsonObject=jsonObj.getJSONObject("PrintLetterBarcodeData");
            uid = jsonObject.getString("uid");
            name = jsonObject.getString("name");
            yob = jsonObject.getString("yob");
            gender = jsonObject.getString("gender");
            street = jsonObject.getString("street");
            location = jsonObject.getString("lm");
            post_office = jsonObject.getString("po");
            district = jsonObject.getString("dist");
            sub_district = jsonObject.getString("subdist");
            state = jsonObject.getString("state");
            pinCode = jsonObject.getString("pc");
            dob = jsonObject.getString("dob");


            Log.d("JSON", jsonObject.toString());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
        }

//        Log.d("XML", dataa);

        Log.d("JSON", jsonObj.toString());
        otp = findViewById(R.id.otp);
        datas=findViewById(R.id.datas);
        kyc_otp = findViewById(R.id.edt_kyc_otp);
        edts = findViewById(R.id.edt_kyc_phone);
        btn_finish = findViewById(R.id.btn_finish_kyc);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ED_OTP = kyc_otp.getText().toString().trim();
                if (ED_OTP.equalsIgnoreCase(""))
                {
                    //  progressDialog.dismiss();
                    Toast.makeText(VerifyKyc.this, "Enter otp", Toast.LENGTH_SHORT).show();
                } else if ((ED_OTP.length()) < 6) {
                    //  progressDialog.dismiss();
                    Toast.makeText(VerifyKyc.this, "Enter 6 digit otp", Toast.LENGTH_SHORT).show();
                }

                else if(ED_OTP.equalsIgnoreCase(otps)){
                    finishs();
                }
                else {
                    Toast.makeText(VerifyKyc.this, "otp not match try again", Toast.LENGTH_SHORT).show();
                    kyc_otp.setText("");
                }
            }
        });
        kyc_next = findViewById(R.id.btn_kyc);
        kyc_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = edts.getText().toString().trim();


                if (mobile.equalsIgnoreCase("")) {
                    //  progressDialog.dismiss();
                    Toast.makeText(VerifyKyc.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                } else if ((mobile.length()) < 10) {
                    //  progressDialog.dismiss();
                    Toast.makeText(VerifyKyc.this, "Enter 10 digit phone number", Toast.LENGTH_SHORT).show();
                }

                else {
                   getDatas();

               //  new VerifyKyc.SignIn1().execute();
                }
            }
        });


    }

    private void getDatas() {

        JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(selfId);
            JSONObject jsonObject=jsonObj.getJSONObject("PrintLetterBarcodeData");
            uid = jsonObject.getString("uid");
            Log.e("JSON exception", uid);
            name = jsonObject.getString("name");
            Log.e("JSON exception", name);
            yob = jsonObject.getString("yob");
            Log.e("JSON exception", yob);
            gender = jsonObject.getString("gender");
            Log.e("JSON exception", gender);
            street = jsonObject.getString("street");
            Log.e("JSON exception", street);
            location = jsonObject.getString("lm");
            Log.e("JSON exception",location);
            post_office = jsonObject.getString("po");
            Log.e("JSON exception", post_office);
            district = jsonObject.getString("dist");
            Log.e("JSON exception", district);
            sub_district = jsonObject.getString("subdist");
            Log.e("JSON exception", sub_district);
            state = jsonObject.getString("state");
            Log.e("JSON exception", state);
            pinCode = jsonObject.getString("pc");
            Log.e("JSON exception", pinCode);
            dob = jsonObject.getString("dob");
            Log.e("JSON exception",dob);
            if(uid.equalsIgnoreCase("")){
                getDatas();
            }else {
                fetchNotification();
            }
            Log.d("JSON", jsonObject.toString());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
        }
    }

    private void fetchNotification()
    {
       RequestBody body = new FormBody.Builder().
                add("user_id",selfId).
                add("uid",uid).
                add("name",name).
                add("yob",yob).
                add("gender",gender).
                add("street",street).
                add("location",location).
                add("post_office",post_office).
                add("district",district).
                add("sub_district",sub_district).
                add("state",state).
                add("pinCode",pinCode).
                add("dob",dob).
                add("mobile",mobile).
                build();

        Log.e("plansss", "fetchNotification: "+body );
        Request request = new Request.Builder().
                url("https://icosom.com/social/main/KYC_API.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                VerifyKyc.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VerifyKyc.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e("plansss", "onResponse: "+response );
                String myResponse = response.body().string();

                Log.e("plansss", "onResponse: "+myResponse );

                try {
                    JSONObject jsonObject = new JSONObject(myResponse);
                    String code = jsonObject.getString("code");
                    if(code.equalsIgnoreCase("1")) {
                        //String msg = jsonObject.getString("msg");
                        otps = jsonObject.getString("OTP");
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                datas.setVisibility(View.GONE);
                                otp.setVisibility(View.VISIBLE);
                            }
                        });


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }



    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "user_id";
        private String value_a = selfId;
        private String key_b = "uid";
        private String value_b = uid;
        private String key_c = "name";
        private String value_c = name;
        private String key_d = "yob";
        private String value_d = yob;
        private String key_e = "gender";
        private String value_e = gender;
        private String key_f = "street";
        private String value_f = street;
        private String key_g = "location";
        private String value_g = location;
        private String key_h = "post_office";
        private String value_h = post_office;
        private String key_i = "district";
        private String value_i = district;
        private String key_j = "sub_district";
        private String value_j = sub_district;
        private String key_k = "state";
        private String value_k = state;
        private String key_l = "pinCode";
        private String value_l = pinCode;
        private String key_m = "dob";
        private String value_m = dob;
        private String key_n = "mobile";
        private String value_n =mobile;

        private String response;
        private RequestBody body;
        private String url = "https://icosom.com/wallet/main/KYC_API.php";

        @Override
        protected String doInBackground(String... strings) {
           // value_b = strings[0];
          //  value_d = strings[1];


            Log.e("back", "doInBackground: " + value_a + value_d);
            body = RequestBuilder.kycParameter(
                    key_a, value_a, key_b, value_b, key_c,value_c,key_d, value_d,key_e,value_e,key_f,value_f,key_g,value_g,key_h,value_h,key_i,value_i,key_j,value_j,key_k,value_k,key_l,value_l,key_m,value_m,key_n,value_n);

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
                    Log.e("planss", "onPostExecute: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if(code.equalsIgnoreCase("1")) {
                        //String msg = jsonObject.getString("msg");
                        otps = jsonObject.getString("OTP");
                        datas.setVisibility(View.GONE);
                        otp.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    private void finishs()
    {
        RequestBody body = new FormBody.Builder().
                add("user_id",selfId).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/KYC_status.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                VerifyKyc.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VerifyKyc.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();

                Log.e("plansss", "onResponse: "+myResponse );

                try {
                    JSONObject jsonObject = new JSONObject(myResponse);
                    String code = jsonObject.getString("code");
                    if(code.equalsIgnoreCase("1")) {
                      //  Toast.makeText(VerifyKyc.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerifyKyc.this,MainActivity.class));

                    }
                    else {
                        Toast.makeText(VerifyKyc.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }
}
