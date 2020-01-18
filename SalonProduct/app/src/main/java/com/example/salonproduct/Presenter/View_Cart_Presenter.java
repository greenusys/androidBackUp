package com.example.salonproduct.Presenter;

import com.example.salonproduct.Model.Cart_Model;
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface View_Cart_Presenter {

    void fetchCartItems();
    void success_cart_items_loaded(ArrayList<Cart_Model> cart_models);





}
