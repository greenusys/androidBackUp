package com.icosom.social.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.SearchUserModel;
import com.icosom.social.recycler_adapter.SearchRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity
{
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchUserModels = new ArrayList<>();
        defaultSearchUserModels = new ArrayList<>();

        db = new DBHelper(getBaseContext());
        cur = db.getDataFromUser();

        if (cur.getCount()>0)
        {
            cur.moveToLast();
            do
            {
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
                if (charSequence.toString().length()>0)
                    searchUser(sp.getString("userId", ""), charSequence.toString());
                else
                    putDefaultSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void putDefaultSearch()
    {
        searchUserModels.clear();
        searchUserModels.addAll(defaultSearchUserModels);
        searchRecyclerAdapter.notifyDataSetChanged();
    }

    private void searchUser(String userId, String searchString)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("searchKey", searchString).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.SEARCH_PROFILE).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String myResponse = response.body().string();
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            searchUserModels.clear();
                            final JSONArray ja = new JSONArray(myResponse);
                            for (int i = 0; i < ja.length(); i++)
                            {
                                searchUserModels.add(new SearchUserModel(
                                        ja.getJSONObject(i).getString("id"),
                                        ja.getJSONObject(i).getString("firstName"),
                                        ja.getJSONObject(i).getString("lastName"),
                                        ja.getJSONObject(i).getString("profilePicture"),
                                        ja.getJSONObject(i).getString("coverPhoto")
                                ));
                                searchRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}