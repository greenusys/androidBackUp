package com.greenusys.customerservice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.RequestBuilder;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.RequestBody;

public class PMainActivity extends AppCompatActivity {

    AppController appController;

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private String dummyMobile = "9850800060";
    private String dummyAmount = "10";
    private String dummyEmail = "contact@greenusys.com";
    private String productInfo = "eyematic";
    private String first_name = "eyematic";
    String payUAmount = "";
    String paymentId = "";
    String status_transaction = "";
    int i = 1;
    Boolean test = false;
    SharedPreferences sp;


    private final String TAG = "PayUActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        if (!test) {
            test = !test;
        } else {
            startActivity(new Intent(PMainActivity.this, CustomerDashboard.class));
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: ");
        super.onBackPressed();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");

        setContentView(R.layout.activity_main);
        if (i == 1) {

            appController = (AppController) getBaseContext().getApplicationContext();
            sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

            dummyAmount = getIntent().getStringExtra("AMOUNT");
            ((AppController) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);

            launchPayUMoneyFlow();
        } else {
            Toast.makeText(PMainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            // finish();
        }
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setPayUmoneyActivityTitle("Eyematic");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(dummyAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";
        String phone = dummyMobile;
        String productName = productInfo;
        String firstName = first_name;
        String email = dummyEmail;
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = ((AppController) getApplication()).getAppEnvironment();
        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();


            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PMainActivity.this, AppPreference.selectedTheme, true);
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PMainActivity.this, R.style.AppTheme_default, false);
            }

        } catch (Exception e) {

            //   Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            payNowButton.setEnabled(true);
        }
    }

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((AppController) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    System.out.println("Transaction Response:  " + transactionResponse);


                } else {
                    System.out.println("Transaction Response:  " + transactionResponse);


                }

                String payuResponse = transactionResponse.getPayuResponse();
                getPaymentResponse(transactionResponse.getPayuResponse());
                System.out.println("PayUTransaction: PayUResponse   " + payuResponse);

                String merchantResponse = transactionResponse.getTransactionDetails();
                System.out.println("PayUTransaction: MerchantResponse   " + merchantResponse);

            }

        }
    }

    private void getPaymentResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            JSONObject result = jsonObject.getJSONObject("result");
            String result_status = result.getString("status");
            paymentId = result.getString("paymentId");
            status_transaction = result.getString("status");
            String error_Message = result.getString("error_Message");
            payUAmount = result.getString("amount");

            if (result_status.equalsIgnoreCase("success")) {
                updateBalance(payUAmount);
            } else {
                Toast.makeText(PMainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PMainActivity.this, CustomerDashboard.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   private void updateBalance(String balance){
       Date c = Calendar.getInstance().getTime();
       System.out.println("Current time => " + c);

       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
       String formattedDate = df.format(c);




        String key_a = "name";
        //set user id here
        String value_a = sp.getString("name", "1");
        String key_b = "amount";
        String value_b = balance;
        String key_c = "paidOn";
        //set user email here
        String value_c = formattedDate;
        String key_d = "customerId";
        String value_d = sp.getString("UserId", "1");
       String key_e = "enggId";
       //set user email here
       String value_e = "cash";
       String key_f = "transId";
       String value_f = paymentId;
        String url = "http://abhishekjain.greenusys.website/customer_service/api/paymentGot.php";

        RequestBody requestBody;
        requestBody = RequestBuilder.sixParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d,key_e,value_e,key_f,value_f);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String statuss = jsonObject.getString("Code");
                        //String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(statuss.equalsIgnoreCase("1")) {
                                    Toast.makeText(PMainActivity.this, "Transcation Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PMainActivity.this, CustomerDashboard.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(PMainActivity.this, "Transcation Failure", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PMainActivity.this, CustomerDashboard.class));
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
