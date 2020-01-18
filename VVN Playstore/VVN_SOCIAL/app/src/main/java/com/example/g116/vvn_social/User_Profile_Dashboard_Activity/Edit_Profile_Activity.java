package com.example.g116.vvn_social.User_Profile_Dashboard_Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.AlertDialogManager;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Edit_Profile_Activity extends AppCompatActivity {


    EditText edt_firstName, edt_lastName, edt_birthDate, edt_email, edt_phone, edt_education, edt_passed_year,
            edt_merital, edt_address;
    Spinner sp_country, sp_state, sp_city;
    RadioGroup rg_gender;
    RadioButton rb_male;
    RadioButton rb_female;
    RadioButton radioButton;
    AlertDialogManager alert = new AlertDialogManager();
    private AppController appController;
    private SessionManager session;
    HashMap<String, String> user;

    String user_type = "";
    String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);

        appController = (AppController) getApplicationContext();

        session = new SessionManager(getApplicationContext());


        edt_firstName = findViewById(R.id.edt_firstName);
        edt_lastName = findViewById(R.id.edt_lastName);
        edt_birthDate = findViewById(R.id.edt_birthDate);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_education = findViewById(R.id.edt_education);
        edt_passed_year = findViewById(R.id.edt_passed_year);
        edt_merital = findViewById(R.id.edt_merital);
        edt_address = findViewById(R.id.edt_address);


        //spinners
        sp_country = findViewById(R.id.sp_country);
        sp_state = findViewById(R.id.sp_state);
        sp_city = findViewById(R.id.sp_city);

        //radio buttons
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);


        user = session.getUserDetails();

        user_type = user.get(SessionManager.KEY_USER_TYPE);
        user_id = user.get(SessionManager.KEY_ID);

        if (user_type.equalsIgnoreCase("student"))
            edt_passed_year.setVisibility(View.VISIBLE);

        if (user_type.equalsIgnoreCase("teacher"))
            edt_passed_year.setVisibility(View.GONE);


        String full_name = user.get(SessionManager.KEY_FULL_NAME);
        String name[] = full_name.split(" ");
        String email2 = user.get(SessionManager.KEY_EMAIL);
        String educationDetails = user.get(SessionManager.KEY_EducationDetails);
        String address2 = user.get(SessionManager.KEY_Address);
        String gender2 = user.get(SessionManager.KEY_Gender);
        String birthDate = user.get(SessionManager.KEY_BirthDate);
        String maritalStatus = user.get(SessionManager.KEY_MaritalStatus);
        String passYear = user.get(SessionManager.KEY_PassYear);
        String mobile2 = user.get(SessionManager.KEY_MOBILE);

        if (name.length == 2) {
            edt_firstName.setText(name[0]);
            edt_lastName.setText(name[1]);
        } else
            edt_firstName.setText(name[0]);

        edt_birthDate.setText(birthDate);
        edt_email.setText(email2);
        edt_phone.setText(mobile2);
        edt_education.setText(educationDetails.equals("null") ? "" : educationDetails);
        edt_passed_year.setText(passYear.equals("null") ? "" : passYear);
        edt_merital.setText(maritalStatus.equals("null") ? "" : maritalStatus);
        edt_address.setText(address2.equals("null") ? "" : address2);


        if (!email2.equals(""))
            edt_email.setEnabled(false);

        if (gender2.equalsIgnoreCase("male"))
            rb_male.setChecked(true);
        else
            rb_female.setChecked(true);


    }

    private void Validate_Data(String firstName, String lastName, String birthDate,
                               String email, String phone, String education, String passed_year,
                               String merital, String address, String gender) {


        System.out.println("f_name" + firstName);
        System.out.println("edt_lastName" + lastName);
        System.out.println("edt_birthDate" + birthDate);
        System.out.println("edt_email" + email);
        System.out.println("edt_phone" + phone);
        System.out.println("edt_education" + education);
        System.out.println("edt_passed_year" + passed_year);
        System.out.println("edt_merital" + merital);
        System.out.println("edt_address" + address.equals(""));
        System.out.println("gender" + gender);


        if (firstName.equals("")) {
            edt_firstName.setError("Please Enter First Name");
            return;
        } else if (birthDate.equals("")) {
            edt_birthDate.setError("Please Enter Date of Birth");
            return;
        } else if (email.equals("")) {
            edt_email.setError("Please Enter Email id");
            return;
        } else if (phone.equals("")) {
            edt_phone.setError("Please Enter Mobile Number");
            return;
        } else if (phone.length() < 10) {
            edt_phone.setError("Please Enter Valid Mobile Number");
            return;
        } else if (education.equals("")) {
            edt_education.setError("Please Enter Education");
            return;
        } else if (merital.equals("")) {
            edt_merital.setError("Please Enter Merital Status");
            return;
        } else if (address.equals("")) {
            edt_address.setError("Please Enter Address");
            return;
        } else if (passed_year.equals("") && user_type.equalsIgnoreCase("student")) {
            edt_passed_year.setError("Please Enter Passed Year");
            return;
        } else {
            edit_profile_api(user_type, user_id, firstName, lastName, birthDate, phone, education, passed_year, merital, address, gender);
        }


    }

    private void edit_profile_api(final String user_type, String user_id, String firstName, String lastName, String birthDate, String phone, String education,
                                  final String passed_year, String merital, String address, String gender) {
        System.out.println("edit_profile_api_called");
        System.out.println("user_type" + user_type);
        System.out.println("user_id" + user_id);


        final ProgressDialog progress = new ProgressDialog(this);
        progress.show();
        progress.setMessage("Loading");
        progress.setCancelable(false);
        RequestBody body = null;

        //for student
        if (user_type.equalsIgnoreCase("student")) {
            body = new FormBody.Builder().
                    add("type", user_type).
                    add("user_id", user_id).
                    add("firstName", firstName).
                    add("lastName", lastName).
                    add("educationDetails", education).
                    add("maritalStatus", merital).
                    add("phone", phone).
                    add("address", address).
                    add("gender", gender).
                    add("birthDate", birthDate).
                    add("passYear", passed_year).
                    build();
        }
        //for teaher
        else {
            body = new FormBody.Builder().
                    add("type", user_type).
                    add("user_id", user_id).
                    add("firstName", firstName).
                    add("lastName", lastName).
                    add("educationDetails", education).
                    add("maritalStatus", merital).
                    add("phone", phone).
                    add("address", address).
                    add("gender", gender).
                    add("birthDate", birthDate).

                    build();
        }


        Request request = new Request.Builder().
                url("https://vvn.city/api/update_profile.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Edit_Profile_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progress.dismiss();
                        // Toast.makeText(getBaseContext(), "Something is Went Wrong Please Try Again Later. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject mainjson = new JSONObject(myResponse);
                    Edit_Profile_Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String status = mainjson.getString("status");
                                String path = "https://vvn.city/";
                                if (status.equals("1")) {

                                    JSONObject jsonObject = mainjson.getJSONObject("data");

                                    String full_name = jsonObject.getString("firstName") + " " +
                                            jsonObject.getString("lastName");

                                    String educationDetails = jsonObject.getString("educationDetails");
                                    String passYear = "";
                                    String maritalStatus = "";
                                    String picture = "";
                                    String birthDate = "";
                                    String class_course = "";
                                    String password = "";


                                    if (user_type.equalsIgnoreCase("student")) {
                                        passYear = jsonObject.getString("passYear");
                                        maritalStatus = jsonObject.getString("maritalStatus");
                                        birthDate = jsonObject.getString("birthDate");
                                        class_course = jsonObject.getString("class");
                                        password = jsonObject.getString("pass");
                                        picture = jsonObject.getString("picture");
                                        picture = picture.replace("../", "");
                                        picture = path + picture;
                                    } else {
                                        picture = jsonObject.getString("picture");
                                        birthDate = jsonObject.getString("dob");
                                        password = jsonObject.getString("pwd");
                                        picture = path + picture;
                                        maritalStatus = jsonObject.getString("t_marital_status");
                                    }


                                    String gender = jsonObject.getString("gender");
                                    String address = jsonObject.getString("address");
                                    String id = jsonObject.getString("sno");
                                    String mobile = jsonObject.getString("phone");
                                    String email = jsonObject.getString("email");


                                    //  String state=jsonObject.getString("state");
                                    // String city=jsonObject.getString("city");

                                    Log.e("User_path+picture", "" + path + picture);

                                    //creating user's session
                                    session.createLoginSession(id, full_name, email, mobile, picture, user_type, class_course, "", "",
                                            educationDetails, passYear, maritalStatus, birthDate, gender, address, password);


                                    startActivity(new Intent(Edit_Profile_Activity.this, User_Profile_Dashboard.class));
                                    finish();

                                } else {
                                    Log.e("login5", "ksdf");


                                }
                                progress.dismiss();


                            } catch (JSONException e) {
                                Log.e("login6", "ksdf");
                                progress.dismiss();

                                e.printStackTrace();
                            }
                        }
                    });
                    return;


                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.dismiss();

                    Log.e("login8", "ksdf");
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_data) {
            //Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            Validate_Data(edt_firstName.getText().toString(),
                    edt_lastName.getText().toString(),
                    edt_birthDate.getText().toString(),
                    edt_email.getText().toString(),
                    edt_phone.getText().toString(),
                    edt_education.getText().toString(),
                    edt_passed_year.getText().toString(),
                    edt_merital.getText().toString(),
                    edt_address.getText().toString(),
                    ((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString()

            );

        }

        return super.onOptionsItemSelected(item);
    }


}
