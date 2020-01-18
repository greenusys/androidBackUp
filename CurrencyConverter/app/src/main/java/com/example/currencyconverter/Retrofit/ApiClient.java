package com.example.currencyconverter.Retrofit;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BaseURL="https://icosom.com/social/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }




}