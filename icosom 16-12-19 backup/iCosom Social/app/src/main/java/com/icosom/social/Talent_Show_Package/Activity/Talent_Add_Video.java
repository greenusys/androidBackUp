package com.icosom.social.Talent_Show_Package.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.icosom.social.Interface.RuntimePermissionsActivity;
import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Adapter.Show_Video_Adapter;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.ImageFilePath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Talent_Add_Video extends RuntimePermissionsActivity {

    EditText text_editbox;
    RecyclerView rv_video;
    ArrayList<String> rv_video_list = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();
    private static final int REQ_PER_GALLERY_VIDEO = 20;
    private static final int REQ_PER_CAMERA_VIDEO = 40;
    private Show_Video_Adapter adapter;
    private String videoFilePath;
    private AppController appController;
    private TextView txt_done;
    private ProgressBar pb_loading;
    String value="";
    boolean video=true;
    Snackbar snackbar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent__add__video);

        value=getIntent().getStringExtra("value");
        System.out.println("talent_add_video"+value);


        initViews();
        settxt_done_setListener();


    }

    private void settxt_done_setListener() {
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // send_Post_API(String talent_option, String user_id,String post_text)

              if(files.size()!=0)
                send_Post_API(value, MainActivity.user_id,text_editbox.getText().toString());
              else
                  snackbar.make(findViewById(R.id.root), "Please upload video file", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQ_PER_GALLERY_VIDEO) {
            openGalleryVideo();
           // isImage = false;
        }  else if (requestCode == REQ_PER_CAMERA_VIDEO) {
            openCameraVideo();
           // isImage = false;
        }
    }
    private void initViews() {
        text_editbox = findViewById(R.id.edit_text);
        rv_video = findViewById(R.id.rv_video);
        txt_done = findViewById(R.id.txt_done);
        pb_loading = findViewById(R.id.pb_loading);



        appController = (AppController) getApplicationContext();


        //show selected video in recycler view
        rv_video.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new Show_Video_Adapter(Talent_Add_Video.this, rv_video_list,video,false);
        rv_video.setAdapter(adapter);


    }




    //for gallery  video
    //1
    public void open_video_gallery(View view) {
        if (ContextCompat.checkSelfPermission(Talent_Add_Video.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Talent_Add_Video.super.requestAppPermissions(new String[]
                    {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, R.string.runtimepermission_txt, REQ_PER_GALLERY_VIDEO);
        } else {
            openGalleryVideo();
            // isImage = false;
        }
    }

    //2
    public void openGalleryVideo() {
        startActivityForResult(Intent.createChooser(new Intent().
                setType("video/*").
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).
                setAction(Intent.ACTION_GET_CONTENT), "Select Video"), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //resultCode=-1; default value

        System.out.println("onactivity_video");
        System.out.println("REQUEST_CODE"+requestCode);
        System.out.println("check_code"+requestCode);
        System.out.println("resultCode"+resultCode);
        System.out.println("data"+data);





        if(resultCode == RESULT_OK)
        {
            //for gallery video
        if (requestCode == 1 )
        {
            System.out.println("for_gallery_video");


            ClipData cd = data.getClipData();
            if (cd != null) {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    rv_video_list.add("" + cd.getItemAt(i).getUri());
                    files.add(new File(ImageFilePath.getPath(getApplicationContext(), Uri.parse(rv_video_list.get(i)))));
                }
            } else {
                rv_video_list.add("" + data.getData());
                files.add(new File(ImageFilePath.getPath(getApplicationContext(), Uri.parse(rv_video_list.get(0)))));
            }
             txt_done.setTextColor(Color.parseColor("#ffffff"));
            adapter.notifyDataSetChanged();
        }
        //for camera video
        else if  (requestCode == 2) {

            System.out.println("for_camera_video");

            rv_video_list.add(videoFilePath);
            files.add(new File(videoFilePath));
            txt_done.setTextColor(Color.parseColor("#ffffff"));
            adapter.notifyDataSetChanged();
            // open camera
        }
        }
        else
        System.out.println("onactivity_video_else");
    }


    //for camera video
    //1
    public void open_video_camera(View view)
    {
        if (ContextCompat.checkSelfPermission(Talent_Add_Video.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Talent_Add_Video.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Talent_Add_Video.super.requestAppPermissions(new String[]
                    {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, R.string.runtimepermission_txt, REQ_PER_CAMERA_VIDEO);
        } else {
            openCameraVideo();
           // isImage = false;
        }
    }

    //2
    private void openCameraVideo() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent pictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createvideoFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Context ctx = getApplicationContext();

                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(pictureIntent, 2);

            }
        }

    }

    //3
    private File createvideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Vid_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );
        // videoFilePath = "file:" + image.getAbsolutePath();
        videoFilePath = image.getAbsolutePath();
        return image;
    }



    private void send_Post_API(String talent_option, String user_id,String post_text)
    {
        System.out.println("send_post_api_called");
        System.out.println("talent_option"+talent_option);
        System.out.println("user_id"+user_id);
        System.out.println("post_text"+post_text);

        pb_loading.setVisibility(View.VISIBLE);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("talent_option", talent_option);
        builder.addFormDataPart("user_id", user_id);
        builder.addFormDataPart("post_text", post_text);

        for (File f : files) {
            String filePath =f.getAbsolutePath();
            MediaType mediaType = MediaType.parse("video/" + filePath.substring(filePath.lastIndexOf(".") + 1));
            builder.addFormDataPart("files[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                    RequestBody.create(mediaType, f));

            System.out.println("f_Name_"+f);//complete path
            System.out.println("file_path"+filePath);//complete path
            System.out.println("file_path_substring"+filePath.substring(filePath.lastIndexOf(".") + 1));
            System.out.println("mediaType"+mediaType);//format
            System.out.println("files[]"+filePath.substring(filePath.lastIndexOf("/") + 1));//file name
            System.out.println("sallu\n");

        }

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/talent_show_api.php").
                post(builder.build()).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Talent_Add_Video.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("failed_to_video");
                        pb_loading.setVisibility(View.INVISIBLE);
                        Log.e("kaif_Excetpion-3","skdj"+e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Talent_Add_Video.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jo = new JSONObject(myResponse);

                            System.out.println("kaif_uploaded"+jo);

                            if (jo.getString("status").equals("1")) {
                                pb_loading.setVisibility(View.INVISIBLE);

                                showAlertBox("Video successfully uploaded");

                            }
                            else
                            {
                                Talent_Add_Video.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pb_loading.setVisibility(View.INVISIBLE);
                                        showAlertBox("Failed to upload video");
                                         }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void showAlertBox(final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Talent_Add_Video.this);
        builder.setTitle("Icosom Talent Show");
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if(!message.contains("failed")) {
                            setResult(RESULT_OK);
                            finish();
                        }
                        else
                        {
                            rv_video_list.clear();
                            files.clear();
                            adapter.notifyDataSetChanged();

                        }

                    }
                });
        builder.show();
    }

}
