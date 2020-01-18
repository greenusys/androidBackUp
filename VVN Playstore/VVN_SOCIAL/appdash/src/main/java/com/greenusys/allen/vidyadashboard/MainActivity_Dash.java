package com.greenusys.allen.vidyadashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_Dash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvQuizDetails,tvQuizClick,tvChatDetails,tvChatClick,tvDocsDetails,tvDocsClick,tvVideoDetails,tvVideoClick;
    TextView tvQuiz,tvChat,tvDocs,tvVideo;
    ImageView ivProfile,bg_chat,bg_quiz,bg_doc,bg_video,btnExploreA,btnExploreB,btnExploreC,btnExploreD;
    private final AppCompatActivity activity = MainActivity_Dash.this;


    String url = "https://vvn.city/apps/jain/rewards.php";
    private DatabaseHelper_Dash databaseHelper;
    private Edit edit;
    String firstName,reward_point;
    String mess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__dash);


        databaseHelper = new DatabaseHelper_Dash(activity);
      //  session = new SessionManagement_Dash(getApplicationContext());

     /*   d


        boolean updateData = databaseHelper.addUser(activity,"s@gmail.com");
        session.createLoginSession("somya", "s@gmail.com");*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
       // getSupportActionBar().setLogo(R.drawable.vidya_logo);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Caviar-Dreams/CaviarDreams.ttf");
        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/Caviar-Dreams/CaviarDreams_BoldItalic.ttf");

        btnExploreA=(ImageView)findViewById(R.id.btnExploreA);
        btnExploreB=(ImageView)findViewById(R.id.btnExploreB);
        btnExploreC=(ImageView)findViewById(R.id.btnExploreC);
        btnExploreD=(ImageView)findViewById(R.id.btnExploreD);

        tvQuiz = (TextView)findViewById(R.id.tv_textQuiz);
        tvQuiz.setTypeface(font_bold);
        tvChat = (TextView)findViewById(R.id.tv_textChat);
        tvChat.setTypeface(font_bold);
        tvDocs = (TextView)findViewById(R.id.tv_textDocs);
        tvDocs.setTypeface(font_bold);
        tvVideo = (TextView)findViewById(R.id.tv_textVideo);
        tvVideo.setTypeface(font_bold);

        tvQuizDetails = (TextView)findViewById(R.id.tv_quizDetail);
        tvQuizDetails.setTypeface(font);
        tvQuizClick = (TextView)findViewById(R.id.tv_clickQuiz);
        tvChatDetails = (TextView)findViewById(R.id.tv_chatDetails);
        tvChatDetails.setTypeface(font);
        tvChatClick = (TextView)findViewById(R.id.tv_clickChat);
        tvDocsDetails = (TextView)findViewById(R.id.tv_docsDetails);
        tvDocsDetails.setTypeface(font);
        tvDocsClick = (TextView)findViewById(R.id.tv_clickDocs);
        tvVideoDetails = (TextView)findViewById(R.id.tv_videoDetails);
        tvVideoDetails.setTypeface(font);
        tvVideoClick = (TextView)findViewById(R.id.tv_clickVideo);
        ivProfile = (ImageView)findViewById(R.id.iv_profile);
        bg_chat=(ImageView)findViewById(R.id.bg_chat);
        bg_doc=(ImageView)findViewById(R.id.bg_doc);
        bg_quiz=(ImageView)findViewById(R.id.bg_quiz);
        bg_video=(ImageView)findViewById(R.id.bg_video);
        Picasso.with(this).load(R.drawable.vidya).transform(new CircleTransform()).into(ivProfile);

      btnExploreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vb=new Intent(MainActivity_Dash.this,Physics.class);
                startActivity(vb);
            }
        });
        btnExploreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vbc1=new Intent(MainActivity_Dash.this,Chemistry.class);
                startActivity(vbc1);
            }
        });
        btnExploreC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vbc=new Intent(MainActivity_Dash.this,Maths.class);
                startActivity(vbc);
            }
        });
        btnExploreD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vbc2=new Intent(MainActivity_Dash.this,Biology.class);
                startActivity(vbc2);
            }
        });

        tvQuizClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent qq1=new Intent(MainActivity_Dash.this,ServerList.class);
                startActivity(qq1);
            }
        });

        tvChatClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home11 = new Intent(MainActivity_Dash.this, MessageListActivity.class);
                startActivity(home11);

            }
        });
        tvDocsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent document1 = new Intent(MainActivity_Dash.this, Doc.class);
                startActivity(document1);

            }
        });
        tvVideoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choose1 = new Intent(MainActivity_Dash.this, ChooseVideo.class);
                startActivity(choose1);


            }
        });
      bg_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q1=new Intent(MainActivity_Dash.this,ServerList.class);
                startActivity(q1);
            }
        });

        bg_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home11 = new Intent(MainActivity_Dash.this, MessageListActivity.class);
                startActivity(home11);

            }
        });
      bg_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent document1 = new Intent(MainActivity_Dash.this, Doc.class);
                startActivity(document1);

            }
        });
        bg_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choose1 = new Intent(MainActivity_Dash.this, ChooseVideo.class);
                startActivity(choose1);


            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent profile=new Intent(MainActivity_Dash.this,Profile.class);
            startActivity(profile);
            return true;
        }
        if (id == R.id.logout) {
            //session.checkLogin();


            // get user data from session
            //HashMap<String, String> user = session.getUserDetails();

            // name
            //String name = user.get(SessionManagement_Dash.KEY_NAME);

            // email
            //String email = user.get(SessionManagement_Dash.KEY_EMAIL);

            //session.logoutUser();
            databaseHelper.deleteUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent home= new Intent(MainActivity_Dash.this,MainActivity_Dash.class);
            startActivity(home);

        }
        else if (id == R.id.whiteboard) {



        }
        else if (id == R.id.abt) {
Intent abt=new Intent(MainActivity_Dash.this,Abttt.class);
            startActivity(abt);


        } else if (id == R.id.quiz) {
            Intent qq10=new Intent(MainActivity_Dash.this,ServerList.class);
            startActivity(qq10);

        } else if (id == R.id.chat) {
            Intent home1 = new Intent(MainActivity_Dash.this, MessageListActivity.class);
            startActivity(home1);


        } else if (id == R.id.up) {
            Intent up = new Intent(MainActivity_Dash.this, Whiteboard.class);
            startActivity(up);

        } else if (id == R.id.rewards) {
            msg();


        }
        else if (id == R.id.course) {
            Intent up = new Intent(MainActivity_Dash.this, Click.class);
            startActivity(up);


        }

//        else if (id == R.id.whiteboard) {
//            Toast.makeText(activity, "Whiteboard", Toast.LENGTH_SHORT).show();
//
//
//        }
        else if (id == R.id.video) {
            Intent choose = new Intent(MainActivity_Dash.this, ChooseVideo.class);
            startActivity(choose);


        }
        else if (id == R.id.doc) {
            Intent document = new Intent(MainActivity_Dash.this, Doc.class);
            startActivity(document);


        }
        else if (id == R.id.privacy) {
            Intent privacypolicy = new Intent(MainActivity_Dash.this, Privacy.class);
            startActivity(privacypolicy);


        }
        else if (id == R.id.visit) {
            String url = "https://vvn.city/";
            Intent c = new Intent(Intent.ACTION_VIEW);
            c.setData(Uri.parse(url));
            startActivity(c);


        }
        else if (id == R.id.nav_share) {
        dia();


        }
        else if (id == R.id.info) {

            Intent infor = new Intent(MainActivity_Dash.this, Terms.class);
            startActivity(infor);

        }
        else if (id == R.id.query) {
            Intent fedd = new Intent(MainActivity_Dash.this, Queryy.class);
            startActivity(fedd);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void dia() {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Vidya");
        i.putExtra(android.content.Intent.EXTRA_TEXT, "https://vvn.city/");
        startActivity(Intent.createChooser(i, "Share via"));

    }

    private void msg() {
        edit = new Edit();
        edit = databaseHelper.getUser();
        firstName = edit.getEmail();
        reward_point=edit.getDate();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    mess = jsonObject.getString("message");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity_Dash.this);
                builder1.setMessage(mess);
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity_Dash.this, "some error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", firstName);
                return params;
            }
        };


        MySingleton.getInstance(getApplication()).addToRequestque(stringRequest);

    }
}
