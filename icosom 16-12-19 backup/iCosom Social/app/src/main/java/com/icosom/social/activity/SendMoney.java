package com.icosom.social.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Adapter.SearchAdapter;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.Pay_Comfirmation_Activity;
import com.icosom.social.R;
import com.icosom.social.model.SearchResult;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class SendMoney extends AppCompatActivity {

    SharedPreferences sp;
    ProgressDialog progressDialog;
    public String value_a = "";
    public String value_b = "";
    public String value_f = "";
    public String value_c = "";

    public TextView tvSubmitSendMoney;
    public EditText etAmountSendMoney,voucher_code;
    public EditText etSearchFriendSendMoney;
    public RecyclerView rvSearchFriendSendMoney;
    SearchAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> stringList;
    AppController appController;
    ArrayList<SearchResult> listModelSearchResult;
    //SharedPreferences sp_user;
    ProgressBar progressBar;
    public String user_device_token="";
    public String friend_id,friend_email,user_id,user_email,amount,friend_name,friend_phone;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    TextView voucher_response,final_pay ;
    String voucher_discount="",final_amount="",amount_to_be_send="";
    EditText edit_amount;
    Handler handler = new Handler();

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {

                String stringEtValue = etSearchFriendSendMoney.getText().toString().trim();


               searchFriends(stringEtValue);
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        appController = (AppController) getBaseContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        progressBar = findViewById(R.id.pb_login_subs);

        user_id = sp.getString("userId", "1");
        user_email = sp.getString("email", "");

        friend_name = sp.getString("firstName", "1")+" "+sp.getString("lastName", "1");

        appController = (AppController) getBaseContext().getApplicationContext();
        etSearchFriendSendMoney = findViewById(R.id.etSearchFriendSendMoney);

        etSearchFriendSendMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    rvSearchFriendSendMoney.setVisibility(View.VISIBLE);
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                    listModelSearchResult.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        rvSearchFriendSendMoney = findViewById(R.id.rvSearchFriendSendMoney);
        layoutManager = new LinearLayoutManager(SendMoney.this, LinearLayoutManager.VERTICAL, false);
        rvSearchFriendSendMoney.setLayoutManager(layoutManager);
        listModelSearchResult = new ArrayList<>();
        adapter = new SearchAdapter(SendMoney.this, listModelSearchResult);
        rvSearchFriendSendMoney.setAdapter(adapter);

        etAmountSendMoney = findViewById(R.id.etAmountSendMoney);
        voucher_code = findViewById(R.id.voucher_code);

        tvSubmitSendMoney = findViewById(R.id.tvSubmitSendMoney);
        tvSubmitSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setTitle("Send Money").setView(R.layout.layout_dialogue);
                }

                final AlertDialog dialog = builder.create();
                dialog.show();

                final AlertDialog diagview = ((AlertDialog) dialog);

                TextView frnd_name3 = (TextView) diagview.findViewById(R.id.textView4);
                TextView frnd_email3 = (TextView) diagview.findViewById(R.id.textView5);
                TextView frnd_mob3 = (TextView) diagview.findViewById(R.id.textView6);
                final_pay = (TextView) diagview.findViewById(R.id.textView9);
                voucher_response = (TextView) diagview.findViewById(R.id.textView8);

                frnd_name3.append(friend_name);
                frnd_email3.append(friend_email);
                frnd_mob3.append(friend_phone);


                Button Pay = (Button) diagview.findViewById(R.id.pay);
                Button Apply_Now = (Button) diagview.findViewById(R.id.apply_code);
                edit_amount = (EditText) diagview.findViewById(R.id.editText);
                final  EditText voucher_code = (EditText) diagview.findViewById(R.id.editText3);

                //custom lisenter for Positive button
                Pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(edit_amount.getText().toString().isEmpty()){
                            edit_amount.setError("Enter Amount!");
                        }

                        /*else if(voucher_code.getText().toString().isEmpty()){
                            voucher_code.setError("Enter Voucher Code!");
                        }*/
                        else{

                            if(voucher_discount.equals("")) {
                                amount_to_be_send=edit_amount.getText().toString();


                                Intent intent=new Intent(SendMoney.this,Pay_Comfirmation_Activity.class);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_device_token",user_device_token);

                                intent.putExtra("user_email",user_email);

                                intent.putExtra("friend_id",friend_id);
                                intent.putExtra("friend_name",friend_name);
                                intent.putExtra("friend_email",friend_email);
                                intent.putExtra("friend_mobile",friend_phone);
                                intent.putExtra("amount",edit_amount.getText().toString());
                                //intent.putExtra("total_discount","0");
                                //intent.putExtra("amount_to_be_paid",String.valueOf(result2));

                                startActivity(intent);

                                // transferMoney();
                            }


                            else {

                                int result = Integer.parseInt(edit_amount.getText().toString())
                                        *
                                        Integer.parseInt(voucher_discount)
                                        /
                                        100;//get amount discount

                                int result2=Integer.parseInt(edit_amount.getText().toString())-result;//discount
                                amount_to_be_send=String.valueOf(result2);

                               /* final_pay.append(amount_to_be_send);
                                final_pay.setVisibility(View.VISIBLE);
*/

                                Log.e("final_amount","ksdjf"+edit_amount.getText().toString());
                                Log.e("voucher_discount","ksdjf"+voucher_discount);
                                Log.e("total-discount","ksdjf"+result);
                                Log.e("result-after-discount","ksdjf"+result2);

                                //transferMoney();
                                Intent intent=new Intent(SendMoney.this,Pay_Comfirmation_Activity.class);

                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_device_token",user_device_token);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("friend_id",friend_id);
                                intent.putExtra("friend_name",friend_name);
                                intent.putExtra("friend_email",friend_email);
                                intent.putExtra("friend_mobile",friend_phone);
                                intent.putExtra("amount",String.valueOf(result2));//amount after calculating discount
                                intent.putExtra("total_discount",String.valueOf(result));
                                intent.putExtra("amount_to_be_paid",edit_amount.getText().toString());


                                //   intent.putExtra("amount_to_be_paid",String.valueOf(result2));

                                startActivity(intent);

                            }

                        }
                    }
                });

                //custom lisenter for Cancel button
                Apply_Now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(voucher_code.getText().toString().isEmpty())
                        {
                            voucher_code.setError("Enter Voucher Code!");
                        }


                        else
                            transfer_Money_And_Voucher(voucher_code.getText().toString().trim());

                    }
                });





            }
        });



    }





    private void searchFriends(String name){

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://icosom.com/social/main/searchProcess.php?action=search";
        String key_a = "searchKey";
        String value_a = name;
        String key_b = "data-source";
        String value_b = "android";
        String key_c = "userId";
        String value_c = sp.getString("userId", "1");
        RequestBody body;

        body = RequestBuilder.threeParameter(key_a, value_a, key_b, value_b, key_c, value_c);
        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String response) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        SearchResult modelSearchResult;
                        listModelSearchResult.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            modelSearchResult = new SearchResult();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            if(jsonObject.getString("id").equalsIgnoreCase(sp.getString("userId", "1"))){

                            }
                            else {



                                String profilePicture = jsonObject.getString("profilePicture");
                                modelSearchResult.setProfilePicture(profilePicture);
                                String firstName = jsonObject.getString("firstName");
                                modelSearchResult.setFirstName(firstName);
                                String lastName = jsonObject.getString("lastName");
                                modelSearchResult.setLastName(lastName);
                                String country = jsonObject.getString("country");
                                modelSearchResult.setCountry(country);
                                String id = jsonObject.getString("id");
                                modelSearchResult.setId(id);

                                String device_token = jsonObject.getString("device_token");
                                modelSearchResult.setDevice_token(device_token);

                                String city = jsonObject.getString("city");
                                modelSearchResult.setCity(city);

                                String phone = jsonObject.getString("phone");
                                modelSearchResult.setPhone(phone);

                                String email = jsonObject.getString("email");
                                modelSearchResult.setEmail(email);
                                String is_friend = jsonObject.getString("isfriend");
                                String gotRequest = jsonObject.getString("gotRequest");
                                String sentRequest = jsonObject.getString("sentRequest");

                                listModelSearchResult.add(modelSearchResult);
                            }

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                progressBar.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();


                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }


    }




    private void transferMoney(){

      //  String device_token=sp.getString("device_token", "");
        //   progressDialog.show();

        String url = "https://icosom.com/wallet/main/androidProcess.php?action=addTransferMoney";
        String key_a = "friendId";

        String key_b = "friendEmail";
        //  value_b = "";
        String key_c = "userId";
        user_id = sp.getString("userId", "1");
        String key_d = "userEmail";
        //  String value_d = "";
        String key_e = "transfer_Amount";
        //  String value_e = etAmountSendMoney.getText().toString().trim();
        String key_f = "userFirstName";
        friend_name = sp.getString("firstName", "1")+" "+sp.getString("lastName", "1");


        RequestBody body=null;


        Log.e("friend_id","k"+friend_id);
        Log.e("friend_email","k"+friend_email);
        Log.e("user_id","k"+user_id);
        Log.e("user_email","k"+user_email);
        Log.e("amount","k"+amount_to_be_send);
        Log.e("friend_name","k"+friend_name);
        Log.e("device_token",""+user_device_token);

        body = RequestBuilder.sevenParams("device_token",user_device_token,key_a, friend_id, key_b, friend_email, key_c, user_id,key_d,user_email, key_e, amount_to_be_send, key_f, friend_name);

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
                                    Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                   // startActivity(new Intent(SendMoney.this, Pay_Friend_Activity.class));
                                    //finish();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //progressDialog.dismiss();
                                    Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
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




    public  void  bui(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendMoney.this);

        // Setting Dialog Title
        alertDialog.setTitle("Money Transfer");

        // Setting Dialog Message
        alertDialog.setMessage("Press ok to continue to transfer Rs "+etAmountSendMoney.getText().toString().trim()+" to "+value_f);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                transferMoney();
               // transfer_Money_And_Voucher();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }









//check voucher is valid or not
    private void transfer_Money_And_Voucher(final String voucher){
        String url = "https://icosom.com/superAdmin/voucher_api.php";
        String key_a = "user_id";
        value_a=sp.getString("userId", "1");
        String key_b = "voucher";
        String key_c = "merchant_id";
        value_b=voucher;
        //value_c=friend_id;
        value_c=value_a;





        Log.e("Kiaf_user_id","kas"+value_a);
        Log.e("kaif_voucher","kas"+value_b);
        Log.e("merchant_id","kas"+value_b);
        Log.e("value_c","kas"+value_c);


        RequestBody body;

        body = RequestBuilder.voucher_six_params(key_a,value_a,key_b,value_b,key_c,value_c);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    System.out.println("Send Money " + id);
                    try {
                        final JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");

                        if (status.equalsIgnoreCase("1"))
                        {

                            final JSONArray jsonArray=jsonObject.getJSONArray("message");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    for(int i=0;i<jsonArray.length();i++)
                                    {

                                        try {


                                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                            String voucher_id=jsonObject1.getString("id");

                                            String voucher_code=jsonObject1.getString("voucher_code");
                                             voucher_discount=jsonObject1.getString("amount");
                                            Log.e("final_voucher","skdj"+voucher_discount);
                                            Log.e("final_amount","skdj"+edit_amount.getText().toString());






                                           // Toast.makeText(SendMoney.this, "Voucher is Valid", Toast.LENGTH_SHORT).show();

                                            voucher_response.setText("Voucher is Valid");
                                            voucher_response.setTextColor(Color.GREEN);
                                            voucher_response.setVisibility(View.VISIBLE);

                                        }
                                        catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }


                                    }



                                }
                            });

                        }
                        else if(status.equalsIgnoreCase("0")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message="";
                                    try {
                                        message = jsonObject.getString("message");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    voucher_discount="";
                                    voucher_response.setText(message);
                                    voucher_response.setTextColor(Color.RED);
                                    voucher_response.setVisibility(View.VISIBLE);
                                   // Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        else if(status.equalsIgnoreCase("2")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message="";
                                    try {
                                        message = jsonObject.getString("message");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    voucher_discount="";
                                    //Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                    voucher_response.setText(message);
                                    voucher_response.setTextColor(Color.RED);
                                    voucher_response.setVisibility(View.VISIBLE);

                                }
                            });
                        }

                        else if(status.equalsIgnoreCase("3")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message="";
                                    try {
                                        message = jsonObject.getString("message");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    voucher_discount="";
                                   // Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                    voucher_response.setText(message);
                                    voucher_response.setTextColor(Color.RED);
                                    voucher_response.setVisibility(View.VISIBLE);

                                }
                            });
                        }







                    } catch (JSONException e) {
                       // progressDialog.dismiss();
                        Toast.makeText(SendMoney.this, "Voucher Not Valid", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(SendMoney.this, DashboardRecharge.class));
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(SendMoney.this, MainActivity.class));
    }


/*

    private void transferMoney(){



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
        Log.e("user_name","k"+friend_name);


        if(amount!=null)
            body = RequestBuilder.sixParameter(key_a, friend_id, key_b, friend_email, key_c, user_id,key_d,user_email, key_e, amount, key_f, friend_name);

        if(etAmountSendMoney.getText().toString()!=null)
            body = RequestBuilder.sixParameter(key_a, friend_id, key_b, friend_email, key_c, user_id,key_d,user_email, key_e, etAmountSendMoney.getText().toString(), key_f, friend_name);

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
                                    Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(SendMoney.this, PassbookRecharge.class));
                                    //finish();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // progressDialog.dismiss();
                                    Toast.makeText(SendMoney.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/


}
