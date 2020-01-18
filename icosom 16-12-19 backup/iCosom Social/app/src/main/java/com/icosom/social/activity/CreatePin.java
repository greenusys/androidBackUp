package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class CreatePin extends AppCompatActivity {
    AppController appController;
    CommonFunctions urlHelper;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String user_id;
    ProgressBar progressBar;
    LinearLayout lin;
    EditText pin, confirm;
    String spin, sconfirm, phone;

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreatePin.this, WalletPin.class));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        progressBar = findViewById(R.id.pb_login_create);
        lin = findViewById(R.id.button_wallet_submit_create);
        pin = findViewById(R.id.edt_pin_create);
        confirm = findViewById(R.id.edt_pin_confirm_create);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        user_id = sp.getString("userId", "");
        phone = sp.getString("phone", "");


        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin = pin.getText().toString();
                sconfirm = confirm.getText().toString();
                if (pin.getText().toString().isEmpty()) {
                    pin.setError("Please Enter New Pin");
                    return;
                }
                if (confirm.getText().toString().isEmpty()) {
                    confirm.setError("Please Enter Confirm Pin");
                    return;
                }
                if (phone.equalsIgnoreCase("") || phone.equalsIgnoreCase("null")) {

                    Toast.makeText(CreatePin.this, "Please update your phone no first", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(CreatePin.this, WalletPin.class));
                } else if (spin.equalsIgnoreCase(sconfirm)) {
                    progressBar.setVisibility(View.VISIBLE);
                    new CreatePin.SignIn1().execute(spin, sconfirm, user_id);
                    return;
                } else {
                    confirm.setError(" confirm pin doesn't match with New Pin");

                }
            }
        });


    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "new-pin";
        private String value_b = "";
        private String key_c = "confirm-new-pin";
        private String value_c = "";
        private String key_d = "userId";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.CREATEPIN;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_c = strings[1];
            value_d = strings[2];

            Log.e("back", "doInBackground: " + value_a + value_c + value_d);
            body = RequestBuilder.fourParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("createPin", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String statuss = rootObject.getString("status");

                    if (statuss.equalsIgnoreCase("1")) {

                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreatePin.this, WalletPin.class));

                    }
                    if (statuss.equalsIgnoreCase("0")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    if (statuss.equalsIgnoreCase("2")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreatePin.this, WalletPin.class));
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(CreatePin.this, MainActivity.class));
    }
}
