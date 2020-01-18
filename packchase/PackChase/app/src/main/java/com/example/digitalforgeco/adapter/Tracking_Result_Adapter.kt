package com.example.digitalforgeco.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.digitalforgeco.R
import com.example.digitalforgeco.modal.Tracking_Modal


import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.extentions.setGone
import com.github.vipulasri.timelineview.sample.extentions.setVisible
import kotlinx.android.synthetic.main.item_tracking_result.view.*
import java.lang.Exception


class Tracking_Result_Adapter(private val mFeedList: List<Tracking_Modal>) : RecyclerView.Adapter<Tracking_Result_Adapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if(!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(mLayoutInflater.inflate(R.layout.item_tracking_result, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val timeLineModel = mFeedList[position]

        when {
            timeLineModel.status.trim().equals("transit") -> {
                setMarker(holder, R.drawable.transit, R.color.green)
            }
            timeLineModel.status.trim().equals("out for delivery")->  {
                setMarker(holder, R.drawable.out_for_delivery, R.color.grey)
            }
            timeLineModel.status.trim().equals("pickup")->  {
                setMarker(holder, R.drawable.out_for_delivery, R.color.grey)
            }

            //for Delivered
            else -> {
                setMarker(holder, R.drawable.delivered, R.color.green)
            }
        }

        if (timeLineModel.date.isNotEmpty()) {
            holder.date.setVisible()

            try {
                holder.date.text = timeLineModel.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")

            }catch (e:Exception)
            {
                holder.date.text = timeLineModel.date

            }



        } else
            holder.date.setGone()

        holder.message.text = timeLineModel.StatusDescription


    }

    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {

try {
    holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId, ContextCompat.getColor(holder.itemView.context, colorFilter))

}catch (e:Exception)
{
    println("timeline_marker_excepton"+e.printStackTrace())

}

    }

    override fun getItemCount() = mFeedList.size

        inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.text_timeline_date
        val message = itemView.text_timeline_title
        val timeline = itemView.timeline

        init {
            timeline.initLine(viewType)
        }
    }

}
