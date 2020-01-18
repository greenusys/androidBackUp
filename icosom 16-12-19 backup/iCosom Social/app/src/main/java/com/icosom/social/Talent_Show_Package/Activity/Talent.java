package com.icosom.social.Talent_Show_Package.Activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.icosom.social.R;

public class Talent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent);




    }

    public void goto_singing_activity(View view) {
        startActivity(new Intent(getApplicationContext(),Singing_And_Dancing_Activity.class).putExtra("value","fetchSinging"));
    }

    public void goto_dancing_activity(View view) {
        startActivity(new Intent(getApplicationContext(),Singing_And_Dancing_Activity.class).putExtra("value","fetchDancing"));

    }

    public void goto_comedy_activity(View view) {
        startActivity(new Intent(getApplicationContext(),Singing_And_Dancing_Activity.class).putExtra("value","fetchComedy"));
    }

    public void goto_gk_activity(View view) {
        startActivity(new Intent(getApplicationContext(),General_knowledge.class).putExtra("value","fetchgk"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Talent_Plan.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

    public void back_activity(View view) {
        startActivity(new Intent(getApplicationContext(), Talent_Plan.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }
}
