package com.greenusys.customerservice.activity;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private static AppSingleton mInstance;
    private static RequestQueue requestQueue;
    private static Context mctx;

    private AppSingleton(Context context) {
        mctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestque(Request<T> request) {
        requestQueue.add(request);
    }
}
