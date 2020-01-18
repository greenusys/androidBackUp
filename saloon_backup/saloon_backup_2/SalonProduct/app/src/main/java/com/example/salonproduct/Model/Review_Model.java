package com.example.salonproduct.Model;

public class Review_Model {

   String review_id;

    public Review_Model(String review_id, String review, String rating) {
        this.review_id = review_id;
        this.review = review;
        this.rating = rating;
    }

    String review;
    String rating;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
