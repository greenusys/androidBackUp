package com.icosom.social;

import android.util.Log;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Allen on 12/28/2017.
 */

public class RequestBuilder {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");

    public static Request urlRequest(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }

    public static RequestBody singleParameter(String key_a, String value_a){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .build();
    }
    public static RequestBody NoParameter(){
        return new FormBody.Builder()
                .build();
    }
    public static RequestBody twoParameter(String key_a, String value_a, String key_b, String value_b){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .build();
    }


    public static RequestBody twoParameterTest(String key_a, String value_a, String key_b, String value_b){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .build();
    }

    public static RequestBody threeParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .build();
    }

    public static RequestBody fourParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d ){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .build();
    }

    public static RequestBody fiveParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e ){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .add(key_e, value_e)
                .build();
    }

    public static RequestBody sixParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e, String key_f, String value_f ){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .add(key_e, value_e)
                .add(key_f, value_f)
                .build();
    }
    public static RequestBody tenParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e, String key_f, String value_f, String key_g, String value_g, String key_h, String value_h , String key_i, String value_i, String key_j, String value_j){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .add(key_e, value_e)
                .add(key_f, value_f)
                .add(key_g, value_g)
                .add(key_h, value_h)
                .add(key_i, value_i)
                .add(key_j, value_j)
                .build();
    }
    public static RequestBody sevenParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e, String key_f, String value_f, String key_g, String value_g ){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .add(key_e, value_e)
                .add(key_f, value_f)
                .add(key_g, value_g)
                .build();
    }

    public static RequestBody eightParameter(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String value_d, String key_e, String value_e, String key_f, String value_f, String key_g, String value_g, String key_h, String value_h ){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .add(key_c, value_c)
                .add(key_d, value_d)
                .add(key_e, value_e)
                .add(key_f, value_f)
                .add(key_g, value_g)
                .add(key_h, value_h)
                .build();
    }

    public static MultipartBody threeParameterFile(String key_a, String value_a, String key_b, String value_b, String key_c, String fileName, File file) {

        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody requestBody = null;
        buildernew.addFormDataPart(key_a, value_a);
        buildernew.addFormDataPart(key_b, value_b);
        MediaType MEDIA_TYPE = MediaType.parse("image/" + fileName.substring(fileName.indexOf(".")+1));
        buildernew.addFormDataPart(key_c, fileName, RequestBody.create(MEDIA_TYPE, file));
        requestBody = buildernew.build();


        return requestBody;
    }

    public static MultipartBody fourParameterFile(String key_a, String value_a, String key_b, String value_b, String key_c, String value_c, String key_d, String[] fileName, File[] file) {

        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody requestBody = null;
        buildernew.addFormDataPart(key_a, value_a);
        buildernew.addFormDataPart(key_b, value_b);
        buildernew.addFormDataPart(key_c, value_c);
        for (int i=0; i<file.length; i++) {
            MediaType MEDIA_TYPE = MediaType.parse("image/" + fileName[i].substring(fileName[i].indexOf(".")+1));
            buildernew.addFormDataPart(key_d, fileName[i], RequestBody.create(MEDIA_TYPE, file[i]));
        }
        requestBody = buildernew.build();
        Log.e("AF", "onPostExecute:Request builder " + requestBody.toString() );
        Log.e("AF", "onPostExecute: Request Part Body" + requestBody.parts().get(0).body() );
        Log.e("AF", "onPostExecute: Request Part Header" + requestBody.parts().get(1) );

        return requestBody;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static RequestBody testRequestBody(String key_a, String value_a, String key_b, String value_b){
        return new FormBody.Builder()
                .add(key_a,value_a)
                .add(key_b,value_b)
                .build();


    }

    public static MultipartBody uploadRequestBody(String key_a, String value_a, String key_b, String value_b)//,
                                                  //String key_c, String value_c, String key_d, String value_d, String key_e,
                                                  //String value_e, String key_f, String value_f, String fileName, File file,
                                                  //String attachment_name)
    {
//        MediaType MEDIA_TYPE = MediaType.parse("image/" + fileName.substring(fileName.indexOf(".")+1));
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(key_a, value_a)
                .addFormDataPart(key_b, value_b)
                //.addFormDataPart(key_c, value_c)
                //.addFormDataPart(key_d, value_d)
                //.addFormDataPart(key_e, value_e)
                //.addFormDataPart(key_f, value_f)
//                .addFormDataPart(attachment_name, fileName, RequestBody.create(MEDIA_TYPE, file))
                .build();
    }

    public static MultipartBody uploadRequestBodyTwo(String userId, String description, String fileName, File file)
    {
        MediaType MEDIA_TYPE = MediaType.parse("image/" + fileName.substring(fileName.indexOf(".")+1));
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data-source", "android")
                .addFormDataPart("userId",userId)
                .addFormDataPart(description, description)
                .addFormDataPart("attachment", fileName, RequestBody.create(MEDIA_TYPE, file))
                .build();
    }
}
