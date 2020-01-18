package com.greenusys.personal.registrationapp;

import okhttp3.OkHttpClient;

/**
 * Created by personal on 2/20/2018.
 */

public class ProviderUtilities {

    public static OkHttpClient providesOkhttpClient()
    {
        return OkHttpClientInstance.getInstance();
    }
}
