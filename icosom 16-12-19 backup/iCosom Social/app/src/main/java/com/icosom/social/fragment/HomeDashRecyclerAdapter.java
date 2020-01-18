package com.icosom.social.fragment;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.icosom.social.R;

/**
 * Created by admin on 22-05-2018.
 */

class HomeDashRecyclerAdapter extends RecyclerView.Adapter<HomeDashRecyclerAdapter.ViewHolder> {
    Context context;

    DecelerateInterpolator sDecelerater = new DecelerateInterpolator();
    OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

    public HomeDashRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_one_layout_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        if (position == 0) {

            holder.img.setImageResource(R.drawable.ic_pay);
            holder.txt.setText("Pay");
        }

        if (position == 1) {

            holder.img.setImageResource(R.drawable.dths);
            holder.txt.setText("Dth");
        }

        if (position == 2) {

            holder.img.setImageResource(R.drawable.elec);
            holder.txt.setText("Electricity");
        }
        if (position == 3) {

            holder.img.setImageResource(R.drawable.gasbill);
            holder.txt.setText("Gas ");
        }
        if (position == 4) {

            holder.img.setImageResource(R.drawable.mobile);
            holder.txt.setText("mobile ");
        }

        /*holder.lay_postShare.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    holder.lay_postShare.animate().setInterpolator(sDecelerater).
                            scaleX(.7f).scaleY(.7f);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    holder.lay_postShare.animate().setInterpolator(sOvershooter).
                            scaleX(1f).scaleY(1f);
                }
                return false;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;


        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_re_fr);
            txt = itemView.findViewById(R.id.txt_re_fr);
        }
    }
}