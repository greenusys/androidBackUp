package com.icosom.social.Test;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.icosom.social.R;

import java.io.File;
import java.io.IOException;

public class Main extends Activity {
    RecyclerView rv_main;
    RecyclerView.LayoutManager layoutManager;
    MainAdapter adapter;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = findViewById(R.id.tv);
        tv.setText("Process not started");

        Process p = null;
        ProcessBuilder pb = new ProcessBuilder("do_foo.sh");
        pb.directory(new File("/home"));
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }







       /* rv_main = findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rv_main.setLayoutManager(layoutManager);
        adapter = new MainAdapter(getBaseContext());
        rv_main.setAdapter(adapter);*/



    }
}
