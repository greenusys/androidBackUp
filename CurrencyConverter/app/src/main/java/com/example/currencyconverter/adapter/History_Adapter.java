package com.example.currencyconverter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.example.currencyconverter.activity.MainActivity;
import com.example.currencyconverter.modal.History;
import com.example.menu_library.FButton;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.ViewHolder> {
    private final MainActivity activity;
    //   private final Activity activity;
    Context context;
    ArrayList<History> list;
    ArrayList<String> img_list;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;


    public History_Adapter(Context context, ArrayList<History> friendRequestModels) {
        this.context = context;
        this.list = friendRequestModels;

        activity = (MainActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.country_name.setText(list.get(position).getCountry_name());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //System.out.println("running>=9");
            ImageDecoder.Source source = ImageDecoder.createSource(ByteBuffer.wrap(list.get(position).getCountry_image()));
            try {
                // ImageDecoder.decodeBitmap(source);
                holder.country_image.setImageBitmap(ImageDecoder.decodeBitmap(source));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //  System.out.println("running<=9");

            final Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getCountry_image(), 0, list.get(position).getCountry_image().length);
            holder.country_image.setImageBitmap(bitmap);

        }


        holder.set_as_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.set_Data_From_History_Adapter("first", list.get(position).getCountry_code3_letter(), list.get(position).getCountry_image());
            }
        });

        holder.set_as_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.set_Data_From_History_Adapter("second", list.get(position).getCountry_code3_letter(), list.get(position).getCountry_image());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country_name;
        ImageView country_image;
        FButton set_as_from, set_as_to;

        public ViewHolder(View itemView) {
            super(itemView);


            country_name = itemView.findViewById(R.id.country_name);
            country_image = itemView.findViewById(R.id.country_image);
            set_as_from = itemView.findViewById(R.id.set_as_from);
            set_as_to = itemView.findViewById(R.id.set_as_to);

        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}