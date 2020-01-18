package com.example.currencyconverter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.currencyconverter.R
import kotlinx.android.synthetic.main.activity_news__description.*

class News_Description : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news__description)

        news_title.text=intent.getStringExtra("title").toString()
        description.text=Html.fromHtml(intent.getStringExtra("description")).toString()
        Glide.with(applicationContext).load(intent.getStringExtra("image")).apply(RequestOptions().placeholder(R.drawable.blank_cur)).thumbnail(0.01f).into(image)



    }

    fun back(view: View) {
        onBackPressed()
    }
}
