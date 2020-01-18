package com.example.g116.vvn_social.Home_Activities;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.g116.vvn_social.Adapter.AddFileRecyclerAdapter;
import com.example.g116.vvn_social.Adapter.ThemeRecyclerAdapter;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.ImageFilePath;
import com.example.g116.vvn_social.Interface.RuntimePermissionsActivity;
import com.example.g116.vvn_social.Modal.TagUserModel;
import com.example.g116.vvn_social.Modal.Theme;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class AddFeedActivity extends RuntimePermissionsActivity {

    // com.example.g116.vvn_social.Save the camera taken picture in this folder.
    private File pictureSaveFolderPath;
    String imageFilePath;
    String videoFilePath;
    // com.example.g116.vvn_social.Save imageview currently displayed picture index in all camera taken pictures..
    private int currentDisplayImageIndex = 0;
    TextView tagFriends;
    LinearLayout lay_share;
    LinearLayout lay_bottom;
    NestedScrollView nestedScroll;
    CoordinatorLayout.LayoutParams params;
    TextView txt_shareNameDesc;
    LinearLayout lay_gallery;
    LinearLayout lay_gallery_video;
    LinearLayout lay_camera;
    LinearLayout lay_camera_video;
    TextView txt_share;
    TextView edt_description;
    TextView txt_tagPeople;
    ArrayList<TagUserModel> tagUsers;
    ArrayList<String> uris;
    ArrayList<File> files;
    RecyclerView rv_addFiles;
    RecyclerView rv_theme;
    RecyclerView.LayoutManager layoutManager;
    AddFileRecyclerAdapter adapter;
    ThemeRecyclerAdapter tadapter;
    List<Theme> themeList = new ArrayList<>();
    SharedPreferences sp;
    AppController appController;
    Boolean isImage = true;
    ProgressBar pb_loading;
    TextView txt_shareOrEdit;
    String postId;
    LinearLayout lay_tags;
    int position;
    String notifyToUser;
    Map<String, String> map;
    RelativeLayout themerelativeLayout;
    ImageView themeimageView;
    EditText themetextView;


    private static final int REQ_PER_GALLERY = 10;
    private static final int REQ_PER_GALLERY_VIDEO = 20;
    private static final int REQ_PER_CAMERA = 30;
    private static final int REQ_PER_CAMERA_VIDEO = 40;
    private static final int REQ_PER = 10;
    private static final int REQ_PER2 = 20;

    private static final String IMAGE_DIRECTORY_NAME = "iCosom";
    private Uri cameraUri;
    private Uri videoUri;
    String TY = "1";
    String image;
    String code="#000000";
    private String mCurrentPhotoPath;
    private SessionManager session;

    public static int backfrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        pictureSaveFolderPath = getExternalCacheDir();
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        appController = (AppController) getApplicationContext();

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        session = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();


        final String user_id = user.get(SessionManager.KEY_ID);
        final String user_type = user.get(SessionManager.KEY_USER_TYPE);



        Log.e("user_id",""+user_id);
        Log.e("user_type",""+user_type);








        themerelativeLayout = findViewById(R.id.lay_theme);
        themeimageView = findViewById(R.id.theme);
        themetextView = findViewById(R.id.theme_txt);
        lay_tags = findViewById(R.id.lay_tags);
        rv_addFiles = findViewById(R.id.rv_addFiles);
        rv_theme = findViewById(R.id.rv_theme);
        tagFriends = findViewById(R.id.tagFriends);
        txt_tagPeople = findViewById(R.id.txt_tagPeople);
        lay_share = findViewById(R.id.lay_share);
        lay_bottom = findViewById(R.id.lay_bottom);
        nestedScroll = findViewById(R.id.nestedScroll);
        txt_shareNameDesc = findViewById(R.id.txt_shareNameDesc);
        lay_gallery = findViewById(R.id.lay_gallery);
        lay_gallery_video = findViewById(R.id.lay_gallery_video);
        lay_camera = findViewById(R.id.lay_camera);
        lay_camera_video = findViewById(R.id.lay_camera_video);
        txt_share = findViewById(R.id.txt_share);
        edt_description = findViewById(R.id.edt_description);
        pb_loading = findViewById(R.id.pb_loading);
        txt_shareOrEdit = findViewById(R.id.txt_shareOrEdit);

        //hide keyboard popup when activity is opened
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        txt_tagPeople.setVisibility(View.GONE);
        pb_loading.setVisibility(View.GONE);

        tagUsers = new ArrayList<>();
        files = new ArrayList<>();
        uris = new ArrayList<>();
        map = new HashMap<>();

        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_theme.setLayoutManager(layoutManager);
        tadapter = new ThemeRecyclerAdapter(AddFeedActivity.this, themeList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                TY = "2";
                isImage = true;
                String img = themeList.get(position).getImg();
                String codess =themeList.get(position).getCol();
                edt_description.setVisibility(View.GONE);
                themerelativeLayout.setVisibility(View.VISIBLE);
                themetextView.setTextColor(Color.parseColor(codess));
                Glide.
                        with(AddFeedActivity.this).
                        load("https://icosom.com/social/postFiles/images/"+ img).
                        into(themeimageView);
                txt_share.setTextColor(Color.parseColor("#000000"));




            }
        });
        rv_theme.setAdapter(tadapter);
       // new AddFeedActivity.SignIn1().execute();

        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_addFiles.setLayoutManager(layoutManager);
        adapter = new AddFileRecyclerAdapter(AddFeedActivity.this, uris);
        rv_addFiles.setAdapter(adapter);

        params = (CoordinatorLayout.LayoutParams) nestedScroll.getLayoutParams();

        if (getIntent().getBooleanExtra("edit", false)) {
            txt_share.setText("save");
            lay_tags.setVisibility(View.GONE);
            lay_bottom.setVisibility(View.GONE);
            edt_description.setText(getIntent().getStringExtra("desc"));
        } else {
            lay_tags.setVisibility(View.VISIBLE);
            lay_bottom.setVisibility(View.VISIBLE);
            txt_share.setText("share");
        }

        if (getIntent().getBooleanExtra("share", false))
        {
            String name = getIntent().getStringExtra("name");
            String desc = getIntent().getStringExtra("desc");
            postId = getIntent().getStringExtra("postId");


            System.out.println("add_name"+name);
            System.out.println("add_desc"+desc);
            System.out.println("add_postId"+postId);

            params.setMargins(0, 0, 0, 0);
            nestedScroll.setLayoutParams(params);
            lay_tags.setVisibility(View.GONE);
            lay_share.setVisibility(View.VISIBLE);
            lay_bottom.setVisibility(View.GONE);

            if (getIntent().getBooleanExtra("edit", false)) {
                txt_shareOrEdit.setText("A preview will be added to your post after you save it.");
            } else {
                notifyToUser = getIntent().getStringExtra("notifyToUser");
                txt_shareOrEdit.setText("A preview will be added to your post after you share it.");
            }

            txt_share.setTextColor(Color.parseColor("#000000"));

            SpannableString ss = new SpannableString(name + " " + desc);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txt_shareNameDesc.setText(ss);
        } else {
           // params.setMargins(0, 0, 0, (int) URL.convertDpToPixel(60f, getBaseContext()));
            nestedScroll.setLayoutParams(params);
            lay_share.setVisibility(View.GONE);
            lay_bottom.setVisibility(View.VISIBLE);
        }

       /* tagFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getBaseContext(), TagFriendsActivity.class), 3);
            }
        });*/

        lay_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    AddFeedActivity.super.requestAppPermissions(new String[]
                            {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, R.string.runtimepermission_txt, REQ_PER_GALLERY);
                } else {
                    openGallery();
                    isImage = true;
                }
            }
        });

        lay_gallery_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    AddFeedActivity.super.requestAppPermissions(new String[]
                            {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, R.string.runtimepermission_txt, REQ_PER_GALLERY_VIDEO);
                } else {
                    openGalleryVideo();
                    isImage = false;
                }
            }
        });

        lay_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    AddFeedActivity.super.requestAppPermissions(new String[]
                            {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            }, R.string.runtimepermission_txt, REQ_PER_CAMERA);
                } else {
                    openCamera();
                    isImage = true;
                }
            }
        });

        lay_camera_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddFeedActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    AddFeedActivity.super.requestAppPermissions(new String[]
                            {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            }, R.string.runtimepermission_txt, REQ_PER_CAMERA_VIDEO);
                } else {
                    openCameraVideo();
                    isImage = false;
                }
            }
        });

        edt_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (uris.size() == 0 && charSequence.length() == 0)
                    txt_share.setTextColor(Color.parseColor("#cccccc"));
                else
                    txt_share.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                themetextView.setCursorVisible(false);

                if (txt_share.getText().toString().trim().equalsIgnoreCase("share")) {
                    if (txt_share.getCurrentTextColor() == Color.parseColor("#000000")) {
                        if (getIntent().getBooleanExtra("share", false)) {
                            //sharePost();
                        } else {
                            if (TY.equalsIgnoreCase("1")) {
                                addPost(user_id,user_type, edt_description.getText().toString(),
                                        map, files, isImage);
                            }
                            if (TY.equalsIgnoreCase("2")) {
                                themetextView.setCursorVisible(false);
                                themerelativeLayout.setDrawingCacheEnabled(true);

                                themerelativeLayout.buildDrawingCache();

                                Bitmap bitmap = themerelativeLayout.getDrawingCache();/*
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                                image = ConvertBitmapToString(resizedBitmap)*/;
                               // File sdCardDirectory = Environment.getExternalStorageDirectory();

                                //by kaif
                                File storageDir = Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES);

                               // File imagess = new File(sdCardDirectory,  "21343453456565.png");



                                File imagess = new File(storageDir,  "21343453456565.png");

                                Log.e("storageDir_kaif_path","skdj");
                                Log.e("images_kaif_path","skdj");
                                boolean success = false;

                                // Encode the file as a PNG image.
                                FileOutputStream outStream;
                                try {

                                    outStream = new FileOutputStream(imagess);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

                                    outStream.flush();
                                    outStream.close();
                                    success = true;
                                } catch (FileNotFoundException e) {

                                    Log.e("kaif_Excetpion-1","skdj");
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    Log.e("kaif_Excetpion-2","skdj");
                                    e.printStackTrace();
                                }
                                if(success) {
                                    files.add(imagess);

                                    /*addPost(sp.getString("userId", ""), edt_description.getText().toString(),
                                            map, files, isImage);*/
                                }
                                else{
                                    Toast.makeText(getBaseContext(), "xcxxx", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } else {
                    editPost(user_id,user_type,postId);
                }
            }
        });
    }


    public void editPost( String user_id, String user_type,String postId) {
        txt_share.setEnabled(false);

        pb_loading.setVisibility(View.VISIBLE);

        Log.e("user_id",""+user_id);
        Log.e("user_type",""+user_type);
        Log.e("postId",""+postId);
        Log.e("desc",""+edt_description.getText().toString());


        RequestBody body = new FormBody.Builder().
                add("action", "edit").
             /*   add("user_id", user_id).*/
                     add("type", user_type).
                        add("desc", edt_description.getText().toString()).
                        add("post_id", postId).
                        build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/casespost.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());


                if(AddFeedActivity.this!=null) {
                    AddFeedActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_loading.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1"))
                    {

                        if(AddFeedActivity.this!=null) {
                            AddFeedActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb_loading.setVisibility(View.INVISIBLE);
                                }
                            });
                        }



                        Log.e("update_post_Method",""+jo.getString("msg"));

                        setResult(RESULT_OK, new Intent().
                                putExtra("description", edt_description.getText().toString()).
                                putExtra("position", position));
                        backfrom=1;
                        finish();

                    } else {
                        if(AddFeedActivity.this!=null) {
                            AddFeedActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb_loading.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                        Log.e("Delete_Method",""+jo.getString("msg"));
                    }
                } catch (JSONException e) {

                    if(AddFeedActivity.this!=null) {
                        AddFeedActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb_loading.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                    e.printStackTrace();
                }
            }

            // nothing to do

        });
    }









    public static String ConvertBitmapToString(Bitmap bitmap) {
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage = URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    public void openCamera() {
        openCameraIntent();

    }

    public void openCameraVideo() {
        openCamerasIntent();

    }

    public void openGallery() {

    startActivityForResult(Intent.createChooser(new Intent().
                setType("image/*").
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).
                setAction(Intent.ACTION_GET_CONTENT), "Select Images"), 1);
    }

    public void openGalleryVideo() {
        startActivityForResult(Intent.createChooser(new Intent().
                setType("video/*").
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).
                setAction(Intent.ACTION_GET_CONTENT), "Select Video"), 4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // from gallery
            ClipData cd = data.getClipData();
            if (cd != null) {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    uris.add(""+cd.getItemAt(i).getUri());
                    files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(i)))));
               Log.e("sayed_path-1","skd"+cd.getItemAt(i).getUri());
               Log.e("sayed_path-2","skd"+new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(i)))));

                }
            } else {
                uris.add(""+data.getData());
                files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(0)))));

                Log.e("sayed_path-1","skd"+data.getData());
                Log.e("sayed_path-2","skd"+new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(0)))));


            }
            txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            //uris.add(Uri.parse(imageFilePath));
            uris.add(imageFilePath);
            files.add(new File(imageFilePath));
            //files.add(new File(imageFilePath));
            txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
            // open camera
        } else if (requestCode == 4 && resultCode == RESULT_OK) {
            // from gallery
            ClipData cd = data.getClipData();
            if (cd != null) {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    uris.add(""+cd.getItemAt(i).getUri());
                    files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(i)))));
                }
            } else {
                uris.add(""+data.getData());
                files.add(new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(0)))));
            }
            txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
        } else if (requestCode == 5 && resultCode == RESULT_OK) {
            uris.add(videoFilePath);

            files.add(new File(videoFilePath));
            txt_share.setTextColor(Color.parseColor("#000000"));
            adapter.notifyDataSetChanged();
            // open camera
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            // from tagging
            ArrayList<TagUserModel> dw = data.getParcelableArrayListExtra("tags");

            for (TagUserModel model : dw) {
                map.put(model.getUserId(), model.getName());
            }

            txt_tagPeople.setText(setTaggingString(map));
        }
    }

    private String setTaggingString(Map<String, String> map) {
        String str = "— with ";

        if (map.size() == 0) {
            txt_tagPeople.setVisibility(View.GONE);
            return "";
        } else {
            txt_tagPeople.setVisibility(View.VISIBLE);
            int count = 1;
            for (String string : map.values()) {
                if (count == 1) {
                    str += string;
                } else {
                    str = string + " and " + (count - 1) + " other.";
                }

                count++;
            }
        }

        return str;
    }

    public void removeImage(int pos) {
        uris.remove(pos);
        files.remove(pos);
        if (uris.size() == 0 && edt_description.getText().toString().length() == 0)
            txt_share.setTextColor(Color.parseColor("#cccccc"));
        else
            txt_share.setTextColor(Color.parseColor("#000000"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void addPost(String userId,String type, String description, Map<String, String> tags,
                         ArrayList<File> files, Boolean isImage) {

        System.out.println("add_post_api_called");

        txt_share.setEnabled(false);

        pb_loading.setVisibility(View.VISIBLE);
        String tag = "";
        if (tags.size() > 0) {
            for (String str : tags.keySet()) {
                tag += str + ",";
            }

            tag = tag.substring(0, tag.lastIndexOf(","));
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("type", type);
        builder.addFormDataPart("addPost", "kk");//send anything
        builder.addFormDataPart("user_id", userId);
        builder.addFormDataPart("latest_post", description);
        //if (!tag.equalsIgnoreCase(""))
        // builder.addFormDataPart("tags", tag);

        for (File f : files) {

            String filePath =f.getAbsolutePath();
//            String filePath = new String(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(0))));
            Log.e("kaif_file_path-4","skdj"+filePath);
//            Log.e("kaif_file_path-6","skdj"+new File(ImageFilePath.getPath(getBaseContext(), Uri.parse(uris.get(0)))));
            Log.e("kaif_file_path-5","skdj"+imageFilePath);
            //   Toast.makeText(this, ""+filePath, Toast.LENGTH_SHORT).show();
            MediaType mediaType = MediaType.parse(isImage ? "image/" : "video/" + filePath.substring(filePath.lastIndexOf(".") + 1));
            builder.addFormDataPart("myImage[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                    RequestBody.create(mediaType, f));
        }

        Request request = new Request.Builder().
                url("https://vvn.city/api/add_post.php").
                post(builder.build()).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                AddFeedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_loading.setVisibility(View.GONE);
                        Log.e("kaif_Excetpion-3","skdj");
                        Toast.makeText(getBaseContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                AddFeedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jo = new JSONObject(myResponse);
                            if (jo.getString("status").equals("1")) {
                                pb_loading.setVisibility(View.GONE);
                                //     Toast.makeText(AddFeedActivity.this, ""+jo.getString("message"), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                backfrom=1;

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }







    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQ_PER_GALLERY) {
            openGallery();
            isImage = true;
        } else if (requestCode == REQ_PER_GALLERY_VIDEO) {
            openGalleryVideo();
            isImage = false;
        } else if (requestCode == REQ_PER_CAMERA) {
            openCamera();
            isImage = true;
        } else if (requestCode == REQ_PER_CAMERA_VIDEO) {
            openCameraVideo();
            isImage = false;
        }
    }
    private Uri getImageFileUriByOsVersion(File file)
    {
        Uri ret = null;

        // Get output image unique resource identifier. This uri is used by camera app to save taken picture temporary.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            // /sdcard/ folder link to /storage/41B7-12F1 folder
            // so below code return /storage/41B7-12F1
            File externalStorageRootDir = Environment.getExternalStorageDirectory();

            // contextRootDir = /data/user/0/com.dev2qa.example/files in my Huawei mate 8.
            File contextRootDir = getFilesDir();

            // contextCacheDir = /data/user/0/com.dev2qa.example/cache in my Huawei mate 8.
            File contextCacheDir = getCacheDir();

            // For android os version bigger than or equal to 7.0 use FileProvider class.
            // Otherwise android os will throw FileUriExposedException.
            // Because the system considers it is unsafe to use local real path uri directly.
            Context ctx = getApplicationContext();
            ret = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".provider", file);
        }else
        {
            // For android os version less than 7.0 there are no safety issue,
            // So we can get the output image uri by file real local path directly.
            ret = Uri.fromFile(file);
        }

        return ret;
    }
  /*  public static Uri getOutputMediaFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }*/

    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);
    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    // new code

//by kaif
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
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

        // com.example.g116.vvn_social.Save a file: path for use with ACTION_VIEW intents
       // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath =image.getAbsolutePath();
        //imageFilePath = image.getAbsolutePath();
        imageFilePath = mCurrentPhotoPath;
        return image;
    }


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


    private void openCameraIntent()
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
                // Error occurred while creating the File
                Log.i("Camer_Kiaf_exception", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Context ctx = getApplicationContext();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, 2);
            }
        }

    }

    private void openCamerasIntent() {
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
                startActivityForResult(pictureIntent, 5);

            }
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom=1;
    }
}