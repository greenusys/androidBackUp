package com.greenusys.customerservice.activity;

import android.content.Context;

/**
 * Created by admin on 24-Aug-18.
 */

public class ServiceModel {
    Context context;
    String name1;
    String name2;

    public ServiceModel(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;

    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
