package com.example.g116.vvn_social.Adapter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Modal.NotificationModel;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Tab_Layout_Fragments.NotificationFragment;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    public Context context;
    private ArrayList<NotificationModel> notificationModels;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    static NotificationFragment fragment;

    public NotificationRecyclerAdapter(Context context, ArrayList<NotificationModel> notificationModels, NotificationFragment fragment)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.fragment = fragment;
        this.notificationModels = notificationModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            View view = inflater.inflate(R.layout.tag_friends_recycler_header_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }
        else if (viewType == TYPE_ITEM_NORMAL)
        {
            View view = inflater.inflate(R.layout.notification_layout_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position != 0)
        {
            if (notificationModels.get(position-1).getReadStatus().equalsIgnoreCase("0"))
                holder.noti_lay.setBackgroundColor(Color.parseColor("#e4f5f5"));
            else
                holder.noti_lay.setBackgroundColor(Color.parseColor("#FFFFFF"));


           /* holder.noti_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    onBindViewHolder(holder,position);


                }
            });
*/


           Glide.
                    with(context).
                    load(notificationModels.get(position-1).getProfilePicture()).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    thumbnail(0.01f).
                    into(holder.iv_img);

           holder.txt_time.setText(notificationModels.get(position-1).getTime());

           // holder.txt_time.setText(notificationModels.get(position-1).get());

            String body = notificationModels.get(position-1).getFirstName()+" "+
                    notificationModels.get(position-1).getLastName()+" has "+
                    notificationModels.get(position-1).getAction()+" on a post.";

            int str1 = notificationModels.get(position-1).getFirstName().length()+" ".length()+
                    notificationModels.get(position-1).getLastName().length();

            SpannableString text = new SpannableString(body);


         ClickableSpan cs1 = new ClickableSpan()
            {
                @Override
                public void onClick(View view)
                {

                   // Toast.makeText(context, "kaif", Toast.LENGTH_SHORT).show();
                    fragment.goToSinglePost(position-1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };


           text.setSpan(cs1, 0, str1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.txt_notiBody.setText(text);
            holder.txt_notiBody.setMovementMethod(LinkMovementMethod.getInstance());
            holder.txt_notiBody.setHighlightColor(Color.TRANSPARENT);


            //open friend's profile
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("user_type","student").
                            putExtra("userId", notificationModels.get(position-1).getUserToId()));


                }
            });

            //open notification's post
            holder.open_notification_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fragment.goToSinglePost(position-1);
                   // Toast.makeText(context, "kaif", Toast.LENGTH_SHORT).show();
                  //  fragment.goToSinglePost(position-1);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (notificationModels.size()+1);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_img;
        TextView txt_notiBody;
        TextView txt_time;
        LinearLayout open_notification_post;
        LinearLayout noti_lay;


        public MyViewHolder(View itemView) {
            super(itemView);
            open_notification_post = itemView.findViewById(R.id.open_notification_post);
            noti_lay = itemView.findViewById(R.id.noti_lay);
            iv_img = (CircleImageView) itemView.findViewById(R.id.iv_img);
            txt_notiBody = itemView.findViewById(R.id.txt_notiBody);
            txt_time = itemView.findViewById(R.id.txt_time);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // fragment.goToSinglePost(getAdapterPosition()-1);
                }
            });*/



        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;

        return TYPE_ITEM_NORMAL;
    }


}