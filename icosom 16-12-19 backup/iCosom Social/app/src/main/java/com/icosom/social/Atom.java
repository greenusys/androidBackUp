package com.icosom.social;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icosom.social.activity.Dash_fragement;
import com.icosom.social.activity.MainActivity;

public class Atom extends AppCompatActivity {
    Button net,card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atom);
        net = findViewById(R.id.net);
        card = findViewById(R.id.card);
        net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Atom.this,Net.class));

            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Atom.this,Card.class));


            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Atom.this, Dash_fragement.class).putExtra("type", "1"));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(Atom.this, MainActivity.class));
    }
}
