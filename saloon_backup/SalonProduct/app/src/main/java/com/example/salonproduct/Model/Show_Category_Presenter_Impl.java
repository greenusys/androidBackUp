package com.example.salonproduct.Model;

import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Presenter.Show_Category_Products_Presenter;

import java.util.ArrayList;

public class Show_Category_Presenter_Impl implements Show_Category_Products_Presenter {

    Show_Category_Products view;
    Show_Category_Product_API show_category_product_api;

    public Show_Category_Presenter_Impl(Show_Category_Products view) {
        this.view=view;
        show_category_product_api=new Show_Category_Product_API(this);

    }


    @Override
    public void fetch_Show_Category_Products() {
        show_category_product_api.load_Category_Products();


    }

    @Override
    public void success_Category_Products_loaded(ArrayList<Prodect_Model> prodect_models) {
view.setDataToShowCategory_Prodcuts_RecylerView(prodect_models);
    }
}
