package com.greenusys.personal.registrationapp.fragments;

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
import com.greenusys.personal.registrationapp.ParentMain;
import com.greenusys.personal.registrationapp.Parent_Gallery;
import com.greenusys.personal.registrationapp.Parent_Test_Result;
import com.greenusys.personal.registrationapp.Parent_Time_Table;
import com.greenusys.personal.registrationapp.R;
import com.greenusys.personal.registrationapp.StudentAssing;
import com.greenusys.personal.registrationapp.StudentResultActivity;
import com.greenusys.personal.registrationapp.Student_Attentec;
import com.greenusys.personal.registrationapp.Student_Reward;
import com.greenusys.personal.registrationapp.Subjects;
import com.greenusys.personal.registrationapp.TimeTableActivity;
import com.greenusys.personal.registrationapp.pojos.HomeGrid;

import java.util.List;

public class Parent_HomeAdapter extends RecyclerView.Adapter<Parent_HomeAdapter.HomeAdapterViewHolder> {

    private static final int VIEW_TYPE_CAROUSEL = 0;
    private static final int VIEW_TYPE_GRID = 1;
    private static final int OFFLINE_TEST_CODE = 1;

    private final Context mContext;

    private List<HomeGrid> mList;

    public Parent_HomeAdapter(Context context, List<HomeGrid> list)
    {
        mContext = context;
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
                    Intent intent= new Intent(mContext, Parent_Test_Result.class);
                    mContext.startActivity(intent);
                }if(pos==1)
                {
                   /* Intent intent= new Intent(mContext, Study.class);
                    intent.putExtra("act","1");
                    mContext.startActivity(intent);*/
                    Intent intent= new Intent(mContext, Parent_Time_Table.class);
                  //  intent.putExtra("class", ParentMain.stu_class);
                    mContext.startActivity(intent);

                }if(pos==2)
                {
                    Intent intent = new Intent(mContext, ImagesGridActivity.class);
                    // Intent intent = new Intent(mContext, StudentResultActivity.class);

                    intent.putExtra(mContext.getString(R.string.offline_test_flag),true);

                    mContext.startActivity(intent);
                }
                if(pos==3)
                {
                    Intent intent= new Intent(mContext, Student_Attentec.class);
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
