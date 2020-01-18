package com.icosom.social.Talent_Show_Package.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Activity.Play_Audio_Activity;
import com.icosom.social.Talent_Show_Package.Activity.Singing_And_Dancing_Activity;
import com.icosom.social.Talent_Show_Package.Activity.Talent_Show_Single_Post;
import com.icosom.social.Talent_Show_Package.Modal.Post_Link_Model;
import com.icosom.social.Talent_Show_Package.Modal.Singing_Model;
import com.icosom.social.Talent_Show_Package.Modal.Voting_Modal;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.ShowSubMediaActivity;
import com.icosom.social.utility.CommonFunctions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Singing_Adapter extends RecyclerView.Adapter<Singing_Adapter.MyViewHolder> {

    Context context;
    String video_path = "https://icosom.com/social/main/talent_uploads/";
    ArrayList<Voting_Modal> voting_list;
    boolean from;
    Activity activity;
    Singing_And_Dancing_Activity activity1;
    Talent_Show_Single_Post activity2;
    private LayoutInflater inflater;
    private ArrayList<Singing_Model.Singing_Model_List> singing_list;
    private ArrayList<Post_Link_Model> file_list;

    public Singing_Adapter(boolean from, Activity activity, Context context, ArrayList<Voting_Modal> voting_list, ArrayList<Singing_Model.Singing_Model_List> singing_list, ArrayList<Post_Link_Model> file_list) {
        inflater = LayoutInflater.from(context);
        this.singing_list = singing_list;
        this.file_list = file_list;
        this.voting_list = voting_list;
        this.context = context;
        this.from = from;
        this.activity = activity;

        if (from)
            activity2 = (Talent_Show_Single_Post) activity;
        else
            activity1 = (Singing_And_Dancing_Activity) activity;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.singing_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int ranking = position;

        holder.user_name.setText(singing_list.get(position).getFirstName() + " " + singing_list.get(position).getLastName());

        if (!from)
            holder.ranking_label.setText("Top #" + ++ranking);


        if (from)
            holder.more_icon.setVisibility(View.GONE);


        if (!voting_list.get(position).getTootal_Good().equals("0"))
            holder.good.setText("Good " + voting_list.get(position).getTootal_Good());
        else
            holder.good.setText("Good ");


        if (!voting_list.get(position).getTootal_V_Good().equals("0"))
            holder.very_good.setText("Very Good " + voting_list.get(position).getTootal_V_Good());
        else
            holder.very_good.setText("Very Good ");


        if (!voting_list.get(position).getTotal_Bad().equals("0"))
            holder.bad.setText("Bad " + voting_list.get(position).getTotal_Bad());
        else
            holder.bad.setText("Bad ");


        if (!voting_list.get(position).getTotal_V_Bad().equals("0"))
            holder.very_bad.setText("Very Bad " + voting_list.get(position).getTotal_V_Bad());
        else
            holder.very_bad.setText("Very Bad  ");

        holder.good_img.setImageResource(
                voting_list.get(position).getGood_Rank() ? R.drawable.circle_1 : R.drawable.ring
        );
        holder.vgood_img.setImageResource(
                voting_list.get(position).getV_Good_Rank() ? R.drawable.circle_2 : R.drawable.ring
        );
        holder.bad_img.setImageResource(
                voting_list.get(position).getBad_Ran() ? R.drawable.circle_3 : R.drawable.ring
        );
        holder.vbad_img.setImageResource(
                voting_list.get(position).getV_Bad_Rank() ? R.drawable.circle_3 : R.drawable.ring
        );

        holder.txt_postTime.setText(singing_list.get(position).getTimestamp());


        holder.status.setText(singing_list.get(position).getText_post());
        Glide.with(context).
                load(CommonFunctions.FETCH_IMAGES + singing_list.get(position).getProfilePicture()).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                thumbnail(0.01f).
                into(holder.user_profile);


        String extention = "";
        if (!file_list.get(position).getFiles().get(0).equals("")) {
            int length = file_list.get(position).getFiles().get(0).length();
            String str = file_list.get(position).getFiles().get(0);

            extention = str.substring(length - 4);
            System.out.println("kaif_mp3" + extention + " " + extention.equals(".mp3"));
        } else {
            System.out.println("kaif_empty");
        }


        //for music

        //   System.out.println("music");

        if (file_list.get(position).getFiles().size() == 0) {
            holder.postFrameA.setVisibility(View.GONE);
            holder.three_layout.setVisibility(View.GONE);
        } else if (file_list.get(position).getFiles().size() != 0) {

            if (extention.equals(".mp3") || extention.equals(".m4a") || extention.equals(".pcm") ||
                    extention.equals(".3gp") || extention.equals(".wav") || extention.equals(".ogg")) {
                System.out.println("music_true");

                if (file_list.get(position).getFiles().size() == 1) {


                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_A,
                            R.drawable.img_background);


                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.loading_icon1.setVisibility(View.GONE);
                    holder.video_icon_1.setVisibility(View.GONE);
                    holder.music_icoon_1.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.GONE);
                } else if (file_list.get(position).getFiles().size() == 2) {

                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_A,
                            R.drawable.img_background);


                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_B,
                            R.drawable.img_background);


                    holder.video_icon_1.setVisibility(View.GONE);
                    holder.video_icon_2.setVisibility(View.GONE);
                    holder.music_icoon_1.setVisibility(View.VISIBLE);
                    holder.music_icoon_2.setVisibility(View.VISIBLE);

                    holder.loading_icon1.setVisibility(View.GONE);
                    holder.loading_icon2.setVisibility(View.GONE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.INVISIBLE);
                    holder.postFrameD.setVisibility(View.INVISIBLE);

                } else if (file_list.get(position).getFiles().size() == 3) {


                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_A,
                            R.drawable.img_background);


                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_B,
                            R.drawable.img_background);

                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_C,
                            R.drawable.img_background);

                    holder.video_icon_1.setVisibility(View.GONE);
                    holder.video_icon_2.setVisibility(View.GONE);
                    holder.video_icon_3.setVisibility(View.GONE);

                    holder.loading_icon1.setVisibility(View.GONE);
                    holder.loading_icon2.setVisibility(View.GONE);
                    holder.loading_icon3.setVisibility(View.GONE);

                    holder.music_icoon_1.setVisibility(View.VISIBLE);
                    holder.music_icoon_2.setVisibility(View.VISIBLE);
                    holder.music_icoon_3.setVisibility(View.VISIBLE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.INVISIBLE);

                } else if (file_list.get(position).getFiles().size() == 4) {

                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_A,
                            R.drawable.img_background);


                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_B,
                            R.drawable.img_background);

                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_C,
                            R.drawable.img_background);
                    set_Success_Glide_Data_method_1(context,
                            "",
                            holder.postimg_D,
                            R.drawable.img_background);

                    holder.loading_icon1.setVisibility(View.GONE);
                    holder.loading_icon2.setVisibility(View.GONE);
                    holder.loading_icon3.setVisibility(View.GONE);
                    holder.loading_icon4.setVisibility(View.GONE);

                    holder.video_icon_1.setVisibility(View.GONE);
                    holder.video_icon_2.setVisibility(View.GONE);
                    holder.video_icon_3.setVisibility(View.GONE);
                    holder.video_icon_4.setVisibility(View.GONE);
                    holder.music_icoon_1.setVisibility(View.VISIBLE);
                    holder.music_icoon_2.setVisibility(View.VISIBLE);
                    holder.music_icoon_3.setVisibility(View.VISIBLE);
                    holder.music_icoon_4.setVisibility(View.VISIBLE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.VISIBLE);

                } else if (file_list.get(position).getFiles().size() > 4) {

                    holder.loading_icon1.setVisibility(View.GONE);
                    holder.loading_icon2.setVisibility(View.GONE);
                    holder.loading_icon3.setVisibility(View.GONE);
                    holder.loading_icon4.setVisibility(View.GONE);

                    holder.video_icon_1.setVisibility(View.GONE);
                    holder.video_icon_2.setVisibility(View.GONE);
                    holder.video_icon_3.setVisibility(View.GONE);
                    holder.video_icon_4.setVisibility(View.GONE);
                    holder.music_icoon_1.setVisibility(View.VISIBLE);
                    holder.music_icoon_2.setVisibility(View.VISIBLE);
                    holder.music_icoon_3.setVisibility(View.VISIBLE);
                    holder.music_icoon_4.setVisibility(View.VISIBLE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.VISIBLE);
                    holder.more_text.setVisibility(View.VISIBLE);


                }
            } else {
                //for video
                System.out.println("video_true");

                if (file_list.get(position).getFiles().size() == 1) {

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.GONE);
                    holder.music_icoon_1.setVisibility(View.GONE);


                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(0),
                            holder.video_icon_1,
                            holder.loading_icon1,
                            holder.postimg_A);


                } else if (file_list.get(position).getFiles().size() == 2) {

                    holder.music_icoon_1.setVisibility(View.GONE);
                    holder.music_icoon_2.setVisibility(View.GONE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.INVISIBLE);
                    holder.postFrameD.setVisibility(View.INVISIBLE);


                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(0),
                            holder.video_icon_1,
                            holder.loading_icon1,
                            holder.postimg_A);

                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(1),
                            holder.video_icon_2,
                            holder.loading_icon2,
                            holder.postimg_B);

                } else if (file_list.get(position).getFiles().size() == 3) {

                    holder.music_icoon_1.setVisibility(View.GONE);
                    holder.music_icoon_2.setVisibility(View.GONE);
                    holder.music_icoon_3.setVisibility(View.GONE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.INVISIBLE);


                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(0),
                            holder.video_icon_1,
                            holder.loading_icon1,
                            holder.postimg_A);

                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(1),
                            holder.video_icon_2,
                            holder.loading_icon2,
                            holder.postimg_B);
                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(2),
                            holder.video_icon_3,
                            holder.loading_icon3,
                            holder.postimg_C);


                } else if (file_list.get(position).getFiles().size() == 4) {

                    holder.music_icoon_1.setVisibility(View.GONE);
                    holder.music_icoon_2.setVisibility(View.GONE);
                    holder.music_icoon_3.setVisibility(View.GONE);
                    holder.music_icoon_4.setVisibility(View.GONE);

                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.VISIBLE);

                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(0),
                            holder.video_icon_1,
                            holder.loading_icon1,
                            holder.postimg_A);

                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(1),
                            holder.video_icon_2,
                            holder.loading_icon2,
                            holder.postimg_B);
                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(2),
                            holder.video_icon_3,
                            holder.loading_icon3,
                            holder.postimg_C);
                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(3),
                            holder.video_icon_4,
                            holder.loading_icon4,
                            holder.postimg_D);


                } else if (file_list.get(position).getFiles().size() > 4) {

                    holder.music_icoon_1.setVisibility(View.GONE);
                    holder.music_icoon_2.setVisibility(View.GONE);
                    holder.music_icoon_3.setVisibility(View.GONE);
                    holder.music_icoon_4.setVisibility(View.GONE);


                    holder.postFrameA.setVisibility(View.VISIBLE);
                    holder.three_layout.setVisibility(View.VISIBLE);
                    holder.postFrameB.setVisibility(View.VISIBLE);
                    holder.postFrameC.setVisibility(View.VISIBLE);
                    holder.postFrameD.setVisibility(View.VISIBLE);
                    holder.more_text.setVisibility(View.VISIBLE);


                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(0),
                            holder.video_icon_1,
                            holder.loading_icon1,
                            holder.postimg_A);

                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(1),
                            holder.video_icon_2,
                            holder.loading_icon2,
                            holder.postimg_B);
                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(2),
                            holder.video_icon_3,
                            holder.loading_icon3,
                            holder.postimg_C);
                    set_Success_Glide_Data(context,
                            video_path + file_list.get(position).getFiles().get(3),
                            holder.video_icon_4,
                            holder.loading_icon4,
                            holder.postimg_D);


                }

                holder.user_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("position" + position);
                        System.out.println("file_size" + file_list.get(position).getFiles().size());
                    }
                });


            }


        }


        holder.postimg_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Talent Show SIngle Post Activity
                if (from && activity2.format_list.get(position).equals("video")) {
                    System.out.println("john_video_if_"+activity2.format_list.get(position).equals("video")+" "+position);
                    setItemListener(position);
                }

                    //Singing ANd Dancing Acitivyt
                else if (!from && activity1.format_list.get(position).equals("video")) {
                    System.out.println("john_video_else_if_"+activity1.format_list.get(position).equals("video")+" "+position);
                    setItemListener(position);
                }

                else {
                    System.out.println("john_video_else_");
                    context.startActivity(new Intent(context, Play_Audio_Activity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("name", singing_list.get(position).getFile_name()).
                            putExtra("voice", file_list.get(position).getFiles()).
                            putExtra("position", 0));
                }


            }
        });

        holder.postimg_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Talent Show SIngle Post Activity
                if (from && activity2.format_list.get(position).equals("video"))
                    setItemListener(position);

                    //Singing ANd Dancing Acitivyt
                else if (!from && activity1.format_list.get(position).equals("video"))
                    setItemListener(position);
                else
                    context.startActivity(new Intent(context, Play_Audio_Activity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("name", singing_list.get(position).getFile_name()).
                            putExtra("voice", file_list.get(position).getFiles()).
                            putExtra("position", 1));
            }
        });

        holder.postimg_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Talent Show SIngle Post Activity
                if (from && activity2.format_list.get(position).equals("video"))
                    setItemListener(position);

                    //Singing ANd Dancing Acitivyt
                else if (!from && activity1.format_list.get(position).equals("video"))
                    setItemListener(position);
                else
                    context.startActivity(new Intent(context, Play_Audio_Activity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("voice", file_list.get(position).getFiles()).
                            putExtra("name", singing_list.get(position).getFile_name()).
                            putExtra("position", 2));
            }
        });

        holder.postimg_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Talent Show SIngle Post Activity
                if (from && activity2.format_list.get(position).equals("video"))
                    setItemListener(position);

                    //Singing ANd Dancing Acitivyt
                else if (!from && activity1.format_list.get(position).equals("video"))
                    setItemListener(position);
                else
                    context.startActivity(new Intent(context, Play_Audio_Activity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("name", singing_list.get(position).getFile_name()).
                            putExtra("voice", file_list.get(position).getFiles()).
                            putExtra("position", 3));
            }
        });

        holder.more_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Talent Show SIngle Post Activity
                if (from && activity2.format_list.get(position).equals("video"))
                    setItemListener(position);

                    //Singing ANd Dancing Acitivyt
                else if (!from && activity1.format_list.get(position).equals("video"))
                    setItemListener(position);
                else
                    context.startActivity(new Intent(context, Play_Audio_Activity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("name", singing_list.get(position).getFile_name()).
                            putExtra("voice", file_list.get(position).getFiles()).
                            putExtra("position", 0));
            }
        });


        holder.good_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Ranks" + singing_list.get(position).getRanks());
                if (voting_list.get(position).getGood_Rank()) {
                    voting_list.get(position).setTootal_Good((Integer.parseInt(voting_list.get(position).getTootal_Good()) - 1) + "");
                } else {
                    voting_list.get(position).
                            setTootal_Good((Integer.parseInt(voting_list.get(position).getTootal_Good()) + 1) + "");
                }

                voting_list.get(position).setGood_Rank(!voting_list.get(position).getGood_Rank());

                //decrease -1 other values
                if (voting_list.get(position).getV_Good_Rank()) {
                    voting_list.get(position).setV_Good_Rank(false);
                    voting_list.get(position).
                            setTootal_V_Good((Integer.parseInt(voting_list.get(position).getTootal_V_Good()) - 1) + "");
                }
                if (voting_list.get(position).getBad_Ran()) {
                    voting_list.get(position).setBad_Rank(false);
                    voting_list.get(position).
                            setTotal_Bad((Integer.parseInt(voting_list.get(position).getTotal_Bad()) - 1) + "");
                }
                if (voting_list.get(position).getV_Bad_Rank()) {
                    voting_list.get(position).setV_Bad_Rank(false);
                    voting_list.get(position).
                            setTotal_V_Bad((Integer.parseInt(voting_list.get(position).getTotal_V_Bad()) - 1) + "");
                }


                notifyItemChanged(position);

                //from true=means Talent_SHow_Single Post
                if (from)
                    activity2.put_rating(singing_list.get(position).getTal_id(), "good");
                else
                    activity1.put_rating(singing_list.get(position).getTal_id(), "good");


            }
        });
        holder.very_good_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (voting_list.get(position).getV_Good_Rank()) {
                    voting_list.get(position).setTootal_V_Good((Integer.parseInt(voting_list.get(position).getTootal_V_Good()) - 1) + "");
                } else {
                    voting_list.get(position).
                            setTootal_V_Good((Integer.parseInt(voting_list.get(position).getTootal_V_Good()) + 1) + "");
                }

                //update their value
                voting_list.get(position).setV_Good_Rank(!voting_list.get(position).getV_Good_Rank());


                //decrease -1 other values
                if (voting_list.get(position).getGood_Rank()) {
                    voting_list.get(position).setGood_Rank(false);
                    voting_list.get(position).
                            setTootal_Good((Integer.parseInt(voting_list.get(position).getTootal_Good()) - 1) + "");
                }
                if (voting_list.get(position).getBad_Ran()) {
                    voting_list.get(position).setBad_Rank(false);
                    voting_list.get(position).
                            setTotal_Bad((Integer.parseInt(voting_list.get(position).getTotal_Bad()) - 1) + "");
                }
                if (voting_list.get(position).getV_Bad_Rank()) {
                    voting_list.get(position).setV_Bad_Rank(false);
                    voting_list.get(position).
                            setTotal_V_Bad((Integer.parseInt(voting_list.get(position).getTotal_V_Bad()) - 1) + "");
                }


                notifyItemChanged(position);

                if (from)
                    activity2.put_rating(singing_list.get(position).getTal_id(), "vgood");
                else
                    activity1.put_rating(singing_list.get(position).getTal_id(), "vgood");


            }
        });
        holder.bad_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (voting_list.get(position).getBad_Ran()) {
                    voting_list.get(position).setTotal_Bad((Integer.parseInt(voting_list.get(position).getTotal_Bad()) - 1) + "");
                } else {
                    voting_list.get(position).
                            setTotal_Bad((Integer.parseInt(voting_list.get(position).getTotal_Bad()) + 1) + "");
                }

                //update their value
                voting_list.get(position).setBad_Rank(!voting_list.get(position).getBad_Ran());


                //decrease -1 other values
                if (voting_list.get(position).getGood_Rank()) {
                    voting_list.get(position).setGood_Rank(false);
                    voting_list.get(position).
                            setTootal_Good((Integer.parseInt(voting_list.get(position).getTootal_Good()) - 1) + "");
                }
                if (voting_list.get(position).getV_Good_Rank()) {
                    voting_list.get(position).setV_Good_Rank(false);
                    voting_list.get(position).
                            setTootal_V_Good((Integer.parseInt(voting_list.get(position).getTootal_V_Good()) - 1) + "");
                }
                if (voting_list.get(position).getV_Bad_Rank()) {
                    voting_list.get(position).setV_Bad_Rank(false);
                    voting_list.get(position).
                            setTotal_V_Bad((Integer.parseInt(voting_list.get(position).getTotal_V_Bad()) - 1) + "");
                }


                notifyItemChanged(position);

                if (from)
                    activity2.put_rating(singing_list.get(position).getTal_id(), "bad");
                else
                    activity1.put_rating(singing_list.get(position).getTal_id(), "bad");
            }
        });
        holder.very_bad_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (voting_list.get(position).getV_Bad_Rank()) {
                    voting_list.get(position).setTotal_V_Bad((Integer.parseInt(voting_list.get(position).getTotal_V_Bad()) - 1) + "");
                } else {
                    voting_list.get(position).
                            setTotal_V_Bad((Integer.parseInt(voting_list.get(position).getTotal_V_Bad()) + 1) + "");
                }

                //update their value
                voting_list.get(position).setV_Bad_Rank(!voting_list.get(position).getV_Bad_Rank());


                //decrease -1 other values
                if (voting_list.get(position).getGood_Rank()) {
                    voting_list.get(position).setGood_Rank(false);
                    voting_list.get(position).
                            setTootal_Good((Integer.parseInt(voting_list.get(position).getTootal_Good()) - 1) + "");
                }
                if (voting_list.get(position).getV_Good_Rank()) {
                    voting_list.get(position).setV_Good_Rank(false);
                    voting_list.get(position).
                            setTootal_V_Good((Integer.parseInt(voting_list.get(position).getTootal_V_Good()) - 1) + "");
                }
                if (voting_list.get(position).getBad_Ran()) {
                    voting_list.get(position).setBad_Rank(false);
                    voting_list.get(position).
                            setTotal_Bad((Integer.parseInt(voting_list.get(position).getTotal_Bad()) - 1) + "");
                }


                notifyItemChanged(position);
                if (from)
                    activity2.put_rating(singing_list.get(position).getTal_id(), "vbad");
                else
                    activity1.put_rating(singing_list.get(position).getTal_id(), "vbad");


            }
        });


        holder.more_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                final PopupMenu popup = new PopupMenu(activity, holder.more_icon);
                //inflating menu from xml resource
                popup.inflate(R.menu.talent_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share:
                                if (!from)
                                    activity1.share_Post(build_URI_For_Share(position));
                                break;
                        }
                        return false;
                    }
                });


                popup.show();


            }
        });

    }

    private void set_Success_Glide_Data_method_1(Context context, String url, ImageView user_profile, int placeholder) {
        Glide.with(context).
                load(url).
                apply(new RequestOptions().placeholder(placeholder)).
                thumbnail(0.01f).
                into(user_profile);

    }

    private void set_Success_Glide_Data(final Context context, final String url, final ImageView video_icon_4_visible, final ProgressBar loading_icon_gone, final ImageView postimg) {

        System.out.println("salluu" + url);


        Glide.with(context).asBitmap().
                load(url)
                .apply(new RequestOptions().fitCenter())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                        video_icon_4_visible.setVisibility(View.GONE);
                        loading_icon_gone.setVisibility(View.GONE);


                        System.out.println("salluu_failed" + url);
                        postimg.getLayoutParams().height = 400;
                        postimg.requestLayout();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                        video_icon_4_visible.setVisibility(View.VISIBLE);
                        loading_icon_gone.setVisibility(View.GONE);


                        int height = get_Device_height();


                        int thirty_percentage = (height * 30) / 100;
                        int result = height - thirty_percentage;


                        postimg.getLayoutParams().height = result;
                        postimg.requestLayout();

                        postimg.setImageBitmap(resource);
                        return true;
                    }
                }).submit();


    }


    public int get_Device_height() {
        Display display = null;

        if (from)
            display = activity2.getWindowManager().getDefaultDisplay();
        else
            display = activity1.getWindowManager().getDefaultDisplay();


        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.e("Width", "" + width);
        Log.e("height", "" + height);

        return height;
    }

    private String build_URI_For_Share(int position) {

        Uri builtUri = Uri.parse("https://www.icosom.com/")
                .buildUpon()
                .path("redirect.php")
                .appendQueryParameter("tal_id", singing_list.get(position).getTal_id())
                .appendQueryParameter("user_id", MainActivity.user_id)
                .appendQueryParameter("video", "video")
                .build();
        try {
            URL url = new URL(builtUri.toString());
            return url.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void setItemListener(int position) {

        context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                putExtra("imgs", file_list.get(position).getFiles()).
                putExtra("isVideos", true).
                putExtra("from_singing", true).
                putExtra("position", 0));
    }

    @Override
    public int getItemCount() {
        return singing_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_profile;
        TextView user_name, status, more_text;
        FrameLayout postFrameA, postFrameB, postFrameC, postFrameD;
        ImageView postimg_A, postimg_B, postimg_C, postimg_D;
        ProgressBar loading_icon1, loading_icon2, loading_icon3, loading_icon4;
        ImageView video_icon_1, video_icon_2, video_icon_3, video_icon_4;
        ImageView music_icoon_1, music_icoon_2, music_icoon_3, music_icoon_4;
        LinearLayout three_layout;
        LinearLayout good_layout, very_good_layout, bad_layout, very_bad_layout;

        TextView good, very_good, bad, very_bad, txt_postTime, ranking_label;
        ImageView more_icon, good_img, vgood_img, bad_img, vbad_img;


        public MyViewHolder(View itemView) {
            super(itemView);
            user_profile = itemView.findViewById(R.id.user_profile);
            user_name = itemView.findViewById(R.id.user_name);
            status = itemView.findViewById(R.id.status);
            postFrameA = itemView.findViewById(R.id.postFrameA);
            postFrameB = itemView.findViewById(R.id.postFrameB);
            postFrameC = itemView.findViewById(R.id.postFrameC);
            postFrameD = itemView.findViewById(R.id.postFrameD);

            postimg_A = itemView.findViewById(R.id.postimg_A);
            postimg_B = itemView.findViewById(R.id.postimg_B);
            postimg_C = itemView.findViewById(R.id.postimg_C);
            postimg_D = itemView.findViewById(R.id.postimg_D);

            loading_icon1 = itemView.findViewById(R.id.loading_icon1);
            loading_icon2 = itemView.findViewById(R.id.loading_icon2);
            loading_icon3 = itemView.findViewById(R.id.loading_icon3);
            loading_icon4 = itemView.findViewById(R.id.loading_icon4);

            video_icon_1 = itemView.findViewById(R.id.video_icon_1);
            video_icon_2 = itemView.findViewById(R.id.video_icon_2);
            video_icon_3 = itemView.findViewById(R.id.video_icon_3);
            video_icon_4 = itemView.findViewById(R.id.video_icon_4);

            music_icoon_1 = itemView.findViewById(R.id.music_icoon_1);
            music_icoon_2 = itemView.findViewById(R.id.music_icoon_2);
            music_icoon_3 = itemView.findViewById(R.id.music_icoon_3);
            music_icoon_4 = itemView.findViewById(R.id.music_icoon_4);

            three_layout = itemView.findViewById(R.id.three_layout);
            more_text = itemView.findViewById(R.id.more_text);


            good_layout = itemView.findViewById(R.id.good_layout);
            very_good_layout = itemView.findViewById(R.id.very_good_layout);
            bad_layout = itemView.findViewById(R.id.bad_layout);
            very_bad_layout = itemView.findViewById(R.id.very_bad_layout);


            good = itemView.findViewById(R.id.good);
            very_good = itemView.findViewById(R.id.very_good);
            bad = itemView.findViewById(R.id.bad);
            very_bad = itemView.findViewById(R.id.very_bad);

            txt_postTime = itemView.findViewById(R.id.txt_postTime);
            more_icon = itemView.findViewById(R.id.more_icon);
            ranking_label = itemView.findViewById(R.id.ranking_label);


            good_img = itemView.findViewById(R.id.good_img);
            vgood_img = itemView.findViewById(R.id.vgood_img);
            bad_img = itemView.findViewById(R.id.bad_img);
            vbad_img = itemView.findViewById(R.id.vbad_img);


        }
    }
}