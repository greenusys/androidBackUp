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
import com.example.digitalforgeco.activity.Weight_Calculator_4.GenericTextWatcher
import com.example.digitalforgeco.adapter.Temperature_Adapter
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.modal.Weight_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Temperature_Calc_NoteRepository
import com.example.digitalforgeco.room_db_package.repository.Weight_Calc_NoteRepository
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import com.google.android.material.bottomsheet.BottomSheetDialog

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Weight_Calculator_4 : AppCompatActivity() {

    internal var df = DecimalFormat("#.####")
    internal lateinit var kg: EditText
    internal lateinit var stone: EditText
    internal lateinit var lbs_pounds: EditText
    internal lateinit var oz: EditText
    internal var calculate: Button? = null

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
    var history_list: ArrayList<Weight_Calc_History> = ArrayList()

    private var noteRepository: Weight_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight__calculator)

        get_Today_Date()
        initVies()


    }


    private fun initVies() {

        history = findViewById(R.id.history)
        noteRepository = Weight_Calc_NoteRepository(applicationContext)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }



        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })

        kg = findViewById(R.id.kg)
        stone = findViewById(R.id.stone)
        lbs_pounds = findViewById(R.id.lbs_pounds)
        oz = findViewById(R.id.oz)

        kg.addTextChangedListener(GenericTextWatcher(kg))
        stone.addTextChangedListener(GenericTextWatcher(stone))
        lbs_pounds.addTextChangedListener(GenericTextWatcher(lbs_pounds))
        oz.addTextChangedListener(GenericTextWatcher(oz))





        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }

    }



    private fun show_History(value: Boolean) {

        if (this@Weight_Calculator_4 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Weight_Calculator_4)
                val sheetView = LayoutInflater.from(this@Weight_Calculator_4).inflate(R.layout.bottom_sheet, null)

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

        if (this@Weight_Calculator_4 != null) {
            runOnUiThread {

                adapter = Temperature_Adapter(today_date as String, "weight", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Weight_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    private fun fetch_All_Record() {

        noteRepository!!.getALL_Weight__Record().observe(this, object : Observer<List<Weight_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Weight_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)


                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Weight_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Weight_Calculator_4 != null) {
                                    this@Weight_Calculator_4.runOnUiThread(Runnable {
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
                    noteRepository!!.clear_Weight_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun insert_data_To_Hisotory_Table(date: String, kg: String, stone: String, lbs: String,oz:String) {

        println("Insert" + date + " " + kg + " " + stone + " " + lbs+" "+oz)
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Weight__Calc_Record(date, kg,stone,lbs,oz)

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


    private fun convert_kg_to_Others(kgs: CharSequence) {

        val kg = java.lang.Double.parseDouble(kgs.toString())

        //stone
        val stone_result = kg / 6.35
        stone.hint = "" + df.format(stone_result)

        //lbs_pounds
        val lbs_pounds_result = kg * 2.205
        lbs_pounds.hint = "" + df.format(lbs_pounds_result)

        //oz
        val oz_result = kg * 35.274
        oz.hint = "" + df.format(oz_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                kgs.toString(),
                stone.hint.toString(),
                lbs_pounds.hint.toString(),
                oz.hint.toString()
        )



    }

    private fun convert_stone_to_Others(stones: CharSequence) {


        val stone = java.lang.Double.parseDouble(stones.toString())

        //kg
        val kg_result = stone * 6.35
        kg.hint = "" + df.format(kg_result)

        //lbs_pounds
        val lbs_pounds_result = stone * 14
        lbs_pounds.hint = "" + df.format(lbs_pounds_result)

        //oz
        val oz_result = stone * 224
        oz.hint = "" + df.format(oz_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                kg.hint.toString(),
                stones.toString(),
                lbs_pounds.hint.toString(),
                oz.hint.toString()
        )


    }

    private fun convert_lbs_to_Others(lbss: CharSequence) {


        val lbs = java.lang.Double.parseDouble(lbss.toString())

        //kg
        val kg_result = lbs / 2.205
        kg.hint = "" + df.format(kg_result)

        //stone
        val stone_result = lbs / 14
        stone.hint = "" + df.format(stone_result)

        //oz
        val oz_result = lbs * 16
        oz.hint = "" + df.format(oz_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                kg.hint.toString(),
                stone.hint.toString(),
                lbss.toString(),
                oz.hint.toString()
        )


    }


    private fun convert_oz_to_Others(ozz: CharSequence) {


        val oz1 = java.lang.Double.parseDouble(ozz.toString())

        //kg
        val kg_result = oz1 / 35.274
        kg.hint = "" + df.format(kg_result)

        //stone
        val stone_result = oz1 / 224
        stone.hint = "" + df.format(stone_result)

        //lbs_pounds
        val lbs_pounds_result = oz1 / 16
        lbs_pounds.hint = "" + df.format(lbs_pounds_result)

        insert_data_To_Hisotory_Table(today_date.toString(),
                kg.hint.toString(),
                stone.hint.toString(),
                lbs_pounds.hint.toString(),
                ozz.toString()
        )

    }




    private inner class GenericTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                //first row
                R.id.kg ->

                    if (editable.length > 0)
                        convert_kg_to_Others(editable)
                    else {
                        kg.hint = ""
                        stone.hint = ""
                        lbs_pounds.hint = ""
                        oz.hint = ""
                    }

                R.id.stone -> if (editable.length > 0)
                    convert_stone_to_Others(editable)
                else {
                    kg.hint = ""
                    stone.hint = ""
                    lbs_pounds.hint = ""
                    oz.hint = ""
                }

                R.id.lbs_pounds -> if (editable.length > 0)
                    convert_lbs_to_Others(editable)
                else {
                    kg.hint = ""
                    stone.hint = ""
                    lbs_pounds.hint = ""
                    oz.hint = ""
                }
                R.id.oz -> if (editable.length > 0)
                    convert_oz_to_Others(editable)
                else {
                    kg.hint = ""
                    stone.hint = ""
                    lbs_pounds.hint = ""
                    oz.hint = ""
                }
            }
        }
    }




}
