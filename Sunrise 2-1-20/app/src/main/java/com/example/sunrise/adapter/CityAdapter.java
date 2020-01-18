package com.example.sunrise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sunrise.Modal.City_Modal;
import com.example.sunrise.R;

import java.util.List;

public class CityAdapter extends BaseAdapter
{
    List<City_Modal> countryModels;
    Context context;
    LayoutInflater inflater = null;

    public CityAdapter(List<City_Modal> countryModels, Context context) {
        this.countryModels = countryModels;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countryModels.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder
    {
        TextView txt_countryName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ViewHolder viewHolder;

        if (v == null)
        {
            v = inflater.inflate(R.layout.country_item, null);
            viewHolder = new ViewHolder();

            viewHolder.txt_countryName = v.findViewById(R.id.txt_countryName);

            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) v.getTag();
        }



        viewHolder.txt_countryName.setText(countryModels.get(i).getName());

        return v;
    }
}
