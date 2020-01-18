package com.icosom.social;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icosom.social.activity.DashboardRecharge;

public class Pay_Comfirmation_Activity extends AppCompatActivity {

    TextView textView13,textView14,textView15,textView16,textView17,textView18,textView11,textView12;
    String user_device_token,user_id,user_name,user_email,friend_id,friend_name,friend_email,friend_mobile,amount,total_discount,amount_to_be_paid;
    Button button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__comfirmation_);

        textView13=findViewById(R.id.textView13);
        textView14=findViewById(R.id.textView14);
        textView15=findViewById(R.id.textView15);
        textView16=findViewById(R.id.textView16);
        textView17=findViewById(R.id.textView17);
        textView18=findViewById(R.id.textView18);

        textView11=findViewById(R.id.textView11);
        textView12=findViewById(R.id.textView12);

        button6=findViewById(R.id.button6);

        if(getIntent().getStringExtra("friend_id")!=null)
            friend_id=  getIntent().getStringExtra("friend_id");

        if(getIntent().getStringExtra("friend_name")!=null)
            friend_name=  getIntent().getStringExtra("friend_name");

        if(getIntent().getStringExtra("friend_email")!=null)
            friend_email=  getIntent().getStringExtra("friend_email");

        if(getIntent().getStringExtra("friend_mobile")!=null)
            friend_mobile=  getIntent().getStringExtra("friend_mobile");

        if(getIntent().getStringExtra("amount")!=null)
            amount=  getIntent().getStringExtra("amount");

        if(getIntent().getStringExtra("total_discount")!=null)
            total_discount=  getIntent().getStringExtra("total_discount");

        if(getIntent().getStringExtra("amount_to_be_paid")!=null)
            amount_to_be_paid=  getIntent().getStringExtra("amount_to_be_paid");



        if(getIntent().getStringExtra("user_id")!=null)
            user_id=  getIntent().getStringExtra("user_id");

        if(getIntent().getStringExtra("user_email")!=null)
            user_email=  getIntent().getStringExtra("user_email");

        if(getIntent().getStringExtra("friend_id")!=null)
            friend_id=  getIntent().getStringExtra("friend_id");

        if(getIntent().getStringExtra("user_device_token")!=null)
            user_device_token=  getIntent().getStringExtra("user_device_token");


        Log.e("user_device_token",""+user_device_token);


        textView13.setText(friend_name);
        textView14.setText(friend_email);
        textView15.setText(friend_mobile);
        textView16.setText(amount+" Rs");


        Log.e("katrina_amount",""+amount);


        if(amount_to_be_paid!=null && total_discount!=null) {
            textView17.setText(total_discount + " Rs");
            textView18.setText(amount_to_be_paid + " Rs");
        }
        else
            {

                textView17.setVisibility(View.INVISIBLE);
                textView18.setVisibility(View.INVISIBLE);
                textView11.setVisibility(View.INVISIBLE);
                textView12.setVisibility(View.INVISIBLE);
        }


        //pay now button
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pay_Comfirmation_Activity.this, DashboardRecharge.class);
                intent.putExtra("friend_id",friend_id);
                intent.putExtra("friend_email",friend_email);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_name",friend_name);//friend name
                intent.putExtra("user_device_token",user_device_token);//user_device_token

                //if(amount==null && total_discount==null)
                intent.putExtra("amount",amount);
               // else
                   // intent.putExtra("amount",amount_to_be_paid);


                Log.e("friend_id",""+friend_id);
                Log.e("friend_email",""+friend_email);
                Log.e("user_id",""+user_id);
                Log.e("user_email",""+user_email);
                Log.e("friend_name",""+friend_name);
                Log.e("amount_to_be_paid",""+amount_to_be_paid);
                Log.e("total_discount",""+total_discount);
                Log.e("amount",""+amount);


               startActivity(intent);

            }
        });




    }
}
