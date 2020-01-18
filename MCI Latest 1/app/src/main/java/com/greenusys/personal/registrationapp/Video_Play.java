package com.greenusys.personal.registrationapp;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;



public class Video_Play extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__play);


       String video_name=getIntent().getStringExtra("video_name");
        //String video_path="http://greenusys.website/mci/uploads/test_video/2019032763830.mp4";
        String video_path="http://greenusys.website/mci/uploads/test_video/";


        System.out.print("video_pathkaif"+video_path);



        VideoView videoView =(VideoView)findViewById(R.id.videoView1);

        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
     //   Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
        Uri uri=Uri.parse(video_path+video_name);


        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }





}
