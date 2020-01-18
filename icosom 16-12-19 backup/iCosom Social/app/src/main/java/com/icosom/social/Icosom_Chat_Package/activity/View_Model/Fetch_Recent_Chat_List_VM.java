package com.icosom.social.Icosom_Chat_Package.activity.View_Model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.icosom.social.Icosom_Chat_Package.activity.Modal.Recent_Chat_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fetch_Recent_Chat_List_VM extends ViewModel {
    MutableLiveData<ArrayList<Recent_Chat_Model>> list;

    ApiInterface apiInterface;

    public LiveData<ArrayList<Recent_Chat_Model>> getRecentChat(String sender_id)
    {
        if(list==null)
        {
            System.out.println("list_is_null");
            apiInterface= ApiClient.getClient().create(ApiInterface.class);
            list=new MutableLiveData<ArrayList<Recent_Chat_Model>>();
            getFrindList2(sender_id);
        }
        else
        System.out.println("list_is_not_null");

        return list;
    }


    public void getFrindList2(String sender_id)
    {
        System.out.println("katrina_id"+sender_id);

        Call<ArrayList<Recent_Chat_Model>> call=apiInterface.getRecentChatList(sender_id,"1");
        call.enqueue(new Callback<ArrayList<Recent_Chat_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Recent_Chat_Model>> call, Response<ArrayList<Recent_Chat_Model>> response) {

                System.out.println("response_kaif"+response.code());

                ArrayList<Recent_Chat_Model> friend_list = response.body();

               if(friend_list!=null && friend_list.size()>0)
                {
                    list.setValue(friend_list);
                    System.out.println("kaif_list"+friend_list.size());
                }
               else
                   list.setValue(null);

            }

            @Override
            public void onFailure(Call<ArrayList<Recent_Chat_Model>> call, Throwable t) {
                System.out.println("Failed"+t.getMessage());
                list.setValue(null);
                call.cancel();

            }
        });



    }


}
