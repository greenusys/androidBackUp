package com.icosom.social.Icosom_Chat_Package.activity.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.ChatActivity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.MessageFormat;
import com.icosom.social.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mess_Adapter extends RecyclerView.Adapter<Mess_Adapter.ViewHolder> {
    List<MessageFormat> list;
    Context context;
    String self_user_id;
    ChatActivity activity = new ChatActivity();


    public Mess_Adapter(List<MessageFormat> list, Context context, String self_user_id, String friend_image) {
        this.list = list;
        this.context = context;
        this.self_user_id = self_user_id;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {





        if(ChatActivity.today_date.equalsIgnoreCase(list.get(position).getMessage_date()))
        holder.date.setText("Today");
        else
            holder.date.setText(list.get(position).getMessage_date());



        holder.date.setVisibility(View.VISIBLE);

        if(position>0)
        {
            if(list.get(position).getMessage_date().equalsIgnoreCase(list.get(position-1).getMessage_date()))
            {
                holder.date.setVisibility(View.GONE);

            }
            else
            {

                if(ChatActivity.today_date.equalsIgnoreCase(list.get(position).getMessage_date()))
                    holder.date.setText("Today");
                else
                    holder.date.setText(list.get(position).getMessage_date());


                holder.date.setVisibility(View.VISIBLE);
            }


        }


        //for self layout
        if (list.get(position).getSender_id().equalsIgnoreCase(self_user_id)) {
            holder.my_message.setText(list.get(position).getText()+"                   ");
            holder.self_layout.setVisibility(View.VISIBLE);
            holder.friend_layout.setVisibility(View.GONE);


            String originalText=list.get(position).getTime()+"k";
            SpannableString spannableStr = new SpannableString(originalText);
            Drawable drawable = context.getResources().getDrawable(R.drawable.double_tick);
            drawable.setBounds(0, 0, 50, 50);
            ImageSpan imageSpan = new ImageSpan(drawable);
            spannableStr.setSpan(imageSpan, originalText.length()-1, originalText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.self_time.setText(spannableStr);


            //copy and delete menu
            holder.self_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //holder.self_layout.setBackgroundResource(R.color.light_grey);
                    PopupMenu popup = new PopupMenu(context, holder.self_layout);
                    popup.inflate(R.menu.message_menu);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        popup.setGravity(Gravity.END);
                    }

                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.copy:
                                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    _clipboard.setText(list.get(position).getText());
                                    Toast.makeText(context, "Copied Text", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.delete:
                                    System.out.println("kaif_message_id" + list.get(position).getMessage_id());
                                    if (!list.get(position).getMessage_id().equals(""))
                                        activity.deleteMessage(list.size(),list.get(position).getMessage_id(),list.get(position).getText());

                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    break;

                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();


                    return false;
                }


            });
        }

        //for friend layout
        else {
            holder.friend_message.setText(list.get(position).getText()+"                   ");
            holder.friend_layout.setVisibility(View.VISIBLE);
            holder.self_layout.setVisibility(View.GONE);
            holder.frnd_time.setText(list.get(position).getTime());

            String originalText=list.get(position).getTime()+"k";
            SpannableString spannableStr = new SpannableString(originalText);
            Drawable drawable = context.getResources().getDrawable(R.drawable.double_tick);
            drawable.setBounds(0, 0, 50, 50);
            ImageSpan imageSpan = new ImageSpan(drawable);
            spannableStr.setSpan(imageSpan, originalText.length()-1, originalText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.frnd_time.setText(spannableStr);



            holder.friend_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(list.get(position).getText());
                    Toast.makeText(context, "Copied Text", Toast.LENGTH_SHORT).show();
                    return false;

                }
            });


        }






    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView my_message;
        TextView friend_message,frnd_time,self_time,date;
        LinearLayout friend_layout;
        LinearLayout self_layout;
        CircleImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            my_message = itemView.findViewById(R.id.my_message);
            friend_message = itemView.findViewById(R.id.message_body);
           // friend_name = itemView.findViewById(R.id.name);
            //image = itemView.findViewById(R.id.image);

            friend_layout = itemView.findViewById(R.id.friend_layout);
            self_layout = itemView.findViewById(R.id.self_layout);
            frnd_time = itemView.findViewById(R.id.frnd_time);
            self_time = itemView.findViewById(R.id.self_time);
            date = itemView.findViewById(R.id.date);
        }
    }
}
