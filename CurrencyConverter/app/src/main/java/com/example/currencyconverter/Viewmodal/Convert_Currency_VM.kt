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


class Convert_Currency_VM : ViewModel() {
    var res_object: MutableLiveData<JSONObject>? = null
    var mul_res_object: MutableLiveData<JSONObject>? = null

    lateinit var apiInterface: ApiInterface


    fun convert_Currency(url: String): LiveData<JSONObject> {

        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        res_object = MutableLiveData()
        convert_Currency_API(url, "first")

        return res_object as MutableLiveData<JSONObject>

    }

    fun convert_mul_Currency(url: String): LiveData<JSONObject> {

        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        mul_res_object = MutableLiveData()
        convert_Currency_API(url, "second")


        return mul_res_object as MutableLiveData<JSONObject>

    }


    fun convert_Currency_API(url: String, value: String) {
        println("api_url$url")
        val call = apiInterface.convert_Currency(url)
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                val postResponse = response.body()

                println("api_response" + postResponse)


                var res: JSONObject? = null
                try {
                    res = JSONObject(postResponse.toString())

                    if (value.equals("first"))
                        res_object!!.value = res

                    if (value.equals("second"))
                        mul_res_object!!.value = res

                    println("api_Json_response" + res)


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
