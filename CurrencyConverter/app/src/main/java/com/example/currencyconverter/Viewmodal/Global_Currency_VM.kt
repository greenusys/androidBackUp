package com.example.currencyconverter.Viewmodal


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.Retrofit.ApiClient
import com.example.currencyconverter.Retrofit.ApiInterface
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Global_Currency_VM : ViewModel() {
    var global_res_object: MutableLiveData<JSONObject>? = null

    lateinit var apiInterface: ApiInterface



    fun convert_Currency(url: String, graph: Boolean, cur: String, dater: String): LiveData<JSONObject> {

        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        global_res_object = MutableLiveData()
        convert_Currency_API(url,graph,cur,dater)

        return global_res_object as MutableLiveData<JSONObject>

    }



    fun convert_Currency_API(url: String, graph: Boolean, cur: String, dater: String) {
        println("api_url$url")

        var call:Call<Any>?=null

        if(graph)
         call = apiInterface.fetch_GraphData(url,cur,dater)
        else
         call = apiInterface.fetch_Global_CUrrency(url)


        call!!.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                val postResponse = response.body()

                println("api_response" + postResponse)


                var res: JSONObject? = null
                try {
                    res = JSONObject(postResponse.toString())
                    global_res_object!!.value=res

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("Failed" + t.message)
                call.cancel()

            }
        })


    }


}
