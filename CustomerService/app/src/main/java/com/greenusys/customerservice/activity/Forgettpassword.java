package com.greenusys.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.greenusys.customerservice.utility.UrlHelper.resetpassword;

public class Forgettpassword extends AppCompatActivity {
    EditText ed_email, ed_conf_pass, ed_passwprd, ed_old;
    Button bt_resetpassword;
    AppController appController;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgettpassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        ed_email = findViewById(R.id.edt_mail);
        ed_conf_pass = findViewById(R.id.edt_conf_password);
        ed_passwprd = findViewById(R.id.edt_new_password);
        ed_old = findViewById(R.id.edt_old_password);
        bt_resetpassword = findViewById(R.id.next_forget_BUTTON);

        bt_resetpassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        validate_data(ed_email.getText().toString(),
                                ed_old.getText().toString(), ed_passwprd.getText().toString(),
                                ed_conf_pass.getText().toString());

                    }
                });
        appController = (AppController) getApplicationContext();
    }

    private void resetpassword(String email, String oldpass, String newpass, String confpass) {

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", oldpass)
                .add("newpassword", newpass)
                .add("confirmpassword", confpass)
                .build();

        Request request = new Request.Builder()
                .url(resetpassword)
                .post(requestBody)
                .build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Forgettpassword.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                Forgettpassword.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);

                            //String code = jsonObject.getString("Code");
                            String msg = jsonObject.getString("Message");

                            message(msg);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void validate_data(String email, String password, String new_password, String new_comfirm_password) {


        if (email.equals("")) {
            ed_email.setError("Please Enter Your Email");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed_email.setError("Please Enter Valid Email");
            return;
        } else if (password.equals("")) {
            ed_old.setError("Please Enter Old Password");
            return;
        } else if (new_password.equals("")) {
            ed_passwprd.setError("Please Enter New Password");
            return;
        } else if (new_comfirm_password.equals("")) {
            ed_conf_pass.setError("Please Enter Comfirm Password");
            return;
        } else
            resetpassword(ed_email.getText().toString(), ed_old.getText().toString(), ed_passwprd.getText().toString(), ed_conf_pass.getText().toString());


    }


    private void message(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg).setTitle("Registration");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (msg.equalsIgnoreCase("Congratulations You have successfully changed your password")) {
                            finish();
                            startActivity(new Intent(Forgettpassword.this, MainActivity.class).putExtra("va", "2"));
                        }
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }

}
