package com.greenusys.personal.registrationapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.greenusys.personal.registrationapp.JsonParser;
import com.greenusys.personal.registrationapp.ProviderUtilities;
import com.greenusys.personal.registrationapp.Quiz;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.StudentResultActivity;
import com.greenusys.personal.registrationapp.Study;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.VideoListActivity;
import com.greenusys.personal.registrationapp.pojos.Quizzz;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 *

 */
public class Student extends Fragment implements Student_adapter.NewItemOnClickHandler {

    private static final String LOG_TAG = Student.class.getSimpleName();
    Student_adapter adapter;
    RecyclerView recyclerView;
    UrlHelper urlHelper;
    String urls;

    private BottomNavigationView bottomNavigationView;
    int check = 0;





    public Student() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student, container, false);
        urlHelper = new UrlHelper();
        urls = urlHelper.quiz;

        Log.e("ssssss","success"+urls);
        bottomNavigationView = (BottomNavigationView)rootView.findViewById(R.id.student_zone_bottom_nv);


                bottomNavigationView.setOnNavigationItemSelectedListener(
                        new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.test_result:

                                        Intent i = new Intent(getActivity(), StudentResultActivity.class);
                                        startActivity(i);
                                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                                        break;
                                    case R.id.test_schedule:
                                        Intent ii = new Intent(getActivity(), VideoListActivity.class);
                                        startActivity(ii);
                                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                                        break;
                                    case R.id.download:
                                        Intent iii = new Intent(getActivity(), Study.class);
                                        iii.putExtra("act","2");
                                        startActivity(iii);
                                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                                        break;

                                }

                                return true;
                            }
                        }
                );


        getNews();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.quiz_rv);
        adapter = new Student_adapter(getActivity(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onClick(Quizzz quizzz) {
        String id = quizzz.getTest_id();
        String time =quizzz.getTest_time();

        Log.e("zzzzzzzzzzzz", "onClick: "+id +time);
        Intent ii = new Intent(getActivity(), Quiz.class);
        ii.putExtra("id",id);
        ii.putExtra("time",time);
        startActivity(ii);

    }
    public void getNews()
    {

        Log.d(LOG_TAG,"getNews called");
        RequestBody formBody = new FormBody.Builder()
                .add("classId",urlHelper.classId)
                .add("data-source","android")
                .build();

        Request request = new Request.Builder()
                .url(urls)
                .post(formBody)
                .build();

        ProviderUtilities.providesOkhttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("ssssss","failed" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("ssssss","success"+response);

                String jsonResponse = response.body().string();
                final List<Quizzz> quizzzList = JsonParser.getQuizList(jsonResponse);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(quizzzList);
                    }
                });

            }
        });

    }

}
