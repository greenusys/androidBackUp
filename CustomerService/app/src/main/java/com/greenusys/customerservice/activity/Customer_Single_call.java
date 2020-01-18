package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.customerservice.R;

public class Customer_Single_call extends AppCompatActivity {
TextView ids,query,date,engName,engPhone;
String gid,gquery,gdatw,gename,gephone;
Button qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__single_call);
        ids =  findViewById(R.id.s_id);
        query = findViewById(R.id.s_query);
        date =  findViewById(R.id.s_q_date);
        engName =  findViewById(R.id.s_eng_name);
        engPhone = findViewById(R.id.s_eng_phone);
        qr = findViewById(R.id.generate_qr);
        gid = getIntent().getStringExtra("id");
        gquery = getIntent().getStringExtra("query");
        gdatw = getIntent().getStringExtra("date");
        gename = getIntent().getStringExtra("engName");
        gephone = getIntent().getStringExtra("EnggPhone");
        ids.setText("Enquiry id : "+gid);
        query.setText("Query : "+gquery);
        date.setText("Date : "+gdatw);
        engName.setText("Enginer Name : "+gename) ;
        engPhone.setText("Enginer Phone No : " +gephone);

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(Customer_Single_call.this,QrGenerator.class);
                startActivity(intent);
                Toast.makeText(Customer_Single_call.this, "hello", Toast.LENGTH_SHORT).show();*/
               // Toast.makeText(Customer_Single_call.this, "hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Customer_Single_call.this,QrGenerator.class).putExtra("id",gid));
               // Toast.makeText(Customer_Single_call.this, "hello1", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
