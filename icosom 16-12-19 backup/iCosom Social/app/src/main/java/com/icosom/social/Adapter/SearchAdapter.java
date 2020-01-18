package com.icosom.social.Adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.activity.SendMoney;
import com.icosom.social.model.SearchResult;
import com.icosom.social.utility.CommonFunctions;

import java.util.List;

/**
 * Created by Allen on 10/30/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
    private List<SearchResult> listModelSearchResult;

    public SearchAdapter(Context context, List<SearchResult> listModelSearchResult) {
        inflater = LayoutInflater.from(context);
        this.listModelSearchResult = listModelSearchResult;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_result, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SearchResult current = listModelSearchResult.get(position);
        Glide.with(context).load(CommonFunctions.FETCH_IMAGES + current.getProfilePicture()).into(holder.ivProfile);

        holder.tvName.setText(current.getFirstName().concat(" ").concat(current.getLastName()));
        holder.tvPlace.setText("Id : "+current.getId());
        holder.cvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String friend_id,friend_email,user_id,user_email,amount,user_name;*/

                ((SendMoney)context).user_device_token = current.getDevice_token();//user device token id

                ((SendMoney)context).friend_id = current.getId();//friend id
                ((SendMoney)context).friend_email = current.getEmail();//frind email
                ((SendMoney)context).friend_phone = current.getPhone();//frind email
                ((SendMoney)context).friend_name = current.getFirstName() +" "+ current.getLastName();//friend name



               // ((SendMoney)context).etAmountSendMoney.setVisibility(View.VISIBLE);
               // ((SendMoney)context).voucher_code.setVisibility(View.VISIBLE);
                ((SendMoney)context).tvSubmitSendMoney.setVisibility(View.VISIBLE);
                ((SendMoney)context).etSearchFriendSendMoney.setText(current.getFirstName()+" "+current.getLastName());
                ((SendMoney)context).rvSearchFriendSendMoney.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelSearchResult.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName;
        TextView tvPlace;
        CardView cvSearch;


        public MyViewHolder(View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile_search);
            tvName = (TextView)itemView.findViewById(R.id.tv_profile_name_search);
            tvPlace = (TextView)itemView.findViewById(R.id.tv_profile_place_search);
            cvSearch = (CardView)itemView.findViewById(R.id.cv_search);
        }
    }
}