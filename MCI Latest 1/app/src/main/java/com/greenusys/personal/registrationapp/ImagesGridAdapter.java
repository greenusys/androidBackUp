package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by personal on 2/21/2018.
 */

public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridAdapter.ImagesGridAdapterViewHolder> {

    private final Context mContext;
    private final ImagesGridAdapterOnClickHandler mClickHandler;

    private List<String> imagesList;

    public interface ImagesGridAdapterOnClickHandler{

        void onClick(String imageSource);
    }

    public ImagesGridAdapter(Context context, List<String> list,
                             ImagesGridAdapterOnClickHandler imagesClickHandler  )
    {
        mContext = context;
        imagesList = list;
        mClickHandler = imagesClickHandler;
    }


    @Override
    public ImagesGridAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.images_grid_item,parent,false);
        return new ImagesGridAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ImagesGridAdapterViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(imagesList.get(position))
                .placeholder(R.drawable.galery)
                .into(holder.gridImage);
    }



    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class ImagesGridAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView gridImage;

        public ImagesGridAdapterViewHolder(View itemView) {
            super(itemView);



            gridImage = (ImageView) itemView.findViewById(R.id.gird_image_item);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            mClickHandler.onClick(imagesList.get(getAdapterPosition()));

        }
    }


}
