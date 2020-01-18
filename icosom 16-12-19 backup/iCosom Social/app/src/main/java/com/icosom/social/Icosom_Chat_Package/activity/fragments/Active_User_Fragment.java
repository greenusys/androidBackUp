package com.icosom.social.Icosom_Chat_Package.activity.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;


import com.airbnb.lottie.LottieAnimationView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.icosom.social.Icosom_Chat_Package.activity.Adapter.Online_User_Adapter;
import com.icosom.social.Icosom_Chat_Package.activity.Chat_Activity.Chat_Main_Activity;
import com.icosom.social.Icosom_Chat_Package.activity.Modal.Online_User_Model;
import com.icosom.social.Icosom_Chat_Package.activity.Network_Package.Socket_URL;
import com.icosom.social.R;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Active_User_Fragment extends Fragment {

    Socket_URL socket_url;
    public static JSONObject newUserData;//used in chat activity for Notifytyping
    private String user_socket_id;
    private AppController appController;
    ArrayList<Online_User_Model> online_user_list = new ArrayList<>();
    private Socket mSocket;
    {
        socket_url = new Socket_URL();
        mSocket = socket_url.getmSocket();

    }


    private RecyclerView recyclerView;
    private SearchView search;
    ;
    private LinearLayoutManager layoutManager;
    LinearLayout no_friend_layout, main_layout, loading_layout,search_layout;
    LottieAnimationView loading_anim;
    private Online_User_Adapter adapter;
    private ArrayList<Online_User_Model> online_list_models = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("second");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_active_user_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        appController = (AppController) getContext().getApplicationContext();
        recyclerView = view.findViewById(R.id.rv_search);//recycler view
        search = view.findViewById(R.id.search_view);
        search_layout = view.findViewById(R.id.search_layout);
        main_layout = view.findViewById(R.id.main_layout);
        no_friend_layout = view.findViewById(R.id.no_friend_layout);
        loading_layout = view.findViewById(R.id.loading_layout);
        loading_anim = view.findViewById(R.id.loading_anim);

        System.out.println("Active_User_Fragment_list" + Chat_Main_Activity.username + MainActivity.online_user_list);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Online_User_Adapter(getContext(), MainActivity.online_user_list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println("Active_kaif_active_list" + MainActivity.online_user_list);

        loading_layout.setVisibility(View.VISIBLE);

        on_And_Configure_Socket();
        search_text_listener();

    }

    private void search_text_listener() {
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();//open search box
            }
        });


        search.setQueryHint("Search Friends");

        search.setMaxWidth(Integer.MAX_VALUE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    private void on_And_Configure_Socket() {

        System.out.println("Active_socket_called_" + Chat_Main_Activity.username);
        //System.out.println(Chat_Main_Activity.username);
        //  System.out.println(Chat_Main_Activity.userID);

        mSocket.connect();
        // mSocket.on("newUser", onNewUser);//receive new user response
        mSocket.on("onlineUsers", onlineUsers);//receive new user response
        mSocket.on("disconnect", userIsDisconnected);//receive new message response

        JSONObject newUserObj = new JSONObject();
        try {
            newUserObj.put("person", Chat_Main_Activity.username);
            newUserObj.put("ids", Chat_Main_Activity.userID);//sender id
            mSocket.emit("newUser", newUserObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int length = args.length;

                        if (length == 0) {
                            return;
                        }


                        String username = args[0].toString();

                        System.out.println("Active_katrina_" + Chat_Main_Activity.username + username);


                        try {


                            JSONObject object = new JSONObject(username);
                            newUserData = object;

                            //self_user_id = object.getString("main_id");
                            // self_name = object.getString("name");
                            user_socket_id = object.getString("id");

                            //  System.out.println("kareena_self_user_id"+self_user_id);
                            //  System.out.println("kareena_self_socket_id"+self_socket_id);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }
    };

    Emitter.Listener onlineUsers = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("Active_online_user_called_" + Chat_Main_Activity.username + args[0].toString());

                        int length = args.length;

                        if (length == 0) {
                            loading_layout.setVisibility(View.GONE);
                            main_layout.setVisibility(View.GONE);
                            no_friend_layout.setVisibility(View.VISIBLE);
                            return;

                        }
                        //Here i'm getting weird error..................///////run :1 and run: 0
                        String online_user = args[0].toString();

                        fetch_my_Online_Friends(args[0].toString(), Chat_Main_Activity.userID);

                    }
                });
            }
        }
    };

    Emitter.Listener userIsDisconnected = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    };


    //receive new user response

    public void fetch_my_Online_Friends(String args, final String userID) {


        RequestBody body = new FormBody.Builder().
                add("ids", userID).
                add("onlineUsers", args).
                build();

        final Request request = new Request.Builder().
                url("https://icosom.com/social/main/androidcheck.php").
                post(body).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());
                loading_layout.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                System.out.println("active_myResponse" + Chat_Main_Activity.username + myResponse);
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


                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    recyclerView.getRecycledViewPool().clear();
                                    adapter = new Online_User_Adapter(getContext(), online_user_list);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    loading_layout.setVisibility(View.GONE);
                                    main_layout.setVisibility(View.VISIBLE);
                                    no_friend_layout.setVisibility(View.GONE);

                                }
                            });
                        }


                    } else {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // loadingProgressBar.hide();
                                    if (MainActivity.online_user_list!= null) {
                                        MainActivity.online_user_list.clear();

                                        if (online_user_list != null)
                                            online_user_list.clear();
                                    }

                                    MainActivity.online_user_list = online_user_list;
                                    adapter.notifyDataSetChanged();
                                    loading_layout.setVisibility(View.GONE);
                                    main_layout.setVisibility(View.GONE);
                                    no_friend_layout.setVisibility(View.VISIBLE);
                                    //loading_layout.setVisibility(View.GONE);
                                }
                            });
                        }


                    }


                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (online_user_list != null)
                                    System.out.println("active_my_frnd_online_list" + Chat_Main_Activity.username + online_user_list);
                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}