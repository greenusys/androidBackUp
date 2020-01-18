package com.example.salonproduct.Model;

import com.example.salonproduct.Activities.HomeActivity;
import com.example.salonproduct.Presenter.HomePresenter;

import java.util.ArrayList;

public class HomePresenterImpl implements HomePresenter {

        HomeActivity homeActivity2;
    Home_Page_API fetch_category_api;

    public HomePresenterImpl( HomeActivity homeActivity2) {
        this.homeActivity2=homeActivity2;
        fetch_category_api=new Home_Page_API(this);

    }


    @Override
    public void success_category_loaded(ArrayList<Categre_model> categre_models) {
        homeActivity2.setDataToCategory_RecylerView(categre_models);
        homeActivity2.hideProgress();

    }

    @Override
    public void success_products_loaded(ArrayList<Prodect_Model> prodect_models) {
        homeActivity2.setDataToProduct_RecylerView(prodect_models);
    }

    @Override
    public void success_Slider_loaded(int[] img) {
        homeActivity2.setDataToSlider_Layout(img);

    }




    @Override
    public void fetchProducts() {
        fetch_category_api.load_Products();

    }

    @Override
    public void fetchSliderImages() {
        fetch_category_api.load_Slider_Images();
    }

    @Override
    public void fetchCategory() {
        fetch_category_api.load_Category();

    }
}
