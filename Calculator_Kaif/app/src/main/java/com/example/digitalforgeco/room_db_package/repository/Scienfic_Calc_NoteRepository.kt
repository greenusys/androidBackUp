package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Scienfic_Calc_History


import com.example.digitalforgeco.modal.Simple_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Scienfic_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? =null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }



    //for simple Calculator


    fun clear_Simple_History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Scienfic_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun insert_Scienfic_Calc_Record(date: String,
                              data: String) {
        println("note_inserted_called"+date+data)

        val note = Scienfic_Calc_History(date, data)
        insert_Scienfic_history_task(note)
    }

    fun update_Scienfic_History_Data(date: String,
                              data: String) {

        update_Scienfic_Calc_Task(date,data)
    }


    fun update_Scienfic_Calc_Task(date: String, data: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().update_Scienfic_Calc(date,data)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    fun insert_Scienfic_history_task(note: Scienfic_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Scienfic_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Scienfic_Calc_Record(): LiveData<List<Scienfic_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Scienfic_Calc_History()
    }

    fun get_Scienfic_Calc_One_Record(date: String): LiveData<List<Scienfic_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_Scienfic_Cal_By_Specific(date)
    }


}
