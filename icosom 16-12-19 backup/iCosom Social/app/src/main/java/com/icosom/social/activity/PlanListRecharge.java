package com.icosom.social.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.icosom.social.R;
import com.icosom.social.fragment.RechargePlan;

import java.util.ArrayList;
import java.util.List;

public class PlanListRecharge extends AppCompatActivity {
    String opid,cirid,type,mob;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity( new Intent(PlanListRecharge.this,DashboardRecharge.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list_recharge);

        opid = getIntent().getStringExtra("opid");
        cirid = getIntent().getStringExtra("cirid");
        type = getIntent().getStringExtra("type");
        mob = getIntent().getStringExtra("num");

       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpagers);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabss);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RechargePlan("TUP",opid,cirid,mob), "TUP");
        adapter.addFrag(new RechargePlan("2G",opid,cirid,mob), "2G");
        adapter.addFrag(new RechargePlan("3G",opid,cirid,mob), "3G");
        adapter.addFrag(new RechargePlan("FTT",opid,cirid,mob), "FTT");
        adapter.addFrag(new RechargePlan("LSC",opid,cirid,mob), "LSC");
        adapter.addFrag(new RechargePlan("OTR",opid,cirid,mob), "OTR");
        adapter.addFrag(new RechargePlan("RMG",opid,cirid,mob), "RMG");
        adapter.addFrag(new RechargePlan("SMS",opid,cirid,mob), "SMS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
