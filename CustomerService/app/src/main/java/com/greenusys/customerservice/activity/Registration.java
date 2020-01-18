package com.greenusys.customerservice.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class Registration extends AppCompatActivity {
    EditText ed_name, ed_email, ed_conf_pass, ed_passwprd, ed_mobile, ed_address;
    TextView bt_reg;
    AppController appController;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ed_name = findViewById(R.id.edt_name_reg);
        ed_email = findViewById(R.id.edt_email_reg);
        ed_conf_pass = findViewById(R.id.edt_confirm_pass_reg);
        ed_passwprd = findViewById(R.id.edt_pass);
        ed_mobile = findViewById(R.id.edt_phone_regisr);
        ed_address = findViewById(R.id.edt_address_reg);
        bt_reg = findViewById(R.id.buttonReg);

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_data(ed_name.getText().toString(),ed_email.getText().toString(),
                        ed_passwprd.getText().toString(),ed_conf_pass.getText().toString(),
                        ed_mobile.getText().toString(),ed_address.getText().toString());


            }
        });
        appController = (AppController) getApplicationContext();

    }

    private void validate_data(String name, String email, String password, String comfirm_password, String mobile, String address) {
        if(name.equals(""))
        {
            ed_name.setError("Please Enter Name");
            return;
        }
        else if(email.equals(""))
        {
            ed_email.setError("Please Enter Your Email");
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            ed_email.setError("Please Enter Valid Email");
            return;
        }
        else if(password.equals(""))
        {
            ed_passwprd.setError("Please Enter Password");
            return;
        }
        else if(comfirm_password.equals(""))
        {
            ed_conf_pass.setError("Please Enter Comfirm Password");
            return;
        }
        else if(!password.equals(comfirm_password))
        {
            ed_conf_pass.setError("Comfirm Password do not matched!");
            return;
        } else if(mobile.length()<10)
        {
            ed_mobile.setError("Please Enter 10 digit Mobile number");
            return;
        }
        else if(address.equals(""))
        {
            ed_address.setError("Please Enter Address");
            return;
        }
        else
            Reg_customer(ed_name.getText().toString(), ed_email.getText().toString(), ed_passwprd.getText().toString(), ed_conf_pass.getText().toString(), ed_mobile.getText().toString(), ed_address.getText().toString());



    }

    private void Reg_customer(String rname, String remail, String rpassword, String rconf_password, String rmoblie, String raddress)
    {
        final ProgressDialog pdLoading = new ProgressDialog(Registration.this);
        RequestBody requestBody = new FormBody.Builder()
            .add("mobile", rmoblie)
            .add("name", rname)
            .add("email", remail)
            .add("pass", rpassword)
            .add("username", rname)
            .add("address", raddress)
            .build();

        Request request = new Request.Builder().
                url(UrlHelper.Registration)
                .post(requestBody)
                .build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                Registration.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();


                Registration.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            String code = jsonObject.getString("Code");
                           // String msg = jsonObject.getString("Message");
                            System.out.println("code_kaif "+code);
                           // System.out.println("msg_kaif "+msg);
                            if(code.equalsIgnoreCase("1"))
                            {
                                message("Verification Code Send on Your Email");

                            }
                            else {
                                message("Customer is Already Exist!");
                            }
                            pdLoading.dismiss();
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
        builder.setMessage(msg) .setTitle("Registration");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!msg.equalsIgnoreCase("Customer Already exists")) {
                            finish();
                            startActivity(new Intent(Registration.this, OTPverifaction.class).putExtra("email", ed_email.getText().toString()));
                        }

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        alert.show();
    }

    public void sign_in(View view) {
        startActivity(new Intent(Registration.this, MainActivity.class).putExtra("va", "2"));

    }
}
