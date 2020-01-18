package com.avinashdavid.trivialtrivia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.Soldier_Clerk.Clerk;
import com.avinashdavid.trivialtrivia.Soldier_GD_Package.Soldier_GD;
import com.avinashdavid.trivialtrivia.Soldier_Nursing_Package.Soldier_Nursing;
import com.avinashdavid.trivialtrivia.Soldier_Tech_Package.Soldier_Tech;
/*
import com.greenusys.army_project.R;
import com.greenusys.army_project.Soldier_Clerk.Clerk;
import com.greenusys.army_project.Soldier_GD_Package.Soldier_GD;
import com.greenusys.army_project.Soldier_Nursing_Package.Soldier_Nursing;
import com.greenusys.army_project.Soldier_Tech_Package.Soldier_Tech;*/


public class OneFragment extends Fragment {
    TextView sample_paper,past_paper,eligibility,syllabus,exam_pattern,syllabus_link,exam_link;
    LinearLayout soldier_gd,soldier_cleark,soldier_tech,soldier_nursing,soldier_post1,soldier_post2;
    int flag=0;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

       /* sample_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new ThreeFragment()).commit();
            }
        });
*/
        return inflater.inflate(R.layout.fragment_one, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sample_paper=(TextView) getView().findViewById(R.id.sample_paper);
        past_paper=(TextView)getView().findViewById(R.id.past_paper);
        eligibility=(TextView)getView().findViewById(R.id.eligibility);
        syllabus=(TextView)getView().findViewById(R.id.syllabus);
        exam_pattern=(TextView)getView().findViewById(R.id.exam_pattern);

        //syllabus_link=(TextView)getView().findViewById(R.id.syllabus_link);
      //  exam_link=(TextView)getView().findViewById(R.id.exam_pattern_link2);


        soldier_gd=(LinearLayout)getView().findViewById(R.id.soldier_gd);
        soldier_cleark=(LinearLayout)getView().findViewById(R.id.soldier_clerk);
        soldier_tech=(LinearLayout)getView().findViewById(R.id.soldier_tech);
        soldier_nursing=(LinearLayout)getView().findViewById(R.id.soldier_nursing);





        soldier_gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Soldier_GD.class);
                startActivity(i );
            }
        });


        soldier_cleark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Clerk.class);
                startActivity(i );
            }
        });



        soldier_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Soldier_Tech.class);
                startActivity(i );
            }
        });


        soldier_nursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Soldier_Nursing.class);
                startActivity(i );
            }
        });

    }



}
