package com.example.digitalforgeco.room_db_package.repository


import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.digitalforgeco.modal.Length_Calc_History
import com.example.digitalforgeco.room_db_package.db.NoteDatabase


class Length_Calc_NoteRepository(context: Context) {
    private val DB_NAME = "calculator_db"

    private var noteDatabase: NoteDatabase? = null

    init {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, DB_NAME).build()
    }


    //for Weight Calculator


    fun clear_Length_History() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().delete_Length_History()

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun insert_Length__Calc_Record(date: String, meters: String, cm: String,
                                   mm: String, feet: String,inches:String) {

        val note = Length_Calc_History(date, meters,cm,mm,feet,inches)
        insert_Length__history_task(note)
    }


    fun insert_Length__history_task(note: Length_Calc_History) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteDatabase!!.daoAccess().insert_Length_Calc(note)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()
    }


    fun getALL_Length__Record(): LiveData<List<Length_Calc_History>> {
        return noteDatabase!!.daoAccess().fetch_All_Length_Calc_History()
    }


}
