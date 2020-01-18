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
import android.view.MenuItem;
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

public class ProfilePictureUploadActivity extends AppCompatActivity
{
    TextView txt_skip;
    CollapsingToolbarLayout toolbar_layout;
    Uri coverUri = null;
    Uri profileUri = null;
    ImageView iv_cover_image_profile;
    ImageView iv_profile_image_profile;
    TextView btn_pick;
    TextView btn_upload;
    BottomSheetDialog sheetDialog;
    File myProfilePhoto;
    ProgressBar pb_profileUpload;
    AppController appController;
    String id;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_update);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sheetDialog = new BottomSheetDialog(this);

        pb_profileUpload = (ProgressBar) findViewById(R.id.pb_profileUpload);
        iv_cover_image_profile = (ImageView) findViewById(R.id.iv_cover_image_profile);
        iv_profile_image_profile = (ImageView) findViewById(R.id.iv_profile_image_profile);
        btn_pick = (TextView) findViewById(R.id.btn_pick);
        btn_upload = (TextView) findViewById(R.id.btn_upload);
        txt_skip = (TextView) findViewById(R.id.txt_skip);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        pb_profileUpload.setVisibility(View.GONE);

        if (getIntent() != null)
        {
            coverUri = getIntent().getParcelableExtra("cover");
        }

        id = sp.getString("userId", "");
        String name = sp.getString("firstName", "") +" "+ sp.getString("lastName", "");
        toolbar_layout.setTitle(name);

        View sheetView = LayoutInflater.from(this).inflate(R.layout.pick_image_from_layout, null);
        sheetDialog.setContentView(sheetView);

        TextView txt_camera = sheetView.findViewById(R.id.txt_camera);
        TextView txt_gallery = sheetView.findViewById(R.id.txt_gallery);

        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.hide();
                showGallery();
            }
        });

        txt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.hide();
                showCamera();
            }
        });

        if (coverUri != null)
            iv_cover_image_profile.setImageURI(coverUri);

        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProfileDescriptionUpdateActivity.class));
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btn_upload.setSelected(false);
                btn_upload.setClickable(false);
                changeProfile();
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
    }

    private void showGallery()
    {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 1);
    }

    private void showCamera()
    {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            profileUri = data.getData();
            iv_profile_image_profile.setImageURI(profileUri);
            myProfilePhoto = new File(ImageFilePath.getPath(getBaseContext(), profileUri));
            btn_upload.setSelected(true);
            btn_upload.setClickable(true);
        }
    }

    private void changeProfile()
    {
        pb_profileUpload.setVisibility(View.VISIBLE);
        String filePath = myProfilePhoto.getAbsolutePath();
        final MediaType MEDIA_TYPE = MediaType.parse("image/"+filePath.substring(filePath.lastIndexOf(".")+1));

        RequestBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).
                addFormDataPart("data-source", "android").
                addFormDataPart("userId", id).
                addFormDataPart("pic_update[]", filePath.substring(filePath.lastIndexOf("/")+1),
                        RequestBody.create(MEDIA_TYPE, myProfilePhoto)).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.CHANGE_PROFILE_PIC).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                ProfilePictureUploadActivity.this.runOnUiThread(new Runnable() {
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
                        ProfilePictureUploadActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb_profileUpload.setVisibility(View.GONE);
                                btn_upload.setSelected(true);
                                btn_upload.setClickable(true);
                                try {
                                    edt.putString("profile", ja.getString("profilePic"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getBaseContext(), ProfileDescriptionUpdateActivity.class).
                                        putExtra("cover", coverUri).
                                        putExtra("profile", profileUri));
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}