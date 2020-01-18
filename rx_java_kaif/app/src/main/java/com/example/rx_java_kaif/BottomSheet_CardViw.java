package com.example.rx_java_kaif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheet_CardViw extends AppCompatActivity {

    private BottomSheetDialog sheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet2);
    }



    public void kaif(View view) {
        sheetDialog = new BottomSheetDialog(BottomSheet_CardViw.this);
        View sheetView = LayoutInflater.from(BottomSheet_CardViw.this).inflate(R.layout.bottom_sheet, null);
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();

    }
}
