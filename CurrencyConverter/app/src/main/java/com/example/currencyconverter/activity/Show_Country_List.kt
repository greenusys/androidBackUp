package com.example.currencyconverter.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.currencyconverter.R
import com.example.currencyconverter.adapter.Country_Adapter
import com.example.currencyconverter.modal.Country_Modal
import com.example.currencyconverter.modal.Data
import com.example.menu_library.animation.GuillotineAnimation

import java.util.ArrayList
import android.R.menu
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Show_Country_List : AppCompatActivity(),SearchView.OnQueryTextListener {


    internal lateinit var country_rv: RecyclerView
    internal lateinit var adapter: Country_Adapter
    internal var country_list = ArrayList<Country_Modal>()
    internal var toolbar: Toolbar? = null
    internal lateinit var root: FrameLayout
    private var value: String? = null
    private var title: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show__country)

        value = intent.getStringExtra("value")
        setCountryNames()
        initViews()

    }

    private fun initViews() {


        supportActionBar!!.setTitle("Choose Currency")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        country_rv = findViewById(R.id.country_rv)
        adapter = Country_Adapter(this, country_list, value!!)
        country_rv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        country_rv.adapter = adapter
        adapter.notifyDataSetChanged()


        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.title = null
        }

        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.title)
        root = findViewById(R.id.root)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu, this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)

        var searchItem = menu.findItem(R.id.app_bar_search)
       var searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint="Choose Curency"

        searchView.setOnQueryTextListener(this)

        return true
    }


    fun setCountryNames() {

        for (i in Data.country_name.indices) {     //old one
            //country_list.add(new Country_Modal(Data.country_name[i],"https://www.countryflags.io/"+Data.country_code[i].substring(0,2)+"/flat/32.png",Data.country_code[i]));
            country_list.add(Country_Modal(Data.country_name[i], "https://www.countryflags.io/" + Data.country_code1[i] + "/flat/32.png", Data.country_code[i], Data.country_code1[i]))

        }

    }


    fun back(view: View) {
        startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }



    override fun onQueryTextSubmit(p0: String?): Boolean {

        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        adapter.filter.filter(newText)

        return true

    }
}

