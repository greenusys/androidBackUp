package com.icosom.social.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.ScannedBarcode;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class WalletPin extends AppCompatActivity {
    AppController appController;
    CommonFunctions urlHelper;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String user_id;
    ProgressBar progressBar;
    LinearLayout lin_submit, lin_create;
    TextView reset;
    EditText pin;
    String spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_pin);
        appController = (AppController) getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        // user_id = sp.getString("userId", "");
        String kycs = sp.getString("kyc", "0");
       /* if (kycs.equalsIgnoreCase("0")) {
            bui();
        }*/
        urlHelper = new CommonFunctions();
        progressBar = findViewById(R.id.pb_login_subs);
        lin_submit = findViewById(R.id.button_wallet_submits);
        pin = findViewById(R.id.edt_pin_logins);
        reset = findViewById(R.id.txt_reset_pins);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletPin.this, ForgetPin.class));
            }
        });


        final String email2 = sp.getString("email", "");
        final String phone2 = sp.getString("phone", "");

        Log.e("email_kaif", "ksdj" + email2);
        Log.e("email_edt_phonef", "ksdj" + phone2);


        user_id = sp.getString("userId", "");
        if (user_id != null || !user_id.equals(""))
            new check_user_pin().execute(spin, user_id);


        if (email2.equals("") || email2 == null)
            bui("Please Update Your Email id First");


        else if (phone2 == null || phone2.equals(""))
            bui("Please Update Your Mobile Number  First");

        else {
            // Toast.makeText(appController, "Else", Toast.LENGTH_SHORT).show();
        }

        lin_create = findViewById(R.id.createPins);
        lin_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin = pin.getText().toString();

                if (email2.equals("") || email2 == null)
                    bui("Please Update Your Email id First");
                    // Toast.makeText(appController, "Please Update Your Email id First", Toast.LENGTH_SHORT).show();

                else if (phone2 == null || phone2.equals(""))
                    bui("Please Update Your Mobile Number  First");
                    //Toast.makeText(appController, "Please Update Your Mobile Number  First", Toast.LENGTH_SHORT).show();



                else {

                }


            }
        });


        lin_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin = pin.getText().toString();


                if (email2.equals("") || email2 == null)
                    bui("Please Update Your Email id First");

                else if (phone2 == null || phone2.equals(""))
                    bui("Please Update Your Mobile Number  First");

                if (pin.getText().toString().isEmpty()) {
                    pin.setError("Please Enter New Pin");
                    return;
                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    new WalletPin.SignIn1().execute(spin, user_id);
                }
            }
        });


    }

    private void bui(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WalletPin.this);

        // Setting Dialog Title
        alertDialog.setTitle("Icosom Wallet");

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("from","wallet"));



            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WalletPin.this, MainActivity.class));
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "pin";
        private String value_b = "";
        private String key_d = "userId";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.loginwallet;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_d = strings[1];


            Log.e("back", "doInBackground: " + value_a + value_d);
            body = RequestBuilder.threeParameter(
                    key_a, value_a, key_b, value_b, key_d, value_d);

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

                        // Toast.makeText(WalletPin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WalletPin.this, DashboardRecharge.class));

                    }
                    if (statuss.equalsIgnoreCase("0")) {

                        Toast.makeText(WalletPin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        //   startActivity( new Intent(WalletPin.this,DashboardRecharge.class));

                    }/*if (statuss.equalsIgnoreCase("0")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    if (statuss.equalsIgnoreCase("2")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(CreatePin.this,WalletPin.class));
                    }*/
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
        startActivity(new Intent(WalletPin.this, MainActivity.class));
    }

    public void bui() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WalletPin.this);

        // Setting Dialog Title
        alertDialog.setTitle("Kyc Verfication");

        // Setting Dialog Message
        alertDialog.setMessage("Be ready with addhar Card to scan for kyc verification");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(WalletPin.this, ScannedBarcode.class).putExtra("act", "kyc"));

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(WalletPin.this, MainActivity.class));
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    private class check_user_pin extends AsyncTask<String, Void, String> {//check user pin is set or not
        private String key_a = "user_id";                                // if pin is  found then  create bin button  will be invisible

        // String userid=sp.getString("userId","");



        private String value_a =user_id;

        private String response;
        private RequestBody body;
        private String url = "https://icosom.com/social/main/checkPin.php";

        @Override
        protected String doInBackground(String... strings) {



            Log.e("back", "doInBackground: " + key_a+ value_a);
            body = RequestBuilder.oneParameter(key_a, value_a);

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

                        lin_create.setVisibility(View.INVISIBLE);
                        reset.setVisibility(View.VISIBLE);
                        Log.e("tru_KAIF","SDFJ"+statuss);
                    }

                    if (statuss.equalsIgnoreCase("0")) {

                        lin_create.setVisibility(View.VISIBLE);
                        reset.setVisibility(View.GONE);

                        Log.e("else_KAIF","SDFJ"+statuss);
                    }



                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }


    }

}
