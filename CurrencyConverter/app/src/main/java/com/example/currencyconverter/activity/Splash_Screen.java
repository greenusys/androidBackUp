package com.example.currencyconverter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyconverter.R;
import com.example.currencyconverter.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash_Screen extends AppCompatActivity {
   // @BindView (R.id.imageView2)
   // ImageView mLogo;
    LinearLayout descimage,desctxt;

    Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash__screen);
       // ButterKnife.bind(this);
       // mLogo=findViewById(R.id.imageView2);

        descimage = (LinearLayout) findViewById(R.id.titleimage);
        desctxt = (LinearLayout) findViewById(R.id.titletxt);
        //uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
       // downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);

       // descimage.setAnimation(downtoup);
        //desctxt.setAnimation(uptodown);



       // RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //rotate.setDuration(2000);
        //rotate.setInterpolator(new LinearInterpolator());
       // mLogo.startAnimation(rotate);

if(Splash_Screen.this!=null)
        Splash_Screen.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });


        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }


}

