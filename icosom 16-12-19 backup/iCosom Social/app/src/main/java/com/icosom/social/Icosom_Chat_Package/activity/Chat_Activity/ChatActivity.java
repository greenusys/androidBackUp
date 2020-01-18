package com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.icosom.social.Icosom_Chat_Package.activity.Adapter.Mess_Adapter;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Delete_Msg_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.MessageFormat;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Online_User_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiClient;
import com.icosom.social.Icosom_Chat_Package.activity.Retrofit.ApiInterface;
import com.icosom.social.R;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.ProfileActivity;
import com.icosom.social.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatActivity extends AppCompatActivity {

    private EditText textField;
    private CircleImageView sendButton,attachment_icon;
    public static final String TAG = "ChatActivity";

    CircleImageView chat_friend_image;
    TextView chat_friend_name;
    TextView chat_status;

    private Boolean hasConnection = false;

    private RecyclerView recyclerView;
    private Mess_Adapter messageAdapter;
    private Thread thread2;
    private boolean startTyping = false;
    private int time = 2;

    ArrayList<Online_User_Model> all_online_user_list = new ArrayList<>();
    public static String self_user_id = "", self_socket_id = "", friend_user_id = "", friend_socket_id = "", friend_socket_id2 = "",friend_name = "", friend_image = "";

    ArrayList<String> socket_sender_id;
    Socket_URL socket_url;
    List<MessageFormat> messageFormatList = new ArrayList<>();
    private AppController appController;
    ArrayList<Online_User_Model> online_user_models = new ArrayList<>();
    boolean online_check = false;
    String last_seen="";

    private Socket mSocket;
    private ApiInterface apiInterface;
    private BottomSheetDialog sheetDialog;
    private Bitmap myBitmap;

    {
        socket_url = new Socket_URL();
        mSocket = socket_url.getmSocket();

    }
    private static final int camera_image_code = 1;
    private static final int camera_video_code = 2;
    private static final int gallery_image_code = 3;
    private static final int gallery_video_code = 4;
    private static final int doc_file_code = 5;
    Uri picUri;
    public  static String today_date="";

    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: typing stopped " + startTyping);
            if (time == 0) {
                // setTitle("SocketIO");
                chat_status.setText("");

                try {
                    setOnline();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "handleMessage: typing stopped time is " + time);
                startTyping = false;
                time = 2;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);


        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         today_date = dateFormat.format(date);

        System.out.println("today_date_"+today_date);


        if (getIntent().getStringExtra("self_user_id") != null)
            self_user_id = getIntent().getStringExtra("self_user_id");

        if (getIntent().getStringExtra("self_socket_id") != null)
            self_socket_id = getIntent().getStringExtra("self_socket_id");

        if (getIntent().getStringExtra("friend_user_id") != null)
            friend_user_id = getIntent().getStringExtra("friend_user_id");

        if (getIntent().getStringExtra("friend_socket_id") != null)
            friend_socket_id = getIntent().getStringExtra("friend_socket_id");

        if (getIntent().getStringExtra("friend_name") != null)
            friend_name = getIntent().getStringExtra("friend_name");

        if (getIntent().getStringExtra("friend_image") != null)
            friend_image = getIntent().getStringExtra("friend_image");


        System.out.println("self_user_id" + self_user_id);
        System.out.println("self_socket_id" + self_socket_id);
        System.out.println("friend_user_id" + friend_user_id);
        System.out.println("friend_socket_id" + friend_socket_id);
        System.out.println("friend_name" + friend_name);
        System.out.println("friend_image" + friend_image);

        initViews();


        mSocket.on("chatMessage", onNewMessage);//receive new message response
        mSocket.on("onlineUsers", onlineUsers);//receive new user response
        mSocket.on("notifyTyping", onTyping);//receive new message response
        mSocket.on("disconnect", userIsDisconnected);//receive new message response
        mSocket.on("last_seen_time", last_seen_time);//receive new message response


        //for self user data
        JSONObject newUserObj = new JSONObject();
        try {
            newUserObj.put("person", Chat_Main_Activity.username);//self user name
            newUserObj.put("ids", Chat_Main_Activity.userID);//self user id
            mSocket.emit("newUser", newUserObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fetch_Messages();
        sendButtonEnabled();
    }

    private void initViews() {

        sheetDialog = new BottomSheetDialog(ChatActivity.this);
        View sheetView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.chat_bottom_sheet_attachment, null);
        sheetDialog.setContentView(sheetView);

        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        attachment_icon = findViewById(R.id.attachment_icon);

        chat_friend_image = findViewById(R.id.chat_friend_image);
        chat_friend_name = findViewById(R.id.chat_friend_name);
        chat_status = findViewById(R.id.chat_status);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        recyclerView = findViewById(R.id.rv);
        appController = (AppController) getApplicationContext();
        messageAdapter = new Mess_Adapter(messageFormatList, getApplicationContext(), self_user_id,friend_image);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

        chat_friend_name.setText(friend_name);

        Glide.with(getApplicationContext())
                .load(friend_image).
                apply(new RequestOptions().placeholder(R.drawable.chat_placeholder)).
                into(chat_friend_image);


        chat_friend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                        putExtra("userId", friend_user_id));
            }
        });


        ;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    public void sendButtonEnabled() {
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                JSONObject sender = MainActivity.newUserData;//self user data

                if(sender!=null) {
                    JSONObject receiver = null;

                    try {
                        // sender = new JSONObject(Online_User_List.newUserData);
                        sender.put("typing", true);

                        receiver = new JSONObject();

                        if (friend_socket_id.equals(""))
                            receiver.put("id", friend_socket_id2);//receiver socket it
                        else
                            receiver.put("id", friend_socket_id);//receiver socket it

                        // receiver.put("id", friend_socket_id);

                        receiver.put("name", friend_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    mSocket.emit("notifyTyping", sender, receiver);
                }
               /* System.out.println("newUserData"+sender);
                System.out.println("receiver"+receiver);*/

                if (charSequence.toString().trim().length() > 0) {
                    attachment_icon.setVisibility(View.INVISIBLE);
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                    attachment_icon.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //receive new user response
    Emitter.Listener onlineUsers = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("online_user_called_chatactivity" + args[0].toString());

                    int length = args.length;

                    if (length == 0) {
                        return;
                    }

                    String online_user = args[0].toString();

                    try {
                        JSONArray main = new JSONArray(online_user);
                        if (all_online_user_list != null)
                            all_online_user_list.clear();

                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);

                            Online_User_Model model = new Online_User_Model(item.getString("id"), item.getString("main_id"), item.getString("name"));
                            all_online_user_list.add(model);

                        }

                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);

                            if (item.getString("main_id").equalsIgnoreCase(friend_user_id)) {
                                friend_socket_id2 = item.getString("id");
                                break;
                            } else
                                friend_socket_id2 = "";


                        }



                        System.out.println("friend_socket_id2"+friend_socket_id2);


                        //show user's online status
                        if (all_online_user_list.size() > 0) {

                            for (int i = 0; i < all_online_user_list.size(); i++) {
                                if (all_online_user_list.get(i).getSender_user_id().equalsIgnoreCase(friend_user_id)) {
                                    online_check = true;
                                    break;
                                } else
                                    online_check = false;
                            }

                            setOnline();
                        }


                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };


    void setOnline() throws ParseException {
        System.out.println("online_kaif" + online_check);
        System.out.println("online_last_seen" + last_seen);

        if (online_check)
        {
            chat_status.setText("online");
            chat_status.setVisibility(View.VISIBLE);
        }
        else if(!last_seen.equals(""))
        {

            setLastSeen(last_seen);

        }
        else
            chat_status.setVisibility(View.INVISIBLE);

        //else
          //  chat_status.setVisibility(View.INVISIBLE);
    }

    private void setLastSeen(final String last_seen)  {

        if(ChatActivity.this!=null) {


            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    //calculate date and  time
                    String date_time[] = last_seen.split(" ");
                    String date = date_time[0];//date
                    String time = date_time[1];//time
                    String time2[] = time.split(":");//time array


                    int time3 = Integer.parseInt(time2[0]);//hour
                    String fulltime = "";

                    //convert yy-mm-dd to mm-dd-yy format
                    SimpleDateFormat df_in = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df_output = new SimpleDateFormat("dd-MM-yyyy");
                    Date date2 = null;
                    try {
                        date2 = df_in.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String date1 = df_output.format(date2);


                    //end

                    //calculate time

                    if (time3 != 00) {
                        if (time3 > 12) {
                            fulltime = String.valueOf(time3 - 12) + ":" + time2[1] + " PM";
                        } else
                            fulltime = String.valueOf(time3) + ":" + time2[1] + " AM";


                    } else

                        fulltime = String.valueOf("12") + ":" + time2[1] + " AM";

                    if (date1.equalsIgnoreCase(today_date))
                        chat_status.setText("Last Seen Today" + " " + fulltime);
                    else
                        chat_status.setText("Last Seen On " + date1 + " " + fulltime);


                    chat_status.setVisibility(View.VISIBLE);


                    System.out.println("kaif_date+time" + date1 + " " + date1 + fulltime);


                }
            });

        }


    }

    //receive new message response
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //System.out.println("kaitrina_chat");

                    // Log.i(TAG, "run: ");
                    //Log.i(TAG, "run: " + args.length);
                    JSONObject data = (JSONObject) args[0];

                    System.out.println("message_recived" + data);

                    String type;
                    String text;
                    String sender_socket_id;
                    String receiver_socket_id;
                    String sender_id;
                    String reciever_id;
                    try {
                        type = data.getString("type");
                        text = data.getString("text");
                        sender_socket_id = data.getString("sender");
                        receiver_socket_id = data.getString("receiver");
                        sender_id = data.getString("sender_id");
                        reciever_id = data.getString("reciever_id");

                        //public MessageFormat(String type, String text, String socket_sender_id, String socket_receiver_id, String sender_id, String reciever_id) {


                       /* MessageFormat format = new MessageFormat(type, text, sender_socket_id, receiver_socket_id, sender_id, reciever_id);
                        messageFormatList.add(format);
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
*/

                        if (messageFormatList.size() > 0) {


                            Date date = Calendar.getInstance().getTime();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String today_date = dateFormat.format(date);
                            ArrayList<String> dateonly=calculateTIme(today_date,true);


                            MessageFormat format = new MessageFormat("",type, text, sender_socket_id, receiver_socket_id, sender_id, reciever_id,dateonly.get(0),dateonly.get(1));
                            messageFormatList.add(format);
                            messageAdapter.notifyItemInserted(messageFormatList.size());
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
                        } else {

                            Date date = Calendar.getInstance().getTime();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String today_date = dateFormat.format(date);
                            ArrayList<String> dateonly=calculateTIme(today_date,true);

                            MessageFormat format = new MessageFormat("",type, text, sender_socket_id, receiver_socket_id, sender_id, reciever_id,dateonly.get(0),dateonly.get(1));
                            messageFormatList.add(format);
                            messageAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
                        }


                    } catch (Exception e) {
                        // System.out.println("katrina_message_Exception");
                        return;
                    }
                }
            });
        }
    };


    //receive new user disconnect response
    Emitter.Listener userIsDisconnected = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        return;
                    }
                    String disconnect_status = args[0].toString();
                    System.out.println("disconnect_user"  + disconnect_status);
                    System.out.println("socket_check_"+mSocket.connected());



                }
            });
        }
    };


    //receive new user disconnect response
    Emitter.Listener last_seen_time = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        try {
                            setOnline();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        last_seen="";
                        return;
                    }

                     last_seen = args[0].toString();
                    try {
                        setOnline();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("last_seen_socket"  + last_seen);




                }
            });
        }
    };

    Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(TAG, "run: " + args[0]);

                    System.out.println("ontyping" + data);
                    try {

                        String userName = data.getString("name") + " is Typing......";
                        String id = data.getString("main_id");


                        //  ontyping{"id":"YYo_VCqrLcIWKKhZAAAF","name":"piyush","main_id":"533"}

                        Boolean typingOrNot = data.getBoolean("typing");
                        // Boolean typingOrNot = false;

                        // String id = data.getString("uniqueId");

                        if (id.equals(self_user_id)) {
                            typingOrNot = false;
                        } else {
                            //setTitle(userName);
                            chat_status.setVisibility(View.VISIBLE);
                            chat_status.setText("typing...");

                        }

                        if (typingOrNot) {

                            if (!startTyping) {
                                startTyping = true;
                                thread2 = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                while (time > 0) {
                                                    synchronized (this) {
                                                        try {
                                                            wait(1000);
                                                            Log.i(TAG, "run: typing " + time);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        time--;
                                                    }
                                                    handler2.sendEmptyMessage(0);
                                                }

                                            }
                                        }
                                );
                                thread2.start();
                            } else {
                                time = 2;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };




    public void sendMessage(View view) {
        String message2 = textField.getText().toString().trim();


        if (TextUtils.isEmpty(message2)) {
            Log.i(TAG, "sendMessage:2 ");
            return;
        }
        textField.setText("");
        JSONObject object = new JSONObject();
        try {

            //public MessageFormat(String type, String text, String socket_sender_id, String socket_receiver_id, String sender_id, String reciever_id) {

            if (messageFormatList.size() > 0) {

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String today_date = dateFormat.format(date);
                ArrayList<String> dateonly=calculateTIme(today_date,true);

                MessageFormat format = new MessageFormat("","text", message2, self_socket_id, friend_socket_id, self_user_id, friend_user_id,dateonly.get(0),dateonly.get(1));
                messageFormatList.add(format);
                messageAdapter.notifyItemInserted(messageFormatList.size());
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            } else {

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String today_date = dateFormat.format(date);
                ArrayList<String> dateonly=calculateTIme(today_date,true);

                MessageFormat format = new MessageFormat("","text", message2, self_socket_id, friend_socket_id, self_user_id, friend_user_id,dateonly.get(0),dateonly.get(1));
                messageFormatList.add(format);
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }


            object.put("type", "text");
            object.put("text", message2);
            object.put("sender", self_socket_id);//sender socket it

            if(friend_socket_id.equals("")) {
                System.out.println("execute_one"+friend_socket_id2);
                object.put("receiver", friend_socket_id2);//receiver socket it
            }
            else {
                System.out.println("execute_two"+friend_socket_id);
                object.put("receiver", friend_socket_id);//receiver socket it
            }


            object.put("sender_id", self_user_id);//USER ID
            object.put("reciever_id", friend_user_id);//USER ID


            System.out.println("type" + "text");
            System.out.println("text" + message2);
            System.out.println("sender" + self_socket_id);

            if(friend_socket_id.equals("")) {
                System.out.println("receiver_1"+friend_socket_id2);
            }
            else {
                System.out.println("receiver_2"+friend_socket_id);
            }

            System.out.println("sender_id" + self_user_id);
            System.out.println("reciever_id" + friend_user_id);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: 1" + mSocket.emit("chatMessage", object));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        //JSONObject user = new JSONObject();
           /* try {
                user.put("person", user_name + " DisConnected");
                user.put("ids", sender_id);
                mSocket.emit("newUser", user);


            } catch (JSONException e) {
                e.printStackTrace();
            }*/


        //mSocket.off("chatMessage", onNewMessage);
        //mSocket.off("newUser", onNewUser);
        //mSocket.off("onlineUsers", onlineUsers);
        //mSocket.off("on typing", onTyping);

        //messageAdapter.clear();
    }


    public void fetch_Messages() {

        RequestBody body = new FormBody.Builder().
                add("reciever_id", friend_user_id).
                add("sender_id", self_user_id).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com//social/main/fetchmsg.php").
                post(body).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("myResponse" + myResponse);
                try {


                    JSONObject mainjson = new JSONObject(myResponse);


                    if (mainjson.getString("status").equals("1")) {

                        if(!mainjson.getString("last_seen").equalsIgnoreCase("no data"))
                            last_seen=mainjson.getString("last_seen");
                        else
                            last_seen="";


                        JSONArray data = mainjson.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);

                            String sender_id = item.getString("sender_id");
                            String receiver_id = item.getString("receiver_id");
                            String message = item.getString("message");
                            String message_id = item.getString("id");
                            String message_date = item.getString("message_date");


                            ArrayList<String> dateonly=calculateTIme(message_date,false);
                            //end time




                            //public MessageFormat(String type, String text, String socket_sender_id, String socket_receiver_id, String sender_id, String reciever_id) {
                            MessageFormat format = new MessageFormat(message_id,"text", message, self_socket_id, friend_socket_id, sender_id, receiver_id,dateonly.get(0),dateonly.get(1));
                            messageFormatList.add(format);


                        }

                        //System.out.println("chat_list" + messageFormatList.size());


                        if (ChatActivity.this != null) {
                            ChatActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
                                    messageAdapter.notifyDataSetChanged();
                                    try {
                                        setOnline();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        }


                    }
                    else
                    {

                        if(!mainjson.getString("last_seen").equalsIgnoreCase("no data"))
                            last_seen=mainjson.getString("last_seen");
                        else
                            last_seen="";

                        setOnline();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private ArrayList calculateTIme(String message_date,boolean system_time) throws ParseException {

        System.out.println("calculate_time_"+message_date);

        ArrayList<String>final_date=new ArrayList<>();
        //calculate date and  time
        String date_time[] = message_date.split(" ");
        String date = date_time[0];//date
        String time = date_time[1];//time
        String time2[] = time.split(":");//time array


        int time3 = Integer.parseInt(time2[0]);//hour
        String fulltime = "";

        //convert yy-mm-dd to mm-dd-yy format
        SimpleDateFormat df_in = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df_output = new SimpleDateFormat("dd-MM-yyyy");
        Date date2 = df_in.parse(date);
        String date1 = df_output.format(date2);

        System.out.println("calculate_date"+date1);


        //end



        if(!system_time) {
            //calculate time
            if (!String.valueOf(time3).equalsIgnoreCase("0")) {
                System.out.println("john_if");
                if (time3 > 12) {
                    fulltime = String.valueOf(time3 - 12) + ":" + time2[1] + " PM";
                } else
                    fulltime = String.valueOf(time3) + ":" + time2[1] + " AM";


            } else {
                System.out.println("john_else");

                fulltime = String.valueOf("12") + ":" + time2[1] + " AM";
            }
        }
        else
        {
            //calculate time
            if (!String.valueOf(time3).equalsIgnoreCase("0")) {
                System.out.println("john_if");
                if (time3 > 12) {
                    fulltime = String.valueOf(time3 - 12) + ":" + time2[1] + " AM";
                } else
                    fulltime = String.valueOf(time3) + ":" + time2[1] + " PM";


            } else {
                System.out.println("john_else");

                fulltime = String.valueOf("12") + ":" + time2[1] + " AM";
            }

        }




        System.out.println("kaif_date" + date1 + " " + fulltime);

        System.out.println("kaif_time3" + time3+" "+String.valueOf(time3).equalsIgnoreCase("00"));

        final_date.add(date1);
        final_date.add(fulltime);

return final_date;

    }


    public void deleteMessage(int size, String mess_id, String text)
    {
        System.out.println("deleteMessage_called_"+mess_id);
        System.out.println("deleteMessage_called_text_"+text);
        System.out.println("size"+size);
        ApiInterface  apiInterface= ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<Delete_Msg_Model> call=apiInterface.deleteMessage(mess_id);
            call.enqueue(new retrofit2.Callback<Delete_Msg_Model>() {
                @Override
                public void onResponse(retrofit2.Call<Delete_Msg_Model> call, retrofit2.Response<Delete_Msg_Model> response) {
                    Delete_Msg_Model postResponse = response.body();
                    System.out.println("delete_resp"+postResponse.getStatus());
                }

                @Override
                public void onFailure(retrofit2.Call<Delete_Msg_Model> call, Throwable t) {
                    System.out.println("Failed_To_Delete"+t.getMessage());

                    call.cancel();

                }
            });




    }


    public void open_gallery_sheet(View view) {
       // sheetDialog.show();

    }

    public void open_image(View view) {
        startActivityForResult(openGalleryIntent(), gallery_image_code);
    }
    public Intent openGalleryIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();
        System.out.println("kaif_url"+outputFileUri);

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all gallery intents
        Intent galleryImages = new Intent(Intent.ACTION_GET_CONTENT);
        galleryImages.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryImages, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryImages);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }


        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);


        return galleryImages;
    }




    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "chat_profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("resultCode"+resultCode);
        if (resultCode == Activity.RESULT_OK && resultCode==gallery_image_code) {

            System.out.println("result_kaif_1"+data);

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                System.out.println("result_kaif_2"+picUri);


                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    // myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    // myBitmap = getResizedBitmap(myBitmap, 500);



                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                //  bitmap = (Bitmap) data.getExtras().get("data");

                //  System.out.println("kaif_nulll"+bitmap);

                // myBitmap = bitmap;




            }

        }

    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);

    }

    public void back_activity(View view) {
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);

        onBackPressed();
    }

    @Override
    protected void onStop() {
        self_user_id="";
        super.onStop();
    }
}
