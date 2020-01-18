package com.example.g116.vvn_social.Login_Registration_API;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g116.vvn_social.Home_Activities.MainActivity;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Registration extends AppCompatActivity
{


    private String user_type="";
    private EditText full_name_edt,email_edt,mobile_edt,password_edt,cpassword_edt;
    Boolean showPassword = false;
    AppController appController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        full_name_edt=findViewById(R.id.full_name);
        email_edt=findViewById(R.id.email);
        mobile_edt=findViewById(R.id.mobile);
        password_edt=findViewById(R.id.password);
        cpassword_edt=findViewById(R.id.cpassword);
        appController = (AppController) getApplicationContext();


        password_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

               showPassword(motionEvent,password_edt);
                return false;
            }
        });
        cpassword_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                showPassword(motionEvent,cpassword_edt);
                return false;
            }
        });



        if(getIntent().getStringExtra("user_type")!=null)
            user_type=getIntent().getStringExtra("user_type");
        Log.e("user_type_value","skdfj  "+ user_type );



    }

    private void showPassword(MotionEvent event, EditText password) {

                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showPassword)
                        {
                            password.setTransformationMethod(new PasswordTransformationMethod());
                            password.setSelected(false);
                        }
                        else
                        {
                            password.setTransformationMethod(new HideReturnsTransformationMethod());
                            password.setSelected(true);
                        }
                        showPassword = !showPassword;
                        //return true;
                    }
                }

    }

    public void register(View view)//register button
    {

        if(!isNetworkAvailable(getApplicationContext()))
            Toast.makeText(this, "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
        else
            Validate(full_name_edt.getText().toString(), email_edt.getText().toString(),
                    mobile_edt.getText().toString(), password_edt.getText().toString(),
                    cpassword_edt.getText().toString());

    }


    //check validation
    private void Validate(String fullname, String email, String mobile, String password, String cpassword)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (fullname.equals(""))
        {
            full_name_edt.setError("Please Enter Full Name");
            return;
        }
       else if (fullname.length()<4)
        {
            full_name_edt.setError("Please Enter Atleast 4 Character");
            return;
        }
        else if (email.equals(""))
        {
            email_edt.setError("Please Enter Email");
            return;
        }
        else if (!email.matches(emailPattern))
        {
            email_edt.setError("Please Enter Valid Email");
            return;
        }
        else if (mobile.equals(""))
        {
            mobile_edt.setError("Please Enter Mobile");
            return;
        }
        else if (mobile.length()<10)
        {
            mobile_edt.setError("Please Enter 10 Digit Mobile");
            return;
        }
        else if (password.equals(""))
        {
            password_edt.setError("Please Enter Password");
            return;
        }
        else if (cpassword.equals(""))
        {
            cpassword_edt.setError("Please Enter Comfirm Password");
            return;
        }
        else if (!password.equalsIgnoreCase(cpassword))
        {
            cpassword_edt.setError("Comfirm Password Do Not Matched");
            return;
        }
        else
            registration(fullname,email,mobile,password);



    }



    private void registration(String fullname, String email, String mobile, String password) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.show();
        progress.setMessage("Loading");
        progress.setCancelable(false);

        Log.e("login2","ksdf");

        RequestBody body = new FormBody.Builder().
                add("first_name", fullname).
                add("last_name", "").
                add("email", email).
                add("phone", mobile).
                add("password", password).
                add("type", user_type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/registration.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Registration.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("login3","ksdf");
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Something is Went Wrong Please Try Again Later. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject mainjson = new JSONObject(myResponse);
                    if (mainjson.has("msg")) {
                        Registration.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   // Student Registered Successfully
                                   // Teacher Registered Successfully

                                    String message=mainjson.getString("msg");
                                    if(message.contains("Registered Successfully"))
                                    {
                                        Log.e("login4","ksdf");
                                        message=mainjson.getString("msg");
                                      showSuccessAlert(message);



                                    }
                                    else
                                    {
                                        Log.e("login5","ksdf");
                                        message=mainjson.getString("msg");
                                        showAlert2(message);


                                    }
                                    progress.dismiss();

                                } catch (JSONException e) {
                                    progress.dismiss();
                                    Log.e("login6","ksdf");
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }


                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                    Log.e("login8","ksdf");
                }
            }
        });
    }



    public void showSuccessAlert( String message) {

        System.out.println("showSuccessAlert");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("VVN CITY");

        // Ask the final question
        builder.setMessage(message);

        // Set click listener for alert dialog buttons
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        System.out.println("alert_ok");

                        startActivity(new Intent(Registration.this, LoginActivity.class)
                                .putExtra("user_type", user_type));
                        finish();

                        break;


                }
            }
        };

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("ok", dialogClickListener);

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();

    }


    private void showAlert2(String message)
    {

        Log.e("message","dks"+message);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message) .setTitle("VVN CITY");

        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();


                    }
                });



        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("VVN CITY");
        alert.show();


    }


    public void login(View view)//already have an account method
    {

        startActivity(new Intent(getBaseContext(),LoginActivity.class)
                .putExtra("user_type",user_type)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}