package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Temperature_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    //for simple Calculator


    fun clear_Temperature__History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Temperature_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun insert_Temperature__Calc_Record(date: String, farhen: String, celsius: String,
                                        kelvin: String) {

        val note = Temperature_Calc_History(date, farhen, celsius, kelvin)
        insert_Temperature__history_task(note)
    }


    fun insert_Temperature__history_task(note: Temperature_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Temperature_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Temperature__Record(): LiveData<List<Temperature_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Temperature_Calc_History()
    }


}
