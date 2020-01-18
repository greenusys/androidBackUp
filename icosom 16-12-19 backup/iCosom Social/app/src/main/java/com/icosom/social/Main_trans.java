package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class Main_trans extends Fragment {
    CommonFunctions urlHelper;
    AppController appController;
    TextInputEditText NUMBER;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String phone, phones;
    TextView sub;

    ProgressBar progressBar;
    private String url;

    public Main_trans() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_trans, container, false);



        appController = (AppController) getContext().getApplicationContext();
        urlHelper = new CommonFunctions();
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edt = sp.edit();
        progressBar = v.findViewById(R.id.transfer1);
        //user_id = sp.getString("userId", "");
        phone = sp.getString("phone", "");
        Log.e("ggg", "onCreateView: "+phone );
        NUMBER = v.findViewById(R.id.number111);
        sub = v.findViewById(R.id.SUBMIT111);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = NUMBER.getText().toString();




                    if(phone.equals("") )
                    {
                        NUMBER.setError("Please Enter Mobile Number");
                        return;
                    }

                else if(phone.length()<10 && phone.length()>10 )
                {
                    NUMBER.setError("Please Enter Valid Mobile Number");
                    return;
                }



                else {


                        url = urlHelper.FETCH_FEEDSs+phone;
                        setData();
                    }
                //loadData();
            }
        });


        //setData();
        return v;

    }


    private void setData() {
        transfer();
        //new Main_trans.SignIn1().execute();
        progressBar.setVisibility(View.VISIBLE);

    }





   /* private class SignIn1 extends AsyncTask<String, Void, String> {
        private String response;
        private RequestBody body;


        @Override
        protected String doInBackground(String... strings) {


            Log.e("add_kaif_url", "onCreateView: "+url );

            body = RequestBuilder.NoParameter();

            try {
                response = appController.POST(url,body);
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
                    Log.e("talent_check", "onPostExecute: " + response);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    String error_code = jsonObject1.getString("error_code");
                    if (error_code.equalsIgnoreCase("200")) {
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getActivity(), Talent_List.class).putExtra("response", response).putExtra("customerMobile", phone));
                    }
                   else if (error_code.equalsIgnoreCase("123")) {
                        startActivity(new Intent(getActivity(), CustomerRegistration.class));

                    }
                    else {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failure : "+jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/


    private void transfer() {

        Log.e("add_kaif_url", "onCreateView: "+url );

        Request request = new Request.Builder().
                url(url).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Something is Went Wrong! Try Again ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                try {

                    final JSONObject ja = new JSONObject(myResponse);

                    Log.e("transfer_response", ": " + ja);

                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                JSONObject jsonObject1 = ja.getJSONObject("data");

                                String error_code = jsonObject1.getString("error_code");
                                if (error_code.equalsIgnoreCase("200")) {
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(getActivity(), Talent_List.class).putExtra("response", myResponse).putExtra("customerMobile", phone));
                                }
                                else if (error_code.equalsIgnoreCase("123")) {
                                    startActivity(new Intent(getActivity(), CustomerRegistration.class));

                                }
                                else {

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Failure : "+jsonObject1.getString("resText"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }













}
