package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salonproduct.Model.Review_Model;
import com.example.salonproduct.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Checkout_Billing_Activity extends AppCompatActivity {

    TextInputLayout fullname_layout,email_Layout,company_layout,address1_layout,address2_layout,
                    city_layout,pincode_layout,phone_layout;
    TextInputEditText fullname_text,email_text,company_text,address1_text,
                    address2_text,city_text,pincode_text,phone_text;
    Spinner state_spinner;
    TextView proceed_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__billing_);

        initviews();
        setListener();
    }

    private void setListener() {
        proceed_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckValidation();

            }
        });
    }

    private void CheckValidation() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
        if (!validateAddress1()) {
            return;
        }
        if (!validateCity()) {
            return;
        }
        if (!validateState()) {
            return;
        }
        if (!validatePinCode()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

    }


    private boolean validateName() {
        if (fullname_text.getText().toString().equals("")) {
            fullname_layout.setError("Please Enter Your Name");
            //requestFocus(fullname_layout);
            return false;
        }else if (fullname_text.getText().toString().length()<3) {
            fullname_layout.setError("Please Enter Your Valid Name");
           // requestFocus(fullname_layout);
            return false;
        } else {
            fullname_layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (email_text.getText().toString().equals("")) {
            email_Layout.setError("Please Enter Your Email");
            requestFocus(email_text);
            return false;
        } else if (!isValidEmail(email_text.getText().toString())) {
            email_Layout.setError("Please Enter Valid Email");
            requestFocus(email_text);
            return false;
        } else {
            email_Layout.setErrorEnabled(false);
        }

        return true;
    }

 private boolean validateAddress1() {
        //System.out.println("address_kaif"+);
        if (address1_text.getText().toString().equals("")) {
            address1_layout.setError("Please Enter House Number and Street Name");
            requestFocus(address1_text);
            return false;
        }else if (address1_text.getText().toString().length()<20) {
            address1_layout.setError("Please Enter Valid House Number and Street Name");
            requestFocus(address1_text);
            return false;
        }
        else {
            address1_layout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateCity() {
        if (city_text.getText().toString().equals("")) {
            city_layout.setError("Please Enter Your City");
            requestFocus(city_text);
            return false;
        } else {
            city_layout.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePinCode() {
        if (pincode_text.getText().toString().equals("")) {
            pincode_layout.setError("Please Enter Your Pin Code");
            requestFocus(pincode_text);
            return false;
        } else if (pincode_text.getText().toString().length()!=6 ) {
            pincode_layout.setError("Please Enter Your Valid Pin Code");
            requestFocus(pincode_text);
            return false;
        } else {
            pincode_layout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePhone() {
        if (phone_text.getText().toString().equals("")) {
            phone_layout.setError("Please Enter Your Mobile Number");
            requestFocus(phone_text);
            return false;
        } else if (phone_text.getText().toString().length()<10) {
            phone_layout.setError("Please Enter Your 10 digit Mobile Number");
            requestFocus(phone_text);
            return false;
        }
        else {
            pincode_layout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateState() {

        String spinner_value = state_spinner.getSelectedItem().toString();

        if (spinner_value.equalsIgnoreCase("Please Select Your State")) {

            TextView errorText = (TextView)state_spinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Provide Your State");//changes the selected item text to this

           // requestFocus(city_text);
            return false;
        } else {
            //city_layout.setErrorEnabled(false);
        }

        return true;
    }

    private void initviews() {
        getSupportActionBar().setTitle("CHECKOUT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TextInput Layouts
        fullname_layout=findViewById(R.id.fullname_Layout);
        email_Layout=findViewById(R.id.email_Layout);
        company_layout=findViewById(R.id.company_layout);
        address1_layout=findViewById(R.id.address1_layout);
        address2_layout=findViewById(R.id.address2_layout);
        city_layout=findViewById(R.id.city_layout);
        pincode_layout=findViewById(R.id.pincode_layout);
        phone_layout=findViewById(R.id.phone_layout);
        state_spinner=findViewById(R.id.state_spinner);

        //TextInputEditText
        fullname_text=findViewById(R.id.fullname_text);
        email_text=findViewById(R.id.email_text);
        company_text=findViewById(R.id.company_text);
        address1_text=findViewById(R.id.address1_text);
        address2_text=findViewById(R.id.address2_text);
        city_text=findViewById(R.id.city_text);
        pincode_text=findViewById(R.id.pincode_text);
        phone_text=findViewById(R.id.phone_text);

        //proceed field
        proceed_text=findViewById(R.id.procedd_text);



    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //point cursor to edittext box which having error
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
