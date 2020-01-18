package com.greenusys.personal.registrationapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.personal.registrationapp.Adapter.Subject_Adapter;
import com.greenusys.personal.registrationapp.FullNewzActivity;
import com.greenusys.personal.registrationapp.JsonParser;
import com.greenusys.personal.registrationapp.MainActivity;
import com.greenusys.personal.registrationapp.ProviderUtilities;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.Subjects;
import com.greenusys.personal.registrationapp.Utility.AppController;
import com.greenusys.personal.registrationapp.Utility.UrlHelper;
import com.greenusys.personal.registrationapp.pojos.News;
import com.greenusys.personal.registrationapp.pojos.Newz;
import com.greenusys.personal.registrationapp.pojos.Subject_Modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by personal on 2/20/2018.
 */

public class NewsFragment extends Fragment implements NewsAdapter.NewItemOnClickHandler {

    private static final String LOG_TAG = NewsFragment.class.getSimpleName();
    NewsAdapter adapter;
    RecyclerView recyclerView;
    UrlHelper urlHelper;
    String urls="http://greenusys.website/mci/api/fetchNews.php";
    String classId;
    AppController appController;
    ArrayList<News>newslist;
    public NewsFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        urlHelper = new UrlHelper();
        urls = urlHelper.news;
        classId = urlHelper.classId;


        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_rv);

        if(newslist!=null)
            newslist.clear();

        newslist= MainActivity.newslist;

        System.out.println("newslistkaif1"+newslist);




        adapter = new NewsAdapter(getActivity(), this,newslist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);



        return rootView;
    }









    @Override
    public void onClick(Newz newz) {

        Intent intent = new Intent(getContext(), FullNewzActivity.class);

        intent.putExtra(getString(R.string.newz_title), newz.getTitle());
        intent.putExtra("time", newz.getTime());
        intent.putExtra(getString(R.string.newz_descrition), newz.getNews());

        startActivity(intent);
    }
}
