package com.greenusys.personal.registrationapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenusys.personal.registrationapp.CourseActivity;
import com.greenusys.personal.registrationapp.ImagesGridActivity;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.StudentAssing;
import com.greenusys.personal.registrationapp.StudentResultActivity;
import com.greenusys.personal.registrationapp.Student_Attentec;
import com.greenusys.personal.registrationapp.Student_Reward;
import com.greenusys.personal.registrationapp.Study_Notes_PDF;
import com.greenusys.personal.registrationapp.Subjects;
import com.greenusys.personal.registrationapp.TimeTableActivity;
import com.greenusys.personal.registrationapp.Video_Subject_Wise;
import com.greenusys.personal.registrationapp.fragments.Student;
import com.greenusys.personal.registrationapp.pojos.HomeGrid;

import java.util.List;

public class Subject_Category_Adapter extends RecyclerView.Adapter<Subject_Category_Adapter.HomeAdapterViewHolder> {

    private static final int VIEW_TYPE_CAROUSEL = 0;
    private static final int VIEW_TYPE_GRID = 1;
    private static final int OFFLINE_TEST_CODE = 1;

    private final Context mContext;
    private String sub_id="";

    private List<HomeGrid> mList;

    public Subject_Category_Adapter(String sub_id,Context context, List<HomeGrid> list)
    {
        mContext = context;
        this.sub_id = sub_id;
        mList = list;
    }


    @Override
    public HomeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //int layoutId;

       /** switch (viewType)
        {
            case VIEW_TYPE_CAROUSEL: {
                layoutId = R.id.dummy_image;
                break;
            }

            case VIEW_TYPE_GRID: {
                layoutId = R.layout.home_rv_grid_item;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        } */

       View view =    LayoutInflater.from(mContext).inflate(R.layout.home_rv_grid_item,parent,false);
        return new HomeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapterViewHolder holder, int position) {
        final int pos = position;
        holder.iconImage.setImageResource(mList.get(position).getImageSource());
        holder.descriptionText.setText(mList.get(position).getImageDescription());


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pos==0)
                {
                    Intent intent= new Intent(mContext, Study_Notes_PDF.class);
                    intent.putExtra("sub_id",sub_id);
                    mContext.startActivity(intent);
                }if(pos==1)
                {
                   // Intent intent= new Intent(mContext, Student.class);
                   // mContext.startActivity(intent);

                }if(pos==2)
                {
                    Intent intent= new Intent(mContext, Video_Subject_Wise.class);
                    mContext.startActivity(intent);

                }


            }
        });




    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeAdapterViewHolder extends RecyclerView.ViewHolder
    {
       ImageView iconImage;
       TextView descriptionText;
       CardView cv;


        public HomeAdapterViewHolder(View itemView) {
            super(itemView);



                iconImage = (ImageView) itemView.findViewById(R.id.item_iv);
                descriptionText = (TextView) itemView.findViewById(R.id.item_description);
                cv = itemView.findViewById(R.id.cv_home);


        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0)
        {
            return VIEW_TYPE_CAROUSEL;
        }
        else
        {
            return VIEW_TYPE_GRID;
        }
    }
}
