package com.greenusys.allen.vidyadashboard;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
;
import android.widget.ExpandableListView;


import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapterQues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mathsques extends AppCompatActivity {
    ExpandableListAdapterQues listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathsques);

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
        listDataHeader.add("Ques-1 if 9 cos x-7=1 and 0 degree<=x<=90degree,find x");
        listDataHeader.add("Ques-2 Out of 30 candiatees applying for a post 17 have degrees,15 diplomas and 4 neither degree nor diploma.how many them have both?");
        listDataHeader.add("Ques-3 How many numbers between 75 and 500 are divisible by 7?");
        listDataHeader.add("Ques-4 IN a class of 50 students ,30 offered history,15 offered Hisory and Geography  while 3 did not offer any of the 2 subjects");
        listDataHeader.add("Ques-5 Given thar x is an int,find the three greatest values of x which satisfy the inequality  7x<2x-13");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("rearranging the eqn results in cos x = 0.8889 and from mathematical tables,it showed x=27.27o. ");
        List<String> now = new ArrayList<String>();
        now.add("15+y-y+17-y+4=30 which gave y=6 ");
        List<String> a1 = new ArrayList<String>();
        a1.add("61.)");
        List<String> a2 = new ArrayList<String>();
        a2.add("17");
        List<String> a3 = new ArrayList<String>();
        a3.add("7x-2x<-13 gave x<-23/5 the three greatest inegers were -3,-4 and -5");



        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), now);
        listDataChild.put(listDataHeader.get(2), a1);
        listDataChild.put(listDataHeader.get(3), a2);

    }
}

