package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.TagUserModel;
import com.icosom.social.recycler_adapter.SelectedTagFriendsRecyclerAdapter;
import com.icosom.social.recycler_adapter.TagFriendsRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

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

public class TagFriendsActivity extends AppCompatActivity
{
    RecyclerView rv_friendList;
    RecyclerView.LayoutManager layoutManager;
    TagFriendsRecyclerAdapter tagFriendsRecyclerAdapter;
    ArrayList<TagUserModel> friendsName, selectedFriendsName, searchFriendsName;
    ArrayList<Boolean> selected;
    LinearLayout lay_selectedTags;
    RecyclerView rv_selectedTags;
    RecyclerView.LayoutManager selectedLayoutManager;
    SelectedTagFriendsRecyclerAdapter selectedTagFriendsRecyclerAdapter;
    EditText edtSearch;
    SharedPreferences sp;
    AppController appController;
    TextView txt_done;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_friends);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        appController = (AppController) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        friendsName = new ArrayList<>();
        selected = new ArrayList<>();
        selectedFriendsName = new ArrayList<>();
        searchFriendsName = new ArrayList<>();

        txt_done = findViewById(R.id.txt_done);
        edtSearch = findViewById(R.id.edtSearch);
        lay_selectedTags = findViewById(R.id.lay_selectedTags);
        rv_selectedTags = findViewById(R.id.rv_selectedTags);
        rv_friendList = findViewById(R.id.rv_friendList);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        selectedLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);

        tagFriendsRecyclerAdapter = new TagFriendsRecyclerAdapter(TagFriendsActivity.this, friendsName, selected);
        getAllFriends();

        rv_friendList.setLayoutManager(layoutManager);
        rv_friendList.setAdapter(tagFriendsRecyclerAdapter);

        rv_selectedTags.setLayoutManager(selectedLayoutManager);
        selectedTagFriendsRecyclerAdapter = new SelectedTagFriendsRecyclerAdapter(TagFriendsActivity.this, selectedFriendsName);
        rv_selectedTags.setAdapter(selectedTagFriendsRecyclerAdapter);

        lay_selectedTags.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                friendsName.clear();

                for (TagUserModel tum : searchFriendsName)
                {
                    if (tum.getName().contains(charSequence.toString()))
                        friendsName.add(tum);
                }

                tagFriendsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFriendsName.size()>0)
                {
                    setResult(RESULT_OK, new Intent().
                            putExtra("tags", selectedFriendsName));
                }
                finish();
            }
        });
    }

    public void removeSelectedTags(int pos, TagUserModel model)
    {
        selectedFriendsName.remove(pos);
        selected.set(friendsName.indexOf(model), false);
        selectedTagFriendsRecyclerAdapter.notifyDataSetChanged();
        tagFriendsRecyclerAdapter.notifyDataSetChanged();
        if (selectedFriendsName.size()==0)
            lay_selectedTags.setVisibility(View.GONE);
    }

    public void addSelectedFriends(TagUserModel name)
    {
        selectedFriendsName.add(name);
        selectedTagFriendsRecyclerAdapter.notifyDataSetChanged();
        if (selectedFriendsName.size()>0)
            lay_selectedTags.setVisibility(View.VISIBLE);
    }

    public void removeSelectedFriends(TagUserModel name)
    {
        selectedFriendsName.remove(name);
        selectedTagFriendsRecyclerAdapter.notifyDataSetChanged();
        if (selectedFriendsName.size()==0)
            lay_selectedTags.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllFriends()
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_ALL_FRIENDS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                TagFriendsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();
                try
                {
                    JSONArray ja = new JSONArray(myResponse);
                    for (int i = 0; i < ja.length(); i++)
                    {
                        friendsName.add(new TagUserModel(
                                ja.getJSONObject(i).getString("id"),
                                ja.getJSONObject(i).getString("firstName")+" "+
                                ja.getJSONObject(i).getString("lastName"),
                                ja.getJSONObject(i).getString("profilePic"),
                                ja.getJSONObject(i).getString("coverPic")
                        ));
                        selected.add(false);
                    }

                    TagFriendsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchFriendsName.addAll(friendsName);
                            tagFriendsRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}