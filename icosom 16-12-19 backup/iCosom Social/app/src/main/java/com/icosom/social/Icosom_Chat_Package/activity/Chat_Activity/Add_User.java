package com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.Socket;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.R;

import java.util.ArrayList;


public class Add_User extends AppCompatActivity {
    public static boolean app_destroy=false;
    private Button setNickName;
    private EditText userNickName;
    private EditText userID;

    Socket_URL socket_url;

    private Socket mSocket;
    {
        socket_url = new Socket_URL();
        mSocket = socket_url.getmSocket();

    }


    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private ArrayList<String> permissionsRejected = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_add__user);



    }


    public void komal(View view) {
        Intent intent = new Intent(Add_User.this, Chat_Main_Activity.class);
        intent.putExtra("username", "komal");
        intent.putExtra("userID", "18");
        startActivity(intent);
    }

    public void piyush(View view) {
        Intent intent = new Intent(Add_User.this, Chat_Main_Activity.class);
        intent.putExtra("username", "piyush");
        intent.putExtra("userID", "533");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }





}
