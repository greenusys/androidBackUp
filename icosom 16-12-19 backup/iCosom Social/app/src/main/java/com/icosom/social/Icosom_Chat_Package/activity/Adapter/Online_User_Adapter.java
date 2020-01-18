package com.icosom.social.Icosom_Chat_Package.activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.ChatActivity;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.Chat_Main_Activity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Online_User_Model;
import com.icosom.social.R;
import com.icosom.social.activity.MainActivity;

import java.util.ArrayList;

public class Online_User_Adapter extends RecyclerView.Adapter<Online_User_Adapter.ViewHolder> implements Filterable
{
    ArrayList<Online_User_Model> list;
    ArrayList<Online_User_Model> friendlistfilter;
    Context context;
    String self_user_id;
    String self_socket_id;
    Activity activity;

    public Online_User_Adapter( Context context,ArrayList<Online_User_Model> list) {
        this.list = list;
        this.context = context;
        this.friendlistfilter = list;
         activity = (Activity) context;
       /* this.self_user_id = self_user_id;
        this.self_socket_id = self_socket_id;*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_show_friend_list_layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(friendlistfilter.get(position).getSender_user_name());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //slide from right to left

                context.startActivity(new Intent(context, ChatActivity.class)
                        .putExtra("self_user_id", Chat_Main_Activity.userID)
                        .putExtra("self_socket_id", MainActivity.user_socket_id)
                        .putExtra("friend_user_id", friendlistfilter.get(position).getSender_user_id())
                        .putExtra("friend_socket_id", friendlistfilter.get(position).getSender_socket_id())
                        .putExtra("friend_name", friendlistfilter.get(position).getSender_user_name())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendlistfilter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;
        LinearLayout main_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.image);
            main_layout=itemView.findViewById(R.id.main_layout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    friendlistfilter = list;
                } else {
                    ArrayList<Online_User_Model> filteredList = new ArrayList<>();
                    for (Online_User_Model row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSender_user_name().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    friendlistfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = friendlistfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                friendlistfilter = (ArrayList<Online_User_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
