package com.example.salonproduct.Model;

import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Presenter.Show_Category_Products_Presenter;
import com.example.salonproduct.Presenter.View_All_Presenter;
import com.example.salonproduct.View.View_All_View;

import java.util.ArrayList;

public class View_All_PresenterImpl implements View_All_Presenter {

    View_All_View view;
    View_All_API show_category_product_api;

    public View_All_PresenterImpl(View_All_View view) {
        this.view=view;
        show_category_product_api=new View_All_API(this);

    }


    @Override
    public void fetch_view_all_Products() {
        show_category_product_api.load_view_all_Products();


    }

    @Override
    public void success_view_all_Products_loaded(ArrayList<Prodect_Model> prodect_models) {
view.setDataToView_ALl_Prodcuts_RecylerView(prodect_models);
    }
}
