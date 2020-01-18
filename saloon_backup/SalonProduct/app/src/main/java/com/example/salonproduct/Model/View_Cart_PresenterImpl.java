package com.example.salonproduct.Model;

import com.example.salonproduct.Activities.HomeActivity;
import com.example.salonproduct.Activities.View_Cart;
import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.Presenter.View_Cart_Presenter;

import java.util.ArrayList;

public class View_Cart_PresenterImpl implements View_Cart_Presenter {

        View_Cart view_cart;
    View_Cart_API view_cart_api;

    public View_Cart_PresenterImpl(View_Cart view_cart) {
        this.view_cart=view_cart;
        view_cart_api=new View_Cart_API(this);

    }


    @Override
    public void fetchCartItems() {
        view_cart_api.load_Cart_Items();

    }

    @Override
    public void success_cart_items_loaded(ArrayList<Cart_Model> cart_models) {

        view_cart.setDataToCart_RecylerView(cart_models);
    }
}
