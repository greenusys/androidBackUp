package com.example.digitalforgeco.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.Temperature_Adapter
import com.example.digitalforgeco.modal.Volume_Calc_History
import com.example.digitalforgeco.modal.Weight_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Volume_Calc_NoteRepository
import com.example.digitalforgeco.room_db_package.repository.Weight_Calc_NoteRepository
import com.example.digitalforgeco.widget.CanaroTextView
import com.example.menu_library.animation.GuillotineAnimation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Volume_Calculator_9 : AppCompatActivity() {
    internal lateinit var cm: Button
    internal lateinit var inch: Button
    internal lateinit var m3_button: ImageView
    internal lateinit var f3_button: ImageView
    internal lateinit var cm3_button: ImageView
    internal lateinit var length: EditText
    internal lateinit var breadth: EditText
    internal lateinit var height: EditText
    internal lateinit var result_name: CanaroTextView
    internal lateinit var result_value: CanaroTextView
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
    private var is_CM: Boolean = false
    private var is_Inch: Boolean = false
    private var result: Double? = 0.0

    internal var df = DecimalFormat("#.####")

    internal var history_rv: RecyclerView? = null
    internal var adapter: Temperature_Adapter? = null
    var history_list: ArrayList<Volume_Calc_History> = ArrayList()

    private var noteRepository: Volume_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_volume__calculator)


get_Today_Date()
        initVies()

    }


    private fun initVies() {

        history = findViewById(R.id.history)
        noteRepository = Volume_Calc_NoteRepository(applicationContext)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })

        length = findViewById(R.id.length)
        breadth = findViewById(R.id.breadth)
        height = findViewById(R.id.heights)
        cm = findViewById(R.id.cm)
        inch = findViewById(R.id.inch)
        result_name = findViewById(R.id.result_name)
        result_value = findViewById(R.id.result_value)

        m3_button = findViewById(R.id.m3_button)
        f3_button = findViewById(R.id.f3_button)
        cm3_button = findViewById(R.id.cm3_button)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }

        cm.setOnClickListener {
            if (length.text.toString().length > 0 && breadth.text.toString().length > 0 &&
                    height.text.toString().length > 0) {
                cm.setBackgroundResource(R.color.green)
                inch.setBackgroundResource(R.color.selected_item_color_2)

                is_CM = true
                is_Inch = false
                calculate_CM()

            } else {
                val rootView = window.decorView.rootView
                show_Snack_Bar("All fields are mandatory", rootView)
            }

        }

        inch.setOnClickListener {
            if (length.text.toString().length > 0 && breadth.text.toString().length > 0 &&
                    height.text.toString().length > 0) {
                inch.setBackgroundResource(R.color.green)
                cm.setBackgroundResource(R.color.selected_item_color_2)
                is_Inch = true
                is_CM = false
                calculate_Inch()
            } else {
                val rootView = window.decorView.rootView
                show_Snack_Bar("All fields are mandatory", rootView)
            }
        }

        m3_button.setOnClickListener { v ->
            if (length.text.toString().length > 0 && breadth.text.toString().length > 0 &&
                    height.text.toString().length > 0 && result != 0.0)
                calculate_M3()
            else {
                show_Snack_Bar("Please select unit", v)

            }
        }
        f3_button.setOnClickListener { v ->
            if (length.text.toString().length > 0 && breadth.text.toString().length > 0 &&
                    height.text.toString().length > 0 && result != 0.0)
                calculate_F3()
            else {

                show_Snack_Bar("Please select unit", v)

            }
        }
        cm3_button.setOnClickListener { v ->
            if (length.text.toString().length > 0 && breadth.text.toString().length > 0 &&
                    height.text.toString().length > 0 && result != 0.0)
                calculate_CM3()
            else {

                show_Snack_Bar("Please select unit", v)


            }

        }


    }


    private fun show_History(value: Boolean) {

        if (this@Volume_Calculator_9 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Volume_Calculator_9)
                val sheetView = LayoutInflater.from(this@Volume_Calculator_9).inflate(R.layout.bottom_sheet, null)

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

        if (this@Volume_Calculator_9 != null) {
            runOnUiThread {

                adapter = Temperature_Adapter(today_date as String, "volume", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Volume_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    private fun fetch_All_Record() {

        noteRepository!!.getALLVolume__Record().observe(this, object : Observer<List<Volume_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Volume_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)


                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Volume_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Volume_Calculator_9 != null) {
                                    this@Volume_Calculator_9.runOnUiThread(Runnable {
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
                    noteRepository!!.clear_Volume_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun insert_data_To_Hisotory_Table(date: String, length: String, width: String, height: String,title_plus_result:String) {

        println("Insert" + date + " " + length + " " + width + " " + height+" "+title_plus_result)
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Volume__Calc_Record(date, length,width,height,title_plus_result)

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


    private fun show_Snack_Bar(msg: String, view: View) {
        val snackbar: Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        val snackBarView = snackbar!!.view
        snackBarView.setBackgroundColor(Color.RED)
        val textView = snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 18f
        snackbar!!.show()

    }

    private fun calculate_M3() {


        if (is_CM) {
            //convert cm to meter
            val result_in_meter = result!! / 100
            result_name.text = "Meters Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "mt\u00B3"
        } else {
            val result_in_meter = result!! / 39.37
            result_name.text = "Meters Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "mt\u00B3"
        }





        insert_data_To_Hisotory_Table(
                today_date.toString(),
                length.text.toString(),
                breadth.text.toString(),
                height.text.toString(),
        result_name.text.toString()+"_"+result_value.text.toString()
        )



    }

    private fun calculate_F3() {

        if (is_CM) {
            //convert cm to feet
            val result_in_meter = result!! / 30.48
            result_name.text = "Feet Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "ft\u00B3"
        } else {
            val result_in_meter = result!! / 12
            result_name.text = "Feet Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "ft\u00B3"
        }

        insert_data_To_Hisotory_Table(
                today_date.toString(),
                length.text.toString(),
                breadth.text.toString(),
                height.text.toString(),
                result_name.text.toString()+"_"+result_value.text.toString()
        )
    }


    private fun calculate_CM3() {

        if (is_CM) {
            //convert inch to cm
            val result_in_meter = result
            result_name.text = "Centimeter Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "cm\u00B3"
        } else {
            val result_in_meter = result!! * 2.54
            result_name.text = "Centimeter Cube"
            result_value.text = "Volume:" + df.format(result_in_meter) + "cm\u00B3"
        }

        insert_data_To_Hisotory_Table(
                today_date.toString(),
                length.text.toString(),
                breadth.text.toString(),
                height.text.toString(),
                result_name.text.toString()+"_"+result_value.text.toString()
        )
    }

    private fun calculate_CM() {

        val len = length.text.toString()
        val bre = breadth.text.toString()
        val hei = height.text.toString()

        val lengths = java.lang.Double.parseDouble(len)
        val breadths = java.lang.Double.parseDouble(bre)
        val heights = java.lang.Double.parseDouble(hei)


        result = lengths * breadths * heights
        // Double result_in_meter = result / 100;

        result_name.text = "Centimeter Cubed"
        result_value.text = "Volume:" + result + "c\u33A5"

        insert_data_To_Hisotory_Table(
                today_date.toString(),
                length.text.toString(),
                breadth.text.toString(),
                height.text.toString(),
                result_name.text.toString()+"_"+result_value.text.toString()
        )


    }

    private fun calculate_Inch() {

        val len = length.text.toString()
        val bre = breadth.text.toString()
        val hei = height.text.toString()

        val lengths = java.lang.Double.parseDouble(len)
        val breadths = java.lang.Double.parseDouble(bre)
        val heights = java.lang.Double.parseDouble(hei)


        val result = lengths * breadths * heights
        // Double result_in_meter = result / 100;

        result_name.text = "Inch Cubed"
        result_value.text = "Feet:" + result + "inch\u00B3"//ft3

        insert_data_To_Hisotory_Table(
                today_date.toString(),
                length.text.toString(),
                breadth.text.toString(),
                height.text.toString(),
                result_name.text.toString()+"_"+result_value.text.toString()
        )


    }


}
