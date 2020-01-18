package com.example.currencyconverter.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.currencyconverter.R
import com.example.currencyconverter.activity.MainActivity
import com.example.currencyconverter.modal.Country_Modal
import com.example.currencyconverter.modal.Data

import java.util.ArrayList

class Country_Adapter(//   private final Activity activity;
        internal var context: Context, internal var friendRequestModels: ArrayList<Country_Modal>, private val value: String) : RecyclerView.Adapter<Country_Adapter.ViewHolder>(), Filterable {
    private val activity: Activity
    internal var friendlistfilter: ArrayList<Country_Modal>
    internal var img_list: ArrayList<String>? = null

    private val second = 1


    init {
        this.friendlistfilter = friendRequestModels
        activity = context as Activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.country_name.text = friendlistfilter[position].country_name

        //  System.out.println("image" + friendlistfilter.get(position).getCountry_image());

        Glide.with(context).load(friendlistfilter[position].country_image).apply(RequestOptions().placeholder(R.drawable.ic_menu)).thumbnail(0.01f).into(holder.country_image)


        holder.layout.setOnClickListener {
            if (value == "first") {
                Data.name1 = friendlistfilter[position].country_code
                Data.image1 = friendlistfilter[position].country_image
                Data.country_code_2lett_first = friendlistfilter[position].country_code_2lett
            }

            if (value == "second") {
                Data.name2 = friendlistfilter[position].country_code
                Data.image2 = friendlistfilter[position].country_image
                Data.country_code_2lett_second = friendlistfilter[position].country_code_2lett

            }

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("country_name", friendlistfilter[position].country_name)
            intent.putExtra("country_image", friendlistfilter[position].country_image)
            intent.putExtra("country_code_2lett", friendlistfilter[position].country_code_2lett)
            intent.putExtra("data", "true")
            intent.putExtra("value", value)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right)

            activity.finish()
        }

    }

    override fun getItemCount(): Int {
        return friendlistfilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    friendlistfilter = friendRequestModels
                } else {
                    val filteredList = ArrayList<Country_Modal>()
                    for (row in friendRequestModels) {

                        if (row.country_name.toLowerCase().contains(charString.toLowerCase()) || row.country_code.toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(row)
                        }
                    }

                    friendlistfilter = filteredList
                    println("friendlistfilter" + friendlistfilter.size)

                }

                val filterResults = Filter.FilterResults()
                filterResults.values = friendlistfilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                friendlistfilter = filterResults.values as ArrayList<Country_Modal>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var country_name: TextView
        internal var country_image: ImageView
        internal var layout: LinearLayout

        init {


            country_name = itemView.findViewById(R.id.country_name)
            country_image = itemView.findViewById(R.id.country_image)
            layout = itemView.findViewById(R.id.layout)

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM_NORMAL

    }

    companion object {

        private val TYPE_HEADER = 0
        private val TYPE_ITEM_NORMAL = 1
    }
}