package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileDescriptionUpdateActivity extends AppCompatActivity
{
    EditText edt_profile_description_profile;
    TextView tv_profile_description_profile;
    CollapsingToolbarLayout toolbar_layout;
    Uri coverUri;
    Uri profileUri;
    ImageView iv_cover_image_profile;
    ImageView iv_profile_image_profile;
    String id;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    ImageView iv_done;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_description_update);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_profile_description_profile = findViewById(R.id.edt_profile_description_profile);
        tv_profile_description_profile = findViewById(R.id.tv_profile_description_profile);
        iv_cover_image_profile = findViewById(R.id.iv_cover_image_profile);
        iv_profile_image_profile = findViewById(R.id.iv_profile_image_profile);
        iv_done = findViewById(R.id.iv_done);
        toolbar_layout = findViewById(R.id.toolbar_layout);

        coverUri = getIntent().getParcelableExtra("cover");
        profileUri = getIntent().getParcelableExtra("profile");

        id = sp.getString("userId", "");
        String name = sp.getString("firstName", "") +" "+ sp.getString("lastName", "");
        toolbar_layout.setTitle(name);

        iv_cover_image_profile.setImageURI(coverUri);
        iv_profile_image_profile.setImageURI(profileUri);

        edt_profile_description_profile.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length()>0)
                {
                    tv_profile_description_profile.setText(charSequence.toString());
                }
                else
                {
                    tv_profile_description_profile.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        iv_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (edt_profile_description_profile.getText().toString().length()>0)
                    updateDescription(edt_profile_description_profile.getText().toString());
                else
                    Toast.makeText(getBaseContext(), "Please Enter Description first.", Toast.LENGTH_SHORT).show();
            }
        });

        ((TextView) findViewById(R.id.txt_skip)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProfileDetailsUpdateActivity.class));
            }
        });
    }

    private void updateDescription(String about)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("about", about).
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.UPDATE_DESCRIPTION).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                ProfileDescriptionUpdateActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();
                try
                {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.getString("status").equals("1"))
                    {
                        ProfileDescriptionUpdateActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getBaseContext(), RememberPasswordActivity.class));
                                finish();
                            }
                        });
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}