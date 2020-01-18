package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.customerservice.R;

public class Customer_Payu extends AppCompatActivity {
    EditText etAmountAddMoney;
    TextView tvSubmitAddMoneyRecharge;
    String amount;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__payu);

        etAmountAddMoney = findViewById(R.id.etAmountAddMoney);

        tvSubmitAddMoneyRecharge = findViewById(R.id.tvSubmitAddMoneyRecharge);
        String c;
        Intent intent = getIntent();
        c = intent.getStringExtra("12");



        etAmountAddMoney.setText(c.toString());

        tvSubmitAddMoneyRecharge.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                amount = etAmountAddMoney.getText().toString().trim();

                Intent payUIntent = new Intent(Customer_Payu.this, PMainActivity.class);
                payUIntent.putExtra("AMOUNT", amount);
                payUIntent.putExtra("ADD_MONEY", "TRUE");
                startActivity(payUIntent);

            }
        });

    }
}
