package com.avinashdavid.trivialtrivia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/*import com.greenusys.army_project.Current_Affair_Hindi_English;
import com.greenusys.army_project.R;*/
import com.avinashdavid.trivialtrivia.Current_Affair_Hindi_English;
import com.avinashdavid.trivialtrivia.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class ThreeFragment extends Fragment {
    String[] SPINNERLIST = {"Hindi", "English"};
    String spinner_value = "";

    public ThreeFragment() {
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


        return inflater.inflate(R.layout.fragment_three, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)

    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) getView().findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);


        materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                spinner_value = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), spinner_value, Toast.LENGTH_LONG).show();

                Intent intent=new Intent(getContext(), Current_Affair_Hindi_English.class);
                intent.putExtra("language",spinner_value);
                startActivity(intent);
            }
        });





    }





}
