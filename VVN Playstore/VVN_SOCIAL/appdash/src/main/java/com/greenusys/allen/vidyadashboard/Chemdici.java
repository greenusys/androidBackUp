package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chemdici extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemdici);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Amorphous Solid");
        listDataHeader.add("Analyte ");
        listDataHeader.add("Anion");
        listDataHeader.add("Anode");
        listDataHeader.add("Aryl Group");
        listDataHeader.add("Atmosphere");

        listDataHeader.add("Atom");
        List<String> a5 = new ArrayList<String>();
        a5.add("Atom os fundamental and basic unit of  element");

        listDataHeader.add("Atomic mass");
        List<String> a6 = new ArrayList<String>();
        a6.add("Atomic mass is the mass of an isotope of an element in atomic mass units");

        listDataHeader.add("Atomic number");
        List<String> a7 = new ArrayList<String>();
        a7.add("It is a no of protons in the nucleus of an atom");

        listDataHeader.add("Base");
        List<String> a8 = new ArrayList<String>();
        a8.add("Base is refers to any  substance that accepts hydrogen ions or protons");

        listDataHeader.add("Bond");
        List<String> a9 = new ArrayList<String>();
        a9.add("Chemical Bond is the attraction between 2 atom that results in a formation of a compound ");


        listDataHeader.add("Carbonyl");
        List<String> a10= new ArrayList<String>();
        a10.add("Carbonyl group is a chemically organic functional group composed of a carbon atom double-bonded to an oxygen atom");

        listDataHeader.add("Catlyst");
        List<String> a11 = new ArrayList<String>();
        a11.add("Catalyst is a substance that speeds up a chemical reaction ,but is not consumed by the reaction");

        listDataHeader.add("Curie");
        List<String> a12 = new ArrayList<String>();
        a12.add("The basic unit used to describe the intensity of radioactivity in a sample of material");

        listDataHeader.add("Conductor");
        List<String> a13 = new ArrayList<String>();
        a13.add("It is a material the freely permits free flow of electric current");

        listDataHeader.add("Density");
        List<String> a14 = new ArrayList<String>();
        a14.add("It is the mass os a substance per unit volume.");
        listDataHeader.add("Effusion");
        List<String> a15 = new ArrayList<String>();
        a15.add("It is the movement of gas molecules through a small opening.");
        listDataHeader.add("Electron");
        List<String> a16 = new ArrayList<String>();
        a15.add("It is the msubatomic particle with a negative elementary electric charge.");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("It is a substance that has a irregular  or unordered position of atoms");
        List<String> now = new ArrayList<String>();
        now.add("Analyte is a substance whose chemical constitues are of interst during analytical porocedure or irritation");
        List<String> a1 = new ArrayList<String>();
        a1.add("Anion is a negatively charged ion");
        List<String> a2 = new ArrayList<String>();
        a2.add("Anode is postively charged ion");
        List<String> a3 = new ArrayList<String>();
        a3.add("Aryl Group are group of atoms derived from benzene or from benzene derivative");

        List<String> a4 = new ArrayList<String>();
        a4.add("it is envelope surrounding the earth ;the air");





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
        listDataChild.put(listDataHeader.get(16), a15);
        listDataChild.put(listDataHeader.get(17), a16);



    }
}
