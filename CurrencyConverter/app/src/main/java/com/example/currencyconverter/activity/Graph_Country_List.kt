package com.example.currencyconverter.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.adapter.Graph_Currency_Adapter
import com.example.currencyconverter.modal.Country_Modal
import java.util.*

class Graph_Country_List : AppCompatActivity(),SearchView.OnQueryTextListener {

    internal lateinit var country_rv: RecyclerView
    internal lateinit var adapter: Graph_Currency_Adapter
    internal var country_list = ArrayList<Country_Modal>()
    internal var toolbar: Toolbar? = null
    private var title: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show__country)

        setCountryNames()
        initViews()

    }

    private fun initViews() {
        supportActionBar!!.setTitle("Choose Currency")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        country_rv = findViewById(R.id.country_rv)
        adapter = Graph_Currency_Adapter(this, country_list)
        country_rv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        country_rv.adapter = adapter
        adapter.notifyDataSetChanged()
        title = findViewById(R.id.title)


        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.title = null
        }

        toolbar = findViewById(R.id.toolbar)




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


        //gives currency codes of 3 letter
        val countries = getResources().getStringArray(R.array.currency);


        var i: Int = 1

        for (i in countries.indices) {

            try {
                country_list.add(Country_Modal("",
                        "https://www.countryflags.io/" + countries.get(i + 1).substring(0, 2) + "/flat/32.png",
                        countries.get(i + 1), ""))
            } catch (e: Exception) {

            }


        }

    }
    override fun onQueryTextSubmit(p0: String?): Boolean {

        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        adapter.filter.filter(newText)

        return true

    }



    fun back(view: View) {
        startActivity(Intent(applicationContext, GraphActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }


}

