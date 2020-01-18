package com.greenusys.allen.vidyadashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.greenusys.allen.vidyadashboard.model.ChatItem;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterChatList extends ArrayAdapter<ChatItem> {

        List<ChatItem> animalList = new ArrayList<>();

        public AdapterChatList(Context context, int textViewResourceId, List<ChatItem> objects) {
            super(context, textViewResourceId, objects);
            animalList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_checked, null);
            ImageView ivChatProfile;
            TextView tvChatProfile;


            ivChatProfile = (ImageView)v.findViewById(R.id.iv_chatProfile);
            tvChatProfile = (TextView)v.findViewById(R.id.tv_chatProfile);
//            tvChatProfile.setTypeface(font_bold);

            tvChatProfile.setText(animalList.get(position).getProfileName());
            ivChatProfile.setImageResource(animalList.get(position).getProfileImage());
            Picasso.with(getContext()).load(animalList.get(position).getProfileImageUrl()).transform(new CircleTransform()).into(ivChatProfile);

            return v;

        }

    }

