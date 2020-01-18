package com.example.digitalforgeco.View_Model;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalforgeco.Retrofit.ApiClient;
import com.example.digitalforgeco.Retrofit.ApiInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fetch_Tracking_Response_VM extends ViewModel {

    MutableLiveData<String> list;
    ApiInterface apiInterface;
    public MutableLiveData<String> getTrackingData(String url) {

     list = new MutableLiveData<String>();

         apiInterface = ApiClient.getClient().create(ApiInterface.class);


        get_Tracking_Data(url);


        return list;
    }


    public void get_Tracking_Data(String url) {

        System.out.println("tracking_url" + url);
        Call<JsonObject> call = apiInterface.getTrackingData(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject postResponse = response.body();


                list.setValue(postResponse.toString());

                System.out.println("postResponse_api" + postResponse);





            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Failed" + t.getMessage());
                list.setValue(null);
                call.cancel();

            }
        });


    }


}
