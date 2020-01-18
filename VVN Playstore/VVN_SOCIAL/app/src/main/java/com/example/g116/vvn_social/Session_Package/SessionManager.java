package com.example.g116.vvn_social.Session_Package;

import java.util.HashMap;
 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.g116.vvn_social.Home_Activities.MainActivity;
import com.example.g116.vvn_social.Login_Registration_API.LoginActivity;
import com.example.g116.vvn_social.Login_Registration_API.Login_Type_Activity;

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
    private static final String PREF_NAME = "vvn_sp";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     

    public static final String KEY_ID = "id";
    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PICTURE = "picture";
    public static final String KEY_USER_TYPE = "type";

    public static final String KEY_CLASS_COURSE = "class_course";
    public static final String KEY_STATE = "state";
    public static final String KEY_CITY = "city";

    public static final String KEY_EducationDetails = "educationDetails";
    public static final String KEY_PassYear = "passYear";
    public static final String KEY_MaritalStatus = "maritalStatus";
    public static final String KEY_BirthDate = "birthDate";
    public static final String KEY_Gender = "gender";
    public static final String KEY_Address = "address";
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }




    /**
     * Create login session
     * */
    public void createLoginSession(String id,String full_name, String email,String mobile,String picture,String type,
    String class_course, String state, String city, String educationDetails,String passYear,
                                   String maritalStatus ,String birthDate,String gender,String address,String password){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         

        editor.putString(KEY_ID, id);
        editor.putString(KEY_FULL_NAME, full_name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_PICTURE, picture);
        editor.putString(KEY_USER_TYPE, type);
        editor.putString(KEY_CLASS_COURSE, class_course);
        editor.putString(KEY_STATE, state);
        editor.putString(KEY_CITY, city);

        editor.putString(KEY_EducationDetails, educationDetails);
        editor.putString(KEY_PassYear, passYear);
        editor.putString(KEY_MaritalStatus, maritalStatus);
        editor.putString(KEY_BirthDate, birthDate);
        editor.putString(KEY_Gender, gender);
        editor.putString(KEY_Address, address);
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }   
     
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            context.startActivity(i);
        }


         
    }
     
     
     

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        user.put(KEY_FULL_NAME, pref.getString(KEY_FULL_NAME, ""));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, ""));
        user.put(KEY_PICTURE, pref.getString(KEY_PICTURE, ""));
        user.put(KEY_USER_TYPE, pref.getString(KEY_USER_TYPE, ""));

        user.put(KEY_CLASS_COURSE, pref.getString(KEY_CLASS_COURSE, ""));
        user.put(KEY_STATE, pref.getString(KEY_STATE, ""));
        user.put(KEY_CITY, pref.getString(KEY_CITY, ""));


        user.put(KEY_EducationDetails, pref.getString(KEY_EducationDetails, ""));
        user.put(KEY_PassYear, pref.getString(KEY_PassYear, ""));
        user.put(KEY_MaritalStatus, pref.getString(KEY_MaritalStatus, ""));
        user.put(KEY_BirthDate, pref.getString(KEY_BirthDate, ""));
        user.put(KEY_Gender, pref.getString(KEY_Gender, ""));
        user.put(KEY_Address, pref.getString(KEY_Address, ""));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, ""));

        // return user
        return user;
    }
     
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login_Type_Activity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}