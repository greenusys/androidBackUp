package com.greenusys.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Forget extends AppCompatActivity {

    EditText edt_forgot;
    LinearLayout next_forgot;
    AppController appController;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edt_forgot = findViewById(R.id.edt_mail_forgot);
        next_forgot = findViewById(R.id.next_forgot);

        next_forgot.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Reg_customer(edt_forgot.getText().toString());
                      //  Toast.makeText(appController, "Sent on Mail", Toast.LENGTH_SHORT).show();
                    }
                });
        appController = (AppController) getApplicationContext();

    }

    private void Reg_customer(String remail) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", remail)
                .build();

        Request request = new Request.Builder().
                url(UrlHelper.Forgetpassord)
                .post(requestBody)
                .build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Forget.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();


                Forget.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            String code = jsonObject.getString("Code");
                            String msg = jsonObject.getString("Message");
                            if (code.equalsIgnoreCase("1")) {
                                message(msg);
                            } else {
                                message(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void message(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg) .setTitle("Mail sent On Your ID");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(new Intent(Forget.this,Loginactivity.class));
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }

}
