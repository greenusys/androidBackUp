package com.example.salonproduct.Presenter;

import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.Model.Review_Model;

import java.util.ArrayList;

public interface Product_Description_Presenter {


    void fetchReviews();
    void success_review_loaded(ArrayList<Review_Model> categre_models);





}
