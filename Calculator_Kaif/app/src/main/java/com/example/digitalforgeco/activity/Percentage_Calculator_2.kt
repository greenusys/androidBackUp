package com.example.digitalforgeco.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.digitalforgeco.R
import com.example.digitalforgeco.widget.CanaroTextView
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import java.text.DecimalFormat


class Percentage_Calculator_2 : AppCompatActivity() {
    internal lateinit var x1: EditText
    internal lateinit var y1: EditText
    internal lateinit var x2: EditText
    internal lateinit var y2: EditText
    internal lateinit var x3: EditText
    internal lateinit var y3: EditText
    internal lateinit var x4: EditText
    internal lateinit var y4: EditText
    internal lateinit var x5: EditText
    internal lateinit var y5: EditText
    internal lateinit var result1: CanaroTextView
    internal lateinit var result2: CanaroTextView
    internal lateinit var result3: CanaroTextView
    internal lateinit var result4: CanaroTextView
    internal lateinit var result5: CanaroTextView
    private var value1: CharSequence = ""
    private var value2: CharSequence = ""
    private var value3: CharSequence = ""
    private var value4: CharSequence = ""
    private var value5: CharSequence = ""
    private var value6: CharSequence = ""
    private var value7: CharSequence = ""
    private var value8: CharSequence = ""
    private var value9: CharSequence = ""
    private var value10: CharSequence = ""
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
    private var isGuillotineOpened: Boolean = false
    internal lateinit var aniMenu: GuillotineAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_percentage__calculator)

        initVies()

    }


    private fun initVies() {


        findViewById<View>(R.id.back).setOnClickListener {
            finish()
            onBackPressed()
        }


        x1 = findViewById(R.id.x1)
        y1 = findViewById(R.id.y1)
        x2 = findViewById(R.id.x2)
        y2 = findViewById(R.id.y2)
        x3 = findViewById(R.id.x3)
        y3 = findViewById(R.id.y3)
        x4 = findViewById(R.id.x4)
        y4 = findViewById(R.id.y4)
        x5 = findViewById(R.id.x5)
        y5 = findViewById(R.id.y5)
        result1 = findViewById(R.id.result1)
        result2 = findViewById(R.id.result2)
        result3 = findViewById(R.id.result3)
        result4 = findViewById(R.id.result4)
        result5 = findViewById(R.id.result5)

        x1.addTextChangedListener(GenericTextWatcher(x1))
        y1.addTextChangedListener(GenericTextWatcher(y1))
        x2.addTextChangedListener(GenericTextWatcher(x2))
        y2.addTextChangedListener(GenericTextWatcher(y2))
        x3.addTextChangedListener(GenericTextWatcher(x3))
        y3.addTextChangedListener(GenericTextWatcher(y3))
        x4.addTextChangedListener(GenericTextWatcher(x4))
        y4.addTextChangedListener(GenericTextWatcher(y4))
        x5.addTextChangedListener(GenericTextWatcher(x5))
        y5.addTextChangedListener(GenericTextWatcher(y5))


    }


    private inner class GenericTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                //first row
                R.id.x1 -> {
                    value1 = editable
                    if (editable.length > 0)
                        calculate_First()
                }
                R.id.y1 -> {
                    value2 = editable
                    if (editable.length > 0)
                        calculate_First()
                }

                //second row
                R.id.x2 -> {
                    value3 = editable
                    if (editable.length > 0)
                        calculate_Second()
                }
                R.id.y2 -> {
                    value4 = editable
                    if (editable.length > 0)
                        calculate_Second()
                }

                //third row
                R.id.x3 -> {
                    value5 = editable
                    if (editable.length > 0)
                        calculate_Third()
                }
                R.id.y3 -> {
                    value6 = editable
                    if (editable.length > 0)
                        calculate_Third()
                }

                //fourth row
                R.id.x4 -> {
                    value7 = editable
                    if (editable.length > 0)
                        calculate_Fourth()
                }

                R.id.y4 -> {
                    value8 = editable
                    if (editable.length > 0)
                        calculate_Fourth()
                }

                //fifth row
                R.id.x5 -> {
                    value9 = editable
                    if (editable.length > 0)
                        calculate_Fifth()
                }

                R.id.y5 -> {
                    value10 = editable
                    if (editable.length > 0)
                        calculate_Fifth()
                }
            }
        }
    }

    private fun calculate_Fifth() {
        var x: Double? = 0.0
        var y: Double? = 0.0
        var result: Double? = 0.0


        if (value9.length > 0)
            x = java.lang.Double.parseDouble(value9.toString())

        if (value10.length > 0)
            y = java.lang.Double.parseDouble(value10.toString())


        if (x != 0.0 && y != 0.0) {
            result = x!! / (y!! / 100)
            result5.text = "" + df.format(result)
        }
    }

    private fun calculate_Fourth() {
        var x: Double? = 0.0
        var y: Double? = 0.0
        var result: Double? = 0.0


        if (value7.length > 0)
            x = java.lang.Double.parseDouble(value7.toString())

        if (value8.length > 0)
            y = java.lang.Double.parseDouble(value8.toString())


        if (x != 0.0 && y != 0.0) {
            result = x!! / 100 * y!!
            result4.text = "" + df.format(result)
        }
    }


    private fun calculate_Third() {
        var x: Double? = 0.0
        var y: Double? = 0.0
        var result: Double? = 0.0


        if (value5.length > 0)
            x = java.lang.Double.parseDouble(value5.toString())

        if (value6.length > 0)
            y = java.lang.Double.parseDouble(value6.toString())


        if (x != 0.0 && y != 0.0) {
            result = x!! + y!! / 100 * x
            result3.text = "" + df.format(result)
        }
    }

    private fun calculate_Second() {
        var x: Double? = 0.0
        var y: Double? = 0.0
        var result: Double? = 0.0


        if (value3.length > 0)
            x = java.lang.Double.parseDouble(value3.toString())

        if (value4.length > 0)
            y = java.lang.Double.parseDouble(value4.toString())


        if (x != 0.0 && y != 0.0) {
            result = x!! - y!! / 100 * x
            result2.text = "" + df.format(result)
        }
    }

    private fun calculate_First() {
        var x: Double? = 0.0
        var y: Double? = 0.0
        var result: Double? = 0.0


        if (value1.length > 0)
            x = java.lang.Double.parseDouble(value1.toString())

        if (value2.length > 0)
            y = java.lang.Double.parseDouble(value2.toString())


        if (x != 0.0 && y != 0.0) {
            result = x!! / 100 * y!!
            result1.text = "" + df.format(result)
        }
    }



}
