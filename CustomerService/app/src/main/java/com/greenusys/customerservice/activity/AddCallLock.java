package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddCallLock extends AppCompatActivity {
    EditText query;
    Button submit;
    AppController appController;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String unqieID, date, name, userId, adress, mail, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call_lock);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        name = sp.getString("name","0");
        userId = sp.getString("userId","0");
        adress = sp.getString("address","0");
        mail = sp.getString("email","0");
        phone = sp.getString("mobile","0");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        unqieID = sdf.format(new Date());

        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MM-yyyy");
        date = sdfs.format(new Date());

        appController = (AppController) getApplicationContext();
        submit = findViewById(R.id.query_submit);
        query = findViewById(R.id.edit_query);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (query.getText().toString().isEmpty()) {
                    query.setError("Please Enter query.");
                    return;
                } else {
                    loginPlease(query.getText().toString());

                }



            }
        });
    }

    private void loginPlease(String query) {

//        pb_login.setVisibility(View.VISIBLE);

        RequestBody body = new FormBody.Builder().
                add("customerId", userId).
                add("uniqueId", unqieID).
                add("name", name).
                add("email", mail).
                add("mobile",phone ).
                add("query", query).
                add("address", adress).
                add("date", date).
                build();

        Request request = new Request.Builder().
                url(UrlHelper.addQuery).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                AddCallLock.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    String code= ja.getString("Code");
                    if(code.equalsIgnoreCase("1")){
                        startActivity(new Intent(AddCallLock.this, CustomerDashboard.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
