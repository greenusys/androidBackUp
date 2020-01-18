package com.example.salonproduct.Presenter;

import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface Show_Category_Products_Presenter {

    void fetch_Show_Category_Products();
    void success_Category_Products_loaded(ArrayList<Prodect_Model> prodect_models);





}
