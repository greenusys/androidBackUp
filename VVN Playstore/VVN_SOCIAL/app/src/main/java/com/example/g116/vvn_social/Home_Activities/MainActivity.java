package com.example.g116.vvn_social.Home_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.g116.vvn_social.Adapter.Home_Post_Adapter;
import com.example.g116.vvn_social.Modal.Home_Post_Model;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.SinglePostActivity;
import com.example.g116.vvn_social.Tab_Layout_Fragments.HomeFragment;
import com.example.g116.vvn_social.Tab_Layout_Fragments.NotificationFragment;
import com.example.g116.vvn_social.Tab_Layout_Fragments.ShowFriendRequestFragment;
import com.example.g116.vvn_social.Tab_Layout_Fragments.WalletFragment;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Update_Password;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.Friend_Profile_Dashboard;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.User_Profile_Dashboard;
import com.greenusys.allen.vidyadashboard.MainActivity_Dash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    RecyclerView recyclerView;

    Home_Post_Adapter homePostAdapter;
    ImageView nav_profile_image;
    LinearLayout toolbar_layout;
    TextView user_email,user_full_name;
    TabLayout tabLayout;
    CardView card_head;
    private SessionManager session;
    AppController appController;
    private FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;
    String user_picture ="";
    HashMap<String, String> user;
    String user_type="";
    String password="";
    String email="";
    NavigationView navigationView;
    private static int stop=0;
    Menu myMenu;

    public static ArrayList<Home_Post_Model> modelFeedArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = (AppController) getApplicationContext();
        session = new SessionManager(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
       // session.checkLogin();//check user is logged in or not if not it will navigate to Login Page

        // get user data from session
         user = session.getUserDetails();



        String full_name = user.get(SessionManager.KEY_FULL_NAME);
         email = user.get(SessionManager.KEY_EMAIL);
         password = user.get(SessionManager.KEY_PASSWORD);
        String user_id = user.get(SessionManager.KEY_ID);
         user_type = user.get(SessionManager.KEY_USER_TYPE);
         user_picture = user.get(SessionManager.KEY_PICTURE);


        /*Log.e("full_name",""+full_name);
        Log.e("email",""+email);
        Log.e("user_id",""+user_id);
        Log.e("user_type",""+user_type);
        Log.e("user_picture",""+user_picture);*/

        update_self_entry(user_id,user_type);


        tabLayout = findViewById(R.id.tabLayout);
       // card_head = findViewById(R.id.card_head);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SearchActivity.class));
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerview = navigationView.getHeaderView(0);
        nav_profile_image = (ImageView) headerview.findViewById(R.id.profile_image_navigation);
        user_full_name = (TextView) headerview.findViewById(R.id.navigation_user_name);
        user_email = (TextView) headerview.findViewById(R.id.navigation_user_email);

        user_full_name.setText(full_name);
        user_email.setText(email);

        nav_profile_image.setOnClickListener(this);
        user_full_name.setOnClickListener(this);
        user_email.setOnClickListener(this);


       // hideItem();//it  hides the VVN Dashboard icon from Navigation Menu





                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                    toolbar_layout.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                }
                if (tab.getPosition() == 1) {
                    toolbar_layout.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShowFriendRequestFragment()).commit();
                }

                if (tab.getPosition() == 2) {
                    toolbar_layout.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commit();
                }
                if (tab.getPosition() == 3) {

                    toolbar_layout.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new WalletFragment()).commit();
                }
                if (tab.getPosition() == 4) {

                    toolbar_layout.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Show_Friend_List_Chat()).commit();
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

  /*  private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        if(user_type.equalsIgnoreCase("student"))
        nav_Menu.findItem(R.id.nav_erp_dashboard).setVisible(true);
        if(user_type.equalsIgnoreCase("teacher"))
        nav_Menu.findItem(R.id.nav_erp_dashboard).setVisible(false);


    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

       getMenuInflater().inflate(R.menu.navigation__drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();




        if (id == R.id.nav_home)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        }
        else if (id == R.id.nav_friend_request) {

            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShowFriendRequestFragment()).commit();



        }
        else if (id == R.id.nav_message) {

        }
        else if (id == R.id.nav_notification) {
            TabLayout.Tab tab = tabLayout.getTabAt(2);
            tab.select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commit();


        }else if (id == R.id.nav_wallet) {

        }
        else if (id == R.id.nav_sign_out)
        {
            session.logoutUser();

        }

        else if (id == R.id.update_password)
        {
            startActivity(new Intent(MainActivity.this, Update_Password.class));


        }
        else if (id == R.id.nav_erp_dashboard)
        {

            startActivity(new Intent(MainActivity.this, MainActivity_Dash.class));

        }





        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {


        if(view==nav_profile_image || view==user_email || view==user_full_name)
        {
            startActivity(new Intent(MainActivity.this,User_Profile_Dashboard.class));
            drawer.closeDrawer(GravityCompat.START);

        }


    }




    @Override
    public void onBackPressed() {



        if (tabLayout.getSelectedTabPosition() >0) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commitAllowingStateLoss();
        }
        else {

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


    }






    public void update_self_entry(String user_id, String type) {

      /* System.out.println("update_self_entry");


        System.out.println("user_id"+user_id);
        System.out.println("type"+type);
*/

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/update_selfpost.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }




    private void fetch_user_Detail(final String email, String password) {



        RequestBody body = new FormBody.Builder().
                add("type", user_type).
                add("email", email).
                add("password", password).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/login.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("login3","ksdf");

                    //    alert.showAlertDialog(LoginActivity.this, "VVN CITY.", "Something is Went Wrong Please Try Again Later.", false);
                        // Toast.makeText(getBaseContext(), "Something is Went Wrong Please Try Again Later. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject mainjson = new JSONObject(myResponse);
                    if (mainjson.has("msg")) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.e("login4","ksdf");
                                    String message=mainjson.getString("msg");
                                    String path = "https://vvn.city/";
                                    if(message.equalsIgnoreCase("Login Successfull")) {





                                        if (user_type.equals("student")) {




                                            JSONObject jsonObject = mainjson.getJSONObject("data");

                                            String full_name = jsonObject.getString("firstName") + " " +
                                                    jsonObject.getString("lastName");
                                            String educationDetails=jsonObject.getString("educationDetails")!=null ? jsonObject.getString("educationDetails"): " " ;
                                            String passYear=jsonObject.getString("passYear")!=null ? jsonObject.getString("passYear"): " " ;
                                            String maritalStatus = jsonObject.getString("maritalStatus")!=null ? jsonObject.getString("maritalStatus"): " " ;
                                            String birthDate = !jsonObject.getString("birthDate").equals("0000-00-00")?jsonObject.getString("birthDate"):" " ;
                                            String gender=jsonObject.getString("gender")!=null ? jsonObject.getString("gender"): " " ;;
                                            String address=jsonObject.getString("address")!=null ? jsonObject.getString("address"): " " ;;
                                            String password = jsonObject.getString("pass");
                                            String id = jsonObject.getString("sno");
                                            String mobile = !jsonObject.getString("phone").equals("0") ? jsonObject.getString("phone"):" ";;
                                            String email = jsonObject.getString("email");
                                            String picture = jsonObject.getString("picture");
                                            picture = picture.replace("../", "");
                                            String class_course = jsonObject.getString("class")!=null ? jsonObject.getString("class"):" ";;
                                            String state = jsonObject.getString("state");
                                            String city = jsonObject.getString("city");


                                        }
                                        if (user_type.equals("teacher"))
                                        {

                                            JSONObject jsonObject=mainjson.getJSONObject("data");



                                            String id=jsonObject.getString("sno");
                                            String full_name=jsonObject.getString("firstName")+" "+
                                                    jsonObject.getString("lastName");

                                            String educationDetails=jsonObject.getString("educationDetails")!=null ? jsonObject.getString("educationDetails"): " " ;
                                            String maritalStatus=jsonObject.getString("t_marital_status")!=null ? jsonObject.getString("t_marital_status"): " " ;;
                                            String birthDate=!jsonObject.getString("dob").equals("0000-00-00")?jsonObject.getString("dob"):" " ;
                                            String gender=jsonObject.getString("gender")!=null ? jsonObject.getString("gender"): " " ;;
                                            String address=jsonObject.getString("address")!=null ? jsonObject.getString("address"): " " ;;
                                            String password=jsonObject.getString("pwd");
                                            String mobile=!jsonObject.getString("phone").equals("0") ? jsonObject.getString("phone"):" ";
                                            String email=jsonObject.getString("email");
                                            String picture=jsonObject.getString("picture");


                                            String state=jsonObject.getString("state");
                                            String city=jsonObject.getString("city");

                                            Log.e("User_path+picture",""+path+picture);

                                            System.out.println("kaif_check");
                                            System.out.println(jsonObject.getString("t_marital_status")==null);



                                        }




                                    }
                                    else
                                    {




                                    }



                                } catch (JSONException e) {
                                    Log.e("login6","ksdf");


                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Log.e("login7","ksdf");


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();


                    Log.e("login8","ksdf");
                }
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("onRestart_is_called");

        System.out.println("tab_position " +  tabLayout.getSelectedTabPosition());
        System.out.println("tab_position " +  tabLayout.getSelectedTabPosition());


        //maintain tab layhout position
        if (tabLayout.getSelectedTabPosition() == 1) {


            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShowFriendRequestFragment()).commitAllowingStateLoss();
        }

        if (tabLayout.getSelectedTabPosition() == 2) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).commitAllowingStateLoss();
        }
        if (tabLayout.getSelectedTabPosition() == 3) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new WalletFragment()).commitAllowingStateLoss();
        }

        if(tabLayout.getSelectedTabPosition() == 4)
        {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new Show_Friend_List_Chat()).commitAllowingStateLoss();

        }





        //this logic is used for to prevent the call fetch_post api again and again
        if(ShowSubMediaActivity.backfrom==1)
        {
            ShowSubMediaActivity.backfrom=0;

        }
        else if(Update_Password.backfrom==1)
        {
            Update_Password.backfrom=0;

        }
        else if(CommentActivity.backfrom==1)
        {
            CommentActivity.backfrom=0;

        }

        else if(SearchActivity.backfrom==1)
        {
            SearchActivity.backfrom=0;

        }
        else if(User_Profile_Dashboard.backfrom==1)
        {
            User_Profile_Dashboard.backfrom=0;

        }
        else if(Friend_Profile_Dashboard.backfrom==1)
        {
            Friend_Profile_Dashboard.backfrom=0;

        }
        else if(SinglePostActivity.backfrom==1)
        {
            SinglePostActivity.backfrom=0;

        }
        else if(MainActivity.stop==1)
        {
        stop=0;

        }


        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commitAllowingStateLoss();

        }
    }







    @Override
    protected void onResume() {
        super.onResume();


        fetch_user_Detail(email,password);

        user_picture = user.get(SessionManager.KEY_PICTURE);

        Glide.with(getApplicationContext())
                .load(user_picture).
                apply(new RequestOptions().placeholder(R.drawable.placeholder)).
                into(nav_profile_image);;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop=1;
    }
}
