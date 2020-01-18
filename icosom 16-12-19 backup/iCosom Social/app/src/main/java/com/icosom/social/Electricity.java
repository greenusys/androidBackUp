package com.icosom.social;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.activity.ListOfPlan;
import com.icosom.social.utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Electricity extends AppCompatActivity {
    private AppController appController;
    ArrayList<String> arrayListStateName;
    ArrayList<String> arrayListStateCode;
    ArrayList<String> arrayListboard;
    ArrayList<String> arrayListcodeboard;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> boardAdapter;
    SharedPreferences sp;
    Spinner state, board;
    EditText etAmount, etconsumer;
    TextView tvSubmitRecharge;

    String phone_number;
    String connection_type;
    public String operator = null;
    String rtype;
    String amount,consumer, c_id;
    String userId, opst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);
        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userId = sp.getString("userId", "");


        //getBalance();
        arrayListStateName = new ArrayList<>();
        arrayListStateCode = new ArrayList<>();
        arrayListboard = new ArrayList<>();
        arrayListcodeboard = new ArrayList<>();

        state = findViewById(R.id.e_state);
        stateAdapter = new ArrayAdapter<String>(Electricity.this, R.layout.myspinner, arrayListStateName);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
        state.setOnItemSelectedListener(new Electricity.HomeSpinnerAdapter());
        getState();


        board = findViewById(R.id.e_e_board);
        boardAdapter = new ArrayAdapter<String>(Electricity.this, R.layout.myspinner, arrayListboard);
        boardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board.setAdapter(boardAdapter);
        board.setOnItemSelectedListener(new Electricity.BoardSpinnerAdapter());
        getBoard();
        etAmount = findViewById(R.id.e_Amount);
        etconsumer = findViewById(R.id.e_cusomer_no);
        tvSubmitRecharge = findViewById(R.id.e_recharge);

        tvSubmitRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             

                amount = etAmount.getText().toString().trim();
                consumer = etconsumer.getText().toString().trim();
                
                if (amount.equalsIgnoreCase("")) {
                    //  progressDialog.dismiss();
                    Toast.makeText(Electricity.this, "Enter amount", Toast.LENGTH_SHORT).show();
                } else if (consumer.equalsIgnoreCase("")) {
                    //  progressDialog.dismiss();
                    Toast.makeText(Electricity.this, "Enter consumer number", Toast.LENGTH_SHORT).show();
                }  else {
                    checkBalance();
                }

            }
        });


        //  init(view);


    }

    private void checkBalance() {
        Confirm cdd=new Confirm(Electricity.this,amount,consumer,rtype);
        cdd.show();
    }


    private void getState() {


        String[] pre = new String[]{"State", "Bihar & Jharkhand", "Uttar Pradesh (E)", "Uttar Pradesh (W)"};
        String[] precode = new String[]{"0", "17", "9", "11"};


        arrayListStateName.clear();
        arrayListStateCode.clear();
        for (int i = 0; i < pre.length; i++) {

            arrayListStateName.add(pre[i]);
            arrayListStateCode.add(precode[i]);

        }


        stateAdapter.notifyDataSetChanged();


    }

    private void getBoard() {


        String[] boards = new String[]{"Electricity Board", "NBPDCL - BIHAR", "SBPDCL - BIHAR", "UPPCL (RURAL) - UTTAR PRADESH", "UPPCL (URBAN) - UTTAR PRADESH"};
        String[] borcode = new String[]{"0", "155", "158", "143", "165"};


        arrayListboard.clear();
        arrayListcodeboard.clear();
        for (int i = 0; i < boards.length; i++) {

            arrayListboard.add(boards[i]);
            arrayListcodeboard.add(borcode[i]);

        }


        boardAdapter.notifyDataSetChanged();


    }

    private String recharges(String PHO) {
        String url = "http://api.rechapi.com/mob_details.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&mobile=" + PHO;
        try {
            appController.GetTest(url, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {

                    Log.e("sss", "lastId: " + id);

                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        c_id = jsonObject1.getString("circleId");
                        Log.e("sss", "lastId: " + c_id);
                        opst = jsonObject1.getString("service");
                        Log.e("sss", "lastId: " + opst);


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
            startActivity(new Intent(Electricity.this, ListOfPlan.class).putExtra("cirid", c_id).putExtra("opid", operator).putExtra("type", rtype).putExtra("num", phone_number));
        }

    }


    class HomeSpinnerAdapter implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            //operator = arrayListStateCode.get(pos);

            //  Toast.makeText(parent.Electricity.this, "OnItemSelectedListener : " + operator, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // operator = arrayListStateCode.get(0);
            // TODO Auto-generated method stub
        }

    }

    class BoardSpinnerAdapter implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            rtype = arrayListcodeboard.get(pos);

            // Toast.makeText(parent.Electricity.this, "OnItemSelectedListener : " + rtype, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            rtype = arrayListboard.get(0);
            // TODO Auto-generated method stub
        }

    }


}

