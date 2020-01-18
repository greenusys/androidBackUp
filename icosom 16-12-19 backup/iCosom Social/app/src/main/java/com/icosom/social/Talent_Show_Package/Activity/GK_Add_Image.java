package com.icosom.social.Talent_Show_Package.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

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

public class GK_Add_Image extends RuntimePermissionsActivity {

    private static final int REQ_PER_GALLERY = 10;
    private static final int REQ_PER_CAMERA = 30;

    ArrayList<String> rv_video_list = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();

    private String mCurrentPhotoPath;
    String imageFilePath;


    EditText text_editbox;
    RecyclerView rv_video;
    private Show_Video_Adapter adapter;
    private String videoFilePath;
    private AppController appController;
    //private TextView txt_done;
    private ProgressBar pb_loading;
    String value="";
    private Snackbar snackbar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk__add__image);

        System.out.println("talent_add_video"+value);

        initViews();

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQ_PER_GALLERY) {
            openGallery();
            // isImage = false;
        }
        else if (requestCode == REQ_PER_CAMERA) {
            openCameraImages();
            //isImage = true;
        }
    }




    private void initViews() {
        text_editbox = findViewById(R.id.edit_text);
        rv_video = findViewById(R.id.rv_gk_list);
        pb_loading = findViewById(R.id.pb_loading);

        appController = (AppController) getApplicationContext();

        //show selected video in recycler view
        rv_video.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new Show_Video_Adapter(GK_Add_Image.this, rv_video_list,false,true);
        rv_video.setAdapter(adapter);


    }

    public void settxt_done_setListener(View view) {

        if(files.size()!=0)
            send_Post_API("gk", MainActivity.user_id,text_editbox.getText().toString());
        else
            snackbar.make(findViewById(R.id.root), "Please upload video file", Snackbar.LENGTH_SHORT).show();



    }

    //for upload image
    //1
    public void open_image_gallery(View view) {
        if (ContextCompat.checkSelfPermission(GK_Add_Image.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            GK_Add_Image.super.requestAppPermissions(new String[]
                    {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, R.string.runtimepermission_txt, REQ_PER_GALLERY);
        } else {
            openGallery();
            //isImage = true;
        }
    }

    //2
    public void openGallery() {
        startActivityForResult(Intent.createChooser(new Intent().
                setType("image/*").
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).
                setAction(Intent.ACTION_GET_CONTENT), "Select Images"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //for gallery image
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // from gallery
            ClipData cd = data.getClipData();

            System.out.println("if_kaif_requestcode"+requestCode);
            System.out.println("kaif_resultCode"+resultCode);
            System.out.println("kaif_data"+data);
            System.out.println("ClipData"+cd);


            if (cd != null) {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    rv_video_list.add(""+cd.getItemAt(i).getUri());
                    files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(rv_video_list.get(i)))));

                }
            } else {
                if(data.getData()!=null) {
                    rv_video_list.add("" + data.getData());
                    files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(rv_video_list.get(0)))));
                }


            }
           // txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
        }
        //for camera images
        else if (requestCode == 2 && resultCode == RESULT_OK) {

            System.out.println("else_if_kaif_requestcode"+requestCode);
            System.out.println("kaif_resultCode"+resultCode);
            System.out.println("kaif_data"+data);
            //uris.add(Uri.parse(imageFilePath));
            rv_video_list.add(imageFilePath);
            files.add(new File(imageFilePath));
            //files.add(new File(imageFilePath));
            //txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath =image.getAbsolutePath();
        //imageFilePath = image.getAbsolutePath();
        imageFilePath = mCurrentPhotoPath;
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
            MediaType mediaType = MediaType.parse("image/" + filePath.substring(filePath.lastIndexOf(".") + 1));
            builder.addFormDataPart("files[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                    RequestBody.create(mediaType, f));
        }

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/talent_show_api.php").
                post(builder.build()).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                GK_Add_Image.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("failed_to_video");
                        pb_loading.setVisibility(View.GONE);
                        Log.e("kaif_Excetpion-3","skdj"+e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                GK_Add_Image.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jo = new JSONObject(myResponse);

                            System.out.println("kaif_uploaded"+jo);

                            if (jo.getString("status").equals("1")) {
                                pb_loading.setVisibility(View.GONE);

                                showAlertBox("Image successfully uploaded");

                            }
                            else
                            {
                                GK_Add_Image.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        pb_loading.setVisibility(View.GONE);
                                        showAlertBox("Failed to upload image");
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
                GK_Add_Image.this);
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


    //for camera image
    //1
    public void open_image_camera(View view) {
        if (ContextCompat.checkSelfPermission(GK_Add_Image.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(GK_Add_Image.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            GK_Add_Image.super.requestAppPermissions(new String[]
                    {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, R.string.runtimepermission_txt, REQ_PER_CAMERA);
        } else {
            openCameraImages();
          //  isImage = true;
        }

    }

    //2
    private void openCameraImages()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Context ctx = getApplicationContext();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, 2);
            }
        }

    }

}
