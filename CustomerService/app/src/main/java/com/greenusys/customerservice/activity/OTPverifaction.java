package com.greenusys.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OTPverifaction extends AppCompatActivity {
    EditText ed_opt;
    Button bt_otp;
    String emails,otp;
     AppController appController;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverifaction);
        appController = (AppController) getApplicationContext();


        emails = getIntent().getStringExtra("email");
        //String otp = getIntent().getStringExtra("otp");
        //emails = getIntent().getStringExtra("email");
        ed_opt = findViewById(R.id.otpreg);
        bt_otp = findViewById(R.id.otpbt);

        bt_otp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otp = ed_opt.getText().toString();
                        if (otp.equalsIgnoreCase(otp)) {
                            otpsend(emails,otp);
                            //Toast.makeText(OTPverifaction.this, "Sucessfully", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(OTPverifaction.this,Loginactivity.class));
                            // new MainActivity().Scourse().execute();
                        } else {
                            Toast.makeText(OTPverifaction.this, "otp not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
    private  void otpsend(String emails , String otp)
    {
        RequestBody requestBody = new FormBody.Builder()
                .add("email",emails)
                .add("otp",otp)
                .build();

        Request request = new Request.Builder().
                url("http://eyematic.in/newdashboard5/php/otp_validate.php")
                .post(requestBody)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                OTPverifaction.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();


                try {
                    final JSONObject jsonObject = new JSONObject(myResponse);
                    Log.e("ccc1","hh"+jsonObject);
                    String code=jsonObject.getString("Code");
                    String Message=jsonObject.getString("Message");
                    System.out.println("code_kaif "+code);
                     System.out.println("msg_kaif "+Message);
                    if(code.equals("1"))
                    {

                        if(OTPverifaction.this!=null) {
                            OTPverifaction.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    message(Message);
                                }
                            });
                        }


                    }
                    else
                    {
                        if(OTPverifaction.this!=null) {
                            OTPverifaction.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    message(Message);
                                }
                            });
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }


    private void message(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg) .setTitle("OTP Verification");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(msg.equalsIgnoreCase("OTP verified successfully")) {
                            finish();
                            startActivity(new Intent(OTPverifaction.this, MainActivity.class).putExtra("va", "2"));
                        }
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }

}
