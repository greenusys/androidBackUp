package com.example.digitalforgeco.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.Tracking_History_List_Adapter
import com.example.digitalforgeco.modal.Tracking_History_Modal
import com.example.digitalforgeco.room_db_package.repository.Tracking_History_NoteRepository
import kotlinx.android.synthetic.main.activity_main.*

class Tracking_History_List : AppCompatActivity() {

    private var noteRepository: Tracking_History_NoteRepository? = null

    var tracking_rv: RecyclerView? = null
    var no_history_layout: View? = null
    var adapter: Tracking_History_List_Adapter? = null
    var list: ArrayList<Tracking_History_Modal> = ArrayList<Tracking_History_Modal>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking__history__list)

        initViews()
    }

    private fun initViews() {

        val title_image: ImageView = findViewById(R.id.title_image)
        title_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.back_arrow))
        title_image.setOnClickListener(View.OnClickListener {

            onBackPressed()
        })


        tracking_rv = findViewById(R.id.tracking_rv)
        no_history_layout = findViewById(R.id.no_history_layout_id)
        noteRepository = Tracking_History_NoteRepository(applicationContext)


        fetch_All_Record()


        set_Recycler_View_Data()


    }

    @SuppressLint("WrongConstant")
    private fun set_Recycler_View_Data() {

        if (this@Tracking_History_List != null) {
            this@Tracking_History_List.runOnUiThread(Runnable {
                adapter = Tracking_History_List_Adapter(applicationContext, list)
                tracking_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                tracking_rv!!.adapter = adapter
                adapter!!.notifyDataSetChanged()

            })
        }


    }


    private fun fetch_All_Record() {
        noteRepository!!.getALL_Tracking_Record().observe(this, object : Observer<List<Tracking_History_Modal>> {
            override fun onChanged(@Nullable history_lst: List<Tracking_History_Modal>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("tracking_history_size" + history_lst.size)



                        if (history_lst.size > 0) {
                            list = history_lst as ArrayList<Tracking_History_Modal>

                            set_Recycler_View_Data()

                        } else {

                            if (this@Tracking_History_List != null) {
                                this@Tracking_History_List.runOnUiThread(Runnable {

                                    visible_No_History_layout()

                                })
                            }

                        }




                        return null
                    }
                }.execute()


            }
        })
    }

    private fun visible_No_History_layout() {
        if (this@Tracking_History_List != null) {
            this@Tracking_History_List.runOnUiThread(Runnable {

                println("kaif"+no_history_layout)
                no_history_layout!!.visibility = View.VISIBLE
            })
        }
    }


}
