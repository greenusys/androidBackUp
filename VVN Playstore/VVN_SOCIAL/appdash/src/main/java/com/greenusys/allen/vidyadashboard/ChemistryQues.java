package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapterQues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChemistryQues extends AppCompatActivity {
    ExpandableListAdapterQues listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry_ques);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapterQues(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Ques-1 Define deliquescence");
        listDataHeader.add("Ques-2 What is atomicity?");
        listDataHeader.add("Ques-3 Define efflorescene");
        listDataHeader.add("Ques-4 State Boyle's Law");



        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("It is aprocess by which a compound absorbs moisture/water from the atmosphere and disloves in to form a soln. ");
        List<String> now = new ArrayList<String>();
        now.add("Atomicity is defined as the number of atoms present in given molecule ");
        List<String> a1 = new ArrayList<String>();
        a1.add("It is a process wherby a hydrated salt loses all or part of its water of crystallization when exposed to  the air.)");
        List<String> a2 = new ArrayList<String>();
        a2.add("It states that the vol of a given mass of gas is inversely proptional to its pressure if the temperature remains constant");



        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), now);
        listDataChild.put(listDataHeader.get(2), a1);
        listDataChild.put(listDataHeader.get(3), a2);

    }
}

