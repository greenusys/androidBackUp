package com.icosom.social.recycler_adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.R;
import com.icosom.social.activity.CommentActivity;
import com.icosom.social.activity.Comment_Reply_Activity;
import com.icosom.social.model.Comment_Reply_Model;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment_Reply_Adapter extends RecyclerView.Adapter<Comment_Reply_Adapter.MyViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    ArrayList<Comment_Reply_Model> commentModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    String userId;

    public Comment_Reply_Adapter(Context context, ArrayList<Comment_Reply_Model> commentModels, String userId) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.userId = userId;
        this.commentModels = commentModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER)
        {
            View view = inflater.inflate(R.layout.tag_friends_recycler_header_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }
        else
        {
            View view = inflater.inflate(R.layout.item_comment_section, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        if (position != getItemCount()-1)
        {
            holder.reply.setVisibility(View.GONE);

            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+commentModels.get(position).getProfilePicture()).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(holder.iv_profileImg);

            holder.txt_comment.setText(commentModels.get(position).getComment());
            holder.txt_name.setText(commentModels.get(position).getName());
            holder.txt_time.setText(commentModels.get(position).getTime());

           //user can delete only their comments
            //holder.txt_moreInComments.setVisibility(userId.equalsIgnoreCase(commentModels.get(position).getUserId())?View.VISIBLE:View.GONE);

            //user can delete only their comments


           //if self post then user can delete all comments from their post
            //holder.all_delete_menu.setVisibility(userId.equalsIgnoreCase(CommentActivity.post_user_id)?View.VISIBLE:View.GONE);

            if(userId.equalsIgnoreCase(CommentActivity.post_user_id))
            {
                holder.all_delete_menu.setVisibility(View.VISIBLE);
                holder.txt_moreInComments.setVisibility(View.GONE);
            }
            else
            {
                if(userId.equalsIgnoreCase(commentModels.get(position).getUserId()))
                {
                    holder.txt_moreInComments.setVisibility(View.VISIBLE);

                }
                else
                {
                    holder.txt_moreInComments.setVisibility(View.GONE);

                }

                holder.all_delete_menu.setVisibility(View.GONE);

            }




            System.out.println("kaif_pata_self"+userId.equalsIgnoreCase(commentModels.get(position).getUserId()));

            System.out.println("kaif_pata_all"+userId.equalsIgnoreCase(CommentActivity.post_user_id));


            holder.txt_moreInComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Comment_Reply_Activity)context).showBottomSheet(commentModels.get(position).getAct_id(), position);
                }
            });

            holder.all_delete_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Comment_Reply_Activity)context).showBottomSheet(commentModels.get(position).getAct_id(), position);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size()+1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_profileImg;
        TextView txt_comment;
        TextView txt_time;
        TextView txt_name;
        TextView reply;
        ImageView txt_moreInComments;
        ImageView all_delete_menu;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_comment = itemView.findViewById(R.id.txt_comment);
            iv_profileImg = itemView.findViewById(R.id.iv_profileImg);
            txt_moreInComments = itemView.findViewById(R.id.txt_moreInComments);//delete only their comments
            all_delete_menu = itemView.findViewById(R.id.all_delete);//use can delete all their post comments
            reply = itemView.findViewById(R.id.reply);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}