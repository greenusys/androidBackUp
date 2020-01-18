package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.PassbookRecharge;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.RequestBody;

public class TransferMoney extends AppCompatActivity {
    CommonFunctions urlHelper;
    AppController appController;
    EditText transferAmount;
    String transferAmounts, phone, beneficiaryId, urid = "85746531";
    TextView sub;
    ProgressBar progressBar;
    String currentDateandTime;
    private String url, customerMobile, balance;
    int i = 0;
    int bal,amount,deBalance;
    String statuss,statusss;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TransferMoney.this, MainActivity.class));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(TransferMoney.this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        beneficiaryId = getIntent().getStringExtra("beneficiaryId");
        customerMobile = getIntent().getStringExtra("customerMobile");
        balance = getIntent().getStringExtra("balance");
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        //user_id = sp.getString("userId", "");

        bal = Integer.parseInt(balance);
        progressBar = findViewById(R.id.transfer6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        currentDateandTime = sdf.format(new Date());
        appController = (AppController) getApplicationContext();
        urlHelper = new CommonFunctions();
        transferAmount = findViewById(R.id.transferAmount);

        sub = findViewById(R.id.transferSubmit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferAmounts = transferAmount.getText().toString();
                if(transferAmounts.isEmpty()) {
                    transferAmount.setError("Please Enter Amount ");

                }
                else {

                    amount = Integer.parseInt(transferAmounts);
                    if (amount <= 1000) {
                        deBalance = amount + 10;
                    } else {
                        deBalance = amount + 20;
                    }

                    if (deBalance <= bal) {


                        url = urlHelper.moneyTransfer;
                        url = url + "&customerMobile=" + customerMobile + "&amount=" + transferAmounts + "&beneficiaryId=" + beneficiaryId + "&urid=" + currentDateandTime;
                        // url=url+"&customerMobile=9458124722"+"&amount=10"+"&beneficiaryId="+beneficiaryId+"&urid="+urid;
                        Log.e("talent", "onPostExecute: " + url);
                        setData();
                    } else {
                        Toast.makeText(TransferMoney.this, "You donot have enough balance in your icosom account ", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        //setData();

    }


    private void setData() {
        if (i == 0) {
            i = 1;
            bal = bal-deBalance;


            new TransferMoney.SignIn1().execute();
            progressBar.setVisibility(View.VISIBLE);

        }
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {
        private String response;
        private RequestBody body;


        @Override
        protected String doInBackground(String... strings) {

            body = RequestBuilder.NoParameter();

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("talent", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("talent", "onPostExecute: " + response);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    String error_code = jsonObject1.getString("error_code");
                    if (error_code.equalsIgnoreCase("200")) {

                       updateBalances();
                      //  startActivity(new Intent(TransferMoney.this, DashboardRecharge.class));
                    } else {
                        i = 0;

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(TransferMoney.this, "Failure : " +jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();
                         //  startActivity(new Intent(TransferMoney.this,DashboardRecharge.class));
                        updateBalances();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void updateBalances(){
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "balance";
        String value_b = ""+deBalance;

        String url = "https://icosom.com/wallet/main/dmrAndroidProcess.php?action=updateBalance";

        RequestBody requestBody;
        requestBody = com.icosom.social.utility.RequestBuilder.twoParameter(key_a, value_a, key_b, value_b);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {  Log.e("idsssss", "lastId: +, "+id);

                        JSONObject jsonObject = new JSONObject(id);
                         statuss = jsonObject.getString("status");
                        final String message = jsonObject.getString("Balance");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(statuss.equalsIgnoreCase("success")) {
                                  updateBalance();
                                }
                                else{
                                    Toast.makeText(TransferMoney.this, "Failure", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(TransferMoney.this, PassbookRecharge.class));
                                    finish();
                                }
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
    private void updateBalance(){
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "amount";
        String value_b = ""+deBalance;
        String key_c = "transactionId";
        String value_c = currentDateandTime;
        String key_d = "number";
        String value_d = customerMobile;
        String key_e = "accNumb";
        String value_e =  beneficiaryId;
        String url = "https://icosom.com/wallet/main/dmrAndroidProcess.php?action=insertInfo";

        RequestBody requestBody;
        requestBody = com.icosom.social.utility.RequestBuilder.fiveParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        Log.e("id", "lastId: +, "+id);
                        JSONObject jsonObject = new JSONObject(id);
                        statusss = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(statusss.equalsIgnoreCase("success")) {
                                    Toast.makeText(TransferMoney.this, "Suceess", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(TransferMoney.this, PassbookRecharge.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(TransferMoney.this, "Failure"+message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(TransferMoney.this, PassbookRecharge.class));
                                    finish();
                                }
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




