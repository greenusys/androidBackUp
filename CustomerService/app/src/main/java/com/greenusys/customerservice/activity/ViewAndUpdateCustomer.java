package com.greenusys.customerservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greenusys.customerservice.R;

import org.w3c.dom.Text;

public class ViewAndUpdateCustomer extends AppCompatActivity {
  Text t_id,t_name,t_email,t_username,t_adress,t_joingdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update_customer);
       /* t_id = findViewById(R.id.txt_id);
        t_name = findViewById(R.id.txt_name);
        t_email = findViewById(R.id.txt_email);
        t_username = findViewById(R.id.txt_username);
        t_adress = findViewById(R.id.txt_address);
        t_joingdata = findViewById(R.id.txt_joing_date);*/


    }
}
