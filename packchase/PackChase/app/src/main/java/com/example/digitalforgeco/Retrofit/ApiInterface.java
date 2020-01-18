package com.example.digitalforgeco.Retrofit;


import com.example.digitalforgeco.modal.Tracking_Rtesponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiInterface {


    /*@FormUrlEncoded
    @POST("https://icosom.com/social/main/fetch_FriendApi.php")
    Call<Tracking_Rtesponse> getTrackingData(@Field("user_id") String user_id);
*/

    @GET
    @Headers({"Content-Type: application/json", "Trackingmore-Api-Key: de1ba7aa-a0e7-49d4-8f60-8a9010002907"})
    Call<JsonObject> getTrackingData(@Url String url);

}
