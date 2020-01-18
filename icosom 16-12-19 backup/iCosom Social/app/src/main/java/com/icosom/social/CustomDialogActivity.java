package com.icosom.social;



 import android.app.Activity;
import android.app.AlertDialog;
 import android.os.Build;
 import android.os.Bundle;
 import androidx.annotation.RequiresApi;
 import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

    /**
     * Created by ThuruThuru on 3/31/2018.
     */

    public class CustomDialogActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.test2);

            Button openalert = (Button) findViewById(R.id.openalert);
            final TextView edm = (TextView) findViewById(R.id.namem);

            openalert.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());


                    builder.setTitle("Send Money").setView(R.layout.layout_dialogue);

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final AlertDialog diagview = ((AlertDialog) dialog);

                    Button Pay = (Button) diagview.findViewById(R.id.pay);
                    Button Apply_Now = (Button) diagview.findViewById(R.id.apply_code);
                  final  EditText amount = (EditText) diagview.findViewById(R.id.editText);
                    final  EditText voucher_code = (EditText) diagview.findViewById(R.id.editText3);

                    //custom lisenter for Positive button
                    Pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(amount.getText().toString().isEmpty()){
                                amount.setError("Enter Amount!");
                            }

                            else if(voucher_code.getText().toString().isEmpty()){
                                voucher_code.setError("Enter Voucher Code!");
                            }
                            else{
                               // edm.setText(et.getText().toString());
                                //dialog.dismiss();
                            }
                        }
                    });

                    //custom lisenter for Cancel button
                    Apply_Now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(voucher_code.getText().toString().isEmpty())
                            {
                                voucher_code.setError("Enter Voucher Code!");
                            }



                        }
                    });
                }
            });

        }
    }