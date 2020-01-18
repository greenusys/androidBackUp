package com.icosom.social.Talent_Show_Package.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Modal.Premium_Response;
import com.icosom.social.Talent_Show_Package.View_Model.Pay_4_Premimum_VM;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.WalletPin;

public class Talent_Plan extends AppCompatActivity {

    private Pay_4_Premimum_VM Viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent__plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Icosom Talent Show");


        Viewmodel = ViewModelProviders.of(this).get(Pay_4_Premimum_VM.class);


    }

    public void free(View view) {
        startActivity(new Intent(getApplicationContext(), Talent.class));
    }

    public void premium(View view) {

        Viewmodel.check_Premium_USer(MainActivity.user_id).observe(Talent_Plan.this, new Observer<Premium_Response>() {
            @Override
            public void onChanged(@Nullable Premium_Response s) {


                System.out.println("result_of_check_premium" + s.getCode() + " " + s.getStatus());

                System.out.println("sayed" + s.getCode().equals("1"));

                if (s.getCode().equals("1")) {
                    System.out.println("called");

                    show_Alert_for_Premium(false, "You are  Icosom Premium Talent user");
                } else {

                    show_Alert_for_Premium(true, "First you need to add more than 50 amount in your Icosom wallet to become premium user");

                }


            }
        });


    }


    private void show_Alert_for_Premium(boolean pay, String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(Talent_Plan.this);
        builder.setTitle("Icosom Premium Talent");
        builder.setMessage(msg);

        if (pay) {
            String positiveText = "Pay";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(getBaseContext(), WalletPin.class));

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

        } else {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }


}
