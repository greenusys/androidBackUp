package com.example.g116.vvn_social.Tab_Layout_Fragments;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g116.vvn_social.Adapter.ShowFriendRequestRecyclerAdapter;
import com.example.g116.vvn_social.Modal.FriendRequestModel;
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

public class ShowFriendRequestFragment extends Fragment {


    SwipeRefreshLayout srl_showFriendRequest;
    RecyclerView rv_showFriendRequest;
    RecyclerView.LayoutManager layoutManager;
    ShowFriendRequestRecyclerAdapter friendRequestRecyclerAdapter;
    AppController appController;
    ArrayList<FriendRequestModel> friendRequestModels;
    Boolean setAdapter = false;
    ShowFriendRequestFragment getInstance = this;
    SharedPreferences sp;
    public TextView txt_noFriendRequest,count_request;
    FriendRequestModel friendRequestModell;
    private SessionManager session;
    String user_type = "";
    String user_id = "";
    private ContentLoadingProgressBar loadingProgressBar;

    public static int backfrom;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_show_friend_request, container, false);


        getActivity().setTitle("Friend Request");

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        appController = (AppController) getContext().getApplicationContext();
        friendRequestModels = new ArrayList<>();
        loadingProgressBar = (ContentLoadingProgressBar)v.findViewById(R.id.progress_bar);

        txt_noFriendRequest = v.findViewById(R.id.txt_noFriendRequest);
        count_request = v.findViewById(R.id.count_request);
       // srl_showFriendRequest = v.findViewById(R.id.srl_showFriendRequest);
        rv_showFriendRequest = v.findViewById(R.id.rv_showFriendRequest);//recycler view
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_showFriendRequest.setLayoutManager(layoutManager);




        txt_noFriendRequest.setVisibility(View.GONE);


        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        user_id = user.get(SessionManager.KEY_ID);
        user_type = user.get(SessionManager.KEY_USER_TYPE);

        Log.e("user_id", "SD" + user_id);
        Log.e("user_type", "SD" + user_type);

        friendRequestRecyclerAdapter = new ShowFriendRequestRecyclerAdapter(user_id,user_type,getContext(), friendRequestModels, getInstance);
        rv_showFriendRequest.setAdapter(friendRequestRecyclerAdapter);



        fetch_Friend_Request_List(user_id,user_type);


        return v;
    }



    private void fetch_Friend_Request_List(String user_id,String type)
    {


        loadingProgressBar.show();
        Log.e("fetch_Frienist_APi_Call", "");

        Log.e("user_id", ""+user_id);
        Log.e("type", "skdj"+type);

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_friendrequest.php").
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
                            txt_noFriendRequest.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Something is Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();

                if(getActivity()!=null)

                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                final JSONObject mainjson = new JSONObject(myResponse);
                                Log.e("mainjson_frnd_list", "" + mainjson);
                                Log.e("status", "" + mainjson.getString("status"));
                                String path = "https://vvn.city/";
                                if (friendRequestModels != null)
                                    friendRequestModels.clear();


                                if (mainjson.getString("status").equals("1")) {


                                    JSONArray jsonArray = mainjson.getJSONArray("data");


                                    if (jsonArray.length() > 0) {

                                        for (int i = 0; i < jsonArray.length(); i++)//data loop
                                        {

                                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                                            String friend_id = jsonObject.getString("sno");
                                            String educationDetails = jsonObject.getString("educationDetails");
                                            String picture = jsonObject.getString("picture");
                                            String full_name = jsonObject.getString("firstName")
                                                    + " " + jsonObject.getString("lastName");

                                            picture = picture.replace("../", "");

                                            Log.e("friend_id", "" + friend_id);
                                            Log.e("educationDetails", "" + educationDetails);
                                            Log.e("picture", "" + path + picture);
                                            Log.e("full_name", "" + full_name);


                                            FriendRequestModel friendRequestModel = new FriendRequestModel();
                                            friendRequestModel.setFriend_id(friend_id);
                                            friendRequestModel.setEducationDetails(educationDetails);
                                            friendRequestModel.setPicture(path + picture);
                                            friendRequestModel.setFull_name(full_name);

                                            friendRequestModels.add(friendRequestModel);


                                        }//data loop closed
                                    }

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadingProgressBar.hide();

                                                txt_noFriendRequest.setVisibility(View.GONE);
                                                friendRequestRecyclerAdapter.notifyDataSetChanged();


                                            }
                                        });
                                    }


                                } else {

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadingProgressBar.hide();

                                                count_request.append("  " + String.valueOf(friendRequestModels.size()));

                                                txt_noFriendRequest.setVisibility(View.VISIBLE);
                                                friendRequestRecyclerAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }


                                }
                            } catch (JSONException e) {

                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingProgressBar.hide();

                                            txt_noFriendRequest.setVisibility(View.VISIBLE);

                                        }
                                    });
                                }

                                e.printStackTrace();
                            }
                        }
                    });

                }



            }
        });
    }


    public void accept_or_reject_friend_request(final FriendRequestModel pos, String user_id, String user_type, String action , String sent_to  ) {


        System.out.println("accept_or_reject_friend_request_called");

        Log.e("pos", "  " + pos);
        Log.e("user_id", "  " + user_id);
        Log.e("user_type", "  " + user_type);
        Log.e("action", "  " + action);
        Log.e("sent_to", "  " + sent_to);


        RequestBody body = new FormBody.Builder().
                add("type", user_type).
                add("action", action).
                add("sent_by", user_id).
                add("sent_to", sent_to).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/casesfriends.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();
                try
                {
                   final  JSONObject ja = new JSONObject(myResponse);
                    if (ja.getString("status").equals("1"))
                    {
                        System.out.println("if_msg__"+ja.getString("msg"));

                    }
                    else
                        System.out.println("else_msg__"+ja.getString("msg"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        backfrom=1;
    }
}