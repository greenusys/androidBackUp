package com.greenusys.personal.registrationapp.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greenusys.personal.registrationapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by personal on 2/22/2018.
 */

public class SlidingImagesAdapter extends PagerAdapter {

    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImagesAdapter(Context context, ArrayList<Integer> images)
    {
        this.context = context;
        this.IMAGES = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.sliding_images_layout,container,false);

        if(imageLayout != null)
        {
            final ImageView imageView = (ImageView)imageLayout.findViewById(R.id.sliding_image);

            //imageView.setImageResource(IMAGES.get(position));


            Picasso.with(context).load(IMAGES.get(position)).into(imageView);

            container.addView(imageLayout,0);

        }

        return imageLayout;
    }
}
