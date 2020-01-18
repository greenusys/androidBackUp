package com.icosom.social.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.activity.CreatePin;
import com.icosom.social.activity.DashboardRecharge;
import com.icosom.social.activity.EditProfileActivity;
import com.icosom.social.activity.ForgetPin;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class WalletFragment extends Fragment
{
    AppController appController;
    CommonFunctions urlHelper;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String user_id;
    ProgressBar progressBar;
    LinearLayout lin_submit,lin_create;
    TextView reset;
    EditText pin;
    String spin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        appController = (AppController) getActivity().getApplicationContext();
        urlHelper = new CommonFunctions();





        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();


        progressBar = v.findViewById(R.id.pb_login_subs);
        lin_submit = v.findViewById(R.id.button_wallet_submits);
        pin = v.findViewById(R.id.edt_pin_logins);
        pin.requestFocus();
      /*  InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
        reset = v.findViewById(R.id.txt_reset_pins);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),ForgetPin.class));
            }
        });


       final String email2=sp.getString("email", "");
        final   String phone2=sp.getString("phone", "");

        Log.e("email_kaif","ksdj"+email2);
        Log.e("email_edt_phonef","ksdj"+phone2);
        Log.e("userId_kaif","ksdj"+sp.getString("userId",""));

        lin_create = v.findViewById(R.id.createPins);
        lin_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email2.equals("") || email2==null)
                    bui("Please Update Your Email id First");
                    // Toast.makeText(appController, "Please Update Your Email id First", Toast.LENGTH_SHORT).show();

                else if(phone2 == null || phone2.equals(""))
                    bui("Please Update Your Mobile Number  First");
                    //Toast.makeText(appController, "Please Update Your Mobile Number  First", Toast.LENGTH_SHORT).show();
                else
                {
                    startActivity( new Intent(getActivity(),CreatePin.class));;
                }


            }
        });


        user_id = sp.getString("userId","");

        if(user_id!=null || !user_id.equals(""))
            new WalletFragment.check_user_pin().execute(spin, user_id);


        if(email2.equals("") || email2==null)
            bui("Please Update Your Email id First");


       else if(phone2 == null || phone2.equals(""))
            bui("Please Update Your Mobile Number  First");

       else
        {
           // Toast.makeText(appController, "Else", Toast.LENGTH_SHORT).show();
        }


        lin_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spin = pin.getText().toString();

                if (email2.equals("") || email2 == null)
                    bui("Please Update Your Email id First");

                else if (phone2 == null || phone2.equals(""))
                    bui("Please Update Your Mobile Number  First");

              else  if (pin.getText().toString().isEmpty()) {
                    pin.setError("Please Enter New Pin");
                    return;
                }


                else {
                    progressBar.setVisibility(View.VISIBLE);
                    new WalletFragment.SignIn1().execute(spin, user_id);
                }


            }
        });



        return v;
    }





    private void bui(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Icosom Wallet");

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(getContext(),EditProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("from","wallet"));
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {
        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "pin";
        private String value_b = "";
        private String key_d = "userId";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.loginwallet;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_d = strings[1];


            Log.e("back", "doInBackground: " + value_a+ value_d+value_b);
            body = RequestBuilder.threeParameter(
                    key_a, value_a, key_b, value_b, key_d, value_d);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("createPin", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String statuss = rootObject.getString("status");

                    if (statuss.equalsIgnoreCase("1")) {

                        // Toast.makeText(WalletPin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(getActivity(),DashboardRecharge.class));

                    }
                    if (statuss.equalsIgnoreCase("0")) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        //   startActivity( new Intent(WalletPin.this,DashboardRecharge.class));

                    }/*if (statuss.equalsIgnoreCase("0")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    if (statuss.equalsIgnoreCase("2")) {
                        Toast.makeText(CreatePin.this, "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(CreatePin.this,WalletPin.class));
                    }*/
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }


    }



    private class check_user_pin extends AsyncTask<String, Void, String> {//check user pin is set or not
        private String key_a = "user_id";                                // if pin is  found then  create bin button  will be invisible

       // String userid=sp.getString("userId","");



        private String value_a =user_id;

        private String response;
        private RequestBody body;
        private String url = "https://icosom.com/social/main/checkPin.php";

        @Override
        protected String doInBackground(String... strings) {



            Log.e("back", "doInBackground: " + key_a+ value_a);
            body = RequestBuilder.oneParameter(key_a, value_a);

            try {
                response = appController.POST(url, body);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("RESSS", "onPostExecute: " + s);
            if (s != null) {
                try {
                    Log.e("createPin", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String statuss = rootObject.getString("status");



                    if (statuss.equalsIgnoreCase("1")) {

                        lin_create.setVisibility(View.INVISIBLE);
                        reset.setVisibility(View.VISIBLE);
                        Log.e("tru_KAIF","SDFJ"+statuss);
                    }

                    if (statuss.equalsIgnoreCase("0")) {

                        lin_create.setVisibility(View.VISIBLE);
                        reset.setVisibility(View.GONE);

                        Log.e("else_KAIF","SDFJ"+statuss);
                    }



                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }


    }
    /*@Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(WalletPin.this, MainActivity.class));
    }*/}

