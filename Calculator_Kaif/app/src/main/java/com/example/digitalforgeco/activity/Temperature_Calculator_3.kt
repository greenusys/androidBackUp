package com.example.digitalforgeco.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.Temperature_Adapter
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Temperature_Calc_NoteRepository
import com.example.menu_library.animation.GuillotineAnimation
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Temperature_Calculator_3 : AppCompatActivity() {


    internal lateinit var farhen: EditText
    internal lateinit var celsius: EditText
    internal lateinit var kelvin: EditText
    // internal lateinit var calculate: Button


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
    private var isGuillotineOpened = false
    internal lateinit var aniMenu: GuillotineAnimation
    internal var df = DecimalFormat("#.####")

    internal var history_rv: RecyclerView? = null
    internal var adapter: Temperature_Adapter? = null
    var history_list: ArrayList<Temperature_Calc_History> = ArrayList()

    private var noteRepository: Temperature_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature__calculator)


        get_Today_Date()
        initVies()

    }


    private fun initVies() {

        history = findViewById(R.id.history)
        noteRepository = Temperature_Calc_NoteRepository(applicationContext)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })


        farhen = findViewById(R.id.farhen)
        celsius = findViewById(R.id.celsius)
        kelvin = findViewById(R.id.kelvin)
        //calculate = findViewById(R.id.calculate)

        farhen.addTextChangedListener(GenericTextWatcher(farhen))
        celsius.addTextChangedListener(GenericTextWatcher(celsius))
        kelvin.addTextChangedListener(GenericTextWatcher(kelvin))



        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }


    }


    private fun show_History(value: Boolean) {

        if (this@Temperature_Calculator_3 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Temperature_Calculator_3)
                val sheetView = LayoutInflater.from(this@Temperature_Calculator_3).inflate(R.layout.bottom_sheet, null)

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

        if (this@Temperature_Calculator_3 != null) {
            runOnUiThread {

                adapter = Temperature_Adapter(today_date as String, "temperature", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Temperature_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    private fun fetch_All_Record() {

        noteRepository!!.getALL_Temperature__Record().observe(this, object : Observer<List<Temperature_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Temperature_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)
                        0

                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Temperature_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Temperature_Calculator_3 != null) {
                                    this@Temperature_Calculator_3.runOnUiThread(Runnable {
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
                    noteRepository!!.clear_Temperature__History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun insert_data_To_Hisotory_Table(date: String, farhen: String, celcius: String, kelvin: String) {

        println("Insert" + date + " " + farhen + " " + celcius + " " + kelvin)
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Temperature__Calc_Record(date, farhen, celcius, kelvin)

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


    private fun convert_Fahrenhei_To_Others(f: CharSequence) {

        val farhen = java.lang.Double.parseDouble(f.toString())

        val celsius_result = (farhen - 32) * 5 / 9
        celsius.hint = "" + df.format(celsius_result)


        val kelvin_result = (farhen - 32) * 5 / 9 + 273.15
        kelvin.hint = "" + df.format(kelvin_result)

        insert_data_To_Hisotory_Table(today_date.toString(), f.toString(), celsius.hint.toString(), kelvin.hint.toString())


    }


    private fun convert_celsium_To_Others(c: CharSequence) {
        val celsius = java.lang.Double.parseDouble(c.toString())

        val farhen_result = celsius * 9 / 5 + 32

        farhen.hint = "" + df.format(farhen_result)

        val kelvin_result = celsius + 273.15
        kelvin.hint = "" + df.format(kelvin_result)

        insert_data_To_Hisotory_Table(today_date.toString(), farhen.hint.toString(), c.toString(), kelvin.hint.toString())

    }


    private fun convert_kelvin_To_Others(k: CharSequence) {

        val kelvin = java.lang.Double.parseDouble(k.toString())

        val farhen_result = (kelvin - 273.15) * 9 / 5 + 32
        farhen.hint = "" + df.format(farhen_result)

        val celsius_result = kelvin - 273.15
        celsius.hint = "" + df.format(celsius_result)

        insert_data_To_Hisotory_Table(today_date.toString(), farhen.hint.toString(), celsius.hint.toString(), k.toString())

    }


    fun convert_value(value: String) {

        if (value.equals("f", ignoreCase = true))
            convert_Fahrenhei_To_Others(farhen.text.toString())
        else if (value.equals("c", ignoreCase = true))
            convert_celsium_To_Others(celsius.text.toString())
        else if (value.equals("k", ignoreCase = true))
            convert_kelvin_To_Others(kelvin.text.toString())


        //calculate.hint = "Clear"

    }


    private inner class GenericTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                //first row
                R.id.farhen ->

                    if (editable.length > 0)
                        convert_Fahrenhei_To_Others(editable)
                    else {
                        farhen.hint = ""
                        celsius.hint = ""
                        kelvin.hint = ""
                    }

                R.id.celsius -> if (editable.length > 0)
                    convert_celsium_To_Others(editable)
                else {
                    farhen.hint = ""
                    celsius.hint = ""
                    kelvin.hint = ""
                }

                R.id.kelvin -> if (editable.length > 0)
                    convert_kelvin_To_Others(editable)
                else {
                    farhen.hint = ""
                    celsius.hint = ""
                    kelvin.hint = ""
                }
            }
        }
    }


}
