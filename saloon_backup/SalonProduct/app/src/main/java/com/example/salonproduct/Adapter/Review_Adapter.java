package com.example.salonproduct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Model.Categre_model;
import com.example.salonproduct.Model.Review_Model;
import com.example.salonproduct.R;

import java.util.ArrayList;

import static com.example.salonproduct.R.layout.item_categree;
import static com.example.salonproduct.R.layout.item_review;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.MyviewHolder> {
       ArrayList<Review_Model> reviewModels;
         Context context;

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(item_review,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {

        holder.review.setText(reviewModels.get(position).getReview());

        holder.ratingBar.setRating(Float.parseFloat(reviewModels.get(position).getRating()));


    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder
    {
       RatingBar ratingBar;
       TextView review;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            this.ratingBar = itemView.findViewById(R.id.rating);
            this.review = itemView.findViewById(R.id.review);

        }
    }

    public Review_Adapter(Context context, ArrayList<Review_Model> review_models) {
        this.reviewModels = review_models;
        this.context = context;

    }
}
