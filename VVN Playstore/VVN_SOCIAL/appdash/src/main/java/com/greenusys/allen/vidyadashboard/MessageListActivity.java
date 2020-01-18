package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenusys.allen.vidyadashboard.apiClient.ApiClient;
import com.greenusys.allen.vidyadashboard.apiClient.ApiInterface;
import com.greenusys.allen.vidyadashboard.data.chatMessageListResponce;
import com.greenusys.allen.vidyadashboard.model.ChatItem;
import com.greenusys.allen.vidyadashboard.model.messageListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListActivity extends AppCompatActivity {
    private ArrayList<messageListModel> dataList;
    private static ArrayList<ChatItem> chatItemsList;
    private ArrayList<String> resultList;
    private static List<String> userIdList;
    private static List<String> userReceiverList;
    private static List<String> userImageList;

    private int userId;
    private String username_list;
    private String image_list;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        dataList = new ArrayList<>();
        resultList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.messenger_List);
        responseChatList();

        //Calling Api
        callMessageListApi();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dataList != null) {
                    userId = getUserId(i);
                    username_list = getUserName(i);
                    image_list =getImage(i);
                } else {
                    Toast.makeText(getApplicationContext(), "No Api responce", Toast.LENGTH_LONG).show();

                    return;
                }

                Intent intent = new Intent(MessageListActivity.this, AllenChat.class);
                intent.putExtra("user_Id", userId);
                intent.putExtra("username_list", username_list);
                intent.putExtra("image_list", image_list);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent yy2=new Intent(MessageListActivity.this,MainActivity_Dash.class);
        startActivity(yy2);
    }

    private String getUserName(int index) {

        if(userReceiverList!=null){
            username_list = userReceiverList.get(index);

        }
//        if (dataList != null) {
//            username_list = dataList.get(index).getReceiver_name();
//        }
        return username_list;
    }
    private String getImage(int index) {

        if(userImageList!=null){
            image_list = userImageList.get(index);

        }
//        if (dataList != null) {
//            username_list = dataList.get(index).getReceiver_name();
//        }
        return image_list;
    }

    public int getUserId(int index) {
        if (userIdList!=null){
            String str = userIdList.get(index);
            index = Integer.parseInt(str);

        }
//        if (dataList != null) {
//
//            String str = dataList.get(index).getUser_id();
//            index = Integer.parseInt(str);
//
//        }
        return index;
    }


    private void setData() {

        AdapterChatList chatListAdapter=new AdapterChatList(this,R.layout.list_item_checked,getData());
       // AdapterChatList chatListAdapter = new AdapterChatList(this,R.layout.list_item_checked,getData());
        //  ArrayAdapter<String> newItemAdapter = new ArrayAdapter<String>(this,R.layout.list_item_checked,resultList);
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(MessageListActivity.this, android.R.layout.list_item_checked, resultList);

        listView.setAdapter(chatListAdapter);
    }

    private List<ChatItem> getData(){
        Log.e("MLA", "getData: "+userReceiverList.get(0) );
        Log.e("MLA", "getData: "+userReceiverList.get(1) );
        Log.e("MLA", "getData: "+userReceiverList.get(2) );

        ChatItem data;
        List<ChatItem> current = new ArrayList<>();
        int size = userIdList.size();
        chatItemsList = new ArrayList<>();
        for (int i=0;i<size;i++){
            data = new ChatItem();
            data.setProfileName(userReceiverList.get(i));
            data.setProfileImageUrl(userImageList.get(i));
            current.add(data);
        }
        return current;
    }


    public void responseChatList(){
        userIdList = new ArrayList<>();
        userReceiverList = new ArrayList<>();
        userImageList = new ArrayList<>();
        String url = "https://vvn.city/apps/jain/chat_list.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.e("MLA", "onResponse: Response "+response );
                try {
                    JSONObject jObject = new JSONObject(response);
                    //Log.e("MLA", "onResponse: "+jObject );
                    for (int a= 0;a<jObject.length();a++){
                        JSONArray receiverArray = jObject.getJSONArray("receiver");
                        //Log.e("MLA", "onResponse: "+receiverArray );
                        for (int b = 0; b < receiverArray.length(); b++) {
                            JSONObject current = receiverArray.getJSONObject(b);
                            String user_id = current.getString("user_id");
                            userIdList.add(user_id);
                            Log.e("MLA", "onResponse: "+user_id );
                            String receiver_name = current.getString("receiver_name");
                            userReceiverList.add(receiver_name);
                            String image = current.getString("img2");
                            userImageList.add(image);
                        }

                    }

                    setData();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessageListActivity.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) ;
        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);

    }

    private void callMessageListApi() {
        try {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<chatMessageListResponce> call = apiService.getMessagesList();
            call.enqueue(new Callback<chatMessageListResponce>() {
                @Override
                public void onResponse(Call<chatMessageListResponce> call, Response<chatMessageListResponce> response) {

//                    dataList = response.body().getData();
//                    Log.d("dataReceive", "" + response.body().getData().toString());
//                    if (dataList != null) {
//
//                        for (messageListModel obj : dataList) {
//                            resultList.add(obj.getReceiver_name().toString());
//                            setData(resultList);
//                        }
//
//                    }
                }

                @Override
                public void onFailure(Call<chatMessageListResponce> call, Throwable t) {
                    Log.d("dataReceiveFailed", "" + t.toString());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
