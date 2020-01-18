package com.icosom.social.Talent_Show_Package.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Adapter.Play_Audio_Adapter;
import com.icosom.social.Talent_Show_Package.Modal.Recording_Model;

import java.util.ArrayList;

public class Play_Audio_Activity extends AppCompatActivity {


    Play_Audio_Adapter adapter;
    ArrayList<String> voicePath;
    ArrayList<Recording_Model> recording_list = new ArrayList<>();
    private RecyclerView rv_voice;
    public static boolean destroy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__audio_);

        voicePath = new ArrayList<String>(getIntent().getStringArrayListExtra("voice"));

        String file_name = getIntent().getStringExtra("name");
        String name[] = file_name.split("split");

        System.out.println("voicepath_size"+voicePath.size());
        System.out.println("name_size"+name.length);

        for (int i = 0; i < voicePath.size(); i++) {

            Recording_Model model = new Recording_Model();
            model.setFileName(voicePath.get(i));
            model.setAudio_name(name[i]);
            model.setPlaying(false);
            recording_list.add(model);
        }

        initViews();
        System.out.println("voicepath" + voicePath);


    }

    private void initViews() {
        rv_voice = findViewById(R.id.rv_voice);
        adapter = new Play_Audio_Adapter(getApplicationContext(), recording_list);
        rv_voice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv_voice.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        destroy=false;

    }

    public void back_activity(View view) {

        destroy=true;
        for (int i = 0; i < recording_list.size(); i++) {
            recording_list.get(i).setPlaying(false);
            recording_list.set(i, recording_list.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        destroy=true;

        System.out.println("on_back_called_play_audio");

        for (int i = 0; i < recording_list.size(); i++) {
            recording_list.get(i).setPlaying(false);
        }
        adapter.notifyDataSetChanged();

        super.onBackPressed();

    }
}
