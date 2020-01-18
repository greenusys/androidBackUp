package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.activity.DashboardRecharge;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class Talent_List extends AppCompatActivity {
    Button add_trans;
    private List<Transfer_Model> talentModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransferAdapter adapter;
    CommonFunctions urlHelper;
    AppController appController;
    String customerMobile,respons,balance;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent__list);
        appController = (AppController) getApplicationContext();
        respons= getIntent().getStringExtra("response");
        customerMobile= getIntent().getStringExtra("customerMobile");
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        add_trans = findViewById(R.id.add_trans);
        add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Talent_List.this,AddBeneficiary.class).putExtra("customerMobile",customerMobile));
            }
        });

        recyclerView = findViewById(R.id.talent_rv);
        adapter = new TransferAdapter(Talent_List.this,talentModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        getBalance();

    }

    protected void setData(String s) {
        Log.e("talent", "onPostExecute: " + s);
        if (s != null) {
            try {
                Log.e("talentSSS", "onPostExecute: " + s);
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                Log.e("talentSSS", "onPostExecute: " + jsonObject2);
                JSONArray jsonArray = jsonObject2.getJSONArray("beneficiaryList");
                Log.e("talentSSS", "onPostExecute: " + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Log.e("talentSSS", "onPostExecute: " + jsonObject1.getString("beneficiaryName"));
                    Log.e("talentSSS", "onPostExecute: " + jsonObject1.getString("beneficiaryMobileNumber"));
                    Log.e("talentSSS", "onPostExecute: " + jsonObject1.getString("beneficiaryAccountNumber"));
                    Log.e("talentSSS", "onPostExecute: " + jsonObject1.getString("beneficiaryId"));


                     Transfer_Model talentModel = new Transfer_Model(jsonObject1.getString("beneficiaryName"), jsonObject1.getString("beneficiaryMobileNumber"), jsonObject1.getString("beneficiaryAccountNumber"), jsonObject1.getString("beneficiaryId"),customerMobile,balance);
                  //Transfer_Model talentModel1 = new Transfer_Model(customerMobile,customerMobile,customerMobile,customerMobile,customerMobile);
                    talentModelList.add(talentModel);
                }
                   /* String status = rootObject.getString("status");*/
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void getBalance(){
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String url ="https://icosom.com/wallet/main/androidProcess.php?action=checkWalletBalance";

        RequestBody body;

        body = com.icosom.social.utility.RequestBuilder.singleParameter(key_a, value_a);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(final String id) {
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            balance =message;
                                setData(respons);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Talent_List.this, DashboardRecharge.class));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(Talent_List.this, MainActivity.class));
    }
}
