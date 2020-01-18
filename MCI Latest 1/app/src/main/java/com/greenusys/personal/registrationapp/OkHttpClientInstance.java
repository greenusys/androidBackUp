package com.greenusys.personal.registrationapp;

import okhttp3.OkHttpClient;

/**
 * Created by personal on 2/20/2018.
 */

public class OkHttpClientInstance {

    private static final Object LOCK = new Object();

    private static OkHttpClient sInstance;

    public static OkHttpClient getInstance()
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                sInstance = new OkHttpClient();
            }
        }

        return sInstance;
    }
}
