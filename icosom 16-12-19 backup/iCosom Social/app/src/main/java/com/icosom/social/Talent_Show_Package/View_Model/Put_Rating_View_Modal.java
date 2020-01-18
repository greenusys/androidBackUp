package com.icosom.social.Talent_Show_Package.View_Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;
import com.icosom.social.Talent_Show_Package.Modal.Put_Rating_Modal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Put_Rating_View_Modal extends ViewModel {
    MutableLiveData<Put_Rating_Modal> list;

    ApiInterface apiInterface;

    public MutableLiveData<Put_Rating_Modal> put_Rating(String user_id, String talent_id, String rating) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        list = new MutableLiveData<Put_Rating_Modal>();
        put_Rating_API(user_id, talent_id, rating);


        return list;
    }


    public void put_Rating_API(String user_id, String talent_id, String rating) {
        Call<Put_Rating_Modal> call = apiInterface.put_rating(user_id, talent_id, rating);
        call.enqueue(new Callback<Put_Rating_Modal>() {
            @Override
            public void onResponse(Call<Put_Rating_Modal> call, Response<Put_Rating_Modal> response) {


                System.out.println("Put_Rating_API_Response" + response.code());
                if (response.isSuccessful()) {

                    Put_Rating_Modal model = response.body();
                    System.out.println("put_rating_Msg" + model.getMsg());


                }

            }

            @Override
            public void onFailure(Call<Put_Rating_Modal> call, Throwable t) {
                System.out.println("Failed" + t.getMessage());
                call.cancel();

            }
        });


    }


}
