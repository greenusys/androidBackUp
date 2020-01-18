package com.icosom.social.activity;

import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.icosom.social.Main_trans;
import com.icosom.social.R;
import com.icosom.social.fragment.AtomsFragment;
import com.icosom.social.fragment.RechargeFragment;

public class Dash_fragement extends AppCompatActivity {
   String type = "0";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Dash_fragement.this, DashboardRecharge.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_fragement);
        type =  getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("1"))
        {
            AtomsFragment fragments = new AtomsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fra, fragments);
            transaction.commit();
        }
        if(type.equalsIgnoreCase("2")){
            RechargeFragment  fragment = new RechargeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fra, fragment);
            transaction.commit();
        }
        if(type.equalsIgnoreCase("3")){
            Main_trans fragmentss = new Main_trans ();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fra, fragmentss);
            transaction.commit();
        }
        if(type.equalsIgnoreCase("0")){

        }

    }
}
