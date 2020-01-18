package com.example.digitalforgeco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.modal.Scienfic_Calc_History
import com.example.digitalforgeco.modal.Simple_Calc_History


class History_Adapter(val today_date: String, val from: String, val context: Context) : RecyclerView.Adapter<History_Adapter.ViewHolder>() {

    var simple_list: ArrayList<Simple_Calc_History>? = null
    var scientfic_list: ArrayList<Scienfic_Calc_History>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        if (from.equals("simple"))
            return simple_list!!.size
        else if (from.equals("scientfic"))
            return scientfic_list!!.size
        else return 0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (from.equals("simple")) {
            if (today_date.equals(this!!.simple_list?.get(position)?.date))
                holder.date.setText("Today")
            else
                holder.date.setText(simple_list?.get(position)?.date)

            holder.data.setText(simple_list?.get(position)?.data)
        } else if (from.equals("scientfic")) {
            if (today_date.equals(this!!.scientfic_list?.get(position)?.date))
                holder.date.setText("Today")
            else
                holder.date.setText(scientfic_list?.get(position)?.date)

            holder.data.setText(scientfic_list?.get(position)?.data)
        }


    }

    fun set_Simple_Data(historyList: ArrayList<Simple_Calc_History>) {
        simple_list = historyList

    }

    fun set_Scienfic_Data(historyList: ArrayList<Scienfic_Calc_History>) {
        scientfic_list = historyList

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val date = view.findViewById<TextView>(R.id.date)
        val data = view.findViewById<TextView>(R.id.data)

    }
}