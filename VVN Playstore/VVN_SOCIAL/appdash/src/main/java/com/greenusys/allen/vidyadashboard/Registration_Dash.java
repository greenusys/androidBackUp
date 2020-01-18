package com.greenusys.allen.vidyadashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registration_Dash extends AppCompatActivity {
    final int req = 1;
    Button up, upload;
    String message, code;
    String sname, slname, semail, spass, spass2;
    EditText fname, lname, remail, rpass, rpass2;
    String url = "http://vvn.city/apps/jain/signup.php";
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private AwesomeValidation awesomeValidation;
    private Bitmap bmp;
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_dash);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        remail = (EditText) findViewById(R.id.remail);
        rpass = (EditText) findViewById(R.id.rpass);
        rpass2 = (EditText) findViewById(R.id.rpass2);
        up = (Button) findViewById(R.id.upreg);
        upload = (Button) findViewById(R.id.upload);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.remail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
//        awesomeValidation.addValidation(this, R.id.rpass, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
//        awesomeValidation.addValidation(this, R.id.rpass2, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        // awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                clicked=true;
                if(clicked)
                {
                    getdata();
                }
                else
                {
                    Toast.makeText(Registration_Dash.this, "Please Upload ur Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();

            }
        });
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearray);
        byte[] imbyte = bytearray.toByteArray();
        return Base64.encodeToString(imbyte, Base64.DEFAULT);

    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == req && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
             /*   img.setImageBitmap(bmp);
                img.setVisibility(View.VISIBLE);
                ed.setVisibility(View.VISIBLE);*/
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    public void getdata()
    {

        sname = fname.getText().toString();
        slname = lname.getText().toString();
        semail = remail.getText().toString();
        spass = rpass.getText().toString();
        spass2 = rpass2.getText().toString();

        if (spass.length() == 0) {
            rpass.setError("Password is must not be empty");
            rpass.requestFocus();
        }
        if (spass2.length() == 0) {
            rpass2.setError("confirm password is must not be empty");
            rpass2.requestFocus();
        }
        if (spass.equals(spass2)) {


            if (awesomeValidation.validate()) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            message = jsonObject.getString("res");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final ProgressDialog pd = new ProgressDialog(Registration_Dash.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                        pd.setTitle("Loading");
                        pd.setMessage("Please wait for admin to access permission");

                        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

                        pd.setIndeterminate(false);

                        pd.show();

                        // Set the progress status zero on each button click
                        progressStatus = 0;

                        // Start the lengthy operation in a background thread
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (progressStatus < 100) {
                                    // Update the progress status
                                    progressStatus += 1;

                                    // Try to sleep the thread for 20 milliseconds
                                    try {
                                        Thread.sleep(20);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    // Update the progress bar
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Update the progress status
                                            pd.setProgress(progressStatus);
                                            // If task execution completed
                                            if (progressStatus == 100) {
                                                Intent ipp = new Intent(getApplicationContext(), Login_Dash.class);
                                                startActivity(ipp);
                                                finish();
                                                // Dismiss/hide the progress dialog
                                                pd.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        }).start(); // Start the operation


//                                Intent reg = new Intent(Registration_Dash.this, Login_Dash.class);
//                                startActivity(reg);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration_Dash.this, "Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("first_name", sname);
                        params.put("last_name", slname);
                        params.put("email", semail);
                        params.put("password", spass);
                        params.put("image", imageToString(bmp));
                        return params;
                    }
                };


                MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);
            }
        }
    }
}

