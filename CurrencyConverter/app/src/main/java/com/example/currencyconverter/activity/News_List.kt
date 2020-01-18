package com.example.currencyconverter.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.currencyconverter.R
import com.example.currencyconverter.Viewmodal.Fetch_News_VM
import com.example.currencyconverter.adapter.News_Adapter
import com.example.currencyconverter.modal.News_Resp_Modal
import kotlinx.android.synthetic.main.activity_news__list.*


class News_List : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var viewmodal: Fetch_News_VM? = null
    internal lateinit var news_rv: RecyclerView
    internal var no_data_found_layout: LinearLayout? = null
    internal var loading_anim: LottieAnimationView? = null

    private var mul_adapter: News_Adapter? = null
    var country = arrayOf("Today News", "Yesterday", "17-12-2019", "16-12-2019", "15-12-2019")

    var news_list = ArrayList<News_Resp_Modal.News_Res_List>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.currencyconverter.R.layout.activity_news__list)

        initSpinner()
        initViews()

        fetch_News_List()


    }

    private fun fetch_News_List() {

        viewmodal!!.fetch_News_List("").observe(this@News_List, Observer<ArrayList<News_Resp_Modal.News_Res_List>> { mainjson ->

            if (news_list.size > 0 || news_list != null)
                news_list.clear()



            if (mainjson != null) {


                //var controller = AnimationUtils.loadLayoutAnimation(applicationContext, R.anim.layout_animation_left_to_right);
                //news_rv.setLayoutAnimation(controller);
                //news_rv.scheduleLayoutAnimation();

                loading_anim!!.visibility = View.GONE
                news_list.addAll(mainjson)





                if (this@News_List != null) {
                    this@News_List.runOnUiThread(Runnable { })
                    mul_adapter!!.notifyDataSetChanged()
                }



            } else {
                loading_anim!!.visibility = View.GONE
                news_rv.recycledViewPool.clear()
                mul_adapter!!.notifyDataSetChanged()
                visible_No_Data_Found_Layout()
            }

        })
    }

    private fun initViews() {


        viewmodal = ViewModelProviders.of(this).get(Fetch_News_VM::class.java)



        news_rv = findViewById(R.id.news_rv)
        no_data_found_layout = findViewById(R.id.no_data_found_layout)
        loading_anim = findViewById(R.id.loading_anim)

        loading_anim!!.visibility = View.VISIBLE


        mul_adapter = News_Adapter(news_list, this)
        news_rv.setLayoutManager(LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false))
        news_rv.setAdapter(mul_adapter)
    }

    private fun visible_No_Data_Found_Layout() {
        no_data_found_layout!!.visibility = View.VISIBLE
    }


    private fun initSpinner() {
        news_sp.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, country)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        news_sp.adapter = aa
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        println("kaif" + country[p2])
    }

    fun back(view: View) {

        onBackPressed()
    }
}
