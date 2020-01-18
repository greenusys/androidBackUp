package com.example.g116.vvn_social.User_Profile_Dashboard_Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Adapter.Self_Post_Adapter;
import com.example.g116.vvn_social.Home_Activities.ShowSubMediaActivity;
import com.example.g116.vvn_social.Modal.Friend_List_Model;
import com.example.g116.vvn_social.Modal.Home_Post_Model;
import com.example.g116.vvn_social.Modal.Image;
import com.example.g116.vvn_social.Modal.ModelFeed;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Display_Photos.Show_All_Photos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Integer.parseInt;


public class Friend_Profile_Dashboard extends AppCompatActivity {



    RecyclerView recyclerView;
    ArrayList<ModelFeed> photos_models = new ArrayList<>();
    private  ArrayList<Friend_List_Model> friend_list_models = new ArrayList<>();
    private  ArrayList<Friend_List_Model> follower_list_models = new ArrayList<>();
    private  ArrayList<Friend_List_Model> following_list_models = new ArrayList<>();
    private  ArrayList<String> photos_list = new ArrayList<>();
    ArrayList<Home_Post_Model> modelFeedArrayList = new ArrayList<>();
    Self_Post_Adapter homePostAdapter;
    private AppController appController;
    private ArrayList<Image> images;
    ImageView iv_imgOne,iv_imgTwo,iv_imgThree;
    ImageView iv_friendOne,iv_friendTwo,iv_friendThree;
    ImageView iv_followerOne,iv_followerTwo,iv_followerThree;
    ImageView iv_followingOne,iv_followingTwo,iv_followingThree;
    private SessionManager session;
    TextView txt_showAllImages,txt_showAllFriends,txt_showAllFollowers,txt_showAllFollowing;
    TextView name,education,passed_year,merital_status,phone,email,address,city,state,dob,gender;
    TextView txt_changeProfile,txt_doneChangeProfile,txt_changeCover,txt_doneChangeCover;
    BottomSheetDialog sheetDialog;
    private Uri cameraUri;

    private static final String IMAGE_DIRECTORY_NAME = "vvn_city";
    private CircleImageView iv_profile_image_profile;
    private ImageView iv_cover_image_profile;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String user_id;
    private String user_type;
    HashMap<String, String> user;
    String logged_in_user_id="";
    boolean isfriend=false;
    boolean isfollow=false;

    LinearLayout friend_follow_layout;
    TextView add_friend;
    TextView follow_friend;
    CardView card_addFriend,card_addFollow;
    SwipeRefreshLayout swipe_refresh;
    public static int backfrom=0;
    LinearLayout pass_year_layout;

    TextView txt_friendOne,txt_friendTwo,txt_friendThree;
    TextView txt_followerOne,txt_followerTwo,txt_followerThree;
    TextView txt_followingOne,txt_followingTwo,txt_followingThree;

    private  String friend_user_id_one="",friend_user_type_one="";
    private  String friend_user_id_two="",friend_user_type_two="";
    private  String friend_user_id_three="",friend_user_type_three="";

    private  String follower_user_id_one="",follower_user_type_one="";
    private  String follower_user_id_two="",follower_user_type_two="";
    private  String follower_user_id_three="",follower_user_type_three="";

    private  String following_user_id_one="",following_user_type_one="";
    private  String following_user_id_two="",following_user_type_two="";
    private  String following_user_id_three="",following_user_type_three="";
    String user_picture="";
    String path = "https://vvn.city/";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__profile__dashboard);


        appController = (AppController) getApplicationContext();





        //it will display first 3 friend name with profile picture
        txt_friendOne = (TextView) findViewById(R.id.txt_friendOne);
        txt_friendTwo = (TextView) findViewById(R.id.txt_friendTwo);
        txt_friendThree = (TextView) findViewById(R.id.txt_friendThree);

        //it will display first 3 follower name with profile picture
        txt_followerOne = (TextView) findViewById(R.id.txt_followerOne);
        txt_followerTwo = (TextView) findViewById(R.id.txt_followerTwo);
        txt_followerThree = (TextView) findViewById(R.id.txt_followerThree);

        //it will display first 3 following name with profile picture
        txt_followingOne = (TextView) findViewById(R.id.txt_followingOne);
        txt_followingTwo = (TextView) findViewById(R.id.txt_followingTwo);
        txt_followingThree = (TextView) findViewById(R.id.txt_followingThree);





        //friend_follow_layout
        friend_follow_layout = (LinearLayout) findViewById(R.id.friend_follow_layout);
        add_friend = (TextView) findViewById(R.id.add_friend);
        follow_friend = (TextView) findViewById(R.id.follow_friend);

        card_addFriend = (CardView) findViewById(R.id.card_addFriend);
        card_addFollow = (CardView) findViewById(R.id.card_addFollow);

        pass_year_layout = (LinearLayout) findViewById(R.id.pass_year_layout);


        //photos list 3 images
        iv_imgOne = (ImageView) findViewById(R.id.iv_imgOne);
        iv_imgTwo = (ImageView) findViewById(R.id.iv_imgTwo);
        iv_imgThree = (ImageView) findViewById(R.id.iv_imgThree);

        pref = getApplicationContext().getSharedPreferences("vvn_sp", 0); // 0 - for private mode
        editor = pref.edit();


        //friend list 3 images
        iv_friendOne = (ImageView) findViewById(R.id.iv_friendOne);
        iv_friendTwo = (ImageView) findViewById(R.id.iv_friendTwo);
        iv_friendThree = (ImageView) findViewById(R.id.iv_friendThree);


        //follower list 3 images
        iv_followerOne = (ImageView) findViewById(R.id.iv_followerOne);
        iv_followerTwo = (ImageView) findViewById(R.id.iv_followerTwo);
        iv_followerThree = (ImageView) findViewById(R.id.iv_followerThree);

        //following list 3 images
        iv_followingOne = (ImageView) findViewById(R.id.iv_followingOne);
        iv_followingTwo = (ImageView) findViewById(R.id.iv_followingTwo);
        iv_followingThree = (ImageView) findViewById(R.id.iv_followingThree);




        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_doneChangeProfile = (TextView) findViewById(R.id.txt_doneChangeProfile);
        txt_doneChangeCover = (TextView) findViewById(R.id.txt_doneChangeCover);
        txt_changeCover = (TextView) findViewById(R.id.txt_changeCover);

        iv_profile_image_profile = (CircleImageView) findViewById(R.id.iv_profile_image_profile);
        iv_cover_image_profile = (ImageView) findViewById(R.id.iv_cover_image_profile);




        name = (TextView) findViewById(R.id.name);
        education = (TextView) findViewById(R.id.education);
        passed_year = (TextView) findViewById(R.id.passed_year);
        merital_status = (TextView) findViewById(R.id.mertial_status);
        phone = (TextView) findViewById(R.id.mobile);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        city = (TextView) findViewById(R.id.city);
        state = (TextView) findViewById(R.id.state);
        dob = (TextView) findViewById(R.id.dob);
        gender = (TextView) findViewById(R.id.gender);



        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();





          logged_in_user_id = user.get(SessionManager.KEY_ID);

        Log.e("sessin_user_id",""+logged_in_user_id);


        if (getIntent().getStringExtra("userId") != null) {
            user_id = getIntent().getStringExtra("userId");
        }

        if (getIntent().getStringExtra("user_type") != null) {
            user_type = getIntent().getStringExtra("user_type");


            if(logged_in_user_id.equalsIgnoreCase(user_id))
                friend_follow_layout.setVisibility(View.GONE);

            if(user_type.equalsIgnoreCase("student"))
                pass_year_layout.setVisibility(View.VISIBLE);

            if(user_type.equalsIgnoreCase("teacher"))
                pass_year_layout.setVisibility(View.GONE);
        }







        System.out.println("On_Create_is_called_friend_profile");

        Log.e("user_id","SD"+user_id);
        Log.e("user_type",""+user_type);





        recyclerView = (RecyclerView) findViewById(R.id.rv_profile);
        txt_showAllImages = (TextView) findViewById(R.id.txt_showAllImages);
        txt_showAllFriends = (TextView) findViewById(R.id.txt_showAllFriends);
        txt_showAllFollowers = (TextView) findViewById(R.id.txt_showAllFollowers);
        txt_showAllFollowing = (TextView) findViewById(R.id.txt_showAllFollowing);

        images = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        homePostAdapter = new Self_Post_Adapter(recyclerView,appController, user_id, user_type, getApplicationContext(), modelFeedArrayList, new User_Profile_Dashboard());
        recyclerView.setAdapter(homePostAdapter);

        recyclerView.setNestedScrollingEnabled(false);

        fetch_friend_basic_detail(user_id,user_type,logged_in_user_id);
        fetch_FriendList(user_id,user_type);
        fetch_Follower_List(user_id,user_type,"follower");
        fetch_Following_List(user_id,user_type,"following");
        fetch_Photos(user_id);
        Fetch_Self_Page_Post(user_id,user_type);
        showFriendProfile();



        txt_showAllFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Show_All_Friends_List.class)
                        .putExtra("friend_list",friend_list_models)
                        .putExtra("title","Friends List")
                );

            }
        });


        txt_showAllFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Show_All_Friends_List.class)
                        .putExtra("friend_list",follower_list_models)
                        .putExtra("title","Followers List")
                );

            }
        });

        txt_showAllFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Show_All_Friends_List.class)
                        .putExtra("friend_list",following_list_models)
                        .putExtra("title","Following List")
                );

            }
        });



        card_addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (add_friend.getText().toString().equalsIgnoreCase("Add Friend")) {
                    add_friend.setText("Cancel Friend Request");
                    addfriend_or_unfriend_or_cancel_friend_request_API(logged_in_user_id,user_id,"addfriend",user_type);
                } else if (add_friend.getText().toString().equalsIgnoreCase("Cancel Friend Request")) {
                    add_friend.setText("Add Friend");
                    addfriend_or_unfriend_or_cancel_friend_request_API(logged_in_user_id,user_id,"cancel",user_type);
                } else if (add_friend.getText().toString().equalsIgnoreCase("Remove Friend")) {
                    add_friend.setText("Add Friend");
                    addfriend_or_unfriend_or_cancel_friend_request_API(logged_in_user_id,user_id,"unfriend",user_type);

                }
            }
        });


        card_addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (follow_friend.getText().toString().equalsIgnoreCase("Follow"))
                {
                    System.out.println("follow_called"+follow_friend.getText().toString());
                    follow_friend.setText("Unfollow");
                    follow(logged_in_user_id,user_id,"follow");//follow friends
                }
                else if (follow_friend.getText().toString().equalsIgnoreCase("Unfollow"))
                {
                    System.out.println("un_follow_called"+follow_friend.getText().toString());
                    follow_friend.setText("Follow");
                    follow(logged_in_user_id,user_id,"unfollow");//unfollow friends
                }
                else
                {

                    follow(logged_in_user_id,user_id,follow_friend.getText().toString());//unfollow friends
                    System.out.println("else__called"+follow_friend.getText().toString());
                }
            }
        });


        //view profile image
        iv_profile_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!user_picture.equals("")) {
                    ArrayList<String> images_list = new ArrayList<>();
                    images_list.add(path+user_picture);
                    startActivity(new Intent(getApplicationContext(), ShowSubMediaActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("images_list", images_list).
                            putExtra("isVideos", false).
                            putExtra("position", 0));
                }

            }
        });












    }



    public void showFriendProfile()
    {


        //show Friend List Profile

        iv_friendOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_friendOne.getVisibility()==View.VISIBLE)
                {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId",friend_user_id_one).
                            putExtra("user_type", friend_user_type_one));
                }

            }
        });



        iv_friendTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_friendTwo.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", friend_user_id_two).
                            putExtra("user_type", friend_user_type_two));
                }

            }
        });


        iv_friendThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_friendThree.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", friend_user_id_three).
                            putExtra("user_type", friend_user_type_three));
                }

            }
        });



        //show Follower List Profile

        iv_followerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followerOne.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", follower_user_id_one).
                            putExtra("user_type", follower_user_type_one));
                }

            }
        });

        iv_followerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followerTwo.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", follower_user_id_two).
                            putExtra("user_type", follower_user_type_two));
                }

            }
        });


        iv_followerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followerThree.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", follower_user_id_three).
                            putExtra("user_type", follower_user_type_three));
                }

            }
        });



        //show Following List Profile

        iv_followingOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followingOne.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", following_user_id_one).
                            putExtra("user_type", following_user_type_one));
                }

            }
        });


        iv_followingTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followingTwo.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", following_user_id_two).
                            putExtra("user_type", following_user_type_two));
                }

            }
        });


        iv_followingThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_followingThree.getVisibility()==View.VISIBLE) {
                    startActivity(new Intent(getApplicationContext(), Friend_Profile_Dashboard.class).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
                            putExtra("userId", following_user_id_three).
                            putExtra("user_type", following_user_type_three));
                }

            }
        });



    }




    public void show_All_Images(View view)
    {


        startActivity(new Intent(getBaseContext(),Show_All_Photos.class).putExtra("images_list",images));
    }





    private void addfriend_or_unfriend_or_cancel_friend_request_API(String sent_by, String sent_to,String action,String type) {

        System.out.println("addfriend_or_unfriend_or_cancel_friend_request_API");


        System.out.println("sent_by"+sent_by);
        System.out.println("sent_to"+sent_to);
        System.out.println("action"+action);
        System.out.println("type"+type);

        RequestBody body = new FormBody.Builder().
                add("action", action).
                add("sent_to", sent_to).
                add("sent_by", sent_by).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/friend_request.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                            if (ja.getString("status").equals("1"))
                            {
                                System.out.println("addfriend_api_status"+ja.getString("msg"));

                            } else {
                                System.out.println("addfriend_api_status"+ja.getString("msg"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



    private void follow(String logged_in_user_id, String friendId,String action) {

        System.out.println("follow_api_called"+action);

        RequestBody body = new FormBody.Builder().
                add("action", action).
                add("user_id", logged_in_user_id).
                add("followId", friendId).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/follow_unfollow.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ja = new JSONObject(myResponse);
                           //for follow(success)
                            if (ja.getString("status").equals("1"))
                            {
                                System.out.println("1-follow_api_status_msg"+ja.getString("status")+" "+ja.getString("msg"));
                                follow_friend.setText("Unfollow");
                            }
                            //for follow(failed)
                            else if (ja.getString("status").equalsIgnoreCase("1.5"))
                            {
                                System.out.println("2-follow_api_status_msg"+ja.getString("status")+" "+ja.getString("msg"));
                                follow_friend.setText("Follow");
                            }



                            //for unfollow(success)
                            else if (ja.getString("status").equals("2")) {
                                System.out.println("3-follow_api_status_msg"+ja.getString("status")+" "+ja.getString("msg"));
                                follow_friend.setText("Follow");
                            }

                            //for unfollow(failed)
                            else if (ja.getString("status").equals("2.5")) {
                                System.out.println("4-follow_api_status_msg"+ja.getString("status")+" "+ja.getString("msg"));
                                follow_friend.setText("Unfollow");
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



    private void fetch_friend_basic_detail(String user_id, final String user_type, String logged_in_user_id)
    {
        System.out.println("fetch_fri_basic_detail");

        System.out.println("user_id"+user_id);
        System.out.println("user_type"+user_type);
        System.out.println("logged_in_user_id"+logged_in_user_id);


        final RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", user_type).
                add("profile_id", logged_in_user_id).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/user_basic_detail.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean isFollow=false;
                            boolean isfriend=false;

                            final JSONObject mainjson = new JSONObject(myResponse);
                            if (mainjson.getString("status").equals("1")) {

                                JSONArray jsonArray = mainjson.getJSONArray("data");

                                JSONObject data2 = mainjson.getJSONObject("data2");
                                JSONObject data3 = mainjson.getJSONObject("data3");

                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    System.out.println("success_fetch_basic");

                                     user_picture=jsonObject.getString("picture");

                                    user_picture=user_picture.replace("../","");

                                    Log.e("User_path+picture",""+path+user_picture);

                                    Glide.with(getApplicationContext())
                                            .load(path+user_picture).
                                            apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                            into(iv_profile_image_profile);


                                    String full_name=jsonObject.getString("firstName")+" "+ jsonObject.getString("lastName");

                                    getSupportActionBar().setTitle(full_name.toUpperCase());
                                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);




                               if(user_type.equalsIgnoreCase("student"))
                               {


                                   //  educationDetails.equals("null")? " ": educationDetails

                                   name.setText(jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                                   education.setText(jsonObject.getString("educationDetails").equals("null")? " ":jsonObject.getString("educationDetails") );
                                   passed_year.setText(jsonObject.getString("passYear").equals("null")? " ":jsonObject.getString("passYear"));
                                   merital_status.setText(jsonObject.getString("maritalStatus").equals("null")? " ":jsonObject.getString("maritalStatus"));
                                   email.setText(jsonObject.getString("email"));
                                   address.setText(jsonObject.getString("address").equals("null")? " ":jsonObject.getString("address"));
                                   city.setText(jsonObject.getString("city"));
                                   state.setText(jsonObject.getString("state"));
                                  // dob.setText(jsonObject.getString("birthDate"));
                                   gender.setText(jsonObject.getString("gender").equals("null")? " ":jsonObject.getString("gender"));

                                   if(!jsonObject.getString("phone").equals("0"))
                                       phone.setText(jsonObject.getString("phone"));

                                   if(!jsonObject.getString("birthDate").equals("0000-00-00"))
                                       dob.setText(jsonObject.getString("birthDate"));

//                                   System.out.println("kaif_birthDate"+jsonObject.getString("dob"));
                               }

                               if(user_type.equalsIgnoreCase("teacher")) {

                                   name.setText(jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                                   education.setText(jsonObject.getString("educationDetails").equals("null")? " ":jsonObject.getString("educationDetails") );
                                   merital_status.setText(jsonObject.getString("t_marital_status").equals("null")? " ":jsonObject.getString("t_marital_status"));
                                   email.setText(jsonObject.getString("email"));
                                   address.setText(jsonObject.getString("address").equals("null")? " ":jsonObject.getString("address"));
                                   city.setText(jsonObject.getString("city"));
                                   state.setText(jsonObject.getString("state"));
                                   //dob.setText(jsonObject.getString("dob"));
                                   gender.setText(jsonObject.getString("gender").equals("null")? " ":jsonObject.getString("gender"));


                                   if(!jsonObject.getString("phone").equals("0"))
                                       phone.setText(jsonObject.getString("phone"));

                                   if(!jsonObject.getString("dob").equals("0000-00-00"))
                                       dob.setText(jsonObject.getString("dob"));


                                   System.out.println("kaif_dob"+jsonObject.getString("dob"));

                               }









                                    //is friend status
                                    String status1=data2.getString("status");//if status is 1 means that user is friend

                                    //is follow status
                                    String status2=data3.getString("status");//if status is 1 means that user is followed

                                    //user is friend
                                    if (status1.equals("1"))
                                    {
                                        isfriend = true;
                                        add_friend.setText("Remove Friend");
                                        add_friend.setVisibility(View.VISIBLE);
                                    }


                                    //user is sent request
                                   else if (status1.equals("2"))
                                    {
                                        add_friend.setText("Cancel Friend Request");
                                        add_friend.setVisibility(View.VISIBLE);
                                    }
                                      else {
                                        isfriend = false;
                                        add_friend.setText("Add Friend");
                                        add_friend.setVisibility(View.VISIBLE);

                                    }



                                    //user is follow
                                    if (status2.equals("1")) {
                                        isFollow = true;
                                        follow_friend.setText("Unfollow");
                                        follow_friend.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        isFollow = false;
                                        follow_friend.setText("Follow");
                                        follow_friend.setVisibility(View.VISIBLE);
                                    }



                                    System.out.println("status2_"+status2);
                                    System.out.println("status1_"+status1);

                                    System.out.println("isfriend"+isfriend);
                                    System.out.println("isFollow"+isFollow);




                                }






                            }
                            else
                                System.out.println("fetch_basic_detail_no_data_found");




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }










    private void fetch_Photos(String user_id)
    {
        System.out.println("Search_APi_Called");

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_photos.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            final JSONObject mainjson = new JSONObject(myResponse);
                            if (mainjson.getString("status").equals("1")) {

                                JSONArray jsonArray = mainjson.getJSONArray("data");
                                String pic="";
                                String path="https://vvn.city/social";
                                for (int i = 0; i < jsonArray.length(); i++)//data loop
                                {
                                    JSONArray jsonArray1=jsonArray.getJSONArray(i);

                                    for(int j=0;j<jsonArray1.length();j++)
                                    {
                                        //Log.e("images_kaif-1", "skdj" + path + jsonArray1.getString(j));

                                        String image_name=jsonArray1.getString(j);

                                        if(!image_name.equals("")) {
                                            pic = image_name.substring(2);

                                          //  Log.e("pic_kaif-1", "skdj" + pic);


                                            Image image = new Image();
                                            image.setName("");
                                            image.setSmall( pic);
                                            image.setMedium( pic);
                                            image.setLarge( pic);
                                            image.setTimestamp("");
                                            images.add(image);

                                            photos_list.add(pic);





                                        }
                                    }//inner loop closed

                                    if(i==0)
                                    {
                                        Glide.with(getApplicationContext()).load(path + pic)
                                                .thumbnail(0.5f)

                                                .into(iv_imgOne);

                                    }
                                    if(i==1)
                                    {
                                        Glide.with(getApplicationContext()).load(path + pic)
                                                .thumbnail(0.5f)

                                                .into(iv_imgTwo);

                                    }
                                    if(i==3)
                                    {
                                        Glide.with(getApplicationContext()).load(path + pic)
                                                .thumbnail(0.5f)

                                                .into(iv_imgThree);

                                    }

                                }//data loop closed

                                txt_showAllImages.setVisibility(View.VISIBLE);



                            } else {
                                txt_showAllImages.setVisibility(View.INVISIBLE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }









    public void Fetch_Self_Page_Post(String user_id, String type) {


        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/self_post.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());


                if (Friend_Profile_Dashboard.this != null) {
                    Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject mainjson = new JSONObject(myResponse);
                    Log.e("mainjson", "" + mainjson);
                    String user_type="";
                    if (mainjson.getString("status").equals("1")) {

                        JSONArray jsonArray = mainjson.getJSONArray("data");
                        String server_time=mainjson.getString("servertime");
                        String path = "https://vvn.city/social/";
                        String pic = "";
                        String views[] = null;
                        int count_views = 0;
                        String pic_res = "";

                        if(modelFeedArrayList!=null)
                            modelFeedArrayList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String post="";
                            ArrayList<String> postFileLinks = new ArrayList<>();
                            if(!jsonObject.getString("fileLink").equalsIgnoreCase("no")) {
                                post = jsonObject.getString("fileLink");//post image


                                String post_pic[] = null;

                                post_pic = post.split(",");



                                for (String str : post.split(",")) {
                                    String image = str.replace("../", "");


                                    postFileLinks.add(image);
                                }
                            }

                            Log.e("image_list", "" + postFileLinks.size());
                            Log.e("image_links", "" + postFileLinks);


                            String id = jsonObject.getString("id");
                            String total_views = jsonObject.getString("views");//get total views


                            String total_view_array[]=total_views.split(",");

                           /* for(int z=0;z<total_view_array.length;z++)
                            Log.e("total_view_array", "" + total_view_array[z]);*/


                            Log.e("total_view_array_length", "" + total_view_array.length);
                            String mylikes2 = jsonObject.getString("0");//
                            String totalLikes = jsonObject.getString("totalLikes");
                            String totalDislikes = jsonObject.getString("totalDislikes");
                            String totalComments = jsonObject.getString("totalComments");
                            String totalShares = jsonObject.getString("totalShares");
                            String postTime = jsonObject.getString("postTime");
                            String post_status = jsonObject.getString("description");
                            String user_profile_pic=jsonObject.getString("picture");//user profile image
                            user_profile_pic=user_profile_pic.replace("../","");
                            String full_name = jsonObject.getString("firstName") + " " +
                                    jsonObject.getString("lastName");
                            String post_user_id = jsonObject.getString("userId");






                            Boolean isMyLike = false;
                            Boolean isMyDislike = false;

                            // that is post has more than 0 likes
                            if (mylikes2.equals("1"))
                                isMyLike = true;

                            // that is post has more than 0 dislikes
                            if (mylikes2.equals("0"))
                                isMyDislike = true;

                            String final_time= calculate_time(postTime,server_time);


                            Home_Post_Model home = new Home_Post_Model();
                            home.setPost_id(id);

                            if(total_view_array.length==1) {
                                home.setTotal_views("0");
                            }
                            else
                                home.setTotal_views(Integer.toString(total_view_array.length));

                            String value = jsonObject.getString("user_type");
                            if (value.equals("1"))
                                user_type = "teacher";
                            if (value.equals("2"))
                                user_type = "student";


                            home.setTotalLikes(totalLikes);
                            home.setTotalDislikes(totalDislikes);
                            home.setTotalComments(totalComments);
                            home.setTotalShares(totalShares);
                           home.setUser_type(user_type);
                            home.setPostTime(final_time);
                            home.setPost_pic(path + pic);
                            home.setPost_status(post_status);
                            home.setFull_name(full_name);
                            home.setUser_profile_pic( user_profile_pic);
                            home.setImage_list(postFileLinks);
                            home.setPost_User_Name(full_name);

                            home.setMyLike(isMyLike);
                            home.setMyDislike(isMyDislike);
                            home.setPost_user_id(post_user_id);

                            System.out.println("path + pic"+ user_profile_pic);


                            //  home.setUser_profile_pic(user_profile_pic);
                            modelFeedArrayList.add(home);


                            if (Friend_Profile_Dashboard.this != null) {
                                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.setVisibility(View.VISIBLE);

                                    }
                                });
                            }





                        }


                        if (Friend_Profile_Dashboard.this != null) {
                            Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    homePostAdapter.notifyDataSetChanged();
                                }
                            });
                        }


                        Log.e("modelFeedArrayList", "ksdj" + modelFeedArrayList);
                        Log.e("modelFeedArrayList", "ksdj" + modelFeedArrayList.size());


                    } else {
                        if (Friend_Profile_Dashboard.this != null) {
                            Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setVisibility(View.GONE);
                                    Log.e("No data found", "ksdj" + "");
                                }
                            });
                        }


                    }


                } catch (JSONException e) {
                    if (Friend_Profile_Dashboard.this != null) {
                        Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                    }

                    e.printStackTrace();
                }


            }
        });
    }

    private void fetch_FriendList(String user_id,String type)
    {
        Log.e("fetch_Frienist_APi_Call", "skdj");
        Log.e("user_id", ""+user_id);
        Log.e("type", "skdj"+type);

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_friendlist.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String user_type="";
                            String path = "https://vvn.city/";
                            final JSONObject mainjson = new JSONObject(myResponse);
                            Log.e("mainjson_frnd_list",""+mainjson);
                            Log.e("status",""+mainjson.getString("status"));


                            if (mainjson.getString("status").equals("1")) {

                                if(friend_list_models!=null)
                                    friend_list_models.clear();


                                JSONArray jsonArray = mainjson.getJSONArray("data");


                                for (int i = 0; i < jsonArray.length(); i++)//data loop
                                {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    String id=String.valueOf(i);
                                    String user_id=jsonObject.getString("sno");
                                    String friend_id=jsonObject.getString("fr_id");
                                    String firstName=jsonObject.getString("firstName");
                                    String lastName=jsonObject.getString("lastName");
                                    String educationDetails=jsonObject.getString("educationDetails");
                                    String picture=jsonObject.getString("picture");
                                    picture=picture.replace("../","");

                                    String value= jsonObject.getString("friend_type_id");

                                    if(value.equals("1"))
                                        user_type="teacher";
                                    if(value.equals("2"))
                                        user_type="student";


                                    System.out.println("1-user_type"+jsonObject.getString("friend_type_id"));

                                    Log.e("User_pic_Profile_Dash",""+path+picture);

                                    Friend_List_Model f=new Friend_List_Model();
                                    f.setId(id);
                                    f.setUser_id(user_id);
                                    f.setFriend_id(friend_id);
                                    f.setFirstName(firstName);
                                    f.setLastName(lastName);
                                    f.setEducationDetails(educationDetails);
                                    f.setEducationDetails(educationDetails);
                                    f.setUser_type(user_type);
                                    f.setPicture(path+picture);

                                    friend_list_models.add(f);

                                    if(i==0)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_friendOne);
                                        txt_friendOne.setText(firstName+" "+lastName);
                                        txt_friendOne.setVisibility(View.VISIBLE);
                                        friend_user_id_one=user_id;
                                        friend_user_type_one=user_type;

                                    }
                                    if(i==1)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_friendTwo);
                                        txt_friendTwo.setText(firstName+" "+lastName);
                                        txt_friendTwo.setVisibility(View.VISIBLE);
                                        friend_user_id_two=user_id;
                                        friend_user_type_two=user_type;

                                    }
                                    if(i==2)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_friendThree);
                                        txt_friendThree.setText(firstName+" "+lastName);
                                        txt_friendThree.setVisibility(View.VISIBLE);
                                        friend_user_id_three=user_id;
                                        friend_user_type_three=user_type;


                                    }

                                }//data loop closed

                                Log.e("friend_list_models_size",""+friend_list_models.size());

                                txt_showAllFriends.setVisibility(View.VISIBLE);



                            } else {
                                txt_showAllFriends.setVisibility(View.INVISIBLE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }




    private void fetch_Follower_List(String user_id,String type,String action)
    {
        Log.e("fetch_Follower_List_Ca", "");

        Log.e("user_id", ""+user_id);
        Log.e("type", ""+type);
        Log.e("action", ""+action);

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).
                add("action", "follower").

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/follower_following.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String path = "https://vvn.city/";
                            String user_type="";
                            final JSONObject mainjson = new JSONObject(myResponse);
                            Log.e("mainjson_frnd_list",""+mainjson);
                            Log.e("status",""+mainjson.getString("status"));


                            if (mainjson.getString("status").equals("1")) {


                                JSONArray jsonArray = mainjson.getJSONArray("data");

                                if(follower_list_models!=null)
                                    follower_list_models.clear();


                                for (int i = 0; i < jsonArray.length(); i++)//data loop
                                {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    String id=jsonObject.getString("id");
                                    String user_id=jsonObject.getString("sno");
                                    String follow_to_id=jsonObject.getString("follow_to_id");
                                    String firstName=jsonObject.getString("firstName");
                                    String lastName=jsonObject.getString("lastName");
                                    String educationDetails=jsonObject.getString("educationDetails");


                                    String picture=jsonObject.getString("picture");

                                    picture=picture.replace("../","");

                                    String value= jsonObject.getString("social_type");
                                    if(value.equals("1"))
                                        user_type="teacher";
                                    if(value.equals("2"))
                                        user_type="student";

                                    Log.e("User_pic_follower_Dash",""+path+picture);

//                                    System.out.println("2-user_type"+jsonObject.getString("friend_type_id"));

                                    Friend_List_Model f=new Friend_List_Model();
                                    f.setId(id);
                                    f.setUser_id(user_id);
                                    f.setFollow_to_id(follow_to_id);
                                    f.setFirstName(firstName);
                                    f.setLastName(lastName);
                                    f.setEducationDetails(educationDetails);
                                    f.setPicture(path+picture);
                                    f.setUser_type(user_type);
                                    follower_list_models.add(f);

                                    if(i==0)
                                    {

                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followerOne);
                                        txt_followerOne.setText(firstName+" "+lastName);
                                        txt_followerOne.setVisibility(View.VISIBLE);
                                        follower_user_id_one=user_id;
                                        follower_user_type_one=user_type;

                                    }
                                    if(i==1)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followerTwo);
                                        txt_followerTwo.setText(firstName+" "+lastName);
                                        txt_followerTwo.setVisibility(View.VISIBLE);
                                        follower_user_id_two=user_id;
                                        follower_user_type_two=user_type;

                                    }
                                    if(i==2)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followerThree);
                                        txt_followerThree.setText(firstName+" "+lastName);
                                        txt_followerThree.setVisibility(View.VISIBLE);
                                        follower_user_id_three=user_id;
                                        follower_user_type_three=user_type;

                                    }

                                }//data loop closed

                                Log.e("friend_list_models_size",""+friend_list_models.size());

                                txt_showAllFollowers.setVisibility(View.VISIBLE);



                            } else {
                                txt_showAllFollowers.setVisibility(View.INVISIBLE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    private void fetch_Following_List(String user_id,String type,String action)
    {
        Log.e("fetch_Follower_List_Ca", "");

        Log.e("user_id", ""+user_id);
        Log.e("type", ""+type);
        Log.e("action", ""+action);

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).
                add("action", "following").

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/follower_following.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Friend_Profile_Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String path = "https://vvn.city/";
                            String user_type="";
                            final JSONObject mainjson = new JSONObject(myResponse);
                            Log.e("mainjson_frnd_list",""+mainjson);
                            Log.e("status",""+mainjson.getString("status"));


                            if (mainjson.getString("status").equals("1")) {


                                JSONArray jsonArray = mainjson.getJSONArray("data");

                                if(following_list_models!=null)
                                    following_list_models.clear();


                                for (int i = 0; i < jsonArray.length(); i++)//data loop
                                {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    String id=jsonObject.getString("id");
                                    String user_id=jsonObject.getString("sno");
                                    String follow_to_id=jsonObject.getString("follow_to_id");
                                    String firstName=jsonObject.getString("firstName");
                                    String lastName=jsonObject.getString("lastName");
                                    String educationDetails=jsonObject.getString("educationDetails");


                                    String picture=jsonObject.getString("picture");

                                    picture=picture.replace("../","");

                                    String value= jsonObject.getString("social_type");
                                    if(value.equals("1"))
                                        user_type="teacher";
                                    if(value.equals("2"))
                                        user_type="student";

                                    System.out.println("3-user_type"+user_type);
                                    Log.e("User_pic_follower_Dash",""+path+picture);

                                    Friend_List_Model f=new Friend_List_Model();
                                    f.setId(id);
                                    f.setUser_id(user_id);
                                    f.setFollow_to_id(follow_to_id);
                                    f.setFirstName(firstName);
                                    f.setLastName(lastName);
                                    f.setEducationDetails(educationDetails);
                                    f.setPicture(path+picture);
                                    f.setUser_type(user_type);
                                    following_list_models.add(f);

                                    if(i==0)
                                    {

                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followingOne);
                                        txt_followingOne.setText(firstName+" "+lastName);
                                        txt_followingOne.setVisibility(View.VISIBLE);
                                        following_user_id_one=user_id;
                                        following_user_type_one=user_type;




                                    }
                                    if(i==1)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followingTwo);
                                        txt_followingTwo.setText(firstName+" "+lastName);
                                        txt_followingTwo.setVisibility(View.VISIBLE);
                                        following_user_id_two=user_id;
                                        following_user_type_two=user_type;

                                    }
                                    if(i==2)
                                    {
                                        Glide.
                                                with(getApplicationContext()).
                                                load(path+picture).
                                                thumbnail(0.01f).apply(new RequestOptions()
                                                .placeholder(R.drawable.placeholder))
                                                .into(iv_followingThree);
                                        txt_followingThree.setText(firstName+" "+lastName);
                                        txt_followingThree.setVisibility(View.VISIBLE);
                                        following_user_id_three=user_id;
                                        following_user_type_three=user_type;

                                    }

                                }//data loop closed

                                Log.e("friend_list_models_size",""+friend_list_models.size());

                                txt_showAllFollowing.setVisibility(View.VISIBLE);



                            } else {
                                txt_showAllFollowing.setVisibility(View.INVISIBLE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    private String calculate_time(String postTime2, String server_timee) {

        System.out.println("calculate_time");

        String post_time= arrange_post_time(postTime2);
        String server_time= arrange_server_time(server_timee);
        // System.out.println("calc_time_post_time "+post_time);
        //System.out.println("calc_time_server_time"+server_time);

        String post_time_date[]=post_time.split(" ");
        String server_time_date[]=server_time.split(" ");

        String post_time2[];
        String server_time2[];
        int post_time2_hr;
        int server_time2_hr;
        int min_diff=0;
        int post_time2_min=0;
        int server_time2_min=0;

        //if post and server date is same
        if(post_time_date[0].equalsIgnoreCase(server_time_date[0]))
        {

            post_time2=post_time_date[1].split(":");
            server_time2=server_time_date[1].split(":");

            post_time2_hr= parseInt(post_time2[0]);
            server_time2_hr= parseInt(server_time2[0]);
            post_time2_min= parseInt(post_time2[1]);
            server_time2_min= parseInt(server_time2[1]);
            min_diff=Math.abs(post_time2_min-server_time2_min);

            //if post and server hour is same then calculate min difference
            if(post_time2_hr==server_time2_hr)
            {
                System.out.println("post and server hour is same");


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }

            //if post and server hour diff is 1

            else if(Math.abs(post_time2_hr-server_time2_hr)==1 )
            {
                System.out.println("post and server hour diff is 1");

                int result1=post_time2_hr*60+post_time2_min;
                int result2=server_time2_hr*60+server_time2_min;
                int res=Math.abs(result1-result2);


                if(res>59)
                    return "About an Hour Ago";
                else
                    return String.valueOf(res)+" minute ago ";


            }


           /* else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min==server_time2_min )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min==server_time2_min");

                return "About an Hour Ago";

            }


            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1
            // then caluclate difference

            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min>0 && server_time2_min>0 )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min>0 *//*&& server_time2_min>0*//**//* *//* )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }else if(Math.abs(post_time2_hr-server_time2_hr)==1 && server_time2_min>0 *//*&& server_time2_min>0*//**//* *//* )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min > 0 ");

                post_time2_min= parseInt(post_time2[1]);
                server_time2_min= parseInt(server_time2[1]);


                if(min_diff!=0)
                    return String.valueOf(min_diff)+" minute ago ";
                else
                    return "Just Now";

            }

            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1 and  post_time2_min is 0 and
            // then caluclate difference
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && post_time2_min==0 )
            {
                System.out.println("post and server hour diff is 1 and post_time2_min==0");

                    return "About an Hour Ago";
            }

            //if post and server hour is different  but date is same
            //if post and server hour diff is only 1 and  server_time2_min is 0 and
            // then caluclate difference
            else if(Math.abs(post_time2_hr-server_time2_hr)==1 && server_time2_min==0 )
            {
                System.out.println("post and server hour diff is 1 and server_time2_min==0");

                    return "About an Hour Ago";
            }
*/
            /*else
            {
                return "About an Hour Ago";
            }*/
            else
                return post_time;

        }
        else
        {
            System.out.println("calc_post_date "+post_time_date[0]+" "+post_time_date[1]);
            //return post_time_date[0]+" "+post_time_date[1];
            return post_time;

        }



    }



    public String arrange_post_time(String postTime)
    {

        String date2[]=postTime.split(" ");
        String date=date2[0];//only date
        String time=date2[1];//only time
        String date_result="";
        String time_result="";
        int hours=0;
        String date3[]=date.split("-");
        String time3[]=time.split(":");

        date_result=date3[2]+"-"+date3[1]+"-"+date3[0];


        if(parseInt(time3[0])>12)
        {
            hours= parseInt(time3[0])-12;

        }
        else
            hours= parseInt(time3[0]);



        //  System.out.println("time_champ"+String.valueOf(parseInt(time3[0])));

        if(parseInt(time3[0])>=12 ) {
            // System.out.println("if_kaif");
            time_result = Integer.toString(hours) + ":" + time3[1] + ":" + time3[2] + " PM";
        }
        else {
            // System.out.println("else_kaif");
            time_result = Integer.toString(hours) + ":" + time3[1] + ":" + time3[2] + " AM";
        }


        return date_result+" "+time_result;
    }

    public String arrange_server_time(String postTime)
    {

        String date2[]=postTime.split(" ");
        String date=date2[0];//only date
        String time=date2[1];//only time
        String date_result="";
        String time_result="";
        int hours=0;
        String date3[]=date.split("-");
        String time3[]=time.split(":");

        date_result=date3[2]+"-"+date3[1]+"-"+date3[0];


        if(parseInt(time3[0])>12)
        {
            hours= parseInt(time3[0])-12;

        }
        else
            hours= parseInt(time3[0]);

        if(parseInt(time3[0])>12 && parseInt(time3[0])==12)
            time_result=Integer.toString(hours)+":"+time3[1]+":"+time3[2]+" PM";
        else
            time_result=Integer.toString(hours)+":"+time3[1]+":"+time3[2]+" AM";


        return date_result+" "+time_result;
    }








    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom=1;
    }







}
