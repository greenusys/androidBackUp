package com.greenusys.customerservice.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenusys.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 24-Aug-18.
 */

            public class ServiceAdapter extends ArrayAdapter<Service> {
                private Context context;
                private List<ServiceModel> serviceList = new ArrayList <>();
                String s ;


                public ServiceAdapter(@NonNull Context context, ArrayList list,String s) {
                    super(context, 0 , list);
                    this.context = context;
                    this.serviceList = list;
                    this.s = s;
                }
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View listitem = convertView;
                    if(listitem == null)
                    {
            listitem = LayoutInflater.from(context).inflate(R.layout.service_item,parent,false);
            ServiceModel serviceModel = serviceList.get(position);

            TextView textView = listitem.findViewById(R.id.name1);
            textView.setText(serviceModel.getName1());

            TextView textView1 = listitem.findViewById(R.id.name2);
            textView1.setText(serviceModel.getName2());


        }

        return listitem;
    }
}


