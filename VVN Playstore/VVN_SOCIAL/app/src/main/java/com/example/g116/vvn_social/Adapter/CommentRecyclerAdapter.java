package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Home_Activities.CommentActivity;
import com.example.g116.vvn_social.Modal.CommentModel;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    CommentActivity commentActivity=new CommentActivity();
    ArrayList<CommentModel> commentModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    String userId="",post_id="",type="";
    AppController appController;
    TextView count_comments;
    TextView edt_comment;


    public CommentRecyclerAdapter(EditText edt_comment,Context context, ArrayList<CommentModel> commentModels, String userId, AppController appController, String post_id, String type,TextView count_comments) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.userId = userId;
        this.edt_comment = edt_comment;
        this.count_comments = count_comments;
        this.post_id = post_id;
        this.type = type;
        this.appController = appController;
        this.commentModels = commentModels;

    }

public CommentRecyclerAdapter()
{

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
            Glide.
                    with(context).
                    load(commentModels.get(position).getProfilePicture()).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(holder.iv_profileImg);

            holder.txt_comment.setText(commentModels.get(position).getComment());
            holder.txt_name.setText(commentModels.get(position).getFirstName()+" "+commentModels.get(position).getLastName());
            holder.txt_time.setText(commentModels.get(position).getActivityTime());

            holder.menu_button.setVisibility(userId.equalsIgnoreCase(commentModels.get(position).getUserId())?View.VISIBLE:View.GONE);

            holder.menu_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, holder.menu_button);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.comment_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    //Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                                    commentActivity.edit="edit";
                                    commentActivity.position=position;
                                    commentActivity.edt_comment.setText(commentModels.get(position).getComment());
                                    commentActivity.commentAdapter=new CommentRecyclerAdapter();
                                    commentActivity.edit_comment_id=commentModels.get(position).getCommentId();
                                    commentActivity.edit_text=commentModels.get(position).getComment();
                                    commentActivity.commentModels=commentModels;




                                    commentActivity.edit(commentModels,edt_comment,position,"edit");



                                    break;
                                case R.id.delete:
                                  //  Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();

                                    commentActivity.deleteComments(count_comments,commentModels,appController,commentModels.get(position).getCommentId(),post_id,type,userId);
                                    commentModels.remove(commentModels.get(position));
                                    notifyDataSetChanged();


                                    break;

                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();


                }
            });






            //open friend's profile
            holder.iv_profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("user_type",commentModels.get(position).getUser_type()).
                            putExtra("userId", commentModels.get(position).getUserId()));


                }
            });
            //open friend's profile
            holder.txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("user_type",commentModels.get(position).getUser_type()).
                            putExtra("userId", commentModels.get(position).getUserId()));


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
        ImageView menu_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_comment = itemView.findViewById(R.id.txt_comment);
            iv_profileImg = itemView.findViewById(R.id.iv_profileImg);
            menu_button = itemView.findViewById(R.id.menu_button);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }
}