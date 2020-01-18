package com.icosom.social;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.utility.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.RequestBody;

public class Confirm extends Dialog implements android.view.View.OnClickListener {

    String URL = "http://api.rechapi.com/recharge.php?format=#format&token=#token&mobile=#mobile&amount=#amount&opid=#opid&urid=#urid&opvalue1=#opvalue1&opvalue2=#opvalue2";
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    public Activity c;
    Context C;
    int val;

    int bal, bals, amount;
    private AppController appController;
    public Dialog d;
    public Button yes, no;
    TextView amt, mob;
    String mobi, amts, opid, urid, userId;

    public Confirm(Context a, String amts, String mobi, String opid) {
        super(a);
        // TODO Auto-generated constructor stub
        this.C = a;
        this.mobi = mobi;
        this.amts = amts;
        this.opid = opid;
    }

    public Confirm() {
        super(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        appController = (AppController) getContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();
        userId = sp.getString("userId", "");
        bal = Integer.parseInt(amts);
        bals = bal + 10;
        getBalance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        urid = sdf.format(new Date());
        amt = findViewById(R.id.c_amount);
        mob = findViewById(R.id.c_phone);
        yes = (Button) findViewById(R.id.btn_con);
        no = (Button) findViewById(R.id.btn_can);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        amt.setText("Amount : " + amts);
        mob.setText("Number :" + mobi);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_con:
                check();

                break;
            case R.id.btn_can:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void check() {

        if (amount >= bal) {
            amount = amount - bal;
            Log.e("bal", "check: " + amount);
            //   updateBalances();
            int ii = getrecharge();
            if (ii == 1) {
                Toast.makeText(getContext(), "Recharge success ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Recharge is Progress. Wait for 10 min ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "Your account balance is low", Toast.LENGTH_SHORT).show();
        }
    }

    private int getrecharge() {


        String url = "http://api.rechapi.com/recharge.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&mobile=" + mobi + "&amount=" + amts + "&opid=" + opid + "&urid=" + urid;

        RequestBody body;

        body = com.icosom.social.utility.RequestBuilder.noParameterTest();

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(final String id) {
                    try {
                        Log.e("rechargessss", "onPostExecute: " + id);

                        JSONObject jsonObject = new JSONObject(id);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        String error_code = jsonObject1.getString("error_code");
                        if (error_code.equalsIgnoreCase("200")) {
                            val = 1;
                            updateBalances();

                            //  startActivity(new Intent(getContext(), DashboardRecharge.class));
                        } else {
                            val = 2;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (val == 1) {
            return 1;
        } else {
            return 2;
        }

    }

    private void getBalance() {
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String url = "https://icosom.com/wallet/main/androidProcess.php?action=checkWalletBalance";

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
                        try {
                            amount = Integer.parseInt(message);
                        } catch (NumberFormatException e) {
                            //not int
                        }
                        //check if float
                        try {
                            float amounts = Float.parseFloat(message);
                            amount= (int)amounts;
                        } catch (NumberFormatException e) {
                            //not float
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateBalances() {
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "balance";
        String value_b = "" + bals;

        String url = "https://icosom.com/wallet/main/dmrAndroidProcess.php?action=updateBalance";

        RequestBody requestBody;
        requestBody = com.icosom.social.utility.RequestBuilder.twoParameter(key_a, value_a, key_b, value_b);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        Log.e("idsssss", "lastId: +, " + id);

                        JSONObject jsonObject = new JSONObject(id);
                        String statuss = jsonObject.getString("status");
                        final String message = jsonObject.getString("Balance");
                        if (statuss.equalsIgnoreCase("success")) {
                            updateBalance();
                        } else {
                            //   Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                            // C.startActivity(new Intent(getContext(), PassbookRecharge.class));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void updateBalance() {
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "amount";
        String value_b = "" + amts;
        String key_c = "transactionId";
        String value_c = urid;
        String key_d = "number";
        String value_d = mobi;
        String key_e = "accNumb";
        String value_e = "Recharge";
        String url = "https://icosom.com/wallet/main/dmrAndroidProcess.php?action=insertInfo";

        RequestBody requestBody;
        requestBody = com.icosom.social.utility.RequestBuilder.fiveParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        Log.e("id", "lastId: +, " + id);
                        JSONObject jsonObject = new JSONObject(id);
                        String statusss = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        if (statusss.equalsIgnoreCase("success")) {
                            //C.startActivity(new Intent(C,));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void msg() {
        Toast.makeText(getContext(), "Recharge Success", Toast.LENGTH_SHORT).show();
    }
}




