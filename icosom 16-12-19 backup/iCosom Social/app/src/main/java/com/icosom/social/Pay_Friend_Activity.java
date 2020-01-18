package com.icosom.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Adapter.Pay_Friend_Adapter;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.activity.SendMoney;
import com.icosom.social.model.PassbookModel;
import com.icosom.social.model.TagModel;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

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

public class Pay_Friend_Activity extends AppCompatActivity {
    FloatingActionButton search;
    RecyclerView recyclerView;
    Pay_Friend_Adapter pay_friend_adapter;
    ArrayList<TagModel> list;
    TextView accountid,balance;
    private AppController appController;
    ArrayList<PassbookModel> listPassbookModel;
    SharedPreferences sp;
    String id ;
    ProgressDialog progressDialog;
    public String user_device_token,friend_id,friend_email,user_id,user_email,amount,user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__friend_);


        appController = (AppController) getBaseContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

      //  list = new ArrayList<>();
        listPassbookModel = new ArrayList<>();
        appController = (AppController)getBaseContext().getApplicationContext();

        accountid=findViewById(R.id.accountid);
        balance=findViewById(R.id.balance);

        if(getIntent().getStringExtra("userid")!=null)
        {
            user_id = getIntent().getStringExtra("userid");
          accountid.setText("Account ID -"+getIntent().getStringExtra("userid"));
        }

        if(getIntent().getStringExtra("balance")!=null)
        {
            balance.setText("₹ "+getIntent().getStringExtra("balance"));
        }

        //getTransactionHistorysearch();
       // getTransactionHistory();

         search = (FloatingActionButton) findViewById(R.id.fab);
         search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(Pay_Friend_Activity.this, SendMoney.class).putExtra("user_id",user_id));
                /* String a = id ;
                 Log.e("myjanu","my"+a);

                startActivity(new Intent(Pay_Friend_Activity.this, SendMoney.class));
                 Toast.makeText(appController, "asdfsadfs"+a, Toast.LENGTH_SHORT).show();*/
             }
         });
      //  ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);




       /* intent.putExtra("friend_id",singleItem.getId());
        intent.putExtra("friend_email",singleItem.getFriend_email());
        intent.putExtra("user_id",singleItem.getUser_id());
        intent.putExtra("user_email",singleItem.getUser_email());
        intent.putExtra("amount",holder.editamount.getText().toString());
        intent.putExtra("user_name",singleItem.getFirstName()+" "+singleItem.getLastName());
*/

        if (getIntent().getStringExtra("friend_id") != null)
            friend_id = getIntent().getStringExtra("friend_id");

        if (getIntent().getStringExtra("friend_email") != null)
            friend_email = getIntent().getStringExtra("friend_email");

        if (getIntent().getStringExtra("user_id") != null)
            user_id = getIntent().getStringExtra("user_id");

             user_email = sp.getString("email", "");
             Log.e("useremail", "d" + user_email);

        if (getIntent().getStringExtra("amount") != null)
            amount = getIntent().getStringExtra("amount");


        if (getIntent().getStringExtra("user_name") != null)
            user_name = getIntent().getStringExtra("user_name");

        if (getIntent().getStringExtra("user_device_token") != null)
            user_device_token = getIntent().getStringExtra("user_device_token");

        getTransactionHistory();
        getBalance();

        if(user_device_token!=null && user_email!=null && friend_id!=null && friend_email!=null && user_email!=null && user_id!=null  && user_name!=null)
        {
            transferMoney();

            getTransactionHistory();
            getBalance();

            accountid.setText("Account ID -"+getIntent().getStringExtra("user_id"));
            accountid.setText("Account ID -"+getIntent().getStringExtra("user_id"));
        }




        recyclerView =findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
      //  recyclerView.addItemDecoration(itemDecoration);


        pay_friend_adapter = new Pay_Friend_Adapter(getApplicationContext(), listPassbookModel, R.layout.pay_friend_items);
        recyclerView.setAdapter(pay_friend_adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        getBalance();
        getTransactionHistory();

    }

    private void getBalance() {
        String key_a = "userId";
        //set user id here
       // String value_a = sp.getString("userId", "1");
        String url = "https://icosom.com/wallet/main/androidProcess.php?action=checkWalletBalance";

        RequestBody body;

        body = RequestBuilder.singleParameter(key_a, user_id);

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
                                balance.setText("₹ " + message);

                               // balance=message;
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

/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Pay_Friend_Activity.this, DashboardRecharge.class));
    }*/

    private void transferMoney()
    {

      //  final String device_token=sp.getString("device_token", "");//logged in user token


     //   progressDialog.show();

        String url = "https://icosom.com/wallet/main/androidProcess.php?action=addTransferMoney";
        String key_a = "friendId";

        String key_b = "friendEmail";
        //  value_b = "";
        String key_c = "userId";
        //  String value_c = sp.getString("userId", "1");
        String key_d = "userEmail";
        //  String value_d = "";
        String key_e = "transfer_Amount";
        //  String value_e = etAmountSendMoney.getText().toString().trim();
        String key_f = "userFirstName";
        //  String value_ff = sp.getString("firstName", "1")+" "+sp.getString("lastName", "1");

        RequestBody body=null;


        Log.e("friend_id","k"+friend_id);
        Log.e("friend_email","k"+friend_email);
        Log.e("user_id","k"+user_id);
        Log.e("user_email","k"+user_email);
        Log.e("amount","k"+amount);
        Log.e("user_name","k"+user_name);
        Log.e("device_token",""+user_device_token);


            body = RequestBuilder.sevenParams("device_token",user_device_token,key_a, friend_id, key_b, friend_email, key_c, user_id,key_d,user_email, key_e, amount, key_f, user_name);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    System.out.println("Send Money " + id);
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        if (status.equalsIgnoreCase("1")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // progressDialog.dismiss();
                                    Toast.makeText(Pay_Friend_Activity.this, message, Toast.LENGTH_SHORT).show();

                                    sendNotification_To_User(user_id,user_device_token,amount);
                                    //startActivity(new Intent(SendMoney.this, PassbookRecharge.class));
                                    //finish();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     //progressDialog.dismiss();
                                    Toast.makeText(Pay_Friend_Activity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    } catch (JSONException e) {
                       // progressDialog.dismiss();
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendNotification_To_User(String user_id,String device_token,String amount)
    {

        System.out.println("kaif_SendNotificationMethod");

        if(device_token==null && device_token.equals(""))
        {
            System.out.println("Device_Token_Is_Null");
        }
        else {


            String user_name = sp.getString("firstName", "")
                    + " " + sp.getString("lastName", "");

            String message = "";
            String title = "Icosom";


            message = user_name + " has " + " send "+amount+" "+"Rs";


            System.out.println("message" + message);
            System.out.println("title" + title);
            System.out.println("device_token" + device_token);


            RequestBody body = new FormBody.Builder().
                    add("send_to", "single").
                    add("firebase_token", device_token).
                    add("message", message).
                    add("title", "icosom").
                    add("image_url", "").
                    add("action", "").
                    add("user_id", user_id).

                    build();

            Request request = new Request.Builder().
                    url("https://icosom.com/kaif_notification/newindex.php").
                    post(body).
                    build();

            appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Errorrrrr " + e.getMessage());

                    Pay_Friend_Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String myResponse = response.body().string();

                    System.out.println("notification_send_Success");

                }
            });

        }
    }








   /* private void transfer_Money_And_Voucher(){
        String url = "https://icosom.com/superAdmin/voucher_api.php";
        String key_a = "voucher";

        String key_b = "friendEmail";
        value_b = "";
        String key_c = "userId";
        String value_c = sp.getString("userId", "1");
        String key_d = "userEmail";
        String value_d = "";
        String key_e = "transfer_Amount";

        final String value_a = voucher_code.getText().toString().trim();
        String key_f = "userFirstName";
        String value_ff = sp.getString("firstName", "1")+" "+sp.getString("lastName", "1");


        Log.e("key_kaif1","kas"+key_a);
        Log.e("key_kaif2","kas"+value_a);
        RequestBody body;

        body = RequestBuilder.twoParemeter(key_a,value_a);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    System.out.println("Send Money " + id);
                    try {
                        final JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");

                        if (status.equalsIgnoreCase("1")){
                            final JSONArray jsonArray=jsonObject.getJSONArray("message");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        try {
                                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                            String voucher_value=jsonObject1.getString("voucher_code");

                                            Log.e("voucher_value_kaif","aksdj"+voucher_value);

                                            if(value_a.equalsIgnoreCase(voucher_value))
                                                Toast.makeText(Pay_Friend_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(Pay_Friend_Activity.this, "Failed", Toast.LENGTH_SHORT).show();



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }



                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(Pay_Friend_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(SendMoney.this, "Failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/




    private void getTransactionHistory() {
        String key_a = "userId";
        final ProgressDialog pdLoading = new ProgressDialog(Pay_Friend_Activity.this);
        //set user id here
       // String value_a = sp.getString("userId", "1");
        //String url ="https://icosom.com/wallet/main/androidProcess.php?action=transactionHistory";
        String url ="https://icosom.com/wallet/main/transapi2.php";

        RequestBody body;

        body = RequestBuilder.singleParameter(key_a, user_id);

        pdLoading.setMessage("Loading...");
        pdLoading.show();
        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(final String response) {
                    System.out.println("Response : " + response);
                    Log.e("history", "lastId: "+response );
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                            if(listPassbookModel!=null)
                                listPassbookModel.clear();


                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PassbookModel passbookModel = new PassbookModel();
                            String id = jsonObject.getString("id");
                            passbookModel.setId(id);
                            String image = "https://icosom.com/social/postFiles/images/"+jsonObject.getString("profilePicture");
                            passbookModel.setImage(image);
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
                            String friend_email = jsonObject.getString("email");
                            passbookModel.setFriend_email(friend_email);
                            String device_tokens = jsonObject.getString("device_tokens");
                            passbookModel.setDevice_tokens(device_tokens);



                            listPassbookModel.add(passbookModel);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pay_friend_adapter.notifyDataSetChanged();
                                pdLoading.dismiss();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pdLoading.dismiss();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            pdLoading.dismiss();
        }

    }



    public void getTransactionHistorysearch(){
        String key_a = "userId";
        final ProgressDialog pdLoading = new ProgressDialog(Pay_Friend_Activity.this);
        //set user id here
        // String value_a = sp.getString("userId", "1");
        String url ="https://icosom.com/wallet/main/androidProcess.php?action=transactionHistory";

        RequestBody body;

        body = RequestBuilder.singleParameter(key_a, getIntent().getStringExtra("userid"));

        pdLoading.setMessage("Loading...");
        pdLoading.show();
        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(final String response) {
                    System.out.println("Response : " + response);
                    Log.e("history", "lastId: "+response );
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if(listPassbookModel!=null)
                            listPassbookModel.clear();

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PassbookModel passbookModel = new PassbookModel();
                            id = jsonObject.getString("id");
                            passbookModel.setId(id);
                            String image = "https://icosom.com/social/postFiles/images/"+jsonObject.getString("profilePicture");
                            passbookModel.setImage(image);
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

                            String user_email = jsonObject.getString("lastName");
                            passbookModel.setUser_email(lastName);
                            String friend_email = jsonObject.getString("lastName");
                            passbookModel.setFriend_email(lastName);


                            listPassbookModel.add(passbookModel);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pay_friend_adapter.notifyDataSetChanged();
                                pdLoading.dismiss();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pdLoading.dismiss();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            pdLoading.dismiss();
        }

    }

}
