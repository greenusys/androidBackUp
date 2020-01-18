package com.icosom.social.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class NameSetUpFragment extends Fragment {
    EditText edt_firstName;
    EditText edt_lastName;
    EditText edt_email_phone;
    CommonFunctions urlHelper;
    private AppController appController;
    String mobile_OTP, type, first, last, email;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_name_set_up, container, false);
        progressBar = v.findViewById(R.id.pb_login_1);
        progressBar.setVisibility(View.GONE);

        edt_firstName = v.findViewById(R.id.edt_firstName);
        edt_lastName = v.findViewById(R.id.edt_lastName);
        edt_email_phone = v.findViewById(R.id.edt_email_phone);
        urlHelper = new CommonFunctions();
        appController = (AppController) getActivity().getApplicationContext();
        ((TextView) v.findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                first = edt_firstName.getText().toString();
                last = edt_lastName.getText().toString();
                email = edt_email_phone.getText().toString();

                if (edt_firstName.getText().toString().isEmpty()) {
                    edt_firstName.setError("Please Enter First Name");
                    return;
                }

                if (edt_lastName.getText().toString().isEmpty()) {
                    edt_lastName.setError("Please Enter Last Name");
                    return;
                }
                if (edt_email_phone.getText().toString().isEmpty()) {
                    edt_email_phone.setError("Please Enter Email/Phone");
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.e("dddd", "onClick: " + first + last + email);
                    //  new NameSetUpFragment.SignIn1().execute(edt_firstName.getText().toString(), edt_lastName.getText().toString(), edt_email_phone.getText().toString());
                    new NameSetUpFragment.SignIn1().execute(first, last, email);
                }
                /*Fragment fragment = new tasks();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                startActivity(new Intent(getContext(), EnterOTPFragment.class));*/
               /* EnterOTPFragment birthDateFragment = new EnterOTPFragment();
                //   Bundle bundle = new Bundle();
                // bundle.putString("firstName", edt_firstName.getText().toString().trim());
                ////  bundle.putString("lastName", edt_lastName.getText().toString().trim());
                //    bundle.putString("email",  edt_email_phone.getText().toString().trim());
                // birthDateFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().
                        replace(R.id.container, birthDateFragment).
                        addToBackStack(null).
                        commit();*/

            }
        });

        return v;
    }

    private class SignIn1 extends AsyncTask<String, Void, String> {

        private String key_a = "data-source";
        private String value_a = "android";
        private String key_b = "firstName";
        private String value_b = "";
        private String key_c = "lastName";
        private String value_c = "";
        private String key_d = "email_number";
        private String value_d = "";
        private String response;
        private RequestBody body;
        private String url = urlHelper.register_step1;

        @Override
        protected String doInBackground(String... strings) {
            value_b = strings[0];
            value_c = strings[1];
            value_d = strings[2];

            Log.e("back", "doInBackground: " + value_a + value_c + value_d);
            body = RequestBuilder.fourParameter(
                    key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);

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
                    String statuss = rootObject.getString("status");

                    if (statuss.equalsIgnoreCase("1")) {

                        JSONObject jsonObject = rootObject.getJSONObject("data");
                        Log.e("resss", "onPostExecute: " + jsonObject);
                        String email_number_type = jsonObject.getString("email_number_type");
                        if (email_number_type.equalsIgnoreCase("email")) {
                            type = "email_OTP";
                            mobile_OTP = jsonObject.getString("email_OTP");
                        }
                        if (email_number_type.equalsIgnoreCase("mobile")) {
                            type = "mobile_OTP";
                            mobile_OTP = jsonObject.getString("mobile_OTP");
                        }


                        EnterOTPFragment birthDateFragments = new EnterOTPFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("firstName", value_b);
                        bundle.putString("lastName", value_c);
                        bundle.putString("email_number", value_d);
                        bundle.putString("email_number_type", email_number_type);
                        bundle.putString("tpye", type);
                        bundle.putString("mobile_OTP", mobile_OTP);
                        birthDateFragments.setArguments(bundle);
                        getFragmentManager().beginTransaction().
                                        replace(R.id.container, birthDateFragments).
                                addToBackStack(null).
                                commit();
                    } else {
                        Toast.makeText(getActivity(), "" + rootObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}