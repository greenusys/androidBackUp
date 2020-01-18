package com.greenusys.personal.registrationapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by personal on 3/5/2018.
 */

public class MciDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";

    private static final int DATABASE_VERSION = 1;

    public MciDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USER_TABLE =

                "CREATE TABLE " + MciContract.MciEntry.TABLE_NAME + "( " +
                        MciContract.MciEntry._ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MciContract.MciEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMNN_NUMBER + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMN_GENDER + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMNN_CLASS + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMN_ID + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMN_USER_TYPE + " TEXT NOT NULL, " +
                        MciContract.MciEntry.COLUMN_CLASSES + " TEXT NOT NULL, " +

                        " UNIQUE (" + MciContract.MciEntry.COLUMN_EMAIL + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
