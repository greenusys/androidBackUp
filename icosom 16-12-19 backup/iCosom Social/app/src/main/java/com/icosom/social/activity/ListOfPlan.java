package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.icosom.social.Padapter;
import com.icosom.social.R;
import com.icosom.social.model.PlanModel;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfPlan extends AppCompatActivity {

    List<PlanModel> PlanModels = new ArrayList<>();
    String dt;


    String opid,cirid,type,mob;



    private List<PlanModel> planModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Padapter adapterss;
    CommonFunctions urlHelper;
    AppController appController;
    String customerMobile, respons, balance;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_plan);
        appController = (AppController) getApplicationContext();
        opid = getIntent().getStringExtra("opid");
        cirid = getIntent().getStringExtra("cirid");
        type = getIntent().getStringExtra("type");
        mob = getIntent().getStringExtra("num");
        Log.e("list", "onCreate: "+opid+cirid+type+mob );
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();


        recyclerView = findViewById(R.id.list_plan);
        adapterss = new Padapter(ListOfPlan.this, planModelList,mob,opid);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterss);
       // recharge();
       /* new ListOfPlan.SignIn1().execute();*/
        volleyJsonObjectRequest("https://api.rechapi.com/rech_plan.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&type="+type+"&cirid="+cirid+"&opid="+opid);
        adapterss.notifyDataSetChanged();


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
                            JSONArray jsonArray = jsonObject2.getJSONArray(type);
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }










    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListOfPlan.this, DashboardRecharge.class));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(ListOfPlan.this, MainActivity.class));
    }

}