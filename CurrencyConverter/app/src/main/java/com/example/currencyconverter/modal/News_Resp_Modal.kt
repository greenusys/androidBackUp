package com.example.currencyconverter.modal

import java.util.ArrayList

class News_Resp_Modal {

    var code: String? = null
    var data: ArrayList<News_Res_List>? = null


    inner class News_Res_List {
        internal var blog_id: String? = null
        internal var blog_image: String? = null
        internal var blog_title: String? = null
        internal var blog_content: String? = null
        internal var blog_date: String? = null


    }




}