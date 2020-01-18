package com.avinashdavid.trivialtrivia.Login_Register_Package;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
/*

import com.greenusys.army_project.AppController;
import com.greenusys.army_project.R;
import com.greenusys.army_project.URL;
*/

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration extends AppCompatActivity {
    EditText email,name,mobile,password,confirm_password;
    Button register;
    AppController appController;
    RadioButton male, female;
    AlertDialog.Builder builder;
    String selectedSuperStar = "male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email_text_view);
        name = findViewById(R.id.name_text_view);

        mobile = findViewById(R.id.number_text_view);

        password = findViewById(R.id.edit_text_password);

        confirm_password = findViewById(R.id.edit_text_confirm_password);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        if (male.isChecked()) {
            selectedSuperStar = male.getText().toString();
        } else if (female.isChecked()) {
            selectedSuperStar = female.getText().toString();
        }

        register = findViewById(R.id.register);

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Buyer_Regcode(email.getText().toString(),
                                name.getText().toString(),
                                mobile.getText().toString(),
                                password.getText().toString(),
                                confirm_password.getText().toString(),
                                selectedSuperStar.toString()
                        );
                    }
                }
        );
        appController = (AppController) getApplicationContext();

    }
    private void Buyer_Regcode(String email,String name,String mobile,String password,String comfirm_password,String gender)
    {
        final ProgressDialog pdLoading = new ProgressDialog(Registration.this);
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("name",name)
                .add("mobile",mobile)
                .add("password",password)
                .add("comfirm_password",comfirm_password)
                .add("gender",gender)
                .build();
        Request request = new Request.Builder().
                url(URL.registration)
                .post(requestBody)
                .build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Registration.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {

                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                Registration.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                  //  Toast.makeText(Registration.this, "1", Toast.LENGTH_SHORT).show();

                                  final  JSONObject ja= new JSONObject(myResponse);

                                    //Toast.makeText(Registration.this, "2", Toast.LENGTH_SHORT).show();

                                    String stauts = ja.getString("status");
                                    String message = ja.getString("message");
                                    if (stauts.equalsIgnoreCase("3"))
                                    {
                                        message(message);
                                    }
                                    else
                                    {
                                        message(message);
                                    }

                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        });
    }

    private void message(String msg) {
        builder = new AlertDialog.Builder(this);

        builder.setMessage(msg) .setTitle("Registration");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       // startActivity(new Intent(Registration.this,Login.class).putExtra("Buyer_Reg_email",ed_buy_email.getText().toString()));
                        startActivity(new Intent(Registration.this,OTP_Verficiation.class).putExtra("Buyer_Reg_email",email.getText().toString()));

                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }
}
