package com.example.currencyconverter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.currencyconverter.R;
import com.example.currencyconverter.activity.MainActivity;
import com.example.currencyconverter.modal.Country_Modal;
import com.example.currencyconverter.modal.Data;

import java.util.ArrayList;

public class Country_Adapter extends RecyclerView.Adapter<Country_Adapter.ViewHolder> implements Filterable {
    private final Activity activity;
    private final String value;
    //   private final Activity activity;
    Context context;
    ArrayList<Country_Modal> friendlistfilter;
    ArrayList<Country_Modal> friendRequestModels;
    ArrayList<String> img_list;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    private final int first = 1;
    private final int second = 1;


    public Country_Adapter(Context context, ArrayList<Country_Modal> friendRequestModels, String value) {
        this.context = context;
        this.friendRequestModels = friendRequestModels;
        this.friendlistfilter = friendRequestModels;
        this.value = value;
        activity = (Activity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.country_name.setText(friendlistfilter.get(position).getCountry_name());

        //  System.out.println("image" + friendlistfilter.get(position).getCountry_image());

        Glide.
                with(context).
                load(friendlistfilter.get(position).getCountry_image()).
                apply(new RequestOptions().placeholder(R.drawable.back_arrow)).
                thumbnail(0.01f).
                into(holder.country_image);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value.equals("first")) {
                    Data.name1 = friendlistfilter.get(position).getCountry_code();
                    Data.image1 = friendlistfilter.get(position).getCountry_image();
                    Data.country_code_2lett_first = friendlistfilter.get(position).getCountry_code_2lett();
                }

                if (value.equals("second")) {
                    Data.name2 = friendlistfilter.get(position).getCountry_code();
                    Data.image2 = friendlistfilter.get(position).getCountry_image();
                    Data.country_code_2lett_second = friendlistfilter.get(position).getCountry_code_2lett();

                }

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("country_name", friendlistfilter.get(position).getCountry_name());
                intent.putExtra("country_image", friendlistfilter.get(position).getCountry_image());
                intent.putExtra("country_code_2lett", friendlistfilter.get(position).getCountry_code_2lett());
                intent.putExtra("data", "true");
                intent.putExtra("value", value);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);

                activity.finish();


            }
        });

    }

    @Override
    public int getItemCount() {
        return friendlistfilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    friendlistfilter = friendRequestModels;
                } else {
                    ArrayList<Country_Modal> filteredList = new ArrayList<>();
                    for (Country_Modal row : friendRequestModels) {

                        if (row.getCountry_name().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCountry_code().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(row);
                        }
                    }

                    friendlistfilter = filteredList;
                    System.out.println("friendlistfilter" + friendlistfilter.size());

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = friendlistfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                friendlistfilter = (ArrayList<Country_Modal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country_name;
        ImageView country_image;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);


            country_name = itemView.findViewById(R.id.country_name);
            country_image = itemView.findViewById(R.id.country_image);
            layout = itemView.findViewById(R.id.layout);

        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}