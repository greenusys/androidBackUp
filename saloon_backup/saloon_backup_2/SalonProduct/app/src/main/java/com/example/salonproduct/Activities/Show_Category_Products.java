package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.salonproduct.Adapter.Prodect_Adapter;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.Model.Show_Category_Presenter_Impl;
import com.example.salonproduct.R;
import com.example.salonproduct.View.Show_Category_Products_View;


import java.util.ArrayList;

public class Show_Category_Products extends AppCompatActivity implements Show_Category_Products_View {

    String category_name;
    RecyclerView prodect_rv;
    TextView category_title;
    Show_Category_Presenter_Impl show_category_presenter_;
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    Prodect_Adapter prodect_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__category__products);

        category_name=getIntent().getStringExtra("cat_name");

        initviews();

        show_category_presenter_=new Show_Category_Presenter_Impl(this);
        show_category_presenter_.fetch_Show_Category_Products();







    }

    private void initviews() {




        prodect_rv = findViewById(R.id.rv_prodect);

        //category_title = findViewById(R.id.category_title);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataToShowCategory_Prodcuts_RecylerView(ArrayList<Prodect_Model> prodect_models) {
        this.prodect_models = prodect_models;
        prodect_adapter = new Prodect_Adapter(this,prodect_models);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        prodect_rv.setLayoutManager(gridLayoutManager);
        prodect_rv.setAdapter(prodect_adapter);
        prodect_adapter.notifyDataSetChanged();

    }

    public void goto_view_cart(View view) {
        startActivity(new Intent(getApplicationContext(),View_Cart.class));
    }
}
