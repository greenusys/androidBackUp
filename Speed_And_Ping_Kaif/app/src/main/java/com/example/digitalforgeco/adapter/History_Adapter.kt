package com.example.digitalforgeco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.modal.History
import kotlinx.android.synthetic.main.history_item.view.*
import java.util.*

class History_Adapter(val context: Context, val list:ArrayList<History>)
    : RecyclerView.Adapter<History_Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.history_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = list[position].date
        holder.time.text = list[position].time
        holder.ping.text = list[position].ping
        holder.download.text = list[position].download
        holder.upload.text = list[position].upload

    }


    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {

        val date = itemView.date
        val time = itemView.time
        val ping = itemView.ping
        val upload = itemView.upload
        val download = itemView.download

    }

}


