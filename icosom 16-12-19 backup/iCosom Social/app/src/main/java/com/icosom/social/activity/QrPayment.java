package com.icosom.social.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.Pay_Comfirmation_Activity;
import com.icosom.social.R;
import com.icosom.social.RequestBuilder;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

public class QrPayment extends AppCompatActivity {
    AppController appController;
    String qr_code_data,user_name,friend_id,user_email;
    ImageView cover;
    CircleImageView profile;
    TextView name,voucher,frnd_id,userid;
    EditText amount;
    Button proceed;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    String selfId;
    private String names;
    public int discount;
    public String value_a = "";
    public String value_b = "";
    public String value_f = "";
    public String value_c = "";
    TextView voucher_response,final_pay ;
    String voucher_discount="",final_amount="",amount_to_be_send="";
String user2[];
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(QrPayment.this, DashboardRecharge.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_payment);
        appController = (AppController) getApplicationContext();

        if(getIntent().getStringExtra("qr_code_data")!=null)
            qr_code_data = getIntent().getStringExtra("qr_code_data");



        //if(getIntent().getStringExtra("friend_id")!=null)
       // friend_id = getIntent().getStringExtra("friend_id");

         voucher_response=(TextView)findViewById(R.id.voucher_response);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        selfId = sp.getString("userId", "1");

        user_email = sp.getString("email", "1");

        cover = findViewById(R.id.qr_cover_image_profile);
        proceed = findViewById(R.id.qr_proceed);
        profile = findViewById(R.id.qr_profile_image_profile);

        final String final_username[]=qr_code_data.split(",");
        user2=final_username;

        name = findViewById(R.id.qr_name_user);//qr code user name
        frnd_id = findViewById(R.id.frnd_id);
        userid = findViewById(R.id.user_id);

        Log.e("ussss_Name","sdkf"+user_name);

        /*friend_id+","+frnd_name+","+frnd_email+","+frnd_phone*/

        name.setText(final_username[1]);
        frnd_id.setText(final_username[0]);//qr code frnd id
        userid.setText(selfId);


        amount = findViewById(R.id.qr_amount);
        voucher = findViewById(R.id.voucher);
       // new QrPayment.SignIn1().execute();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amount.getText().toString();
                if(final_username[0].equalsIgnoreCase(selfId)){
                    buiError();
                }
                else
                    {

                if (amount.getText().toString().equalsIgnoreCase("")) {
                    amount.setError("Enter amount");
                    return;
                }
                else {

                    if(voucher_discount.equals(""))
                    {
                        amount_to_be_send = amount.getText().toString();

                        Intent intent = new Intent(QrPayment.this, Pay_Comfirmation_Activity.class);
                        intent.putExtra("user_id", selfId);
                        intent.putExtra("user_email", user_email);

                        intent.putExtra("friend_id", final_username[0]);//qr code values
                        intent.putExtra("friend_name", final_username[1]);
                        intent.putExtra("friend_email", final_username[2]);
                        intent.putExtra("friend_mobile", final_username[3]);
                        intent.putExtra("amount", amount.getText().toString());
                        //intent.putExtra("total_discount","0");
                        //intent.putExtra("amount_to_be_paid",String.valueOf(result2));

                        startActivity(intent);
                    }
                    else {

                        int result = Integer.parseInt(amount.getText().toString())
                                *
                                Integer.parseInt(voucher_discount)
                                /
                                100;//get amount discount

                        int result2=Integer.parseInt(amount.getText().toString())-result;//discount
                        amount_to_be_send=String.valueOf(result2);



                        Log.e("final_amount","ksdjf"+amount.getText().toString());
                        Log.e("voucher_discount","ksdjf"+voucher_discount);
                        Log.e("total-discount","ksdjf"+result);
                        Log.e("result-after-discount","ksdjf"+result2);

                        //transferMoney();
                        Intent intent=new Intent(QrPayment.this,Pay_Comfirmation_Activity.class);
                        intent.putExtra("user_id",selfId);
                        intent.putExtra("user_email",user_email);
                        intent.putExtra("friend_id",final_username[0]);
                        intent.putExtra("friend_name",final_username[1]);
                        intent.putExtra("friend_email",final_username[2]);
                        intent.putExtra("friend_mobile",final_username[3]);
                        intent.putExtra("amount",String.valueOf(result2));//amount after calculating discount
                        intent.putExtra("total_discount",String.valueOf(result));
                        intent.putExtra("amount_to_be_paid",amount.getText().toString());






                        //   intent.putExtra("amount_to_be_paid",String.valueOf(result2));

                        startActivity(intent);

                    }



                    }




                      /*  Intent intent=new Intent(QrPayment.this,DashboardRecharge.class);
                   intent.putExtra("friend_id",final_username[0]);//frnd id
                    intent.putExtra("user_name",final_username[1]);//frien name
                   intent.putExtra("friend_email",final_username[2]);//frind email
                   intent.putExtra("user_id",selfId);//user id
                   intent.putExtra("user_email",user_email);//user email
                   intent.putExtra("amount",amount.getText().toString());
                    startActivity(intent);
*/





            }}
        });

    }

    public void check_voucher_code(View view) {

        EditText voucher_text=(EditText)findViewById(R.id.apply_code);




                if(voucher_text.getText().toString().isEmpty())
                    voucher_text.setError("Enter Voucher Code!");

                else
                    Check_VOucher_code_and_Discount(voucher_text.getText().toString().trim());


    }



    private void Check_VOucher_code_and_Discount(final String voucher){
        String url = "https://icosom.com/superAdmin/voucher_api.php";
        String key_a = "user_id";
        value_a=sp.getString("userId", "1");
        String key_b = "voucher";
        String key_c = "merchant_id";
        value_b=voucher;
        value_c=value_a;
        //value_c=user2[0];//frnd id
        //value_c=user2[0];





        Log.e("Kiaf_user_id","kas"+value_a);
        Log.e("kaif_voucher","kas"+value_b);
        Log.e("merchant_id","kas"+value_c);


        RequestBody body;

        body = com.icosom.social.utility.RequestBuilder.voucher_six_params(key_a,value_a,key_b,value_b,key_c,value_c);

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
                                            voucher_discount=jsonObject1.getString("amount");//storing voucher discount
                                            Log.e("final_voucher","skdj"+voucher_discount);
                                            Log.e("final_amount","skdj"+voucher);











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
                        Toast.makeText(QrPayment.this, "Voucher Not Valid", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    private class SignIn1 extends AsyncTask<String, Void, String> {
        private String response;
        private RequestBody body;
        String key_a = "userId";
        String value_a = user2[0];
        String url = CommonFunctions.QR_LIST_USER;


        @Override
        protected String doInBackground(String... strings) {

            body = RequestBuilder.singleParameter(key_a, value_a);

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

                    JSONObject jsonObject = new JSONObject(s);
                    names = jsonObject.getString("name");
                    String picture = jsonObject.getString("picture");
                    String coverpicture = jsonObject.getString("coverpicture");
                    name.setText(names);
                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + coverpicture).
                            into(cover);
                    Glide.
                            with(getBaseContext()).
                            load(CommonFunctions.FETCH_IMAGES + picture).
                            into(profile);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void bui() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                QrPayment.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Money Transfer");

        // Setting Dialog Message
        alertDialog.setMessage("Press ok to continue to transfer Rs " + amount.getText().toString().trim() + " to " + names);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                transferMoney();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void buiError() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                QrPayment.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Money Transfer");

        // Setting Dialog Message
        alertDialog.setMessage("Cannot Transfer money to self account");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                startActivity(new Intent(QrPayment.this, DashboardRecharge.class));
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void transferMoney() {
        String device_token=sp.getString("device_token", "");

        String url = "http://icosom.com/wallet/main/androidProcess.php?action=addTransferMoney";
        String key_a = "friendId";
//        value_a = "4";
        String key_b = "friendEmail";
        String value_b = "";
        String key_c = "userId";
        String value_c = sp.getString("userId", "1");
        String key_d = "userEmail";
        String value_d = "";
        String key_e = "transfer_Amount";
        String value_e = amount.getText().toString().trim();
        String key_f = "userFirstName";
//        value_f = "prashant";
        RequestBody body;

        body = com.icosom.social.utility.RequestBuilder.sevenParams("device_token",device_token,key_a, user2[0], key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e, key_f, names);

        try {
            appController.PostTest(url, body, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    System.out.println("Send Money " + id);
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        if (status.equalsIgnoreCase("1")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(QrPayment.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(QrPayment.this, PassbookRecharge.class));
                                    finish();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(QrPayment.this, message, Toast.LENGTH_SHORT).show();
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
}
