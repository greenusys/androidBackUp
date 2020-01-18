package com.example.g116.vvn_social.Home_Activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.g116.vvn_social.Adapter.Chat_Message_Adapter;
import com.example.g116.vvn_social.Modal.Mess_model;
import com.example.g116.vvn_social.Network_Package.AppController;
import com.example.g116.vvn_social.R;
import com.example.g116.vvn_social.Session_Package.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    public static int backfrom;
    TextInputEditText M_message;
    Button M_send;
    TextView txt;
    String Sender;
    String Recver;
    AppController appController;
    AlertDialog.Builder builder;
    RecyclerView rv;
    ArrayList<Mess_model> mess_modelsList = new ArrayList<>();
    Mess_model mess_model;

    Chat_Message_Adapter chatMessageAdapter;
    private SharedPreferences sp;
    private TextView no_chat_found;
    private TextView frnd_name;
    RecyclerView.LayoutManager layoutManager;
    private SessionManager session;
    private HashMap<String, String> user;
    String user_id ="";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        M_send = findViewById(R.id.bt_M_send);
        M_message = findViewById(R.id.ed_M_message);

        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();
        user_id = user.get(SessionManager.KEY_ID);

       final  String  Sender = user_id;

       final  String  Recver = getIntent().getStringExtra("friend_id");
       final  String  frnd_name2 = getIntent().getStringExtra("friend_name");

       System.out.println("chat_Activity");
       System.out.println("Sender"+Sender);
       System.out.println("Recver"+Recver);

        no_chat_found = findViewById(R.id.no_frnd_text);
        frnd_name = findViewById(R.id.frnd_name);

        frnd_name.setText(frnd_name2);



        appController = (AppController) getApplicationContext();

        Receive(Sender, Recver);








        M_send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Send(Sender, Recver, M_message.getText().toString());
                        M_message.setText("");
                        Receive(Sender, Recver);

                    }
                }
        );


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Receive(Sender, Recver);
            }
        }, 0, 1500);//put here time 1000 milliseconds=1 second





        // Receive(Sender,Recver);
        rv = findViewById(R.id.messagedata);

        chatMessageAdapter = new Chat_Message_Adapter(mess_modelsList,Sender);
 layoutManager = new LinearLayoutManager(getApplicationContext());

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(chatMessageAdapter);
        // prepareMovieData();






    }









    private void Send(String sent_to, String sent_by, String message)
    {
        System.out.println("h1"+sent_to+"h2"+sent_by+"h3"+message);

        //final ProgressDialog pdLoading = new ProgressDialog(Message.this);
        RequestBody requestBody = new FormBody.Builder()

                .add("sent_to", sent_to)
                .add("sent_by", sent_by)
                .add("message", message)

                .build();

        Request request = new Request.Builder().
                url("https://icosom.com/test/msgapi.php")
                .post(requestBody)
                .build();
        // pdLoading.setMessage("Loading...");
        // pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();


                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            //String code = jsonObject.getString("Code");
                            // String msg = jsonObject.getString("Message");

                            System.out.println("json"+jsonObject);

                            // pdLoading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void Receive(final String sender, String receiver)
    {
//        final ProgressDialog pdLoading = new ProgressDialog(getApplicationContext());
        RequestBody requestBody = new FormBody.Builder()
                .add("sent_to", sender)
                .add("sent_by", receiver)
                .build();

        Request request = new Request.Builder().
                url("https://icosom.com/test/fetchmessage.php")
                .post(requestBody)
                .build();
        //  pdLoading.setMessage("Loading...");
        // pdLoading.show();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error" + e.getMessage());
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();


                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);

                            if(jsonObject.getString("status").equals("1")) {

                                JSONArray jr = jsonObject.getJSONArray("msgs");
                                if (mess_modelsList != null)
                                    mess_modelsList.clear();

                                for (int i = jr.length() - 1; i >= 0; i--) {
                                    JSONObject jsonObject1 = jr.getJSONObject(i);


                                    //for self message
                                    if(jsonObject1.getString("sent_to").equals(sender))
                                    {

                                        Mess_model m=new Mess_model();
                                        String message = jsonObject1.getString("message");
                                        String self_id = jsonObject1.getString("sent_to");
                                        String self_time = jsonObject1.getString("sended_on");
                                        m.setSelf_message(message);
                                        m.setSelf_id(self_id);
                                        m.setSelf_message_time(self_time);
                                        mess_modelsList.add(m);
                                        chatMessageAdapter.notifyDataSetChanged();

                                    }
                                    //for frnd message
                                   else
                                    {

                                        Mess_model m=new Mess_model();
                                        String message = jsonObject1.getString("message");
                                        String sent_by = jsonObject1.getString("sent_by");
                                        String friend_time = jsonObject1.getString("sended_on");
                                        m.setFriend_message(message);
                                        m.setFrnd_id(sent_by);
                                        m.setFriend_message_time(friend_time);
                                        mess_modelsList.add(m);
                                        chatMessageAdapter.notifyDataSetChanged();

                                    }

                                    //for scroll recycler view in last position

                                    if(ChatActivity.this!=null) {
                                        ChatActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                rv.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        int bottom = rv.getAdapter().getItemCount()-1;
                                                        //rv.smoothScrollToPosition(bottom);
                                                        no_chat_found.setVisibility(View.GONE);
                                                     //   layoutManager.scrollToPosition(bottom); // yourList is the ArrayList that you are passing to your RecyclerView Adapter.

                                                    }
                                                });

                                            }
                                        });
                                    }





                                }

                            }
                            else
                            {

                                if(ChatActivity.this!=null) {
                                    ChatActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            no_chat_found.setVisibility(View.VISIBLE);

                                        }
                                    });
                                }
                            }




                            // pdLoading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom=1;
    }


}
