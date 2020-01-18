package com.icosom.social.fragment;

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
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;

public class EnterOTPFragment extends Fragment
{
    EditText edt_code;
    CommonFunctions urlHelper;
    private AppController appController;
    LinearLayout btn_finish;
    TextView txt_finish;
    ProgressBar pb_finish;
    Boolean veryfyFlag = false;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String firstName,lastName,email_phone,email_number_type,mobile_OTP,type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_enter_otp, container, false);

        appController = (AppController) getActivity().getApplicationContext();
        urlHelper = new CommonFunctions();

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();

        edt_code = v.findViewById(R.id.edt_code);
        btn_finish = v.findViewById(R.id.btn_finish);
        txt_finish = v.findViewById(R.id.txt_finish);
        pb_finish = v.findViewById(R.id.pb_finish_2);

        pb_finish.setVisibility(View.GONE);
        txt_finish.setVisibility(View.VISIBLE);
        btn_finish.setClickable(true);
        firstName = getArguments().getString("firstName");
        lastName = getArguments().getString("lastName");
        email_phone = getArguments().getString("email_number");
        email_number_type = getArguments().getString("email_number_type");
        type=getArguments().getString("type");
        mobile_OTP = getArguments().getString("mobile_OTP");
        Log.e("reg2", "onCreateView: "+firstName+lastName+email_phone +email_number_type+mobile_OTP);
        btn_finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pb_finish.setVisibility(View.VISIBLE);
                if (edt_code.getText().toString().isEmpty())
                {
                    edt_code.setError("Please Enter OTP");
                    pb_finish.setVisibility(View.GONE);
                    return;
                }

                if (edt_code.getText().toString().equalsIgnoreCase(mobile_OTP))
                {
                    BirthDateFragment birthDateFragment = new BirthDateFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("firstName", firstName);
                    bundle.putString("lastName", lastName);
                    bundle.putString("email_number", email_phone);
                    bundle.putString("email_number_type", email_number_type);
                    bundle.putString("type", type);
                    bundle.putString("mobile_OTP", mobile_OTP);
                    birthDateFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().
                            replace(R.id.container, birthDateFragment).
                            addToBackStack(null).
                            commit();
                    pb_finish.setVisibility(View.GONE);
                    return;
                }
                else {
                    edt_code.setError("OTP is incorrect");
                    pb_finish.setVisibility(View.GONE);
                }

                //  verifyOtp(sp.getString("userId", ""));
            }
        });



        (v.findViewById(R.id.txt_resendOtp)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                resendOtp();
            }
        });

        return v;
    }



    private void resendOtp()
    {
        
        new EnterOTPFragment.SignIn1().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!veryfyFlag)
        {
            edt.putBoolean("verification", false);
            edt.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!veryfyFlag)
        {
            edt.putBoolean("verification", true);
            edt.commit();
        }
    }
    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "firstName";
        private String value_b = firstName;
        private String key_c = "lastName";
        private String value_c = lastName;
        private String key_d = "email_number";
        private String value_d = email_phone;
        private String key_e = "email_number_type";
        private String value_e = email_number_type;
        private String key_f = "mobile_otp";
        private String value_f = mobile_OTP;
        private String key_g = "otp-time-difference";
        private String value_g = "6";
        private String response;
        private RequestBody body;
        private String url = urlHelper.register_step2;

        @Override
        protected String doInBackground(String... strings) {


            Log.e("back", "doInBackground: " + value_a + value_b+value_e+value_c + value_d+value_g+value_f);
            body = RequestBuilder.sevenParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e, key_f, value_f, key_g, value_g);

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
                    Log.e("RESSS", "onPostExecute: " + response);
                    JSONObject rootObject = new JSONObject(response);
                    String status = rootObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "please check your  otp sent again", Toast.LENGTH_SHORT).show();
                          pb_finish.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(getActivity(), ""+rootObject.getString("message"), Toast.LENGTH_SHORT).show();
                           pb_finish.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}