package com.example.salonproduct.View;

import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface HomeView {

    void showProgress();
    void hideProgress();
    void setDataToCategory_RecylerView(ArrayList<Categre_model> categre_models);
    void setDataToProduct_RecylerView(ArrayList<Prodect_Model> product_models);
    void setDataToSlider_Layout(int[] img);



}
