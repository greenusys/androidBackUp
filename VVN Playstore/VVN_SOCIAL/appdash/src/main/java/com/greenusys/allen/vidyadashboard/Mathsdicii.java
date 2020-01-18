package com.greenusys.allen.vidyadashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.greenusys.allen.vidyadashboard.Adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mathsdicii extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathsdicii);
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
        listDataHeader.add("Constant");
        listDataHeader.add("Modulus");
        listDataHeader.add("Absolute number");
        listDataHeader.add("Acute Triangle");
        listDataHeader.add("Isosceles Triangle");
        listDataHeader.add("Obtuse Triangle");

        listDataHeader.add("Ratio");
        List<String> a5 = new ArrayList<String>();
        a5.add("Ratio in mathematics is use to compare 2 quanties");

        listDataHeader.add("Rational no");
        List<String> a6 = new ArrayList<String>();
        a6.add(" it is no that can be written as a ratio of 2 integer no ex-8.9");

        listDataHeader.add("Natural no");
        List<String> a7 = new ArrayList<String>();
        a7.add("they are nos use in iunting ex-60 people");

        listDataHeader.add("Sets");
        List<String> a8 = new ArrayList<String>();
        a8.add("It is a collection of objects .The objects of a Set is refer to as the elements of the set");

        listDataHeader.add("Surds");
        List<String> a9 = new ArrayList<String>();
        a9.add("Square root of irratinal no");


        listDataHeader.add("Side");
        List<String> a10= new ArrayList<String>();
        a10.add("it is use to describe a line that makes up a plane or geometric shape");

        listDataHeader.add("Point");
        List<String> a11 = new ArrayList<String>();
        a11.add("Point is a location");

        listDataHeader.add("Vertex");
        List<String> a12 = new ArrayList<String>();
        a12.add("Vertex is a point where 2 or more line meets");

        listDataHeader.add("Matrix");
        List<String> a13 = new ArrayList<String>();
        a13.add("It is an array of no usually arranged in rows and cols. ");

        listDataHeader.add("Series ");
        List<String> a14 = new ArrayList<String>();
        a14.add("It is the sumof terms of a sequence");

        listDataHeader.add("Series ");
        List<String> a15 = new ArrayList<String>();
        a15.add("It is the sum of terms of a sequence");


        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("A Constant in mathematics is a fixed and non varying value ,usually a real no or a letter ");
        List<String> now = new ArrayList<String>();
        now.add("a complex no is regarded as a combination of real and imaginary no");
        List<String> a1 = new ArrayList<String>();
        a1.add("A absolute no is the non negative value of a no without considering its negative sign the abs of -7 is 7 ");
        List<String> a2 = new ArrayList<String>();
        a2.add("an acute triangle is a triangle with its three angles less than 90 degrees");
        List<String> a3 = new ArrayList<String>();
        a3.add("it is a triangle with 2 equal sides and one side with different length");

        List<String> a4 = new ArrayList<String>();
        a4.add("It is a form of triangle that contains one angle hat is greater than 90 degrees");





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



    }

}
