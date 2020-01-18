package com.example.currencyconverter.Retrofit;


import com.example.currencyconverter.modal.News_Resp_Modal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {


    @GET
    Call<Object> convert_Currency(@Url String url);

    @GET
    Call<Object> fetch_Global_CUrrency(@Url String url);

    @FormUrlEncoded
    @POST
    Call<Object> fetch_GraphData(@Url String url,
                                 @Field("cur") String cur,
                                 @Field("dater") String dater);


    @FormUrlEncoded
    @POST("http://convertedcurrency.com/admin/fetch_blog.php")
    Call<News_Resp_Modal> get_News_List(@Field("user_id") String user_id);


}
