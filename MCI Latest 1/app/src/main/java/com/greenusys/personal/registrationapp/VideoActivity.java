package com.greenusys.personal.registrationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.appunite.appunitevideoplayer.PlayerActivity;


public class VideoActivity extends AppCompatActivity {

    VideoView mVideoView;
    MediaController mediaController;

    private static final String VIDEO_URI = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        /**Uri uri = Uri.parse(VIDEO_URI);

         mVideoView = (VideoView)findViewById(R.id.video_view);

         mediaController = new MediaController(this);

         mediaController.setAnchorView(mVideoView);

         mVideoView.setMediaController(mediaController);

         mVideoView.setVideoURI(uri);

         mVideoView.requestFocus();

         mVideoView.start();*/



    }


    public void playVideo(View view) {

        startActivity(PlayerActivity.getVideoPlayerIntent(VideoActivity.this,VIDEO_URI,"Muse"));

    }
}
