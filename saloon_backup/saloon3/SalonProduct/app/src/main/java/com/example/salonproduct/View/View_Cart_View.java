package com.example.salonproduct.View;

import com.example.salonproduct.Model.Cart_Model;
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface View_Cart_View {

    void showProgress();
    void hideProgress();
    void setPayment_Detail();
    void setDataToCart_RecylerView(ArrayList<Cart_Model> cart_models);




}
