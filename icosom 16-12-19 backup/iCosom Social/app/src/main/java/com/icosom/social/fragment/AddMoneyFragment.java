package com.icosom.social.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.icosom.social.PayU.PayUMainActivity;
import com.icosom.social.R;
import com.icosom.social.activity.PassbookRecharge;


public class AddMoneyFragment extends Fragment {

    EditText etAmountAddMoney;
    TextView tvSubmitAddMoneyRecharge;
    String amount;
    TextView tvViewBalanceAddMoneyRecharge;

    public AddMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_money, container, false);

        etAmountAddMoney = view.findViewById(R.id.etAmountAddMoney);

        tvSubmitAddMoneyRecharge = view.findViewById(R.id.tvSubmitAddMoneyRecharge);
        tvViewBalanceAddMoneyRecharge = view.findViewById(R.id.tvViewBalanceAddMoneyRechargesss);
        tvViewBalanceAddMoneyRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PassbookRecharge.class));
            }
        });


        tvSubmitAddMoneyRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = etAmountAddMoney.getText().toString().trim();
                Intent payUIntent = new Intent(getActivity(), PayUMainActivity.class);
                payUIntent.putExtra("AMOUNT", amount);
                payUIntent.putExtra("ADD_MONEY", "TRUE");
                startActivity(payUIntent);

            }
        });

        return view;
    }

}
