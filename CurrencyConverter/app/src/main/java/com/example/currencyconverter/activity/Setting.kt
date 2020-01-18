package com.example.currencyconverter.activity

import android.os.Bundle
import android.view.View

import com.example.currencyconverter.R
import com.example.currencyconverter.widget.BaseActivity

class Setting : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, Theme_Fragment())
                .commit()

    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }


    fun back(view: View) {

        onBackPressed()
    }
}