package com.example.g116.vvn_social.Home_Activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.example.g116.vvn_social.Adapter.SearchRecyclerAdapter;
import com.example.g116.vvn_social.Database_Package.DBHelper;
import com.example.g116.vvn_social.Modal.SearchUserModel;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rv_search;
    RecyclerView.LayoutManager layoutManager;
    SearchRecyclerAdapter searchRecyclerAdapter;
    DBHelper db;
    Cursor cur;
    ArrayList<SearchUserModel> searchUserModels, defaultSearchUserModels;
    EditText edt_search;
    AppController appController;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    SearchActivity searchActivity = this;
    private SessionManager session;
    static int backfrom=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        appController = (AppController) getApplicationContext();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        final String user_type = user.get(SessionManager.KEY_USER_TYPE);


        searchUserModels = new ArrayList<>();
        defaultSearchUserModels = new ArrayList<>();

        db = new DBHelper(getBaseContext());
        cur = db.getDataFromUser();

        if (cur.getCount() > 0) {
            cur.moveToLast();
            do {
                defaultSearchUserModels.add(new SearchUserModel(
                        cur.getString(0), cur.getString(1),
                        cur.getString(2), cur.getString(3),
                        cur.getString(4)
                ));
            }
            while (cur.moveToPrevious());
            cur.close();
        }

        edt_search = findViewById(R.id.edt_search);
        rv_search = findViewById(R.id.rv_search);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rv_search.setLayoutManager(layoutManager);

        searchRecyclerAdapter = new SearchRecyclerAdapter(getBaseContext(), searchUserModels, db, searchActivity);
        rv_search.setAdapter(searchRecyclerAdapter);

        putDefaultSearch();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 1)
                    searchUser(user_type, charSequence.toString());

                else {
                    putDefaultSearch();

                    if (charSequence.toString().length()<1)//clear list data when edit text box string is empty
                    {
                        if (searchUserModels != null) {
                            searchUserModels.clear();
                            searchRecyclerAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    private void putDefaultSearch() {
        searchUserModels.clear();
        searchUserModels.addAll(defaultSearchUserModels);
        searchRecyclerAdapter.notifyDataSetChanged();
    }

    private void searchUser(String type, String searchString) {
        Log.e("Search_APi_Called", "skdj");

        RequestBody body = new FormBody.Builder().
                add("user", searchString).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/search_friend.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            searchUserModels.clear();

                            final JSONObject mainjson = new JSONObject(myResponse);
                            String user_type="";
                            String path = "https://vvn.city/";
                            if (mainjson.getString("status").equals("1"))
                            {
                                JSONArray data = mainjson.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++)
                                {
                                    JSONObject ja = data.getJSONObject(i);

                                    String picture=ja.getString("picture");

                                    picture=picture.replace("../","");

                                   // Log.e("User_path+picture",""+path+picture);


                                    String value= ja.getString("social_type");
                                    if(value.equals("1"))
                                        user_type="teacher";
                                    if(value.equals("2"))
                                        user_type="student";

                                  //  System.out.println("search_social_type"+user_type);


                                    searchUserModels.add(new SearchUserModel(
                                            ja.getString("sno"),//user id
                                            ja.getString("firstName"),
                                            ja.getString("lastName"),
                                            path+picture,// user picture
                                            user_type
                                            ));
                                    searchRecyclerAdapter.notifyDataSetChanged();




                                }









                            } else {
                                Toast.makeText(appController, "No User Found !", Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}