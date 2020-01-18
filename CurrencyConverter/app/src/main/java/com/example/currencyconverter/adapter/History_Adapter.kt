package com.example.currencyconverter.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.activity.MainActivity
import com.example.currencyconverter.modal.Favourite_Modal
import com.example.currencyconverter.modal.History
import com.example.menu_library.FButton
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

class History_Adapter(//   private final Activity activity;
        internal var context: Context, internal var list: ArrayList<History>,
        var fvrt_list: ArrayList<String>)
    : RecyclerView.Adapter<History_Adapter.ViewHolder>() {


    private val activity: MainActivity
    internal var img_list: ArrayList<String>? = null


    init {

        activity = context as MainActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.country_name.text = list[position].country_name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //System.out.println("running>=9");
            val source = ImageDecoder.createSource(ByteBuffer.wrap(list[position].country_image))
            try {
                // ImageDecoder.decodeBitmap(source);
                holder.country_image.setImageBitmap(ImageDecoder.decodeBitmap(source))
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else {
            //  System.out.println("running<=9");

            val bitmap = BitmapFactory.decodeByteArray(list[position].country_image, 0, list[position].country_image.size)
            holder.country_image.setImageBitmap(bitmap)

        }

        if (fvrt_list.size >0 && fvrt_list.contains(list[position].country_name))
            holder.favourite.setImageResource(R.drawable.ic_like_fill)
        else
            holder.favourite.setImageResource(R.drawable.ic_like)



        holder.favourite.setOnClickListener()
        {

            //already added to fvrt list
            if (fvrt_list.size >0 && fvrt_list.contains(list[position].country_name)) {
                holder.favourite.setImageResource(R.drawable.ic_like)//do unfvrt
                //remove value from SP
                activity.remove_Fvrt_From_SP(list[position].country_name.substring(0,4))
                //notifyDataSetChanged()

            } else {
                holder.favourite.setImageResource(R.drawable.ic_like_fill)//do fvrt
                activity.add_Favourite_Country(list[position].country_name)
                //notifyDataSetChanged()
            }
        }


        holder.set_as_from.setOnClickListener { activity.set_Data_From_History_Adapter("first", list[position].country_code3_letter, list[position].country_image) }

        holder.set_as_to.setOnClickListener { activity.set_Data_From_History_Adapter("second", list[position].country_code3_letter, list[position].country_image) }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var country_name: TextView
        internal var country_image: ImageView
        internal var set_as_from: FButton
        internal var set_as_to: FButton
        internal var favourite: ImageView

        init {


            country_name = itemView.findViewById(R.id.country_name)
            country_image = itemView.findViewById(R.id.country_image)
            favourite = itemView.findViewById(R.id.favourite)
            set_as_from = itemView.findViewById(R.id.set_as_from)
            set_as_to = itemView.findViewById(R.id.set_as_to)

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM_NORMAL

    }

    companion object {

        private val TYPE_HEADER = 0
        private val TYPE_ITEM_NORMAL = 1
    }
}