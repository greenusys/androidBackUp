package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.modal.Weight_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Weight_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    //for Weight Calculator


    fun clear_Weight_History() {
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


    fun insert_Weight__Calc_Record(date: String, kg: String, stone: String,
                                        lbs: String,pound:String) {

        val note = Weight_Calc_History(date, kg, stone, lbs,pound)
        insert_Weight__history_task(note)
    }


    fun insert_Weight__history_task(note: Weight_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Weight_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Weight__Record(): LiveData<List<Weight_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Weight_Calc_History()
    }


}
