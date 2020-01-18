package com.example.salonproduct.View;

import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface Show_Category_Products_View {

    void showProgress();
    void hideProgress();
    void setDataToShowCategory_Prodcuts_RecylerView(ArrayList<Prodect_Model> prodect_models);




}
