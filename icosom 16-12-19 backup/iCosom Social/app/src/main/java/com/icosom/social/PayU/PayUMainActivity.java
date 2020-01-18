package com.icosom.social.PayU;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.R;
import com.icosom.social.activity.DashboardRecharge;
import com.icosom.social.activity.PassbookRecharge;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;
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
import java.util.HashMap;

import okhttp3.RequestBody;

public class PayUMainActivity extends AppCompatActivity {

    AppController appController;

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private String dummyMobile = "7417966838";
    private String dummyAmount = "10";
    private String dummyEmail = "contact@greenusys.com";
    private String productInfo = "recharge";
    private String first_name = "icosom";
    String payUAmount = "";
    String paymentId = "";
    String status_transaction = "";

    Boolean test = false;
    SharedPreferences sp;



    private final String TAG = "PayUActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
        if (!test){
            test = !test;
        }else {
            startActivity(new Intent(PayUMainActivity.this, DashboardRecharge.class));
            finish();

         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: " );
        super.onBackPressed();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );

        setContentView(R.layout.activity_pay_umain);
        if (getIntent().getStringExtra("ADD_MONEY").equalsIgnoreCase("TRUE")){

            appController = (AppController)getBaseContext().getApplicationContext();
            sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

            dummyAmount = getIntent().getStringExtra("AMOUNT");
            ((AppController) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);

            launchPayUMoneyFlow();
        }else {
            startActivity(new Intent(PayUMainActivity.this, DashboardRecharge.class));
            finish();
        }
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setPayUmoneyActivityTitle("iPay");

        //Use this to set your custom text on result screen button
/*
        payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle(((EditText) findViewById(R.id.activity_title_et)).getText().toString());
*/

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

            /*
            * Hash should always be generated from your server side.
            * */
//            generateHashFromServer(mPaymentParams);

/*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PayUMainActivity.this, AppPreference.selectedTheme, true);
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PayUMainActivity.this, R.style.AppTheme_default, false);
            }

        } catch (Exception e) {
            // some exception occurred
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

//Success Transaction
                } else {
                    System.out.println("Transaction Response:  " + transactionResponse);

//Failure Transaction
                }
// Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                getPaymentResponse(transactionResponse.getPayuResponse());
                System.out.println("PayUTransaction: PayUResponse   " +  payuResponse);
// Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                System.out.println("PayUTransaction: MerchantResponse   " +  merchantResponse);

            }

        }
    }

    private void getPaymentResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            JSONObject result= jsonObject.getJSONObject("result");
            String result_status = result.getString("status");
            paymentId = result.getString("paymentId");
            status_transaction = result.getString("status");
            String error_Message = result.getString("error_Message");
            payUAmount = result.getString("amount");

            if (result_status.equalsIgnoreCase("success")){
                updateBalance(payUAmount);
            }else {
                Toast.makeText(PayUMainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PayUMainActivity.this, DashboardRecharge.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateBalance(String balance){
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "balance";
        String value_b = balance;
        String key_c = "transactionId";
        //set user email here
        String value_c = paymentId;
        String key_d = "status";
        String value_d = status_transaction;
        String url = "http://icosom.com/wallet/main/androidProcess.php?action=updateWalletBalance";

        RequestBody requestBody;
        requestBody = RequestBuilder.fourParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(PayUMainActivity.this, PassbookRecharge.class));
                                finish();
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
