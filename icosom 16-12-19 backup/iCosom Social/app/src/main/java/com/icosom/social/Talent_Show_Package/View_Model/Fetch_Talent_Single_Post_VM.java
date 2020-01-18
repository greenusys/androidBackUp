package com.icosom.social.Talent_Show_Package.View_Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;
import com.icosom.social.Talent_Show_Package.Activity.Talent_Show_Single_Post;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fetch_Talent_Single_Post_VM extends ViewModel {
    MutableLiveData<ArrayList<Singing_Model.Singing_Model_List>> list;

    ApiInterface apiInterface;

    public LiveData<ArrayList<Singing_Model.Singing_Model_List>> get_singel_Post(String tal_id,String user_id)
    {
        if(list==null )
        {
            apiInterface= ApiClient.getClient().create(ApiInterface.class);
            list=new MutableLiveData<ArrayList<Singing_Model.Singing_Model_List>>();
            getSinginPostAPI(tal_id,user_id);

        }



        return list;
    }


    public void getSinginPostAPI(String tal_id,String user_id)
    {
        System.out.println("get_Single_post_called"+tal_id+" "+user_id);

        Call<Singing_Model> call=apiInterface.fetch_single_post(tal_id,user_id);
        call.enqueue(new Callback<Singing_Model>() {
            @Override
            public void onResponse(Call<Singing_Model> call, Response<Singing_Model> response) {

                if(response.isSuccessful()) {

                    Singing_Model singing_model = response.body();


                    if (singing_model.getStatus().equals("1")) {
                        ArrayList<Singing_Model.Singing_Model_List> singing_list = singing_model.data;
                        list.setValue(singing_list);

                        System.out.println("api_size" + singing_list.size());
                    }

                }

            }

            @Override
            public void onFailure(Call<Singing_Model> call, Throwable t) {
                System.out.println("Failed"+t.getMessage()+"\n"+t.getStackTrace());
                call.cancel();

            }
        });



    }


}
