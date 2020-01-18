package com.icosom.social.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.Atom;
import com.icosom.social.R;


public class AtomsFragment extends Fragment  {
    TextView sub;
    public AtomsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atoms, container, false);

        sub = view.findViewById(R.id.SUBMIT11111);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Atom.class));
            }
        });



        return view;
    }





}
