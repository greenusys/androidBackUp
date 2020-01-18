package com.icosom.social;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.utility.AppController;

import java.util.ArrayList;

public class DthRecharge extends AppCompatActivity {
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
        setContentView(R.layout.activity_dth_recharge);
        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userId = sp.getString("userId", "");


        //getBalance();
        arrayListStateName = new ArrayList<>();
        arrayListStateCode = new ArrayList<>();
        arrayListboard = new ArrayList<>();
        arrayListcodeboard = new ArrayList<>();

        state = findViewById(R.id.e_state);
        stateAdapter = new ArrayAdapter<String>(DthRecharge.this, R.layout.myspinner, arrayListStateName);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
        state.setOnItemSelectedListener(new DthRecharge.HomeSpinnerAdapter());
        getState();


        board = findViewById(R.id.d_operator);
        boardAdapter = new ArrayAdapter<String>(DthRecharge.this, R.layout.myspinner, arrayListboard);
        boardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board.setAdapter(boardAdapter);
        board.setOnItemSelectedListener(new DthRecharge.BoardSpinnerAdapter());
        getBoard();
        etAmount = findViewById(R.id.d_Amount);
        etconsumer = findViewById(R.id.d_cusomer_no);
        tvSubmitRecharge = findViewById(R.id.d_recharge);

        tvSubmitRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                amount = etAmount.getText().toString().trim();
                consumer = etconsumer.getText().toString().trim();

                if (amount.equalsIgnoreCase("")) {
                    //  progressDialog.dismiss();
                    Toast.makeText(DthRecharge.this, "Enter amount", Toast.LENGTH_SHORT).show();
                } else if (consumer.equalsIgnoreCase("")) {
                    //  progressDialog.dismiss();
                    Toast.makeText(DthRecharge.this, "Enter Subscription ID", Toast.LENGTH_SHORT).show();
                }  else {
                    checkBalance();
                }

            }
        });


        //  init(view);


    }

    private void checkBalance() {
        Confirm cdd=new Confirm(DthRecharge.this,amount,consumer,rtype);
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


        String[] boards = new String[]{"Select the operator", "SUN DTH", "BIG TV DTH", "TATA SKY DTH", "AIRTEL DTH","DISH DTH","VIDEOCON DTH"};
        String[] borcode = new String[]{"0", "26", "24", "27", "23","25","28"};


        arrayListboard.clear();
        arrayListcodeboard.clear();
        for (int i = 0; i < boards.length; i++) {

            arrayListboard.add(boards[i]);
            arrayListcodeboard.add(borcode[i]);

        }


        boardAdapter.notifyDataSetChanged();


    }






    class HomeSpinnerAdapter implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            //operator = arrayListStateCode.get(pos);

            //  Toast.makeText(parent.DthRecharge.this, "OnItemSelectedListener : " + operator, Toast.LENGTH_SHORT).show();
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

            // Toast.makeText(parent.DthRecharge.this, "OnItemSelectedListener : " + rtype, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            rtype = arrayListboard.get(0);
            // TODO Auto-generated method stub
        }

    }


}

