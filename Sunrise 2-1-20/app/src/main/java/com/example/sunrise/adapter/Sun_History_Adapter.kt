package com.example.sunrise.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrise.Modal.Favourite_Modal
import com.example.sunrise.Modal.History_Modal
import com.example.sunrise.R
import com.example.sunrise.activity.Show_History_Activity

class Sun_History_Adapter(var is_all_selected: Boolean, val list: ArrayList<History_Modal>, val context: Context) : RecyclerView.Adapter<Sun_History_Adapter.ViewHolder>() {

    var activity: Show_History_Activity? = null

    init {
        activity = context as Show_History_Activity
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.address.text = list[position].address
        holder.sunrise_time.text = list[position].sunrise_time
        holder.sunset_time.text = list[position].sunset_time




        if (is_all_selected)
            holder.card_layout.setBackgroundColor(context.resources.getColor(R.color.dark_grey))


        holder.card_layout.setOnLongClickListener {


            //if item already selected
            if (list[position].isItem_selected == true) {
                //unselect that item
                list[position].isItem_selected = false
                holder.card_layout.setBackgroundColor(context.resources.getColor(R.color.light_grey))

            } else {
                activity!!.enable_Delete_and_CheckBox()

                list[position].isItem_selected = true
                holder.card_layout.setBackgroundColor(context.resources.getColor(R.color.dark_grey))


            }


            return@setOnLongClickListener true
        }


    }


    fun is_Selected_Item(): Boolean {

        var value: Boolean = false

        for (i in 0..list.size - 1) {
            if (list.get(i).isItem_selected)
                value = true
            break
        }

        return value
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val address = view.findViewById<TextView>(R.id.address)
        val sunrise_time = view.findViewById<TextView>(R.id.sunrise_time)
        val sunset_time = view.findViewById<TextView>(R.id.sunset_time)
        val card_layout = view.findViewById<CardView>(R.id.card_layout)
    }
}