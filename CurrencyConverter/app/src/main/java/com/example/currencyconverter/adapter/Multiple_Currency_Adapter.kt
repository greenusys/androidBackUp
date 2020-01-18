package com.example.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.currencyconverter.R
import com.example.currencyconverter.modal.Mul_Currency_Modal
import kotlinx.android.synthetic.main.multiple_cur_item.view.*

class Multiple_Currency_Adapter(val list:ArrayList<Mul_Currency_Modal>,val context:Context): RecyclerView.Adapter<Multiple_Currency_Adapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val v=LayoutInflater.from(context).inflate(R.layout.multiple_cur_item,parent,false);
    return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(list[position].country_image).apply(RequestOptions().placeholder(R.drawable.blank_cur)).thumbnail(0.01f).into(holder.country_image)


        holder.country_name.text=list[position].cur_name
        holder.country_code.text=list[position].cur_code
        holder.result.text=list[position].result
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        var country_image=view.country_image
        var country_code=view.country_code
        var country_name=view.country_name
        var result=view.result

    }


}