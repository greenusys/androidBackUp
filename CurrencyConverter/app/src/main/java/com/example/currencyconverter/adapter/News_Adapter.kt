package com.example.currencyconverter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.currencyconverter.R
import com.example.currencyconverter.activity.News_Description
import com.example.currencyconverter.modal.News_Resp_Modal
import kotlinx.android.synthetic.main.news_item.view.*

class News_Adapter(val list:ArrayList<News_Resp_Modal.News_Res_List>, val context:Context): RecyclerView.Adapter<News_Adapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val v=LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
    return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.main_layout.setOnClickListener()
        {
            context.startActivity(Intent(context,News_Description::class.java)
                    .putExtra("title",list[position].blog_title)
                    .putExtra("description",list[position].blog_content)
                    .putExtra("image","https://convertedcurrency.com/admin/"+list[position].blog_image)
            )
        }

        Glide.with(context)
                .load("https://convertedcurrency.com/admin/"+list[position].blog_image)
                .into(holder.blog_image)
        holder.blog_title.text=list[position].blog_title

    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        var main_layout:LinearLayout=view.main_layout
        var blog_image=view.blog_image
        var blog_title=view.blog_title
    }


}