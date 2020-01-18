package com.icosom.social.Talent_Show_Package.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.icosom.social.R;

import java.util.ArrayList;

public class Show_Video_Adapter extends RecyclerView.Adapter<Show_Video_Adapter.ViewHolder>
{
    Context context;
    private static final int TYPE_ITEM_NORMAL = 0;
    ArrayList<String> uris;
    boolean video;
    boolean image;


    public Show_Video_Adapter(Context context, ArrayList<String> uris,boolean video,boolean image)
    {
        this.context = context;
        this.uris = uris;
        this.video = video;
        this.image = image;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_ITEM_NORMAL)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_files_layout_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        System.out.println("kaif_vi"+uris.get(position)+" image"+image);


        if(image==false)
        {

            if (video == true || video == false) {
                System.out.println("1");
                //for video
                if (video == true) {
                    holder.video_icon.setVisibility(View.VISIBLE);
                    holder.voice_icon.setVisibility(View.GONE);
                    Glide.with(context).load(uris.get(position)).into(holder.iv_img);
                }
                //for voice
                else {
                    holder.video_icon.setVisibility(View.GONE);
                    holder.voice_icon.setVisibility(View.VISIBLE);
                }
            }
        }
        //for image
        else if(image==true)
        {
            System.out.println("2");
            holder.video_icon.setVisibility(View.GONE);
            holder.voice_icon.setVisibility(View.GONE);
            Glide.with(context).load(uris.get(position)).into(holder.iv_img);
        }




        holder.iv_close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               uris.remove(position);
               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return uris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_img;
        ImageView iv_close,video_icon,voice_icon;

        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_close = itemView.findViewById(R.id.iv_close);
            video_icon = itemView.findViewById(R.id.video_icon);
            voice_icon = itemView.findViewById(R.id.voice_icon);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return TYPE_ITEM_NORMAL;
    }
}