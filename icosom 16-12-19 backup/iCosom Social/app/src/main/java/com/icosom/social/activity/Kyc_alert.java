package com.icosom.social.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.icosom.social.R;
import com.icosom.social.ScannedBarcode;
import com.icosom.social.utility.AppController;

public class Kyc_alert extends AppCompatActivity {
    AppController appController;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_alert);
        appController = (AppController) getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
       // user_id = sp.getString("userId", "");
        String kycs = sp.getString("kyc", "0");
        if (kycs.equalsIgnoreCase("0")) {
            bui();
        }
        else{
            buis();
        }
    }
    private void bui() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Kyc_alert.this);

        // Setting Dialog Title
        alertDialog.setTitle("Kyc Verfication");

        // Setting Dialog Message
        alertDialog.setMessage("Be ready with addhar Card to scan for kyc verification");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Scan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Kyc_alert.this, ScannedBarcode.class).putExtra("act", "kyc"));

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Kyc_alert.this, MainActivity.class));
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    private void buis() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Kyc_alert.this);

        // Setting Dialog Title
        alertDialog.setTitle("Kyc Verfication");

        // Setting Dialog Message
        alertDialog.setMessage("You have already done your kyc verification");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Kyc_alert.this, MainActivity.class));
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
