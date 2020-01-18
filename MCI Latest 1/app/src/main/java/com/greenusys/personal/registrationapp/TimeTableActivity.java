package com.greenusys.personal.registrationapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.RequestBuilder;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.RequestBody;

public class TimeTableActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
    String dayOfTheWeek;
    private static final String DATA_SOURCE = "data-source";
    private static final String DATA_SOURCE_VALUE = "android";
    private static final String CLASS_ID = "classId";
    private static final String CLASS_ID_VALUE = "1";
    private static final String LOG_TAG = TimeTableActivity.class.getSimpleName();
    private AppController appController;
    private UrlHelper urlHelper;
    private String currentWeekday;
    private  DateFormat df;
    private RecyclerView recyclerView;
    TimetableAdapter adapter;
    private ArrayList<String> timeTableSlot = new ArrayList<>();
    private ArrayList<String> timeTableLectureBy = new ArrayList<>();
    private TextView weekdayTextView;
    private Toolbar toolbar;
    private TextView toolbarTv;
    private ProgressBar progressBar;
    TextView errorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        appController = (AppController)getApplicationContext();
        urlHelper = new UrlHelper();

        progressBar = (ProgressBar)findViewById(R.id.time_table_pb);

        errorTextView = (TextView)findViewById(R.id.error_tv);

        recyclerView = (RecyclerView)findViewById(R.id.timeTable_rv);

        weekdayTextView = (TextView)findViewById(R.id.weekday);

        toolbar = (Toolbar)findViewById(R.id.time_table_toolbar);

        toolbarTv = toolbar.findViewById(R.id.toolbar_text);

        toolbarTv.setText("Time Table");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        adapter = new TimetableAdapter(this, new ArrayList<>(), new ArrayList<>());

        recyclerView.setAdapter(adapter);

        df = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());

        currentWeekday = df.format(System.currentTimeMillis()).split(",")[0];


        weekdayTextView.setText(currentWeekday);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        Log.e("date", "onCreate: ,"+dayOfTheWeek );
        Log.d(LOG_TAG,currentWeekday);



        getSupportLoaderManager().initLoader(100, null, this);

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {



            String timeTableResponseStr;

            @Override
            protected void onStartLoading() {

                showProgressbar();

                if(timeTableResponseStr != null)
                {
                    deliverResult(timeTableResponseStr);
                }
                else
                {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public String loadInBackground() {

                RequestBody body = RequestBuilder.timeTableImport(DATA_SOURCE,DATA_SOURCE_VALUE,
                        CLASS_ID,UrlHelper.classId);

                try{

                    timeTableResponseStr = appController.POST(urlHelper.timeTableImport,body);
                    Log.d(LOG_TAG, timeTableResponseStr);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


                return timeTableResponseStr;
            }

            @Override
            public void deliverResult(@Nullable String data) {

                timeTableResponseStr = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        Log.d(LOG_TAG, "load finished " + data);

        if(data.isEmpty() || data == null)
        {
            errorTextView.setVisibility(View.VISIBLE);
        }

        parseTimeTableResonse(data);

        adapter.updateDataset(timeTableSlot, timeTableLectureBy);

        hideProgressbar();


    }



    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void parseTimeTableResonse(String jsonResponse)
    {
        Log.e("date", "onCreate: ,"+jsonResponse );
        try {
            JSONArray rootArray = new JSONArray(jsonResponse);
            for(int i = 0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String timeSlot = jsonObject.getString("Day_Time");
                String lectureBy = jsonObject.getString(dayOfTheWeek);

                Log.d(LOG_TAG, timeSlot);
                Log.d(LOG_TAG,lectureBy);

                timeTableSlot.add(timeSlot);
                timeTableLectureBy.add(lectureBy);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


    private void showProgressbar()
    {

        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar()
    {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
