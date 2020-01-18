package com.example.salonproduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.salonproduct.R;

public class SliderAdapter extends PagerAdapter {
    private int[] img;
    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context, int[] img){
        this.context=context;
        this.img=img;
    }

    @Override
    public int getCount() {
        return img.length ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview=layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView=(ImageView)itemview.findViewById(R.id.swipeimage);
        imageView.setImageResource(img[position]);
        container.addView(itemview);
        return itemview;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
