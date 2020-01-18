package com.example.salonproduct.Network;

public class SharedPreference {

    /*SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public SharedPreference(Context context){
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public void putSession(String name){
        editor.putBoolean(name, true);

        editor.commit();
    }

    public void putData(String name, String value){

        editor.putString(name, value);
        editor.commit();
    }

    public String getValue(String name, String def){
        return pref.getString(name, def);
    }


    public boolean isLoggedIn(String name){
        return pref.getBoolean(name, false);
    }

    public void removeSession(String name){
        editor.putBoolean(name, false);
        editor.commit();
    }

    public static void saveLoginState(Context context, String name)
    {
        SharedPreferences  sp = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString(context.getString(R.string.user_name), context.getString(R.string.default_user_name));
        editor.putBoolean(context.getString(R.string.is_logged_in), true);

        editor.apply();

    }

    public static void removeLoggedInState(Context context)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString(context.getString(R.string.user_name), "none");
        editor.putBoolean(context.getString(R.string.is_logged_in), false);

        editor.apply();
    }

    public static Boolean isLoggedIn(Context context)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if(sp.getBoolean(context.getString(R.string.is_logged_in), false) &&
                sp.getString(context.getString(R.string.user_name),
                        context.getString(R.string.default_user_name))
                        != context.getString(R.string.default_user_name))
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/

}
