package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Temperature_Calc_History
import com.example.digitalforgeco.modal.Volume_Calc_History
import com.example.digitalforgeco.modal.Weight_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Volume_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    //for Volume Calculator


    fun clear_Volume_History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Volume_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun insert_Volume__Calc_Record(date: String, length: String, width: String,
                                        heights: String,title_plus_result:String) {

        val note = Volume_Calc_History(date, length, width, heights,title_plus_result)
        insert_Volume__history_task(note)
    }


    fun insert_Volume__history_task(note: Volume_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Volume_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALLVolume__Record(): LiveData<List<Volume_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Volume_Calc_History()
    }


}
