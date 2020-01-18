package com.icosom.social.Icosom_Chat_Package.activity.View_Model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.icosom.social.Icosom_Chat_Package.activity.Modal.Friend_List_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fetch_Friend_VM extends ViewModel {
    MutableLiveData<ArrayList<Friend_List_Model.Friend_List_Model2>> list;

    ApiInterface apiInterface;

    public LiveData<ArrayList<Friend_List_Model.Friend_List_Model2>> getFrindList(String userID)
    {
        if(list==null)
        {
            System.out.println("list_is_null");

            apiInterface= ApiClient.getClient().create(ApiInterface.class);
            list=new MutableLiveData<>();
            getFrindList2(userID);
        }
        else
            System.out.println("list_is_not_null");

            return list;

    }


    public void getFrindList2(String userID)
    {
        Call<Friend_List_Model> call=apiInterface.getFriendList(userID);
        call.enqueue(new Callback<Friend_List_Model>() {
            @Override
            public void onResponse(Call<Friend_List_Model> call, Response<Friend_List_Model> response) {

                Friend_List_Model postResponse = response.body();

                if(postResponse.getStatus().equalsIgnoreCase("1"))
                {
                    ArrayList<Friend_List_Model.Friend_List_Model2> friend_list = postResponse.data;

                    list.setValue(friend_list);
                    System.out.println("kaif_list"+friend_list.size());

                }


            }

            @Override
            public void onFailure(Call<Friend_List_Model> call, Throwable t) {
                System.out.println("Failed");
                list.setValue(null);
                call.cancel();

            }
        });



    }


}
