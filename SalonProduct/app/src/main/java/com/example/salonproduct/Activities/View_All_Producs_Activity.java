package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.salonproduct.Adapter.Prodect_Adapter;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.Model.Show_Category_Presenter_Impl;
import com.example.salonproduct.Model.View_All_PresenterImpl;
import com.example.salonproduct.R;
import com.example.salonproduct.View.View_All_View;

import java.util.ArrayList;

public class View_All_Producs_Activity extends AppCompatActivity  implements View_All_View {

    String category_name;
    RecyclerView prodect_rv;
    TextView category_title;
    View_All_PresenterImpl view_all_presenter;
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    Prodect_Adapter prodect_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all_);

        initviews();
        view_all_presenter = new View_All_PresenterImpl(this);
        view_all_presenter.fetch_view_all_Products();

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
    public void setDataToView_ALl_Prodcuts_RecylerView(ArrayList<Prodect_Model> prodect_models) {
        this.prodect_models = prodect_models;
        prodect_adapter = new Prodect_Adapter(this, prodect_models);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        prodect_rv.setLayoutManager(gridLayoutManager);
        prodect_rv.setAdapter(prodect_adapter);
        prodect_rv.setNestedScrollingEnabled(false);
        prodect_adapter.notifyDataSetChanged();

    }

    public void goto_view_cart(View view) {
        startActivity(new Intent(getApplicationContext(), View_Cart.class));
    }
}