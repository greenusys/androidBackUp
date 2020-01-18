package com.icosom.social.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UninstallIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // fetching package names from extras\

        Toast.makeText(context, "i am in social", Toast.LENGTH_SHORT).show();

        String[] packageNames = intent.getStringArrayExtra("android.intent.extra.PACKAGES");

        if(packageNames!=null){
            for(String packageName: packageNames){
                if(packageName!=null && packageName.equals("com.icosom.social"))
                {
                    // User has selected our application under the Manage Apps settings
                    // now initiating background thread to watch for activity
                    Toast.makeText(context, "i am in social", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

}