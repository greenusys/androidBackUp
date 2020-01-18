package com.example.digitalforgeco.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity


import com.example.digitalforgeco.R
import com.example.menu_library.animation.GuillotineAnimation

import java.text.DecimalFormat

class BMI_Calculator_7 : AppCompatActivity() {


    internal lateinit var calculate: Button
    internal lateinit var feet: EditText
    internal lateinit var inch: EditText
    internal lateinit var cm: EditText
    internal lateinit var stone: EditText
    internal lateinit var lbs: EditText
    internal lateinit var kg: EditText
    internal lateinit var result: TextView
    internal var df = DecimalFormat("#")


    //for menu layout
    internal var home: LinearLayout? = null
    internal var distance_1: LinearLayout? = null
    internal var percentage_2: LinearLayout? = null
    internal var temperature_3: LinearLayout? = null
    internal var weight_4: LinearLayout? = null
    internal var length_5: LinearLayout? = null
    internal var simple_6: LinearLayout? = null
    internal var scientific_7: LinearLayout? = null
    internal var volume_8: LinearLayout? = null
    private val isGuillotineOpened: Boolean = false
    internal var aniMenu: GuillotineAnimation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi__calculator)



        initVies()

    }

    private fun initVies() {

        feet = findViewById(R.id.feet)
        inch = findViewById(R.id.inches)
        cm = findViewById(R.id.cm)
        stone = findViewById(R.id.stone)
        lbs = findViewById(R.id.lbs)
        kg = findViewById(R.id.kg)
        calculate = findViewById(R.id.calculate)
        result = findViewById(R.id.result)

        calculate.setOnClickListener {
            if (calculate.text.toString() == "Clear") {

                feet.setText("")
                inch.setText("")
                cm.setText("")
                stone.setText("")
                lbs.setText("")
                kg.setText("")
                result.text = ""
                calculate.text = "Calculate"

            } else {

                val bmi = calculate_Weight()!! / calculate_Heights()!!
                println("weight" + calculate_Weight()!!)
                println("height" + calculate_Heights()!!)
                println("result$bmi")


                if (bmi.isNaN() || bmi.isInfinite() || bmi == 0.0) {
                    println("k1")
                    result.text = "BMI"
                } else {
                    println("k2")
                    result.text = "" + df.format(bmi)
                    calculate.text = "Clear"
                }


            }
        }

    }


    private fun calculate_Heights(): Double? {

        var height = 0.0
        var feets = 0.0
        var feet_meter_1 = 0.0
        var inchs = 0.0
        var inches_meter_2 = 0.0

        if (cm.text.toString() == "") {
            println("1")

            val feet_value = feet.text.toString()
            val inch_value = inch.text.toString()

            if (feet.text.toString() != "") {
                feets = java.lang.Double.parseDouble(feet_value)
                feet_meter_1 = feets / 3.281

            }

            if (inch.text.toString() != "") {
                inchs = java.lang.Double.parseDouble(inch_value)
                inches_meter_2 = inchs / 39.37

            }

            if (feets != 0.0 && inchs != 0.0)
                cm.setText("" + df.format(feets * 30.48 + inchs * 2.54))


            if (feet_meter_1 != 0.0 && inches_meter_2 != 0.0) {
                height = (feet_meter_1 + inches_meter_2) * (feet_meter_1 + inches_meter_2)


            }


        } else if (cm.text.toString() != "") {
            val value = cm.text.toString()

            println("2")
            val cms = java.lang.Double.parseDouble(value)
            val height_in_meter = cms / 100
            height = height_in_meter * height_in_meter

        }


        return height

    }


    private fun calculate_Weight(): Double? {

        var weight = 0.0
        var stones = 0.0
        var stone_in_kg = 0.0
        var lbss = 0.0
        var lbs_in_kg = 0.0

        if (kg.text.toString() == "") {
            println("1")

            val stone_value = stone.text.toString()
            val lbs_value = lbs.text.toString()

            if (stone.text.toString() != "") {
                stones = java.lang.Double.parseDouble(stone_value)
                stone_in_kg = stones * 6.35

            }

            if (lbs.text.toString() != "") {
                lbss = java.lang.Double.parseDouble(lbs_value)
                lbs_in_kg = lbss / 2.205

            }


            if (stone_in_kg != 0.0) {
                weight = stone_in_kg
                kg.setText("" + df.format(weight))
            }

            if (lbs_in_kg != 0.0) {
                weight = lbs_in_kg
                kg.setText("" + df.format(weight))
            }


        } else if (kg.text.toString() != "") {
            val value = kg.text.toString()

            println("2")
            val kg = java.lang.Double.parseDouble(value)

            weight = kg
        }


        return weight

    }

}
