package com.greenusys.personal.registrationapp.Utility;

import android.support.multidex.MultiDexApplication;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class AppController extends MultiDexApplication
{
    private OkHttpClient client;
    private static AppController mInstance;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    @Override
    public void onCreate() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        super.onCreate();
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        mInstance = this;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public String GET(String url) throws IOException
    {
        System.out.println("URL_FEED: "+url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = getClient().newCall(request).execute();
        return response.body().string();
    }

    public String POST(String url, RequestBody body) throws IOException
    {
        System.out.println("URL_FEED: "+url);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = getClient().newCall(request).execute();
        return response.body().string();
    }

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

  /*  public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener)
    {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }*/
}