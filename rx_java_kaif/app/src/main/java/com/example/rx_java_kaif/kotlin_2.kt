package com.example.rx_java_kaif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class kotlin_2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_2)



        val bundle:Bundle = intent.extras!!

       val id= bundle.get("id")
        println("kotlin_2_data"+id)


        loop1@ for (i in 1..5) {
           loop2@ for (j in 1..5) {
                println("run"+i)
                if (i == 2)
                    break@loop1
            }
        }


    }
}
