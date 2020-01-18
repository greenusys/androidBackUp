package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.greenusys.customerservice.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Eng_single_view extends AppCompatActivity {
    private IntentIntegrator qrScan;
    Button start, end;
    TextView name, phone, address, queryid, query, date;
    String sname, sphone, saddress, squeryid, squery, sdate, sid,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_single_view);
        qrScan = new IntentIntegrator(this);

        name = findViewById(R.id.ce_u_name);
        phone = findViewById(R.id.ce_mob);
        address = findViewById(R.id.ce_address);
        queryid = findViewById(R.id.ce_id);
        query = findViewById(R.id.ce_query);
        date = findViewById(R.id.ce_q_date);
        start = findViewById(R.id.start_service);
        end = findViewById(R.id.end_service);
        sname = getIntent().getStringExtra("cname");
        sphone = getIntent().getStringExtra("cmobile");
        saddress = getIntent().getStringExtra("caddress");
        squeryid = getIntent().getStringExtra("cqid");
        squery = getIntent().getStringExtra("cquery");
        sdate = getIntent().getStringExtra("cdate");
        sid = getIntent().getStringExtra("pid");
        status = getIntent().getStringExtra("status");
        if (status.equalsIgnoreCase("1")) {
          start.setVisibility(View.GONE);

        } else if (status.equalsIgnoreCase("2")) {
            start.setVisibility(View.GONE);
            end.setVisibility(View.GONE);

        } else {
            start.setVisibility(View.VISIBLE);
            end.setVisibility(View.VISIBLE);

        }


        name.setText("Customer name  : " + sname);
        phone.setText("Customer Phone  : " + sphone);
        address.setText("Customer Address  : " + saddress);
        queryid.setText("Enquiry Id  : " + squeryid);
        query.setText("Customer Query  : " + squery);
        date.setText("Date  : " + sdate);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Eng_single_view.this,Scan_Barcode.class).putExtra("val","1"));
            }
        });

        //Jain shab ka code hai its working perfectly
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Eng_single_view.this,Scan_Barcode.class).putExtra("val","2"));

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
