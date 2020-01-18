package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.greenusys.customerservice.R;

public class Loginactivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    ImageView imgeng, imgcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imgeng = findViewById(R.id.eng_ser);
        imgcut = findViewById(R.id.custo_ser);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();



        if (sp.getBoolean("savePassword", false)) {

            String val= sp.getString("user_type","");
            System.out.println("user_type_val"+val);

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



        imgeng.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Loginactivity.this, MainActivity.class).putExtra("va", "1"));
                    }
                }
        );

        imgcut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Loginactivity.this, MainActivity.class).putExtra("va", "2"));
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
