package com.icosom.social.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.DthRecharge;
import com.icosom.social.Electricity;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.QRcode;
import com.icosom.social.R;
import com.icosom.social.ScannedBarcode;
import com.icosom.social.Talent_Show_Package.Modal.Premium_Response;
import com.icosom.social.Talent_Show_Package.View_Model.Pay_4_Premimum_VM;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.RequestBuilder;
import com.icosom.social.Pay_Friend_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardRecharge extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String userId;
    TextView bal, idd, qrcod;

    LinearLayout tranfer, pass, rech, pay, wallet, datacard, dth, elec, water,Loan,talent_premium;
    ImageView addmoney;
    private String balance;
    private AppController appController;
    public String user_device_token,friend_id,friend_email,user_id,user_email,amount,user_name;
    ProgressBar progressBar;
    Pay_4_Premimum_VM Viewmodel;
    private boolean premiumm_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_recharge);
        appController = (AppController) getApplicationContext();

        progressBar = findViewById(R.id.progress_bar);
        Viewmodel = ViewModelProviders.of(this).get(Pay_4_Premimum_VM.class);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        appController = (AppController) getBaseContext().getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (getIntent().getStringExtra("friend_id") != null)
            friend_id = getIntent().getStringExtra("friend_id");

        if (getIntent().getStringExtra("friend_email") != null)
            friend_email = getIntent().getStringExtra("friend_email");

        if (getIntent().getStringExtra("user_id") != null)
            user_id = getIntent().getStringExtra("user_id");

        if (getIntent().getStringExtra("user_email") != null)
            user_email = getIntent().getStringExtra("user_email");

        if (getIntent().getStringExtra("amount") != null)
            amount = getIntent().getStringExtra("amount");

        if (getIntent().getStringExtra("user_name") != null)
            user_name = getIntent().getStringExtra("user_name");

        if (getIntent().getStringExtra("user_device_token") != null)
            user_device_token = getIntent().getStringExtra("user_device_token");



        if(getIntent().getStringExtra("user_device_token") != null
            &&
        getIntent().getStringExtra("friend_id") != null
                &&
                getIntent().getStringExtra("friend_email") != null
                &&
                getIntent().getStringExtra("user_id") != null
                &&
                getIntent().getStringExtra("user_email") != null
                &&
                getIntent().getStringExtra("amount") != null
                &&
                getIntent().getStringExtra("user_name") != null )
        {
           transferMoney();



            Log.e("friend_id","k"+friend_id);
            Log.e("friend_email","k"+friend_email);
            Log.e("friend_name","k"+user_name);


            Log.e("user_id","k"+user_id);
            Log.e("user_email","k"+user_email);
            Log.e("amount","k"+amount);

        }

        userId = sp.getString("userId", "");
        water = findViewById(R.id.water_option);
        talent_premium = findViewById(R.id.talent_premium);


        talent_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talent_premium();
            }
        });

         pay = findViewById(R.id.pay_option);
        wallet = findViewById(R.id.wallet_option);
        datacard = findViewById(R.id.data_card_option);
        dth = findViewById(R.id.dth_option);
        elec = findViewById(R.id.elec_optiopn);
        Loan = findViewById(R.id.Loan);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kycs = sp.getString("kyc", "0");


               /* if (kycs.equalsIgnoreCase("0"))
                {
                    bui();
                }
                else*/
                startActivity(new Intent(DashboardRecharge.this, Pay_Friend_Activity.class).putExtra("userid",userId).putExtra("balance",balance));
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashboardRecharge.this, ScannedBarcode.class).putExtra("act","rech"));
            }
        });
        datacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("data card recharge");
            }
        });
        dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, DthRecharge.class));
            }
        });
        elec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, Electricity.class));
            }
        });
        /*water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("water bill");
            }
        });*/
        Loan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(DashboardRecharge.this,LoanActivity.class));
                    }
                }
        );
        tranfer = findViewById(R.id.transfer_option);
        pass = findViewById(R.id.pass_option);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, PassbookRecharge.class));
            }
        });
        rech = findViewById(R.id.mob_rec_option);
        addmoney = findViewById(R.id.add_money_option);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, QRcode.class).putExtra("id",userId));
            }
        });
        idd = findViewById(R.id.iwId);
        bal = findViewById(R.id.txt_iwBal);
        bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, PassbookRecharge.class));
            }
        });
        idd.setText("Unique Number - " + userId);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String kycs = sp.getString("kyc", "0");

                if (kycs.equalsIgnoreCase("0"))
                {
                    bui();
                }
                else*/
                startActivity(new Intent(DashboardRecharge.this, Dash_fragement.class).putExtra("type", "1"));
            }
        });
        tranfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kycs = sp.getString("kyc", "0");


                /*if (kycs.equalsIgnoreCase("0"))
                {
                    bui();
                }
                else
                    {*/
                    startActivity(new Intent(DashboardRecharge.this, Dash_fragement.class).putExtra("type", "3"));
               // }


            }
        });

        rech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRecharge.this, Dash_fragement.class).putExtra("type", "2"));
            }
        });
        getBalance();


    }



    private void transferMoney()
    {

         //String device_token=sp.getString("device_token", "");

        progressBar.setVisibility(View.VISIBLE);
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
                                    Toast.makeText(DashboardRecharge.this, message, Toast.LENGTH_SHORT).show();
                                    getBalance();

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
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(DashboardRecharge.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    } catch (JSONException e) {
                        // progressDialog.dismiss();
                        progressBar.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.INVISIBLE);
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

                    DashboardRecharge.this.runOnUiThread(new Runnable() {
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






    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(DashboardRecharge.this, DashboardRecharge.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DashboardRecharge.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();

    }

    private void getBalance() {

progressBar.setVisibility(View.VISIBLE);
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String url = "https://icosom.com/wallet/main/androidProcess.php?action=checkWalletBalance";
        //String url = "https://icosom.com/wallet/main/transapi2.php?action=checkWalletBalance";

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
                                Log.e("Kaif_Bal","skd"+message);
                                bal.setText(" ₹ " + message);
                                balance=message;
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } catch (IOException e) {
            progressBar.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }

    }


    private void msg(String mg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Coming soon !")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       /* Intent i = new Intent(DashboardRecharge.this, DashboardRecharge.class);
                        startActivity(i);*/
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(mg);
        alert.show();

    }

    public void bui() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DashboardRecharge.this);

        // Setting Dialog Title
        alertDialog.setTitle("Kyc Verfication");

        // Setting Dialog Message
        alertDialog.setMessage("Be ready with addhar Card to scan for kyc verification");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               // startActivity(new Intent(DashboardRecharge.this, ScannedBarcode.class).putExtra("act", "kyc"));
              //  startActivity(new Intent(DashboardRecharge.this, KYC_Registration.class));
               // startActivity(new Intent(DashboardRecharge.this, VerifyKyc.class).putExtra("userId",user_id));

                dialog.cancel();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(new Intent(DashboardRecharge.this, DashboardRecharge.class));

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    public void center_icon(View view) {

        startActivity(new Intent(DashboardRecharge.this, MainActivity.class));
    }

    public void talent_premium() {

        final int curr_bal=Integer.parseInt(balance);

        Viewmodel.check_Premium_USer(MainActivity.user_id).observe(DashboardRecharge.this, new Observer<Premium_Response>() {
            @Override
            public void onChanged(@Nullable Premium_Response s) {


                System.out.println("result_of_check_premium"+s.getCode()+" "+s.getStatus());

                System.out.println("sayed"+s.getCode().equals("1"));

                if(s.getCode().equals("1"))
                {
                    System.out.println("called");

                    show_Alert_for_Premium(false,"You are already Icosom Premium Talent user");
                }
                else {

                    if (curr_bal >= 50) {
                        //can pay for premium talent user
                        System.out.println("eligible_for_premium");
                        //show alert box then pay for premium talent

                        show_Alert_for_Premium(true, "To become Icosom Premium Talent User, You have to pay minimum 50 Rs.");

                        //cal api send amount and user id


                    } else {
                        System.out.println("needs to add balance in their account");

                        show_Alert_for_Premium(false, "Low Balance! First you need to add more than 50 amount in your Icosom wallet ");

                    }
                }


            }
        });



    }

    private void show_Alert_for_Premium(boolean pay,String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardRecharge.this);
        builder.setTitle("Icosom Premium Talent");
        builder.setMessage(msg);

        if (pay) {
            String positiveText = "Pay";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Viewmodel.make_user_premium(MainActivity.user_id, "50").observe(DashboardRecharge.this, new Observer<Premium_Response>() {
                                @Override
                                public void onChanged(@Nullable Premium_Response s) {

                                    getBalance();
                                    System.out.println("result_of_premium"+s.getCode()+" "+s.getStatus());
                                    show_Alert_for_Premium(false,"Now you can access Icosom Premium Talent");


                                }
                            });

                            dialog.dismiss();
                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

        }
        else
        {
            String negativeText = getString(android.R.string.ok);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }




}
