package com.example.digitalforgeco.activity


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.History_Adapter
import com.example.digitalforgeco.data.Calculations
import com.example.digitalforgeco.modal.Scienfic_Calc_History
import com.example.digitalforgeco.room_db_package.repository.Scienfic_Calc_NoteRepository
import com.example.menu_library.animation.GuillotineAnimation
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class Scientific_Calculator_8 : AppCompatActivity() {

    internal var calculations = Calculations(this)
    private val HYP = false

    private var sub: TextView? = null
    private var main: TextView? = null


    private var sin: Button? = null
    private var cos: Button? = null
    private var tan: Button? = null


    private var parent_open: Button? = null
    private var parent_close: Button? = null

    private var sq: Button? = null
    private var e: Button? = null
    private var c: Button? = null
    private var bs: Button? = null
    private var div: Button? = null

    private var seven: Button? = null
    private var eight: Button? = null
    private var nine: Button? = null
    private var mul: Button? = null

    private var four: Button? = null
    private var five: Button? = null
    private var six: Button? = null
    private var plus: Button? = null

    private var sqrt: Button? = null
    private var ln: Button? = null
    private var one: Button? = null
    private var two: Button? = null
    private var three: Button? = null
    private var minus: Button? = null


    private var negate: Button? = null
    private var zero: Button? = null
    private var decimal: Button? = null
    private var equal: Button? = null


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
    var isGuillotineOpened: Boolean = false
    internal lateinit var aniMenu: GuillotineAnimation

    internal var history_rv: RecyclerView? = null
    internal var adapter: History_Adapter? = null
    var history_list: ArrayList<Scienfic_Calc_History> = ArrayList()

    private var noteRepository: Scienfic_Calc_NoteRepository? = null
    lateinit var history: TextView
    var fetch_All: Boolean = false
    var fetch_Single: Boolean = false
    var today_date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scientific__calculator)


        get_Today_Date()
        initVies()

        perform_Calculation()


    }


    private fun initVies() {

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }

        history = findViewById(R.id.history)
        noteRepository = Scienfic_Calc_NoteRepository(applicationContext)




        history.setOnClickListener(View.OnClickListener {
            fetch_All = true
            fetch_All_Record()
        })


    }


    private fun show_History(value: Boolean) {

        if (this@Scientific_Calculator_8 != null) {
            runOnUiThread {


                val sheetDialog = BottomSheetDialog(this@Scientific_Calculator_8)
                val sheetView = LayoutInflater.from(this@Scientific_Calculator_8).inflate(R.layout.bottom_sheet, null)

                history_rv = sheetView.findViewById(R.id.history_rv)
               val not_history_found:LinearLayout = sheetView.findViewById(R.id.not_history_found)

                val clear_history: TextView = sheetView.findViewById(R.id.clear_history)


                //no history found
                if (value) {
                    not_history_found!!.visibility = View.VISIBLE
                    clear_history!!.visibility = View.GONE
                }
                else {
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

    private fun clear_History() {


        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.clear_Simple_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }

    @SuppressLint("WrongConstant")
    private fun set_Data_To_RecycylerView() {

        if (this@Scientific_Calculator_8 != null) {
            runOnUiThread {

                adapter = History_Adapter(today_date as String, "scientfic", applicationContext)
                history_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                history_rv!!.adapter = adapter

                adapter!!.set_Scienfic_Data(history_list)

                adapter!!.notifyDataSetChanged()


            }
        }


    }


    fun insert_into_History(Data: String) {

        try {
            fetch_Single = true
            //if same date data is already exist then update their value
            get_Single_Simple_Record(Data)
            //else insert new data with new date

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun fetch_All_Record() {
        noteRepository!!.getALL_Scienfic_Calc_Record().observe(this, object : Observer<List<Scienfic_Calc_History>> {
            override fun onChanged(@Nullable history_lst: List<Scienfic_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("history_lst_size" + history_lst.size)
                        //println("data" + history_lst.get(0).data)


                        if (fetch_All) {

                            if (history_lst.size > 0) {
                                history_list = history_lst as ArrayList<Scienfic_Calc_History>
                                show_History(false)

                            } else {

                                if (this@Scientific_Calculator_8 != null) {
                                    this@Scientific_Calculator_8.runOnUiThread(Runnable {
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




    private fun get_Single_Simple_Record(newData: String) {
        noteRepository!!.get_Scienfic_Calc_One_Record(today_date.toString()).observe(this, object : Observer<List<Scienfic_Calc_History>> {
            override fun onChanged(@Nullable single: List<Scienfic_Calc_History>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {


                        println("get_single_Record" + single.size)
                        //println("data" + single.get(0).data)


                        if (fetch_Single) {

                            if (single.size > 0) {
                                println("update_data")
                                update_Data_To_Hisotry_Table(single.get(0).data + "\n\n\n" + newData)
                            } else {
                                println("insert_data")
                                insert_data_To_Hisotory_Table(newData)
                            }

                        }

                        fetch_Single = false



                        return null
                    }
                }.execute()


            }
        })
    }


    private fun update_Data_To_Hisotry_Table(data: String) {


        println("update_called")
        //noteRepository!!.update_Simple_History_Data(today_date.toString(), data)

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.update_Scienfic_History_Data(today_date.toString(), data)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }

    private fun insert_data_To_Hisotory_Table(data: String) {
        println("insert_data_To_Hisotory_Table_called" + data)

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Scienfic_Calc_Record(today_date.toString(), data)

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

    private fun perform_Calculation() {
        sub = findViewById(R.id.scientific_tv_sub)
        main = findViewById(R.id.scientific_tv_main)


        sin = findViewById(R.id.scientific_btn_sin)
        sin!!.setOnClickListener {
            if (HYP) {
                calculations.operatorClicked("sinh")
            } else {
                calculations.operatorClicked("sin")
            }
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        cos = findViewById(R.id.scientific_btn_cos)
        cos!!.setOnClickListener {
            if (HYP) {
                calculations.operatorClicked("cosh")
            } else {
                calculations.operatorClicked("cos")
            }
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        tan = findViewById(R.id.scientific_btn_tan)
        tan!!.setOnClickListener {
            if (HYP) {
                calculations.operatorClicked("tanh")
            } else {
                calculations.operatorClicked("tan")
            }
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }


        parent_open = findViewById(R.id.scientific_btn_parent_open)
        parent_open!!.setOnClickListener {
            calculations.parent_openClicked()
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }
        parent_close = findViewById(R.id.scientific_btn_parent_close)
        parent_close!!.setOnClickListener {
            calculations.parent_closeClicked()
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        sq = findViewById(R.id.scientific_btn_sq)
        sq!!.setOnClickListener {
            calculations.operatorClicked("sq")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        e = findViewById(R.id.scientific_btn_e)
        e!!.setOnClickListener {
            calculations.operatorClicked("e")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }


        c = findViewById(R.id.scientific_btn_c)
        c!!.setOnClickListener {
            calculations.clear()
            main!!.text = "0"
            sub!!.text = calculations.calc(calculations.numbers)
        }
        bs = findViewById(R.id.scientific_btn_bs)
        bs!!.setOnClickListener {
            calculations.bs()
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }
        div = findViewById(R.id.scientific_btn_div)
        div!!.setOnClickListener {
            calculations.operatorClicked("/")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }


        seven = findViewById(R.id.scientific_btn_7)
        seven!!.setOnClickListener {
            calculations.numberClicked("7")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        eight = findViewById(R.id.scientific_btn_8)
        eight!!.setOnClickListener {
            calculations.numberClicked("8")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        nine = findViewById(R.id.scientific_btn_9)
        nine!!.setOnClickListener {
            calculations.numberClicked("9")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        mul = findViewById(R.id.scientific_btn_mul)
        mul!!.setOnClickListener {
            calculations.operatorClicked("*")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }


        four = findViewById(R.id.scientific_btn_4)
        four!!.setOnClickListener {
            calculations.numberClicked("4")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        five = findViewById(R.id.scientific_btn_5)
        five!!.setOnClickListener {
            calculations.numberClicked("5")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        six = findViewById(R.id.scientific_btn_6)
        six!!.setOnClickListener {
            calculations.numberClicked("6")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        plus = findViewById(R.id.scientific_btn_add)
        plus!!.setOnClickListener {
            calculations.operatorClicked("+")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        sqrt = findViewById(R.id.scientific_btn_sqrt)
        sqrt!!.setOnClickListener {
            calculations.operatorClicked("sqrt")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        ln = findViewById(R.id.scientific_btn_ln)
        ln!!.setOnClickListener {
            calculations.operatorClicked("ln")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        one = findViewById(R.id.scientific_btn_1)
        one!!.setOnClickListener {
            calculations.numberClicked("1")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        two = findViewById(R.id.scientific_btn_2)
        two!!.setOnClickListener {
            calculations.numberClicked("2")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        three = findViewById(R.id.scientific_btn_3)
        three!!.setOnClickListener {
            calculations.numberClicked("3")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        minus = findViewById(R.id.scientific_btn_sub)
        minus!!.setOnClickListener {
            calculations.operatorClicked("-")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }


        negate = findViewById(R.id.scientific_btn_negate)
        negate!!.setOnClickListener {
            calculations.operatorClicked("±")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        zero = findViewById(R.id.scientific_btn_0)
        zero!!.setOnClickListener {
            calculations.numberClicked("0")
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        decimal = findViewById(R.id.scientific_btn_decimal)
        decimal!!.setOnClickListener {
            calculations.decimalClicked()
            main!!.text = calculations.currentNumber
            sub!!.text = calculations.calc(calculations.numbers)
        }

        equal = findViewById(R.id.scientific_btn_equal)
        equal!!.setOnClickListener {
            try {
                val expression = calculations.numbers

                var sub_txt: String = sub!!.text.toString()

                calculations.evaluateAnswer()
                main!!.text = calculations.answer
                sub!!.text = ""

                insert_into_History(sub_txt + "\n\n" + main!!.text.toString() + "\n")
            } catch (e: Exception) {
                println("Exception:$e")
            }
        }
    }


}
