package com.greenusys.allen.vidyadashboard.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenusys.allen.vidyadashboard.R;
import com.greenusys.allen.vidyadashboard.model.Chat;


import java.util.Collections;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
    List<Chat> data= Collections.emptyList();

    public ChatRecyclerAdapter(Context context, List<Chat> data){
        this.inflater = LayoutInflater.from(context);
        this.data=data;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_chat_student, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         Chat current = data.get(position);
        if (current.getUser_type() == 0){
            holder.chatMessageTeacher.setText(current.getMessage());
            holder.chatMessageTeacher.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            holder.chatMessageTeacher.setBackgroundResource(R.drawable.balloon_outgoing_normal);
            RecyclerView.LayoutParams relativeParams = (RecyclerView.LayoutParams)holder.rlChat.getLayoutParams();
            relativeParams.setMargins(50, 5, 0, 5);  // left, top, right, bottom
            holder.rlChat.setLayoutParams(relativeParams);

        }else{
            holder.chatMessageTeacher.setText(current.getMessage());
            holder.chatMessageTeacher.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            holder.chatMessageTeacher.setBackgroundResource(R.drawable.balloon_incoming_normal);
            RecyclerView.LayoutParams relativeParams = (RecyclerView.LayoutParams)holder.rlChat.getLayoutParams();
            relativeParams.setMargins(0, 5, 50, 5);  // left, top, right, bottom
            holder.rlChat.setLayoutParams(relativeParams);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView chatMessageTeacher;
        //CardView cvChatItemTeacher;
        RelativeLayout rlChat;
        public MyViewHolder(View itemView) {
            super(itemView);
            chatMessageTeacher = (TextView)itemView.findViewById(R.id.tvChatMessageStudent);
          //  cvChatItemTeacher = (CardView)itemView.findViewById(R.id.cvChatItemStudent);
            rlChat = (RelativeLayout)itemView.findViewById(R.id.rlChatItem);
        }
    }
}