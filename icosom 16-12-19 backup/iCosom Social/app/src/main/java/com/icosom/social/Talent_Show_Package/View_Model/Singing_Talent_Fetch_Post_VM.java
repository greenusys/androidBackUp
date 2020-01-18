package com.icosom.social.Talent_Show_Package.View_Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Singing_Talent_Fetch_Post_VM extends ViewModel {
    MutableLiveData<ArrayList<Singing_Model.Singing_Model_List>> list;

    ApiInterface apiInterface;

    public LiveData<ArrayList<Singing_Model.Singing_Model_List>> getSinginPost(String talent_option,String user_id,boolean from_swipe)
    {
        if(list==null && from_swipe==false)
        {
            System.out.println("list_is_null"+talent_option);
            apiInterface= ApiClient.getClient().create(ApiInterface.class);
            list=new MutableLiveData<ArrayList<Singing_Model.Singing_Model_List>>();
            getSinginPostAPI(talent_option,user_id);
        }

        else {
            System.out.println("list_is_not_null"+talent_option);
            apiInterface= ApiClient.getClient().create(ApiInterface.class);
            list=new MutableLiveData<ArrayList<Singing_Model.Singing_Model_List>>();
            getSinginPostAPI(talent_option,user_id);

        }


        return list;
    }


    public void getSinginPostAPI(final String talent_option, String user_id)
    {
        System.out.println("champ"+talent_option+user_id);
        Call<Singing_Model> call=apiInterface.fetch_talent_post(talent_option,"18");
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
                System.out.println("Failed_"+talent_option+t.getMessage()+"\n"+t.getStackTrace());
                call.cancel();

            }
        });



    }


}
