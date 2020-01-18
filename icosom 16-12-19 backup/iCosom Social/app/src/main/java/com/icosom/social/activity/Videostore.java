
package com.icosom.social.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.Videostore_model;
import com.icosom.social.recycler_adapter.Videostore_adapter_rev;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Videostore extends AppCompatActivity {
  RecyclerView recyclerView;
  Videostore_adapter_rev videostore_adapter_rev;
   Context context;
    List<Videostore_model> videostore_models = new ArrayList<>();

    Set<String> titles=new HashSet<String>();
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String url;

    AppController appController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videostore);
        recyclerView = findViewById(R.id.rv);
        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        loadData();

        videostore_adapter_rev = new Videostore_adapter_rev(getApplicationContext(),videostore_models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videostore_adapter_rev);
     //   videostore_adapter_rev.notifyDataSetChanged();




    }


    private void loadData()
    {

        System.out.println("load_data_counting");

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId","")).
                build();
        System.out.println("userId"+sp.getString("userId",""));
        final Request request = new Request.Builder().
                url(CommonFunctions.FETCH_FEEDS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Videostore.this.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Videostore.this, "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string();
                try {
                    JSONArray ja = new JSONArray(myResponse);

                    System.out.println("load_data_ja_length"+ja.length());
                    System.out.println("load_data_ja_length"+ja);

                    String filelink="";
                    String profilepic="";
                    String user_name="";
                    String user_post_date="";

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        JSONObject post = jo.getJSONObject("post");
                        ArrayList<String> postFileLinks = new ArrayList<>();
                        ArrayList<String> profilepiclink = new ArrayList<>();
                        if(post.getString("type").equals("2"))
                        {
                             filelink=post.getString("fileLink");


                            for (String str : filelink.split(",")) {
                                postFileLinks.add(str);
                            }
                            profilepic = post.getString("profilePicture");
                            user_name = post.getString("firstName");
                            user_post_date = post.getString("postTime");

                            videostore_models.add(new Videostore_model(filelink,postFileLinks,user_name,user_post_date,profilepic));
                            System.out.println("filelinksdfsdfsdfsdf"+profilepic);

                        }
                    }




                    if(Videostore.this != null)
                    {
                        Videostore.this.runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        videostore_adapter_rev.notifyDataSetChanged();
                                    }
                                }
                        );
                    }












                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
