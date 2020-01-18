package com.example.digitalforgeco.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.digitalforgeco.Network_Package.AppController
import com.example.digitalforgeco.R
import com.example.digitalforgeco.View_Model.Fetch_Tracking_Response_VM
import com.example.digitalforgeco.adapter.Tracking_Result_Adapter
import com.example.digitalforgeco.modal.Courier_Modal
import com.example.digitalforgeco.modal.Tracking_History_Modal
import com.example.digitalforgeco.modal.Tracking_Modal
import com.example.digitalforgeco.room_db_package.repository.Tracking_History_NoteRepository
import com.example.menu_library.FButton
import com.example.menu_library.animation.GuillotineAnimation
import com.example.menu_library.interfaces.GuillotineListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    internal var toolbar: Toolbar? = null
    internal var order_id_title: TextView? = null
    internal var root: FrameLayout? = null
    internal var contentHamburger: View? = null
    internal var aniMenu: GuillotineAnimation? = null
    internal lateinit var track: FButton
    internal var tracking_input: EditText? = null
    internal var four_box_layout: LinearLayout? = null
    internal var tracking_result_adapter: Tracking_Result_Adapter? = null
    internal var tracking_list = ArrayList<Tracking_Modal>()
    internal var final_tracking_list: ArrayList<Tracking_Modal>? = ArrayList()
    internal var json_res_list = ArrayList<String>()
    internal var code_list = ArrayList<String>()
    internal var output_code_list: ArrayList<String>? = ArrayList()
    internal var courier_list: ArrayList<Courier_Modal>? = ArrayList()
    internal var shipped_via: TextView? = null
    internal var status: TextView? = null
    internal var weight: TextView? = null
    internal var expected_date: TextView? = null
    internal var loading_anim: LottieAnimationView? = null
    internal var tracking_data_layout: LinearLayout? = null
    internal var no_shipment_layout: LinearLayout? = null
    internal lateinit var no_internet_layout: LinearLayout
    private var isGuillotineOpened: Boolean = false
    private var data_rv: RecyclerView? = null
    private val courier_list_rv: RecyclerView? = null
    private var appController: AppController? = null
    private var data_exist: Boolean = false
    private val data_find = false
    private var noteRepository: Tracking_History_NoteRepository? = null


    private var mInterstitialAd: InterstitialAd? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            initViews()
            setMenus()
            set_Button_Listener()
            share_Links()
            //loadInterStitialsAd();
            //loadBannerAdAd();

            val intent = intent
            if (intent != null && intent.data != null) {

                val uri = intent.data


                val track_no = uri!!.getQueryParameter("track_no")

                println("track_number" + track_no!!)

                val rootView = window.decorView.rootView
                hideKeyboardFrom(applicationContext, rootView)
                call_Api(track_no)


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun share_Links() {
        order_id_title!!.setOnClickListener {
            //set up URI data
            val builtUri = Uri.parse("https://areuloved.com/")
                    .buildUpon()
                    .path("redirect.php")
                    .appendQueryParameter("track_no", tracking_input!!.text.toString())
                    .build()
            try {
                val url = URL(builtUri.toString())


                println("me_link$url")
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                //String shareBodyText = "Your shearing message goes here";
                intent.putExtra(Intent.EXTRA_SUBJECT, "PackChase ")
                intent.putExtra(Intent.EXTRA_TEXT, url.toString())
                startActivity(Intent.createChooser(intent, "Choose sharing method"))


            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
    }

    private fun loadInterStitialsAd() {
        mInterstitialAd = InterstitialAd(this)
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test adid
        mInterstitialAd!!.adUnitId = "ca-app-pub-3701953680756708/2892052145" //live adid

        mInterstitialAd!!.loadAd(AdRequest.Builder().build())

        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                if (mInterstitialAd != null)
                    mInterstitialAd!!.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.

                Toast.makeText(this@MainActivity, "$errorCode code", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        }
    }

    private fun loadBannerAdAd() {
        /* AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");*/
        val adRequest = AdRequest.Builder().build()
        adView!!.loadAd(adRequest)


        adView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Toast.makeText(this@MainActivity, "$errorCode Banner code", Toast.LENGTH_SHORT).show()

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    override fun onPause() {
        if (adView != null)
            adView!!.pause()
        super.onPause()
    }

    override fun onResume() {
        if (adView != null)
            adView!!.resume()
        super.onResume()
    }

    override fun onDestroy() {
        if (adView != null)
            adView!!.destroy()
        super.onDestroy()
    }

    fun isNetworkAvailable(context: Context)//check internet of device
            : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    private fun set_Button_Listener() {
        track.setOnClickListener {
            val tracking_no = tracking_input!!.text.toString()

            if (!isNetworkAvailable(applicationContext)) {
                visible_no_internet_layout()
            } else {
                if (tracking_no.length > 0) {

                    val rootView = window.decorView.rootView
                    hideKeyboardFrom(applicationContext, rootView)

                    call_Api(tracking_no)
                } else {
                    val snackbar = Snackbar.make(findViewById(R.id.root), "Please Enter Tracking Number", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
        }
    }

    private fun call_Api(tracking_no: String?) {
        loading_anim!!.playAnimation()
        loading_anim!!.visibility = View.VISIBLE

        if (json_res_list != null)
            json_res_list!!.clear()

        if (courier_list != null)
            courier_list!!.clear()

        fetch_courier_code(tracking_no)
    }


    private fun initViews() {

        code_list.add("fedex")
        code_list.add("ups")
        code_list.add("usps")
        code_list.add("tnt")
        code_list.add("dhl")


        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.title = null
        }

        noteRepository = Tracking_History_NoteRepository(applicationContext)


        toolbar = findViewById(R.id.toolbar)
        four_box_layout = findViewById(R.id.four_box_layout)


        tracking_data_layout = findViewById(R.id.tracking_data_layout)
        no_shipment_layout = findViewById(R.id.no_shipment_layout)
        no_internet_layout = findViewById(R.id.no_internet_layout)

        adView = findViewById(R.id.adView)
        loading_anim = findViewById(R.id.loading_anim)
        shipped_via = findViewById(R.id.shipped_via)
        status = findViewById(R.id.status)
        weight = findViewById(R.id.weight)
        expected_date = findViewById(R.id.expected_date)

        order_id_title = findViewById(R.id.order_id_title)
        appController = applicationContext as AppController
        root = findViewById(R.id.root)
        contentHamburger = findViewById(R.id.title_image)


        val title_image = findViewById<ImageView>(R.id.title_image)
        title_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu_90))


        track = findViewById(R.id.track)
        tracking_input = findViewById(R.id.tracking_input)


        //courier_list_rv = findViewById(R.id.courier_list_rv);
        data_rv = findViewById(R.id.tracking_rv)

        // courier_list_rv.setAdapter(courier_list_adapter);
        tracking_result_adapter = Tracking_Result_Adapter(tracking_list)


    }

    private fun set_Up_Rv_Data(trackingList2: ArrayList<Tracking_Modal>) {

        this@MainActivity?.runOnUiThread {

            println("kareena" + trackingList2.size)

            data_rv!!.recycledViewPool.clear()
            data_rv!!.adapter = null

            tracking_result_adapter = Tracking_Result_Adapter(trackingList2)

            data_rv!!.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

            data_rv!!.adapter = tracking_result_adapter
            tracking_result_adapter!!.notifyDataSetChanged()

            println("adapter" + tracking_result_adapter!!.itemCount)
        }

    }


    //fetch courier list
    fun fetch_courier_code(tracking_number: String?) {

        println("1---fetch_courier_code")


        val jsonObject = JSONObject()
        try {
            jsonObject.put("tracking_number", tracking_number)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        //String requestData="{\"tracking_number\":"\+tracking_number+"\}";
        val JSON = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, jsonObject.toString())


        val request = Request.Builder().url("https://api.trackingmore.com/v2/carriers/detect").addHeader("Content-Type", "application/json").addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907").post(body).build()


        appController!!.okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Errorrrrr " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body()!!.string()
                try {

                    if (courier_list != null)
                        courier_list!!.clear()

                    if (json_res_list != null)
                        json_res_list!!.clear()

                    if (tracking_list != null)
                        tracking_list!!.clear()


                    val mainjson = JSONObject(myResponse)
                    println("response$mainjson")
                    val meta = mainjson.getJSONObject("meta")


                    //for success
                    if (meta.getString("code") == "200") {

                        val data = mainjson.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val item = data.getJSONObject(i)
                            courier_list!!.add(Courier_Modal(item.getString("name"), item.getString("code")))

                        }

                        this@MainActivity?.runOnUiThread { create_tracking_api() }


                    } else {


                        this@MainActivity?.runOnUiThread {
                            //clear RV data,
                            // gone 4 box layout
                            // ,gone animation,
                            // gone tracking title
                            clear_data_and_RV_Data("from_courier_code")


                            //gone tracking_layout,
                            // gone no_internet_layout,
                            // visible no_shipment_found layout
                            gone_tracking_data_layout("from_courier_code")
                        }

                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }


    //only for create tracking api
    @Synchronized
    fun create_tracking_api() {


        if (courier_list!!.size > 0) {

            for (i in courier_list!!.indices) {


                println("2----create_tracking_api_called_" + courier_list!![i].code)
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("tracking_number", tracking_input!!.text.toString())
                    jsonObject.put("carrier_code", courier_list!![i].code)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val JSON = MediaType.parse("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, jsonObject.toString())


                val request = Request.Builder().url("https://api.trackingmore.com/v2/trackings/post").addHeader("Content-Type", "application/json").addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907").post(body).build()


                appController!!.okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("Errorrrrr " + e.message)
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val myResponse = response.body()!!.string()
                        try {


                            val mainjson = JSONObject(myResponse)
                            println("response_second$mainjson")

                            //fetch_Tracking_Data(courier_list.get(finalI).getName(), courier_list.get(finalI).getCode(), tracking_input.getText().toString());


                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                })


            }
        }

        // fetch_Tracking_Data()

        fetch_Tracking_Data_VM()
    }

    private fun fetch_Tracking_Data_VM() {


        if (courier_list!!.size > 0) {

            for (i in courier_list!!.indices) {

                val url = "https://api.trackingmore.com/v2/trackings/" + courier_list!![i].code + "/" + tracking_input!!.text.toString();



                call_Tracking_API(url)


            }//loop

        }//if

    }

    private fun call_Tracking_API(url: String) {

        println("call_tracking_api" + url)
        val Viewmodel = ViewModelProviders.of(this).get(Fetch_Tracking_Response_VM::class.java!!)

        Viewmodel.getTrackingData(url).observe(this, Observer { todo_list ->

            println("sallu" + todo_list)

            json_res_list.add(todo_list)
            println("json_res_list" + json_res_list.size)




            if (json_res_list.size == courier_list!!.size) {
                println("set_up_called_after")
                set_up_Data(json_res_list)
            }
            else
                println("no_setUp" + json_res_list.size + " " + courier_list!!.size)


        })


    }


    //only for fetch tracking details
    @Synchronized
    fun fetch_Tracking_Data() {

        println("couries_size" + courier_list!!.size)

        for (k in courier_list!!.indices)
            println("couries" + courier_list!![k].code)

        if (courier_list!!.size > 0) {

            for (i in courier_list!!.indices) {


                println("3----fetch_Tracking_Data_api_called_" + courier_list!![i].code)


                val request = Request.Builder().url("https://api.trackingmore.com/v2/trackings/"
                        + courier_list!![i].code + "/" + tracking_input!!.text.toString())
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Trackingmore-Api-Key", "de1ba7aa-a0e7-49d4-8f60-8a9010002907")
                        .build()

                println("couries_2_code" + courier_list!![i].code)



                appController!!.okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("Errorrrrr " + e.message)
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val myResponse = response.body()!!.string()

                        //System.out.println("kaif"+myResponse);
                        json_res_list.add(myResponse)

                        System.out.println("katrina_" + json_res_list);


                        if (i == courier_list!!.size - 1) {
                            println("json_list" + json_res_list.size + " " + i)

                            if (tracking_list.size > 0)
                                tracking_list.clear()

                            set_up_Data(json_res_list)
                        }

                    }
                })


            }
        }


    }

    private fun set_up_Data(json_res_list2: ArrayList<String>) {

        data_rv!!.recycledViewPool.clear()


        println("result_size" + json_res_list2.size)
        println("result$json_res_list2")

         get_Origin_Code_List(json_res_list2)

        val output_code_list2 = get_code_name_from_Json_List(courier_list)


        println("output_code_lis" + output_code_list!!)//print tracking/couries codes according result response



        val json_list_position = get_json_list_position(code_list, output_code_list)

        println("kaif_position$json_list_position")


        var i = 0
        while (i < json_res_list2.size) {

            println("loop_run")
            try {
                var mainjson: JSONObject? = null


                if (json_list_position != -1) {
                    mainjson = JSONObject(json_res_list2[i])

                } else {
                    mainjson = JSONObject(json_res_list2[json_list_position])
                    i=json_res_list2.size

                }



                val meta = mainjson.getJSONObject("meta")

                println("mainJson$i$mainjson")


                if (meta.getString("code") == "200") {


                    val data = mainjson.getJSONObject("data")
                    val origin_info = data.getJSONObject("origin_info")

                    var trackinfo: JSONArray? = null

                    if (origin_info.getString("trackinfo") !== "null") {
                        trackinfo = origin_info.getJSONArray("trackinfo")

                        for (k in trackinfo!!.length() - 1 downTo 0) {
                            val item = trackinfo.getJSONObject(k)
                            tracking_list.add(Tracking_Modal(item.getString("Date"), item.getString("checkpoint_status"), item.getString("StatusDescription")))

                        }//track info loop end

                        if (tracking_list.size > 0) {


                            val status2 = data.getString("status")
                            val weight2 = data.getString("weight")
                            val expected_date2 = data.getString("created_at")
                            val date = expected_date2.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val shipped_via2 = courier_list!![i].name


                            this@MainActivity?.runOnUiThread {
                                order_id_title!!.text = "ORDER TRACKING:\n" + tracking_input!!.text.toString()
                                order_id_title!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.share_icon, 0)
                                status!!.text = status2
                                weight!!.text = weight2

                                weight!!.text = if (weight2.equals("")) "NA" else weight2

                                shipped_via!!.text = shipped_via2
                                expected_date!!.text = getDate(date[0])

                                //insert into history

                                check_Track_Record_From_Table(tracking_input!!.text.toString(),
                                        status!!.text.toString(),
                                        data
                                )
                            }


                            visible_tracking_data_layout()


                            json_res_list.clear()//cause new data will be stored
                            break
                        }


                    }//for track info
                    else {

                        json_res_list.clear()//cause new data will be stored
                        println("no_tracking_record_found_and_size_finallist" + tracking_list.size)

                        //clear RV data,
                        // gone 4 box layout
                        // ,gone animation,
                        // gone tracking title
                        clear_data_and_RV_Data("from_fetch_tracking_no_tracking_found")


                        //gone tracking_layout,
                        // gone no_internet_layout,
                        // visible no_shipment_found layout
                        gone_tracking_data_layout("from_fetch_tracking_no_tracking_found")
                    }


                }//if check code stauts
                else {
                    json_res_list.clear()//cause new data will be stored
                    clear_data_and_RV_Data("from_fetch_tracking_status_unsuccess")
                    gone_tracking_data_layout("from_fetch_tracking_status_unsuccess")


                }


            } catch (e: JSONException) {
                json_res_list.clear()//cause new data will be stored
                clear_data_and_RV_Data("from_exception")
                gone_tracking_data_layout("from_exception")

                e.printStackTrace()
            }

            i++


        }


    }

    private fun get_Origin_Code_List(jsonResList2: ArrayList<String>): ArrayList<String>? {

        for (i in jsonResList2.indices) {

            try {
                val mainjson = JSONObject(jsonResList2.get(i))

                val data = mainjson.getJSONObject("data")
                output_code_list!!.add(data.getString("carrier_code"))

            } catch (e: JSONException) {
                e.printStackTrace()
            }


        }

        return output_code_list
    }


    private fun check_Track_Record_From_Table(tracking_no: String, status: String, jsondata: JSONObject) {

        println("check_Track_Record_From_Table" + tracking_no + " " + status + " " + jsondata)
        noteRepository!!.get_Tracking_One_Record(tracking_no).observe(this, object : Observer<List<Tracking_History_Modal>> {
            override fun onChanged(@Nullable modal: List<Tracking_History_Modal>) {

                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg voids: Void): Void? {


                        println("get_single_Record" + modal.size)
                        //println("data" + single.get(0).data)


                        //table content that record
                        if (modal.size > 0) {
                            if (modal.get(0).completed.equals("yes")) {

                                println("no_insert_and_no_update_data")

                            }
                            //order is not yet delivered
                            else {
                                // update_Data_To_Hisotry_Table(single.get(0).data + "\n\n\n" + newData)


                                if (status.equals("delivered"))
                                    update_Data_To_Hisotry_Table("yes", jsondata.toString(), tracking_no)
                                else
                                    update_Data_To_Hisotry_Table("no", jsondata.toString(), tracking_no)
                            }


                        } else {
                            println("insert_data")
                            //insert_data_To_Hisotory_Table(newData)


                            if (status.equals("delivered"))
                                insert_data_To_Hisotory_Table(get_Today_Date(), tracking_no, "yes", jsondata.toString())
                            else
                                insert_data_To_Hisotory_Table(get_Today_Date(), tracking_no, "no", jsondata.toString())


                        }




                        return null
                    }
                }.execute()


            }
        })


    }


    private fun insert_data_To_Hisotory_Table(date: String, tracking_no: String, completed: String, jsondata: String) {
        println("insert_data_To_Hisotory_Table_called" + date + " " + tracking_no + " " + completed + " " + jsondata)

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.insert_Tracking_History_Record(date, tracking_no, completed, jsondata)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }


    private fun update_Data_To_Hisotry_Table(completed: String, jsonData: String, tracking_no: String) {


        println("update_called" + completed + " " + tracking_no + " " + jsonData)

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {

                try {
                    noteRepository!!.update_Tracking_History_Data(completed, jsonData, tracking_no)

                } catch (e: Exception) {

                }

                return null
            }
        }.execute()


    }

    private fun get_Today_Date(): String {
        val date = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val today_date = formatter.format(date)


        return today_date as String

    }

    private fun get_json_list_position(code_list: ArrayList<String>, output_code_list: ArrayList<String>?): Int {

        var temp = false
        var value = -1


        try {

            for (i in code_list.indices) {
                for (j in output_code_list!!.indices) {

                    if (code_list[i].equals(output_code_list[j], ignoreCase = true)) {
                        temp = true

                        println("checkkk$i")
                        println("firsttt" + code_list[j])
                        println("seconddd" + output_code_list[j])

                        value = j

                    }
                    if (temp)
                        break
                }
                if (temp)
                    break
            }

        } catch (e: Exception) {

        }

        return value
    }

    private fun get_code_name_from_Json_List(json_res_list2: ArrayList<Courier_Modal>?): ArrayList<String>? {

        if (output_code_list != null)
            output_code_list!!.clear()

        for (i in json_res_list2!!.indices) {

            output_code_list!!.add(json_res_list2[i].code)


        }


        return output_code_list

    }


    private fun visible_tracking_data_layout() {
        this@MainActivity?.runOnUiThread {


            //tracking_result_adapter!!.notifyDataSetChanged()

            set_Up_Rv_Data(tracking_list)


            println("visible_tracking_data_layout_called")
            data_rv!!.smoothScrollToPosition(1)

            tracking_data_layout!!.visibility = View.VISIBLE
            no_shipment_layout!!.visibility = View.GONE
            no_internet_layout!!.visibility = View.GONE

            four_box_layout!!.visibility = View.VISIBLE
            order_id_title!!.visibility = View.VISIBLE

            loading_anim!!.visibility = View.GONE
            loading_anim!!.cancelAnimation()


            data_exist = true
        }


    }

    private fun visible_no_internet_layout() {
        this@MainActivity?.runOnUiThread {
            tracking_data_layout!!.visibility = View.VISIBLE
            no_shipment_layout!!.visibility = View.GONE
            no_internet_layout!!.visibility = View.VISIBLE
        }


    }

    private fun gone_tracking_data_layout(from_courier_code: String) {

        println("call_gone_tracking$from_courier_code")
        this@MainActivity?.runOnUiThread {
            tracking_data_layout!!.visibility = View.GONE
            no_shipment_layout!!.visibility = View.VISIBLE
            no_internet_layout!!.visibility = View.GONE
        }


    }

    private fun clear_data_and_RV_Data(from_courier_code: String) {

        println("clear_data_rv_$from_courier_code")

        this@MainActivity?.runOnUiThread {
            if (final_tracking_list != null)
                final_tracking_list!!.clear()

            tracking_result_adapter!!.notifyDataSetChanged()

            four_box_layout!!.visibility = View.GONE
            order_id_title!!.visibility = View.GONE


            loading_anim!!.visibility = View.GONE
            loading_anim!!.cancelAnimation()
        }
    }

    fun getDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        var date3: Date? = null
        try {
            date3 = inputFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return outputFormat.format(date3!!)
    }

    private fun setMenus() {
        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null)
        root!!.addView(guillotineMenu)

        val guillotineBuilder = GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)

        guillotineBuilder.setGuillotineListener(object : GuillotineListener {
            override fun onGuillotineOpened() {
                isGuillotineOpened = true

            }

            override fun onGuillotineClosed() {
                isGuillotineOpened = false
            }
        })
        guillotineBuilder.setStartDelay(RIPPLE_DURATION)
        guillotineBuilder.setActionBarViewForAnimation(toolbar)
        guillotineBuilder.setClosedOnStart(true)

        aniMenu = guillotineBuilder.build()
    }

    override fun onBackPressed() {

        if (!isGuillotineOpened) {
            finishAffinity()
        } else
            close_Menu()
    }

    private fun close_Menu() {

        this@MainActivity?.runOnUiThread { aniMenu!!.close() }
    }


    fun about_us(view: View) {
        close_Menu()
        startActivity(Intent(applicationContext, About_us::class.java))
    }

    fun goto_Tracking_List(view: View) {
        close_Menu()
        startActivity(Intent(applicationContext, Tracking_History_List::class.java))
    }

    companion object {

        private val RIPPLE_DURATION: Long = 100

        fun hideKeyboardFrom(context: Context, view: View) {

            try {

                val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }


        }
    }

    fun share_app(view: View) {
        close_Menu()

        val link = "https://play.google.com/store/apps/details?id=com.digitalforgeco.packchase&ddl=1&pcampaignid=web_ddl_1"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareBodyText = "Share app with your friends";
        intent.putExtra(Intent.EXTRA_SUBJECT, shareBodyText)
        intent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(Intent.createChooser(intent, "Choose sharing method"))

    }
}
