package com.icosom.social.Icosom_Chat_Package.activity.Retrofit;


import com.icosom.social.Icosom_Chat_Package.activity.Modal.Delete_Msg_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Friend_List_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Recent_Chat_Model;
import com.icosom.social.Talent_Show_Package.Modal.Premium_Response;
import com.icosom.social.Talent_Show_Package.Modal.Put_Rating_Modal;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    /* @GET("fetch_FriendApi.php")
     Call<ArrayList<Friend_List_Model>> getFriendList();

 */
    @FormUrlEncoded
    @POST("https://icosom.com/social/main/fetch_FriendApi.php")
    Call<Friend_List_Model> getFriendList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("https://icosom.com/social/main/new_api/recentchat_api.php")
    Call<ArrayList<Recent_Chat_Model>> getRecentChatList(@Field("sender_id") String sender_id, @Field("cases") String cases);


    @FormUrlEncoded
    @POST("https://icosom.com/social/main/new_api/deletemsg.php")
    Call<Delete_Msg_Model> deleteMessage(@Field("msg_id") String msg_id);


    @FormUrlEncoded
    @POST("https://icosom.com/social/main/new_api/talent_show_api.php")
    Call<Singing_Model> fetch_talent_post(@Field("talent_option") String talent_option, @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("https://icosom.com/social/main/new_api/talent_rating_api.php")
    Call<Put_Rating_Modal> put_rating(@Field("user_id") String user_id, @Field("talent_id") String talent_id, @Field("rating") String rating);


    @FormUrlEncoded
    @POST("https://icosom.com/wallet/main/make_my_account_premium.php")
    Call<Premium_Response> make_user_To_Premium(@Field("user_id") String user_id, @Field("amount") String amount);


    @FormUrlEncoded
    @POST(" https://icosom.com/wallet/main/checkForPremium.php")
    Call<Premium_Response> check_User_Premium(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("https://icosom.com/social/main/new_api/fetch_talent.php")
    Call<Singing_Model> fetch_single_post(@Field("tal_id") String tal_id, @Field("user_id") String user_id);


}
