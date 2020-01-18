package com.avinashdavid.trivialtrivia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avinashdavid.trivialtrivia.Notification_package.Admit_Card;
import com.avinashdavid.trivialtrivia.Notification_package.All_Rally;
import com.avinashdavid.trivialtrivia.Notification_package.Latest_Notification;
import com.avinashdavid.trivialtrivia.Notification_package.More_Notification;
import com.avinashdavid.trivialtrivia.Notification_package.Result;
import com.avinashdavid.trivialtrivia.R;

/*import com.greenusys.army_project.Notification_package.Admit_Card;
import com.greenusys.army_project.Notification_package.All_Rally;
import com.greenusys.army_project.Notification_package.Latest_Notification;
import com.greenusys.army_project.Notification_package.More_Notification;
import com.greenusys.army_project.Notification_package.Result;
import com.greenusys.army_project.R;*/


public class FourFragment extends Fragment {
    TextView sample_paper,past_paper,eligibility,syllabus,exam_pattern,syllabus_link,exam_link;
    LinearLayout all_rally,result,latest_notification,admin_card,marite_result;
    public FourFragment() {
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
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        all_rally= (LinearLayout) getView().findViewById(R.id.all_rally);
        result= (LinearLayout) getView().findViewById(R.id.result);
        latest_notification= (LinearLayout) getView().findViewById(R.id.latest_notification);
        admin_card= (LinearLayout) getView().findViewById(R.id.admit_card);
        marite_result= (LinearLayout) getView().findViewById(R.id.marite_result);

        //syllabus_link=(TextView)getView().findViewById(R.id.syllabus_link);
        //  exam_link=(TextView)getView().findViewById(R.id.exam_pattern_link2);


      all_rally.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(getContext(), All_Rally.class));
                  }
              }
      );
      result.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(getContext(), Result.class));
                  }
              }
      );
      latest_notification.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(getContext(), Latest_Notification.class));
                  }
              }
      );
      admin_card.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(getContext(), Admit_Card.class));
                  }
              }
      );

      marite_result.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(getContext(), More_Notification.class));
                  }
              }
      );
    }
}
