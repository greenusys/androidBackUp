package com.icosom.social.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.R;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.ImageFilePath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileCoverUpdateActivity extends AppCompatActivity
{
    CollapsingToolbarLayout toolbar_layout;
    AppController appController;
    ImageView iv_cover_image_profile;
    TextView btn_pick;
    TextView btn_upload;
    ProgressBar pb_coverUpload;
    TextView txt_skip;
    BottomSheetDialog sheetDialog;
    File myCoverPhoto;
    Uri coverUri;
    String id;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cover_update);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        sheetDialog = new BottomSheetDialog(this);

        txt_skip = findViewById(R.id.txt_skip);
        iv_cover_image_profile = findViewById(R.id.iv_cover_image_profile);
        pb_coverUpload = findViewById(R.id.pb_coverUpload);
        btn_pick = findViewById(R.id.btn_pick);
        btn_upload = findViewById(R.id.btn_upload);
        toolbar_layout = findViewById(R.id.toolbar_layout);

        btn_upload.setSelected(false);
        btn_upload.setClickable(false);
        pb_coverUpload.setVisibility(View.GONE);

        id = sp.getString("userId", "");
        String name = sp.getString("firstName", "") +" "+ sp.getString("lastName", "");
        toolbar_layout.setTitle(name);

        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProfilePictureUploadActivity.class));
            }
        });

        View sheetView = LayoutInflater.from(this).inflate(R.layout.pick_image_from_layout, null);
        sheetDialog.setContentView(sheetView);

        TextView txt_gallery = sheetView.findViewById(R.id.txt_gallery);
        TextView txt_camera = sheetView.findViewById(R.id.txt_camera);

        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.hide();
                openGallery();
            }
        });

        txt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.hide();
                openCamera();
            }
        });

        btn_pick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sheetDialog.show();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                changeCover();
                btn_upload.setSelected(false);
                btn_upload.setClickable(false);
            }
        });
    }

    public void openCamera()
    {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 2);
    }

    public void openGallery()
    {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            coverUri = data.getData();
            iv_cover_image_profile.setImageURI(coverUri);
            myCoverPhoto = new File(ImageFilePath.getPath(getBaseContext(), coverUri));
            btn_upload.setSelected(true);
            btn_upload.setClickable(true);
        }
    }

    private void changeCover()
    {
        pb_coverUpload.setVisibility(View.VISIBLE);
        String filePath = myCoverPhoto.getAbsolutePath();
        final MediaType MEDIA_TYPE = MediaType.parse("image/"+filePath.substring(filePath.lastIndexOf(".")+1));
        System.out.println("mmmm "+filePath);
        RequestBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).
                addFormDataPart("data-source", "android").
                addFormDataPart("userId", id).
                addFormDataPart("pic_update[]", filePath.substring(filePath.lastIndexOf("/")+1),
                        RequestBody.create(MEDIA_TYPE, myCoverPhoto)).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.CHANGE_COVER_PIC).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                ProfileCoverUpdateActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_upload.setSelected(true);
                        btn_upload.setClickable(true);
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String myResponse = response.body().string();

                try
                {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.getString("status").equals("1"))
                    {
                        ProfileCoverUpdateActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb_coverUpload.setVisibility(View.GONE);
                                btn_upload.setSelected(true);
                                btn_upload.setClickable(true);
                                try {
                                    edt.putString("cover", ja.getString("coverPic"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getBaseContext(), ProfilePictureUploadActivity.class).
                                        putExtra("cover", coverUri));
                            }
                        });
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}