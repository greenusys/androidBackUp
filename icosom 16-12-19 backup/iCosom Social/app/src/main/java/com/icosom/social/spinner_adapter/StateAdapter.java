package com.icosom.social.spinner_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.model.CountryModel;
import com.icosom.social.model.StateModel;

import java.util.ArrayList;

public class StateAdapter extends BaseAdapter
{
    ArrayList<StateModel> stateModels;
    Context context;
    LayoutInflater inflater = null;

    public StateAdapter(ArrayList<StateModel> stateModels, Context context) {
        this.stateModels = stateModels;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stateModels.size();
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

        viewHolder.txt_countryName.setText(stateModels.get(i).getName());

        return v;
    }
}