package com.greenusys.personal.registrationapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.data.MciContract;

public class ProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Toolbar toolbar;
    TextView nameTextView;
    EditText nameEditText;
    TextView phoneTextView;
    EditText phoneEditText;
    Button saveButton;
    TextView classTextView;
    EditText classEditText;
    TextView emailTextView;
    private boolean isEditEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar)findViewById(R.id.profile_toolbar_layout);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nameTextView = (TextView)findViewById(R.id.profile_name);
        phoneTextView = (TextView)findViewById(R.id.profile_phnNo);
        nameEditText = (EditText)findViewById(R.id.profile_name_et);
        phoneEditText = (EditText)findViewById(R.id.profile_phnNo_et);
        saveButton = (Button)findViewById(R.id.profile_save_button);
        classTextView = (TextView)findViewById(R.id.profile_class);
        classEditText = (EditText)findViewById(R.id.profile_class_et);
        emailTextView = (TextView)findViewById(R.id.profile_email);

        getSupportLoaderManager().initLoader(100,null,this);
    }

    private void fetchLocalUser(Cursor cursor)
    {

        int nameIndex = cursor.getColumnIndex(MciContract.MciEntry.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndex(MciContract.MciEntry.COLUMN_EMAIL);
        int classIndex = cursor.getColumnIndex(MciContract.MciEntry.COLUMN_CLASSES);
        int phoneIndex = cursor.getColumnIndex(MciContract.MciEntry.COLUMNN_NUMBER);
        String name = "";
        String email = "";
        String clss= "";
        String phoneNumber = "";

     while(cursor.moveToNext())
     {
         name = cursor.getString(nameIndex);
         email = cursor.getString(emailIndex);
         clss = cursor.getString(classIndex);
         phoneNumber = cursor.getString(phoneIndex);

     }

     nameTextView.setText(name);
     emailTextView.setText(email);
     phoneTextView.setText(phoneNumber);
     classTextView.setText(clss);
    }






    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor cursor = null;

            @Override
            protected void onStartLoading() {
                if(cursor != null)
                {
                    deliverResult(cursor);
                }
                else
                {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver()
                            .query(MciContract.MciEntry.CONTENT_URI,
                                    null, null, null, null);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data)
            {
                cursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d("errrr", data.getCount()+"");

        fetchLocalUser(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
