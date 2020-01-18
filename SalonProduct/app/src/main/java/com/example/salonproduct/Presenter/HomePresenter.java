package com.example.salonproduct.Presenter;

import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;

import java.util.ArrayList;

public interface HomePresenter {

    void fetchCategory();
    void fetchProducts();
    void fetchSliderImages();
    void success_category_loaded(ArrayList<Categre_model> categre_models);
    void success_products_loaded(ArrayList<Prodect_Model> prodect_models);
    void success_Slider_loaded(int img[]);




}
