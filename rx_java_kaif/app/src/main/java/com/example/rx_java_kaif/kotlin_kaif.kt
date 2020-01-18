package com.example.rx_java_kaif

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kotlin_kaif.*

class kotlin_kaif : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_kaif)

        val k=findViewById<TextView>(R.id.textView2)

         intent = Intent(this,kotlin_2::class.java)
            intent.putExtra("id","kaif")
        startActivity(intent)





    }

    fun kaif(view: View) {}
}
