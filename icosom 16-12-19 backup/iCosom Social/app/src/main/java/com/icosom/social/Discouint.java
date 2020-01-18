package com.icosom.social;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icosom.social.PayU.PayUMainActivity;

public class Discouint extends AppCompatActivity {

    TextView user_name,user_id,frnd_id,discount,amount,voucher;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discouint);

        user_name=findViewById(R.id.user_name);
        user_id=findViewById(R.id.user_id);
        frnd_id=findViewById(R.id.frnd_id);
        discount=findViewById(R.id.discount);
        amount=findViewById(R.id.amount);
        voucher=findViewById(R.id.voucher);
        pay=findViewById(R.id.pay);





        Log.e("name","name"+getIntent().getStringExtra("name"));
        Log.e("friend_id","friend_id"+getIntent().getStringExtra("frnd_id"));
        Log.e("userid","userid"+getIntent().getStringExtra("userid"));
        Log.e("discount","discount"+getIntent().getStringExtra("discount"));
        Log.e("amount","amount"+getIntent().getStringExtra("amount"));
        Log.e("voucher","voucher"+getIntent().getStringExtra("voucher"));

        user_name.setText(getIntent().getStringExtra("name"));
        user_id.setText(getIntent().getStringExtra("userid"));
        frnd_id.setText(getIntent().getStringExtra("frnd_id"));
        discount.setText(getIntent().getStringExtra("discount"));
        amount.setText(getIntent().getStringExtra("amount"));
        voucher.setText(getIntent().getStringExtra("voucher"));


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(Discouint.this, PayUMainActivity.class);
                intent.putExtra("AMOUNT",getIntent().getStringExtra("discount"));
                intent.putExtra("ADD_MONEY","true");
                startActivity(intent);


            }
        });


    }
}
