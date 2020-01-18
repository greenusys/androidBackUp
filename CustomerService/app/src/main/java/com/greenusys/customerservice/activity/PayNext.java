package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.greenusys.customerservice.R;

public class PayNext extends AppCompatActivity {
    EditText etAmountAddMoney;
    TextView tvSubmitAddMoneyRecharge;
    String amount;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_next);

        etAmountAddMoney = findViewById(R.id.etAmountAddMoneys);

        tvSubmitAddMoneyRecharge = findViewById(R.id.tvSubmitAddMoneyRecharges);


        tvSubmitAddMoneyRecharge.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                amount = etAmountAddMoney.getText().toString().trim();

                Intent payUIntent = new Intent(PayNext.this, PMainActivity.class);
                payUIntent.putExtra("AMOUNT", amount);
                payUIntent.putExtra("ADD_MONEY", "TRUE");
                startActivity(payUIntent);

            }
        });

    }
}
