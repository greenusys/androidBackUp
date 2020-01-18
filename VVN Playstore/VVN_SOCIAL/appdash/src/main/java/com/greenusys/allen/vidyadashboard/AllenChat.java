package com.greenusys.allen.vidyadashboard;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenusys.allen.vidyadashboard.Adapter.ChatRecyclerAdapter;
import com.greenusys.allen.vidyadashboard.model.Chat;
import com.greenusys.allen.vidyadashboard.model.ChatSecond;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllenChat extends AppCompatActivity {
    CircleImageView image;
    ImageView back;



    String   username_list;
    String imageurl;
    private Toolbar toolbar;
    int reciever_id;



    EditText etChatInput;
    ImageView btnChatSend;
    RecyclerView rvChat;
    ChatRecyclerAdapter adapter;
    private static List<Chat> listChat = null;
    private static List<ChatSecond> listCheckChat = null;
    private static Chat modelChat = null;
    private static ChatSecond modelCheckChat = null;
    private static String id = null;
    private static String senderId = null;
    private static String senderType = null;
    private static String receiverId = null;
    private static String receiverType = null;
    private static String message = null;
    private static String readStatus = null;
    private static String checkid = null;
    private static String checksenderId = null;
    private static String checksenderType = null;
    private static String checkreceiverId = null;
    private static String checkreceiverType = null;
    private static String checkmessage = null;
    private static String checkreadStatus = null;
    private static List<String> listId = null;
    private static List<String> listMessage = null;
    private static List<String> listMsgs = null;
    private static List<String> listCheckMsgs = null;
    private static List<ChatSecond> listModelChatSecond = null;

    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask;

    private static int count = 0;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allen_chat);
        Log.e("AC", "onCreate: " );
        setContentView(R.layout.activity_allen_chat);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username_list = (String) extras.get("username_list");
        reciever_id = (int) extras.get("user_Id");
        imageurl = (String) extras.get("image_list");
        toolbar = (Toolbar) findViewById(R.id.appbarlayout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back=(ImageView)findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(AllenChat.this,MessageListActivity.class);
                startActivity(n);
            }
        });
        image = (CircleImageView) findViewById(R.id.imagevw);
        Picasso.with(this).load(imageurl).resize(80, 80).into(image);

        t = (TextView) findViewById(R.id.tool);
        t.setText( username_list);
        rvChat = (RecyclerView)findViewById(R.id.rvChat);
        etChatInput = (EditText)findViewById(R.id.etChatInput);
        btnChatSend = (ImageView) findViewById(R.id.btnChatSend);
        btnChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("AC", "onClick:Button Send " );
                String message_text = etChatInput.getText().toString();
                sendMessageResponse(message_text);
                etChatInput.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ChatRecyclerAdapter(this,chatResponse());
        rvChat.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        llm.setStackFromEnd(true);
        rvChat.setLayoutManager(llm);

//        mUpdateTimeTask = new Runnable() {
//            public void run() {
//                Log.e("AC", "run: Thread" );
//
//                mHandler.postDelayed(this, 20000 );
//
//            }
//        };
//        mHandler.postDelayed(mUpdateTimeTask, 1000);

    }


    private void sendMessageResponse(final String messages){
        String url = "https://vvn.city/apps/jain/new_message.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("AC", "onResponse:Response " + response );
                try {
                    JSONObject jObject = new JSONObject(response);
                    Log.e("AC", "onResponse:Object " + jObject );
                    for (int a = 0; a < jObject.length(); a++) {
                        JSONArray receiverArray = jObject.getJSONArray("new_msg");
                        Log.e("AC", "onResponse:Array " + receiverArray );
                        for (int b = 0; b < receiverArray.length(); b++) {
                            JSONObject current = receiverArray.getJSONObject(b);
                            String status = current.getString("status");
                            Log.e("AC", "onResponse: "+status );

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllenChat.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("sender_id", "1");
                params.put("sender_type", "Student");
                params.put("r_id", "1");
                params.put("receiver_type", "Teacher");
                params.put("message", messages);
                params.put("read_status", "0");

                return params;
            }
        };
        ;
        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);

    }


    private List<Chat> chatResponse(){
        listMsgs = new ArrayList<>();
        listChat = new ArrayList<>();
        listId = new ArrayList<>();
        Log.e("AC", "newCallMessageApi: " );

        String url = "https://vvn.city/apps/jain/chat.php?sender=1&receiver=1";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    for (int a= 0;a<jObject.length();a++){
                        JSONArray receiverArray = jObject.getJSONArray("result");
                        for (int b = 0; b < receiverArray.length(); b++) {
                            JSONObject current = receiverArray.getJSONObject(b);
                            modelChat = new Chat();

                            id = current.getString("id");
                            modelChat.setId(Integer.parseInt(id));
                            listId.add(id);


                            senderId = current.getString("sender_id");
                            modelChat.setSender_id(Integer.parseInt(senderId));

                            senderType = current.getString("sender_type");
                            modelChat.setSender_type(senderType);
                            if(senderType.equalsIgnoreCase("Student")){
                                modelChat.setUser_type(0);
                            } else {
                                modelChat.setUser_type(1);
                            }

                            receiverId = current.getString("reciver_id");
                            modelChat.setReceiver_id(Integer.parseInt(receiverId));

                            receiverType = current.getString("reciver_type");
                            modelChat.setReceiver_type(receiverType);

                            message = current.getString("message");
                            modelChat.setMessage(message);
                            listMsgs.add(message);

                            readStatus = current.getString("read_status");
                            modelChat.setStatus(Integer.parseInt(readStatus));

                            modelChat.setImage(R.mipmap.ic_launcher);

                            listChat.add(modelChat);
                        }
                        adapter.notifyDataSetChanged();
                    }


                    //Log.e("AC", "onResponse: " + listChat.size() );

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllenChat.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) ;
        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);

        return listChat;
    }


    private void sendMessage(final String messageText) {
        int status = 0;
        Log.e("CA", "onKey: " + "Action nine");
        if (messageText.trim().length() == 0)
            return;

        final Chat message = new Chat();
        message.setStatus(status);

        //adding message
        message.setMessage(messageText);
        message.setSender_type("Student");
        listChat.add(message);

        final ChatSecond messageSecond = new ChatSecond();
        messageSecond.setId((listCheckChat.get(listCheckChat.size()-1).getId())+1);
        messageSecond.setStatus(status);
        messageSecond.setSender_type("Student");
        messageSecond.setMessage(messageText);
        listCheckChat.add(messageSecond);

        adapter.notifyDataSetChanged();
    }

    private List<ChatSecond> check(){
        Log.e("AC", "check: " );
        listCheckChat = new ArrayList<>();
        listCheckMsgs = new ArrayList<>();
        String url = "https://www.greenusys.website/apps/chat.php?sender=1&receiver=1";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    for (int a= 0;a<jObject.length();a++){
                        JSONArray receiverArray = jObject.getJSONArray("result");
                        for (int b = 0; b < receiverArray.length(); b++) {
                            JSONObject current = receiverArray.getJSONObject(b);
                            modelCheckChat = new ChatSecond();

                            checkid = current.getString("id");
                            modelCheckChat.setId(Integer.parseInt(checkid));

                            checksenderId = current.getString("sender_id");
                            modelCheckChat.setSender_id(Integer.parseInt(checksenderId));

                            checksenderType = current.getString("sender_type");
                            modelCheckChat.setSender_type(checksenderType);
                            if(checksenderType.equalsIgnoreCase("Student")){
                                modelCheckChat.setUser_type(0);
                            } else {
                                modelCheckChat.setUser_type(1);
                            }

                            checkreceiverId = current.getString("reciver_id");
                            modelCheckChat.setReceiver_id(Integer.parseInt(checkreceiverId));

                            checkreceiverType = current.getString("reciver_type");
                            modelCheckChat.setReceiver_type(checkreceiverType);

                            checkmessage = current.getString("message");
                            modelCheckChat.setMessage(checkmessage);
                            listCheckMsgs.add(checkmessage);

                            checkreadStatus = current.getString("read_status");
                            modelCheckChat.setStatus(Integer.parseInt(checkreadStatus));

                            modelCheckChat.setImage(R.mipmap.ic_launcher);

                            listCheckChat.add(modelCheckChat);
                        }
                    }

                    Log.e("AC", "onResponse: " + listChat.size() );

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllenChat.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) ;
        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);
//        Log.e("AC", "check: "+listCheckChat.size() );
        return listCheckChat;
    }

    public void compare(){
        Log.e("AC", "compare: " );
        for (int i = 0;i<listChat.size();i++){
            Log.e("AC", "onRestart:list chat " + listChat.get(i).getMessage() +"\tcount: "+i );
        }

        mUpdateTimeTask = new Runnable() {
            public void run() {
                Log.e("AC", "run: Thread" );
                for (int i = 0;i<listCheckChat.size();i++){
//            Log.e("AC", "onRestart:list Check Chat " + listCheckChat.get(i).getMessage() +"\tcount: "+i );
                    if (listChat.get(i).getId() == listCheckChat.get(i).getId()) {
                        Log.e("AC", "compare: " + "Same size List"+"\tcount:"+i );
                    }else {
                        Log.e("AC", "compare: " + "Different Size List\t" + (listCheckChat.size() - listChat.size()));
                        Toast.makeText(AllenChat.this, listCheckChat.get(i).getMessage()+"Count:"+i, Toast.LENGTH_SHORT).show();
                    }
                }
                mHandler.postDelayed(this, 20000 );

            }
        };
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }

    public void checkTwo(){
        listMessage = new ArrayList<>();
        listModelChatSecond = new ArrayList<>();
        mUpdateTimeTask = new Runnable() {
            public void run() {
                Log.e("AC", "run: Thread" );

                if(listCheckChat.size()!=0){
                    //  Log.e("AC", "CheckTwo First List\t Last message "+listChat.get(listChat.size() - 1).getMessage()+"\tid"+listChat.get(listChat.size() - 1).getId() );
                    Log.e("AC", "CheckTwo Check List\t Last message "+listCheckChat.get(listCheckChat.size() - 1).getMessage()+"\tid"+listCheckChat.get(listCheckChat.size() - 1).getId() );
                    listMessage.add(listCheckChat.get(listCheckChat.size() - 1).getMessage());
                    listModelChatSecond.add(listCheckChat.get(listCheckChat.size() - 1));
                    Log.e("AC", "checkTwo: "+listMessage );
                    if (listMessage.size()>2){
                        if (!listMessage.get(listMessage.size()-1).equals(listMessage.get(listMessage.size()-2))){
                            Toast.makeText(AllenChat.this, listMessage.get(listMessage.size()-1), Toast.LENGTH_SHORT).show();
                        }

                    }

//                    Log.e("AC", "run:CheckTwo Set Message "+setMessage );
//                    Log.e("AC", "run:CheckTwo Set Model "+setModelChatSecond );

                    check();

                }
                mHandler.postDelayed(this, 5000 );
            }
        };
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }

    public void checkThree(){

        mUpdateTimeTask = new Runnable() {
            public void run() {
                Log.e("AC", "run: Thread" );

                if(listCheckChat.size()!=0){
                    if (listChat.size()!=listCheckChat.size()){
                        Log.e("AC", "run: " + listChat.size() );
                        for (int i = listChat.size();i<listCheckChat.size();i++){
                            modelChat = new Chat();
                            modelChat.setId(listCheckChat.get(i).getId());
                            modelChat.setSender_id(listCheckChat.get(i).getSender_id());
                            modelChat.setSender_type(listCheckChat.get(i).getSender_type());
                            String check = listCheckChat.get(i).getSender_type();
                            if(check.equalsIgnoreCase("Student")){
                                modelChat.setUser_type(0);
                            } else {
                                modelChat.setUser_type(1);
                            }
                            modelChat.setReceiver_id(listCheckChat.get(i).getReceiver_id());
                            modelChat.setReceiver_type(listCheckChat.get(i).getReceiver_type());
                            modelChat.setMessage(listCheckChat.get(i).getMessage());
                            modelChat.setStatus(listCheckChat.get(i).getStatus());
                            listChat.add(modelChat);
                        }
                        adapter.notifyDataSetChanged();
                        rvChat.smoothScrollToPosition(listChat.get(listChat.size()-1).getId());
                    }

                    check();
                }
                mHandler.postDelayed(this, 2000 );
            }
        };
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("AC", "onResume: " );
        check();
        checkThree();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("AC", "onPause: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("AC", "onStart: " );

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("AC", "onStop: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("AC", "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        Log.e("AC", "onDestroy: " );
        super.onDestroy();
        listChat.clear();
        mHandler.removeCallbacksAndMessages(null);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listChat.clear();
        mHandler.removeCallbacksAndMessages(null);
    }
}



