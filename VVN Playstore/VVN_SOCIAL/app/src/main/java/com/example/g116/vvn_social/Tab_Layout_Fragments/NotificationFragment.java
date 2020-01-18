package com.example.g116.vvn_social.Tab_Layout_Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g116.vvn_social.Adapter.NotificationRecyclerAdapter;
import com.example.g116.vvn_social.Modal.NotificationModel;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;
import com.example.g116.vvn_social.User_Profile_Dashboard_Activity.SinglePostActivity;

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


public class NotificationFragment extends Fragment {


    RecyclerView notification_recycler;
    RecyclerView.LayoutManager mLayoutManager;
    AppController appController;
    ArrayList<NotificationModel> notificationModels;
    NotificationRecyclerAdapter adapter;
    SharedPreferences sp;
    SwipeRefreshLayout srl_noti;
    TextView text_notification;
    NotificationModel notificationModel;
    int check = 0;
    private SessionManager session;
    String user_id="",user_type="";
    private ContentLoadingProgressBar loadingProgressBar;
    public static int backfrom;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        backfrom=1;

        getActivity().setTitle("Notification");

        appController = (AppController) getActivity().getApplicationContext();

        loadingProgressBar = (ContentLoadingProgressBar)v.findViewById(R.id.progress_bar);
        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        user_id = user.get(SessionManager.KEY_ID);
        user_type = user.get(SessionManager.KEY_USER_TYPE);

        Log.e("Notification_Class","SD");
        Log.e("user_id","SD"+user_id);
        Log.e("user_type","SD"+user_type);



        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        text_notification = v.findViewById(R.id.text_notification);
        notification_recycler = v.findViewById(R.id.notification_recycler);

       // srl_noti = v.findViewById(R.id.srl_noti);
        notificationModels = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notification_recycler.setLayoutManager(mLayoutManager);
        adapter = new NotificationRecyclerAdapter(getContext(), notificationModels, NotificationFragment.this);
        notification_recycler.setAdapter(adapter);



        fetch_Notification(user_id,user_type);








        return v;
    }




    public void fetch_Notification(String user_id, String type) {

        loadingProgressBar.show();
        RequestBody body = new FormBody.Builder().
                add("user_id", user_id).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/fetch_notification.php").
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
                            loadingProgressBar.hide();
                            Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject mainjson = new JSONObject(myResponse);
                    Log.e("mainjson",""+mainjson);
                    if (mainjson.getString("status").equals("1"))
                    {



                        JSONArray jsonArray = mainjson.getJSONArray("data");
                        String server_time=mainjson.getString("servertime");

                        String path="https://vvn.city/";
                        String pic="";
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);



                            String notification_id=jsonObject.getString("notify_id");
                            String userByID=jsonObject.getString("userByid");
                            String firstName=jsonObject.getString("firstName");
                            String lastName=jsonObject.getString("lastName");
                            String postId=jsonObject.getString("postId");
                            String userToId=jsonObject.getString("userToId");
                            String action=jsonObject.getString("action");
                            String status=jsonObject.getString("status");
                            String educationDetails=jsonObject.getString("educationDetails");
                            String picture=jsonObject.getString("picture");
                            String notifyDate=jsonObject.getString("notifyDate");

                            picture=picture.replace("../","");

                            Log.e("notification_pic",""+path+picture);

                            String final_time= calculate_time(notifyDate,server_time);


                            NotificationModel not=new NotificationModel();
                            not.setNotification_id(notification_id);
                            not.setUserByID(userByID);
                            not.setFirstName(firstName);
                            not.setLastName(lastName);
                            not.setPostId(postId);
                            not.setUserToId(userToId);
                            not.setAction(action);
                            not.setTime(final_time);
                            not.setReadStatus(status);
                            not.setProfilePicture(path+picture);
                            not.setEducation_detail(educationDetails);
                            notificationModels.add(not);

                        }

                        if(getActivity()!=null)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                 

                                    loadingProgressBar.hide();
                                    text_notification.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }



                        Log.e("notification_model_size",""+notificationModels.size());

                    }
                    else
                    {
                        if(getActivity()!=null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingProgressBar.hide();

                                    text_notification.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                    }



                } catch (JSONException e)
                {
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_notification.setVisibility(View.VISIBLE);
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




    public void updateNotification(String notificationId)
    {
        System.out.println("updateNotification_api_called" + notificationId );

        RequestBody body = new FormBody.Builder().
                add("notify_id", notificationId).
                build();

        Request request = new Request.Builder().
                url("https://vvn.city/api/readnotification.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                System.out.println("Errorrrrr "+e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                System.out.println("notification_read_status "+response.body().string());

            }
        });
    }

    public void goToSinglePost(int position)
    {

        System.out.println("goToSinglePost_user_pic "+notificationModels.get(position).getProfilePicture());

        updateNotification(notificationModels.get(position).getNotification_id());
        notificationModels.get(position).setReadStatus("1");
        adapter.notifyDataSetChanged();
        startActivity(new Intent(getContext(), SinglePostActivity.class).
                putExtra("post_id", notificationModels.get(position).getPostId()).
                putExtra("time", notificationModels.get(position).getTime()).
                putExtra("education", notificationModels.get(position).getEducation_detail()).
                putExtra("user_name", notificationModels.get(position).getFirstName()+" "+notificationModels.get(position).getLastName()).
                putExtra("user_pic", notificationModels.get(position).getProfilePicture())
        );
    }



    @Override
    public void onDetach() {
        super.onDetach();
        backfrom=1;
    }





}