package com.example.currencyconverter.activity

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.Viewmodal.Global_Currency_VM
import com.example.currencyconverter.adapter.Global_Currency_Adapter
import com.example.currencyconverter.modal.Gloabl_Currency_Modal
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Global_Currency : AppCompatActivity() {

    val today_cur_list: ArrayList<String> = ArrayList<String>()
    val yesterday_cur_list: ArrayList<String> = ArrayList<String>()

    val today_date = Date()
    internal var shared: SharedPreferences? = null
    internal var sp: SharedPreferences.Editor? = null
    private var gloabl_rv: RecyclerView? = null
    private val multiple_cur_rv: RecyclerView? = null
    internal lateinit var adapter: Global_Currency_Adapter
    internal var global_list = ArrayList<Gloabl_Currency_Modal>()
    private var Viewmodel: Global_Currency_VM? = null
    var count: Int = 0
    private var progress: ProgressBar? = null

    var jsondata: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global__currency)

        initViews()


    }

    private fun initViews() {

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Global Currencies"


        Viewmodel = ViewModelProviders.of(this).get(Global_Currency_VM::class.java)


        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sp.edit()


        //set convert multiple currency adapter
        progress = findViewById(R.id.progress)
        gloabl_rv = findViewById(R.id.gloabl_rv)
        adapter = Global_Currency_Adapter(global_list, this)
        gloabl_rv!!.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        gloabl_rv!!.adapter = adapter




        progress!!.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);


        fetch_Global_Currency_Data(latest_currency)
        fetch_Global_Currency_Data(yesterday_currency)




        println("today_date" + today_date.toString())
        editor.putString("today_date", today_date.toString())
        editor.apply()

    }

    private fun fetch_Global_Currency_Data(url: String) {

        println("global_url" + url)

        Viewmodel!!.convert_Currency(url,false,"","").
                observe(this@Global_Currency, Observer { mainjson ->
            println("gloabl_currency$mainjson")

            ++count


            jsondata = jsondata + mainjson.toString() + "kaif"

            if (count == 2)
                CompareBothDataForGainerLooser(jsondata)


        })


    }

    private fun CompareBothDataForGainerLooser(jsondata: String) {

        println("jsondata" + jsondata)
        var data = jsondata.split("kaif")

        println("yesterday" + data[0])
        println("today" + data[1])
        var firstJson: JSONObject? = null
        var secondJson: JSONObject? = null

        val yesterday = data[0]
        val today = data[1]

        firstJson = JSONObject(today.toString())
        secondJson = JSONObject(yesterday.toString())


        var ratesFirst: JSONObject = secondJson.getJSONObject("rates")
        var ratesSecond: JSONObject = firstJson.getJSONObject("rates")


        today_cur_list.add(ratesFirst.getString("USD"))
        today_cur_list.add(ratesFirst.getString("EUR"))
        today_cur_list.add(ratesFirst.getString("INR"))
        today_cur_list.add(ratesFirst.getString("JPY"))
        today_cur_list.add(ratesFirst.getString("GBP"))
        today_cur_list.add(ratesFirst.getString("CHF"))
        today_cur_list.add(ratesFirst.getString("CAD"))
        today_cur_list.add(ratesFirst.getString("AUD"))
        today_cur_list.add(ratesFirst.getString("ZAR"))



        yesterday_cur_list.add(ratesSecond.getString("USD"))
        yesterday_cur_list.add(ratesSecond.getString("EUR"))
        yesterday_cur_list.add(ratesSecond.getString("INR"))
        yesterday_cur_list.add(ratesSecond.getString("JPY"))
        yesterday_cur_list.add(ratesSecond.getString("GBP"))
        yesterday_cur_list.add(ratesSecond.getString("CHF"))
        yesterday_cur_list.add(ratesSecond.getString("CAD"))
        yesterday_cur_list.add(ratesSecond.getString("AUD"))
        yesterday_cur_list.add(ratesSecond.getString("ZAR"))



       val check= today_cur_list.get(1).toDouble() >
                yesterday_cur_list.get(1).toDouble()


        println("kattu"+check)
        println("eur_today"+today_cur_list.get(1).toDouble())
        println("eur_yesterday"+yesterday_cur_list.get(1).toDouble())


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/US/flat/32.png",
                        "United States of America",
                        "USD",
                        today_cur_list.get(0), if(today_cur_list.get(0).toDouble() >
                        yesterday_cur_list.get(0).toDouble()) true else false
                ))



                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/EU/flat/32.png",
                        "France",
                        "EUR",
                        today_cur_list.get(1), if(today_cur_list.get(1).toDouble() >
                        yesterday_cur_list.get(1).toDouble()) true else false
                ))



                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/IN/flat/32.png",
                        "India",
                        "INR",
                        today_cur_list.get(2), if(today_cur_list.get(2).toDouble() >
                        yesterday_cur_list.get(2).toDouble()) true else false
                ))



                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/JP/flat/32.png",
                        "Japan",
                        "JPY",
                        today_cur_list.get(3), if(today_cur_list.get(3).toDouble() >
                        yesterday_cur_list.get(3).toDouble()) true else false
                ))


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/GB/flat/32.png",
                        "United Kingdom",
                        "GBP",
                        today_cur_list.get(4), if(today_cur_list.get(4).toDouble() >
                        yesterday_cur_list.get(4).toDouble()) true else false
                ))


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/CH/flat/32.png",
                        "Switzerland",
                        "CHF",
                        today_cur_list.get(5), if(today_cur_list.get(5).toDouble() >
                        yesterday_cur_list.get(5).toDouble()) true else false
                ))


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/CA/flat/32.png",
                        "Canada",
                        "CAD",
                        today_cur_list.get(6), if(today_cur_list.get(6).toDouble() >
                        yesterday_cur_list.get(6).toDouble()) true else false
                ))


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/AU/flat/32.png",
                        "Australia",
                        "AUD",
                        today_cur_list.get(7), if(today_cur_list.get(7).toDouble() >
                        yesterday_cur_list.get(7).toDouble()) true else false
                ))


                global_list.add(Gloabl_Currency_Modal(
                        "https://www.countryflags.io/ZA/flat/32.png",
                        "South Africa",
                        "ZAR",
                        today_cur_list.get(8),
                        if(today_cur_list.get(8).toDouble() >
                        yesterday_cur_list.get(8).toDouble()) true else false
                ))

        progress!!.visibility=View.GONE
        adapter!!.notifyDataSetChanged()



    }


    companion object {


        //give yesterday results
        internal val latest_currency = "https://api.exchangeratesapi.io/latest?base=USD"

        //give day yesterday results
        internal val yesterday_currency = "https://api.exchangeratesapi.io/" + getYesterdayDate() + "?base=USD"

        private fun getYesterdayDate(): String {

            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            var cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -2)

            println("day_yesterday_date" + dateFormat.format(cal.time))
            return dateFormat.format(cal.time)


        }
    }
}
