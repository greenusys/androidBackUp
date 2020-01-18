package com.icosom.social.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Confirm;
import com.icosom.social.Interface.GetLastIdCallback;

import com.icosom.social.R;
import com.icosom.social.activity.PassbookRecharge;
import com.icosom.social.activity.PlanListRecharge;
import com.icosom.social.utility.AppController;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class RechargeFragment extends Fragment {

    private AppController appController;
    ArrayList<String> arrayListOperatorName;
    ArrayList<String> arrayListOperatorCode;
    ArrayList<String> arrayListtype;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> typeAdapter;
    TextView changeOperator;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    RecyclerView rv_home;
    RecyclerView.LayoutManager layoutManager;
    HomeDashRecyclerAdapter recyclerAdapter;
    TextView planfinders;
    EditText etPhoneNumber;
    RadioGroup rgConnection;
    RadioButton rbPrepaid;
    RadioButton rbPostpaid;
    Spinner spinnerOperator, spinnerType;
    EditText etAmount;
    TextView op;
    TextView tvSubmitRecharge;
    private static int currentPge = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.rech, R.drawable.electr, R.drawable.dth, R.drawable.gas};
    int manual = 1;
    private static ViewPager carouselVp;

    private static CirclePageIndicator circleIndicator;

    private static final ArrayList<Integer> imagesList = new ArrayList<>();
    String phone_number;
    String val;
    String connection_type;
    public String operator = null;
    String rtype;
    String amount, pho, c_id;
    String userId, opst;
    TextView tvViewBalanceRecharge;
    ProgressDialog progressDialog;
    long delay = 100; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {

                String stringEtValue = etPhoneNumber.getText().toString().trim();
                if (stringEtValue.length() >= 4) {
                    String str = stringEtValue;
                    pho = str.substring(0, 4);
                    Log.e("sss", "lastId: " + pho);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            manual = 1;
                            spinnerOperator.setVisibility(View.GONE);
                            op.setVisibility(View.VISIBLE);
                            op.setText(recharges(pho));

                        }
                    });


                }
            }
        }
    };

    public RechargeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);

        appController = (AppController) getContext().getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        userId = sp.getString("userId", "");


        //getBalance();
        planfinders = view.findViewById(R.id.planFinder);
        planfinders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSteps();
            }
        });

        arrayListOperatorName = new ArrayList<>();
        arrayListOperatorCode = new ArrayList<>();
        arrayListtype = new ArrayList<>();
        op = view.findViewById(R.id.spinnerOperators);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                last_text_edit = System.currentTimeMillis();
                handler.postDelayed(input_finish_checker, delay);


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {

                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);

                } else {

                }
            }
        });
        rgConnection = view.findViewById(R.id.rgConnection);
        rbPrepaid = view.findViewById(R.id.rbPrepaid);
        rbPrepaid.setChecked(true);


        rbPrepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOperators("Mobile");
                // Toast.makeText(getActivity(), "Prepaid", Toast.LENGTH_SHORT).show();
            }
        });
        rbPostpaid = view.findViewById(R.id.rbPostpaid);
        rbPostpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOperators("Postpaid");
                // Toast.makeText(getActivity(), "Postpaid", Toast.LENGTH_SHORT).show();
            }
        });


        spinnerOperator = view.findViewById(R.id.spinnerOperator);
        dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.myspinner, arrayListOperatorName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperator.setAdapter(dataAdapter);
        spinnerOperator.setOnItemSelectedListener(new HomeSpinnerAdapter());


        spinnerType = view.findViewById(R.id.spinnerType);
        typeAdapter = new ArrayAdapter<String>(getContext(), R.layout.myspinner, arrayListtype);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setOnItemSelectedListener(new TypeSpinnerAdapter());

        etAmount = view.findViewById(R.id.etAmount);
        changeOperator = view.findViewById(R.id.changeOperator);
        changeOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOperators("Mobile");
                op.setVisibility(View.GONE);
                spinnerOperator.setVisibility(View.VISIBLE);

                // changeOperator.setText("AUTO");

            }
        });
        tvSubmitRecharge = view.findViewById(R.id.tvSubmitRecharge);

        tvSubmitRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextStep();

            }
        });

        tvViewBalanceRecharge = view.findViewById(R.id.tvViewBalanceRecharge);
        tvViewBalanceRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PassbookRecharge.class));
            }
        });

        //  init(view);

        getTypes();
        String[] poscode = new String[]{"31", "40", "33", "32", "35", "36", "37", "38", "30", "41", "34", "39"};
        // Inflate the layout for this fragment
        return view;

    }

    private void nextSteps() {
        int selectedId = 0;
        phone_number = etPhoneNumber.getText().toString().trim();
        selectedId = rgConnection.getCheckedRadioButtonId();
        if (selectedId == R.id.rbPostpaid) {
            connection_type = rbPostpaid.getText().toString();

        } else if (selectedId == R.id.rbPrepaid) {
            connection_type = rbPrepaid.getText().toString();
        }
        amount = etAmount.getText().toString().trim();

        System.out.println("Recharge phone number" + phone_number);
        System.out.println("Recharge connection_type" + connection_type);
        System.out.println("Recharge operator" + operator);
        System.out.println("Recharge amount" + amount);

        if (phone_number.equalsIgnoreCase("")) {
            //  progressDialog.dismiss();
            Toast.makeText(getActivity(), "Enter phone number", Toast.LENGTH_SHORT).show();
        } else if ((phone_number.length()) < 10) {
            //  progressDialog.dismiss();
            Toast.makeText(getActivity(), "Enter 10 digit phone number", Toast.LENGTH_SHORT).show();
        } else {
            if (amount.equalsIgnoreCase("")) {
                //  progressDialog.dismiss();
                if (manual == 1) {
                    getOperator("Mobile");
                } else {
                    Confirm cdds = new Confirm(getActivity(), amount, phone_number, operator);
                    cdds.show();
                }
            } else {
                if (manual == 1) {
                    getOperator("Mobile");
                } else {
                    Confirm cdds = new Confirm(getActivity(), amount, phone_number, operator);
                    cdds.show();
                }
            }
        }
    }

    private void nextStep() {
        int selectedId = 0;
        phone_number = etPhoneNumber.getText().toString().trim();
        selectedId = rgConnection.getCheckedRadioButtonId();
        if (selectedId == R.id.rbPostpaid) {
            connection_type = rbPostpaid.getText().toString();

        } else if (selectedId == R.id.rbPrepaid) {
            connection_type = rbPrepaid.getText().toString();
        }
        amount = etAmount.getText().toString().trim();

        System.out.println("Recharge phone number" + phone_number);
        System.out.println("Recharge connection_type" + connection_type);
        System.out.println("Recharge operator" + operator);
        System.out.println("Recharge amount" + amount);

        if (phone_number.equalsIgnoreCase("")) {
            //  progressDialog.dismiss();
            Toast.makeText(getActivity(), "Enter phone number", Toast.LENGTH_SHORT).show();
        } else if ((phone_number.length()) < 10) {
            //  progressDialog.dismiss();
            Toast.makeText(getActivity(), "Enter 10 digit phone number", Toast.LENGTH_SHORT).show();
        } else {
            if (manual == 1) {
                getOperatorss("Mobile");
            } else {
                Confirm cdds = new Confirm(getActivity(), amount, phone_number, operator);
                cdds.show();
            }
        }

    }

    private void getTypes() {


        String[] ptype = new String[]{"TUP", "2G", "3G", "FTT", "LSC", "OTR", "RMG", "SMS"};


        arrayListtype.clear();
        for (int i = 0; i < ptype.length; i++) {

            arrayListtype.add(ptype[i]);

        }

        typeAdapter.notifyDataSetChanged();
        spinnerType.setVisibility(View.VISIBLE);


    }

    private void getOperator(final String type) {


        String[] pre = new String[]{"Airtel", "Aircel", "Idea", "BSNL", "Reliance CDMA", "Reliance GSM", "Tata Docomo", "Tata Indicom", "Vodafone", "MTS", "Uninor", "Loop Mobile", "Videocon", "MTNL DL", "MTNL Mumbai", "Tata Walky", "Reliance Jio"};
        String[] precode = new String[]{"1", "2", "3", "4", "6", "22", "7", "9", "10", "11", "12", "14", "15", "17", "19", "21", "93"};

        String[] posts = new String[]{"Airtel Postpaid", "Aircel Postpaid", "Idea Postpaid", "BSNL Postpaid", "Reliance CDMA Postpaid", "Reliance GSM Postpaid", "Tata Docomo Postpaid", "Tata Indicom Postpaid", "Vodafone Postpaid", "MTS Postpaid", "Loop Mobile Postpaid", "Tata Walky Postpaid"};
        String[] poscode = new String[]{"31", "40", "33", "32", "35", "36", "37", "38", "30", "41", "34", "39"};

        if (type.equalsIgnoreCase("Mobile")) {

            for (int i = 0; i < pre.length; i++) {
                String chec = pre[i];
                Log.e("sss", opst + "getOperator: " + chec);
                if (opst.contains(chec)) {
                    Log.e("sss", opst + " " + precode[i]);
                    operator = precode[i];


                }


            }

            recharge();
        }
        if (type.equalsIgnoreCase("Postpaid")) {
            for (int i = 0; i < posts.length; i++) {
                String chec = posts[i];
                Log.e("sss", opst + "getOperator: " + chec);
                if (opst.contains(chec)) {
                    Log.e("sss", opst + " " + poscode[i]);
                    operator = poscode[i];

                }


            }


        }
        recharge();

        dataAdapter.notifyDataSetChanged();
        spinnerOperator.setVisibility(View.GONE);


    }

    private void getOperatorss(final String type) {


        String[] pre = new String[]{"Airtel", "Aircel", "Idea", "BSNL", "Reliance CDMA", "Reliance GSM", "Tata Docomo", "Tata Indicom", "Vodafone", "MTS", "Uninor", "Loop Mobile", "Videocon", "MTNL DL", "MTNL Mumbai", "Tata Walky", "Reliance Jio"};
        String[] precode = new String[]{"1", "2", "3", "4", "6", "22", "7", "9", "10", "11", "12", "14", "15", "17", "19", "21", "93"};

        String[] posts = new String[]{"Airtel Postpaid", "Aircel Postpaid", "Idea Postpaid", "BSNL Postpaid", "Reliance CDMA Postpaid", "Reliance GSM Postpaid", "Tata Docomo Postpaid", "Tata Indicom Postpaid", "Vodafone Postpaid", "MTS Postpaid", "Loop Mobile Postpaid", "Tata Walky Postpaid"};
        String[] poscode = new String[]{"31", "40", "33", "32", "35", "36", "37", "38", "30", "41", "34", "39"};

        if (type.equalsIgnoreCase("Mobile")) {

            for (int i = 0; i < pre.length; i++) {
                String chec = pre[i];
                Log.e("sss", opst + "getOperator: " + chec);
                if (opst.contains(chec)) {
                    Log.e("sss", opst + " " + precode[i]);
                    operator = precode[i];


                }


            }

            Confirm cdds = new Confirm(getActivity(), amount, phone_number, operator);
            cdds.show();
        }
        if (type.equalsIgnoreCase("Postpaid")) {
            for (int i = 0; i < posts.length; i++) {
                String chec = posts[i];
                Log.e("sss", opst + "getOperator: " + chec);
                if (opst.contains(chec)) {
                    Log.e("sss", opst + " " + poscode[i]);
                    operator = poscode[i];

                }


            }

            Confirm cdds = new Confirm(getActivity(), amount, phone_number, operator);
            cdds.show();
        }


        dataAdapter.notifyDataSetChanged();
        spinnerOperator.setVisibility(View.GONE);


    }

    private void getOperators(final String type) {


        String[] pre = new String[]{"Airtel", "Aircel", "Idea", "BSNL", "Reliance CDMA", "Reliance GSM", "Tata Docomo", "Tata Indicom", "Vodafone", "MTS", "Uninor", "Loop Mobile", "Videocon", "MTNL DL", "MTNL Mumbai", "Tata Walky", "Reliance Jio"};
        String[] precode = new String[]{"1", "2", "3", "4", "6", "22", "7", "9", "10", "11", "12", "14", "15", "17", "19", "21", "93"};

        String[] posts = new String[]{"Airtel Postpaid", "Aircel Postpaid", "Idea Postpaid", "BSNL Postpaid", "Reliance CDMA Postpaid", "Reliance GSM Postpaid", "Tata Docomo Postpaid", "Tata Indicom Postpaid", "Vodafone Postpaid", "MTS Postpaid", "Loop Mobile Postpaid", "Tata Walky Postpaid"};
        String[] poscode = new String[]{"31", "40", "33", "32", "35", "36", "37", "38", "30", "41", "34", "39"};

        if (type.equalsIgnoreCase("Mobile")) {

            arrayListOperatorName.clear();
            arrayListOperatorCode.clear();
            for (int i = 0; i < pre.length; i++) {

                arrayListOperatorName.add(pre[i]);
                arrayListOperatorCode.add(precode[i]);

            }


        }
        if (type.equalsIgnoreCase("Postpaid")) {
            arrayListOperatorName.clear();
            arrayListOperatorCode.clear();
            for (int i = 0; i < posts.length; i++) {

                arrayListOperatorName.add(posts[i]);
                arrayListOperatorCode.add(poscode[i]);

            }


        }


        dataAdapter.notifyDataSetChanged();


    }

    private String recharges(String PHO) {
        String url = "https://api.rechapi.com/mob_details.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&mobile=" + PHO;

        try {
            appController.GetTest(url, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {

                    Log.e("sss", "lastId: " + id);

                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        c_id = jsonObject1.getString("circleId");
                        if (c_id.equalsIgnoreCase("null")) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    manual = 2;
                                    spinnerOperator.setVisibility(View.VISIBLE);
                                    op.setVisibility(View.GONE);
                                    getOperators("Mobile");

                                }
                            });


                        } else {
                            manual = 1;
                            Log.e("sss", "lastId: " + c_id);
                            opst = jsonObject1.getString("service");
                            Log.e("sss", "lastId: " + opst);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return opst;

    }


    private void recharge() {
        Log.e("op", "recharge: " + operator);
        if (operator != null) {
            //  startActivity(new Intent(getContext(), ListOfPlan.class).putExtra("cirid", c_id).putExtra("opid", operator).putExtra("type", rtype).putExtra("num", phone_number));
            startActivity(new Intent(getContext(), PlanListRecharge.class).putExtra("cirid", c_id).putExtra("opid", operator).putExtra("type", rtype).putExtra("num", phone_number));

        }

    }


    class HomeSpinnerAdapter implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            operator = arrayListOperatorCode.get(pos);

            //  Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + operator, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            operator = arrayListOperatorCode.get(0);
            // TODO Auto-generated method stub
        }

    }

    class TypeSpinnerAdapter implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            rtype = arrayListtype.get(pos);

            // Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + rtype, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            rtype = arrayListtype.get(0);
            // TODO Auto-generated method stub
        }

    }


}
