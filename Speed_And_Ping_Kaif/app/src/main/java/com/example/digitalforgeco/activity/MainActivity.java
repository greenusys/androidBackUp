package com.example.digitalforgeco.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.digitalforgeco.R;
import com.example.digitalforgeco.fragments.DNS_Fragment;
import com.example.digitalforgeco.fragments.Security_Fragment;
import com.example.digitalforgeco.fragments.Signal_Fragment;
import com.example.digitalforgeco.fragments.Speed_Fragment;
import com.example.digitalforgeco.fragments.Wifi_Fragment;
import com.example.digitalforgeco.room_db_package.repository.NoteRepository;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final long RIPPLE_DURATION = 100;
    GuillotineAnimation aniMenu;
    private View guillotineMenu;
    private boolean isGuillotineOpened;
    private TabLayout tabLayout;
    private ViewPager viewPage;
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setMenus();
        initViews();
        set_Up_Tab_Text_And_Icons();
    }


    private void initViews() {

        noteRepository = new NoteRepository(getApplicationContext());

        viewPage = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPage);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPage);
    }

    private void set_Up_Tab_Text_And_Icons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.speedometer);
        tabLayout.getTabAt(1).setIcon(R.drawable.signal);
        tabLayout.getTabAt(2).setIcon(R.drawable.wifi);
        tabLayout.getTabAt(3).setIcon(R.drawable.dns);
        tabLayout.getTabAt(4).setIcon(R.drawable.security);

        tabLayout.getTabAt(0).setText(R.string.speed);
        tabLayout.getTabAt(1).setText(R.string.signal);
        tabLayout.getTabAt(2).setText(R.string.wifi);
        tabLayout.getTabAt(3).setText(R.string.dns);
        tabLayout.getTabAt(4).setText(R.string.security);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Speed_Fragment(), "ONE");
        adapter.addFrag(new Signal_Fragment(), "TWO");
        adapter.addFrag(new Wifi_Fragment(), "THREE");
        adapter.addFrag(new DNS_Fragment(), "Four");
        adapter.addFrag(new Security_Fragment(), "Five");
        viewPager.setAdapter(adapter);
    }

    public void clear_history(View view) {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteRepository.Delete_ALL_History_Record();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                if (MainActivity.this != null)
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            aniMenu.close();

                        }
                    });


                return null;
            }
        }.execute();


    }

    private void setMenus() {

        FrameLayout root;
        View contentHamburger;
        Toolbar toolbar = null;


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        root = findViewById(R.id.coor);
        contentHamburger = findViewById(R.id.content_hamburger);

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
        root.addView(guillotineMenu);


        GuillotineAnimation.GuillotineBuilder guillotineBuilder = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger);
        guillotineBuilder.setStartDelay(RIPPLE_DURATION);
        guillotineBuilder.setActionBarViewForAnimation(toolbar);
        guillotineBuilder.setGuillotineListener(new GuillotineListener() {
            @Override
            public void onGuillotineOpened() {
                isGuillotineOpened = true;

            }

            @Override
            public void onGuillotineClosed() {
                isGuillotineOpened = false;

            }
        });

        guillotineBuilder.setClosedOnStart(true);
        aniMenu = guillotineBuilder.build();
        //aniMenu.close();


    }

    @Override
    public void onBackPressed() {

        System.out.println("check" + !isGuillotineOpened);
        if (!isGuillotineOpened) {
            super.onBackPressed();
        } else
            aniMenu.close();
    }

    public void rate(View view) {
        show_Rate_Me_Dialoge();
    }

    private void show_Rate_Me_Dialoge() {

        close_Menu();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this,R.style.RoundShapeTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.rate_me_layout, null);
        TextView rate_me=dialogLayout.findViewById(R.id.rate_me_text);
        builder.setView(dialogLayout);
        builder.show();
    }

    public void share(View view) {

        close_Menu();

        String post_link = "";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Share the app to your friends";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Speed And Ping ");
        intent.putExtra(Intent.EXTRA_TEXT, post_link);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    public void about_us(View view) {

        close_Menu();
        startActivity(new Intent(getApplicationContext(), About_us.class));


    }


    public void close_Menu() {
        if (MainActivity.this != null)
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    aniMenu.close();
                }
            });
    }

    public void feedback(View view) {

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
