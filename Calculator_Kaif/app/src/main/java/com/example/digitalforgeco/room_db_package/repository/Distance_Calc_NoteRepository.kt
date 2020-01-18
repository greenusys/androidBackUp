package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Distance_Calc_History
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Distance_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    //for simple Calculator


    fun clear_Distance__History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Distance_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun insert_Distance__Calc_Record(date: String, miles: String, km: String
                                     ,meters: String,feet: String,inches: String) {

        val note = Distance_Calc_History(date,miles,km,meters,feet,inches)
        insert_Distance__history_task(note)
    }


    fun insert_Distance__history_task(note: Distance_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Distance_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Distance__Record(): LiveData<List<Distance_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Distance_Calc_History()
    }


}
