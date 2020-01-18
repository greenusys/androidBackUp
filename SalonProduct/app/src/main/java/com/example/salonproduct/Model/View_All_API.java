package com.example.salonproduct.Model;

import com.example.salonproduct.Presenter.Show_Category_Products_Presenter;
import com.example.salonproduct.Presenter.View_All_Presenter;
import com.example.salonproduct.R;

import java.util.ArrayList;

public class View_All_API implements View_All_Model {


    View_All_Presenter presenter;
    ArrayList<Categre_model> categre_models = new ArrayList<>();
    ArrayList<Prodect_Model> prodect_models = new ArrayList<>();
    public View_All_API(View_All_Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void load_view_all_Products()
    {


        Prodect_Model prodect_model = new Prodect_Model("nasdf", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Hair", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Color", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Nivia", R.drawable.haircondition);
        prodect_models.add(prodect_model);
        prodect_model = new Prodect_Model("Garnier", R.drawable.haircondition);
        prodect_models.add(prodect_model);

        presenter.success_view_all_Products_loaded(prodect_models);





    }






}
