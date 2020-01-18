package com.icosom.social.Icosom_Chat_Package.activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.ChatActivity;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.Chat_Main_Activity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Recent_Chat_Model;
import com.icosom.social.R;
import com.icosom.social.activity.MainActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recent_Chat_List_Adapter extends RecyclerView.Adapter<Recent_Chat_List_Adapter.ViewHolder>   implements Filterable
{
    private final Activity activity;
    Context context;
    ArrayList<Recent_Chat_Model> friendRequestModels;
    ArrayList<Recent_Chat_Model> friendlistfilter;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;


    public Recent_Chat_List_Adapter(Context context, ArrayList<Recent_Chat_Model> friendRequestModels)
    {
        this.context = context;
        this.friendRequestModels = friendRequestModels;
        this.friendlistfilter = friendRequestModels;
        activity = (Activity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recent_chat_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        final String path = "https://icosom.com/social/postFiles/images/";
        final Recent_Chat_Model friend_list_model = friendlistfilter.get(position);

        System.out.println(ChatActivity.self_user_id+"adapter_self_id"+ChatActivity.self_user_id);
        System.out.println(ChatActivity.self_user_id+"adapter_receiver_id"+friend_list_model.getReceiver_id());

            if(friend_list_model.getRead_status().equals("0"))
            {
                if(!ChatActivity.self_user_id.equals(friend_list_model.getReceiver_id()))
                holder.main_layout.setBackgroundResource(R.color.light_grey);
            }
            else
                holder.main_layout.setBackgroundResource(R.color.white);


            holder.txt_name.setText(friend_list_model.getFirstName() + " "
                    + friend_list_model.getLastName());

            holder.first_message.setText(friend_list_model.getMessage());

        System.out.println("picture"+path+friend_list_model.getProfilePicture());

        Glide.with(context).
                load(path+friend_list_model.getProfilePicture()).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                       // holder.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }
                }).
                thumbnail(0.01f).
                apply(new RequestOptions().placeholder(R.drawable.chat_placeholder)).
                into(holder.iv_profile);

         holder.main_layout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 holder.main_layout.setBackgroundResource(R.color.white);
                 friendlistfilter.get(position).setRead_status("1");
                 notifyItemChanged(position);

                 context.startActivity(new Intent(context, ChatActivity.class)

                         .putExtra("self_user_id", Chat_Main_Activity.userID)
                         .putExtra("self_socket_id", MainActivity.user_socket_id)
                         .putExtra("friend_user_id",friend_list_model.getReceiver_id())
                         .putExtra("friend_socket_id","")
                         .putExtra("friend_name",friend_list_model.getFirstName()+" "+friend_list_model.getLastName())
                         .putExtra("friend_image",path+friend_list_model.getProfilePicture())
                         .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                 );
                 activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

             }
         });




    }

    @Override
    public int getItemCount()
    {
        return friendlistfilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    friendlistfilter = friendRequestModels;
                } else {
                    ArrayList<Recent_Chat_Model> filteredList = new ArrayList<>();
                    for (Recent_Chat_Model row : friendRequestModels) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) ) {
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
                friendlistfilter = (ArrayList<Recent_Chat_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_profile;
        TextView txt_name,first_message;
        TextView course_name;
        CardView main_layout;
        ProgressBar progress;


        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.image);
            main_layout = itemView.findViewById(R.id.main_layout);
            txt_name = itemView.findViewById(R.id.name);
            first_message = itemView.findViewById(R.id.first_message);

           // progress = itemView.findViewById(R.id.progress);

        }
    }

    /*@Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }*/
}