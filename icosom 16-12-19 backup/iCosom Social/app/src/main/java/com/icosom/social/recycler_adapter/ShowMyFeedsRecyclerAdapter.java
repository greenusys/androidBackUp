package com.icosom.social.recycler_adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.icosom.social.R;
import com.icosom.social.activity.AddFeedActivity;
import com.icosom.social.activity.CommentActivity;
import com.icosom.social.activity.ProfileActivity;
import com.icosom.social.activity.ShowAllTagPeopleActivity;
import com.icosom.social.activity.ShowSubMediaActivity;
import com.icosom.social.model.FeedModel;
import com.icosom.social.utility.CommonFunctions;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowMyFeedsRecyclerAdapter extends RecyclerView.Adapter<ShowMyFeedsRecyclerAdapter.ViewHolder>
{
    Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_NORMAL = 1;
    private static final int TYPE_ITEM_SHARE = 2;
    private static final int TYPE_FOOTER = 3;

    ArrayList<FeedModel> modelArrayList;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    DecelerateInterpolator sDecelerater = new DecelerateInterpolator();
    OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

    public ShowMyFeedsRecyclerAdapter(Context context, ArrayList<FeedModel> modelArrayList)
    {
        this.context = context;
        this.modelArrayList = modelArrayList;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        edt = sp.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_feed_layout_head, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }
        else if (viewType == TYPE_ITEM_NORMAL)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_process_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }
        else if (viewType == TYPE_ITEM_SHARE)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_feed_process_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }
        else if (viewType == TYPE_FOOTER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_bottom_footer, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position==0)
        {
            /*Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+sp.getString("profile", "")).
                    thumbnail(0.01f).
                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                    into(holder.profile);

            holder.card_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AddFeedActivity.class));
                }
            });*/
        }/*
        else if (position == getItemCount()-1)
        {

        }*/
        else if (modelArrayList.get(position-1).getPostIsShared())
        {
            if (modelArrayList.get(position-1).getPostImgFlag().equals("0"))
            {
                if (modelArrayList.get(position-1).getPostIsTagged())
                {
                    if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                    {
                        String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName()+
                                " shared "+modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                modelArrayList.get(position-1).getSharePostUserLastName()+
                                " post with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                        int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getPostUserLastName().length();
                        int str2 = str1+" shared ".length();
                        int str3 = str2+modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getSharePostUserLastName().length();
                        int str4 = str3+" post with ".length();
                        int str5 = str4 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                        int str6 = str5+ ", ".length();
                        int str7 = str6+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                        int str8 = str7+ " and ".length();
                        int str9 = str8 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getPostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan cs2 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan cs3 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan cs4 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        ClickableSpan cs5 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        text.setSpan(cs5, str8, str9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_shareUserName.setText(text);
                        holder.txt_shareUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_shareUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                    else
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " shared "+modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " post with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1 + " shared ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str4 = str3+" post with ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareUserName.setText(text);
                            holder.txt_shareUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " shared "+modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " post with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" shared ".length();
                            int str3 = str2+modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str4 = str3+" post with ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str6 = str5+ " and ".length();
                            int str7 = str6+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareUserName.setText(text);
                            holder.txt_shareUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " shared "+modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " post with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" shared ".length();
                            int str3 = str2+modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str4 = str3+" post with ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str6 = str5+ ", ".length();
                            int str7 = str6+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str8 = str7+ " and ".length();
                            int str9 = str8 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                }
                            };

                            ClickableSpan cs5 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs5, str8, str9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareUserName.setText(text);
                            holder.txt_shareUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                    }
                }
                else
                {
                    String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                            modelArrayList.get(position-1).getPostUserLastName()+
                            " shared "+modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                            modelArrayList.get(position-1).getSharePostUserLastName()+
                            " post.";

                    int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                            modelArrayList.get(position-1).getPostUserLastName().length();
                    int str2 = str1 + " shared ".length();
                    int str3 = str2 + modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                            modelArrayList.get(position-1).getSharePostUserLastName().length();

                    final SpannableString text = new SpannableString(str);

                    ClickableSpan cs1 = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            goToProfile(modelArrayList.get(position-1).getPostUserId());
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    };

                    ClickableSpan cs2 = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    };

                    text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    holder.txt_shareUserName.setText(text);
                    holder.txt_shareUserName.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.txt_shareUserName.setHighlightColor(Color.TRANSPARENT);
                }
            }

            holder.txt_shareTime.setText(modelArrayList.get(position-1).getPostTime());

            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostUserProfilePicture()).
                    apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    thumbnail(0.01f).
                    into(holder.iv_shareUserProfile);

            /*if (modelArrayList.get(position-1).getPostDescription().equals(""))
            {
                holder.txt_shareDescription.setVisibility(View.GONE);
            }
            else
            {
                holder.txt_shareDescription.setVisibility(View.VISIBLE);
                holder.txt_shareDescription.setText(
                        modelArrayList.get(position-1).getPostDescription()
                );
            }*/

            if (modelArrayList.get(position-1).getPostDescription().equals(""))
            {
                holder.txt_shareDescription.setVisibility(View.INVISIBLE);
                holder.txt_shareDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                holder.txt_shareDescription.setTypeface(null, Typeface.NORMAL);
                holder.txt_shareDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            }
            else {
                holder.txt_shareDescription.setVisibility(View.VISIBLE);

                if (modelArrayList.get(position-1).getPostDescription().contains("<a href="))
                {

                    String str_temp2 = "";

                    final ArrayList<Integer> arrayListLength = new ArrayList<>();
                    final ArrayList<String> arrayListLink = new ArrayList<>();
                    int linkLength =0;

                    for(int i=0; i<pullLinks(modelArrayList.get(position-1).getPostDescription()).size() ; i= i+2 )
                    {
                        if (i == 0){
                            arrayListLength.add(pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString().length());
                            linkLength = pullLinks(modelArrayList.get(position-1).getPostDescription()).get(0).toString().length();
                        }else {
                            linkLength = linkLength + pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString().length();
                            arrayListLength.add(linkLength);
                        }
                        arrayListLink.add(pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString());

                        str_temp2 = str_temp2 + arrayListLink.get(i/2) + " ";
                    }

                    SpannableString ss = new SpannableString(str_temp2);
                    ClickableSpan[] clickableSpans = new ClickableSpan[arrayListLink.size()];
                    for (int i = 0; i < arrayListLink.size(); i++) {
                        final int finalI = i;
                        clickableSpans[i] = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(arrayListLink.get(finalI)));
                                context.startActivity(intent);                            }
                        };
                        if (i == 0) {
                            ss.setSpan(clickableSpans[i], 0, arrayListLength.get(0), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if (i == arrayListLink.size()-1){
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), str_temp2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }else {
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), arrayListLength.get(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    holder.txt_shareDescription.setText(ss);
                    holder.txt_shareDescription.setMovementMethod(LinkMovementMethod.getInstance());

                    holder.txt_shareDescription.setTypeface(null, Typeface.NORMAL);
                    holder.txt_shareDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                    holder.txt_shareDescription.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));

                }
                else
                {
                    holder.txt_shareDescription.setText(modelArrayList.get(position - 1).getPostDescription());

                    if (modelArrayList.get(position - 1).getPostDescription().length()<30)
                    {
                        holder.txt_shareDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_shareDescription.setTypeface(null, Typeface.BOLD);
                        holder.txt_shareDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                    }
                    else
                    {
                        holder.txt_shareDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_shareDescription.setTypeface(null, Typeface.NORMAL);
                        holder.txt_shareDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                    }
                }
            }

            if (modelArrayList.get(position-1).getSharePostImgFlag().equals("0"))
            {
                if (modelArrayList.get(position-1).getSharePostFileLists().size()>0)
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " added "+modelArrayList.get(position-1).getSharePostFileLists().size()+
                                    " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str2 = str1+ " added ".length()+(modelArrayList.get(position-1).getSharePostFileLists().size()+"").length()+
                                    " media with ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+ ", ".length();
                            int str5 = str4+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5 + " and ".length();
                            int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareParentUserName.setText(text);
                            holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getSharePostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " added ".length()+(modelArrayList.get(position-1).getSharePostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getSharePostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " added ".length()+(modelArrayList.get(position-1).getSharePostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + " and ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getSharePostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " added ".length()+(modelArrayList.get(position-1).getSharePostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + ", ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5 + " and ".length();
                                int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                modelArrayList.get(position-1).getSharePostUserLastName()+
                                " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                " media";

                        int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getSharePostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_shareParentUserName.setText(text);
                        holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
                else
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str2 = str1+ " is with ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3 + ", ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5 + " and ".length();
                            int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareParentUserName.setText(text);
                            holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+ " is with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + ", ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and"+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " is with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + " and".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " is with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + ", ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5+ " and ".length();
                                int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                modelArrayList.get(position-1).getSharePostUserLastName();

                        int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getSharePostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_shareParentUserName.setText(text);
                        holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
            }
            else
            {
                if (modelArrayList.get(position-1).getSharePostImgFlag().equals("1"))
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str2 = str1+ " updated his profile picture with ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3 + ", ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5+ " and ".length();
                            int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareParentUserName.setText(text);
                            holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +"profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his profile picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +"profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his profile picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + " and ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his profile picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + ", ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();
                                int str6 = str5 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                modelArrayList.get(position-1).getSharePostUserLastName()+
                                " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +"profile picture.";

                        int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getSharePostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_shareParentUserName.setText(text);
                        holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
                else
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getSharePostUserLastName()+
                                    " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +"cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getSharePostUserLastName().length();
                            int str2 = str1+ " updated his cover picture with ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3 + ", ".length();
                            int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5 + " and ".length();
                            int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_shareParentUserName.setText(text);
                            holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his cover picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his cover picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + " and ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getSharePostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getSharePostUserLastName().length();
                                int str2 = str1+ " updated his cover picture with ".length();
                                int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3 + ", ".length();
                                int str5 = str4 + modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5 + " and ".length();
                                int str7 = str6 + ((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getSharePostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_shareParentUserName.setText(text);
                                holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getSharePostUserFirstName()+" "+
                                modelArrayList.get(position-1).getSharePostUserLastName()+
                                " updated "+ checkGender(modelArrayList.get(position-1).getSharePostUserGender()) +" cover picture.";

                        int str1 = modelArrayList.get(position-1).getSharePostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getSharePostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getSharePostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_shareParentUserName.setText(text);
                        holder.txt_shareParentUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_shareParentUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
            }

            holder.txt_shareParentTime.setText(modelArrayList.get(position-1).getSharePostTime());

            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostUserProfilePicture()).
                    apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    thumbnail(0.01f).
                    into(holder.iv_shareParentUserProfile);

           /* if (modelArrayList.get(position-1).getSharePostDescription().equals(""))
            {
                holder.txt_shareParentDescription.setVisibility(View.GONE);
            }
            else
            {
                holder.txt_shareParentDescription.setVisibility(View.VISIBLE);
                holder.txt_shareParentDescription.setText(modelArrayList.get(position-1).getSharePostDescription());
            }*/

            if (modelArrayList.get(position-1).getSharePostDescription().equals(""))
            {
                holder.txt_shareParentDescription.setVisibility(View.INVISIBLE);
                holder.txt_shareParentDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                holder.txt_shareParentDescription.setTypeface(null, Typeface.NORMAL);
                holder.txt_shareParentDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            }
            else {
                holder.txt_shareParentDescription.setVisibility(View.VISIBLE);

                if (modelArrayList.get(position-1).getSharePostDescription().contains("<a href="))
                {

                    String str_temp2 = "";

                    final ArrayList<Integer> arrayListLength = new ArrayList<>();
                    final ArrayList<String> arrayListLink = new ArrayList<>();
                    int linkLength =0;

                    for(int i=0; i<pullLinks(modelArrayList.get(position-1).getSharePostDescription()).size() ; i= i+2 )
                    {
                        if (i == 0){
                            arrayListLength.add(pullLinks(modelArrayList.get(position-1).getSharePostDescription()).get(i).toString().length());
                            linkLength = pullLinks(modelArrayList.get(position-1).getSharePostDescription()).get(0).toString().length();
                        }else {
                            linkLength = linkLength + pullLinks(modelArrayList.get(position-1).getSharePostDescription()).get(i).toString().length();
                            arrayListLength.add(linkLength);
                        }
                        arrayListLink.add(pullLinks(modelArrayList.get(position-1).getSharePostDescription()).get(i).toString());

                        str_temp2 = str_temp2 + arrayListLink.get(i/2) + " ";
                    }

                    SpannableString ss = new SpannableString(str_temp2);
                    ClickableSpan[] clickableSpans = new ClickableSpan[arrayListLink.size()];
                    for (int i = 0; i < arrayListLink.size(); i++) {
                        final int finalI = i;
                        clickableSpans[i] = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(arrayListLink.get(finalI)));
                                context.startActivity(intent);                            }
                        };
                        if (i == 0) {
                            ss.setSpan(clickableSpans[i], 0, arrayListLength.get(0), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if (i == arrayListLink.size()-1){
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), str_temp2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }else {
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), arrayListLength.get(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    holder.txt_shareParentDescription.setText(ss);
                    holder.txt_shareParentDescription.setMovementMethod(LinkMovementMethod.getInstance());

                    holder.txt_shareParentDescription.setTypeface(null, Typeface.NORMAL);
                    holder.txt_shareParentDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                    holder.txt_shareParentDescription.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));

                }
                else
                {
                    holder.txt_shareParentDescription.setText(modelArrayList.get(position - 1).getSharePostDescription());

                    if (modelArrayList.get(position - 1).getSharePostDescription().length()<30)
                    {
                        holder.txt_shareParentDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_shareParentDescription.setTypeface(null, Typeface.BOLD);
                        holder.txt_shareParentDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                    }
                    else
                    {
                        holder.txt_shareParentDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_shareParentDescription.setTypeface(null, Typeface.NORMAL);
                        holder.txt_shareParentDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                    }
                }
            }

            if (modelArrayList.get(position-1).getSharePostFileLists().size()==0)
            {
                holder.lay_sharedMedia.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.lay_sharedMedia.setVisibility(View.VISIBLE);

                if (modelArrayList.get(position-1).getSharePostFileLists().size() == 1)
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.INVISIBLE);
                    holder.txt_shareImgCount.setVisibility(View.INVISIBLE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.INVISIBLE);

                    holder.frame_shareB.setVisibility(View.INVISIBLE);
                    holder.iv_shareImgB.setVisibility(View.INVISIBLE);
                    holder.shareProgressB.setVisibility(View.INVISIBLE);
                    holder.iv_shareIsVideoB.setVisibility(View.INVISIBLE);

                    holder.frame_shareC.setVisibility(View.INVISIBLE);
                    holder.iv_shareImgC.setVisibility(View.INVISIBLE);
                    holder.shareProgressC.setVisibility(View.INVISIBLE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.GONE);
                    holder.iv_shareImgD.setVisibility(View.GONE);
                    holder.shareProgressD.setVisibility(View.GONE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.GONE);
                    holder.iv_shareImgE.setVisibility(View.GONE);
                    holder.shareProgressE.setVisibility(View.GONE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });
                    }

                }
                else if (modelArrayList.get(position-1).getSharePostFileLists().size() == 2)
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.GONE);
                    holder.txt_shareImgCount.setVisibility(View.GONE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.GONE);

                    holder.frame_shareB.setVisibility(View.VISIBLE);
                    holder.iv_shareImgB.setVisibility(View.VISIBLE);
                    holder.shareProgressB.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoB.setVisibility(View.GONE);

                    holder.frame_shareC.setVisibility(View.GONE);
                    holder.iv_shareImgC.setVisibility(View.GONE);
                    holder.shareProgressC.setVisibility(View.GONE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.GONE);
                    holder.iv_shareImgD.setVisibility(View.GONE);
                    holder.shareProgressD.setVisibility(View.GONE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.GONE);
                    holder.iv_shareImgE.setVisibility(View.GONE);
                    holder.shareProgressE.setVisibility(View.GONE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 3)
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_shareImgCount.setVisibility(View.GONE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.GONE);

                    holder.frame_shareB.setVisibility(View.GONE);
                    holder.iv_shareImgB.setVisibility(View.GONE);
                    holder.shareProgressB.setVisibility(View.GONE);
                    holder.iv_shareIsVideoB.setVisibility(View.GONE);

                    holder.frame_shareC.setVisibility(View.VISIBLE);
                    holder.iv_shareImgC.setVisibility(View.VISIBLE);
                    holder.shareProgressC.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.VISIBLE);
                    holder.iv_shareImgD.setVisibility(View.VISIBLE);
                    holder.shareProgressD.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.GONE);
                    holder.iv_shareImgE.setVisibility(View.GONE);
                    holder.shareProgressE.setVisibility(View.GONE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 4)
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_shareImgCount.setVisibility(View.GONE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.GONE);

                    holder.frame_shareB.setVisibility(View.GONE);
                    holder.iv_shareImgB.setVisibility(View.GONE);
                    holder.shareProgressB.setVisibility(View.GONE);
                    holder.iv_shareIsVideoB.setVisibility(View.GONE);

                    holder.frame_shareC.setVisibility(View.VISIBLE);
                    holder.iv_shareImgC.setVisibility(View.VISIBLE);
                    holder.shareProgressC.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.VISIBLE);
                    holder.iv_shareImgD.setVisibility(View.VISIBLE);
                    holder.shareProgressD.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.VISIBLE);
                    holder.iv_shareImgE.setVisibility(View.VISIBLE);
                    holder.shareProgressE.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 5)
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_shareImgCount.setVisibility(View.GONE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.GONE);

                    holder.frame_shareB.setVisibility(View.VISIBLE);
                    holder.iv_shareImgB.setVisibility(View.VISIBLE);
                    holder.shareProgressB.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoB.setVisibility(View.GONE);

                    holder.frame_shareC.setVisibility(View.VISIBLE);
                    holder.iv_shareImgC.setVisibility(View.VISIBLE);
                    holder.shareProgressC.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.VISIBLE);
                    holder.iv_shareImgD.setVisibility(View.VISIBLE);
                    holder.shareProgressD.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.VISIBLE);
                    holder.iv_shareImgE.setVisibility(View.VISIBLE);
                    holder.shareProgressE.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 4));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 4));
                            }
                        });
                    }
                }
                else
                {
                    holder.lay_shareForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_shareImgCount.setVisibility(View.VISIBLE);

                    holder.frame_shareA.setVisibility(View.VISIBLE);
                    holder.iv_shareImgA.setVisibility(View.VISIBLE);
                    holder.shareProgressA.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoA.setVisibility(View.GONE);

                    holder.frame_shareB.setVisibility(View.VISIBLE);
                    holder.iv_shareImgB.setVisibility(View.VISIBLE);
                    holder.shareProgressB.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoB.setVisibility(View.GONE);

                    holder.frame_shareC.setVisibility(View.VISIBLE);
                    holder.iv_shareImgC.setVisibility(View.VISIBLE);
                    holder.shareProgressC.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoC.setVisibility(View.GONE);

                    holder.frame_shareD.setVisibility(View.VISIBLE);
                    holder.iv_shareImgD.setVisibility(View.VISIBLE);
                    holder.shareProgressD.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoD.setVisibility(View.GONE);

                    holder.frame_shareE.setVisibility(View.VISIBLE);
                    holder.iv_shareImgE.setVisibility(View.VISIBLE);
                    holder.shareProgressE.setVisibility(View.VISIBLE);
                    holder.iv_shareIsVideoE.setVisibility(View.GONE);

                    holder.txt_shareImgCount.setText("+ "+(modelArrayList.get(position-1).getSharePostFileLists().size()-5)+" more");

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getSharePostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 4));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressA.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgA);

                        holder.iv_shareImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressB.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgB);

                        holder.iv_shareImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressC.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgC);

                        holder.iv_shareImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressD.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgD);

                        holder.iv_shareImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getSharePostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.shareProgressE.setVisibility(View.GONE);
                                        holder.iv_shareIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_shareImgE);

                        holder.iv_shareImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getSharePostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 4));
                            }
                        });
                    }
                }
            }

            holder.txt_shareLikeDislikeCount.setText(
                    modelArrayList.get(position-1).getPostTotalLikes()+" likes, "+
                            modelArrayList.get(position-1).getPostTotalDislikes()+" dislikes"
            );

            holder.txt_shareCommentShareCount.setText(
                    modelArrayList.get(position-1).getPostTotalComments()+" comments, "+
                            modelArrayList.get(position-1).getPostTotalShares()+" shares"
            );

            holder.iv_shareLike.setImageResource(
                    modelArrayList.get(position-1).getMyLike()?R.drawable.ic_like_fill:R.drawable.ic_like
            );

            holder.txt_shareLike.setTextColor(
                    modelArrayList.get(position-1).getMyLike()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_shareDislike.setImageResource(
                    modelArrayList.get(position-1).getMyDislike()?R.drawable.ic_dislike_fill:R.drawable.ic_dislike
            );

            holder.txt_shareDislike.setTextColor(
                    modelArrayList.get(position-1).getMyDislike()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_shareComment.setImageResource(
                    modelArrayList.get(position-1).getMyComment()?R.drawable.ic_comment_fill:R.drawable.ic_comment
            );

            holder.txt_shareComment.setTextColor(
                    modelArrayList.get(position-1).getMyComment()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_shareShare.setImageResource(
                    modelArrayList.get(position-1).getMyShare()?R.drawable.ic_share_fill:R.drawable.ic_share
            );

            holder.txt_shareShare.setTextColor(
                    modelArrayList.get(position-1).getMyShare()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.lay_shareLike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        holder.lay_shareLike.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        holder.lay_shareLike.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);

                        if (modelArrayList.get(position-1).getMyLike())
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())-1)+"20");
                        }
                        else
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())+1)+"21");
                        }

                        modelArrayList.get(position-1).setMyLike(!modelArrayList.get(position-1).getMyLike());

                        if (modelArrayList.get(position-1).getMyDislike())
                        {
                            modelArrayList.get(position-1).setMyDislike(false);
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())-1)+"");
                        }

                        notifyDataSetChanged();

                        ((ProfileActivity) context).likeDislikeFeeds(
                                position - 1, modelArrayList.get(position - 1).getPostId(),
                                "like",modelArrayList.get(position-1).getDevice_token(),
                                modelArrayList.get(position-1).getPostUserId(),
                                modelArrayList.get(position-1).getPostUserFirstName()+" "+modelArrayList.get(position-1).getPostUserLastName()

                        );
                    }
                    return false;
                }
            });

            holder.lay_shareDislike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        holder.lay_shareDislike.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        holder.lay_shareDislike.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);

                        if (modelArrayList.get(position-1).getMyDislike())
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())-1)+"");
                        }
                        else
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())+1)+"");
                        }

                        modelArrayList.get(position-1).setMyDislike(!modelArrayList.get(position-1).getMyDislike());

                        if (modelArrayList.get(position-1).getMyLike())
                        {
                            modelArrayList.get(position-1).setMyLike(false);
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())-1)+"");
                        }

                        notifyDataSetChanged();

                        ((ProfileActivity) context).likeDislikeFeeds(
                                position - 1,
                                modelArrayList.get(position - 1).getPostId(), "dislike",
                                modelArrayList.get(position-1).getDevice_token(),
                                modelArrayList.get(position-1).getPostUserId(),
                                modelArrayList.get(position-1).getPostUserFirstName()+" "+modelArrayList.get(position-1).getPostUserLastName()

                        );
                    }
                    return false;
                }
            });

            holder.lay_shareComment.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        holder.lay_shareComment.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        holder.lay_shareComment.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);

                        MediaPlayer.create(context, R.raw.beep3).start();

                        ((ProfileActivity) context).startActivityForResult(
                                new Intent(context, CommentActivity.class).
                                        putExtra("post_user_id", modelArrayList.get(position - 1).getPostUserId()).
                                        putExtra("device_token", modelArrayList.get(position - 1).getDevice_token()).
                                        putExtra("postId", modelArrayList.get(position-1).getPostId()).
                                        putExtra("position", position-1)
                                , 10
                        );
                    }
                    return false;
                }
            });

            holder.lay_shareShare.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        holder.lay_shareShare.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        holder.lay_shareShare.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);

                        MediaPlayer.create(context, R.raw.beep3).start();

                        context.startActivity(new Intent(context, AddFeedActivity.class).
                                putExtra("share", true).
                                putExtra("post_user_id", modelArrayList.get(position - 1).getPostUserId()).
                                putExtra("device_token", modelArrayList.get(position - 1).getDevice_token()).
                                putExtra("name", modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName()).
                                putExtra("notifyToUser", modelArrayList.get(position - 1).getPostUserId()).
                                putExtra("postId", modelArrayList.get(position - 1).getPostId()).
                                putExtra("desc", modelArrayList.get(position-1).getPostDescription()));


                    }
                    return false;
                }
            });
        }
        else
        {
            if (modelArrayList.get(position-1).getPostImgFlag().equals("0"))
            {
                if (modelArrayList.get(position-1).getPostFileLists().size()>0)
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            /*String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                    " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" added ".length()+(modelArrayList.get(position-1).getPostFileLists().size()+"".length())+
                                    " media with ".length();
                            int str3 = str2 + modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+ ", ".length();
                            int str5 = str4+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5+" and ".length();
                            int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();*/

                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                    " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" added ".length()+(modelArrayList.get(position-1).getPostFileLists().size()+"").length()+
                                    " media with ".length();
                            int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+", ".length();
                            int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5+" and ".length();
                            int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_postUserName.setText(text);
                            holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" added ".length()+(modelArrayList.get(position-1).getPostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" added ".length()+(modelArrayList.get(position-1).getPostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+" and ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                        " media with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" added ".length()+(modelArrayList.get(position-1).getPostFileLists().size()+"").length()+
                                        " media with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+", ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5+" and ".length();
                                int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName()+
                                " added "+modelArrayList.get(position-1).getPostFileLists().size()+
                                " media";

                        int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getPostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getPostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_postUserName.setText(text);
                        holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
                else
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" is with ".length();
                            int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+", ".length();
                            int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5+" and ".length();
                            int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_postUserName.setText(text);
                            holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" is with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" is with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+" and ".length();
                                int str5 = str4+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " is with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" is with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+", ".length();
                                int str5 = str4+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5+" and ".length();
                                int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);
                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName();

                        int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getPostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getPostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_postUserName.setText(text);
                        holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
            }
            else
            {
                if (modelArrayList.get(position-1).getPostImgFlag().equals("1"))
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" updated his profile picture with ".length();
                            int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+", ".length();
                            int str5 = str4+ modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 = str5+" and ".length();
                            int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_postUserName.setText(text);
                            holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his profile picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his profile picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+" and ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" profile picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his profile picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+", ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5+" and ".length();
                                int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName()+
                                " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" profile picture.";

                        int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getPostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getPostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_postUserName.setText(text);
                        holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
                else
                {
                    if (modelArrayList.get(position-1).getPostIsTagged())
                    {
                        if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) > 3)
                        {
                            String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                    modelArrayList.get(position-1).getPostUserLastName()+
                                    " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                    (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" others.";

                            int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getPostUserLastName().length();
                            int str2 = str1+" updated his cover picture with ".length();
                            int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                            int str4 = str3+", ".length();
                            int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                    modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                            int str6 =  str5+" and ".length();
                            int str7 =  str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" others.".length();

                            final SpannableString text = new SpannableString(str);

                            ClickableSpan cs1 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getPostUserId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs2 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs3 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            ClickableSpan cs4 = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };

                            text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            holder.txt_postUserName.setText(text);
                            holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                            holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                        }
                        else
                        {
                            if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 1)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his cover picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else if (Integer.parseInt(modelArrayList.get(position-1).getTagCount()) == 2)
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+" and "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+".";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his cover picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+" and ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length()+".".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                            else
                            {
                                String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()+
                                        " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" cover picture with "+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName()+", "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagFirstName()+" "+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName()+" and "+
                                        (Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+" other.";

                                int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getPostUserLastName().length();
                                int str2 = str1+" updated his cover picture with ".length();
                                int str3 = str2+modelArrayList.get(position-1).getTagList().get(0).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(0).getTagLastName().length();
                                int str4 = str3+", ".length();
                                int str5 = str4+modelArrayList.get(position-1).getTagList().get(1).getTagFirstName().length()+" ".length()+
                                        modelArrayList.get(position-1).getTagList().get(1).getTagLastName().length();
                                int str6 = str5+" and ".length();
                                int str7 = str6+((Integer.parseInt(modelArrayList.get(position-1).getTagCount())-2)+"").length()+" other.".length();

                                final SpannableString text = new SpannableString(str);

                                ClickableSpan cs1 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getPostUserId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs2 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(0).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs3 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        goToProfile(modelArrayList.get(position-1).getTagList().get(1).getTagId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                ClickableSpan cs4 = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        showAllTagedPerson(modelArrayList.get(position-1).getPostId());
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                };

                                text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs2, str2, str3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs3, str4, str5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                text.setSpan(cs4, str6, str7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                holder.txt_postUserName.setText(text);
                                holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                                holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    else
                    {
                        String str = modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                modelArrayList.get(position-1).getPostUserLastName()+
                                " updated "+ checkGender(modelArrayList.get(position-1).getPostUserGender()) +" cover picture.";

                        int str1 = modelArrayList.get(position-1).getPostUserFirstName().length()+" ".length()+
                                modelArrayList.get(position-1).getPostUserLastName().length();

                        final SpannableString text = new SpannableString(str);

                        ClickableSpan cs1 = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                goToProfile(modelArrayList.get(position-1).getPostUserId());
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                            }
                        };

                        text.setSpan(cs1, 0, str1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        holder.txt_postUserName.setText(text);
                        holder.txt_postUserName.setMovementMethod(LinkMovementMethod.getInstance());
                        holder.txt_postUserName.setHighlightColor(Color.TRANSPARENT);
                    }
                }
            }

            holder.txt_postTime.setText(modelArrayList.get(position-1).getPostTime());

            Glide.
                    with(context).
                    load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostUserProfilePicture()).
                    apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                    thumbnail(0.01f).
                    into(holder.iv_postUserProfile);

            if (modelArrayList.get(position-1).getPostDescription().equals(""))
            {
                System.out.println("qwer empty post");
                holder.txt_postDescription.setVisibility(View.GONE);
                holder.txt_postDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                holder.txt_postDescription.setTypeface(null, Typeface.NORMAL);
                holder.txt_postDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            }
            else {
                holder.txt_postDescription.setVisibility(View.VISIBLE);

                if (modelArrayList.get(position-1).getPostDescription().contains("<a href="))
                {
                    System.out.println("qwer link post");

                    String str_temp2 = "";

                    final ArrayList<Integer> arrayListLength = new ArrayList<>();
                    final ArrayList<String> arrayListLink = new ArrayList<>();
                    int linkLength =0;

                    for(int i=0; i<pullLinks(modelArrayList.get(position-1).getPostDescription()).size() ; i= i+2 )
                    {
                        if (i == 0){
                            arrayListLength.add(pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString().length());
                            linkLength = pullLinks(modelArrayList.get(position-1).getPostDescription()).get(0).toString().length();
                        }else {
                            linkLength = linkLength + pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString().length();
                            arrayListLength.add(linkLength);
                        }
                        arrayListLink.add(pullLinks(modelArrayList.get(position-1).getPostDescription()).get(i).toString());

                        str_temp2 = str_temp2 + arrayListLink.get(i/2) + " ";
                    }

                    SpannableString ss = new SpannableString(str_temp2);
                    ClickableSpan[] clickableSpans = new ClickableSpan[arrayListLink.size()];
                    for (int i = 0; i < arrayListLink.size(); i++) {
                        final int finalI = i;
                        clickableSpans[i] = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(arrayListLink.get(finalI)));
                                context.startActivity(intent);                            }
                        };
                        if (i == 0) {
                            ss.setSpan(clickableSpans[i], 0, arrayListLength.get(0), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if (i == arrayListLink.size()-1){
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), str_temp2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }else {
                            ss.setSpan(clickableSpans[i], arrayListLength.get(i-1), arrayListLength.get(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    holder.txt_postDescription.setText(ss);
                    holder.txt_postDescription.setMovementMethod(LinkMovementMethod.getInstance());

                    holder.txt_postDescription.setTypeface(null, Typeface.NORMAL);
                    holder.txt_postDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                }
                else
                {
                    System.out.println("qwer normal post");

                    holder.txt_postDescription.setText(modelArrayList.get(position - 1).getPostDescription());

                    if (modelArrayList.get(position - 1).getPostDescription().length()<30)
                    {
                        holder.txt_postDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_postDescription.setTypeface(null, Typeface.BOLD);
                        holder.txt_postDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                    }
                    else
                    {
                        holder.txt_postDescription.setTextColor(ContextCompat.getColor(context, R.color.text_colour));
                        holder.txt_postDescription.setTypeface(null, Typeface.NORMAL);
                        holder.txt_postDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    }
                }
            }

           /* if (modelArrayList.get(position-1).getPostDescription().equals(""))
            {
                holder.txt_postDescription.setVisibility(View.GONE);
            }
            else
            {
                holder.txt_postDescription.setVisibility(View.VISIBLE);
                holder.txt_postDescription.setText(modelArrayList.get(position-1).getPostDescription());
            }*/

            if (modelArrayList.get(position-1).getPostFileLists().size()==0)
            {
                holder.lay_postMedia.setVisibility(View.GONE);
            }
            else
            {
                holder.lay_postMedia.setVisibility(View.VISIBLE);

                if (modelArrayList.get(position-1).getPostFileLists().size() == 1)
                {
                    holder.lay_postForBelowImgs.setVisibility(View.GONE);
                    holder.txt_postImgCount.setVisibility(View.GONE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                  //  holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.GONE);
                    holder.iv_postImgB.setVisibility(View.GONE);
                    holder.loading_iconB.setVisibility(View.GONE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.GONE);
                    holder.iv_postImgC.setVisibility(View.GONE);
                    holder.loading_iconC.setVisibility(View.GONE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.GONE);
                    holder.iv_postImgD.setVisibility(View.GONE);
                    holder.loading_iconD.setVisibility(View.GONE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.GONE);
                    holder.iv_postImgE.setVisibility(View.GONE);
                    holder.loading_iconE.setVisibility(View.GONE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        //holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                      //  holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 2)
                {
                    holder.lay_postForBelowImgs.setVisibility(View.GONE);
                    holder.txt_postImgCount.setVisibility(View.GONE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                   // holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.VISIBLE);
                    holder.iv_postImgB.setVisibility(View.VISIBLE);
                    holder.loading_iconB.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.GONE);
                    holder.iv_postImgC.setVisibility(View.GONE);
                    holder.loading_iconC.setVisibility(View.GONE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.GONE);
                    holder.iv_postImgD.setVisibility(View.GONE);
                    holder.loading_iconD.setVisibility(View.GONE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.GONE);
                    holder.iv_postImgE.setVisibility(View.GONE);
                    holder.loading_iconE.setVisibility(View.GONE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        //holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        //holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        holder.iv_postIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 3)
                {
                    holder.lay_postForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_postImgCount.setVisibility(View.GONE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                   // holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.GONE);
                    holder.iv_postImgB.setVisibility(View.GONE);
                    holder.loading_iconB.setVisibility(View.GONE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.VISIBLE);
                    holder.iv_postImgC.setVisibility(View.VISIBLE);
                    holder.loading_iconC.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.VISIBLE);
                    holder.iv_postImgD.setVisibility(View.VISIBLE);
                    holder.loading_iconD.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.GONE);
                    holder.iv_postImgE.setVisibility(View.GONE);
                    holder.loading_iconE.setVisibility(View.GONE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        //holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                      //  holder.loading_iconA.setVisibility(View.GONE);
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        holder.iv_postIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        holder.iv_postIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 4)
                {
                    holder.lay_postForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_postImgCount.setVisibility(View.GONE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                    holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.GONE);
                    holder.iv_postImgB.setVisibility(View.GONE);
                    holder.loading_iconB.setVisibility(View.GONE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.VISIBLE);
                    holder.iv_postImgC.setVisibility(View.VISIBLE);
                    holder.loading_iconC.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.VISIBLE);
                    holder.iv_postImgD.setVisibility(View.VISIBLE);
                    holder.loading_iconD.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.VISIBLE);
                    holder.iv_postImgE.setVisibility(View.VISIBLE);
                    holder.loading_iconE.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        holder.iv_postIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        holder.iv_postIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        holder.iv_postIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });
                    }
                }
                else if (modelArrayList.get(position-1).getPostFileLists().size() == 5)
                {
                    holder.lay_postForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_postImgCount.setVisibility(View.GONE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                    holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.VISIBLE);
                    holder.iv_postImgB.setVisibility(View.VISIBLE);
                    holder.loading_iconB.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.VISIBLE);
                    holder.iv_postImgC.setVisibility(View.VISIBLE);
                    holder.loading_iconC.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.VISIBLE);
                    holder.iv_postImgD.setVisibility(View.VISIBLE);
                    holder.loading_iconD.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.VISIBLE);
                    holder.iv_postImgE.setVisibility(View.VISIBLE);
                    holder.loading_iconE.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 4));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        holder.iv_postIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        holder.iv_postIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        holder.iv_postIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        holder.iv_postIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 4));
                            }
                        });
                    }
                }
                else
                {
                    holder.lay_postForBelowImgs.setVisibility(View.VISIBLE);
                    holder.txt_postImgCount.setVisibility(View.VISIBLE);

                    holder.frame_postA.setVisibility(View.VISIBLE);
                    holder.iv_postImgA.setVisibility(View.VISIBLE);
                    holder.loading_iconA.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoA.setVisibility(View.GONE);

                    holder.frame_postB.setVisibility(View.VISIBLE);
                    holder.iv_postImgB.setVisibility(View.VISIBLE);
                    holder.loading_iconB.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoB.setVisibility(View.GONE);

                    holder.frame_postC.setVisibility(View.VISIBLE);
                    holder.iv_postImgC.setVisibility(View.VISIBLE);
                    holder.loading_iconC.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoC.setVisibility(View.GONE);

                    holder.frame_postD.setVisibility(View.VISIBLE);
                    holder.iv_postImgD.setVisibility(View.VISIBLE);
                    holder.loading_iconD.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoD.setVisibility(View.GONE);

                    holder.frame_postE.setVisibility(View.VISIBLE);
                    holder.iv_postImgE.setVisibility(View.VISIBLE);
                    holder.loading_iconE.setVisibility(View.VISIBLE);
                    holder.iv_postIsVideoE.setVisibility(View.GONE);

                    holder.txt_postImgCount.setText("+ "+(modelArrayList.get(position-1).getPostFileLists().size()-5)+" more");

                    if (modelArrayList.get(position-1).getPostType().equals("1"))
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_IMAGES+modelArrayList.get(position-1).getPostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", false).
                                        putExtra("position", 4));
                            }
                        });
                    }
                    else
                    {
                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(0)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconA.setVisibility(View.GONE);
                                        holder.iv_postIsVideoA.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgA);

                        holder.iv_postImgA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 0));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(1)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconB.setVisibility(View.GONE);
                                        holder.iv_postIsVideoB.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgB);

                        holder.iv_postImgB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 1));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(2)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconC.setVisibility(View.GONE);
                                        holder.iv_postIsVideoC.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgC);

                        holder.iv_postImgC.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 2));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(3)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconD.setVisibility(View.GONE);
                                        holder.iv_postIsVideoD.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgD);

                        holder.iv_postImgD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 3));
                            }
                        });

                        Glide.
                                with(context).
                                load(CommonFunctions.FETCH_VIDEOS+modelArrayList.get(position-1).getPostFileLists().get(4)).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                                listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.loading_iconE.setVisibility(View.GONE);
                                        holder.iv_postIsVideoE.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }).
                                thumbnail(0.01f).
                                into(holder.iv_postImgE);

                        holder.iv_postImgE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(context, ShowSubMediaActivity.class).
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                                        putExtra("imgs", modelArrayList.get(position-1).getPostFileLists()).
                                        putExtra("isVideos", true).
                                        putExtra("position", 4));
                            }
                        });
                    }
                }
            }

          /*  txt_postLike txt_postDislike txt_postComment txt_postShare
            */
            if(modelArrayList.get(position - 1).getPostTotalLikes().equals("0")) {
                holder.txt_postLike.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.txt_postLike.setVisibility(View.VISIBLE);
                holder.txt_postLike.setText(modelArrayList.get(position -1).getPostTotalLikes());
            }

            // modelArrayList.get(position - 1).getPostTotalLikes()

            if(modelArrayList.get(position -1).getPostTotalDislikes().equals("0")) {
                holder.txt_postDislike.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.txt_postDislike.setVisibility(View.VISIBLE);
                holder.txt_postDislike.setText(
                        modelArrayList.get(position-1).getPostTotalDislikes());
            }

            if(modelArrayList.get(position -1).getPostTotalComments().equals("0"))
            {
                holder.txt_postComment.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.txt_postComment.setVisibility(View.VISIBLE);
                holder.txt_postComment.setText(modelArrayList.get(position -1).getPostTotalComments());
            }

            holder.txt_postComment.setText(
                    modelArrayList.get(position-1).getPostTotalComments()
            );
            holder.txt_postShare.setText(
                    modelArrayList.get(position-1).getPostTotalShares()
            );

           /* holder.txt_postLikeDislikeCount.setText(
                    modelArrayList.get(position-1).getPostTotalLikes()+" likes, "+
                    modelArrayList.get(position-1).getPostTotalDislikes()+" dislikes"
            );

            holder.txt_postCommentShareCount.setText(
                    modelArrayList.get(position-1).getPostTotalComments()+" comments, "+
                            modelArrayList.get(position-1).getPostTotalShares()+" shares"
            );*/




            holder.iv_postLike.setImageResource(
                    modelArrayList.get(position-1).getMyLike()?R.drawable.ic_like_fill:R.drawable.ic_like
            );

            holder.txt_postLike.setTextColor(
                    modelArrayList.get(position-1).getMyLike()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_postDislike.setImageResource(
                    modelArrayList.get(position-1).getMyDislike()?R.drawable.ic_dislike_fill:R.drawable.ic_dislike
            );

            holder.txt_postDislike.setTextColor(
                    modelArrayList.get(position-1).getMyDislike()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_postComment.setImageResource(
                    modelArrayList.get(position-1).getMyComment()?R.drawable.ic_comment_fill:R.drawable.ic_comment
            );

            holder.txt_postComment.setTextColor(
                    modelArrayList.get(position-1).getMyComment()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.iv_postShare.setImageResource(
                    modelArrayList.get(position-1).getMyShare()?R.drawable.ic_share_fill:R.drawable.ic_share
            );

            holder.txt_postShare.setTextColor(
                    modelArrayList.get(position-1).getMyShare()?Color.parseColor("#e53935"):Color.parseColor("#000000")
            );

            holder.lay_postLike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    holder.txt_postLike.setVisibility(View.VISIBLE);
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        /*holder.lay_postLike.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);*/
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                       /* holder.lay_postLike.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);*/

                        if (modelArrayList.get(position-1).getMyLike())
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())-1)+"");
                        }
                        else
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())+1)+"");
                        }

                        modelArrayList.get(position-1).setMyLike(!modelArrayList.get(position-1).getMyLike());

                        if (modelArrayList.get(position-1).getMyDislike())
                        {
                            modelArrayList.get(position-1).setMyDislike(false);
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())-1)+"");
                        }

                        notifyDataSetChanged();


                        ((ProfileActivity)context).likeDislikeFeeds(
                                position - 1, modelArrayList.get(position - 1).getPostId(),
                                "like",modelArrayList.get(position-1).getDevice_token(),
                                modelArrayList.get(position-1).getPostUserId(),
                                modelArrayList.get(position-1).getPostUserFirstName()+" "+modelArrayList.get(position-1).getPostUserLastName()
                        );




                    }
                    return false;
                }
            });

            holder.lay_postDislike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    holder.txt_postDislike.setVisibility(View.VISIBLE);
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                       /* holder.lay_postDislike.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);*/
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        /*holder.lay_postDislike.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);
*/
                        if (modelArrayList.get(position-1).getMyDislike())
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())-1)+"");
                        }
                        else
                        {
                            modelArrayList.get(position-1).
                                    setPostTotalDislikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalDislikes())+1)+"");
                        }

                        modelArrayList.get(position-1).setMyDislike(!modelArrayList.get(position-1).getMyDislike());
                        if (modelArrayList.get(position-1).getMyLike())
                        {
                            modelArrayList.get(position-1).setMyLike(false);
                            modelArrayList.get(position-1).
                                    setPostTotalLikes((Integer.parseInt(modelArrayList.get(position-1).getPostTotalLikes())-1)+"");
                        }
                        notifyDataSetChanged();

                        ((ProfileActivity) context).likeDislikeFeeds(
                                position - 1,
                                modelArrayList.get(position - 1).getPostId(), "dislike",
                                modelArrayList.get(position-1).getDevice_token(),
                                modelArrayList.get(position-1).getPostUserId(),
                                modelArrayList.get(position-1).getPostUserFirstName()+" "+modelArrayList.get(position-1).getPostUserLastName()

                        );
                    }
                    return false;
                }
            });

            holder.lay_postComment.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    holder.txt_postComment.setVisibility(View.VISIBLE);
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        /*holder.lay_postComment.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);*/
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        /*holder.lay_postComment.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);
*/
                        MediaPlayer.create(context, R.raw.beep3);

                        ((ProfileActivity) context).startActivityForResult(new Intent(context, CommentActivity.class).
                                putExtra("postId", modelArrayList.get(position-1).getPostId()).
                                        putExtra("post_user_id", modelArrayList.get(position - 1).getPostUserId()).
                                        putExtra("device_token", modelArrayList.get(position - 1).getDevice_token()).

                                putExtra("position", position-1),
                                10);
                    }
                    return false;
                }
            });

            holder.lay_postShare.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                       /* holder.lay_postShare.animate().setInterpolator(sDecelerater).
                                scaleX(.7f).scaleY(.7f);*/
                    }
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                       /* holder.lay_postShare.animate().setInterpolator(sOvershooter).
                                scaleX(1f).scaleY(1f);*/

                        MediaPlayer.create(context, R.raw.beep3);

                        context.startActivity(new Intent(context, AddFeedActivity.class).
                                putExtra("share", true).
                                putExtra("post_user_id", modelArrayList.get(position - 1).getPostUserId()).
                                putExtra("device_token", modelArrayList.get(position - 1).getDevice_token()).
                                putExtra("notifyToUser", modelArrayList.get(position - 1).getPostUserId()).
                                        putExtra("postId", modelArrayList.get(position - 1).getPostId()).
                                putExtra("name", modelArrayList.get(position-1).getPostUserFirstName()+" "+
                                        modelArrayList.get(position-1).getPostUserLastName()).
                                putExtra("desc", modelArrayList.get(position-1).getPostDescription()));
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        /*if (showFooter.get(0))
            return modelArrayList.size()+2;*/

        return modelArrayList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView card_head;
        CircleImageView profile;

        TextView txt_postUserName;
        TextView txt_postTime;
        CircleImageView iv_postUserProfile;
        ImageView iv_postMore;
        TextView txt_postDescription;
        LinearLayout lay_postMedia;
        FrameLayout frame_postA, frame_postB, frame_postC, frame_postD, frame_postE;
        ImageView iv_postImgA, iv_postImgB, iv_postImgC, iv_postImgD, iv_postImgE;
        ImageView iv_postIsVideoA, iv_postIsVideoB, iv_postIsVideoC, iv_postIsVideoD, iv_postIsVideoE;
        //ProgressBar /*loading_iconA,*/ loading_iconB, loading_iconC, loading_iconD, loading_iconE;
        LinearLayout lay_postForBelowImgs;
        TextView txt_postImgCount;
        TextView txt_postLikeDislikeCount;
        TextView txt_postCommentShareCount;
        LinearLayout lay_postLike;
        LinearLayout lay_postDislike;
        LinearLayout lay_postComment;
        LinearLayout lay_postShare;
        ImageView iv_postLike, iv_postDislike, iv_postComment, iv_postShare;
        TextView txt_postLike, txt_postDislike, txt_postComment, txt_postShare;

        TextView txt_shareUserName;
        TextView txt_shareTime;
        CircleImageView iv_shareUserProfile;
        ImageView iv_shareMore;
        TextView txt_shareDescription;
        TextView txt_shareParentUserName;
        TextView txt_shareParentTime;
        CircleImageView iv_shareParentUserProfile;
        TextView txt_shareParentDescription;
        LinearLayout lay_sharedMedia;
        FrameLayout frame_shareA, frame_shareB, frame_shareC, frame_shareD, frame_shareE;
        ImageView iv_shareImgA, iv_shareImgB, iv_shareImgC, iv_shareImgD, iv_shareImgE;
        ImageView iv_shareIsVideoA, iv_shareIsVideoB, iv_shareIsVideoC, iv_shareIsVideoD, iv_shareIsVideoE;
        ProgressBar shareProgressA, shareProgressB, shareProgressC, shareProgressD, shareProgressE;
        LinearLayout lay_shareForBelowImgs;
        TextView txt_shareImgCount;
        TextView txt_shareLikeDislikeCount;
        TextView txt_shareCommentShareCount;
        LinearLayout lay_shareLike, lay_shareDislike, lay_shareComment, lay_shareShare;
        ImageView iv_shareLike, iv_shareDislike, iv_shareComment, iv_shareShare;
        TextView txt_shareLike, txt_shareDislike, txt_shareComment, txt_shareShare;

        ProgressBar loading_iconA,loading_iconB,loading_iconC,loading_iconD,loading_iconE;
        public ViewHolder(View itemView)
        {
            super(itemView);
            card_head = itemView.findViewById(R.id.card_head);
            profile = itemView.findViewById(R.id.profile);

            txt_postUserName = itemView.findViewById(R.id.txt_postUserName);
            txt_postTime = itemView.findViewById(R.id.txt_postTime);
            iv_postUserProfile = itemView.findViewById(R.id.iv_postUserProfile);
            iv_postMore = itemView.findViewById(R.id.iv_postMore);
            txt_postDescription = itemView.findViewById(R.id.txt_postDescription);
            lay_postMedia = itemView.findViewById(R.id.lay_postMedia);
            frame_postA = itemView.findViewById(R.id.frame_postA);
            frame_postB = itemView.findViewById(R.id.frame_postB);
            frame_postC = itemView.findViewById(R.id.frame_postC);
            frame_postD = itemView.findViewById(R.id.frame_postD);
            frame_postE = itemView.findViewById(R.id.frame_postE);
            iv_postImgA = itemView.findViewById(R.id.iv_postImgA);
            iv_postImgB = itemView.findViewById(R.id.iv_postImgB);
            iv_postImgC = itemView.findViewById(R.id.iv_postImgC);
            iv_postImgD = itemView.findViewById(R.id.iv_postImgD);
            iv_postImgE = itemView.findViewById(R.id.iv_postImgE);
            iv_postIsVideoA = itemView.findViewById(R.id.iv_postIsVideoA);
            iv_postIsVideoB = itemView.findViewById(R.id.iv_postIsVideoB);
            iv_postIsVideoC = itemView.findViewById(R.id.iv_postIsVideoC);
            iv_postIsVideoD = itemView.findViewById(R.id.iv_postIsVideoD);
            iv_postIsVideoE = itemView.findViewById(R.id.iv_postIsVideoE);
            
          //  loading_iconA = itemView.findViewById(R.id.loading_iconA);
           /* loading_iconB = itemView.findViewById(R.id.loading_iconB);
            loading_iconC = itemView.findViewById(R.id.loading_iconC);
            loading_iconD = itemView.findViewById(R.id.loading_iconD);
            loading_iconE = itemView.findViewById(R.id.loading_iconE);*/

            loading_iconA = itemView.findViewById(R.id.loading_iconA);
            loading_iconB = itemView.findViewById(R.id.loading_iconB);
            loading_iconC = itemView.findViewById(R.id.loading_iconC);
            loading_iconD = itemView.findViewById(R.id.loading_iconD);
            loading_iconE = itemView.findViewById(R.id.loading_iconE);
            
            lay_postForBelowImgs = itemView.findViewById(R.id.lay_postForBelowImgs);
            txt_postImgCount = itemView.findViewById(R.id.txt_postImgCount);
            txt_postLikeDislikeCount = itemView.findViewById(R.id.txt_postLikeDislikeCount);
            txt_postCommentShareCount = itemView.findViewById(R.id.txt_postCommentShareCount);
            lay_postLike = itemView.findViewById(R.id.lay_postLike);
            lay_postDislike = itemView.findViewById(R.id.lay_postDislike);
            lay_postComment = itemView.findViewById(R.id.lay_postComment);
            lay_postShare = itemView.findViewById(R.id.lay_postShare);
            iv_postLike = itemView.findViewById(R.id.iv_postLike);
            iv_postDislike = itemView.findViewById(R.id.iv_postDislike);
            iv_postComment = itemView.findViewById(R.id.iv_postComment);
            iv_postShare = itemView.findViewById(R.id.iv_postShare);

            txt_postLike = itemView.findViewById(R.id.likes);

           txt_postDislike = itemView.findViewById(R.id.dislikes);
            txt_postComment = itemView.findViewById(R.id.comment);
            txt_postShare = itemView.findViewById(R.id.share);



            txt_shareUserName = itemView.findViewById(R.id.txt_shareUserName);
            txt_shareTime = itemView.findViewById(R.id.txt_shareTime);
            iv_shareUserProfile = itemView.findViewById(R.id.iv_shareUserProfile);
            iv_shareMore = itemView.findViewById(R.id.iv_shareMore);
            txt_shareDescription = itemView.findViewById(R.id.txt_shareDescription);
            txt_shareParentUserName = itemView.findViewById(R.id.txt_shareParentUserName);
            txt_shareParentTime = itemView.findViewById(R.id.txt_shareParentTime);
            iv_shareParentUserProfile = itemView.findViewById(R.id.iv_shareParentUserProfile);
            txt_shareParentDescription = itemView.findViewById(R.id.txt_shareParentDescription);
            lay_sharedMedia = itemView.findViewById(R.id.lay_sharedMedia);
            frame_shareA = itemView.findViewById(R.id.frame_shareA);
            frame_shareB = itemView.findViewById(R.id.frame_shareB);
            frame_shareC = itemView.findViewById(R.id.frame_shareC);
            frame_shareD = itemView.findViewById(R.id.frame_shareD);
            frame_shareE = itemView.findViewById(R.id.frame_shareE);
            iv_shareImgA = itemView.findViewById(R.id.iv_shareImgA);
            iv_shareImgB = itemView.findViewById(R.id.iv_shareImgB);
            iv_shareImgC = itemView.findViewById(R.id.iv_shareImgC);
            iv_shareImgD = itemView.findViewById(R.id.iv_shareImgD);
            iv_shareImgE = itemView.findViewById(R.id.iv_shareImgE);
            iv_shareIsVideoA = itemView.findViewById(R.id.iv_shareIsVideoA);
            iv_shareIsVideoB = itemView.findViewById(R.id.iv_shareIsVideoB);
            iv_shareIsVideoC = itemView.findViewById(R.id.iv_shareIsVideoC);
            iv_shareIsVideoD = itemView.findViewById(R.id.iv_shareIsVideoD);
            iv_shareIsVideoE = itemView.findViewById(R.id.iv_shareIsVideoE);
            shareProgressA = itemView.findViewById(R.id.shareProgressA);
            shareProgressB = itemView.findViewById(R.id.shareProgressB);
            shareProgressC = itemView.findViewById(R.id.shareProgressC);
            shareProgressD = itemView.findViewById(R.id.shareProgressD);
            shareProgressE = itemView.findViewById(R.id.shareProgressE);
            lay_shareForBelowImgs = itemView.findViewById(R.id.lay_shareForBelowImgs);
            txt_shareImgCount = itemView.findViewById(R.id.txt_shareImgCount);
            txt_shareLikeDislikeCount = itemView.findViewById(R.id.txt_shareLikeDislikeCount);
            txt_shareCommentShareCount = itemView.findViewById(R.id.txt_shareCommentShareCount);
            lay_shareLike = itemView.findViewById(R.id.lay_shareLike);
            lay_shareDislike = itemView.findViewById(R.id.lay_shareDislike);
            lay_shareComment = itemView.findViewById(R.id.lay_shareComment);
            lay_shareShare = itemView.findViewById(R.id.lay_shareShare);
            iv_shareLike = itemView.findViewById(R.id.iv_shareLike);
            iv_shareDislike = itemView.findViewById(R.id.iv_shareDislike);
            iv_shareComment = itemView.findViewById(R.id.iv_shareComment);
            iv_shareShare = itemView.findViewById(R.id.iv_shareShare);
            txt_shareLike = itemView.findViewById(R.id.txt_shareLike);
            txt_shareDislike = itemView.findViewById(R.id.txt_shareDislike);
            txt_shareComment = itemView.findViewById(R.id.txt_shareComment);
            txt_shareShare = itemView.findViewById(R.id.txt_shareShare);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;

        /*if (position == getItemCount()-1 && showFooter.get(0))
            return TYPE_FOOTER;*/

        if (modelArrayList.get(position-1).getPostIsShared())
            return TYPE_ITEM_SHARE;

        return TYPE_ITEM_NORMAL;
    }

    private ArrayList pullLinks(String text) {
        ArrayList links = new ArrayList();

        String regex = "\\(?\\b((http|https|ftp|ftps)://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }

    private void goToProfile(String userId) {
        context.startActivity(new Intent(context, ProfileActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                putExtra("userId", userId));
    }

    private void showAllTagedPerson(String postId) {
        context.startActivity(new Intent(context, ShowAllTagPeopleActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                putExtra("postId", postId));
    }

    private String checkGender(String gender)
    {
        return gender.equalsIgnoreCase("male")?"his":"her";
    }
}