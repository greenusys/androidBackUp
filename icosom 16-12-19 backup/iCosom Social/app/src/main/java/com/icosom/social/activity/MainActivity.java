package com.icosom.social.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.Chat_Main_Activity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Online_User_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.Icosom_Chat_Package.activity.Notification.MyService;
import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Activity.Talent_Plan;
import com.icosom.social.fragment.HomeFragment;
import com.icosom.social.fragment.NotificationFragment;
import com.icosom.social.fragment.ShowFriendRequestFragment;
import com.icosom.social.fragment.Show_Friend_List_Chat;
import com.icosom.social.fragment.WalletFragment;
import com.icosom.social.list_adapter.NavListAdapter;
import com.icosom.social.model.ActiveUserModel;
import com.icosom.social.recycler_adapter.ShowActiveUserRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String FILTER_ACTION_KEY = "any_key";
    //Toolbar toolbar;
    public static String user_id = "";
    public static String User_Name = "";
    public static Socket mSocket;
    public static boolean app_destroy;
    public static JSONObject newUserData;//used in chat activity for Notifytyping
    public static String user_socket_id, user_socket_picture;//used in ChatActivity
    public static ArrayList<Online_User_Model> online_user_list = new ArrayList<>();
    final ArrayList<Online_User_Model> final_online_user_list = new ArrayList<Online_User_Model>();
    TabLayout tabLayout;
    LinearLayout toolbar_layout;
    NavigationView nav_activeUser;
    ImageView iv_navRight;
    RecyclerView rv_activeUsers;
    TextView txt_noOnlineFriend;
    RecyclerView.LayoutManager layoutManager;
    ShowActiveUserRecyclerAdapter activeUserRecyclerAdapter;
    ArrayList<ActiveUserModel> activeUserModels;
    AppController appController;
    Boolean setAdapter = false;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    ListView lv_nav;
    ArrayList<String> text;
    ArrayList<Integer> logo;
    ArrayList<Boolean> select;
    NavListAdapter listAdapter;
    ImageView iv_cover;
    ImageView iv_profile;
    boolean doubleBackToExitPressedOnce = false;
    //for chat
    Socket_URL socket_url;
    int bundleNotificationId = 100;


    //receive new user response
    Emitter.Listener onlineUsers = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Chat_Main_Activity_online_user_called" + User_Name + " " + args[0].toString());

                    int length = args.length;

                    if (length == 0) {
                        return;
                    }
                    //Here i'm getting weird error..................///////run :1 and run: 0
                    String online_user = args[0].toString();

                    fetch_my_Online_Friends(args[0].toString(), user_id);

                }
            });
        }
    };
    private FragmentManager fragmentManager;
    private MyReceiver myReceiver;
    private NotificationManager notifManager;
    private String socket_user_name = "";
    //receive new user response
    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        System.out.println("MainActivity_onNewUser_No_Data");
                        return;
                    }

                    String user = args[0].toString();

                    System.out.println("MainActivity_Home" + User_Name + user);
                    System.out.println("MainActivity_Home_length_" + length);


                    try {

                        JSONObject object = new JSONObject(user);
                        newUserData = object;

                        //   self_user_id = object.getString("main_id");
                        //self_name = object.getString("name");
                        user_socket_id = object.getString("id");
                        user_socket_picture = object.getString("picture");
                        socket_user_name = object.getString("name");
                        System.out.println("user_socket_picture" + user_socket_picture);
                        //System.out.println("kareena_self_socket_id"+self_socket_id);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };
    //receive new user disconnect response
    Emitter.Listener userIsDisconnected = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        return;
                    }

                    String disconnect_status = args[0].toString();
                    System.out.println("disconnect_user" + User_Name + " " + disconnect_status);
                    System.out.println("socket_check_" + mSocket.connected());
                    //System.out.println("disconnect_user_2" + args);

                    // transport error
                    if (disconnect_status.equalsIgnoreCase("transport error")) {
                        mSocket.on("newUser", onNewUser);//receive new user response
                        System.out.println("new_user_called_after_transport_error");
                    }


                    //Here i'm getting weird error..................///////run :1 and run: 0


                }
            });
        }
    };

    {
        socket_url = new Socket_URL();
        mSocket = socket_url.getmSocket();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);





        //redirect to playstore
       /* final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.iicosom.social")));
        } catch (android.content.ActivityNotFoundException anfe) {

           //exceptino will occur if the playstore app is not installed in the mobile then is will open playstore url in browswer
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.iicosom.social" )));
        }*/

        fragmentManager = getSupportFragmentManager();

        appController = (AppController) getApplicationContext();
        // toolbar = findViewById(R.id.toolbar);
        // toolbar.setVisibility(View.GONE);
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        user_id = sp.getString("userId", "");
        User_Name = (sp.getString("firstName", "") + " " + sp.getString("lastName", ""));

        on_And_Configure_Socket();


        Log.e("Main-UserId", "run: " + "empty" + user_id);

        if (!user_id.isEmpty()) {
            notifys(user_id);
            userInfo(user_id);
        } else {
            Log.e("jaa", "run: " + "empty");
        }
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar_layout = findViewById(R.id.toolbar_layout);
        //  toolbar_layout.setVisibility(View.GONE);
        lv_nav = findViewById(R.id.lv_nav);

        activeUserModels = new ArrayList<>();
        text = new ArrayList<>();
        logo = new ArrayList<>();
        select = new ArrayList<>();

        nav_activeUser = (NavigationView) findViewById(R.id.nav_activeUser);
        iv_navRight = (ImageView) findViewById(R.id.iv_navRight);
        rv_activeUsers = (RecyclerView) findViewById(R.id.rv_activeUsers);
        txt_noOnlineFriend = (TextView) findViewById(R.id.txt_noOnlineFriend);
        layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, new HomeFragment()).commit();

        //  text.add("Home");
        ////text.add("Friend Request");
        // text.add("Notification");
        // text.add("iCosom Messenger");
        // text.add("iWallet (New)");
        //text.add("iTalent (New)");
        //  text.add("Kyc");
        //text.add("Sign Out");
        // text.add("Icosom talent");
        // text.add("Terms Of Use");
        // text.add("Privacy And Policy");
        //text.add("Refund Policy");
        //text.add("Setting Privacy");
        //text.add("Report An Issue");


        text.add("My Account");
        text.add("IM Pay");
        text.add("IM Talent");
        text.add("Sign Out");
        text.add("Feedback");
        text.add("Terms & Condition");
        text.add("Privacy Policy");
        text.add("Refund Policy");

        // text.add("IM version- 1.30");


        logo.add(R.drawable.ic_home_nav);
        logo.add(R.drawable.ic_pay);
        logo.add(R.drawable.ic_tallent);
        logo.add(R.drawable.ic_sign_out);
        logo.add(0);
        logo.add(0);
        logo.add(0);
        logo.add(0);


        select.add(true);
        select.add(false);
        select.add(false);
        select.add(false);
        select.add(false);
        select.add(false);
        select.add(false);
        select.add(false);


        // select.add(false);

        listAdapter = new NavListAdapter(getBaseContext(), text, logo, select);

        View listHeader = LayoutInflater.from(getBaseContext()).inflate(R.layout.nav_list_header, null);
        lv_nav.addHeaderView(listHeader);

        lv_nav.setAdapter(listAdapter);
        tabLayout = findViewById(R.id.tabLayout);
        // setupTabIcons();


        iv_cover = findViewById(R.id.iv_cover);
        iv_profile = findViewById(R.id.iv_profile);
        TextView txt_name = findViewById(R.id.txt_name);
        TextView txt_mail = findViewById(R.id.txt_mail);

        Glide.
                with(getBaseContext()).
                load(CommonFunctions.FETCH_IMAGES + sp.getString("covers", "")).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                thumbnail(0.01f).
                into(iv_cover);

        Glide.
                with(getBaseContext()).
                load(CommonFunctions.FETCH_IMAGES + sp.getString("profiles", "")).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                thumbnail(0.01f).
                into(iv_profile);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProfileActivity.class).
                        putExtra("userId", sp.getString("userId", "")));
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        txt_name.setText(sp.getString("firstName", "")
                + " " + sp.getString("lastName", ""));


        Log.e("user_kaif_name1", "run: " + User_Name);

        txt_mail.setText(sp.getString("email", ""));

        lv_nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* if (i >= 1 && i <= 3) {
                    int index = 0;
                    for (Boolean b : select) {
                        select.set(index, false);
                        index++;
                    }
                    select.set(i - 1, true);
                    listAdapter.notifyDataSetChanged();*/

                //tabLayout.getTabAt(i - 1).select();

                System.out.println("item_kaif_" + i);

                if (i == 1)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

                else if (i == 2)
                    startActivity(new Intent(getBaseContext(), WalletPin.class));
                else if (i == 3)
                    startActivity(new Intent(getBaseContext(), Talent_Plan.class));
                else if (i == 4)
                    Delete_Token(user_id);

                else if (i == 5) {
                } else if (i == 6)
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/social/main/termsOfUse.php").
                            putExtra("title1", "Terms").
                            putExtra("title2", " Of Use"));

                else if (i == 7)
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/privacy.html").
                            putExtra("title1", "Privacy ").
                            putExtra("title2", " and Policy"));

                else if (i == 8)
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/refund.html").
                            putExtra("title1", "Refund ").
                            putExtra("title2", " Policy"));


                   /* if(i==3) {
                        Log.e("noto", "");

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commit();
                    }*/


                drawer.closeDrawer(GravityCompat.START);
            }

               /* else if (i == 3)
                {
                    Log.e("noto","");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commit();
                    drawer.closeDrawer(GravityCompat.START);
                    // go for iPay wallet
                }*/

             /*   else if (i == 4) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.greenusys.icosom.messenger");
                    if (intent == null) {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.greenusys.icosom.messenger&hl=en_IN"));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (i == 5) {
                    startActivity(new Intent(getBaseContext(), WalletPin.class));
                    drawer.closeDrawer(GravityCompat.START);
                    // go for iPay wallet
                } else if (i == 6) {
                    startActivity(new Intent(getBaseContext(), Talent.class));
                    drawer.closeDrawer(GravityCompat.START);
                    // go for iTallent
                } else if (i == 111) {
                    *//*Intent intent = getPackageManager().getLaunchIntentForPackage("com.icosom.store");
                    if (intent == null) {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.icosom.store"));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);*//*
                } else if (i == 5555) {
                    startActivity(new Intent(MainActivity.this, Kyc_alert.class));
                    drawer.closeDrawer(GravityCompat.START);
                }else if (i == 7)
                {
                   Delete_Token(user_id);


                }*/
               /* else if (i == 8)
                {
                    startActivity(new Intent(getBaseContext(), Talent.class));
                    drawer.closeDrawer(GravityCompat.START);
                }*/
               /* else if (i == 8) {
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/social/main/termsOfUse.php").
                            putExtra("title1", "Terms").
                            putExtra("title2", " Of Use"));
                    drawer.closeDrawer(GravityCompat.START);
                } else if (i == 9) {
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/privacy.html").
                            putExtra("title1", "Privacy ").
                            putExtra("title2", " and Policy"));
                    drawer.closeDrawer(GravityCompat.START);
                } else if (i == 10) {
                    startActivity(new Intent(getBaseContext(), WebActivity.class).
                            putExtra("url", "https://icosom.com/refund.html").
                            putExtra("title1", "Refund ").
                            putExtra("title2", " Policy"));
                    drawer.closeDrawer(GravityCompat.START);
                } else if (i == 11) {
                    startActivity(new Intent(getBaseContext(), PrivacySetting.class));
                    drawer.closeDrawer(GravityCompat.START);
                } else if (i == 12) {
                    startActivity(new Intent(getBaseContext(), Report.class));
                    drawer.closeDrawer(GravityCompat.START);
                }*/
            //  }
        });

        getAllActiveUsers();

        toolbar_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SearchActivity.class));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    System.out.println("Tab_is_on_Home");
                    toolbar.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                }
                if (tab.getPosition() == 1) {
                    System.out.println("Tab_is_on_ShowFriendRequestFragment");
                    toolbar.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShowFriendRequestFragment()).commit();
                }
                if (tab.getPosition() == 2) {
                    System.out.println("Tab_is_on_ShowFriendRequestFragment");
                    toolbar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(MainActivity.this, Videostore.class));
                }
                if (tab.getPosition() == 3) {
                    System.out.println("Tab_is_on_NotificationFragment");
                    toolbar.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commit();
                }
                if (tab.getPosition() == 4) {
                    System.out.println("Tab_is_on_WalletFragment");
                    // tabLayout.setVisibility(View.GONE );
                    toolbar.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new WalletFragment()).commit();
                }
                if (tab.getPosition() == 5) {
                    System.out.println("Tab_is_on_WalletFragment");
                    // tabLayout.setVisibility(View.GONE );
                    toolbar.setVisibility(View.GONE);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, new Show_Friend_List_Chat()).commit();
                    startActivity(new Intent(getApplicationContext(), Chat_Main_Activity.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

      /* tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container, new HomeFragment()).commit();

                    int index = 0;
                    for (Boolean b : select) {
                        select.set(index, false);
                        index++;
                    }
                    select.set(0, true);
                    listAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container, new ShowFriendRequestFragment()).commit();

                    int index = 0;
                    for (Boolean b : select) {
                        select.set(index, false);
                        index++;
                    }
                    select.set(1, true);
                    listAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 2) {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container, new NotificationFragment()).commit();

                    int index = 0;
                    for (Boolean b : select) {
                        select.set(index, false);
                        index++;
                    }
                    select.set(2, true);
                    listAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 3) {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container, new WalletFragment()).commit();

                    int index = 0;
                    for (Boolean b : select) {
                        select.set(index, false);
                        index++;
                    }
                    select.set(3, true);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/
        iv_navRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.END))
                    drawer.closeDrawer(GravityCompat.END);
                else
                    drawer.openDrawer(GravityCompat.END);
            }
        });
    }

    private void on_And_Configure_Socket() {

        //  String picture=CommonFunctions.FETCH_IMAGES + sp.getString("profile","");
        String picture = CommonFunctions.FETCH_IMAGES + sp.getString("profile", "");


        mSocket.connect();
        mSocket.on("newUser", onNewUser);//receive new user response
        mSocket.on("onlineUsers", onlineUsers);//receive new user response
        mSocket.on("disconnect", userIsDisconnected);//receive new message response

        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("message", "kaif_service");
        startService(intent);

        JSONObject newUserObj = new JSONObject();
        try {
            newUserObj.put("person", User_Name);
            newUserObj.put("ids", user_id);//sender id
            newUserObj.put("picture", picture);//sender id
            mSocket.emit("newUser", newUserObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else
            super.onBackPressed();
        //finishAffinity()*/;

    public void fetch_my_Online_Friends(String onlineUsers, final String userID) {

        RequestBody body = new FormBody.Builder().
                add("ids", userID).
                add("onlineUsers", onlineUsers).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/androidcheck.php").
                post(body).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("Chat_Main_Activity_myResponse" + User_Name + myResponse);
                try {

                    if (online_user_list != null)
                        online_user_list.clear();

                    JSONObject mainjson = new JSONObject(myResponse);

                    if (mainjson.getString("status").equals("1")) {
                        JSONArray main = mainjson.getJSONArray("main");
                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);


                            Online_User_Model model = new Online_User_Model(item.getString("id"), item.getString("main_id"), item.getString("name"));
                            online_user_list.add(model);

                        }
                    }


                    if (MainActivity.this != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (final_online_user_list != null)
                                    System.out.println("chat_my_frnd_online_list" + User_Name + " " + online_user_list);
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getAllActiveUsers() {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.GET_ONLINE_USERS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        txt_noOnlineFriend.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {
                        JSONArray ja = jo.getJSONArray("message");
                        for (int i = 0; i < ja.length(); i++) {
                            activeUserModels.add(new ActiveUserModel(
                                    ja.getJSONObject(i).getString("friend_id"),
                                    ja.getJSONObject(i).getString("firstName"),
                                    ja.getJSONObject(i).getString("lastName"),
                                    ja.getJSONObject(i).getString("profilePicture"),
                                    ja.getJSONObject(i).getString("active")
                            ));
                        }
                        if (setAdapter) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (activeUserModels.size() == 0)
                                        txt_noOnlineFriend.setVisibility(View.VISIBLE);
                                    else
                                        txt_noOnlineFriend.setVisibility(View.GONE);

                                    activeUserRecyclerAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAdapter = true;
                                    activeUserRecyclerAdapter = new ShowActiveUserRecyclerAdapter(getBaseContext(), activeUserModels);
                                    rv_activeUsers.setLayoutManager(layoutManager);
                                    rv_activeUsers.setAdapter(activeUserRecyclerAdapter);

                                    if (activeUserModels.size() == 0)
                                        txt_noOnlineFriend.setVisibility(View.VISIBLE);
                                    else
                                        txt_noOnlineFriend.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userInfo(String idss) {

        RequestBody body = new FormBody.Builder().
                add("userId", idss).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/wallet/main/dmrAndroidProcess.php?action=userInfo").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
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
                    MainActivity.this.runOnUiThread(new Runnable() {
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
                                edt.putString("profile", jaa.getString("profilePicture"));
                                edt.putString("covers", jaa.getString("coverPhoto"));
                                edt.putString("birthDate", jaa.getString("birthDate"));
                                edt.putString("country", jaa.getString("country"));
                                edt.putString("state", jaa.getString("state"));
                                edt.putString("city", jaa.getString("city"));
                                edt.putString("gender", jaa.getString("gender"));
                                edt.putString("address1", jaa.getString("address1"));
                                edt.putString("website", jaa.getString("website"));
                                edt.putString("place", jaa.getString("place"));
                                edt.commit();
                                Log.e("jaa", "run: " + edt);
                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + jaa.getString("coverPhoto")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_cover);

                                Glide.
                                        with(getBaseContext()).
                                        load(CommonFunctions.FETCH_IMAGES + jaa.getString("profilePicture")).
                                        apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                                        thumbnail(0.01f).
                                        into(iv_profile);

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

    @SuppressLint("ResourceType")
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_footer_news, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(" " + sp.getString("request", "0"));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend_request_footer_news, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video_footer_news, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_footer_news, 0, 0, 0);
        tabFour.setText(" " + sp.getString("notification", "0"));
        tabLayout.getTabAt(3).setCustomView(tabFour);
        TextView tabfive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet_footer_news, 0, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabfive);

    }

    private void notifys(String user) {

        RequestBody body = new FormBody.Builder().
                add("user_id", user).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.notify).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
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
                    if (ja.has("code")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Log.e("loginss", "run: " + ja);
                                    edt.putString("request", ja.getString("request"));
                                    edt.putString("notification", ja.getString("notification"));

                                    System.out.println("Total_Notification_Count" + ja.getString("notification"));

                                    edt.putString("kyc", ja.getString("kyc"));
                                    edt.commit();
                                    setupTabIcons();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //delete token after logout..
    private void Delete_Token(String user_id) {
        Log.e("Delete_Token_User_id", "" + user_id);

        RequestBody body = new FormBody.Builder().
                add("userId", user_id).
                add("data-source", "android").
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/social/main/logoutProcess.php?action=logout").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
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

                    Log.e("Delete_Token_User_id", "" + ja);

                    if (ja.getString("status").equals("1")) {
                        edt.clear();
                        edt.putBoolean("FirstTimeHowTo", true);
                        edt.apply();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));


                        if (MainActivity.this != null) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateToken() {


        System.out.println("Token_Updated_method");


        String email = sp.getString("email", "");
        String password = sp.getString("password", "");
        String firebase_token = sp.getString("device_token", "");

        System.out.println("Champ_token" + firebase_token);

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("email", email).
                add("password", password).
                add("device_token", firebase_token).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LOGIN).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.length() != 0) {

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("Token_Updated_success");
                                //Toast.makeText(getBaseContext(), ""+ja.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart_is_called");

        updateToken();

        if (ChatActivity.backfrom == 1) {
            ChatActivity.backfrom = 0;
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new Show_Friend_List_Chat()).commitAllowingStateLoss();

        } else {

            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        updateToken();
    }

    @Override
    public void onBackPressed() {

        int i = 0;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() != 0) {
            String tag = fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount() - 1).getTag();
            //setToolbarTitle(tag);
            //when search is click and goes back if home
            assert tag != null;

            super.onBackPressed();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                //return;
                finishAffinity();
                i = 1;

            }


            this.doubleBackToExitPressedOnce = true;

            if (i != 1)
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }

    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    public void createNotification(String message, String socket_user_name, String user_socket_picture, Context context) {

        System.out.println("create_notification_called");
        System.out.println("socket_user_name" + socket_user_name);
        System.out.println("user_socket_picture" + user_socket_picture);
        System.out.println("message" + message);


        //String iconUrl = "https://icosom.com/social/postFiles/images/201907031250250.jpg";
        String iconUrl = user_socket_picture;
        Bitmap iconBitMap = null;
        if (iconUrl != null) {
            iconBitMap = getBitmapFromURL(iconUrl);
        }


        // final int NOTIFY_ID = 0; // ID of notification
        String id = "0"; // default_channel_id
        String title = "helllo"; // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            System.out.println("oreo>=");

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            String bundle_notification_id = "bundle_notification_" + bundleNotificationId;
            bundleNotificationId += 100;
            // required
            builder  // required
                    .setGroup(bundle_notification_id)
                    .setGroupSummary(true)
                    .setLargeIcon(iconBitMap)
                    .setSmallIcon(R.drawable.logointe)
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(socket_user_name)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        } else {
            System.out.println("oreo<=");
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(socket_user_name)
                    .setLargeIcon(iconBitMap)
                    .setSmallIcon(R.drawable.logointe)
                    .setContentText(message) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }



       /* NotificationCompat.Builder summaryNotificationBuilder = new NotificationCompat.Builder(this, "bundle_channel_id")
                .setGroup(bundle_notification_id)
                .setGroupSummary(true)
                .setContentTitle(socket_user_name)
                .setContentText(message)
                .setLargeIcon(iconBitMap)
                .setSmallIcon(R.drawable.logointe);
        notifManager.notify(bundleNotificationId, summaryNotificationBuilder.build());*/

        Notification notification = builder.build();
        notifManager.notify(Integer.parseInt(id), notification);
    }

    private Bitmap getBitmapFromURL(String strURL) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            try {
                URL url = new URL(strURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("Destory_Called_Main");

        app_destroy = true;
        CommonFunctions.app_running = false;
        CommonFunctions.home_page_list = null;

        mSocket.disconnect();
        // unregisterReceiver(myReceiver);

    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String reciever_id = intent.getStringExtra("reciever_id");
            String message = intent.getStringExtra("message");

            System.out.println("MyReceiver_called");

            System.out.println("reciever_id" + reciever_id);
            System.out.println("friend_id" + com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.ChatActivity.self_user_id);


            // if chat activity is  open it will not show message notification otherwise it will show
            if (!reciever_id.equals(com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.ChatActivity.self_user_id))
                createNotification(message, socket_user_name, user_socket_picture, getApplicationContext());
            else
                System.out.println("notificaton_not_called");


        }
    }




}