package com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;


import com.github.nkzawa.socketio.client.Socket;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.Icosom_Chat_Package.activity.fragments.Active_User_Fragment;
import com.icosom.social.Icosom_Chat_Package.activity.fragments.Friend_List_Fragment;
import com.icosom.social.Icosom_Chat_Package.activity.fragments.Recent_Chat_List_Fragment;
import com.icosom.social.R;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;



public class Chat_Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar toolbar;

    private ViewPager viewPager;
    private int[] tabIcons = {R.drawable.chat_icon, R.drawable.chat_active_user_icon, R.drawable.chat_friend_icon,};
    public static String username, userID, user_socket_id;//used in ChatActivity
   // public static JSONObject newUserData;//used in chat activity for Notifytyping
   // public static ArrayList<Online_User_Model> online_user_list = new ArrayList<>();

    //final ArrayList<Online_User_Model> final_online_user_list = new ArrayList<Online_User_Model>();


    private AppController appController;
    Socket_URL socket_url;

    private Socket mSocket;

    {
        socket_url = new Socket_URL();
        mSocket = socket_url.getmSocket();

    }

    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_activity_main);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        tabLayout = findViewById(R.id.tabLayout);
        loadFragment(new Recent_Chat_List_Fragment());
        tabLayoutClickListener();

       // toolbar = getSupportActionBar();
        appController = (AppController) getApplicationContext();


        //username = getIntent().getStringExtra("username");
        //userID = getIntent().getStringExtra("userID");

        username = MainActivity.User_Name;
        userID = MainActivity.user_id;


        on_And_Configure_Socket();




    }

    private void tabLayoutClickListener() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                    Fragment fragment = new Recent_Chat_List_Fragment();
                    loadFragment(fragment);

                }
                if (tab.getPosition() == 1) {

                    Fragment fragment = new Active_User_Fragment();
                    loadFragment(fragment);
                }

                if (tab.getPosition() == 2) {
                    Fragment fragment = new Friend_List_Fragment();
                    loadFragment(fragment);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void on_And_Configure_Socket() {
        //mSocket.connect();
       // mSocket.on("newUser", onNewUser);//receive new user response
       // mSocket.on("onlineUsers", onlineUsers);//receive new user response
        //mSocket.on("disconnect", userIsDisconnected);//receive new message response

        /*JSONObject newUserObj = new JSONObject();
        try {
            newUserObj.put("person", username);
            newUserObj.put("ids", userID);//sender id
            mSocket.emit("newUser", newUserObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }


   /* //receive new user response
    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        return;
                    }

                    String username2 = args[0].toString();

                    System.out.println("Chat_Main_Activity_" + username + username2);


                    try {

                        JSONObject object = new JSONObject(username2);
                        newUserData = object;

                        //   self_user_id = object.getString("main_id");
                        //self_name = object.getString("name");
                        user_socket_id = object.getString("id");

                        //System.out.println("kareena_self_user_id"+self_user_id);
                        //System.out.println("kareena_self_socket_id"+self_socket_id);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };*/


   /* //receive new user response
    Emitter.Listener onlineUsers = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Chat_Main_Activity_online_user_called" + username + " " + args[0].toString());

                    int length = args.length;

                    if (length == 0) {
                        return;
                    }
                    //Here i'm getting weird error..................///////run :1 and run: 0
                    String online_user = args[0].toString();

                   // fetch_my_Online_Friends(args[0].toString(), userID);

                }
            });
        }
    };
*/
   /* //receive new user disconnect response
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
                    System.out.println("disconnect_user" + username + " " + disconnect_status);
                    System.out.println("socket_check_"+mSocket.connected());
                    //System.out.println("disconnect_user_2" + args);

                   // transport error
*//*
                    if(disconnect_status.equalsIgnoreCase("transport error")) {
                        mSocket.on("newUser", onNewUser);//receive new user response
                        System.out.println("new_user_called_after_transport_error");
                    }*//*



                    //Here i'm getting weird error..................///////run :1 and run: 0



                }
            });
        }
    };
*/

  /*  public void fetch_my_Online_Friends(String onlineUsers, final String userID) {

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
                System.out.println("Chat_Main_Activity_myResponse" + username + myResponse);
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


                    if (Chat_Main_Activity.this != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (final_online_user_list != null)
                                    System.out.println("chat_my_frnd_online_list" + username + " " + online_user_list);
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


   /* @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("Destory_Called_ChatMain");

        mSocket.disconnect();

    }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    @Override
    public void onBackPressed() {

        System.out.println("tab_positon"+tabLayout.getSelectedTabPosition());
        if (tabLayout.getSelectedTabPosition() >0) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            Fragment fragment = new Recent_Chat_List_Fragment();
            loadFragment(fragment);
        }
        else {
            finish();
        }
    }




}