package com.icosom.social.fragment;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.icosom.social.Padapter;
import com.icosom.social.R;
import com.icosom.social.activity.AppSingleton;
import com.icosom.social.model.PlanModel;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

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

public class RechargePlan extends Fragment {
TextView txt;
    String VAL;
    String opid,cirid,type,mob;
    List<PlanModel> PlanModels = new ArrayList<>();
    String dt;
    private List<PlanModel> planModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Padapter adapterss;
    CommonFunctions urlHelper;
    AppController appController;
    String customerMobile, respons, balance;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    public RechargePlan() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public RechargePlan(String tup, String opid, String cirid, String mob) {
        VAL = tup;
        this.opid=opid;
        this.cirid=cirid;
        this.mob=mob;


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recharge_plan, container, false);
       // Toast.makeText(getActivity(), ""+VAL, Toast.LENGTH_SHORT).show();
        Log.e("list", "onCreate: "+opid+cirid+VAL+mob );
        appController = (AppController) getActivity().getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();


        recyclerView = v.findViewById(R.id.list_plans);
        adapterss = new Padapter(getActivity(), planModelList,mob,opid);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterss);
        // recharge();
       /* new ListOfPlan.SignIn1().execute();*/
       String URL = "https://api.rechapi.com/rech_plan.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&type="+VAL+"&cirid="+cirid+"&opid="+opid;
        Log.e("list", "onCreate: "+URL );
       // fetchNotification("https://api.rechapi.com/rech_plan.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&type=FTT&cirid=11&opid=7");
       volleyJsonObjectRequest(URL);
        adapterss.notifyDataSetChanged();

        return v;
    }
    public void volleyJsonObjectRequest(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";


        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject2.getJSONArray(VAL);
                            for (int i = 0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String a = jsonObject1.getString("amount");
                                String b = jsonObject1.getString("detail");
                                String c = jsonObject1.getString("validity");
                                String d = jsonObject1.getString("talktime");


                                Log.e("data_res", "onResponse: "+a+b+c+d );
                                PlanModel planModel = new PlanModel(a,b,c,d);
                                planModelList.add(planModel);


                            }
                            adapterss.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }
    private void fetchNotification(String URL)
    {
        RequestBody body = new FormBody.Builder().
                build();

        Request request = new Request.Builder().
                url(URL).
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

                Log.e("plansss", "onResponse: "+myResponse );

                try {
                    JSONObject jsonObject = new JSONObject(myResponse);
                    Log.e("plansss", "onResponse: "+jsonObject );
                    JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                    Log.e("plansss", "onResponse: "+jsonObject2 );
                    JSONArray jsonArray = jsonObject2.getJSONArray(type);
                    Log.e("plansss", "onResponse: "+jsonArray );
                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String a = jsonObject1.getString("amount");
                        String b = jsonObject1.getString("detail");
                        String c = jsonObject1.getString("validity");
                        String d = jsonObject1.getString("talktime");


                        Log.e("data_res", "onResponse: "+a+b+c+d );
                        PlanModel planModel = new PlanModel(a,b,c,d);
                        planModelList.add(planModel);


                    }
                    if (getActivity() != null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                adapterss.notifyDataSetChanged();
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


             /*   try {
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
                }*/

            }
        });
       /* if (check!= 0){
            text_notification.setVisibility(View.GONE);
        }*/
    }

}
