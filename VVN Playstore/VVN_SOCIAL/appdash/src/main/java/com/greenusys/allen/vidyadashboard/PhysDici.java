package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhysDici extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phys_dici);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Acceleration");
        listDataHeader.add("Alpha Radiation");
        listDataHeader.add("Alternating Current");
        listDataHeader.add("Alternator");
        listDataHeader.add("Ampere");
        listDataHeader.add("Amplifier");

        listDataHeader.add("Amplitude");
        List<String> a5 = new ArrayList<String>();
        a5.add("Amplitude is the maximum displacement of a perodic waves");

        listDataHeader.add("Analogue");
        List<String> a6 = new ArrayList<String>();
        a6.add("Analogue is aform of circuit or device that has an output that is propotional to the input");

        listDataHeader.add("Atom");
        List<String> a7 = new ArrayList<String>();
        a7.add("Atom is a fundamental and basic unit of an element");

        listDataHeader.add("Battery");
        List<String> a8 = new ArrayList<String>();
        a8.add("Battery is two or more primary cells connected together,usually in series, to provide a source of electric current");

        listDataHeader.add("Capacitor");
        List<String> a9 = new ArrayList<String>();
        a9.add("The ratio of charge strored on an isolated conductor to the change in potentia");


        listDataHeader.add("Core");
        List<String> a10= new ArrayList<String>();
        a10.add("The central part of an object");

        listDataHeader.add("Cell");
        List<String> a11 = new ArrayList<String>();
        a11.add("Cell is a device capable of changing some form of energy");

        listDataHeader.add("Charge");
        List<String> a12 = new ArrayList<String>();
        a12.add("Electric charge is the basic property of matter that characterized by some particles");

        listDataHeader.add("Conductor");
        List<String> a13 = new ArrayList<String>();
        a13.add("It is a material the freely permits free flow of electric current");

        listDataHeader.add("Conduction");
        List<String> a14 = new ArrayList<String>();
        a14.add("It is the process by which energy is transfer by the movement of paticles in contact to each other");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Accleration is the rate of change of velocity with respect to time");
        List<String> now = new ArrayList<String>();
        now.add("Alpha Radiation is alpha particles emitted from a radioactive isotope");
        List<String> a1 = new ArrayList<String>();
        a1.add("Alternating current is an electric current that reverses direction in a circuit at regular intervals");
        List<String> a2 = new ArrayList<String>();
        a2.add("Alternator is an electric generator that produces alternating current");
        List<String> a3 = new ArrayList<String>();
        a3.add("Ampere is the unit of current");

        List<String> a4 = new ArrayList<String>();
        a4.add("Amplifier is the unit of current");





        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), now);
        listDataChild.put(listDataHeader.get(2), a1);
        listDataChild.put(listDataHeader.get(3), a2);
        listDataChild.put(listDataHeader.get(4), a3);
        listDataChild.put(listDataHeader.get(5), a4);
        listDataChild.put(listDataHeader.get(6), a5);
        listDataChild.put(listDataHeader.get(8), a6);
        listDataChild.put(listDataHeader.get(9), a7);
        listDataChild.put(listDataHeader.get(10), a8);
        listDataChild.put(listDataHeader.get(11), a9);
        listDataChild.put(listDataHeader.get(12), a10);
        listDataChild.put(listDataHeader.get(13), a11);
        listDataChild.put(listDataHeader.get(13), a12);
        listDataChild.put(listDataHeader.get(14), a13);
        listDataChild.put(listDataHeader.get(15), a14);



    }
}