package com.example.digitalforgeco.Network_Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.digitalforgeco.fragments.Speed_Fragment;

public class MyReceiver extends BroadcastReceiver {

    //this class is used to detect network status either internet or wifi is on or off
    //if status is off it will call broadcast receiver method which is declared in Speed_Fragment.


    Speed_Fragment ma; //a reference to activity's context

    public MyReceiver(Speed_Fragment maContext){
        ma=maContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {



        String action = intent.getAction();
        if (action.equals("com.digitalforgeco.AN_INTENT")) {
           // Toast.makeText(context, "Explicit Broadcast was triggered", Toast.LENGTH_SHORT).show();

            System.out.println("Explicit Broadcast was triggered");
        }

        if (("android.net.conn.CONNECTIVITY_CHANGE").equals(action)) {
           // Toast.makeText(context, "Implicit Broadcast was triggered using registerReceiver", Toast.LENGTH_SHORT).show();

            ma.broadcast_Receiver();
            System.out.println("Implicit Broadcast was triggered using registerReceiver");

        }

    }
}
