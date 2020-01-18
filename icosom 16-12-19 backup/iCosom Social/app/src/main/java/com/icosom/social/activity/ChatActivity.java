package com.icosom.social.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icosom.social.Adapter.Mess_adapter;
import com.icosom.social.R;
import com.icosom.social.model.Mess_model;
import com.icosom.social.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

    Mess_adapter mess_adapter;
    private SharedPreferences sp;
    private TextView no_chat_found;
    private TextView frnd_name;
    RecyclerView.LayoutManager layoutManager;
      String  firebase_token ="";
    private String logged_in_user_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        M_send = findViewById(R.id.bt_M_send);
        M_message = findViewById(R.id.ed_M_message);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       final  String  Sender = sp.getString("userId", "");
       final  String  Recver = getIntent().getStringExtra("friend_id");
       final  String  frnd_name2 = getIntent().getStringExtra("friend_name");
       firebase_token = getIntent().getStringExtra("firebase_token");

        logged_in_user_name = sp.getString("firstName", "")+" "+ sp.getString("lastName", "");

       System.out.println("chat_Activity");
       System.out.println("Sender"+Sender);
       System.out.println("Recver"+Recver);
       System.out.println("firebase_token"+firebase_token);
       System.out.println("logged_in_user_name"+logged_in_user_name);

        no_chat_found = findViewById(R.id.no_frnd_text);
        frnd_name = findViewById(R.id.frnd_name);

        frnd_name.setText(frnd_name2);


        appController = (AppController) getApplicationContext();

        Receive(Sender, Recver);

        M_send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(M_message.getText().toString().equals(""))
                        {
                            M_message.setError("Please Enter Message");
                            return;

                        }
                        else
                        {
                            Send(Sender, Recver, M_message.getText().toString());
                            M_message.setText("");
                            Receive(Sender, Recver);
                        }
                    }
                }
        );


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              //  System.out.println("kaif_called");
                Receive(Sender, Recver);
            }
        }, 0, 1500);//put here time 1000 milliseconds=1 second
        // Receive(Sender,Recver);
        rv = findViewById(R.id.messagedata);

        mess_adapter = new Mess_adapter(mess_modelsList,Sender);
 layoutManager = new LinearLayoutManager(getApplicationContext());

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mess_adapter);
        // prepareMovieData();

    }
    private void Send(String rname, final String remail, final String rpassword)
    {
       // System.out.println("h1"+rname+"h2"+remail+"h3"+rpassword);

        //final ProgressDialog pdLoading = new ProgressDialog(Message.this);
        RequestBody requestBody = new FormBody.Builder()

                .add("sent_to", rname)
                .add("sent_by", remail)
                .add("message", rpassword)

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

                            //System.out.println("json"+jsonObject);

                            sendNotification_To_User(firebase_token,remail,rpassword);

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
                                       // System.out.println("time1"+self_time);
                                        String a [] = self_time.split(" ");
                                        String b = a[1];
                                       // System.out.println(b);
                                        m.setSelf_message(message);
                                        m.setSelf_id(self_id);
                                        m.setSelf_message_time(b);
                                        mess_modelsList.add(m);
                                        mess_adapter.notifyDataSetChanged();

                                    }
                                    //for frnd message
                                   else
                                    {

                                        Mess_model m=new Mess_model();
                                        String message = jsonObject1.getString("message");
                                        String sent_by = jsonObject1.getString("sent_by");
                                        String friend_time = jsonObject1.getString("sended_on");

                                      //  System.out.println("time2"+friend_time);
                                        m.setFriend_message(message);
                                        m.setFrnd_id(sent_by);
                                        m.setFriend_message_time(friend_time);
                                        mess_modelsList.add(m);
                                        mess_adapter.notifyDataSetChanged();

                                    }

                                    //for scroll recycler view in last position

                                    /*if(ChatActivity.this!=null) {
                                        ChatActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                rv.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        int bottom = rv.getAdapter().getItemCount()-1;
                                                        //rv.smoothScrollToPosition(bottom);
                                                        no_chat_found.setVisibility(View.GONE);
                                                        layoutManager.scrollToPosition(bottom); // yourList is the ArrayList that you are passing to your RecyclerView Adapter.

                                                    }
                                                });

                                            }
                                        });
                                    }
*/





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


    public void sendNotification_To_User(String device_token,String User_Name,String message)
    {



        System.out.println("kaif_SendNotificationMethod");
        System.out.println("message"+message);
        System.out.println("title"+logged_in_user_name);
        System.out.println("device_token"+device_token);



        RequestBody body = new FormBody.Builder().
                add("send_to", "single").
                add("firebase_token", device_token).
                add("message", message).
                add("title", logged_in_user_name).
                add("image_url", "").
                add("action", "").

                build();

        Request request = new Request.Builder().
                url("https://icosom.com/kaif_notification/chatnotification.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("notification_send_Success"+myResponse);

            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backfrom=1;
    }
}
