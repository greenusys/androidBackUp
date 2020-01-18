package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.salonproduct.R;
import com.example.salonproduct.View.Product_Description_View;
import com.steelkiwi.library.view.BadgeHolderLayout;

public class Product_Description extends AppCompatActivity implements Product_Description_View {
    int card_count = 0;
    int product_count = 0;

    BadgeHolderLayout badgeHolderLayout;
    TextView total_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__description);
        initviews();
    }

    private void initviews() {
        badgeHolderLayout = (BadgeHolderLayout) findViewById(R.id.view);
        total_product = (TextView) findViewById(R.id.total_product);
    }






    @Override
    public void add_to_cart(View view) {

        badgeHolderLayout.setCountWithAnimation(++card_count);
    }

    @Override
    public void add_review(View view) {

        System.out.println("ohhh");

        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.add_review);

       // Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        /*dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }







    @Override
    public void decrease_count(View view) {
        if (product_count >= 2) {
            product_count = product_count - 1;
            total_product.setText(String.valueOf(product_count));
        }
    }

    @Override
    public void increase_count(View view) {
        product_count = product_count + 1;
        total_product.setText(String.valueOf(product_count));
    }
}
