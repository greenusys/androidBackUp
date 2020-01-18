package com.example.sunrise.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrise.Modal.Favourite_Modal
import com.example.sunrise.R
import com.example.sunrise.activity.Show_Favourite_Location
import com.example.sunrise.activity.Show_History_Activity

class Fvrt_Locatoin_Adapter(var is_all_selected: Boolean, val list: ArrayList<Favourite_Modal>, val context: Context) : RecyclerView.Adapter<Fvrt_Locatoin_Adapter.ViewHolder>() {


    var activity: Show_Favourite_Location? = null

    init {
        activity = context as Show_Favourite_Location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favourite_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.address.text = list[position].address



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



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val address = view.findViewById<TextView>(R.id.address)
        val card_layout = view.findViewById<CardView>(R.id.card_layout)
    }
}