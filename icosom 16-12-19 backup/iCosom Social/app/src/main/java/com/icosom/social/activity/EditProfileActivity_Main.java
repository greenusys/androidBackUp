package com.icosom.social.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.CityModel;
import com.icosom.social.model.CountryModel;
import com.icosom.social.model.StateModel;
import com.icosom.social.spinner_adapter.CityAdapter;
import com.icosom.social.spinner_adapter.CountryAdapter;
import com.icosom.social.spinner_adapter.StateAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity_Main extends AppCompatActivity implements View.OnClickListener {
    EditText edt_firstName;
    EditText edt_lastName;
    EditText edt_birthDate;
    EditText edt_email;
    EditText edt_phone;
    EditText edt_about;
    EditText edt_website;
    EditText edt_from;
    Spinner sp_country;
    Spinner sp_state;
    Spinner sp_city;
    TextView txt_advanceProfile;
    RadioGroup rg_gender;
    RadioButton rb_male;
    RadioButton rb_female;
    AppController appController;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    int coin, sin, cin;
    int ii = 1;

    ArrayList<CountryModel> countryModels;
    ArrayList<StateModel> stateModels;
    ArrayList<CityModel> cityModels;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_firstName = findViewById(R.id.edt_firstName);
        edt_lastName = findViewById(R.id.edt_lastName);
        edt_birthDate = findViewById(R.id.edt_birthDate);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_about = findViewById(R.id.edt_about);
        edt_website = findViewById(R.id.edt_website);
        edt_from = findViewById(R.id.edt_from);
        sp_country = findViewById(R.id.sp_country);
        sp_state = findViewById(R.id.sp_state);
        sp_city = findViewById(R.id.sp_city);
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        txt_advanceProfile = findViewById(R.id.txt_advanceProfile);

        countryModels = new ArrayList<>();
        stateModels = new ArrayList<>();
        cityModels = new ArrayList<>();

        getCountry();
        countryAdapter = new CountryAdapter(countryModels, getBaseContext());
        sp_country.setAdapter(countryAdapter);

        edt_firstName.setText(sp.getString("firstName", ""));
        edt_lastName.setText(sp.getString("lastName", ""));
        edt_birthDate.setText(sp.getString("birthDate", ""));

        edt_birthDate.setOnClickListener(this);


        edt_email.setText(sp.getString("email", ""));
        edt_phone.setText(sp.getString("phone", ""));

    String email2=sp.getString("email", "");
    String phone2=sp.getString("phone", "");


        if(email2!=null && !email2.equals(""))
            edt_email.setEnabled(false);


        if(phone2!=null && !phone2.equals(""))
            edt_phone.setEnabled(false);


        Log.e("email_kaif","ksdj"+email2);
        Log.e("email_edt_phonef","ksdj"+phone2);


        if (sp.getString("gender", "").equalsIgnoreCase("male"))
            rb_male.setChecked(true);
        else if (sp.getString("gender", "").equalsIgnoreCase("female"))
            rb_female.setChecked(true);


        edt_about.setText(sp.getString("about", ""));
        edt_from.setText(sp.getString("place", ""));
        edt_website.setText(sp.getString("website", ""));



        int coun = Integer.parseInt(sp.getString("country", ""));
        int stat = Integer.parseInt(sp.getString("state", ""));
        int cit = Integer.parseInt(sp.getString("city", ""));
        coin = coun - 1;
        sin = stat - 1;
        cin = cit - 1;






        Log.e("sdf","sdf"+coun);




        /*sp_country.setTag(sp.getString("country", ""));

        sp_city.setTag(sp.getString("city", ""));
        sp_state.setTag(sp.getString("state", ""));*/




        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (ii == 1) {
                    getStates(countryModels.get(i).getId());
                    i++;
                } else {
                    getStatess(countryModels.get(i).getId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCity(stateModels.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txt_advanceProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EditAdvanceProfileActivity.class));
            }
        });

        (findViewById(R.id.txt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_firstName.getText().toString().isEmpty()) {
                    edt_firstName.setError("Enter first name");
                    return;
                }

                if (edt_lastName.getText().toString().isEmpty()) {
                    edt_lastName.setError("Enter last name");
                    return;
                }

                if (edt_birthDate.getText().toString().isEmpty()) {
                    edt_birthDate.setError("Enter Birth Date");
                    return;
                }

                if (edt_email.getText().toString().isEmpty()) {
                    edt_email.setError("Enter Email");
                    return;
                }

                editProfile(((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editProfile(final String gender) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("birthDate", edt_birthDate.getText().toString()).
                add("firstName", edt_firstName.getText().toString()).
                add("lastName", edt_lastName.getText().toString()).
                add("gender", gender).
                add("email", edt_email.getText().toString()).
                add("phone", edt_phone.getText().toString()).
                add("about", edt_about.getText().toString()).
                //add("website","").
                add("website", edt_website.getText().toString()).
               // add("place", "").
               add("place", edt_from.getText().toString()).
               // add("state", "").
                add("state", stateModels.get(sp_state.getSelectedItemPosition()).getId()).
                //add("country", "").
               add("country", countryModels.get(sp_country.getSelectedItemPosition()).getId()).
                //add("city", "").
               // add("city", cityModels.get(sp_city.getSelectedItemPosition()).getId()).
                build();
        Log.e("country", "onResponse: " + countryModels.get(sp_country.getSelectedItemPosition()).getId());
        Log.e("state", "onResponse: " + stateModels.get(sp_state.getSelectedItemPosition()).getId());
//        Log.e("city", "onResponse: " + cityModels.get(sp_city.getSelectedItemPosition()).getId());


        Log.e("profileReturn", "onResponse: " + body);
        Request request = new Request.Builder().
                url(CommonFunctions.EDIT_PROFILE).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("profileReturn", "onResponse: " + ja);
                    if (ja.getString("status").equalsIgnoreCase("1")) {
                        EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    edt.putString("birthDate", edt_birthDate.getText().toString());
                                    edt.putString("firstName", edt_firstName.getText().toString());
                                    edt.putString("lastName", edt_lastName.getText().toString());
                                    edt.putString("gender", gender);
                                    edt.putString("email", edt_email.getText().toString());
                                    edt.putString("phone", edt_phone.getText().toString());
                                    edt.putString("about", edt_about.getText().toString());
                                    edt.putString("birthDate", edt_birthDate.getText().toString());

                                    edt.putString("website", edt_website.getText().toString());
                                    edt.putString("place", edt_from.getText().toString());
                                   // edt.putString("state", stateModels.get(sp_state.getSelectedItemPosition()).getId());
                                    edt.putString("country", countryModels.get(sp_country.getSelectedItemPosition()).getId());
                                   //edt.putString("city", cityModels.get(sp_city.getSelectedItemPosition()).getId());
                                    edt.apply();
                                    Toast.makeText(getBaseContext(), "" + ja.getString("message"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(EditProfileActivity_Main.this, MainActivity.class));
                            }
                        });
                    }
                    else if(ja.getString("status").equalsIgnoreCase("0"))
                    {
                        Toast.makeText(getBaseContext(), "This Email Id is already Exist!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCountry() {
        Request request = new Request.Builder().
                url(CommonFunctions.GET_COUNTRY).
                get().
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("rrrrrrrrrr " + myResponse);
                try {
                 final   JSONArray ja = new JSONArray(myResponse);
                    System.out.println("rrrrrrrrrr parseSuccessful");
                    for (int i = 0; i < ja.length()-1; i++) {
                        if (i == 0) {
                            if(coin!=-1)
                            countryModels.add(new CountryModel(ja.getJSONObject(coin).getString("id"), ja.getJSONObject(coin).getString("name")));
                        }
                        if (i == coin) {


                        } else {
                            countryModels.add(new CountryModel(
                                    ja.getJSONObject(i).getString("id"),
                                    ja.getJSONObject(i).getString("name")
                            ));
                        }

                        System.out.println("rrrrrrrrrr " + countryModels.get(i).getName());
                    }

                    EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("rrrrrrrrrr notify");
                            countryAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void getStates(String countryId) {
        stateModels.clear();

        Log.e("kaif_country_id","sdk"+countryId);

        RequestBody body = new FormBody.Builder().
                add("country_id", countryId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_STATES).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("ttttttttttttt " + myResponse);

                try {
                  final  JSONArray ja = new JSONArray(myResponse);
                    System.out.println("ttttttttttttt parse succ");
                    for (int i = 0; i < ja.length(); i++) {
                        if (i == 0)
                        {
                            if(coin!=-1 && sin!=-1)
                            stateModels.add(new StateModel(
                                    ja.getJSONObject(sin).getString("id"),
                                    ja.getJSONObject(sin).getString("name")
                            ));
                        }
                        if (i == sin) {


                        } else {


                            stateModels.add(new StateModel(
                                    ja.getJSONObject(i).getString("id"),
                                    ja.getJSONObject(i).getString("name")
                            ));
                        }
                        System.out.println("ttttttttttttt " + stateModels.get(i).getName());
                    }

                    EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (stateAdapter == null) {
                                System.out.println("ttttttttttttt first");
                                stateAdapter = new StateAdapter(stateModels, getBaseContext());
                                sp_state.setAdapter(stateAdapter);
                            } else {
                                System.out.println("ttttttttttttt notify");
                                stateAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCity(String stateId) {
        cityModels.clear();

        Log.e("kaif_staste_id","sdk"+stateId);


        RequestBody body = new FormBody.Builder().
                add("state_id", stateId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_CITY).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONArray ja = new JSONArray(myResponse);
                    Log.e("kaif_1","skd"+cityModels);
                    for (int i = 0; i < ja.length(); i++) {



                        if(coin!=-1) {

                            Log.e("kaif_if","skd"+cityModels);
                            cityModels.add(new CityModel(
                                    ja.getJSONObject(i).getString("id"),
                                    ja.getJSONObject(i).getString("name")
                            ));

                        }
                        else
                            Log.e("kaif_else","skd"+cityModels);
                    }

                    EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {

                            Log.e("kaif_cities","skd"+cityModels);
                            if (cityAdapter == null) {
                                Log.e("kaif_3","skd"+cityModels);
                                cityAdapter = new CityAdapter(cityModels, getBaseContext());
                                sp_city.setAdapter(cityAdapter);
                            } else {
                                Log.e("kaif_4","skd"+cityModels);
                                cityAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getStatess(String countryId) {
        stateModels.clear();

        RequestBody body = new FormBody.Builder().
                add("country_id", countryId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_STATES).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("ttttttttttttt " + myResponse);

                try {
                    JSONArray ja = new JSONArray(myResponse);
                    System.out.println("ttttttttttttt parse succ");
                    for (int i = 0; i < ja.length(); i++) {
                        if(coin!=-1)
                        stateModels.add(new StateModel(
                                ja.getJSONObject(i).getString("id"),
                                ja.getJSONObject(i).getString("name")
                        ));

                        System.out.println("ttttttttttttt " + stateModels.get(i).getName());
                    }

                    EditProfileActivity_Main.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (stateAdapter == null) {
                                System.out.println("ttttttttttttt first");
                                stateAdapter = new StateAdapter(stateModels, getBaseContext());
                                sp_state.setAdapter(stateAdapter);
                            } else {
                                System.out.println("ttttttttttttt notify");
                                stateAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

         int mYear, mMonth, mDay;

        if (v == edt_birthDate)
        {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           // edt_birthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            edt_birthDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }




    }
}