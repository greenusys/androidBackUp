package com.icosom.social.list_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icosom.social.R;

import java.util.ArrayList;

public class NavListAdapter extends BaseAdapter
{
    LayoutInflater inflater = null;
    Context context;
    ArrayList<String> text;
    ArrayList<Integer> logo;
    ArrayList<Boolean> select;

    public NavListAdapter(Context context, ArrayList<String> text, ArrayList<Integer> logo, ArrayList<Boolean> select)
    {
        this.context = context;
        this.text = text;
        this.logo = logo;
        this.select = select;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return text.size();
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
        LinearLayout lay;
        View view_select;
        ImageView iv_logo;
        TextView txt_text;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ViewHolder viewHolder;
        if (v == null)
        {
            v = inflater.inflate(R.layout.nav_layout_item, null);
            viewHolder = new ViewHolder();

            viewHolder.iv_logo = v.findViewById(R.id.iv_logo);
            viewHolder.lay = v.findViewById(R.id.lay);
            viewHolder.view_select = v.findViewById(R.id.view_select);
            viewHolder.txt_text = v.findViewById(R.id.txt_text);

            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.iv_logo.setImageResource(logo.get(i));
        viewHolder.txt_text.setText(text.get(i));

        viewHolder.view_select.setVisibility(select.get(i)?View.VISIBLE:View.INVISIBLE);

        viewHolder.lay.setBackgroundColor(select.get(i)? Color.parseColor("#1b000000"):0);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);

        if (viewHolder.txt_text.getText().equals("iWallet (New)")){
            viewHolder.iv_logo.startAnimation(animation);
            viewHolder.txt_text.startAnimation(animation);
        }

        if (viewHolder.txt_text.getText().equals("iTalent (New)")){
            viewHolder.iv_logo.startAnimation(animation);
            viewHolder.txt_text.startAnimation(animation);
        }

        return v;
    }
}