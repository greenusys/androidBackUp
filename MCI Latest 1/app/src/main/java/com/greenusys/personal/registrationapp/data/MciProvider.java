package com.greenusys.personal.registrationapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by personal on 3/5/2018.
 */

public class MciProvider extends ContentProvider {

    public static final int CODE_USER = 100;
    public static final int CODE_USER_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MciDbHelper mOpenHelper;

    public  static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MciContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MciContract.PATH_USER, CODE_USER);

        matcher.addURI(authority, MciContract.PATH_USER + "/#", CODE_USER_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {

        mOpenHelper = new MciDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectoinArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch(match)
                {
                    case CODE_USER:

                        retCursor = db.query(MciContract.MciEntry.TABLE_NAME,
                                projection,
                                selection,
                                selectoinArgs,
                                null,
                                null,
                                sortOrder);
                        break;

                    case CODE_USER_ID:

                        String userId = uri.getLastPathSegment();

                        String[] selectionArguments = new String[]{userId};

                        retCursor = db.query(MciContract.MciEntry.TABLE_NAME,
                                projection,
                                MciContract.MciEntry._ID + " = ? ",
                                selectionArguments,
                                null,
                                null,
                                sortOrder);
                        break;


                    default:
                        throw new UnsupportedOperationException("Unknown uri: " + uri);
                }

                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match)
        {
            case CODE_USER:
            long id = db.insert(MciContract.MciEntry.TABLE_NAME,null,contentValues);

            if(id > 0)
            {
                returnUri = ContentUris.withAppendedId(MciContract.MciEntry.CONTENT_URI,id);
            }
            else
            {
                throw new android.database.SQLException("Failed to insert row into " + uri);
            }
            break;

            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsDeleted;

        if(selection == null ) selection = "1";

        switch (sUriMatcher.match(uri))
        {
            case CODE_USER_ID:

                numRowsDeleted = mOpenHelper.getReadableDatabase().delete(
                        MciContract.MciEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(numRowsDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsUpdated;

        switch (sUriMatcher.match(uri))
        {
            case CODE_USER_ID:

                String userId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{userId};

                numRowsUpdated = mOpenHelper.getWritableDatabase().update(
                        MciContract.MciEntry.TABLE_NAME,
                        contentValues,
                        MciContract.MciEntry._ID + " = ? ",
                        selectionArgs
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(numRowsUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return numRowsUpdated;
    }
}
