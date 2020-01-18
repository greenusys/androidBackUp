package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.icosom.social.Adapter.PassbookAdapter;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.R;
import com.icosom.social.model.PassbookModel;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class PassbookRecharge extends AppCompatActivity {

    private TextView tvBalance;
    private TextView tvAddMoney;
    private TextView tvSendMoney;
    SharedPreferences sp;
    private AppController appController;
    String idUser;

    RecyclerView rv_passbook;
    RecyclerView.LayoutManager layoutManager;
    PassbookAdapter adapter;
    AppBarLayout app_bar_passbook;
    List<PassbookModel> listPassbookModel;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PassbookRecharge.this, DashboardRecharge.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook_recharge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appController = (AppController)getBaseContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        idUser =sp.getString("userId", "1");

        tvBalance = findViewById(R.id.tvBalance);
        tvAddMoney = findViewById(R.id.tvAddMoney);
        tvAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PassbookRecharge.this, DashboardRecharge.class));//.putExtra("ADD_MONEY", "true"));
            }
        });

        tvSendMoney = findViewById(R.id.tvSendMoney);
        tvSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PassbookRecharge.this, SendMoney.class));
            }
        });

        getBalance();

        rv_passbook = findViewById(R.id.rv_passbook);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rv_passbook.setLayoutManager(layoutManager);
        listPassbookModel = new ArrayList<>();
        adapter = new PassbookAdapter(getBaseContext(), listPassbookModel,idUser);
        rv_passbook.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getTransactionHistory();

        app_bar_passbook = findViewById(R.id.app_bar_passbook);



     /*   TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_passbook);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_passbook);

        final PassbookPagerAdapter adapter = new PassbookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Transaction History");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/
    }

    private void getBalance(){
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String url ="https://icosom.com/wallet/main/androidProcess.php?action=checkWalletBalance";

        RequestBody body;

        body = RequestBuilder.singleParameter(key_a, value_a);

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
                                tvBalance.setText("₹ " + message);
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

    private void getTransactionHistory(){
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String url ="https://icosom.com/wallet/main/androidProcess.php?action=transactionHistory";

        RequestBody body;

        body = RequestBuilder.singleParameter(key_a, value_a);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(final String response) {
                    System.out.println("Response : " + response);
                    Log.e("history", "lastId: "+response );
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PassbookModel passbookModel = new PassbookModel();
                            String id = jsonObject.getString("id");
                            passbookModel.setId(id);
                            String user_id = jsonObject.getString("user_id");
                            passbookModel.setUser_id(user_id);
                            String transaction_id = jsonObject.getString("transaction_id");
                            passbookModel.setTransaction_id(transaction_id);
                            String amount = jsonObject.getString("amount");
                            passbookModel.setAmount(amount);
                            String status = jsonObject.getString("status");
                            passbookModel.setStatus(status);
                            String mobile_number = jsonObject.getString("mobile_number");
                            passbookModel.setMobile_number(mobile_number);
                            String operator_id = jsonObject.getString("operator_id");
                            passbookModel.setOperator_id(operator_id);
                            String error_warning = jsonObject.getString("error_warning");
                            passbookModel.setError_warning(error_warning);
                            String balance = jsonObject.getString("balance");
                            passbookModel.setBalance(balance);
                            String recharge_id = jsonObject.getString("recharge_id");
                            passbookModel.setRecharge_id(recharge_id);
                            String transaction_type = jsonObject.getString("transaction_type");
                            passbookModel.setTransaction_type(transaction_type);
                            String transfer_to_id = jsonObject.getString("transfer_to_id");
                            passbookModel.setTransfer_to_id(transfer_to_id);
                            String currentdate = jsonObject.getString("currentdate");
                            passbookModel.setCurrentdate(currentdate);
                            String firstName = jsonObject.getString("firstName");
                            passbookModel.setFirstName(firstName);
                            String lastName = jsonObject.getString("lastName");
                            passbookModel.setLastName(lastName);
                            listPassbookModel.add(passbookModel);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
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
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(PassbookRecharge.this, MainActivity.class));
    }
}
