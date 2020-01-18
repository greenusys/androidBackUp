package com.icosom.social.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "UserSearch";
    public static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE_NAME = "search";

    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "f_name";
    public static final String USER_LAST_NAME = "l_name";
    public static final String PROFILE = "profile_picture";
    public static final String COVER = "cover_picture";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table if not exists "+USER_TABLE_NAME+"("
                +USER_ID+" text, "
                +USER_FIRST_NAME+" text, "
                +USER_LAST_NAME+" text, "
                +PROFILE+" text,"
                +COVER+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("Drop table if exists "+USER_TABLE_NAME);
        onCreate(db);
    }

//  *******************************************************************************************   //
                                //  for data insertion  //

    public boolean insertDataToUser(String USER_ID, String USER_FIRST_NAME, String USER_LAST_NAME,
                                    String PROFILE, String COVER)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put(this.USER_ID, USER_ID);
        con.put(this.USER_FIRST_NAME, USER_FIRST_NAME);
        con.put(this.USER_LAST_NAME, USER_LAST_NAME);
        con.put(this.PROFILE, PROFILE);
        con.put(this.COVER, COVER);
        long result = db.insert(USER_TABLE_NAME, null, con);
        if (result == -1)
            return false;
        else
            return true;
    }

                                    //  for data retrieve  //

    public Cursor getDataFromUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+USER_TABLE_NAME, null);
        return res;
    }

                                    //  for data update  //

    public boolean updateDataAtUser(String USER_ID,
                                    String USER_FIRST_NAME, String USER_LAST_NAME, String USER_GENDER,
                                    String PROFILE, String COVER)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put(this.USER_FIRST_NAME, USER_FIRST_NAME);
        con.put(this.USER_LAST_NAME, USER_LAST_NAME);
        con.put(this.PROFILE, PROFILE);
        con.put(this.COVER, COVER);
        db.update(USER_TABLE_NAME, con, "id = ?", new String[]{(USER_ID)});
        return true;
    }

                                        //  for data delete  //

    public boolean deleteDataFromUser(String USER_ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(USER_TABLE_NAME, "id = ?", new String[] {USER_ID});
        if (res > 0)
            return true;
        else
            return false;
    }
}