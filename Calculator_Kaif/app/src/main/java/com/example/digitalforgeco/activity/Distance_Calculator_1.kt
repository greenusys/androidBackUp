package com.example.digitalforgeco.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.Nullable

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.digitalforgeco.R
import com.example.digitalforgeco.activity.Distance_Calculator_1.GenericTextWatcher
import com.example.digitalforgeco.adapter.Temperature_Adapter
import com.example.digitalforgeco.modal.Distance_Calc_History
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Distance_Calc_NoteRepository
import com.example.digitalforgeco.room_db_package.repository.Temperature_Calc_NoteRepository
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import com.google.android.material.bottomsheet.BottomSheetDialog

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Distance_Calculator_1 : AppCompatActivity() {


    internal lateinit var miles: EditText
    internal lateinit var km: EditText
    internal lateinit var meters: EditText
    internal lateinit var feet: EditText
    internal lateinit var inches: EditText
    internal var df = DecimalFormat("#.####")

    private val calculate: Button? = null
    private val sum = 0.0

    //for menu layout
    internal lateinit var home: LinearLayout
    internal lateinit var distance_1: LinearLayout
    internal lateinit var percentage_2: LinearLayout
    internal lateinit var temperature_3: LinearLayout
    internal lateinit var weight_4: LinearLayout
    internal lateinit var length_5: LinearLayout
    internal lateinit var simple_6: LinearLayout
    internal lateinit var scientific_7: LinearLayout
    internal lateinit var volume_8: LinearLayout
    private var isGuillotineOpened: Boolean = false
    internal lateinit var aniMenu: GuillotineAnimation

    internal var history_rv: RecyclerView? = null
    internal var adapter: Temperature_Adapter? = null
    var history_list: ArrayList<Distance_Calc_History> = ArrayList()

    private var noteRepository: Distance_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance__calculator)

        get_Today_Date()
        initVies()


    }


    private fun initVies() {


        history = findViewById(R.id.history)
        noteRepository = Distance_Calc_NoteRepository(applicationContext)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })


        miles = findViewById(R.id.miles)
        km = findViewById(R.id.km)
        meters = findViewById(R.id.meters)
        feet = findViewById(R.id.feet)
        inches = findViewById(R.id.inches)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        miles.addTextChangedListener(GenericTextWatcher(miles))
        km.addTextChangedListener(GenericTextWatcher(km))
        meters.addTextChangedListener(GenericTextWatcher(meters))
        feet.addTextChangedListener(GenericTextWatcher(feet))
        inches.addTextChangedListener(GenericTextWatcher(inches))


    }


    private fun show_History(value: Boolean) {

        if (this@Distance_Calculator_1 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Distance_Calculator_1)
                val sheetView = LayoutInflater.from(this@Distance_Calculator_1).inflate(R.layout.bottom_sheet, null)

                history_rv = sheetView.findViewById(R.id.history_rv)
                val not_history_found: LinearLayout = sheetView.findViewById(R.id.not_history_found)
                val clear_history: TextView = sheetView.findViewById(R.id.clear_history)


                //no history found
                if (value) {
                    not_history_found!!.visibility = View.VISIBLE
                    clear_history!!.visibility = View.GONE
                } else {
                    not_history_found!!.visibility = View.GONE
                    set_Data_To_RecycylerView()

                }

                clear_history.setOnClickListener(View.OnClickListener {
                    sheetDialog.dismiss()
                    clear_History()

                })



                sheetDialog.dismiss()
                sheetDialog.setContentView(sheetView)
                sheetDialog.show()

            }
        }


    }


    @SuppressLint("WrongConstant")
    private fun set_Data_To_RecycylerView() {

        if (this@Distance_Calculator_1 != null) {
            runOnUiThread {

                adapter = Temperature_Adapter(today_date as String, "distance", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Distance_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    private fun fetch_All_Record() {

        noteRepository!!.getALL_Distance__Record().observe(this, object : Observer<List<Distance_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Distance_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)
                        0

                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Distance_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Distance_Calculator_1 != null) {
                                    this@Distance_Calculator_1.runOnUiThread(Runnable {
                                        history_list.clear()
                                        show_History(true)

                                    })
                                }

                            }
                        }

                        fetch_All = false


                        return null
                    }
                }.execute()


            }
        })
    }


    private fun clear_History() {


        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.clear_Distance__History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun insert_data_To_Hisotory_Table(date: String, miles: String, km: String, meters: String, feet: String, inches: String) {

        println("Insert" + date + " " + miles + " " + km + " " + meters+" "+feet+" "+inches)
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Distance__Calc_Record(date, miles,km,meters,feet,inches)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }

    private fun get_Today_Date(): String {
        val date = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        today_date = formatter.format(date)

        println("today_date" + today_date)

        return today_date as String

    }



    private inner class GenericTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                //first row
                R.id.miles ->

                    if (editable.length > 0)
                        convert_Miles_to_Others(editable)
                    else {
                        miles.hint = ""
                        km.hint = ""
                        meters.hint = ""
                        feet.hint = ""
                        inches.hint = ""
                    }

                R.id.km -> if (editable.length > 0)
                    convert_km_to_Others(editable)
                else {
                    miles.hint = ""
                    km.hint = ""
                    meters.hint = ""
                    feet.hint = ""
                    inches.hint = ""
                }

                R.id.meters -> if (editable.length > 0)
                    convert_meters_to_Others(editable)
                else {
                    miles.hint = ""
                    km.hint = ""
                    meters.hint = ""
                    feet.hint = ""
                    inches.hint = ""
                }

                R.id.feet -> if (editable.length > 0)
                    convert_feet_to_Others(editable)
                else {
                    miles.hint = ""
                    km.hint = ""
                    meters.hint = ""
                    feet.hint = ""
                    inches.hint = ""
                }

                R.id.inches -> if (editable.length > 0)
                    convert_inches_to_Others(editable)
                else {
                    miles.hint = ""
                    km.hint = ""
                    meters.hint = ""
                    feet.hint = ""
                    inches.hint = ""
                }
            }
        }
    }


    private fun convert_Miles_to_Others(Miles: CharSequence) {
        println("miles")

        val miles = java.lang.Double.parseDouble(Miles.toString())

        //km
        val km_result = miles * 1.609
        km.hint = "" + df.format(km_result)

        //meters
        val meters_result = miles * 1609.3
        meters.hint = "" + df.format(meters_result)

        //feet
        val feet_result = miles * 5280
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = miles * 63360
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                Miles.toString(),
                km.hint.toString(),
                meters.hint.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )



    }


    private fun convert_km_to_Others(KM: CharSequence) {

        println("km")

        val km = java.lang.Double.parseDouble(KM.toString())

        //mile
        val mile_result = km / 1.609
        miles.hint = "" + df.format(mile_result)

        //meters
        val meters_result = km * 1000
        meters.hint = "" + df.format(meters_result)

        //feet
        val feet_result = km * 3280.8
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = km * 39370.07
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                miles.hint.toString(),
                km.toString(),
                meters.hint.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )


    }


    private fun convert_meters_to_Others(meters: CharSequence) {

        println("meters")

        val meter = java.lang.Double.parseDouble(meters.toString())


        //mile
        val mile_result = meter / 1609.34
        miles.hint = "" + df.format(mile_result)

        //km
        val km_result = meter / 0.0001
        km.hint = "" + df.format(km_result)

        //feet
        val feet_result = meter * 3.28
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = meter * 39.37
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                miles.hint.toString(),
                km.hint .toString(),
                meters.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )


    }


    private fun convert_feet_to_Others(fee: CharSequence) {

        println("feet")

        val feets = java.lang.Double.parseDouble(fee.toString())


        //mile
        val mile_result = feets / 5280
        miles.hint = "" + df.format(mile_result)

        //km
        val km_result = feets / 3280.8
        km.hint = "" + df.format(km_result)

        //meters
        val meters_result = feets / 3.281
        meters.hint = "" + df.format(meters_result)

        //inches
        val inches_result = feets * 12
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                miles.hint.toString(),
                km.hint .toString(),
                meters.toString(),
                feets.toString(),
                inches.hint.toString()

        )


    }


    private fun convert_inches_to_Others(inc: CharSequence) {

        println("inches")

        val inchs = java.lang.Double.parseDouble(inc.toString())


        //mile
        val mile_result = inchs / 63360
        miles.hint = "" + df.format(mile_result)

        //km
        val km_result = inchs / 39370.079
        km.hint = "" + df.format(km_result)

        //meters
        val meters_result = inchs / 39.37
        meters.hint = "" + df.format(meters_result)

        //feet
        val feet_result = inchs / 12
        feet.hint = "" + df.format(feet_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                miles.hint.toString(),
                km.hint .toString(),
                meters.toString(),
                feet.hint.toString(),
                inchs.toString()

        )

    }



}
