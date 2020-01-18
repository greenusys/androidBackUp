package com.example.digitalforgeco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.modal.*


class Temperature_Adapter(val today_date: String, val from: String, val context: Context) : RecyclerView.Adapter<Temperature_Adapter.ViewHolder>() {

    var temp_list: ArrayList<Temperature_Calc_History>? = null
    var distance_list: ArrayList<Distance_Calc_History>? = null
    var weight_list: ArrayList<Weight_Calc_History>? = null
    var length_list: ArrayList<Length_Calc_History>? = null
    var volume_list: ArrayList<Volume_Calc_History>? = null
    var setted: Boolean = true


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (from.equals("temperature")) {

            val view = LayoutInflater.from(context).inflate(R.layout.temperature_item, parent, false)
            return ViewHolder(view)
        } else if (from.equals("distance")) {

            val view = LayoutInflater.from(context).inflate(R.layout.distance_history_item, parent, false)
            return ViewHolder(view)
        } else if (from.equals("weight")) {

            val view = LayoutInflater.from(context).inflate(R.layout.weight_history_item, parent, false)
            return ViewHolder(view)
        } else if (from.equals("length")) {

            val view = LayoutInflater.from(context).inflate(R.layout.length_history_item, parent, false)
            return ViewHolder(view)
        } else if (from.equals("volume")) {

            val view = LayoutInflater.from(context).inflate(R.layout.volume_item, parent, false)
            return ViewHolder(view)
        } else
            throw Exception("kaiffff")
    }

    override fun getItemCount(): Int {

        if (from.equals("temperature"))
            return temp_list!!.size
        else if (from.equals("distance"))
            return distance_list!!.size
        else if (from.equals("weight"))
            return weight_list!!.size
        else if (from.equals("length"))
            return length_list!!.size
        else if (from.equals("volume"))
            return volume_list!!.size
        else return 0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (from.equals("temperature"))
            Bind_Temperature_Data(holder, position)
        else if (from.equals("distance"))
            Bind_Distance_Data(holder, position)
        else if (from.equals("weight"))
            Bind_Weight_Data(holder, position)
        else if (from.equals("length"))
            Bind_Length_Data(holder, position)

else if (from.equals("volume"))
            Bind_Volume_Data(holder, position)


    }

    private fun Bind_Temperature_Data(holder: ViewHolder, position: Int) {

        if (today_date.equals(temp_list?.get(position)?.date)) {
            holder.temp_date.text = "Today"
        }  else
            holder.temp_date.text = temp_list?.get(position)!!.date



        holder.farhen.text = temp_list?.get(position)!!.farhenheit
        holder.celsius.text = temp_list?.get(position)!!.celsius
        holder.kelvin.text = temp_list?.get(position)!!.kelvin


    }


    private fun Bind_Distance_Data(holder: ViewHolder, position: Int) {

        if (today_date.equals(distance_list?.get(position)?.date) ) {
            holder.distance_date.text = "Today"
        }  else
            holder.distance_date.text = distance_list?.get(position)!!.date



        holder.miles.text = distance_list?.get(position)!!.miles
        holder.km.text = distance_list?.get(position)!!.km
        holder.meters.text = distance_list?.get(position)!!.meters
        holder.feet.text = distance_list?.get(position)!!.feet
        holder.inches.text = distance_list?.get(position)!!.inches


    }

    private fun Bind_Length_Data(holder: ViewHolder, position: Int) {

        if (today_date.equals(length_list?.get(position)?.date)) {
            holder.length_date.text = "Today"
        } else
            holder.length_date.text = length_list?.get(position)!!.date



        holder.length_meters.text = length_list?.get(position)!!.meters
        holder.length_cm.text = length_list?.get(position)!!.centimeter
        holder.length_mm.text = length_list?.get(position)!!.mm
        holder.length_feet.text = length_list?.get(position)!!.feet
        holder.length_inches.text = length_list?.get(position)!!.inches


    }


    private fun Bind_Weight_Data(holder: ViewHolder, position: Int) {

        if (today_date.equals(weight_list?.get(position)?.date) ) {
            holder.weight_date.text = "Today"
        } else
            holder.weight_date.text = weight_list?.get(position)!!.date



        holder.kg.text = weight_list?.get(position)!!.kg
        holder.stone.text = weight_list?.get(position)!!.stones
        holder.lbs.text = weight_list?.get(position)!!.lbs_pound
        holder.oz.text = weight_list?.get(position)!!.oz


    }

    private fun Bind_Volume_Data(holder: ViewHolder, position: Int) {

        if (today_date.equals(volume_list?.get(position)?.date) ) {
            holder.volume_date.text = "Today"
        } else
            holder.volume_date.text = volume_list?.get(position)!!.date



        holder.length.text = volume_list?.get(position)!!.length
        holder.width.text = volume_list?.get(position)!!.width
        holder.height.text = volume_list?.get(position)!!.height

        val result= volume_list!![position].title_plus_result.split("_")


        holder.result_title.text =result[0]
        holder.result.text =result[1]

    }


    fun set_Temperature_Data(historyList: ArrayList<Temperature_Calc_History>) {
        temp_list = historyList

    }

    fun set_Distance_Data(historyList: ArrayList<Distance_Calc_History>) {
        distance_list = historyList

    }

    fun set_Weight_Data(historyList: ArrayList<Weight_Calc_History>) {
        weight_list = historyList

    }

    fun set_Length_Data(historyList: ArrayList<Length_Calc_History>) {
        length_list = historyList

    }
    fun set_Volume_Data(historyList: ArrayList<Volume_Calc_History>) {
        volume_list = historyList

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //for temperature
        val temp_date = view.findViewById<TextView>(R.id.date)
        val temp_date_title = view.findViewById<TextView>(R.id.date_title)
        val farhen = view.findViewById<TextView>(R.id.farhen)
        val celsius = view.findViewById<TextView>(R.id.celsius)
        val kelvin = view.findViewById<TextView>(R.id.kelvin)


        //for distance
        val distance_date = view.findViewById<TextView>(R.id.date)
        val distance_date_title = view.findViewById<TextView>(R.id.date_title)
        val miles = view.findViewById<TextView>(R.id.miles)
        val km = view.findViewById<TextView>(R.id.km)
        val meters = view.findViewById<TextView>(R.id.meters)
        val feet = view.findViewById<TextView>(R.id.feet)
        val inches = view.findViewById<TextView>(R.id.inches)

        //for weight
        val weight_date = view.findViewById<TextView>(R.id.date)
        val weight_date_title = view.findViewById<TextView>(R.id.date_title)
        val kg = view.findViewById<TextView>(R.id.kg)
        val stone = view.findViewById<TextView>(R.id.stone)
        val lbs = view.findViewById<TextView>(R.id.lbs)
        val oz = view.findViewById<TextView>(R.id.oz)

        //for Length
        val length_date = view.findViewById<TextView>(R.id.date)
        val length_date_title = view.findViewById<TextView>(R.id.date_title)
        val length_meters = view.findViewById<TextView>(R.id.meters)
        val length_cm = view.findViewById<TextView>(R.id.cm)
        val length_mm = view.findViewById<TextView>(R.id.mm)
        val length_feet = view.findViewById<TextView>(R.id.feet)
        val length_inches = view.findViewById<TextView>(R.id.inches)

        //for Volume
        val volume_date = view.findViewById<TextView>(R.id.date)
        val volume_date_title = view.findViewById<TextView>(R.id.date_title)
        val length = view.findViewById<TextView>(R.id.length)
        val width = view.findViewById<TextView>(R.id.width)
        val height = view.findViewById<TextView>(R.id.height)
        val result_title = view.findViewById<TextView>(R.id.result_title)
        val result = view.findViewById<TextView>(R.id.result)


    }
}