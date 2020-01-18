package com.example.salonproduct.Presenter;

import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface View_All_Presenter {

    void fetch_view_all_Products();
    void success_view_all_Products_loaded(ArrayList<Prodect_Model> prodect_models);





}
