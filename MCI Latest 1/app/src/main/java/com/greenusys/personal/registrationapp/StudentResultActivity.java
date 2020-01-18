package com.greenusys.personal.registrationapp;


import android.os.Bundle;
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
import com.greenusys.personal.registrationapp.pojos.TestResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class StudentResultActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private static final String DATA_SOURCE = "data-source";
    private static final String DATA_SOURCE_VALUE = "android";
    private static final String USER_ID = "userId";
    private  String USER_ID_VALUE ;
    private static final String CLASS_ID = "classId";
    private  String CLASS_ID_VALUE ;
    private static final String LOG_TAG = TimeTableActivity.class.getSimpleName();
    private AppController appController;
    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    private ArrayList<TestResult> testResults = new ArrayList<>();
    private Toolbar toolbar;
    private TextView toolbarText;
    private ProgressBar progressBar;
    private boolean isOfflineResult = false;

    private static String testTitle;
    private static String testMax;
    private static String scores;
    UrlHelper urlHelper;
    private TextView errorTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);
        urlHelper = new UrlHelper();
        USER_ID_VALUE=urlHelper.userId;
        CLASS_ID_VALUE=urlHelper.classId;

        recyclerView = (RecyclerView)findViewById(R.id.studentResult_rv);
        toolbar = (Toolbar)findViewById(R.id.student_result_toolbar);

        errorTv = (TextView)findViewById(R.id.error_tv);

        toolbarText = (TextView)toolbar.findViewById(R.id.toolbar_text);

        progressBar = (ProgressBar)findViewById(R.id.result_pb);

        isOfflineResult = getIntent()
                .getBooleanExtra(getString(R.string.offline_test_flag),false);

        if(isOfflineResult)
        {
            testTitle = "title";
            testMax = "total_marks";
            scores = "marks_obtained";
        }
        else
        {
            testTitle = "test_title";
            testMax = "total_marks";
            scores = "obtain_marks";
        }

        toolbarText.setText("Results");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new ResultAdapter(this,new ArrayList<TestResult>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        appController = (AppController)getApplicationContext();
        urlHelper = new UrlHelper();

        getSupportLoaderManager().initLoader(101,null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String resultJsonResponse;

            @Override
            protected void onStartLoading() {

                showProgressbar();
                if(resultJsonResponse != null)
                {
                    deliverResult(resultJsonResponse);
                }
                else
                {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                RequestBody body;
                String url;

                if(isOfflineResult)
                {
                    body = RequestBuilder.threeParameter(DATA_SOURCE,DATA_SOURCE_VALUE,USER_ID,UrlHelper.userId,
                            CLASS_ID,urlHelper.classId);

                    url = UrlHelper.studentOfflineResult;
                }
                else {

                     body = RequestBuilder.twoParameter(DATA_SOURCE, DATA_SOURCE_VALUE,
                            USER_ID, UrlHelper.userId);
                     url = UrlHelper.studentResult;
                }
                try{

                    resultJsonResponse = appController.POST(url,body);
                    Log.d(LOG_TAG, resultJsonResponse);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return resultJsonResponse;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if(data.isEmpty() || data == null)
        {
            errorTv.setVisibility(View.VISIBLE);
        }

        parseStudentResultResonse(data);

        adapter.updateDataset(testResults);

        hideProgressbar();

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void parseStudentResultResonse(String jsonResponse)
    {
        try {
            JSONArray rootArray = new JSONArray(jsonResponse);
            for(int i = 0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String testName = jsonObject.getString(testTitle);
                String score = jsonObject.getString(scores);
                String maxMarks = jsonObject.getString(testMax);


                Log.d(LOG_TAG, testName);
                Log.d(LOG_TAG,maxMarks);
                Log.d(LOG_TAG,score);

                testResults.add(new TestResult(testName,maxMarks,score));


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
