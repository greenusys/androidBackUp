package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.salonproduct.Adapter.Review_Adapter;
import com.example.salonproduct.Adapter.View_Cart_Adapter;
import com.example.salonproduct.Model.Cart_Model;
import com.example.salonproduct.Model.Review_Model;
import com.example.salonproduct.Model.View_Cart_PresenterImpl;
import com.example.salonproduct.Presenter.View_Cart_Presenter;
import com.example.salonproduct.R;
import com.example.salonproduct.View.View_Cart_View;

import java.util.ArrayList;

public class View_Cart extends AppCompatActivity implements View_Cart_View {

    private RecyclerView cart_rv;
    TextView total_cart_items,subtotal,shipping_price,total_price;
    private View_Cart_Adapter view_cart_adapter;
    ArrayList<Cart_Model> cart_models = new ArrayList<>();
    View_Cart_PresenterImpl view_cart_presenter;
    LinearLayout empty_bag_layout,sub_content_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__cart);
        initViews();
        view_cart_presenter=new View_Cart_PresenterImpl(this);
        view_cart_presenter.fetchCartItems();
        setPayment_Detail();




    }

    private void initViews() {
        cart_rv = (RecyclerView) findViewById(R.id.review_rv);
        total_cart_items = (TextView) findViewById(R.id.total_cart_items);
        subtotal = (TextView) findViewById(R.id.sub_total);
        shipping_price = (TextView) findViewById(R.id.shippting_price);
        total_price = (TextView) findViewById(R.id.total_price);
        empty_bag_layout = (LinearLayout) findViewById(R.id.empty_bag_layout);
        sub_content_layout = (LinearLayout) findViewById(R.id.sub_content_layout);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPayment_Detail() {
        int subtotal2=0;
        int total2=0;

        if(cart_models.size()>0)
        total_cart_items.setText(cart_models.size()+" Item in Your Bag");
        else
            total_cart_items.setText("Your Bag is empty");

        if(cart_models.size()>0) {

            //set sub total payments
            for (int i = 0; i < cart_models.size(); i++)
            {
                subtotal2=subtotal2+cart_models.get(i).getProduct_total_price();

            }

            //set  total payments
            for (int i = 0; i < cart_models.size(); i++)
            {
                total2=total2+cart_models.get(i).getProduct_total_price();

            }

            total_price.setText(String.valueOf(total2+cart_models.get(0).getShipping_price()));
            subtotal.setText(String.valueOf(subtotal2));
            shipping_price.setText("Flat rs: "+String.valueOf(cart_models.get(0).getShipping_price()));


        }


    }

    @Override
    public void setDataToCart_RecylerView(ArrayList<Cart_Model> cart_models) {

    if(cart_models.size()==0) {
        empty_bag_layout.setVisibility(View.VISIBLE);
        sub_content_layout.setVisibility(View.GONE);
    }


    else {
        empty_bag_layout.setVisibility(View.GONE);
        sub_content_layout.setVisibility(View.VISIBLE);


        this.cart_models = cart_models;
        view_cart_adapter = new View_Cart_Adapter(this, cart_models);
        cart_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        cart_rv.setAdapter(view_cart_adapter);
        view_cart_adapter.notifyDataSetChanged();
    }

    }

    public void select_address(View view) {
        startActivity(new Intent(getApplicationContext(),Checkout_Billing_Activity.class));
    }

    public void start_shopping(View view) {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }
}
