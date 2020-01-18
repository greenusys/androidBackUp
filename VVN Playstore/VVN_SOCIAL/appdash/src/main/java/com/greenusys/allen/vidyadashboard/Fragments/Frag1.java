package com.greenusys.allen.vidyadashboard.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

import com.greenusys.allen.vidyadashboard.R;



public class Frag1 extends Fragment implements OnItemClickListener  {


    public Frag1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_frag1, container, false);

        String[] physlist= {"Physics as Science","Kinematics","Fluid and Weight","Scalar and Vector",
                "Force","Circular Motion","Energy","Momentum","Heat and Thermodynamics","Optics and Waves","Magnetism"
                ,"Electricity","Modern Physics","Nuclear Physics"};
        ListView lv= (ListView) view.findViewById(R.id.phys_list);

        ArrayAdapter<String> lva=new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,physlist);
lv.setAdapter(lva);
        return view;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }



}
