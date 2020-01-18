package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.greenusys.customerservice.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Scanner extends AppCompatActivity {
    private Button buttonscan;
    private IntentIntegrator qrscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        buttonscan= findViewById(R.id.buttonScan);
        buttonscan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Scanner.this,Scan_Barcode.class));
                    }
                }
        );
     /* buttonscan = findViewById(R.id.buttonScan);
      qrscan = new IntentIntegrator(this);
      buttonscan.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Something went wrong ", Toast.LENGTH_LONG).show();
            } else {

                try {
                    Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();

                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void onClick(View v) {
        qrscan.*///initiateScan();
    }
}