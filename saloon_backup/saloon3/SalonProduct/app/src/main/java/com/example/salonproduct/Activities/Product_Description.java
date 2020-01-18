package com.example.salonproduct.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.salonproduct.Adapter.Prodect_Adapter;
import com.example.salonproduct.Adapter.Review_Adapter;
import com.example.salonproduct.Model.ProductDescription_PresenterImpl;
import com.example.salonproduct.Model.Review_Model;
import com.example.salonproduct.Model.Show_Category_Presenter_Impl;
import com.example.salonproduct.R;
import com.example.salonproduct.View.Product_Description_View;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.steelkiwi.library.view.BadgeHolderLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class Product_Description extends AppCompatActivity implements Product_Description_View,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    int card_count = 0;
    int product_count = 0;

    BadgeHolderLayout badgeHolderLayout;
    TextView total_product;
    TextInputLayout textinputlayout1;
    TextInputLayout textinputlayout2;
    TextInputLayout textinputlayout3;
    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText review;
    RatingBar ratingBar;
    ProductDescription_PresenterImpl productDescription_presenter;
    ArrayList<Review_Model> review_models=new ArrayList<>();
    Review_Adapter review_adapter;
    RecyclerView review_rv;
    TextView no_review,total_review,view_more;
     Dialog dialog;

    SliderLayout product_slider_images;
    HashMap<String,String> Hash_file_maps_images ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__description);
        initviews();

        productDescription_presenter=new ProductDescription_PresenterImpl(this);

       productDescription_presenter.fetchReviews();
        check_Review();
        add_slider_images();
    }

    private void initviews() {
        badgeHolderLayout = (BadgeHolderLayout) findViewById(R.id.view);
        total_product = (TextView) findViewById(R.id.total_product);
        no_review = (TextView) findViewById(R.id.no_review);
        total_review = (TextView) findViewById(R.id.total_review);
        view_more = (TextView) findViewById(R.id.view_more);
        review_rv = (RecyclerView) findViewById(R.id.review_rv);

        product_slider_images = (SliderLayout) findViewById(R.id.product_slider_images);
    }


    @Override
    public void add_to_cart(View view) {

        badgeHolderLayout.setCountWithAnimation(++card_count);
    }

    @Override
    public void add_review(View view) {

        System.out.println("ohhh");

       dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.add_review);


        textinputlayout1 = (TextInputLayout) dialog.findViewById(R.id.textinputlayout1);
        textinputlayout2 = (TextInputLayout) dialog.findViewById(R.id.textinputlayout2);
        textinputlayout3 = (TextInputLayout) dialog.findViewById(R.id.textinputlayout3);
        ratingBar=(RatingBar)dialog.findViewById(R.id.rating);
        TextView submit_review = (TextView) dialog.findViewById(R.id.submit_review);
        name = (TextInputEditText) dialog.findViewById(R.id.name);
        email = (TextInputEditText) dialog.findViewById(R.id.email);
        review = (TextInputEditText) dialog.findViewById(R.id.review);
        // if button is clicked, close the custom dialog
        submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitReview();
                setOnlyFiveRevies();

            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void check_Review() {
        if(review_models.size()>0)
        {
            String countreview=Integer.toString(review_models.size());
            no_review.setVisibility(View.GONE);
            total_review.setText("Review"+"("+countreview+")");
        }

        if(review_models.size()>4)
        {
            view_more.setVisibility(View.VISIBLE);
            view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),View_More_Reviews.class));
                }
            });
        }



    }

    @Override
    public void setDataToReview_RecylerView(ArrayList<Review_Model> review_models) {

        this.review_models = review_models;

        setOnlyFiveRevies();


    }

    private void setOnlyFiveRevies() {

        if(review_models.size()>3)
        {
            for(int i=4;i<review_models.size();i++)
            {
                review_models.remove(i);
            }
        }

        review_adapter = new Review_Adapter(this,review_models);
        review_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        review_rv.setAdapter(review_adapter);
        review_rv.setNestedScrollingEnabled(false);
        review_adapter.notifyDataSetChanged();



    }

    private void submitReview() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateReview()) {
            return;
        }
        if (!validateRating()) {
            return;
        }
        dialog.dismiss();



        Review_Model review_model=new Review_Model("2",review.getText().toString(),String.valueOf(ratingBar.getRating()));
        review_models.add(review_model);
      //  review_adapter.notifyDataSetChanged();


    }


    private boolean validateName() {
        if (name.getText().toString().equals("")) {
            textinputlayout1.setError("Please Enter Your Name");
            requestFocus(name);
            return false;
        } else {
            textinputlayout1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (email.equals("")) {
            textinputlayout2.setError("Please Enter Your Email");
            requestFocus(email);
            return false;
        } else if (!isValidEmail(email.getText().toString())) {
            textinputlayout2.setError("Please Enter Valid Email");
            requestFocus(email);
            return false;
        } else {
            textinputlayout2.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateReview() {
        if (review.getText().toString().equals("")) {
            textinputlayout3.setError("Please Enter Your Review");
            requestFocus(review);
            return false;
        } else {
            textinputlayout3.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateRating() {
        if (ratingBar.getRating()<=0) {
            Toast.makeText(this, "Please Select Your Rating ", Toast.LENGTH_SHORT).show();;
            return false;
        }
        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    public void decrease_count(View view) {
        if (product_count >= 2) {
            product_count = product_count - 1;
            total_product.setText(String.valueOf(product_count));
        }
    }

    @Override
    public void increase_count(View view) {
        product_count = product_count + 1;
        total_product.setText(String.valueOf(product_count));
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void goto_view_cart(View view) {
        startActivity(new Intent(getApplicationContext(),View_Cart.class));
    }




    private void add_slider_images()
    {
        //for slider images
        Hash_file_maps_images = new HashMap<String, String>();


        Hash_file_maps_images.put("Android CupCake", "https://image.freepik.com/free-photo/graphic-designers-meeting_1170-2002.jpg");
        Hash_file_maps_images.put("Android Donut", "https://image.freepik.com/free-photo/happy-middle-eastern-call-center-operator-smiling-showing-thumbs-up-office_97712-396.jpg");
        Hash_file_maps_images.put("Android Eclair", "https://image.freepik.com/free-photo/woman-student-posing-with-computer-while-studying-it-room_73503-1264.jpg");
        Hash_file_maps_images.put("Android Froyo", "https://image.freepik.com/free-photo/teens-holding-text-boxes_53876-90853.jpg");
        Hash_file_maps_images.put("Android GingerBread", "https://image.freepik.com/free-photo/colleagues-giving-fist-bump_53876-64857.jpg");

        for(String name : Hash_file_maps_images.keySet()){




            DefaultSliderView defaultSliderView = new DefaultSliderView(Product_Description.this);
            defaultSliderView .image(Hash_file_maps_images.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);



            product_slider_images.addSlider(defaultSliderView);

        }
        product_slider_images.setPresetTransformer(SliderLayout.Transformer.Accordion);
        product_slider_images.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        product_slider_images.setCustomAnimation(new DescriptionAnimation());
        //product_slider_images.setDuration(300000000);
        product_slider_images.stopAutoCycle();
        product_slider_images.addOnPageChangeListener(this);


        //end slider images

    }


    //for slider images
    @Override
    protected void onStop() {

        product_slider_images.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        System.out.println("kaif_ye"+slider.getUrl());

         //Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    //end slider images

    public void back_activity(View view) {
        //startActivity(new Intent(getApplicationContext(), Show_Category_Products.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        onBackPressed();
    }

}
