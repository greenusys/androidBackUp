package com.example.digitalforgeco.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalforgeco.R
import com.example.digitalforgeco.adapter.Tracking_Result_Adapter
import com.example.digitalforgeco.modal.Tracking_Modal
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Show_Tracking_History : AppCompatActivity() {


    internal var shipped_via: TextView? = null
    internal var status: TextView? = null
    internal var weight: TextView? = null
    internal var expected_date: TextView? = null
    internal var order_id_title: TextView? = null
    internal var tracking_data_layout: LinearLayout? = null
    internal var tracking_list = ArrayList<Tracking_Modal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_details_layout)

        initViews()
    }

    private fun initViews() {

        val title_image: ImageView = findViewById(R.id.title_image)
        val title_bar: View = findViewById(R.id.title_bar)
        title_bar.visibility = View.VISIBLE
        title_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.back_arrow))
        title_image.setOnClickListener(View.OnClickListener {

            onBackPressed()
        })


        shipped_via = findViewById(R.id.shipped_via)
        status = findViewById(R.id.status)
        weight = findViewById(R.id.weight)
        expected_date = findViewById(R.id.expected_date)
        order_id_title = findViewById(R.id.order_id_title)
        tracking_data_layout = findViewById(R.id.tracking_data_layout)


        val jsondata = intent.getStringExtra("jsondata");

        try {
            set_Data_To_Layout(jsondata)

        }catch (e:Exception)
        {
            e.printStackTrace()
        }



    }

    private fun set_Data_To_Layout(jsondata: String?) {
        val data: JSONObject = JSONObject(jsondata)



        status!!.text = data.getString("status");
        shipped_via!!.text = data.getString("carrier_code");
        weight!!.text = data.getString("weight");

        weight!!.text=if(data.getString("weight").equals("")) "NA" else data.getString("weight")


        expected_date!!.text = getDate(data.getString("created_at"));
        order_id_title!!.text = "ORDER TRACKING:\n" + data.getString("tracking_number")


        Visible_Box_Layout()

        val origin_info = data.getJSONObject("origin_info")
        var trackinfo: JSONArray? = null


        if (origin_info.getString("trackinfo") !== "null") {
            trackinfo = origin_info.getJSONArray("trackinfo")

            for (k in trackinfo!!.length() - 1 downTo 0) {
                val item = trackinfo.getJSONObject(k)
                tracking_list.add(Tracking_Modal(item.getString("Date"), item.getString("checkpoint_status"), item.getString("StatusDescription")))

            }//track info loop end

            set_Time_line_Data()


        } else {

            /* json_res_list.clear()//cause new data will be stored
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
      */
        }


    }

    private fun set_Time_line_Data() {

        this@Show_Tracking_History?.runOnUiThread {

            val tracking_result_adapter: Tracking_Result_Adapter = Tracking_Result_Adapter(tracking_list)
            val timeline_rv: RecyclerView = findViewById(R.id.tracking_rv)


            timeline_rv!!.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)


            timeline_rv!!.adapter = tracking_result_adapter
            tracking_result_adapter.notifyDataSetChanged()
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

    private fun Visible_Box_Layout() {

        this@Show_Tracking_History?.runOnUiThread {

            tracking_data_layout!!.visibility = View.VISIBLE;


            if (tracking_data_layout!!.getLayoutParams() is ViewGroup.MarginLayoutParams) {
                val p = tracking_data_layout!!.getLayoutParams() as ViewGroup.MarginLayoutParams
                p.setMargins(0, 110, 0, 0)
                tracking_data_layout!!.requestLayout()
            }
        }


    }


    /* private fun set_up_Data(json_res_list2: ArrayList<String>) {

         println("result_size" + json_res_list2.size)
         println("result$json_res_list2")

         val output_code_list = get_code_name_from_Json_List(courier_list)
         println("output_code_lis" + output_code_list!!)//print tracking codes


         val json_list_position = get_json_list_position(code_list, output_code_list)

         println("kaif_position$json_list_position")


         var i = 0
         while (i < json_res_list2.size) {

             println("loop_run")
             try {
                 var mainjson: JSONObject? = null

                 if (json_list_position != -1)
                     mainjson = JSONObject(json_res_list2[i])
                 else {
                     mainjson = JSONObject(json_res_list2[json_list_position])
                     i = json_res_list2.size
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


                             this@Show_Tracking_History?.runOnUiThread {
                                 order_id_title!!.text = "ORDER TRACKING:\n" + tracking_input!!.text.toString()
                                 order_id_title!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.share_icon, 0)
                                 status!!.text = status2
                                 weight!!.text = weight2
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

     private fun get_json_list_position(code_list: ArrayList<String>, output_code_list: ArrayList<String>?): Int {

         var temp = false
         var value = -1


         try {

             for (i in code_list.indices) {
                 for (j in output_code_list!!.indices) {

                     if (code_list[i].equals(output_code_list[j], ignoreCase = true)) {
                         temp = true

                         println("check$i")
                         println("first" + code_list[j])
                         println("second" + output_code_list[j])

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
         this@Show_Tracking_History?.runOnUiThread {
             tracking_result_adapter!!.notifyDataSetChanged()

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
         this@Show_Tracking_History?.runOnUiThread {
             tracking_data_layout!!.visibility = View.VISIBLE
             no_shipment_layout!!.visibility = View.GONE
             no_internet_layout!!.visibility = View.VISIBLE
         }


     }

     private fun gone_tracking_data_layout(from_courier_code: String) {

         println("call_gone_tracking$from_courier_code")
         this@Show_Tracking_History?.runOnUiThread {
             tracking_data_layout!!.visibility = View.GONE
             no_shipment_layout!!.visibility = View.VISIBLE
             no_internet_layout!!.visibility = View.GONE
         }


     }

     private fun clear_data_and_RV_Data(from_courier_code: String) {

         println("clear_data_rv_$from_courier_code")

         this@Show_Tracking_History?.runOnUiThread {
             if (final_tracking_list != null)
                 final_tracking_list!!.clear()

             tracking_result_adapter!!.notifyDataSetChanged()

             four_box_layout!!.visibility = View.GONE
             order_id_title!!.visibility = View.GONE


             loading_anim!!.visibility = View.GONE
             loading_anim!!.cancelAnimation()
         }
     }*/

}
