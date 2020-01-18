package com.example.digitalforgeco.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.activity.Show_Tracking_History
import com.example.digitalforgeco.modal.Tracking_History_Modal
import kotlinx.android.synthetic.main.item_courier_list.view.*
import kotlinx.android.synthetic.main.tracking_history_list_item.view.*


class Tracking_History_List_Adapter(val context: Context, private val mFeedList: List<Tracking_History_Modal>) : RecyclerView.Adapter<Tracking_History_List_Adapter.TimeLineViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.tracking_history_list_item, parent, false)
        return TimeLineViewHolder(view)


    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

holder.date.text=mFeedList[position].date
holder.tracking_number.text=mFeedList[position].tracking_no

        holder.main_layout.setOnClickListener(View.OnClickListener {

            context.startActivity(Intent(context,Show_Tracking_History::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("jsondata",mFeedList[position].json_data)
            )
        })


    }


    override fun getItemCount() = mFeedList.size

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tracking_number = itemView.tracking_number
        val date = itemView.date
        val main_layout = itemView.main_layout


    }

}
