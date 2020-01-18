package com.example.currencyconverter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.Country_Adapter;
import com.example.currencyconverter.modal.Country_Modal;
import com.example.currencyconverter.modal.Data;
import com.example.menu_library.animation.GuillotineAnimation;
import com.example.menu_library.interfaces.GuillotineListener;

import java.util.ArrayList;

public class Show_Country_List extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 100;
    RecyclerView country_rv;
    Country_Adapter adapter;
    ArrayList<Country_Modal> country_list = new ArrayList<>();
    ArrayList<String> country_img_list = new ArrayList<>();
    SearchView searchView;
    Toolbar toolbar;
    FrameLayout root;
    LinearLayout clear_history,home;
    View contentHamburger;
    GuillotineAnimation aniMenu;
    private String value;
    private boolean isGuillotineOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__country);

        value = getIntent().getStringExtra("value");
        setCountryNames();
        initViews();

        setMenus();
    }

    private void initViews() {
        country_rv = findViewById(R.id.country_rv);
        searchView = findViewById(R.id.search_view);
        adapter = new Country_Adapter(this, country_list, value);
        country_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        country_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        toolbar = findViewById(R.id.toolbar);
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);


        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        //searchView.onActionViewExpanded();//open search box
        //searchView.setQueryHint("Search Country");


        return super.onCreateOptionsMenu(menu);
    }

    public void open(View view) {


    }


    public void setCountryNames() {

        for (int i = 0; i < Data.country_name.length; i++) {     //old one
            //country_list.add(new Country_Modal(Data.country_name[i],"https://www.countryflags.io/"+Data.country_code[i].substring(0,2)+"/flat/32.png",Data.country_code[i]));
            country_list.add(new Country_Modal(Data.country_name[i], "https://www.countryflags.io/" + Data.country_code1[i] + "/flat/32.png", Data.country_code[i], Data.country_code1[i]));

        }

    }


    private void setMenus() {
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
        root.addView(guillotineMenu);

        clear_history= guillotineMenu.findViewById(R.id.clear_history);
        home= guillotineMenu.findViewById(R.id.home);
        clear_history.setVisibility(View.GONE);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        GuillotineAnimation.GuillotineBuilder guillotineBuilder = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger);

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
        guillotineBuilder.setStartDelay(RIPPLE_DURATION);
        guillotineBuilder.setActionBarViewForAnimation(toolbar);
        guillotineBuilder.setClosedOnStart(true);

        aniMenu = guillotineBuilder.build();

        guillotineMenu.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        System.out.println("check" + !isGuillotineOpened);
        if (!isGuillotineOpened) {
            super.onBackPressed();
        }
        aniMenu.close();
    }
}

