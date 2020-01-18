package com.icosom.social.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.model.FriendRequestModel;
import com.icosom.social.recycler_adapter.ShowFriendRequestRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowFriendRequestFragment extends Fragment
{
    SwipeRefreshLayout srl_showFriendRequest;
    RecyclerView rv_showFriendRequest;
    RecyclerView.LayoutManager layoutManager;
    ShowFriendRequestRecyclerAdapter friendRequestRecyclerAdapter;
    AppController appController;
    ArrayList<FriendRequestModel> friendRequestModels;
    Boolean setAdapter = false;
    ShowFriendRequestFragment getInstance = this;
    SharedPreferences sp;
    TextView txt_noFriendRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_show_friend_request, container, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        appController = (AppController) getContext().getApplicationContext();

        txt_noFriendRequest = v.findViewById(R.id.txt_noFriendRequest);
        srl_showFriendRequest = v.findViewById(R.id.srl_showFriendRequest);
        rv_showFriendRequest = v.findViewById(R.id.rv_showFriendRequest);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_showFriendRequest.setLayoutManager(layoutManager);
        friendRequestModels = new ArrayList<>();

        txt_noFriendRequest.setVisibility(View.GONE);

        srl_showFriendRequest.setDistanceToTriggerSync(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            srl_showFriendRequest.setProgressViewOffset(false, 0,300);
        }

        srl_showFriendRequest.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                friendRequestModels.clear();
                getFriendRequests();
            }
        });

        srl_showFriendRequest.setRefreshing(true);
        getFriendRequests();

        return v;
    }

    public void getFriendRequests() {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.FETCH_FRIEND_REQUEST).
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
                        txt_noFriendRequest.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();
                try
                {
                    JSONArray jo = new JSONArray(myResponse);
                    for (int i = 0; i < jo.length(); i++)
                    {
                        friendRequestModels.add(new FriendRequestModel(
                                jo.getJSONObject(i).getString("id"),
                                jo.getJSONObject(i).getString("firstName"),
                                jo.getJSONObject(i).getString("lastName"),
                                jo.getJSONObject(i).getString("profilePicture")
                        ));
                    }
                    if (setAdapter)
                    {
                        if (getActivity() != null)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (srl_showFriendRequest.isRefreshing())
                                        srl_showFriendRequest.setRefreshing(false);

                                    if (friendRequestModels.size()==0)
                                        txt_noFriendRequest.setVisibility(View.VISIBLE);
                                    else
                                        txt_noFriendRequest.setVisibility(View.GONE);

                                    friendRequestRecyclerAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                    else
                    {
                        if (getActivity() != null)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (srl_showFriendRequest.isRefreshing())
                                        srl_showFriendRequest.setRefreshing(false);

                                    setAdapter = true;
                                    friendRequestRecyclerAdapter = new ShowFriendRequestRecyclerAdapter(getContext(),
                                            friendRequestModels, getInstance);
                                    rv_showFriendRequest.setAdapter(friendRequestRecyclerAdapter);

                                    if (friendRequestModels.size()==0)
                                        txt_noFriendRequest.setVisibility(View.VISIBLE);
                                    else
                                        txt_noFriendRequest.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void acceptFriendRequests(String friendsUserId, final int pos) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", friendsUserId).
                add("active_user_id", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.ACCEPT_FIREND_REQUEST).
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
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1") || jo.getString("message").equals("Success"))
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                friendRequestModels.remove(pos);
                                friendRequestRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteFriendRequests(String friendsUserId, final int pos)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("friendId", friendsUserId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.DELETE_FIREND_REQUEST).
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
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1") || jo.getString("message").equals("Success"))
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                friendRequestModels.remove(pos);
                                friendRequestRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void followFriendRequests(String friendsUserId, final int pos)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("friendId", friendsUserId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.FOLLOW_FIREND_REQUEST).
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
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1") || jo.getString("message").equals("Success"))
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                friendRequestModels.remove(pos);
                                friendRequestRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}