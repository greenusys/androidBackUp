package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.pager_adapter.HowToPagerAdapter;

public class HowToActivity extends AppCompatActivity
{
    ViewPager vp_howTo;
    LinearLayout lay_indicator;
    LinearLayout.LayoutParams params;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        if (sp.getBoolean("FirstTimeHowTo", false))
        {
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
            return;
        }

        edt.putBoolean("FirstTimeHowTo", true);
        edt.commit();

        vp_howTo = (ViewPager) findViewById(R.id.vp_howTo);
        lay_indicator = (LinearLayout) findViewById(R.id.lay_indicator);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);

        vp_howTo.setAdapter(new HowToPagerAdapter(getSupportFragmentManager()));

        addIndicator();

        vp_howTo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((TextView) findViewById(R.id.txt_signUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class).
                        putExtra("login", false));
                finish();
            }
        });

        ((TextView) findViewById(R.id.txt_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class).
                        putExtra("login", true));
                finish();
            }
        });
    }

    private void addIndicator() {
        lay_indicator.removeAllViews();

        for (int i = 0; i < 3; i++)
        {
            ImageView iv = new ImageView(getBaseContext());
            iv.setLayoutParams(params);
            iv.setBackgroundResource(vp_howTo.getCurrentItem()==i?R.drawable.shape_oval_white:R.drawable.shape_oval_grey);
            lay_indicator.addView(iv);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}