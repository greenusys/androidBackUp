package com.greenusys.personal.registrationapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.FullNewzActivity;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.pojos.News;
import com.greenusys.personal.registrationapp.pojos.Newz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by personal on 2/22/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private final Context context;

    private List<News> newsList;

    private NewItemOnClickHandler clickHandler;
    public interface NewItemOnClickHandler{

        public void onClick(Newz newz);
    }

    public NewsAdapter(Context context, NewItemOnClickHandler clickHandler,ArrayList<News> newsList)
    {
        this.context = context;
        this.newsList = newsList;

        newsList = new ArrayList<>();
        this.clickHandler = clickHandler;
    }


    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {



        holder.heading.setText(newsList.get(position).getTitle());
        holder.date.setText(newsList.get(position).getTime().split(" ")[0]);
        holder.description.setText(newsList.get(position).getNews());

        holder.newslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), FullNewzActivity.class);

              intent.putExtra("title",newsList.get(position).getTitle());
                intent.putExtra("date",newsList.get(position).getTime());
                intent.putExtra("description",newsList.get(position).getNews());

                v.getContext().startActivity(intent);



            }
        });




    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView heading;
        TextView date;
        TextView description;
        ConstraintLayout newslayout;

        public NewsAdapterViewHolder(View itemView)
        {
            super(itemView);
            heading = (TextView)itemView.findViewById(R.id.headline_tv);
            date = (TextView)itemView.findViewById(R.id.date_tv);
            description = (TextView)itemView.findViewById(R.id.description_tv);
            newslayout = (ConstraintLayout) itemView.findViewById(R.id.news_layout);



           // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

           // clickHandler.onClick(newsList.get(getAdapterPosition()));

        }
    }

    public void updateData(List<News> newsList)
    {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
