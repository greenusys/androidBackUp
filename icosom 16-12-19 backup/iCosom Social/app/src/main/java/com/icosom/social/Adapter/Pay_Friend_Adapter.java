package com.icosom.social.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.model.PassbookModel;
import com.icosom.social.Pay_Friend_Activity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by laxmi.
 */
public class Pay_Friend_Adapter extends RecyclerView.Adapter<Pay_Friend_Adapter.ItemRowHolder> {

    private ArrayList<PassbookModel> dataList;
    private Context mContext;
    private int rowLayout;

    TextView latest;

    public Pay_Friend_Adapter(Context context, ArrayList<PassbookModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final PassbookModel singleItem = dataList.get(position);



            holder.friend_name.setText(singleItem.getFirstName());
             Glide.with(mContext).load(singleItem.getImage()).into(holder.friend_image);
            holder.status.setText(singleItem.getStatus());
            holder.amount.setText(singleItem.getAmount());
            holder.transaction_id.setText(singleItem.getTransaction_id());
            holder.date.setText(singleItem.getCurrentdate());




            holder.pay_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(v.getRootView().getContext());
                    View mView = layoutInflaterAndroid.inflate(R.layout.alert_box_layout, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(v.getRootView().getContext());
                    alertDialogBuilderUserInput.setView(mView);


                    final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {



                                    Intent intent = new Intent(mContext, Pay_Friend_Activity.class);
                                    intent.putExtra("user_device_token",singleItem.getDevice_tokens());
                                    intent.putExtra("friend_id",singleItem.getId());
                                    intent.putExtra("friend_email",singleItem.getFriend_email());
                                    intent.putExtra("user_id",singleItem.getUser_id());
                                    intent.putExtra("user_email",singleItem.getUser_email());
                                    intent.putExtra("amount",userInputDialogEditText.getText().toString());
                                    intent.putExtra("user_name",singleItem.getFirstName()+" "+singleItem.getLastName());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    mContext.startActivity(intent);



                                }
                            })

                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    });

                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                   alertDialogAndroid.show();





                }
            });













            /*

        holder.pay_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();

                        if(holder.editamount.getText().toString().equals("")) {
                            holder.editamount.setError("Please Enter the ammount");
                            return;
                          //  Toast.makeText(mContext, "Please Enter the amount", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                          //  Toast.makeText(mContext, "Yesssss", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), Pay_Friend_Activity.class);
                            intent.putExtra("friend_id",singleItem.getId());
                            intent.putExtra("friend_email",singleItem.getFriend_email());
                            intent.putExtra("user_id",singleItem.getUser_id());
                            intent.putExtra("user_email",singleItem.getUser_email());
                           // intent.putExtra("amount",holder.editamount.getText().toString());
                            intent.putExtra("user_name",singleItem.getFirstName()+" "+singleItem.getLastName());
                            mContext.startActivity(intent);

                        }


                    }
                }
        );
*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        CircleImageView friend_image;
        TextView friend_name,status,amount,transaction_id,date,editamount,pay_button;



        ItemRowHolder(View itemView) {
            super(itemView);
            friend_name = itemView.findViewById(R.id.friend_name);
            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            friend_image = itemView.findViewById(R.id.friend_image);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            date = itemView.findViewById(R.id.date);
            //editamount = itemView.findViewById(R.id.editamount);
            pay_button = itemView.findViewById(R.id.pay_button);



        }
    }
}
