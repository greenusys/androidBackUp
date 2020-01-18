package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Queryy extends AppCompatActivity {
    CardView call;
    EditText e;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryy);
        b=(Button)findViewById(R.id.submit);
        call=(CardView)findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxx=new Intent(Intent.ACTION_DIAL);
                nxx.setData(Uri.parse("tel:9944553311"));
                startActivity(nxx);

            }
        });



    }
}
