package com.icosom.social.Icosom_Chat_Package.activity.Notification;

import android.app.IntentService;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.nkzawa.emitter.Emitter;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.activity.MainActivity;

import org.json.JSONObject;

public class MyService extends IntentService {

    public MyService() {
        super("MyService");
    }

    //for chat
    Socket_URL socket_url;
    Intent intent;


    @Override
    protected void onHandleIntent(Intent intent) {

        this.intent = intent;

        //  mSocket.connect();
        MainActivity.mSocket.on("chatMessage", onNewMessage);//receive new message response

        System.out.println("kaif_service_called_" + MainActivity.mSocket.connected());


    }

    //receive new message response
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {


            new Thread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];

                    System.out.println("MyService_message_recived" + data);

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
                        sender_id = data.getString("sender_id");//self id
                        reciever_id = data.getString("reciever_id");//friend id
                        intent.setAction(MainActivity.FILTER_ACTION_KEY);
                        LocalBroadcastManager.getInstance(getApplicationContext())
                                .sendBroadcast(intent
                                        .putExtra("reciever_id", reciever_id)
                                        .putExtra("message",text)
                                );


                    } catch (Exception e) {
                        // System.out.println("katrina_message_Exception");
                        return;
                    }


                }

                ;
            }).start();


        }
    };


}