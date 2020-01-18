package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask

import androidx.lifecycle.LiveData
import androidx.room.Room


import com.example.digitalforgeco.modal.Simple_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Simple_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? =null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    fun clear_Simple_History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Simple_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    //for simple Calculator
    fun insert_Simple_Calc_Record(date: String,
                              data: String) {
        println("note_inserted_called"+date+data)

        val note = Simple_Calc_History(date, data)
        insert_Simple_history_task(note)
    }

    fun update_Simple_History_Data(date: String,
                              data: String) {

        update_Simple_Calc_Task(date,data)
    }


    fun update_Simple_Calc_Task(date: String, data: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().update_Simple_Calc(date,data)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    fun insert_Simple_history_task(note: Simple_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Simple_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Simple_Calc_Record(): LiveData<List<Simple_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Simple_Calc_History()
    }

    fun get_Simple_Calc_One_Record(date: String): LiveData<List<Simple_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_Simple_Cal_By_Specific(date)
    }


}
