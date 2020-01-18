package com.icosom.social.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appunite.appunitevideoplayer.PlayerActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.icosom.social.Interface.RuntimePermissionsActivity;
import com.icosom.social.R;
import com.icosom.social.model.FeedModel;
import com.icosom.social.model.LikeDislikeModel;
import com.icosom.social.model.TagModel;
import com.icosom.social.recycler_adapter.ShowMyFeedsRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;
import com.icosom.social.utility.ImageFilePath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
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

public class ProfileActivity extends RuntimePermissionsActivity {
    CoordinatorLayout coordinatorLayout;
    AppController appController;
    RecyclerView rv_profile;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FeedModel> modelArrayList;
    ShowMyFeedsRecyclerAdapter recyclerAdapter;
    int counting = 0;
    boolean isLoading = false;
    boolean firstTime = true;
    File myCoverPhoto;
    Uri coverUri;
    File myProfilePhoto;
    Uri profileUri;
    Boolean alreadySet = true;
    ArrayList<String> allImages;
    ArrayList<String> allVideos;

    CollapsingToolbarLayout toolbar_layout;
    TextView edit_profile;
    TextView txt_description;
    ImageView iv_cover_image_profile;
    TextView txt_changeCover;
    CircleImageView iv_profile_image_profile;
    TextView txt_changeProfile;
    LinearLayout ll_addFriend_follow_profile;
    TextView txt_homeLocation;
    TextView txt_currentLocation;
    TextView txt_gender;
    TextView txt_email;
    TextView txt_dob;
    TextView txt_phoneNumber;
    TextView txt_website;
    LinearLayout lay_friendOne;
    LinearLayout lay_friendTwo;
    LinearLayout lay_friendThree;
    ImageView iv_friendOne;
    ImageView iv_friendTwo;
    ImageView iv_friendThree;
    TextView txt_friendOne;
    TextView txt_friendTwo;
    TextView txt_friendThree;
    TextView txt_showAllFriends;
    LinearLayout lay_followerOne;
    ImageView iv_followerOne;
    TextView txt_followerOne;
    LinearLayout lay_followerTwo;
    ImageView iv_followerTwo;
    TextView txt_followerTwo;
    LinearLayout lay_followerThree;
    ImageView iv_followerThree;
    TextView txt_followerThree;
    TextView txt_showAllFollowers;
    LinearLayout lay_followingOne;
    ImageView iv_followingOne;
    TextView txt_followingOne;
    LinearLayout lay_followingTwo;
    ImageView iv_followingTwo;
    TextView txt_followingTwo;
    LinearLayout lay_followingThree;
    ImageView iv_followingThree;
    TextView txt_followingThree;
    TextView txt_showAllFollowing;
    ImageView iv_imgOne;
    ImageView iv_imgTwo;
    ImageView iv_imgThree;
    TextView txt_showAllImages;
    FrameLayout lay_videoOne;
    ImageView iv_videoOne;
    FrameLayout lay_videoTwo;
    ImageView iv_videoTwo;
    FrameLayout lay_videoThree;
    ImageView iv_videoThree;
    TextView txt_showAllVideos;
    TextView txt_addFriend;
    TextView txt_followFriend;
    TextView txt_doneChangeCover;
    TextView txt_doneChangeProfile;
    CardView card_addFriend;
    CardView card_addFollow;
    NestedScrollView nestedScroll;
    ProgressBar progress;

    SharedPreferences sp;
    SharedPreferences.Editor edt;

    private static final String IMAGE_DIRECTORY_NAME = "iCosom";
    private Uri cameraUri;
    BottomSheetDialog sheetDialog;
    private static final int REQ_PER_GALLERY_COVER = 10;
    private static final int REQ_PER_CAMERA_COVER = 20;
    private static final int REQ_PER_GALLERY_PROFILE = 30;
    private static final int REQ_PER_CAMERA_PROFILE = 40;

     String device_token="";
    private String user_id;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setContentView(R.layout.activity_profile);

        appController = (AppController) getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();

        user_id = sp.getString("userId", "");

        userInfo(sp.getString("userId", ""));//get logged in user info


        coordinatorLayout = findViewById(R.id.CoordinatorLayout);
        txt_doneChangeProfile = (TextView) findViewById(R.id.txt_doneChangeProfile);
        txt_doneChangeCover = (TextView) findViewById(R.id.txt_doneChangeCover);
        txt_followFriend = (TextView) findViewById(R.id.txt_followFriend);
        txt_addFriend = (TextView) findViewById(R.id.txt_addFriend);
        edit_profile = (TextView) findViewById(R.id.edit_profile);
        txt_changeCover = (TextView) findViewById(R.id.txt_changeCover);
        txt_description = (TextView) findViewById(R.id.txt_description);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        ll_addFriend_follow_profile = (LinearLayout) findViewById(R.id.ll_addFriend_follow_profile);
        txt_homeLocation = (TextView) findViewById(R.id.txt_homeLocation);
        txt_currentLocation = (TextView) findViewById(R.id.txt_currentLocation);
        txt_gender = (TextView) findViewById(R.id.txt_gender);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_dob = (TextView) findViewById(R.id.txt_dob);
        txt_phoneNumber = (TextView) findViewById(R.id.txt_phoneNumber);
        txt_website = (TextView) findViewById(R.id.txt_website);
        lay_friendOne = (LinearLayout) findViewById(R.id.lay_friendOne);
        lay_friendTwo = (LinearLayout) findViewById(R.id.lay_friendTwo);
        lay_friendThree = (LinearLayout) findViewById(R.id.lay_friendThree);
        iv_friendOne = (ImageView) findViewById(R.id.iv_friendOne);
        iv_friendTwo = (ImageView) findViewById(R.id.iv_friendTwo);
        iv_friendThree = (ImageView) findViewById(R.id.iv_friendThree);
        txt_friendOne = (TextView) findViewById(R.id.txt_friendOne);
        txt_friendTwo = (TextView) findViewById(R.id.txt_friendTwo);
        txt_friendThree = (TextView) findViewById(R.id.txt_friendThree);
        txt_showAllFriends = (TextView) findViewById(R.id.txt_showAllFriends);
        lay_followerOne = findViewById(R.id.lay_followerOne);
        iv_followerOne = findViewById(R.id.iv_followerOne);
        txt_followerOne = findViewById(R.id.txt_followerOne);
        lay_followerTwo = findViewById(R.id.lay_followerTwo);
        iv_followerTwo = findViewById(R.id.iv_followerTwo);
        txt_followerTwo = findViewById(R.id.txt_followerTwo);
        lay_followerThree = findViewById(R.id.lay_followerThree);
        iv_followerThree = findViewById(R.id.iv_followerThree);
        txt_followerThree = findViewById(R.id.txt_followerThree);
        txt_showAllFollowers = findViewById(R.id.txt_showAllFollowers);
        lay_followingOne = findViewById(R.id.lay_followingOne);
        iv_followingOne = findViewById(R.id.iv_followingOne);
        txt_followingOne = findViewById(R.id.txt_followingOne);
        lay_followingTwo = findViewById(R.id.lay_followingTwo);
        iv_followingTwo = findViewById(R.id.iv_followingTwo);
        txt_followingTwo = findViewById(R.id.txt_followingTwo);
        lay_followingThree = findViewById(R.id.lay_followingThree);
        iv_followingThree = findViewById(R.id.iv_followingThree);
        txt_followingThree = findViewById(R.id.txt_followingThree);
        txt_showAllFollowing = findViewById(R.id.txt_showAllFollowing);
        iv_imgOne = findViewById(R.id.iv_imgOne);
        iv_imgTwo = findViewById(R.id.iv_imgTwo);
        iv_imgThree = findViewById(R.id.iv_imgThree);
        txt_showAllImages = findViewById(R.id.txt_showAllImages);
        lay_videoOne = findViewById(R.id.lay_videoOne);
        iv_videoOne = findViewById(R.id.iv_videoOne);
        lay_videoTwo = findViewById(R.id.lay_videoTwo);
        iv_videoTwo = findViewById(R.id.iv_videoTwo);
        lay_videoThree = findViewById(R.id.lay_videoThree);
        iv_videoThree = findViewById(R.id.iv_videoThree);
        txt_showAllVideos = findViewById(R.id.txt_showAllVideos);
        iv_cover_image_profile = (ImageView) findViewById(R.id.iv_cover_image_profile);
        iv_profile_image_profile = (CircleImageView) findViewById(R.id.iv_profile_image_profile);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        card_addFriend = (CardView) findViewById(R.id.card_addFriend);
        card_addFollow = (CardView) findViewById(R.id.card_addFollow);
        nestedScroll = (NestedScrollView) findViewById(R.id.nestedScroll);
        progress = (ProgressBar) findViewById(R.id.progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_doneChangeCover.setVisibility(View.GONE);
        txt_doneChangeProfile.setVisibility(View.GONE);

        rv_profile = findViewById(R.id.rv_profile);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rv_profile.setLayoutManager(layoutManager);
        allImages = new ArrayList<>();
        allVideos = new ArrayList<>();
        modelArrayList = new ArrayList<>();

        if (getIntent().getStringExtra("userId").equals(sp.getString("userId", ""))) {
            edit_profile.setVisibility(View.VISIBLE);
            txt_changeCover.setVisibility(View.VISIBLE);
            txt_changeProfile.setVisibility(View.VISIBLE);
            ll_addFriend_follow_profile.setVisibility(View.GONE);
        } else {
            checkPrivacy(getIntent().getStringExtra("userId"));
            edit_profile.setVisibility(View.GONE);
            txt_changeCover.setVisibility(View.GONE);
            txt_changeProfile.setVisibility(View.GONE);
            ll_addFriend_follow_profile.setVisibility(View.VISIBLE);
            isFriendOrFollower(sp.getString("userId", ""), getIntent().getStringExtra("userId"));
        }


        showProfile(getIntent().getStringExtra("userId"));





        txt_changeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog = new BottomSheetDialog(ProfileActivity.this);
                View sheetView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.pick_image_from_layout, null);
                sheetDialog.setContentView(sheetView);
                sheetDialog.show();

                TextView txt_gallery = sheetView.findViewById(R.id.txt_gallery);
                TextView txt_camera = sheetView.findViewById(R.id.txt_camera);

                txt_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheetDialog.hide();
                        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ProfileActivity.super.requestAppPermissions(new String[]
                                    {
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    }, R.string.runtimepermission_txt, REQ_PER_GALLERY_COVER);
                        } else {
                            openGalleryCover();

                        }
                    }
                });

                txt_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheetDialog.hide();
                        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ProfileActivity.super.requestAppPermissions(new String[]
                                    {
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.CAMERA
                                    }, R.string.runtimepermission_txt, REQ_PER_CAMERA_COVER);
                        } else {
                            openCameraCover();
                        }

                    }
                });

            }
        });

        txt_doneChangeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCover();
            }
        });

        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog = new BottomSheetDialog(ProfileActivity.this);
                View sheetView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.pick_image_from_layout, null);
                sheetDialog.setContentView(sheetView);
                sheetDialog.show();

                TextView txt_gallery = sheetView.findViewById(R.id.txt_gallery);
                TextView txt_camera = sheetView.findViewById(R.id.txt_camera);

                txt_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheetDialog.hide();
                        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ProfileActivity.super.requestAppPermissions(new String[]
                                    {
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    }, R.string.runtimepermission_txt, REQ_PER_GALLERY_PROFILE);
                        } else {
                            openGalleryProfile();
                        }

                    }
                });

                txt_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheetDialog.hide();
                        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ProfileActivity.super.requestAppPermissions(new String[]
                                    {
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.CAMERA
                                    }, R.string.runtimepermission_txt, REQ_PER_CAMERA_PROFILE);
                        } else {
                            openCameraProfile();
                        }
                    }
                });
            }
        });

        txt_doneChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile();
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EditProfileActivity.class));
            }
        });

        card_addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_addFriend.getText().toString().equalsIgnoreCase("Add Friend")) {
                    txt_addFriend.setText("Cancel Friend Request");
                    sendFriendRequest(sp.getString("userId", ""), getIntent().getStringExtra("userId"));
                } else if (txt_addFriend.getText().toString().equalsIgnoreCase("Cancel Friend Request")) {
                    txt_addFriend.setText("Add Friend");
                    cancelFriendRequest(sp.getString("userId", ""), getIntent().getStringExtra("userId"));
                } else if (txt_addFriend.getText().toString().equalsIgnoreCase("Remove Friend")) {
                    txt_addFriend.setText("Add Friend");
                    unFriend(sp.getString("userId", ""), getIntent().getStringExtra("userId"));

                }
            }
        });

        card_addFollow = findViewById(R.id.card_addFollow);
        card_addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_followFriend.getText().toString().equalsIgnoreCase("Follow")) {
                    txt_followFriend.setText("Unfollow");
                    follow(sp.getString("userId", ""), getIntent().getStringExtra("userId"));
                } else if (txt_followFriend.getText().toString().equalsIgnoreCase("Unfollow")) {
                    txt_followFriend.setText("Follow");
                    unFollow(sp.getString("userId", ""), getIntent().getStringExtra("userId"));
                }
            }
        });

        rv_profile.setNestedScrollingEnabled(false);

        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > v.getMaxScrollAmount() && alreadySet) {
                    //  Toast.makeText(ProfileActivity.this, "My Last Position", Toast.LENGTH_SHORT).show();
                    alreadySet = false;
                    progress.setVisibility(View.GONE);
                    loadMyData(counting, getIntent().getStringExtra("userId"));
                }
            }
        });

        txt_showAllFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show all friend list
            }
        });

        txt_showAllFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show all follower list
            }
        });

        txt_showAllFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show all following list
            }
        });

        txt_showAllImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show all images
                startActivity(new Intent(getBaseContext(), ShowSubMediaActivity.class).
                        putExtra("imgs", allImages).
                        putExtra("isVideos", false));
            }
        });

        txt_showAllVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ShowSubMediaActivity.class).
                        putExtra("imgs", allVideos).
                        putExtra("isVideos", true));
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQ_PER_GALLERY_COVER) {
            openGalleryCover();
        } else if (requestCode == REQ_PER_CAMERA_COVER) {
            openCameraCover();
        } else if (requestCode == REQ_PER_GALLERY_PROFILE) {
            openGalleryProfile();
        } else if (requestCode == REQ_PER_CAMERA_PROFILE) {
            openCameraProfile();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // upload cover pic
            coverUri = data.getData();
            Glide.
                    with(getBaseContext()).
                    load(coverUri).
                    into(iv_cover_image_profile);
            myCoverPhoto = new File(ImageFilePath.getPath(getBaseContext(), coverUri));
            txt_doneChangeCover.setVisibility(View.VISIBLE);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            profileUri = data.getData();
            Glide.
                    with(getBaseContext()).
                    load(profileUri).
                    into(iv_profile_image_profile);
            myProfilePhoto = new File(ImageFilePath.getPath(getBaseContext(), profileUri));
            txt_doneChangeProfile.setVisibility(View.VISIBLE);
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            // coverUri = cameraUri;
            coverUri = data.getData();
            Glide.
                    with(getBaseContext()).
                    load(coverUri).
                    into(iv_cover_image_profile);
            myCoverPhoto = new File(ImageFilePath.getPath(getBaseContext(), coverUri));
            txt_doneChangeCover.setVisibility(View.VISIBLE);

        } else if (requestCode == 4 && resultCode == RESULT_OK) {

            profileUri = data.getData();
            //profileUri = cameraUri;

            Glide.
                    with(getBaseContext()).
                    load(profileUri).
                    into(iv_profile_image_profile);
            myProfilePhoto = new File(ImageFilePath.getPath(getBaseContext(), profileUri));
            txt_doneChangeProfile.setVisibility(View.VISIBLE);
        }
    }

    public void openGallery() {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 1);
    }

    public void openGalleryCover() {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 1);
    }

    public void openGalleryProfile() {
        startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), 2);
    }

    public void openCameraCover() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, 3);
    }

    public void openCameraProfile() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, 4);
    }

    private void isFriendOrFollower(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("friendId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_FOLLOW_AND_FRIEND).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("isFriend").equals("false")) {
                                txt_addFriend.setText("Add Friend");
                            } else if (ja.getString("isFriend").equals("Pending")) {
                                txt_addFriend.setText("Cancel Friend Request");
                            } else {
                                txt_addFriend.setText("Remove Friend");
                            }

                            if (ja.getString("isFollower").equals("false")) {
                                txt_followFriend.setText("Follow");
                            } else {
                                txt_followFriend.setText("Unfollow");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



    private void sendFriendRequest(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("friendId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.SEND_FRIEND_REQUEST).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1"))
                            {

                                sendNotification_To_User(device_token);



                                txt_addFriend.setText("Cancel Friend Request");
                            } else {
                                txt_addFriend.setText("Add Friend");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public void sendNotification_To_User(String device_token)
    {

        System.out.println("kaif_SendNotificationMethod");

        if(device_token==null && device_token.equals(""))
        {
            System.out.println("Device_Token_Is_Null");
        }
        else {


            String user_name = sp.getString("firstName", "")
                    + " " + sp.getString("lastName", "");

            String message = "";
            String title = "Icosom";


            message = user_name + " has " + " send friend request";


            System.out.println("message" + message);
            System.out.println("title" + title);
            System.out.println("device_token" + device_token);
            System.out.println("user_id" + user_id);


            RequestBody body = new FormBody.Builder().
                    add("send_to", "single").
                    add("firebase_token", device_token).
                    add("message", message).
                    add("title", "icosom").
                    add("image_url", "").
                    add("action", "").
                    add("user_id", user_id).
                    build();

            Request request = new Request.Builder().
                    url("https://icosom.com/kaif_notification/newindex.php").
                    post(body).
                    build();

            appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Errorrrrr " + e.getMessage());

                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String myResponse = response.body().string();

                    System.out.println("notification_send_Success");

                }
            });

        }
    }






    private void cancelFriendRequest(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("active_user_id", userId).
                add("userId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.CANCEL_FRIEND_REQUEST).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1")) {
                                txt_addFriend.setText("Add Friend");
                            } else {
                                txt_addFriend.setText("Cancel Friend Request");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void unFriend(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("friendId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.UNFRIEND).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                System.out.println("Unfriend" + myResponse);
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1")) {
                                txt_addFriend.setText("Add Friend");
                            } else {
                                txt_addFriend.setText("Friends");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void follow(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("friendId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.FOLLOW).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1")) {
                                txt_followFriend.setText("Unfollow");
                            } else {
                                txt_followFriend.setText("Follow");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void unFollow(String userId, String friendId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("friendId", friendId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.UNFOLLOW).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1")) {
                                txt_followFriend.setText("Follow");
                            } else {
                                txt_followFriend.setText("Unfollow");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void showProfile(String userId) {

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.SHOW_PROFILE).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject ja = new JSONObject(myResponse);
                            JSONObject basic = ja.getJSONObject("basic");
                            toolbar_layout.setTitle(basic.getString("firstName") + " " + basic.getString("lastName"));
                            if (basic.getString("description").equals("null")) {
                                txt_description.setVisibility(View.GONE);
                            } else {
                                txt_description.setVisibility(View.VISIBLE);
                                txt_description.setText(basic.getString("description"));
                            }

                            Glide.
                                    with(getBaseContext()).
                                    load(CommonFunctions.FETCH_IMAGES + basic.getString("coverPhoto")).
                                    thumbnail(0.01f).
                                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                    into(iv_cover_image_profile);

                            Glide.
                                    with(getBaseContext()).
                                    load(CommonFunctions.FETCH_IMAGES + basic.getString("profilePic")).
                                    thumbnail(0.01f).
                                    apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                    into(iv_profile_image_profile);

                            txt_homeLocation.setText(basic.getString("from"));
                            txt_currentLocation.setText(basic.getString("currentPlace"));
                            txt_gender.setText(basic.getString("gender"));
                            txt_email.setText(basic.getString("email"));
                            txt_dob.setText(basic.getString("birthDate"));
                            txt_phoneNumber.setText(basic.getString("phone"));
                            txt_website.setText(basic.getString("website"));
                             device_token=basic.getString("device_token");

                            JSONObject friends = ja.getJSONObject("friends");
                            int totalFriends = Integer.parseInt(friends.getString("count"));
                            final JSONArray frnds = friends.getJSONArray("friend");

                            if (totalFriends < 4) {
                                txt_showAllFriends.setVisibility(View.GONE);
                            } else {
                                txt_showAllFriends.setVisibility(View.VISIBLE);
                                txt_showAllFriends.setText("Show all " +
                                        (totalFriends) +
                                        " friends");
                            }

                            if (totalFriends == 0) {
                                lay_friendOne.setVisibility(View.GONE);
                                lay_friendTwo.setVisibility(View.GONE);
                                lay_friendThree.setVisibility(View.GONE);
                                txt_showAllFriends.setText("No friends yet");
                            } else if (totalFriends == 1) {
                                lay_friendOne.setVisibility(View.VISIBLE);
                                lay_friendTwo.setVisibility(View.GONE);
                                lay_friendThree.setVisibility(View.GONE);
                                txt_showAllFriends.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendOne);
                                txt_friendOne.setText(frnds.getJSONObject(0).getString("userFirstName") + " " + frnds.getJSONObject(0).getString("userLastName"));

                                lay_friendOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFriends == 2) {
                                lay_friendOne.setVisibility(View.VISIBLE);
                                lay_friendTwo.setVisibility(View.VISIBLE);
                                lay_friendThree.setVisibility(View.GONE);
                                txt_showAllFriends.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendOne);
                                txt_friendOne.setText(frnds.getJSONObject(0).getString("userFirstName") + " " + frnds.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendTwo);
                                txt_friendTwo.setText(frnds.getJSONObject(1).getString("userFirstName") + " " + frnds.getJSONObject(1).getString("userLastName"));

                                lay_friendOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_friendTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFriends == 3) {
                                lay_friendOne.setVisibility(View.VISIBLE);
                                lay_friendTwo.setVisibility(View.VISIBLE);
                                lay_friendThree.setVisibility(View.VISIBLE);
                                txt_showAllFriends.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendOne);
                                txt_friendOne.setText(frnds.getJSONObject(0).getString("userFirstName") + " " + frnds.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendTwo);
                                txt_friendTwo.setText(frnds.getJSONObject(1).getString("userFirstName") + " " + frnds.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendThree);
                                txt_friendThree.setText(frnds.getJSONObject(2).getString("userFirstName") + " " + frnds.getJSONObject(2).getString("userLastName"));

                                lay_friendOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_friendTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_friendThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                lay_friendOne.setVisibility(View.VISIBLE);
                                lay_friendTwo.setVisibility(View.VISIBLE);
                                lay_friendThree.setVisibility(View.VISIBLE);
                                txt_showAllFriends.setVisibility(View.VISIBLE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendOne);
                                txt_friendOne.setText(frnds.getJSONObject(0).getString("userFirstName") + " " + frnds.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendTwo);
                                txt_friendTwo.setText(frnds.getJSONObject(1).getString("userFirstName") + " " + frnds.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + frnds.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_friendThree);
                                txt_friendThree.setText(frnds.getJSONObject(2).getString("userFirstName") + " " + frnds.getJSONObject(2).getString("userLastName"));

                                lay_friendOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_friendTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_friendThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", frnds.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            JSONObject followers = ja.getJSONObject("followers");
                            int totalFollowers = Integer.parseInt(followers.getString("count"));
                            final JSONArray follwrs = followers.getJSONArray("followers");

                            if (totalFollowers < 4) {
                                txt_showAllFollowers.setVisibility(View.GONE);
                            } else {
                                txt_showAllFollowers.setVisibility(View.VISIBLE);
                                txt_showAllFollowers.setText("Show all " +
                                        (totalFollowers) +
                                        " followers");
                            }

                            if (totalFollowers == 0) {
                                lay_followerOne.setVisibility(View.GONE);
                                lay_followerTwo.setVisibility(View.GONE);
                                lay_followerThree.setVisibility(View.GONE);
                                txt_showAllFollowers.setText("No followers yet");
                            } else if (totalFollowers == 1) {
                                lay_followerOne.setVisibility(View.VISIBLE);
                                lay_followerTwo.setVisibility(View.GONE);
                                lay_followerThree.setVisibility(View.GONE);
                                txt_showAllFollowers.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerOne);
                                txt_followerOne.setText(follwrs.getJSONObject(0).getString("userFirstName") + " " + follwrs.getJSONObject(0).getString("userLastName"));

                                lay_followerOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFollowers == 2) {
                                lay_followerOne.setVisibility(View.VISIBLE);
                                lay_followerTwo.setVisibility(View.VISIBLE);
                                lay_followerThree.setVisibility(View.GONE);
                                txt_showAllFollowers.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerOne);
                                txt_followerOne.setText(follwrs.getJSONObject(0).getString("userFirstName") + " " + follwrs.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerTwo);
                                txt_followerTwo.setText(follwrs.getJSONObject(1).getString("userFirstName") + " " + follwrs.getJSONObject(1).getString("userLastName"));

                                lay_followerOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followerTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFollowers == 3) {
                                lay_followerOne.setVisibility(View.VISIBLE);
                                lay_followerTwo.setVisibility(View.VISIBLE);
                                lay_followerThree.setVisibility(View.VISIBLE);
                                txt_showAllFollowers.setVisibility(View.GONE);
                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerOne);
                                txt_followerOne.setText(follwrs.getJSONObject(0).getString("userFirstName") + " " + follwrs.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerTwo);
                                txt_followerTwo.setText(follwrs.getJSONObject(1).getString("userFirstName") + " " + follwrs.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerThree);
                                txt_followerThree.setText(follwrs.getJSONObject(2).getString("userFirstName") + " " + follwrs.getJSONObject(2).getString("userLastName"));

                                lay_followerOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followerTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followerThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                lay_followerOne.setVisibility(View.VISIBLE);
                                lay_followerTwo.setVisibility(View.VISIBLE);
                                lay_followerThree.setVisibility(View.VISIBLE);
                                txt_showAllFollowers.setVisibility(View.VISIBLE);
                                Log.e("img1", "run," + follwrs.getJSONObject(0).getString("userFirstName") + " " + follwrs.getJSONObject(0).getString("userLastName"));
                                Log.e("img1", "run," + follwrs.getJSONObject(1).getString("userFirstName") + " " + follwrs.getJSONObject(1).getString("userLastName"));
                                Log.e("img1", "run," + follwrs.getJSONObject(2).getString("userFirstName") + " " + follwrs.getJSONObject(2).getString("userLastName"));


                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerOne);
                                txt_followerOne.setText(follwrs.getJSONObject(0).getString("userFirstName") + " " + follwrs.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerTwo);
                                txt_followerTwo.setText(follwrs.getJSONObject(1).getString("userFirstName") + " " + follwrs.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + follwrs.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followerThree);
                                txt_followerThree.setText(follwrs.getJSONObject(2).getString("userFirstName") + " " + follwrs.getJSONObject(2).getString("userLastName"));

                                lay_followerOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followerTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followerThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", follwrs.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            JSONObject following = ja.getJSONObject("following");
                            int totalFollowing = Integer.parseInt(following.getString("count"));
                            final JSONArray folwing = following.getJSONArray("following");

                            if (totalFollowing < 4) {
                                txt_showAllFollowing.setVisibility(View.GONE);
                            } else {
                                txt_showAllFollowing.setVisibility(View.VISIBLE);
                                txt_showAllFollowing.setText("Show all " +
                                        (totalFollowing) +
                                        " following");
                            }

                            if (totalFollowing == 0) {
                                lay_followingOne.setVisibility(View.GONE);
                                lay_followingTwo.setVisibility(View.GONE);
                                lay_followingThree.setVisibility(View.GONE);
                                txt_showAllFollowing.setText("No following yet");
                            } else if (totalFollowing == 1) {
                                lay_followingOne.setVisibility(View.VISIBLE);
                                lay_followingTwo.setVisibility(View.GONE);
                                lay_followingThree.setVisibility(View.GONE);
                                txt_showAllFollowing.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingOne);
                                txt_followingOne.setText(folwing.getJSONObject(0).getString("userFirstName") + " " + folwing.getJSONObject(0).getString("userLastName"));

                                lay_followingOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFollowing == 2) {
                                lay_followingOne.setVisibility(View.VISIBLE);
                                lay_followingTwo.setVisibility(View.VISIBLE);
                                lay_followingThree.setVisibility(View.GONE);
                                txt_showAllFollowing.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingOne);
                                txt_followingOne.setText(folwing.getJSONObject(0).getString("userFirstName") + " " + folwing.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingTwo);
                                txt_followingTwo.setText(folwing.getJSONObject(1).getString("userFirstName") + " " + folwing.getJSONObject(1).getString("userLastName"));

                                lay_followingOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followingTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalFollowers == 3) {
                                lay_followingOne.setVisibility(View.VISIBLE);
                                lay_followingTwo.setVisibility(View.VISIBLE);
                                lay_followingThree.setVisibility(View.VISIBLE);
                                txt_showAllFollowing.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingOne);
                                txt_followingOne.setText(folwing.getJSONObject(0).getString("userFirstName") + " " + folwing.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingTwo);
                                txt_followingTwo.setText(folwing.getJSONObject(1).getString("userFirstName") + " " + folwing.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingThree);
                                txt_followingThree.setText(folwing.getJSONObject(2).getString("userFirstName") + " " + folwing.getJSONObject(2).getString("userLastName"));

                                lay_followingOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followingTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followingThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                lay_followingOne.setVisibility(View.VISIBLE);
                                lay_followingTwo.setVisibility(View.VISIBLE);
                                lay_followingThree.setVisibility(View.VISIBLE);
                                txt_showAllFollowing.setVisibility(View.VISIBLE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(0).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingOne);
                                txt_followingOne.setText(folwing.getJSONObject(0).getString("userFirstName") + " " + folwing.getJSONObject(0).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(1).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingTwo);
                                txt_followingTwo.setText(folwing.getJSONObject(1).getString("userFirstName") + " " + folwing.getJSONObject(1).getString("userLastName"));

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + folwing.getJSONObject(2).getString("userProfilePic")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_followingThree);
                                txt_followingThree.setText(folwing.getJSONObject(2).getString("userFirstName") + " " + folwing.getJSONObject(2).getString("userLastName"));

                                lay_followingOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(0).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followingTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(1).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_followingThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                                                    putExtra("userId", folwing.getJSONObject(2).getString("userId")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            JSONObject photos = ja.getJSONObject("photos");
                            int totalPhotos = Integer.parseInt(photos.getString("count"));
                            JSONArray pics = photos.getJSONArray("Photos");

                            for (int i = 0; i < pics.length(); i++) {
                                allImages.add(pics.getString(i));
                            }

                            if (totalPhotos < 4) {
                                txt_showAllImages.setVisibility(View.GONE);
                            } else {
                                txt_showAllImages.setVisibility(View.VISIBLE);
                                txt_showAllImages.setText("Show all " +
                                        (totalPhotos) +
                                        " photos");
                            }

                            if (totalPhotos == 0) {
                                iv_imgOne.setVisibility(View.GONE);
                                iv_imgTwo.setVisibility(View.GONE);
                                iv_imgThree.setVisibility(View.GONE);
                                txt_showAllImages.setText("No images yet");
                            } else if (totalPhotos == 1) {
                                iv_imgOne.setVisibility(View.VISIBLE);
                                iv_imgTwo.setVisibility(View.GONE);
                                iv_imgThree.setVisibility(View.GONE);
                                txt_showAllImages.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgOne);
                            } else if (totalPhotos == 2) {
                                iv_imgOne.setVisibility(View.VISIBLE);
                                iv_imgTwo.setVisibility(View.VISIBLE);
                                iv_imgThree.setVisibility(View.GONE);
                                txt_showAllImages.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgTwo);
                            } else if (totalPhotos == 3) {
                                iv_imgOne.setVisibility(View.VISIBLE);
                                iv_imgTwo.setVisibility(View.VISIBLE);
                                iv_imgThree.setVisibility(View.VISIBLE);
                                txt_showAllImages.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgTwo);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(2)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgThree);
                            } else {
                                iv_imgOne.setVisibility(View.VISIBLE);
                                iv_imgTwo.setVisibility(View.VISIBLE);
                                iv_imgThree.setVisibility(View.VISIBLE);
                                txt_showAllImages.setVisibility(View.VISIBLE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgTwo);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + pics.getString(2)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_imgThree);
                            }

                            JSONObject videos = ja.getJSONObject("videos");
                            int totalVideos = Integer.parseInt(videos.getString("count"));
                            final JSONArray vidos = videos.getJSONArray("video");

                            for (int i = 0; i < vidos.length(); i++) {
                                allVideos.add(vidos.getString(i));
                            }

                            if (totalVideos < 4) {
                                txt_showAllVideos.setVisibility(View.GONE);
                            } else {
                                txt_showAllVideos.setVisibility(View.VISIBLE);
                                txt_showAllVideos.setText("Show all " +
                                        (totalVideos) +
                                        " videos");
                            }

                            if (totalVideos == 0) {
                                iv_videoOne.setVisibility(View.GONE);
                                iv_videoTwo.setVisibility(View.GONE);
                                iv_videoThree.setVisibility(View.GONE);
                                txt_showAllVideos.setText("No images yet");
                            } else if (totalPhotos == 1) {
                                iv_videoOne.setVisibility(View.VISIBLE);
                                iv_videoTwo.setVisibility(View.GONE);
                                iv_videoThree.setVisibility(View.GONE);
                                txt_showAllVideos.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoOne);

                                lay_videoOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(0),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalVideos == 2) {
                                iv_videoOne.setVisibility(View.VISIBLE);
                                iv_videoTwo.setVisibility(View.VISIBLE);
                                iv_videoThree.setVisibility(View.GONE);
                                txt_showAllVideos.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoTwo);

                                lay_videoOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(0),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_videoTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(1),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (totalVideos == 3) {
                                iv_videoOne.setVisibility(View.VISIBLE);
                                iv_videoTwo.setVisibility(View.VISIBLE);
                                iv_videoThree.setVisibility(View.VISIBLE);
                                txt_showAllVideos.setVisibility(View.GONE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoTwo);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(2)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoThree);

                                lay_videoOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(0),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_videoTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(1),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_videoThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(2),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                iv_videoOne.setVisibility(View.VISIBLE);
                                iv_videoTwo.setVisibility(View.VISIBLE);
                                iv_videoThree.setVisibility(View.VISIBLE);
                                txt_showAllVideos.setVisibility(View.VISIBLE);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(0)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoOne);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(1)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoTwo);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_VIDEOS + vidos.getString(2)).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_videoThree);

                                lay_videoOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(0),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_videoTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(1),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                lay_videoThree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(PlayerActivity.getVideoPlayerIntent(getBaseContext(),
                                                    CommonFunctions.FETCH_VIDEOS + vidos.getString(2),
                                                    ""));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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

        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMyData(final int count, String userId) {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userId).
                add("count", count + "").
                build();

        Log.e("kaif_count","skdj"+count);

        Request request = new Request.Builder().
                url(CommonFunctions.SHOW_ALL_MY_POST).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {

                    JSONArray ja = new JSONArray(myResponse);
                    if (ja.length() != 0) {

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);

                            JSONObject post = jo.getJSONObject("post");

                            JSONObject action = jo.getJSONObject("action");
                            JSONObject share = null;
                            JSONObject tag = null;
                            //  JSONArray likes = jo.getJSONArray("like");
                            ArrayList<LikeDislikeModel> likeArrayList = new ArrayList<>();
                           /* if (likes.length() != 0)
                            {
                                for (int l=0; l<likes.length(); l++)
                                {
                                    LikeDislikeModel likeDislikeModel = new LikeDislikeModel();

                                    JSONObject like = likes.getJSONObject(l);

                                    String userId = like.getString("userId");
                                    likeDislikeModel.setUserId(userId);
                                    String firstName = like.getString("firstName");
                                    likeDislikeModel.setFirstName(firstName);
                                    String lastName = like.getString("lastName");
                                    likeDislikeModel.setLasttName(lastName);
                                    String profilePicture = like.getString("profilePicture");
                                    likeDislikeModel.setProfilePicture(profilePicture);
                                    likeArrayList.add(likeDislikeModel);
                                }
                            }*/
                            Log.e("11111", "onResponse: " + jo);
                            ArrayList<LikeDislikeModel> dislikeArrayList = new ArrayList<>();

                            //JSONArray dislikes = jo.getJSONArray("dislike");
                            //Log.e("dislikes", "onResponse: "+dislikes);
                            /*if (dislikes.length() != 0){
                                for (int l=0; l<dislikes.length(); l++)
                                {
                                    LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
                                    JSONObject dislike = likes.getJSONObject(l);
                                    String userId = dislike.getString("userId");
                                    likeDislikeModel.setUserId(userId);
                                    String firstName = dislike.getString("firstName");
                                    likeDislikeModel.setFirstName(firstName);
                                    String lastName = dislike.getString("lastName");
                                    likeDislikeModel.setLasttName(lastName);
                                    String profilePicture = dislike.getString("profilePicture");
                                    likeDislikeModel.setProfilePicture(profilePicture);
                                    dislikeArrayList.add(likeDislikeModel);
                                }
                            }*/

                            if (post.getString("shareFlag").equalsIgnoreCase("1")) {
                                share = jo.getJSONObject("share");
                            }

                            if (post.getString("tagFlag").equalsIgnoreCase("1")) {
                                tag = jo.getJSONObject("tag");
                            }
                            Log.e("myfeed111", "onResponse: " + post);
                            String postId = post.getString("id");
                            Log.e("myfeed111", "onResponse: " + postId);
                            String postType = post.getString("type");
                            String postUserId = post.getString("userId");
                            String postTotalLikes = post.getString("totalLikes");
                            String postTotalDislikes = post.getString("totalDislikes");
                            String postTotalComments = post.getString("totalComments");
                            String postTotalShares = post.getString("totalShares");
                            String postDescription = post.getString("description");
                            String postImgFlag = post.getString("flag");
                            String postLink = post.getString("fileLink");
                            String device_token = post.getString("device_token");
                            String post_link = post.getString("post_url");

                            ArrayList<String> postFileLinks = new ArrayList<>();
                            for (String str : postLink.split(",")) {
                                postFileLinks.add(str);
                            }
                            if (postFileLinks.size() > 0 && postFileLinks.get(0).equalsIgnoreCase("")) {
                                postFileLinks.remove(0);
                            }
                            Boolean isShared = post.getString("shareFlag").equals("1");
                            String postTime = post.getString("postTime");
                            String postUserFirstName = post.getString("firstName");
                            String postUserLastName = post.getString("lastName");
                            String postUserProfilePicture = post.getString("profilePicture");
                            Boolean postIsTagged = post.getString("tagFlag").equals("1");
                            String postUserGender = post.getString("gender");

                            Boolean isMyLike = action.getString("like").equals("1");
                            Boolean isMyDislike = action.getString("dislike").equals("1");
                            Boolean isMyComment = action.getString("comment").equals("1");
                            Boolean isMyShare = action.getString("share").equals("1");

                            String sharePostId = share != null ? share.getString("id") : "";
                            String sharePostUserId = share != null ? share.getString("userId") : "";
                            String sharePostDescription = share != null ? share.getString("description") : "";
                            String sharePostImgFlag = share != null ? share.getString("flag") : "";
                            String sharePostlink = share != null ? share.getString("fileLink") : "";
                            ArrayList<String> shareFileLinks = new ArrayList<>();
                            for (String str : sharePostlink.split(",")) {
                                shareFileLinks.add(str);
                            }
                            if (shareFileLinks.size() > 0 && shareFileLinks.get(0).equalsIgnoreCase("")) {
                                shareFileLinks.remove(0);
                            }
                            String sharePostTime = share != null ? share.getString("postTime") : "";
                            String sharePostUserFirstName = share != null ? share.getString("firstName") : "";
                            String sharePostUserLastName = share != null ? share.getString("lastName") : "";
                            String sharePostUserProfilePicture = share != null ? share.getString("profilePicture") : "";
                            String sharePostUserGender = share != null ? share.getString("gender") : "";

                            String tagCount = tag != null ? tag.getString("tagCount") : "0";
                            ArrayList<TagModel> tagModels = new ArrayList<>();
                            ;
                            if (tag != null) {
                                JSONArray jaTag = tag.getJSONArray("tagData");
                                for (int j = 0; j < jaTag.length(); j++) {
                                    JSONObject joTag = jaTag.getJSONObject(j);

                                    String tagFirstName = joTag.getString("firstName");
                                    String tagLastName = joTag.getString("lastName");
                                    String tagId = joTag.getString("id");

                                    tagModels.add(new TagModel(tagFirstName, tagLastName, tagId));
                                }
                            }
                            modelArrayList.add(new FeedModel(
                                    post_link,postId, postType, postUserId, postTotalLikes, postTotalDislikes,
                                    postTotalComments, postTotalShares, postDescription, postImgFlag,
                                    postFileLinks, isShared, postTime, postUserFirstName, postUserLastName,
                                    postUserProfilePicture, postIsTagged, postUserGender, isMyLike, isMyDislike,
                                    isMyComment, isMyShare, sharePostId, sharePostUserId, sharePostDescription,
                                    sharePostImgFlag, shareFileLinks, sharePostTime, sharePostUserFirstName,
                                    sharePostUserLastName, sharePostUserProfilePicture, sharePostUserGender,
                                    tagCount, tagModels, likeArrayList, dislikeArrayList,device_token
                            ));
                        }
//
                        counting++;
                        isLoading = false;
                    } else {
                        isLoading = true;
                    }

                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (firstTime) {
                                firstTime = false;
                                recyclerAdapter = new ShowMyFeedsRecyclerAdapter(ProfileActivity.this, modelArrayList);
                                rv_profile.setAdapter(recyclerAdapter);
                            } else {
                                recyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //final int position, String postId, final String type, final String device_token,final String postUser_id, final String postUserName

   /* public void likeDislikeFeeds(final int position, String postId, String type) {

        MediaPlayer.create(getBaseContext(), R.raw.beep3).start();

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("id", postId).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LIKE_DISLIKE_FEED).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {




                        // no need to get response
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/

    public void likeDislikeFeeds(final int position, String postId, final String type, final String device_token,final String postUser_id, final String postUserName) {
        System.out.println("like_api_token"+position);
        System.out.println("post_id"+postId);
        System.out.println("postUserName"+postUserName);
        System.out.println("liked_Api_called");

        final String userid=sp.getString("userId", "");

        MediaPlayer.create(getApplicationContext(), R.raw.beep3).start();

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userid).
                add("id", postId).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LIKE_DISLIKE_FEED).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {


                        System.out.println("check_user_and_post_id");
                        //for send notification
                        if(device_token!=null && !device_token.equals(""))
                        {
                            if (!userid.equalsIgnoreCase(postUser_id))
                            {
                                System.out.println("like+userid"+userid);
                                System.out.println("like+postUserid"+postUser_id);

                                sendNotification_To_User(device_token,postUser_id,type, postUserName);
                            }
                            else
                                System.out.println("same post's user id");
                        }
                        else
                            System.out.println("Device_Token_Is_Null");




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void sendNotification_To_User(String device_token,String postuserid,String type,String postUsername)
    {

        String user_name=sp.getString("firstName", "")
                + " " + sp.getString("lastName", "");

        //String device_token = sp.getString("device_token", "");//firebase token id


        String message="";
        String title="Icosom";



        if(type.equalsIgnoreCase("like"))
            message =user_name+" has "+"liked on your post";

        else if(type.equalsIgnoreCase("dislike"))
            message =user_name+" has "+" disliked on your post";

        System.out.println("kaif_SendNotificationMethod");
        System.out.println("message"+message);
        System.out.println("title"+title);
        System.out.println("device_token"+device_token);
        System.out.println("postuserid"+postuserid);


        RequestBody body = new FormBody.Builder().
                add("send_to", "single").
                add("firebase_token", device_token).
                add("message", message).
                add("title", "icosom").
                add("image_url", "").
                add("action", "").
                add("user_id", postuserid).

                build();

        Request request = new Request.Builder().
                url("https://icosom.com/kaif_notification/newindex.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("notification_send_Success");
                /*try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("success").equals("1")) {
                        Log.e("notification_success","");
                        System.out.println("notification_success"+jo);
                    }
                    else
                    {
                        Log.e("notification__failed","");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }




    private void changeCover() {
        txt_doneChangeCover.setVisibility(View.GONE);
        String filePath = myCoverPhoto.getAbsolutePath();
        final MediaType MEDIA_TYPE = MediaType.parse("image/" + filePath.substring(filePath.lastIndexOf(".") + 1));
        System.out.println("mmmm " + filePath);
        RequestBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).
                addFormDataPart("data-source", "android").
                addFormDataPart("userId", sp.getString("userId", "")).
                addFormDataPart("pic_update[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                        RequestBody.create(MEDIA_TYPE, myCoverPhoto)).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.CHANGE_COVER_PIC).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.getString("status").equals("1")) {
                        ProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    edt.putString("cover", ja.getString("coverPic"));
                                    edt.apply();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeProfile() {
        txt_doneChangeProfile.setVisibility(View.GONE);
        String filePath = myProfilePhoto.getAbsolutePath();
        final MediaType MEDIA_TYPE = MediaType.parse("image/" + filePath.substring(filePath.lastIndexOf(".") + 1));

        RequestBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).
                addFormDataPart("data-source", "android").
                addFormDataPart("userId", sp.getString("userId", "")).
                addFormDataPart("pic_update[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                        RequestBody.create(MEDIA_TYPE, myProfilePhoto)).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.CHANGE_PROFILE_PIC).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                Log.e("error", "onFailure: "+e.getMessage() );

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("error", "onFailure: "+myResponse );

                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.getString("status").equals("1")) {
                        ProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
                                    edt.putString("profile", ja.getString("profilePic"));
                                    edt.apply();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userInfo(String id) {

        RequestBody body = new FormBody.Builder().
                add("userId", id).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/wallet/main/dmrAndroidProcess.php?action=userInfo").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {

                    final JSONObject ja = new JSONObject(myResponse);

                    Log.e("jaa", "run: " + ja);
                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                              /*  if (ja.getString("status").equalsIgnoreCase("success")) {*/
                                Log.e("jaa", "run: " + ja);
                                JSONArray jsonArray = ja.getJSONArray("data");
                                JSONObject jaa = jsonArray.getJSONObject(0);
                                Log.e("jaa", "run: " + jaa);
                                edt.putString("userId", jaa.getString("id"));
                                edt.putString("firstName", jaa.getString("firstName"));
                                edt.putString("lastName", jaa.getString("lastName"));
                                edt.putString("email", jaa.getString("email"));
                                edt.putString("phone", jaa.getString("phone"));
                                edt.putString("profiles", jaa.getString("profilePicture"));
                                edt.putString("profile", jaa.getString("profilePicture"));
                                edt.putString("cover", jaa.getString("coverPhoto"));
                                edt.putString("covers", jaa.getString("coverPhoto"));
                                edt.putString("birthDate", jaa.getString("birthDate"));
                                edt.putString("country", jaa.getString("country"));
                                edt.putString("state", jaa.getString("state"));
                                edt.putString("city", jaa.getString("city"));
                                edt.putString("gender", jaa.getString("gender"));
                                edt.putString("address1", jaa.getString("address1"));
                                edt.commit();
                                Log.e("jaa", "run: " + jaa.getString("profilePicture"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkPrivacy(String id) {

        RequestBody body = new FormBody.Builder().
                add("userId", id).
                add("data-source", "android").
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/profileProcess.php?action=privacyCheck").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {

                    final JSONObject ja = new JSONObject(myResponse);

                    Log.e("jaa", "run: " + ja);
                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {


                                JSONArray jsonArray = ja.getJSONArray("data");
                                JSONObject jaa = jsonArray.getJSONObject(0);
                                String emailcheck = jaa.getString("email_status");
                                String phonecheck = jaa.getString("phone_status");
                                if (emailcheck.equalsIgnoreCase("1")) {
                                    txt_email.setVisibility(View.GONE);
                                } else {
                                    txt_email.setVisibility(View.VISIBLE);
                                }
                                if (phonecheck.equalsIgnoreCase("1")) {

                                    txt_phoneNumber.setVisibility(View.GONE);
                                } else {
                                    txt_phoneNumber.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}