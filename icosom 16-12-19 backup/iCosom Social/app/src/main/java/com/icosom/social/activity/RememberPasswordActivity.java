package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.icosom.social.R;

public class RememberPasswordActivity extends AppCompatActivity
{
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        (findViewById(R.id.txt_notNow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putBoolean("isLogin", false);
                edt.commit();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        (findViewById(R.id.txt_remember)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putBoolean("isLogin", true);
                edt.commit();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}