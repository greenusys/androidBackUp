package com.greenusys.allen.vidyadashboard.apiClient;



import com.greenusys.allen.vidyadashboard.model.messageModel;
import com.greenusys.allen.vidyadashboard.data.chatMessageResponce;
import com.greenusys.allen.vidyadashboard.data.chatMessageListResponce;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("chat.php")
    Call<chatMessageResponce> getMessages(@Query("sender") int sender_value, @Query("receiver") int reciever_value);

    @GET("chat_list.php")
    Call<chatMessageListResponce> getMessagesList();

    @POST("new_message.php")
    @FormUrlEncoded
    Call<messageModel> postMessage(@Field("sender_id") int sender_id,
                                   @Field("sender_type") String sender_type,
                                   @Field("r_id") int r_id,
                                   @Field("receiver_type") String receiver_type,
                                   @Field("message") String message,
                                   @Field("read_status") int read_status);
}