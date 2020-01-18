package com.greenusys.customerservice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText mail, pass;
    TextView forget, reset, createNewAccount;
    AppController appController;
    ProgressBar pb_login;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    Boolean showPassword = false;
    String type;
    String val;
    String vn;
    TextView title,login_text;
    Button regcus;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reset = findViewById(R.id.txt_reset);
        reset.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Forgettpassword.class));
                    }
                }
        );
        forget = findViewById(R.id.txt_forgotPassword);
        login_text = findViewById(R.id.login_text);
        forget.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Forget.class));
                    }
                }
        );
        createNewAccount = findViewById(R.id.createNewAccount);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });
        val = getIntent().getStringExtra("va");
        Log.d("t1", "msg" + val);


        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();


        if (sp.getBoolean("savePassword", false)) {
            // Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();

            if (val.equalsIgnoreCase("1")) {
                startActivity(new Intent(getBaseContext(), EnginerDashboard.class));
                finish();
                return;
            }
            if (val.equalsIgnoreCase("2")) {
                startActivity(new Intent(getBaseContext(), CustomerDashboard.class));
                finish();
                return;
            }
        } else {
            //Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
        }

        appController = (AppController) getApplicationContext();
        login = findViewById(R.id.buttonlogin);
        mail = findViewById(R.id.edt_mail);
        pass = findViewById(R.id.edt_password);

        createNewAccount = findViewById(R.id.createNewAccount);
        if (val.equalsIgnoreCase("1")) {
            login_text.setText("Log in with your Engineer service Account");
            createNewAccount.setVisibility(View.GONE);
            forget.setVisibility(View.INVISIBLE);
            reset.setVisibility(View.INVISIBLE);

        } else {
            login_text.setText("Log in with your Customer service Account");
            createNewAccount.setVisibility(View.VISIBLE);

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mail.getText().toString().isEmpty()) {
                    mail.setError("Please Enter Email Id.");
                    return;
                }

                if (pass.getText().toString().isEmpty()) {
                    pass.setError("Please Enter Password.");
                    return;
                } else {
                    loginPlease(mail.getText().toString(), pass.getText().toString());

                }

            }
        });
    }

    private void loginPlease(String email, String password) {

//        pb_login.setVisibility(View.VISIBLE);
        final ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        RequestBody body = new FormBody.Builder().
                add("email", email).
                add("password", password).
                add("type", val).
                build();

        Request request = new Request.Builder().
                url(UrlHelper.cusLogin).
                post(body).
                build();
        pdLoading.setMessage("Loading...");
        pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*buttonlogin.setClickable(true);
                        txt_login.setVisibility(View.VISIBLE);
                        pb_login.setVisibility(View.GONE);*/
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);

                try {

                    final JSONArray jsonArray = new JSONArray(myResponse);

                    final JSONObject ja = jsonArray.getJSONObject(0);

                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("Code")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    edt.putBoolean("savePassword", true);
                                    // edt.commit();
                                    edt.putString("userId", ja.getString("userId"));
                                    edt.putString("name", ja.getString("Name"));
                                    edt.putString("email", ja.getString("Email"));
                                    Log.e("h1", "sdf" + email);
                                    edt.putString("mobile", ja.getString("Mobile"));
                                    edt.putString("address", ja.getString("Address"));
                                    edt.putString("user_type", val);
                                    edt.commit();
                                    type = ja.getString("Type");

                                    edt.putBoolean("isLogin", true);
                                    edt.commit();

                                    if (ja.getString("Type").equalsIgnoreCase("user")) {
                                        startActivity(new Intent(MainActivity.this, CustomerDashboard.class));
                                        finish();
                                    }
                                    if (ja.getString("Type").equalsIgnoreCase("engg")) {
                                        startActivity(new Intent(MainActivity.this, EnginerDashboard.class).putExtra("h1", "20"));
                                        Log.e("hh", "sdf" + email);
                                        finish();
                                    }
                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // clearApplicationData();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("t1", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
