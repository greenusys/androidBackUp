package com.example.salonproduct.Model;

import android.util.Log;

import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.Presenter.View_Cart_Presenter;
import com.example.salonproduct.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class View_Cart_API implements View_Cart_Model {

    ArrayList<Cart_Model> cart_models = new ArrayList<>();
    View_Cart_Presenter presenter;

    public View_Cart_API(View_Cart_Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void load_Cart_Items() {

        Cart_Model cart_model1=new Cart_Model("Habibs Hair conditioner (400 ml.)",3000,3000,"image",1,45);
        Cart_Model cart_model2=new Cart_Model("Habibs Hair conditioner (400 ml.)",3000,3000,"image",2,45);
        Cart_Model cart_model3=new Cart_Model("Habibs Hair conditioner (400 ml.)",3000,3000,"image",3,45);
        cart_models.add(cart_model1);
        cart_models.add(cart_model2);
        cart_models.add(cart_model3);
        presenter.success_cart_items_loaded(cart_models);



    }
}
