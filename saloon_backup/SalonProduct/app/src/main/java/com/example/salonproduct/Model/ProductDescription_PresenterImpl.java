package com.example.salonproduct.Model;

import com.example.salonproduct.Activities.HomeActivity;
import com.example.salonproduct.Activities.Product_Description;
import com.example.salonproduct.Activities.View_More_Reviews;
import com.example.salonproduct.Presenter.HomePresenter;
import com.example.salonproduct.Presenter.Product_Description_Presenter;

import java.util.ArrayList;

public class ProductDescription_PresenterImpl implements Product_Description_Presenter {

        Product_Description product_description;
    Product_Description_API product_description_api;
    View_More_Reviews view_more_reviews;

    public ProductDescription_PresenterImpl(Product_Description product_description) {
        this.product_description=product_description;
        product_description_api=new Product_Description_API(this);

    }
    public ProductDescription_PresenterImpl(View_More_Reviews view_more_reviews, String fromViewMore) {
        this.view_more_reviews=view_more_reviews;
        product_description_api=new Product_Description_API(this);

    }


    @Override
    public void fetchReviews() {
        product_description_api.load_Reviews();

    }

    @Override
    public void success_review_loaded(ArrayList<Review_Model> review_models) {
if(product_description!=null)
        product_description.setDataToReview_RecylerView(review_models);

        if(view_more_reviews!=null)
    view_more_reviews.setDataToReviewMore_RecylerView(review_models);

    }
}
