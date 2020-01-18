package com.example.g116.vvn_social.Tab_Layout_Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g116.vvn_social.Adapter.Home_Post_Adapter;
import com.example.g116.vvn_social.Home_Activities.AddFeedActivity;
import com.example.g116.vvn_social.Home_Activities.MainActivity;
import com.example.g116.vvn_social.Modal.Home_Post_Model;
import com.example.g116.vvn_social.Modal.Image_String;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONArray;
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

import static java.lang.Integer.parseInt;


public class HomeFragment extends Fragment {


    private SessionManager session;
    private ContentLoadingProgressBar loadingProgressBar;
    private  int position;

    RecyclerView recyclerView;
    public ArrayList<Home_Post_Model> modelFeedArrayList;
    ArrayList<Image_String> image_list = new ArrayList<>();
   public Home_Post_Adapter homePostAdapter;
    LinearLayout add_post_layout, no_data_found_layout;
    AppController appController;
    String user_type = "";
    String user_id = "";
    SwipeRefreshLayout swipe_refresh;
    TextView no_post_text;
    BottomSheetDialog sheetDialogMe;
    TextView txt_edtPostMe,txt_deletePostMe;

    public static int no_data;

    public static int backfrom;
    ArrayList<String> postFileLinks ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        appController = (AppController) getContext().getApplicationContext();
        loadingProgressBar = (ContentLoadingProgressBar) v.findViewById(R.id.progress_bar);
        add_post_layout = (LinearLayout) v.findViewById(R.id.add_post_layout);
        no_data_found_layout = (LinearLayout) v.findViewById(R.id.no_data_found_layout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        swipe_refresh = v.findViewById(R.id.srl_home);
        no_post_text = v.findViewById(R.id.no_post_text);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        modelFeedArrayList = new ArrayList<>();

        sheetDialogMe = new BottomSheetDialog(getActivity());
        View sheetViewMe = LayoutInflater.from(getActivity()).inflate(R.layout.more_options_sheet_layout_me, null);
        sheetDialogMe.setContentView(sheetViewMe);






        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        user_id = user.get(SessionManager.KEY_ID);
        user_type = user.get(SessionManager.KEY_USER_TYPE);

        Log.d("Life_cycle","oncreateView");

        Log.e("user_id", "SD" + user_id);
        Log.e("user_type", "SD" + user_type);


        //check internet is available or not
        if (!isNetworkAvailable(getContext())) {
            recyclerView.setVisibility(View.GONE);
            no_data=1;
            no_post_text.setText("Please Check You Internet");
            no_data_found_layout.setVisibility(View.VISIBLE);

        } else {

            if(ShowFriendRequestFragment.backfrom==1)
            {
                ShowFriendRequestFragment.backfrom=0;
                loadingProgressBar.hide();

                if(MainActivity.modelFeedArrayList!=null)
                {
                    System.out.println("katrina_ShowFriendRequestFragment.backfrom==1"+MainActivity.modelFeedArrayList.size());

                    homePostAdapter = new Home_Post_Adapter(homePostAdapter, sheetDialogMe, appController, user_id, user_type, getContext(), MainActivity.modelFeedArrayList, image_list, new HomeFragment());
                    recyclerView.setAdapter(homePostAdapter);
                    homePostAdapter.notifyDataSetChanged();
                    System.out.println("katrina_yes_1");
                }



            }
            else if(NotificationFragment.backfrom==1)
            {
                loadingProgressBar.hide();

                NotificationFragment.backfrom=0;
                if(MainActivity.modelFeedArrayList!=null) {
                    System.out.println("katrina_NotificationFragment.backfrom==1"+MainActivity.modelFeedArrayList.size());
                    homePostAdapter = new Home_Post_Adapter(homePostAdapter, sheetDialogMe, appController, user_id, user_type, getContext(), MainActivity.modelFeedArrayList, image_list, new HomeFragment());
                    recyclerView.setAdapter(homePostAdapter);
                    homePostAdapter.notifyDataSetChanged();
                    System.out.println("katrina_yes_2");
                }


            }
            else
            {

                 homePostAdapter = new Home_Post_Adapter(homePostAdapter,sheetDialogMe,appController, user_id, user_type, getContext(), modelFeedArrayList, image_list, new HomeFragment());
                 recyclerView.setAdapter(homePostAdapter);

                System.out.println("katrina_HomeFragment"+modelFeedArrayList.size());
                Fetch_Home_Page_Post(user_id, user_type);
            }

        }


        //swipe referesh listener
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkAvailable(getContext())) {
                    loadingProgressBar.hide();
                    swipe_refresh.setRefreshing(false);
                    //Toast.makeText(getContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
                }


                else {
                    no_data_found_layout.setVisibility(View.INVISIBLE);
                    homePostAdapter = new Home_Post_Adapter(homePostAdapter,sheetDialogMe,appController, user_id, user_type, getContext(), modelFeedArrayList, image_list, new HomeFragment());
                    recyclerView.setAdapter(homePostAdapter);
                    Fetch_Home_Page_Post(user_id, user_type);
                    swipe_refresh.setRefreshing(false);
                }


            }
        });





        //add post listener
        add_post_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddFeedActivity.class));
            }
        });


        return v;

    }



    public void deletePost(AppController appController, final ArrayList<Home_Post_Model> modelFeedArrayList, String user_id, String user_type, final int position) {

       // Log.e("user_id",""+user_id);
       // Log.e("user_type",""+user_type);
       // Log.e("position",""+ position);
       // Log.e("post_id",""+ modelFeedArrayList.get(position).getPost_id());


        RequestBody body = new FormBody.Builder().
                add("action", "delete").
             /*   add("user_id", user_id).*/
                add("type", user_type).
                add("desc", "").
                add("post_id", modelFeedArrayList.get(position).getPost_id()).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/casespost.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());


                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                        try {
                            JSONObject jo = new JSONObject(myResponse);
                            if (jo.getString("status").equals("0"))
                            {
                                //modelFeedArrayList.remove(position);


                                //Log.e("Delete_Method",""+jo.getString("msg"));

                            } else {
                              //  Log.e("Delete_Method",""+jo.getString("msg"));
                               // Toast.makeText(getContext(), "" + jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                // nothing to do

        });
    }




    public void Fetch_Home_Page_Post(String user_id, String type) {

        loadingProgressBar.show();
        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_post.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                no_data=1;


                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingProgressBar.hide();
                            no_post_text.setText("Please Check Your Internet");
                            no_data_found_layout.setVisibility(View.VISIBLE);}
                    });
                }


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    String user_type="";
                    JSONObject mainjson = new JSONObject(myResponse);
                    Log.e("mainjson", "" + mainjson);
                    if (mainjson.getString("status").equals("1")) {

                        JSONArray jsonArray = mainjson.getJSONArray("data");


                        String server_time=mainjson.getString("servertime");
                        String path = "https://vvn.city/social/";
                        String pic = "";
                        String views[] = null;
                        int count_views = 0;
                        String pic_res = "";
                        ArrayList<String>myLikeslist=new ArrayList<>();
                      //  Boolean isMyLike = false;
                        //Boolean isMyDislike = false;


                        if(modelFeedArrayList!=null)
                            modelFeedArrayList.clear();

                        if(myLikeslist!=null)
                            myLikeslist.clear();

                       no_data=0;

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String mylikes = "";







                                String post = "";

                                postFileLinks = new ArrayList<>();

                                if (!jsonObject.getString("fileLink").equalsIgnoreCase("no")) {
                                    post = jsonObject.getString("fileLink");//post image


                                    for (String str : post.split(",")) {
                                        String image = str.replace("../", "");


                                        postFileLinks.add(image);
                                    }
                                }


                                String Post_User_Name = jsonObject.getString("firstName") + " " +
                                        jsonObject.getString("lastName");
                                String id = jsonObject.getString("id");
                                String post_user_id = jsonObject.getString("userId");
                                String total_views = jsonObject.getString("1");//get total views
                                String mylikes2 = jsonObject.getString("0");//
                                String totalLikes = jsonObject.getString("totalLikes");
                                String totalDislikes = jsonObject.getString("totalDislikes");
                                String totalComments = jsonObject.getString("totalComments");
                                String totalShares = jsonObject.getString("totalShares");
                                String postTime = jsonObject.getString("postTime");
                                String post_status = jsonObject.getString("description");
                                String educationDetails = jsonObject.getString("educationDetails");

                                String user_profile_pic = jsonObject.getString("picture");//user profile image
                                String full_name = jsonObject.getString("firstName") + " " +
                                        jsonObject.getString("lastName");

                                String value = jsonObject.getString("user_type");
                                if (value.equals("1"))
                                    user_type = "teacher";
                                if (value.equals("2"))
                                    user_type = "student";

                            user_profile_pic = user_profile_pic.replace("../", "");



                                Boolean isMyLike = false;
                                Boolean isMyDislike = false;

                                // that is post has more than 0 likes
                                if (mylikes2.equals("1"))
                                    isMyLike = true;

                                // that is post has more than 0 dislikes
                                if (mylikes2.equals("0"))
                                    isMyDislike = true;




                                String final_time = calculate_time(postTime, server_time);


                                Home_Post_Model home = new Home_Post_Model();
                                home.setMylikelist(myLikeslist);
                                home.setPost_id(id);
                                home.setTotal_views(total_views);
                                home.setTotalLikes(totalLikes);
                                home.setTotalDislikes(totalDislikes);
                                home.setTotalComments(totalComments);
                                home.setTotalShares(totalShares);
                                //home.setPostTime(date_result+" "+time_result);
                                home.setPostTime(final_time);
                                home.setPost_pic(path + pic);
                                home.setPost_status(post_status);
                                home.setFull_name(full_name);
                                home.setUser_profile_pic(user_profile_pic);
                                home.setImage_list(postFileLinks);
                                home.setPost_user_id(post_user_id);
                                home.setView(false);
                                home.setEducation_detail(educationDetails);

                                home.setMyLike(isMyLike);
                                home.setMyDislike(isMyDislike);
                                home.setPost_User_Name(Post_User_Name);
                                home.setUser_type(user_type);


                                System.out.println("sayed_user_pic " + user_profile_pic);


                                modelFeedArrayList.add(home);


                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            no_data_found_layout.setVisibility(View.GONE);
                                            no_data = 0;
                                        }
                                    });
                                }


                            }


                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingProgressBar.hide();
                                        homePostAdapter.notifyDataSetChanged();
                                        MainActivity.modelFeedArrayList = modelFeedArrayList;
                                    }
                                });
                            }



                    }//loop close
                    else {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingProgressBar.hide();
                                    recyclerView.setVisibility(View.GONE);
                                    no_post_text.setText("No Posts Available");
                                    no_data=1;
                                    MainActivity.modelFeedArrayList=null;

                                    no_data_found_layout.setVisibility(View.VISIBLE);
                                   // Log.e("No data found", "ksdj" + "");
                                }
                            });
                        }


                    }


                } catch (JSONException e) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                no_post_text.setText("Please Try Again Later!");
                                no_data_found_layout.setVisibility(View.VISIBLE);
                                MainActivity.modelFeedArrayList=null;
                                no_data=1;
                                loadingProgressBar.hide();

                            }
                        });
                    }

                    e.printStackTrace();
                }


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




    public void put_like(AppController appController, String post_id, String type_like, String user_id, String user_type) {


        Log.e("user_id", "" + user_id);
        Log.e("post_id", "" + post_id);
        Log.e("user_type", "" + user_type);
        Log.e("type_like", "" + type_like);

        // MediaPlayer.create(getContext(), R.raw.beep3).start();

        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("post_id", post_id).
                add("user_type", user_type).
                add("type", type_like).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/put_like_dislike.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {

                        String message = jo.getString("msg");
                        Log.e("message_like", "" + message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }










    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public void view_post(AppController appController, String user_id, String post_id) {

        System.out.println("view_post_called");
        System.out.println("user_id"+user_id);
        System.out.println("post_id"+post_id);


        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("post_id", post_id).

                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/countview.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONArray jo = new JSONArray(myResponse);
                    JSONObject jsonObject=jo.getJSONObject(0);
                    String total_view=jsonObject.getString("count");




                    System.out.println("total_view"+total_view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });
    }




    @Override
    public void onResume() {
        super.onResume();
        Log.d("Life_cycle","onResume");


        if(HomeFragment.no_data==1)
        {
            no_data_found_layout.setVisibility(View.VISIBLE);
        }
        if(HomeFragment.no_data!=1)
        {
            no_data_found_layout.setVisibility(View.GONE);
        }


         if(AddFeedActivity.backfrom==1)
        {
            System.out.println("katrina_AddFeedActivity.backfrom==1"+user_id+user_type);
            AddFeedActivity.backfrom=0;
            homePostAdapter = new Home_Post_Adapter(homePostAdapter,sheetDialogMe,appController, user_id, user_type, getContext(), modelFeedArrayList, image_list, new HomeFragment());
            recyclerView.setAdapter(homePostAdapter);
            Fetch_Home_Page_Post(user_id, user_type);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        backfrom=1;
    }


}
