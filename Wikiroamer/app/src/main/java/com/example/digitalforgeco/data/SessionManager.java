package com.example.digitalforgeco.data;

import java.util.HashMap;
 
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "wikiroamer";
     

    public static final String KEY_ID = "map_value";


    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void store_Map_Value(String value){
        editor.putString(KEY_ID, value);
        editor.commit();
    }   



    public HashMap<String, String> getMap_Value(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        return user;
    }

}