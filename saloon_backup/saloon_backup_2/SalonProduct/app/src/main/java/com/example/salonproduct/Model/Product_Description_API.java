package com.example.salonproduct.Model;

import android.util.Log;

import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product_Description_API implements ProductDescription_Model {


    ProductDescription_PresenterImpl presenter;


    public static ArrayList<Review_Model> reviewModels2 = new ArrayList<>();
    public Product_Description_API(ProductDescription_PresenterImpl presenter) {
    this.presenter=presenter;
    }


    @Override
    public void load_Reviews() {

        if(reviewModels2!=null)
            reviewModels2.clear();

        Review_Model review_model=new Review_Model("1","Good Product","4");
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        reviewModels2.add(review_model);
        presenter.success_review_loaded(reviewModels2);
    }
}
