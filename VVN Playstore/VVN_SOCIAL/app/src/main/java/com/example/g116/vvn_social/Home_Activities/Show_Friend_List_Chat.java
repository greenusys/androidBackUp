package com.example.g116.vvn_social.Home_Activities;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g116.vvn_social.Adapter.SearchFriendAdapter;
import com.example.g116.vvn_social.Adapter.ShowFriendList_Chat_Adapter;
import com.example.g116.vvn_social.Modal.Friend_List_Model;
import com.example.g116.vvn_social.Modal.SearchResult;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.Network_Package.GetLastIdCallback;
import com.example.g116.vvn_social.Network_Package.RequestBuilder;
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


public class Show_Friend_List_Chat extends Fragment {

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {

                String stringEtValue = edt_search.getText().toString().trim();
                searchFriends(stringEtValue);
            }
        }
    };

    private AppController appController;
    private ArrayList<Friend_List_Model> friend_list_models = new ArrayList<>();

    ArrayList<SearchResult> listModelSearchResult= new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ShowFriendList_Chat_Adapter showFriendListRecyclerAdapter;
    private ContentLoadingProgressBar loadingProgressBar;
    private EditText edt_search;
    private RecyclerView rv_search;
    private SearchFriendAdapter searchRecyclerAdapter;
    Show_Friend_List_Chat searchActivity = this;
    Handler handler = new Handler();
    SharedPreferences sp;
    private TextView no_frnd_text;
    private SessionManager session;
    private HashMap<String, String> user;
    String user_id ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_show__friend__list__chat, container, false);

        appController = (AppController) getContext().getApplicationContext();

        getActivity().setTitle("Chats");

        session = new SessionManager(getContext());
        user = session.getUserDetails();
         user_id = user.get(SessionManager.KEY_ID);

        no_frnd_text = v.findViewById(R.id.no_frnd_text);



        loadingProgressBar = (ContentLoadingProgressBar)v.findViewById(R.id.progress_bar);
        edt_search = v.findViewById(R.id.edt_search);

         recyclerView = v.findViewById(R.id.rv_showFriendList);//recycler view
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        showFriendListRecyclerAdapter = new ShowFriendList_Chat_Adapter(getContext(),friend_list_models);
        recyclerView.setAdapter(showFriendListRecyclerAdapter);


        //for search recycler view
        rv_search = v.findViewById(R.id.rv_search);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_search.setLayoutManager(layoutManager);
        rv_search.setLayoutManager(layoutManager);
        searchRecyclerAdapter = new SearchFriendAdapter(getContext(), listModelSearchResult);
        rv_search.setAdapter(searchRecyclerAdapter);


        fetch_FriendList(user_id);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                    //frnd list gone
                    recyclerView.setVisibility(View.VISIBLE);

                    listModelSearchResult.clear();
                    searchRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        return v;
    }



    private void searchFriends(String name){

        Log.e("searchFriends", "skdj");


        String url = "https://icosom.com/social/main/searchProcess.php?action=search";
        String key_a = "searchKey";
        String value_a = name;
        String key_b = "data-source";
        String value_b = "android";
        String key_c = "userId";
        String value_c =user_id;
        RequestBody body;

        body = RequestBuilder.threeParameter(key_a, value_a, key_b, value_b, key_c, value_c);
        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String response) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        SearchResult modelSearchResult;
                        listModelSearchResult.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            modelSearchResult = new SearchResult();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            if(jsonObject.getString("id").equalsIgnoreCase("533")){

                            }
                            else {



                                String profilePicture = jsonObject.getString("profilePicture");
                                modelSearchResult.setProfilePicture(profilePicture);
                                String firstName = jsonObject.getString("firstName");
                                modelSearchResult.setFirstName(firstName);
                                String lastName = jsonObject.getString("lastName");
                                modelSearchResult.setLastName(lastName);
                                String country = jsonObject.getString("country");
                                modelSearchResult.setCountry(country);
                                String id = jsonObject.getString("id");
                                modelSearchResult.setId(id);

                                String device_token = jsonObject.getString("device_token");
                                modelSearchResult.setDevice_token(device_token);

                                String city = jsonObject.getString("city");
                                modelSearchResult.setCity(city);

                                String phone = jsonObject.getString("phone");
                                modelSearchResult.setPhone(phone);

                                String email = jsonObject.getString("email");
                                modelSearchResult.setEmail(email);


                                listModelSearchResult.add(modelSearchResult);
                            }

                        }

                        if(getActivity()!=null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //frnd list gone
                                    recyclerView.setVisibility(View.GONE);

                                    //search friend list visible
                                    rv_search.setVisibility(View.VISIBLE);

                                    searchRecyclerAdapter.notifyDataSetChanged();


                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if(getActivity()!=null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    searchRecyclerAdapter.notifyDataSetChanged();


                                }
                            });
                        }





                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    private void fetch_FriendList(String user_id)
    {
        loadingProgressBar.show();

        Log.e("fetch_Frienist_APi_Call", "skdj");
        Log.e("user_id", ""+user_id);


        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/fetch_FriendApi.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingProgressBar.hide();
                            Toast.makeText(getContext(), "Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String path = "https://icosom.com/social/postFiles/images/";
                                final JSONObject mainjson = new JSONObject(myResponse);
                                Log.e("mainjson_frnd_list", "" + mainjson);
                                Log.e("status", "" + mainjson.getString("status"));


                                if (mainjson.getString("status").equals("1")) {

                                    if (friend_list_models != null)
                                        friend_list_models.clear();


                                    JSONArray jsonArray = mainjson.getJSONArray("data");


                                    for (int i = 0; i < jsonArray.length(); i++)//data loop
                                    {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                                        String friend_id = jsonObject.getString("id");
                                        ;
                                        String firstName = jsonObject.getString("firstName");
                                        String lastName = jsonObject.getString("lastName");
                                        String picture = jsonObject.getString("profilePicture");

                                        Log.e("User_pic_Profile_Dash", "" + path + picture);

                                        Friend_List_Model f = new Friend_List_Model();
                                        f.setFriend_id(friend_id);
                                        f.setFirstName(firstName);
                                        f.setLastName(lastName);
                                        f.setPicture(path + picture)
                                        ;
                                        friend_list_models.add(f);


                                    }//data loop closed

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadingProgressBar.hide();
                                                showFriendListRecyclerAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }


                                } else {

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadingProgressBar.hide();
                                                no_frnd_text.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }




}
