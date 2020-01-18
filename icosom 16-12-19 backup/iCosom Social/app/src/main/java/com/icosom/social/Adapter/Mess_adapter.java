package com.icosom.social.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icosom.social.R;
import com.icosom.social.model.Mess_model;

import java.util.List;


public class Mess_adapter extends RecyclerView.Adapter<Mess_adapter.ViewHolder> {

    List<Mess_model> mess_models;
    String user_id;

    public Mess_adapter(List<Mess_model> mess_models, String user_id) {
        this.mess_models = mess_models;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Mess_model mess_model = mess_models.get(position);


        if(user_id.equalsIgnoreCase(mess_model.getSelf_id()))
        {
            holder.self_message.setText(mess_model.getSelf_message()+"\n"+mess_model.getSelf_message_time());
            holder.self_message.setVisibility(View.VISIBLE);
            holder.frnd_message.setVisibility(View.GONE);

        }
        else
        {
            holder.frnd_message.setText(mess_model.getFriend_message()+"\n"+mess_model.getFriend_message_time());
            holder.frnd_message.setVisibility(View.VISIBLE);
            holder.self_message.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return mess_models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView self_message;
        public TextView frnd_message;


        public ViewHolder(View itemView)
        {
            super(itemView);
            self_message = itemView.findViewById(R.id.self_message);
            Linkify.addLinks(self_message, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);
            frnd_message = itemView.findViewById(R.id.frnd_message);
            Linkify.addLinks(frnd_message, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);
        }
    }
}