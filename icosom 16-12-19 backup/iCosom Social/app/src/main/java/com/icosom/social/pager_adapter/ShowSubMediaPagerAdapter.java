package com.icosom.social.pager_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.appunite.appunitevideoplayer.PlayerActivity;
import com.bumptech.glide.Glide;
import com.icosom.social.R;
import com.icosom.social.utility.CommonFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowSubMediaPagerAdapter extends PagerAdapter
{
    LayoutInflater inflater = null;
    Context context;
    ArrayList<String> imgPath;
    Boolean isVideo;
    Boolean from_singing;

    String path="";
   // String video_path="";
    //String image_path="";

    public ShowSubMediaPagerAdapter(Context context, ArrayList<String> imgPath, Boolean isVideo, Boolean from_singing)
    {
        this.context = context;
        this.imgPath = imgPath;
        this.isVideo = isVideo;
        this.from_singing = from_singing;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(from_singing==true)
        {
            path= "https://icosom.com/social/main/talent_uploads/";
        }
        else if(isVideo==true)
        {
            path=CommonFunctions.FETCH_VIDEOS;
        }
        else if(isVideo==false)
        {
            path=CommonFunctions.FETCH_IMAGES;
        }


    }

    @Override
    public int getCount()
    {
        return imgPath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position)
    {
        View itemView = inflater.inflate(R.layout.show_sub_media_item, container, false);
        final ImageView iv_img = itemView.findViewById(R.id.img);
        ImageView isVideo = itemView.findViewById(R.id.isVideo);
        final FrameLayout fl = itemView.findViewById(R.id.fl);

        final Boolean flag = this.isVideo;

        isVideo.setVisibility(this.isVideo?View.VISIBLE:View.GONE);

        if (this.isVideo)
        {
            System.out.println("vide_katrina");
            Glide.
                    with(context).
                    load(path+imgPath.get(position)).
                    thumbnail(0.1f).
                    into(iv_img);
            iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Toast.makeText(context, "Video download started", Toast.LENGTH_SHORT).show();
                    new DownloadVideo().execute(imgPath.get(position), imgPath.get(position));

                    return true;
                }
            });
        }
        else
        {
            System.out.println("vide_katrina_else");

            Glide.
                    with(context).
                    load(path+imgPath.get(position)).
                    thumbnail(0.1f).
                    into(iv_img);

            iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    fl.setDrawingCacheEnabled(true);

                    fl.buildDrawingCache();

                    Bitmap bitmap = fl.getDrawingCache();
                               /* BitmapDrawable drawable = (BitmapDrawable) holder.iv.getDrawable();
                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                                        R.drawable.fitzone);*/
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    File image = new File(sdCardDirectory, imgPath.get(position));
                    boolean success = false;

                    // Encode the file as a PNG image.
                    FileOutputStream outStream;
                    try {

                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

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
        }

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag)
                {

                    context.startActivity(PlayerActivity.getVideoPlayerIntent(context,
                            path+imgPath.get(position),
                            "").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        });

        container.addView(itemView);
        return itemView;
    }

    public  class DownloadVideo extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String path = strings[0];
            String file = strings[1];

            downloadFile(path, file);
            return null;
        }
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

}