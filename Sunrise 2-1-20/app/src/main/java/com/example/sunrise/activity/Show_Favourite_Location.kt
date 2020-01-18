package com.example.sunrise.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.AsyncTask
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sunrise.Modal.Favourite_Modal
import com.example.sunrise.Modal.History_Modal
import com.example.sunrise.R
import com.example.sunrise.adapter.Fvrt_Locatoin_Adapter
import com.example.sunrise.adapter.Sun_History_Adapter
import com.example.sunrise.room_db_package.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_show__history_.*
import java.util.*
import kotlin.collections.ArrayList


class Show_Favourite_Location : AppCompatActivity() {

    var adapter: Fvrt_Locatoin_Adapter? = null
    var fvrt_rv: RecyclerView? = null
    var no_data_found_layout: LinearLayout? = null
    private var noteRepository: NoteRepository? = null
    var delete_icon: ImageView? = null
    var select_all: CheckBox? = null
    var checked: Boolean = false
    var anim: LottieAnimationView?=null
     var fvrt_list2: ArrayList<Favourite_Modal> = ArrayList<Favourite_Modal>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show__favourite__location)

        initViews()

    }

    private fun initViews() {
        noteRepository = NoteRepository(applicationContext)

        fvrt_rv = findViewById(R.id.fvrt_rv)
        no_data_found_layout = findViewById(R.id.no_data_found_layout)


        delete_icon = findViewById(R.id.delete_icon)
        select_all = findViewById(R.id.select_all)



        fetch_Favourite_Record()



    }

    private fun visible_not_data_Found_Layout() {

        this@Show_Favourite_Location.runOnUiThread(Runnable {
            no_data_found_layout!!.visibility=View.VISIBLE

        })

    }


    @SuppressLint("WrongConstant")
    private fun show_Data_To_RecyclerView(fvrtList: ArrayList<Favourite_Modal>, is_Select_All: Boolean) {

        this@Show_Favourite_Location.runOnUiThread(Runnable {

            if (fvrtList.size > 0) {
                fvrt_rv!!.recycledViewPool.clear()
                fvrt_rv!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                adapter = Fvrt_Locatoin_Adapter(is_Select_All, fvrt_list2, this)
                fvrt_rv!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else {
                disable_Delete_icon_CheckBox()
                visible_not_data_Found_Layout()
            }


        })


    }


    private fun fetch_Favourite_Record() {
        noteRepository!!.get_All_Favourite_Record().observe(this, object : Observer<List<Favourite_Modal>> {
            override fun onChanged(@Nullable fvrt_list: List<Favourite_Modal>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {

                        println("fetch_called_fvrt_size" + fvrt_list.size)

                        if (fvrt_list.size > 0) {
                            fvrt_list2 = fvrt_list as ArrayList<Favourite_Modal>
                            Collections.reverse(fvrt_list2)
                            show_Data_To_RecyclerView(fvrt_list2, false)
                        } else {

                            if(this@Show_Favourite_Location!=null) {
                                this@Show_Favourite_Location.runOnUiThread(Runnable {
                                   // adapter!!.notifyDataSetChanged()
                                    fvrt_list2.clear()
                                    fvrt_rv!!.recycledViewPool.clear()

                                    fvrt_rv!!.adapter=null

                                    disable_Delete_icon_CheckBox()
                                    visible_not_data_Found_Layout()

                                })
                            }

                        }


                        return null
                    }
                }.execute()


            }
        })
    }


    fun delete_history( latlong: String) {


        println("deleted_history_called" + latlong)

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                noteRepository!!.Delete_One_Favourite_Record(latlong)




                return null
            }
        }.execute()


    }


    fun goto_Back(view: View) {
        onBackPressed()
    }

    fun enable_Delete_and_CheckBox() {
        delete_icon!!.visibility = View.VISIBLE
        select_all!!.visibility = View.VISIBLE

        select_all!!.setOnClickListener(View.OnClickListener {

            if (checked) {
                //unchecked
                checked = false
                select_all!!.isChecked = false
                update_checked_item_to_ArrayList(false)
                disable_Delete_icon_CheckBox()
                show_Data_To_RecyclerView(fvrt_list2, false)

            } else {
                enable_Delete_and_CheckBox()
                checked = true
                update_checked_item_to_ArrayList(true)
                show_Data_To_RecyclerView(fvrt_list2, true)

            }


        })

        delete_icon!!.setOnClickListener(View.OnClickListener {

            delete_Selected_item()
        })


    }



    private fun update_checked_item_to_ArrayList(value: Boolean) {
        for (g in 0..fvrt_list2.size - 1) {
            fvrt_list2[g].isItem_selected = value
        }
        adapter!!.notifyDataSetChanged()
    }

    fun disable_Delete_icon_CheckBox() {
        delete_icon!!.visibility = View.GONE
        select_all!!.visibility = View.GONE

    }

    private fun delete_Selected_item() {


        val positonlist: ArrayList<Int> = ArrayList<Int>()


        if (positonlist.size >= 0) positonlist.clear()


        for (g in 0..fvrt_list2.size - 1) {
            var position: Int = if (fvrt_list2.get(g).isItem_selected) g else -1


            if (position != -1)
                positonlist.add(position)


        }

        println("selected_positin" + positonlist)



        for (k in 0..positonlist.size - 1)
            try {

                println("removed" + fvrt_list2.get(k).address)
                println("positoin_to_remove" + positonlist.get(k))
                delete_history( fvrt_list2.get(positonlist.get(k)).latlong)
                fvrt_list2.removeAt(positonlist.get(k))
            } catch (e: IndexOutOfBoundsException) {
                println("removed_2" + fvrt_list2.get(0).address)
                println("positoin_to_remove" + positonlist.get(k))
                delete_history( fvrt_list2.get(0).latlong)
                fvrt_list2.removeAt(0)
            }

        //show_Data_To_RecyclerView(fvrt_list2, false)


    }

}
