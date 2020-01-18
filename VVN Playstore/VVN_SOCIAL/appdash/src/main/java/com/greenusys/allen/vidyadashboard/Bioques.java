package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapterQues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bioques extends AppCompatActivity {
    ExpandableListAdapterQues listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bioques);
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
        listDataHeader.add("Ques-1 State four modes of nutrition in plants");
        listDataHeader.add("Ques-2 List two biotic nd abiotic factors each that affect population growth?");
        listDataHeader.add("Ques-3 Define population");
        listDataHeader.add("Ques-4 List various natural resources thar need to be conserved .");
        listDataHeader.add("Ques-5 List 5 morphological features that are characterstic of plant in tropical rainforest ");
        listDataHeader.add("Ques-6 State 3 function of kidney? ");



        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Four modes of nutrition in plants are::1-Autotrophic or holophytic nutrition\n2-Parastic nutrion\n3-Mutualism(symbiosis)\n4-Epiphytic nutrion ");
        List<String> now = new ArrayList<String>();
        now.add("Two biotic factors are::(1)-Animals(2)-Microbes\nTwo abiotic factors are::(1)-Rainfall(2)-wind");
        List<String> a1 = new ArrayList<String>();
        a1.add("The term population can be defined as the number of a specific species of organism living in a particular habitat which have the capability of interbreeding.");
        List<String> a2 = new ArrayList<String>();
        a2.add("There are a lot of natural resources  that need to be conserved.Eg-are:water,soil,Wildlife and solid Minerals");
        List<String> a3 = new ArrayList<String>();
        a3.add("1-They are tall trees with trunks that are not branched but which taper very little.\n1-the plants have thin barks are made up of buttters roots" +
                "\n3-The leaf buds of plants in tropical rain forest do not have protective covering\n4=The matured leaves of plants in tropical rain forest are oval in shape\nThey possess whole margins and sharp or drippy tip. ");
        List<String> a4 = new ArrayList<String>();
        a4.add("The 3 function are:\n1-it will functions as a osmoregulatory organ\n2-it plays huge role in homeostatis\n3-it helps in a formation of urine");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), now);
        listDataChild.put(listDataHeader.get(2), a1);
        listDataChild.put(listDataHeader.get(3), a2);
        listDataChild.put(listDataHeader.get(4), a3);
        listDataChild.put(listDataHeader.get(5), a4);

    }

}
