package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Biodici extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodici);
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
        listDataHeader.add("Abdomen");
        listDataHeader.add("Abiotic ");
        listDataHeader.add("Abomasum");
        listDataHeader.add("Activation energy");
        listDataHeader.add("Adenylyl cyclase");
        listDataHeader.add("Adipose tissue");

        listDataHeader.add("Amino acid");
        List<String> a5 = new ArrayList<String>();
        a5.add("Amino acid  are the building blocks of all biological proteins");

        listDataHeader.add("Anaerobic respiration");
        List<String> a6 = new ArrayList<String>();
        a6.add("It is a part of a flower located at the top of a filament where pollen grains are formed");

        listDataHeader.add("Anther");
        List<String> a7 = new ArrayList<String>();
        a7.add("It is a no of protons in the nucleus of an atom");

        listDataHeader.add("Appendix");
        List<String> a8 = new ArrayList<String>();
        a8.add("It refers to vestigal organ A sac at the end of the large intestine");

        listDataHeader.add("Blood");
        List<String> a9 = new ArrayList<String>();
        a9.add("Blood is the red fluid that flows around the body ");


        listDataHeader.add("Larva");
        List<String> a10= new ArrayList<String>();
        a10.add("larva is a seperate immature form a lot of animals pass through before they undergo metamorphosis into adults");

        listDataHeader.add("Flagellum");
        List<String> a11 = new ArrayList<String>();
        a11.add("It is a lash like appendage that comes out of the cell body of certain prokaryotic and eukaryotic cells.");

        listDataHeader.add("Ecology");
        List<String> a12 = new ArrayList<String>();
        a12.add("It is the scientific study of interaction between organism and their environmnent");

        listDataHeader.add("Digestion");
        List<String> a13 = new ArrayList<String>();
        a13.add("It is the breakdown of food into smaller compoments that can be more readily absorbed and assimilated by the body");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("It is a internal part of mammal that lies between the Thorax and the Pelvis");
        List<String> now = new ArrayList<String>();
        now.add("Abiotic is a term used to define the non living physical and chemical factors in our enviromnment");
        List<String> a1 = new ArrayList<String>();
        a1.add("Abomasum is the fourth stomach in ruminant animals");
        List<String> a2 = new ArrayList<String>();
        a2.add("Activation energy is the least amt of energy for a reaction to take place");
        List<String> a3 = new ArrayList<String>();
        a3.add("Adenylyl cyclase is an enzyme that catalyzes the formation of cyclic AMP from ATP");

        List<String> a4 = new ArrayList<String>();
        a4.add("it is a term used for loose connective tissue");





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




    }
}


