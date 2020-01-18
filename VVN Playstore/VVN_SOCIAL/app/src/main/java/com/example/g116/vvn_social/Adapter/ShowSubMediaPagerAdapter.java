package com.example.g116.vvn_social.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.g116.vvn_social.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowSubMediaPagerAdapter extends PagerAdapter {
    LayoutInflater inflater = null;
    Context context;
    ArrayList<String> imgPath;
    Boolean isVideo;

    public ShowSubMediaPagerAdapter(Context context, ArrayList<String> imgPath, Boolean isVideo) {
        this.context = context;
        this.imgPath = imgPath;
        this.isVideo = isVideo;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgPath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        String path = "https://vvn.city/social/";

        View itemView = inflater.inflate(R.layout.show_sub_media_item, container, false);
        final ImageView iv_img = itemView.findViewById(R.id.img);
        ImageView isVideo = itemView.findViewById(R.id.isVideo);
        TextView lblCount = itemView.findViewById(R.id.lbl_count);
        final FrameLayout fl = itemView.findViewById(R.id.fl);
        final ProgressBar progress_bar = itemView.findViewById(R.id.progress_bar);
        final ImageView iv_postMore = itemView.findViewById(R.id.more);


        final Boolean flag = this.isVideo;

        isVideo.setVisibility(this.isVideo ? View.VISIBLE : View.GONE);

        if (this.isVideo) {
            Glide.
                    with(context).
                    load("" + imgPath.get(position)).
                    thumbnail(0.1f).
                    into(iv_img);
            iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Toast.makeText(context, "Video download started", Toast.LENGTH_SHORT).show();
                    new DownloadVideo().execute("" + imgPath.get(position), imgPath.get(position));

                    return true;
                }
            });
        } else {
            if (getCount() > 1)
                lblCount.setText((position + 1) + " of " + getCount());
            else
                lblCount.setVisibility(View.GONE);



            /*Glide.
                    with(context).
                    load(path+imgPath.get(position)).
                    thumbnail(0.1f).
                    into(iv_img);*/

            progress_bar.setVisibility(View.VISIBLE);
            iv_postMore.setVisibility(View.GONE);


            if (!imgPath.get(position).contains("https:")) {
                System.out.println("kaif_1" + path + imgPath.get(position));

                Glide.with(context).
                        load(path + imgPath.get(position)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.1f).
                        into(iv_img);
            } else {
                System.out.println("kaif_2" + imgPath.get(position));
                Glide.with(context).
                        load(imgPath.get(position)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).
                        listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        }).
                        thumbnail(0.1f).
                        into(iv_img);
            }


            iv_postMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(view.getContext(), iv_postMore);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.down_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.save:


                                    fl.setDrawingCacheEnabled(true);

                                    fl.buildDrawingCache();

                                    Bitmap bitmap = fl.getDrawingCache();
                             /*  *//**//* BitmapDrawable drawable = (BitmapDrawable) holder.iv.getDrawable();
                                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                                            R.drawable.fitzone);*//**//**/
                                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                                    File image = new File(sdCardDirectory, imgPath.get(position));
                                    boolean success = false;

                                    // Encode the file as a PNG image.
                                    FileOutputStream outStream;
                                    try {

                                        outStream = new FileOutputStream(image);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                                        //*//* 100 to keep full quality of the image *//*

                                        outStream.flush();
                                        outStream.close();
                                        success = true;
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (success) {
                                        Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show();
                                    }


                                    break;


                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();


                }
            });



        /*    iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    fl.setDrawingCacheEnabled(true);

                    fl.buildDrawingCache();

                    Bitmap bitmap = fl.getDrawingCache();
                               *//**//* BitmapDrawable drawable = (BitmapDrawable) holder.iv.getDrawable();
                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                                        R.drawable.fitzone);*//**//*
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    File image = new File(sdCardDirectory, imgPath.get(position));
                    boolean success = false;

                    // Encode the file as a PNG image.
                    FileOutputStream outStream;
                    try {

                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        *//**//* 100 to keep full quality of the image *//**//*

                        outStream.flush();
                        outStream.close();
                        success = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (success){
                        Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
*/


        }



        /*iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag)
                {


                    context.startActivity(PlayerActivity.getVideoPlayerIntent(context,
                            CommonFunctions.FETCH_VIDEOS+imgPath.get(position),
                            "").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        });*/

        container.addView(itemView);
        return itemView;
    }

    public void downloadFile(String fileURL, String fileName) {
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }

    }

    class DownloadVideo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String path = strings[0];
            String file = strings[1];

            downloadFile(path, file);
            return null;
        }
    }

}