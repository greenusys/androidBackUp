package com.icosom.social.fragment;

import android.content.Intent;
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
import com.icosom.social.activity.SinglePostActivity;
import com.icosom.social.model.NotificationModel;
import com.icosom.social.recycler_adapter.NotificationRecyclerAdapter;
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

public class NotificationFragment extends Fragment
{
    RecyclerView notification_recycler;
    RecyclerView.LayoutManager mLayoutManager;
    AppController appController;
    ArrayList<NotificationModel> notificationModels;
    NotificationRecyclerAdapter adapter;
    SharedPreferences sp;
    SwipeRefreshLayout srl_noti;
    TextView text_notification;
    int check= 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        appController = (AppController) getActivity().getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        text_notification = v.findViewById(R.id.text_notification);
        notification_recycler = v.findViewById(R.id.notification_recycler);
        srl_noti = v.findViewById(R.id.srl_noti);
        notificationModels = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notification_recycler.setLayoutManager(mLayoutManager);
        adapter = new NotificationRecyclerAdapter(getContext(), notificationModels, NotificationFragment.this);
        notification_recycler.setAdapter(adapter);

        srl_noti.setDistanceToTriggerSync(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            srl_noti.setProgressViewOffset(false, 0,3000);
        }

        srl_noti.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNotification(sp.getString("userId", ""));
            }
        });

        srl_noti.setRefreshing(true);
        fetchNotification(sp.getString("userId", ""));

        return v;
    }

    private void fetchNotification(String userId)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.FETCH_NOTIFICATION).
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

                System.out.println("gggggggggggg "+myResponse);

                try {
                    JSONObject jo = new JSONObject(myResponse);

                    JSONArray result = jo.getJSONArray("result");
                    check =  result.length();

                    for (int i = 0; i < result.length(); i++)
                    {

                        notificationModels.add(new NotificationModel(
                                result.getJSONObject(i).getString("id"),
                                result.getJSONObject(i).getString("notifyDate"),
                                result.getJSONObject(i).getString("readStatus"),
                                result.getJSONObject(i).getString("notifyToUser"),
                                result.getJSONObject(i).getString("postId"),
                                result.getJSONObject(i).getString("action"),
                                result.getJSONObject(i).getString("notifee"),
                                result.getJSONObject(i).getString("firstName"),
                                result.getJSONObject(i).getString("lastName"),
                                result.getJSONObject(i).getString("profilePicture")
                        ));
                    }

                    if (getActivity() != null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (srl_noti.isRefreshing())
                                    srl_noti.setRefreshing(false);

                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        if (check!= 0){
            text_notification.setVisibility(View.GONE);
        }
    }

    public void updateNotification(String notificationId)
    {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("notificationId", notificationId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.UPDATE_NOTIFICATION).
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
                System.out.println("kkkkkkkkkkkkkkkk "+response.body().string());
                // no response needed
            }
        });
    }

    public void goToSinglePost(int position)
    {
        updateNotification(notificationModels.get(position).getNotificationId());
        notificationModels.get(position).setReadStatus("1");
        adapter.notifyDataSetChanged();
        startActivity(new Intent(getContext(), SinglePostActivity.class).
                putExtra("postId", notificationModels.get(position).getPostId()+""));
    }
}