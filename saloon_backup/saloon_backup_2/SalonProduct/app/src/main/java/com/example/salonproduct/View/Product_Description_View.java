package com.example.salonproduct.View;

import android.view.View;

import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.Model.Review_Model;

import java.util.ArrayList;

public interface Product_Description_View {

   void decrease_count(View view);
   void increase_count(View view);
   void add_to_cart(View view);
   void add_review(View view);
   void check_Review();
   void setDataToReview_RecylerView(ArrayList<Review_Model> review_models);





}
