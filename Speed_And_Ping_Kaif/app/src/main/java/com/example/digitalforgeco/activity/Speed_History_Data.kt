package com.example.digitalforgeco.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.History_Adapter
import com.example.digitalforgeco.modal.History
import com.example.digitalforgeco.room_db_package.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_speed__history__data.*

class Speed_History_Data : AppCompatActivity() {


    val list = ArrayList<History>()
    var noteRepository: NoteRepository? = null
    lateinit var adapter: History_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed__history__data)



        initViews()


    }

    private fun initViews() {

        noteRepository = NoteRepository(applicationContext)

        history_rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = History_Adapter(applicationContext, list)
        history_rv.adapter = adapter


        fetch_History_Record();


    }

    private fun fetch_History_Record() {

        noteRepository?.getALL_History_Record()?.observe(this, Observer<List<History>> { notes ->
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg voids: Void): Void? {

                    if (notes.size > 0) {
                        for (i in 0..notes.size - 1)
                            list.add(History(notes.get(i).date,
                                    notes.get(i).time,
                                    notes.get(i).ping,
                                    notes.get(i).download,
                                    notes.get(i).upload
                            ))

                        if (this@Speed_History_Data != null) {
                            this@Speed_History_Data.runOnUiThread(Runnable {
                                no_data_layout.visibility = View.GONE

                                history_rv.recycledViewPool.clear()
                                adapter!!.notifyDataSetChanged()
                            })
                        }


                    } else {

                        if (this@Speed_History_Data != null) {
                            this@Speed_History_Data.runOnUiThread(Runnable {
                                no_data_layout.visibility = View.VISIBLE
                            })
                        }

                    }





                    return null
                }
            }.execute()
        })

    }

    fun back(view: View) {
        startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}
