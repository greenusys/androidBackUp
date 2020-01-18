package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    TextView tvfname, tvedu1, tvphno, tvemail, tvrewardss, tvwallet;
    TextView q,c,e,r,w;

    ImageView imageView;
    String urls;
    private DatabaseHelper_Dash databaseHelper;
    private Edit edit;
    private final AppCompatActivity activity = Profile.this;
    String url = "http://vvn.city/apps/jain/new_login.php";
    String firstName, lastName, educationalDetails, phone, email, reward_point, wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = (ImageView) findViewById(R.id.ppic);
        databaseHelper = new DatabaseHelper_Dash(activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  Picasso.with(this).load("http://shubham.greenusys.website/vidya_1/student/student_images/2017082275246.jpg").into(imageView);
        tvfname = (TextView) findViewById(R.id.tvfname);
        //  tvfname.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        // tvlname = (TextView) findViewById(R.id.tvlname);
        tvedu1 = (TextView) findViewById(R.id.tvedu1);
        tvphno = (TextView) findViewById(R.id.tvphno);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvrewardss = (TextView) findViewById(R.id.tvrewardss);
        tvwallet = (TextView) findViewById(R.id.tvwallet);
        q = (TextView) findViewById(R.id.q);
        // q.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        c = (TextView) findViewById(R.id.c);
        // c.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        e = (TextView) findViewById(R.id.e);
        // e.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        r = (TextView) findViewById(R.id.r);
        //  r.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        w = (TextView) findViewById(R.id.w);
        //  w.setPaintFlags(tvfname.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        re();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent yy4=new Intent(Profile.this,MainActivity_Dash.class);
        startActivity(yy4);
    }


    private void re() {
        edit = new Edit();
        edit = databaseHelper.getUser();

        firstName = edit.getName();
        educationalDetails=edit.getLocation();
        phone=edit.getContact();
        email=edit.getEmail();
        reward_point=edit.getDate();
        wallet=edit.getTime();
        urls =edit.getAddress();
        Picasso.with(this).load(urls).transform(new CircleTransform()).into(imageView);
        tvfname.setText(""+firstName);
        tvedu1.setText(""+educationalDetails);
        tvphno.setText(""+phone);
        tvemail.setText(""+email);
        tvrewardss.setText(""+reward_point);
        tvwallet.setText(""+wallet);



    }
}
