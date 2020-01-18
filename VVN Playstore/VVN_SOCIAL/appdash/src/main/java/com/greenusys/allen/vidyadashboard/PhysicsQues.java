package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapterQues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhysicsQues extends AppCompatActivity {
    ExpandableListAdapterQues listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics_ques);
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
        listDataHeader.add("Ques-1 A particle is projected horizontally at 15ms-1 from a height of 20 m \n\n Calculate the horizonal distance covered by the particle just before hitting the ground\n[g=10ms-2]");
        listDataHeader.add("Ques-2 Explain why mercury does not wet glass while water does?");
        listDataHeader.add("Ques-3 What is meant by cations");
        listDataHeader.add("Ques-4 An electric charge of 9.6*104C liberates 1 mole of substances containing 6.0*1023 atoms.Determine the value of the electronic charge");



        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The expected ans is:\n \t Let R represents the horizontal distance covered at a time,t\nh=1/2gt\u2072\n20=1/2*10*t\u2072\nt=2s\nR=ut\n=15*2=30m ");
        List<String> now = new ArrayList<String>();
        now.add("Mercury does not wet glass because (force of) cohesion and attraction of mercury molecules is greater than adhesion/attractionbetween glass and mercury molecules\n\n water wets glass because attraction between glass and water molecules is greater than cohesion of water molecules ");
        List<String> a1 = new ArrayList<String>();
        a1.add("Cations-are positive ions that are attracted to the cathode(during electrolysis)");
        List<String> a2 = new ArrayList<String>();
        a2.add("Let e represents the electronic charge\ne=Faradays constant\nAvogardo's number\n=9.6*10\u2074\n=6.0*10");



        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), now);
        listDataChild.put(listDataHeader.get(2), a1);
        listDataChild.put(listDataHeader.get(3), a2);

    }
}
