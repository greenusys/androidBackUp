package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask

import androidx.lifecycle.LiveData
import androidx.room.Room


import com.example.digitalforgeco.modal.Tracking_History_Modal
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Tracking_History_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    fun clear_Tracking_History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Tracking_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    fun clear_One_Tracking_History(tracking_no:String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_One_Tracking_History(tracking_no)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    fun insert_Tracking_History_Record(date: String,
                                       trackingno: String,
                                       completed: String,
                                       jsondata: String) {

        val note = Tracking_History_Modal(date, trackingno, completed, jsondata)
        insert_Tracking_history_task(note)
    }

    fun update_Tracking_History_Data(completed: String,
                                     jsondata: String,
                                     tracking_no: String) {

        update_Simple_Calc_Task(completed, jsondata, tracking_no)
    }


    fun update_Simple_Calc_Task(completed: String, jsondata: String, tracking_no: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().update_Tracking_History(completed, jsondata, tracking_no)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }

    fun insert_Tracking_history_task(note: Tracking_History_Modal) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Tracking_History(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Tracking_Record(): LiveData<List<Tracking_History_Modal>> {
        return noteDatabase!!.daoAccess().fetch_All_Tracking_History()
    }

    fun get_Tracking_One_Record(tracking_no: String): LiveData<List<Tracking_History_Modal>> {
        return noteDatabase!!.daoAccess().fetch_Tracking_History_By_Specific(tracking_no)
    }


}
