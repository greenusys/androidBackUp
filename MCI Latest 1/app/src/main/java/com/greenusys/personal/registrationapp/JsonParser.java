package com.greenusys.personal.registrationapp;

import android.util.Log;

import com.greenusys.personal.registrationapp.pojos.Newz;
import com.greenusys.personal.registrationapp.pojos.Quizzz;
import com.greenusys.personal.registrationapp.pojos.Studyy;
import com.greenusys.personal.registrationapp.pojos.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-112 on 28-02-2018.
 */

public class JsonParser {


    public static List<Video> getVideoList(String jsonResponse)
    {
        List<Video> videoList = new ArrayList<>();
        try {

            JSONArray rootArray = new JSONArray(jsonResponse);

            for(int i=0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String classes = jsonObject.getString("classes");
                String video = jsonObject.getString("video");
                String title = jsonObject.getString("description");
                Log.e("ddd", "getVideoList: "+ classes);
                videoList.add(new Video(classes,video,title));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoList;
    }
    public static List<Quizzz> getQuizList(String jsonResponse)
    {
        List<Quizzz> quizzzList = new ArrayList<>();
        try {

            JSONArray rootArray = new JSONArray(jsonResponse);

            for(int i=0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String test_id = jsonObject.getString("test_id");
                String test_title = jsonObject.getString("test_title");
                String time = jsonObject.getString("test_time");
                Log.e("ddd", "getQuizList: "+ test_id);
                quizzzList.add(new Quizzz(test_id,test_title,time));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return quizzzList;
    }
    public static List<Studyy> getStudyList(String jsonResponse)
    {
        List<Studyy> studyList = new ArrayList<>();
        try {

            JSONArray rootArray = new JSONArray(jsonResponse);

            for(int i=0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String classes = jsonObject.getString("upload_file");
                String video = jsonObject.getString("description");
                String title = jsonObject.getString("classes");
                Log.e("ddd", "getVideoList: "+ classes);
                studyList.add(new Studyy(classes,video,title));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studyList;
    }

    public static List<Newz> getNewsList(String jsonResponse)
    {
        List<Newz> newzList = new ArrayList<>();
        try {

            JSONArray rootArray = new JSONArray(jsonResponse);

            for(int i=0; i<rootArray.length(); i++)
            {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String clss = jsonObject.getString("class");
                String title = jsonObject.getString("title");
                String news = jsonObject.getString("news");
                String time = jsonObject.getString("time");
                newzList.add(new Newz(id,clss,title,news,time));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newzList;
    }


}
