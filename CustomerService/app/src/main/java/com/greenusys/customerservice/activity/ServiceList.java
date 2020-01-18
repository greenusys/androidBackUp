package com.greenusys.customerservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenusys.customerservice.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.greenusys.customerservice.activity.Service.name11;
import static com.greenusys.customerservice.activity.Service.name22;

public class ServiceList extends AppCompatActivity {
    private ListView listView;
    private ServiceAdapter serviceAdapter;
    private  ServiceModel serviceModel;
    String s;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
         //Snackbar.make(ServiceList.this,"sdf",Snackbar.LENGTH_SHORT).show();
        listView = findViewById(R.id.listservice);
        ArrayList<ServiceModel> serviceArrayList =  new ArrayList <>();
        for(int i = 0; i< name11.length; i++)
        {
            serviceArrayList.add(
                    new ServiceModel(name11[i],Service.name22[i]));
        }

        serviceAdapter= new ServiceAdapter(this,serviceArrayList,"hell");
        listView.setAdapter(serviceAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                        ServiceModel serviceModel = serviceArrayList.get(position);

                        String z = serviceModel.getName2().toString();
                        Toast.makeText(ServiceList.this, z.toString(), Toast.LENGTH_SHORT).show();

                        Intent send = new Intent(ServiceList.this,Customer_Payu.class);
                        send.putExtra("12",z);
                        startActivity(send);

                        /*String b,a= "hello";
                        int x = 0;
                        ServiceModel serviceModel1 = serviceArrayList.get(position);
                         String c = serviceModel1.getName2();
                         Integer integer = Integer.parseInt(c);
                         x = integer/200;*/

                        //Toast.makeText(ServiceList.this, String.valueOf(x), Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }

}
