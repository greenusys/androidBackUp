package com.example.salonproduct.Model;

import android.util.Log;

import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.R;
import com.example.salonproduct.View.HomeView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home_Page_API implements HomeActivityModel {


    HomePresenter homePresenter;

    ArrayList<Categre_model> categre_models = new ArrayList<>();
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    public Home_Page_API(HomePresenter homePresenter) {
this.homePresenter=homePresenter;
    }

    @Override
    public void load_Category()
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        client.post("https://greenusys.com/salon/Categories" ,params,new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();


            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.e("hhh", "onResponse: " + result);

                try {
                    final JSONObject ja1 = new JSONObject(result);
                    if (ja1.getString("status").equals("1")) {

                        JSONArray jsonArray = ja1.getJSONArray("data");
                        JSONObject ja = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ja = jsonArray.getJSONObject(i);
                            Categre_model categre_model1 = new Categre_model(ja.getString("cat_name"), R.drawable.haircondition);
                            categre_models.add(categre_model1);
                            categre_models.add(categre_model1);
                            categre_models.add(categre_model1);
                            categre_models.add(categre_model1);
                            categre_models.add(categre_model1);
                            categre_models.add(categre_model1);
                        }

                        homePresenter.success_category_loaded(categre_models);




                    } else {


                    }

                    return;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error)
            {

            }


        });



    }

    @Override
    public void load_Products() {
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

        homePresenter.success_products_loaded(prodect_models);

    }

    @Override
    public void load_Slider_Images() {
      int[] img = {R.drawable.habib, R.drawable.amora, R.drawable.mac, R.drawable.himalaya, R.drawable.lotusbrand, R.drawable.habib};


      homePresenter.success_Slider_loaded(img);
    }


}
