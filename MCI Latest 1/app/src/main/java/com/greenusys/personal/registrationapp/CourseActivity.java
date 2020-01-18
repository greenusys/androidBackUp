package com.greenusys.personal.registrationapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.greenusys.personal.registrationapp.pojos.Course;
import com.greenusys.personal.registrationapp.pojos.TestResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class CourseActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>,CourseAdapter.CourseItemClickHandler {

    private static final String DATA_SOURCE = "data-source";
    private static final String DATA_SOURCE_VALUE = "android";
    private static final String USER_ID = "userId";
    private String USER_ID_VALUE;
    private static final String CLASS_ID = "classId";
    private String CLASS_ID_VALUE;
    private static final String LOG_TAG = TimeTableActivity.class.getSimpleName();
    private AppController appController;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private ArrayList<Course> courseResults = new ArrayList<>();
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
        setContentView(R.layout.activity_course);

        urlHelper = new UrlHelper();

        recyclerView = (RecyclerView) findViewById(R.id.courseDetail_rv);
        toolbar = (Toolbar) findViewById(R.id.course_detail_toolbar);

        errorTv = (TextView) findViewById(R.id.error_tv);

        toolbarText = (TextView) toolbar.findViewById(R.id.toolbar_text);

        progressBar = (ProgressBar) findViewById(R.id.course_detail_pb);


        toolbarText.setText("Courses");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new CourseAdapter(this, new ArrayList<Course>(),this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        appController = (AppController) getApplicationContext();
        urlHelper = new UrlHelper();

        hideProgressbar();

        getSupportLoaderManager().initLoader(101, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String resultJsonResponse;

            @Override
            protected void onStartLoading() {



                if (resultJsonResponse != null) {
                    deliverResult(resultJsonResponse);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                showProgressbar();

                RequestBody body = RequestBuilder.Parameter(DATA_SOURCE, DATA_SOURCE_VALUE);
                try {

                    resultJsonResponse = appController.POST(UrlHelper.courseUrl, body);
                    Log.d(LOG_TAG, resultJsonResponse);
                } catch (IOException e) {
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

        adapter.updateDataset(courseResults);

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
                String className = jsonObject.getString("classes");
                String uploadFileName = jsonObject.getString("upload_file");



                Log.d(LOG_TAG, className);
                Log.d(LOG_TAG,uploadFileName);


                courseResults.add(new Course(className,uploadFileName));


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

    @Override
    public void onClick(Course course) {
        String urlss = urlHelper.coursePdfStaticUrl;
        urlss = urlss+course.getCoursePfdName();
        Log.e("zzzz", "onClick: "+urlss );
        Intent ii = new Intent(CourseActivity.this, Pdf.class);
        ii.putExtra("link",urlss);
        startActivity(ii);

        /*
        Intent intent = new Intent(CourseActivity.this, Pdf.class);
        intent.putExtra(getString(R.string.pdf_intent_extra),course.getCoursePfdName());
        this.startActivity(intent);*/
    }
}
