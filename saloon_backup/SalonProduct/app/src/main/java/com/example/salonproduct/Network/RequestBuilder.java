package com.example.salonproduct.Network;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Allen on 12/28/2017.
 */

public class RequestBuilder {

    public static RequestBody twoParameter(String key_a, String value_a, String key_b, String value_b){
        Log.e("body ", "doInBackground: "+value_a +value_b );
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .build();
    }
    public static RequestBody Parameter(String key_a, String value_a){
        Log.e("body ", "doInBackground: "+value_a);
        return new FormBody.Builder()
                .add(key_a,value_a)
                .build();
    }
    public static RequestBody threeParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c){
        Log.e("body ", "RequestBuilder: "+value_a +value_b+value_c  );
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c,value_c)
                .build();
    }
    public static RequestBody fourParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d){
        Log.e("body ", "RequestBuilder: "+value_a +value_b+value_c +value_d );
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c,value_c)
                .add(key_d,value_d)
                .build();
    }

    public static RequestBody sixParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e, String key_f, String value_f){
        Log.e("body ", "RequestBuilder: "+value_a +value_b+value_c +value_d+value_e +value_f );
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c,value_c)
                .add(key_d,value_d)
                .add(key_e,value_e)
                .add(key_f,value_f)
                .build();
    }

    public static RequestBody timeTableImport(String dataSource, String dataSourceValue,
                                              String classId, String classIdValue)
    {
        return new FormBody.Builder()
                .add(dataSource, dataSourceValue)
                .add(classId, classIdValue)
                .build();
    }


}
