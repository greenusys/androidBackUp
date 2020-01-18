package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class CheckBalance extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBalance;
    private TextView tvAddMoney;
    private TextView tvSendMoney;
    SharedPreferences sp;
    private AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        appController = (AppController)getBaseContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        tvBalance = findViewById(R.id.tvBalance);
        tvAddMoney = findViewById(R.id.tvAddMoney);
        tvAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckBalance.this, DashboardRecharge.class));//.putExtra("ADD_MONEY", "true"));
            }
        });

        tvSendMoney = findViewById(R.id.tvSendMoney);
        tvSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckBalance.this, SendMoney.class));
            }
        });

        getBalance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAddMoney:
                break;

            case R.id.tvSendMoney:
                break;

            default:
                break;
        }
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

}
