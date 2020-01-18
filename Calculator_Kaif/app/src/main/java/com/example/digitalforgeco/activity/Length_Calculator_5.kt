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
import com.example.digitalforgeco.activity.Length_Calculator_5.GenericTextWatcher
import com.example.digitalforgeco.adapter.Temperature_Adapter
import com.example.digitalforgeco.modal.Distance_Calc_History
import com.example.digitalforgeco.modal.Length_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Distance_Calc_NoteRepository
import com.example.digitalforgeco.room_db_package.repository.Length_Calc_NoteRepository
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import com.google.android.material.bottomsheet.BottomSheetDialog

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Length_Calculator_5 : AppCompatActivity() {
    internal var calculate: Button? = null
    internal lateinit var meters: EditText
    internal lateinit var centimeters: EditText
    internal lateinit var mm: EditText
    internal lateinit var feet: EditText
    internal lateinit var inches: EditText
    internal var df = DecimalFormat("#.####")
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
    internal lateinit var aniMenu: GuillotineAnimation
    private var isGuillotineOpened: Boolean = false

    internal var history_rv: RecyclerView? = null
    internal var adapter: Temperature_Adapter? = null
    var history_list: ArrayList<Length_Calc_History> = ArrayList()

    private var noteRepository: Length_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_length__calculator)

        get_Today_Date()
        initVies()


    }


    private fun initVies() {

        history = findViewById(R.id.history)
        noteRepository = Length_Calc_NoteRepository(applicationContext)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })

        meters = findViewById(R.id.meter)
        centimeters = findViewById(R.id.centimeter)
        mm = findViewById(R.id.mm)
        feet = findViewById(R.id.feet)
        inches = findViewById(R.id.inches)

        meters.addTextChangedListener(GenericTextWatcher("meter"))
        centimeters.addTextChangedListener(GenericTextWatcher("cm"))
        mm.addTextChangedListener(GenericTextWatcher("mm"))
        feet.addTextChangedListener(GenericTextWatcher("feet"))
        inches.addTextChangedListener(GenericTextWatcher("inches"))


        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }
    }


    private fun show_History(value: Boolean) {

        if (this@Length_Calculator_5 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Length_Calculator_5)
                val sheetView = LayoutInflater.from(this@Length_Calculator_5).inflate(R.layout.bottom_sheet, null)

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

        if (this@Length_Calculator_5 != null) {
            runOnUiThread {

                adapter = Temperature_Adapter(today_date as String, "length", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Length_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    private fun fetch_All_Record() {

        noteRepository!!.getALL_Length__Record().observe(this, object : Observer<List<Length_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Length_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)


                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Length_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Length_Calculator_5 != null) {
                                    this@Length_Calculator_5.runOnUiThread(Runnable {
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
                    noteRepository!!.clear_Length_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun insert_data_To_Hisotory_Table(date: String, meters: String, cm: String, mm: String, feet: String, inches: String) {

        println("Insert" + date + " " + meters + " " + cm + " " + mm+" "+feet+" "+inches)
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Length__Calc_Record(date, meters,cm,mm,feet,inches)

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



    private fun convert_meters_to_Other(meter: CharSequence) {

        val meters = java.lang.Double.parseDouble(meter.toString())


        //cm
        val cm_result = meters * 100
        centimeters.hint = "" + df.format(cm_result)

        //mm
        val mm_result = meters * 1000
        mm.hint = "" + df.format(mm_result)

        //feet
        val feet_result = meters * 3.2810
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = meters * 39.3700
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                meter.toString(),
                centimeters.hint.toString(),
                mm.hint.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )

    }

    private fun convert_cm_to_Others(cm: CharSequence) {

        val cms = java.lang.Double.parseDouble(cm.toString())

        //meters
        val meter_result = cms / 100
        meters.hint = "" + df.format(meter_result)

        //mm
        val mm_result = cms * 10
        mm.hint = "" + df.format(mm_result)

        //feet
        val feet_result = cms / 30.48
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = cms / 2.54
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                meters.hint.toString(),
                cm.toString(),
                mm.hint.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )

    }

    private fun convert_mm_to_Others(mm3: CharSequence) {

        val mms = java.lang.Double.parseDouble(mm3.toString())

        //meters
        val meter_result = mms / 1000
        meters.hint = "" + df.format(meter_result)

        //cm
        val cm_result = mms / 10
        centimeters.hint = "" + df.format(cm_result)

        //feet
        val feet_result = mms / 304.8
        feet.hint = "" + df.format(feet_result)

        //inches
        val inches_result = mms / 25.4
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                meters.hint.toString(),
                centimeters.hint.toString(),
                mm3.toString(),
                feet.hint.toString(),
                inches.hint.toString()

        )


    }

    private fun convert_feet_to_Others(feetss: CharSequence) {


        val feets = java.lang.Double.parseDouble(feetss.toString())

        //meters
        val meter_result = feets / 3.281
        meters.hint = "" + df.format(meter_result)

        //cm
        val cm_result = feets * 30.48
        centimeters.hint = "" + df.format(cm_result)

        //mm
        val mm_result = feets * 304.8
        mm.hint = "" + df.format(mm_result)

        //inches
        val inches_result = feets * 12
        inches.hint = "" + df.format(inches_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                meters.hint.toString(),
                centimeters.hint.toString(),
                mm.hint.toString(),
                feetss.toString(),
                inches.hint.toString()

        )

    }

    private fun convert_inches_to_Others(inchesss: CharSequence) {


        val inches = java.lang.Double.parseDouble(inchesss.toString())

        //meters
        val meter_result = inches / 39.37
        meters.hint = "" + df.format(meter_result)

        //cm
        val cm_result = inches * 2.54
        centimeters.hint = "" + df.format(cm_result)

        //mm
        val mm_result = inches * 25.4
        mm.hint = "" + df.format(mm_result)

        //feet
        val feet_result = inches / 12
        feet.hint = "" + df.format(feet_result)



        insert_data_To_Hisotory_Table(today_date.toString(),
                meters.hint.toString(),
                centimeters.hint.toString(),
                mm.hint.toString(),
                feet.hint.toString(),
                inchesss.toString()

        )


    }




    private inner class GenericTextWatcher(private val view: String) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            //first row
            if (view == "meter") {
                if (editable.length > 0)
                    convert_meters_to_Other(editable)
                else {
                    meters.hint = ""
                    centimeters.hint = ""
                    mm.hint = ""
                    feet.hint = ""
                    inches.hint = ""

                }
            } else if (view == "cm") {
                if (editable.length > 0)
                    convert_cm_to_Others(editable)
                else {
                    meters.hint = ""
                    centimeters.hint = ""
                    mm.hint = ""
                    feet.hint = ""
                    inches.hint = ""

                }
            } else if (view == "mm") {
                if (editable.length > 0)
                    convert_mm_to_Others(editable)
                else {
                    meters.hint = ""
                    centimeters.hint = ""
                    mm.hint = ""
                    feet.hint = ""
                    inches.hint = ""

                }
            } else if (view == "feet") {
                if (editable.length > 0)
                    convert_feet_to_Others(editable)
                else {
                    meters.hint = ""
                    centimeters.hint = ""
                    mm.hint = ""
                    feet.hint = ""
                    inches.hint = ""

                }
            } else if (view == "inches") {
                if (editable.length > 0)
                    convert_inches_to_Others(editable)
                else {
                    meters.hint = ""
                    centimeters.hint = ""
                    mm.hint = ""
                    feet.hint = ""
                    inches.hint = ""

                }
            }
        }
    }



}
