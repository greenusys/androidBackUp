package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.salonproduct.Adapter.Review_Adapter;
import com.example.salonproduct.Model.ProductDescription_PresenterImpl;
import com.example.salonproduct.Model.Product_Description_API;
import com.example.salonproduct.Model.Review_Model;
import com.example.salonproduct.R;
import com.example.salonproduct.View.View_More_View;

import java.util.ArrayList;

public class View_More_Reviews extends AppCompatActivity implements View_More_View {

    ArrayList<Review_Model> review_modelsss=new ArrayList<>();

    private Review_Adapter review_adapter;
    RecyclerView review_rv;
    private ProductDescription_PresenterImpl productDescription_presenter;
    private TextView total_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__more__reviews);

        initViews();

        productDescription_presenter=new ProductDescription_PresenterImpl(this,"more");
        productDescription_presenter.fetchReviews();


        System.out.println("view_more"+Product_Description_API.reviewModels2.size());


    }

    private void initViews() {
        review_rv=findViewById(R.id.review_rv);
        total_review = (TextView) findViewById(R.id.total_review);

    }

    @Override
    public void setDataToReviewMore_RecylerView(ArrayList<Review_Model> reviewlist) {
        String countreview=Integer.toString(reviewlist.size());
        total_review.setText("Review"+"("+countreview+")");

        this.review_modelsss=reviewlist;
        review_adapter = new Review_Adapter(this, review_modelsss);
        review_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        review_rv.setAdapter(review_adapter);
        review_rv.setNestedScrollingEnabled(false);
        review_adapter.notifyDataSetChanged();
    }
}
