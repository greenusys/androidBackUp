package com.example.salonproduct.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.salonproduct.Activities.Product_Description;
import com.example.salonproduct.Activities.Show_Category_Products;
import com.example.salonproduct.Model.Prodect_Model;
import com.example.salonproduct.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class Prodect_Adapter extends RecyclerView.Adapter<Prodect_Adapter.ViewHolder> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    ArrayList<Prodect_Model> prodect_models;
    Context context;
    int product_count;
    TextView dec_quantity,inc_quantity,total_product,add_to_cart;
    SliderLayout product_slider_images;
    HashMap<String,String> Hash_file_maps_images ;
    public Prodect_Adapter(Context context, ArrayList<Prodect_Model> prodect_models) {
        this.prodect_models = prodect_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodect,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

     holder.textView.setText(prodect_models.get(position).getName());
     holder.img.setImageResource(prodect_models.get(position).getImage());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Product_Description.class));
            }
        });


        holder.quick_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





              Dialog  dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.quick_view_item);

                 dec_quantity=dialog.findViewById(R.id.dec_quantity);
                 inc_quantity=dialog.findViewById(R.id.inc_quantity);
                 total_product=dialog.findViewById(R.id.total_product);
                 add_to_cart=dialog.findViewById(R.id.add_to_cart);
                product_slider_images=dialog.findViewById(R.id.product_slider_images);

                add_slider_images();
                inc_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_count = product_count + 1;
                        total_product.setText(String.valueOf(product_count));
                    }
                });
                dec_quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (product_count >= 2) {
                            product_count = product_count - 1;
                            total_product.setText(String.valueOf(product_count));
                        }
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            }
        });


    }

    @Override
    public int getItemCount() {
        return prodect_models.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
          TextView textView;
         ImageView img;
         LinearLayout quick_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.rv_pro_img_id);
            this.textView = itemView.findViewById(R.id.rv_pro_txt_id);
            this.quick_view = itemView.findViewById(R.id.quick_view);
        }
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

            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
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




}
