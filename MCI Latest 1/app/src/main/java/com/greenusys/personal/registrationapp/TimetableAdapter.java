package com.greenusys.personal.registrationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by personal on 3/9/2018.
 */

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableAdapterViewModel> {

    private final Context context;
    private ArrayList<String> timeSlot;
    private ArrayList<String> lectureBy;

    public TimetableAdapter(Context context, ArrayList<String> timeSlot, ArrayList<String> lectureBy)
    {

        this.context = context;
        this.timeSlot = timeSlot;
        this.lectureBy = lectureBy;
    }

    @Override
    public TimetableAdapterViewModel onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.time_table_item,parent,false);
        return new TimetableAdapterViewModel(rootView);
    }

    @Override
    public void onBindViewHolder(TimetableAdapterViewModel holder, int position) {



            holder.timeSlotTextView.setText(timeSlot.get(position));
            holder.lectureByTextView.setText(lectureBy.get(position));


    }

    @Override
    public int getItemCount() {
        return timeSlot.size();
    }

    class TimetableAdapterViewModel extends RecyclerView.ViewHolder
    {

        TextView timeSlotTextView;
        TextView lectureByTextView;

        public TimetableAdapterViewModel(View itemView) {
            super(itemView);

            timeSlotTextView = (TextView)itemView.findViewById(R.id.time_slot_tv);
            lectureByTextView = (TextView)itemView.findViewById(R.id.lecture_by);
        }
    }

    public void updateDataset(ArrayList<String> timeSlot, ArrayList<String> lectureBy )
    {
        this.timeSlot = timeSlot;
        this.lectureBy = lectureBy;
        notifyDataSetChanged();
    }
}
