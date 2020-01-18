package com.example.currencyconverter.Viewmodal


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.Retrofit.ApiClient
import com.example.currencyconverter.Retrofit.ApiInterface
import com.example.currencyconverter.modal.News_Resp_Modal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Fetch_News_VM : ViewModel() {
    var news_list_live: MutableLiveData<ArrayList<News_Resp_Modal.News_Res_List>>? = null

    lateinit var apiInterface: ApiInterface

    fun fetch_News_List(url: String): MutableLiveData<ArrayList<News_Resp_Modal.News_Res_List>> {

        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        news_list_live = MutableLiveData()
        fetch_News_List_API(url)

        return news_list_live as MutableLiveData<ArrayList<News_Resp_Modal.News_Res_List>>

    }


    fun fetch_News_List_API(url: String) {
        val call = apiInterface.get_News_List("2")
        call.enqueue(object : Callback<News_Resp_Modal> {
            override fun onResponse(call: Call<News_Resp_Modal>, response: Response<News_Resp_Modal>) {

                try {

                    if (response.isSuccessful) {

                        val news_modal: News_Resp_Modal = response.body() as News_Resp_Modal


                        if (news_modal.code.equals("1")) {
                            val news_list: ArrayList<News_Resp_Modal.News_Res_List>? = news_modal.data


                            news_list_live?.value = news_list


                            System.out.println("api_size" + news_list!!.size)
                        } else
                            news_list_live?.value = null

                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<News_Resp_Modal>, t: Throwable) {
                println("Failed" + t.message)
                news_list_live?.value = null
                call.cancel()

            }
        })


    }


}
