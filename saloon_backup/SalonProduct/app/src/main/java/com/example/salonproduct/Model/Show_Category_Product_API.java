package com.example.salonproduct.Model;

import android.util.Log;

import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.Presenter.Show_Category_Products_Presenter;
import com.example.salonproduct.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Show_Category_Product_API implements Show_Category_Product_Model {


    Show_Category_Products_Presenter homePresenter;
    ArrayList<Categre_model> categre_models = new ArrayList<>();
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    public Show_Category_Product_API(Show_Category_Products_Presenter presenter) {
        this.homePresenter=presenter;
    }

    @Override
    public void load_Category_Products()
    {


        Prodect_Model prodect_model = new Prodect_Model("nasdf", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Hair", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Color", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Nivia", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);

        homePresenter.success_Category_Products_loaded(prodect_models);





    }






}
