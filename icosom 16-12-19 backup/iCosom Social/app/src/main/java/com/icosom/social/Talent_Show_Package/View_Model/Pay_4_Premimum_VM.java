package com.icosom.social.Talent_Show_Package.View_Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;
import com.icosom.social.Talent_Show_Package.Modal.Premium_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Pay_4_Premimum_VM extends ViewModel {
    MutableLiveData<Premium_Response> list;

    ApiInterface apiInterface;

    public MutableLiveData<Premium_Response> make_user_premium(String user_id, String amount) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        list = new MutableLiveData<Premium_Response>();
        make_user_to_Premium_API(user_id, amount);
        return list;
    }

    public MutableLiveData<Premium_Response> check_Premium_USer(String user_id) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        list = new MutableLiveData<Premium_Response>();
        check_Premium_User_API(user_id);
        return list;
    }


    public void make_user_to_Premium_API(String user_id, String amount) {
        Call<Premium_Response> call = apiInterface.make_user_To_Premium(user_id, amount);
        call.enqueue(new Callback<Premium_Response>() {
            @Override
            public void onResponse(Call<Premium_Response> call, Response<Premium_Response> response) {


                System.out.println("Put_Rating_API_Response" + response.code());
                if (response.isSuccessful()) {

                    Premium_Response model = response.body();
                   // System.out.println("put_rating_Msg" + model.getMsg());


                    list.setValue(model);
                }

            }

            @Override
            public void onFailure(Call<Premium_Response> call, Throwable t) {
                System.out.println("Failed_to_make_premium" + t.getMessage());
                call.cancel();

            }
        });


    }


    public void check_Premium_User_API(String user_id) {
        Call<Premium_Response> call = apiInterface.check_User_Premium(user_id);
        call.enqueue(new Callback<Premium_Response>() {
            @Override
            public void onResponse(Call<Premium_Response> call, Response<Premium_Response> response) {


                System.out.println("Check_PRemium_API_Response" + response.code());
                if (response.isSuccessful()) {

                    Premium_Response model = response.body();
                   // System.out.println("put_rating_Msg" + model.getMsg());


                    list.setValue(model);
                }

            }

            @Override
            public void onFailure(Call<Premium_Response> call, Throwable t) {
                System.out.println("Failed_to_check_premium" + t.getMessage());
                call.cancel();

            }
        });


    }


}
