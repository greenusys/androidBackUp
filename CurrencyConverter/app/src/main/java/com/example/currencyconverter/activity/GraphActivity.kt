package com.example.currencyconverter.activity

/**
 * Created by Ryan on 8/11/2017.
 */

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.currencyconverter.R
import com.example.currencyconverter.Viewmodal.Global_Currency_VM
import com.example.currencyconverter.graph.MonthSlashDayDateFormatter
import com.example.currencyconverter.graph.MonthSlashYearFormatter
import com.example.currencyconverter.graph.TimeDateFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class GraphActivity : AppCompatActivity(), OnChartValueSelectedListener {
    var selected_value: String = "CAD"
    var countries: Array<String>? = null
    internal var graphlist: MutableList<Entry>? = ArrayList()
    val monthSlashDayXAxisFormatter: IAxisValueFormatter = MonthSlashDayDateFormatter()
    val dayCommaTimeDateFormatter = TimeDateFormatter()
    val monthSlashYearFormatter = MonthSlashYearFormatter()
    internal var fullDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    private var chartFillColor: Int = 0
    private var chartBorderColor: Int = 0
    private var percentageColor: Int = 0
    private var lineChart: LineChart? = null
    private var XAxisFormatter: IAxisValueFormatter? = null
    private val currentTimeWindow = ""
    private var buttonGroup: SingleSelectToggleGroup? = null
    private var chartProgressBar: ProgressBar? = null
    private val tsymbol: String? = null

    private var country_code: TextView? = null
    private var country_image: ImageView? = null

    private var Viewmodel: Global_Currency_VM? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph_main)


        try {

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "Global Currency Graph"



            country_code = findViewById(R.id.cur_name)
            country_image = findViewById(R.id.cur_image)

            initSpinner()

            lineChart = findViewById(R.id.chart)
            setUpChart()
            chartProgressBar = findViewById(R.id.chartProgressSpinner)

            Viewmodel = ViewModelProviders.of(this).get(Global_Currency_VM::class.java)

            buttonGroup = findViewById(R.id.chart_interval_button_grp)


            buttonGroup!!.check(R.id.weekButton)
            if(intent.getStringExtra("from")==null) {
                fetch_GraphData(country_code!!.text.toString(), "5d")
            }

            buttonGroup!!.setOnCheckedChangeListener { group, checkedId ->
                Calendar.getInstance()
                when (checkedId) {

                    R.id.weekButton -> {
                        chartProgressBar!!.visibility=View.VISIBLE
                        setWeekChecked(Calendar.getInstance())
                        fetch_GraphData(country_code!!.text.toString(), "5d")
                    }
                    R.id.monthButton -> {
                        chartProgressBar!!.visibility=View.VISIBLE
                        setMonthChecked(Calendar.getInstance())
                        fetch_GraphData(country_code!!.text.toString(), "1m")
                    }

                    R.id.yearButton -> {
                        chartProgressBar!!.visibility=View.VISIBLE
                        setYearChecked(Calendar.getInstance())
                        fetch_GraphData(country_code!!.text.toString(), "1y")
                    }
                    R.id.fiveyearButton -> {
                        chartProgressBar!!.visibility=View.VISIBLE
                        setAllTimeChecked()
                        fetch_GraphData(country_code!!.text.toString(), "5y")
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()

        if (intent.getStringExtra("country_code") != null) {

            country_code!!.setText(intent.getStringExtra("country_code"))

            Glide.with(applicationContext).load(intent.getStringExtra("country_image")).thumbnail(0.01f).into(country_image!!)

            setWeekChecked(Calendar.getInstance())
            fetch_GraphData(country_code!!.text.toString(), "5d")

        }
    }


    fun setColors(percentChange: Float) {
        if (percentChange >= 0) {
            chartFillColor = ResourcesCompat.getColor(resources, R.color.materialLightGreen, null)
            chartBorderColor = ResourcesCompat.getColor(resources, R.color.darkGreen, null)
            percentageColor = ResourcesCompat.getColor(resources, R.color.percentPositiveGreen, null)
        } else {
            chartFillColor = ResourcesCompat.getColor(resources, R.color.materialLightGreen, null)
            chartBorderColor = ResourcesCompat.getColor(resources, R.color.darkGreen, null)
            percentageColor = ResourcesCompat.getColor(resources, R.color.materialLightGreen, null)
        }
    }

    fun setUpChart() {
        val xAxis = lineChart!!.xAxis
        xAxis.setDrawAxisLine(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setAvoidFirstLastClipping(true)
        lineChart!!.axisLeft.isEnabled = true
        lineChart!!.axisLeft.setDrawGridLines(false)
        lineChart!!.xAxis.setDrawGridLines(false)
        lineChart!!.axisRight.isEnabled = false
        lineChart!!.legend.isEnabled = false
        lineChart!!.isDoubleTapToZoomEnabled = false
        lineChart!!.setScaleEnabled(false)
        lineChart!!.description.isEnabled = false
        lineChart!!.contentDescription = ""
        lineChart!!.setNoDataText(getString(R.string.noChartDataString))
        lineChart!!.setNoDataTextColor(R.color.darkRed)
        lineChart!!.setOnChartValueSelectedListener(this)

    }

    private fun fetch_GraphData(cur: String, dater: String) {

        println("fetchGraphURL"+cur+" "+dater)
        Viewmodel!!.convert_Currency("https://convertedcurrency.com/graph_api.php", true, cur, dater).observe(this@GraphActivity, Observer { mainjson -> prepareGraphData(mainjson) })

    }

    private fun prepareGraphData(mainjson: JSONObject) {


        if (graphlist != null)
            graphlist!!.clear()

        try {
            val main = JSONObject(mainjson.toString())
            if (main.getString("status") == "1") {
                val data = main.getJSONArray("data")

                for (i in 0 until data.length()) {
                    val item = data.getJSONObject(i)

                    val price = item.getString("price")
                    val date_time = item.getString("date_time")
                    graphlist!!.add(Entry(i.toFloat(),
                            java.lang.Float.parseFloat(price),
                            date_time))


                }
                getCMCChart()

            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }

    fun setUpLineDataSet(entries: List<Entry>): LineDataSet {

        println("close_size_after" + entries.size)


        val dataSet = LineDataSet(entries, "Price")
        dataSet.color = chartBorderColor
        dataSet.fillColor = chartFillColor
        dataSet.setDrawHighlightIndicators(true)
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(true)
        dataSet.setCircleColor(chartBorderColor)
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(false)
        dataSet.circleRadius = 1f
        dataSet.highlightLineWidth = 2f
        dataSet.isHighlightEnabled = true
        dataSet.setDrawHighlightIndicators(true)
        dataSet.highLightColor = chartBorderColor // color for highlight indicator
        return dataSet
    }

    fun getCMCChart() {
        val percentChangeText = findViewById<TextView>(R.id.percent_change)
        val currPriceText = findViewById<TextView>(R.id.current_price)
        lineChart!!.isEnabled = true
        lineChart!!.clear()

        //Log.d("DON", "URL: " + GraphActivity.CURRENT_CHART_URL + " " + cryptoID);

        val closePrices = ArrayList<Entry>()
        for (i in 0..49) {
            val random = Random().nextInt(100 - 33 + 1) + 25

            closePrices.add(Entry(i.toFloat(), random.toFloat()))
        }



        if (closePrices.size == 0) {
            lineChart!!.data = null
            lineChart!!.isEnabled = false
            lineChart!!.invalidate()
            percentChangeText.text = ""
            currPriceText.text = ""
            lineChart!!.setNoDataText(getString(R.string.noChartDataString))
            chartProgressBar!!.visibility = View.GONE
            return
        }
        val xAxis = lineChart!!.xAxis
        //xAxis.setValueFormatter(XAxisFormatter);
        val currentPriceTextView = findViewById<TextView>(R.id.current_price)
        val currPrice = closePrices[closePrices.size - 1].y
        val chartDateTextView = findViewById<TextView>(R.id.graphFragmentDateTextView)
        //chartDateTextView.setText(getFormattedFullDate(closePrices.get(closePrices.size() - 1).getX()));

        currentPriceTextView.setTextColor(Color.BLACK)

        println("katrina_current_price" + currentPriceTextView.text.toString())

        var firstPrice = closePrices[0].y
        for (e in closePrices) {
            if (firstPrice != 0f) {
                break
            } else {
                firstPrice = e.y
            }
        }
        val difference = currPrice - firstPrice
        val percentChange = difference / firstPrice * 100
        if (percentChange < 0) {

            percentChangeText.text = String.format(getString(R.string.negative_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference))

        } else {

            percentChangeText.text = String.format(getString(R.string.positive_variable_pct_change_with_dollars_format), currentTimeWindow, percentChange, Math.abs(difference))

        }
        setColors(percentChange)

        println("close_size_before" + closePrices.size)
        percentChangeText.setTextColor(percentageColor)

        /* List<Entry> kaiflist = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int random = new Random().nextInt((100 - 33) + 1) + 25;

            kaiflist.add(new Entry(i, random));
        }*/


        val dataSet = setUpLineDataSet(graphlist!!)
        val lineData = LineData(dataSet)
        lineChart!!.data = lineData
        lineChart!!.animateX(800)
        chartProgressBar!!.visibility = View.GONE
    }

    fun setDayChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        // CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        // currentTimeWindow = getString(R.string.oneDay);
        XAxisFormatter = dayCommaTimeDateFormatter
    }

    fun setWeekChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, -7)
        val startTime = cal.timeInMillis
        cal.clear()
        //CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        // currentTimeWindow = getString(R.string.Week);
        XAxisFormatter = monthSlashDayXAxisFormatter

    }

    fun setMonthChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.MONTH, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        // CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        //currentTimeWindow = getString(R.string.Month);
        XAxisFormatter = monthSlashDayXAxisFormatter
    }

    fun setThreeMonthChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.MONTH, -3)
        val startTime = cal.timeInMillis
        cal.clear()
        //CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        // currentTimeWindow = getString(R.string.threeMonth);
        XAxisFormatter = monthSlashDayXAxisFormatter
    }

    fun setYearChecked(cal: Calendar) {
        val endTime = cal.timeInMillis
        cal.add(Calendar.YEAR, -1)
        val startTime = cal.timeInMillis
        cal.clear()
        // CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_WINDOW, cryptoID, startTime, endTime);
        // currentTimeWindow = getString(R.string.Year);
        // println("yearlyka" + CURRENT_CHART_URL!!)


        XAxisFormatter = monthSlashYearFormatter
    }

    fun setAllTimeChecked() {
        //currentTimeWindow = getString(R.string.AllTime);
        //CURRENT_CHART_URL = String.format(COIN_MARKETCAP_CHART_URL_ALL_DATA, cryptoID);
        XAxisFormatter = monthSlashYearFormatter
    }

    private fun initSpinner() {
        countries = getResources().getStringArray(R.array.currency);


    }


    override fun onValueSelected(e: Entry, h: Highlight) {
        val currentPrice = findViewById<TextView>(R.id.current_price)
        val dateTextView = findViewById<TextView>(R.id.graphFragmentDateTextView)

        currentPrice.text =country_code!!.text.toString()+" "+e.y.toString()




        try {

            val format = SimpleDateFormat("yyyy-MM-dd")

            val dateString = format.format(Date())
            val date = format.parse(e.data.toString())
            dateTextView.text = date.toString()

        } catch (e2: ParseException) {
            e2.printStackTrace()
        }

    }

    override fun onNothingSelected() {

    }

    fun getFormattedFullDate(unixSeconds: Float): String {
        val date = Date(unixSeconds.toLong())
        return fullDateFormat.format(date)
    }


    companion object {

        var CURRENT_CHART_URL: String? = null
    }

    fun select_country(view: View) {

        startActivity(Intent(applicationContext, Graph_Country_List::class.java))

    }


}
