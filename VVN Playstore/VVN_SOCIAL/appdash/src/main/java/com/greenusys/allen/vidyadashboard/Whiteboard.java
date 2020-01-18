package com.greenusys.allen.vidyadashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Whiteboard extends AppCompatActivity {
    String url = "https://vvn.city/apps/jain/booking.php";
    String urls = "https://vvn.city/bbb/session.php";
    String urlss = "https://vvn.city/apps/jain/check.php";
    String filename = "vidyasession.txt";
    String type = "1", str, link, cour, bat, tim, jain, batch_id, codes, messagess;
    ListView listView;
    int images[] = new int[1];
    // String[] a =null ;
    List<String> batch;
    List<String> course;
    List<String> times;
    List<String> batch_no;
    String id;
    String batchlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);
        images[0] = R.drawable.next_button;
        batch = new ArrayList<>();
        course = new ArrayList<>();
        times = new ArrayList<>();
        batch_no = new ArrayList<>();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        cour = jsonObject.getString("course");
                        bat = jsonObject.getString("batch_name");
                        tim = jsonObject.getString("s_time");
                        str = jsonObject.getString("s_date");
                        batch_id = jsonObject.getString("batch_id");

                        //    final String time = "12:18:00";

                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                            final Date dateObj = sdf.parse(tim);
                            jain = new SimpleDateFormat("hh:mm aa").format(dateObj);
                        } catch (final java.text.ParseException e) {
                            e.printStackTrace();
                        }
                        // a[i]=str;
                        course.add(cour);
                        times.add(str + " " + jain);
                        batch_no.add(batch_id);
                        batch.add(bat);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView = (ListView) findViewById(R.id.whiteboard_list);
                Whiteboard.Adapter myAdapter = new Whiteboard.Adapter(getApplicationContext(), batch, course, times, images);
                listView.setAdapter(myAdapter);
                jain();
               /* ArrayAdapter<String> adapter=new ArrayAdapter<String>(Doc_Next.this,android.R.layout.simple_list_item_1,a);
                listView.setAdapter(adapter);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Whiteboard.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", type);
                return params;
            }
        };


        MySingleton.getInstance(Whiteboard.this).addToRequestque(stringRequest);
    }

    private void jain() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                link = batch_no.get(i);

                StringRequest stringRequestss = new StringRequest(Request.Method.POST, urlss, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                codes = jsonObject.getString("code");
                                messagess = jsonObject.getString("message");
                                Log.e("message", "onResponse: "+messagess );
                                // Toast.makeText(getApplicationContext(), "" + jsonObject.getString("code") + " " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        check();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Whiteboard.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("batch", link);
                        params.put("id", type);
                        return params;
                    }
                };


                MySingleton.getInstance(Whiteboard.this).addToRequestque(stringRequestss);


            }
        });


    }

    private void check() {
        if (codes.equalsIgnoreCase("1")) {
            next(link);
        }
        if (codes.equalsIgnoreCase("0")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Whiteboard.this);
            builder1.setMessage(messagess);
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            //Toast.makeText(getApplicationContext(), messagess, Toast.LENGTH_SHORT).show();
        }
    }

    public void next(String lin) {
        batchlink = lin;
        StringRequest stringRequests = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        messagess = jsonObject.getString("message");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (iseritable()) {
                    if (isStoragePermissionGranted()){
                        write(messagess);
                        bui();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Whiteboard.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("batch", batchlink);
                params.put("id", type);
                return params;
            }
        };


        MySingleton.getInstance(Whiteboard.this).addToRequestque(stringRequests);
    }


    class Adapter extends ArrayAdapter {

        int[] imagear;
        List<String> namear;
        List<String> courses;
        List<String> timess;

        public Adapter(Context context, List<String> name, List<String> course, List<String> times, int image[]) {
            super(context, R.layout.whitelist, R.id.titlelist, name);
            this.imagear = image;
            this.namear = name;
            this.courses = course;
            this.timess = times;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.whitelist, parent, false);
            ImageView im = (ImageView) row.findViewById(R.id.conts);
            TextView t = (TextView) row.findViewById(R.id.batch_name);
            TextView t1 = (TextView) row.findViewById(R.id.course);
            TextView t2 = (TextView) row.findViewById(R.id.times);


            im.setImageResource(imagear[0]);
            t.setText(batch.get(position));
            t1.setText(course.get(position));
            t2.setText(times.get(position));

            return row;

        }

    }
    private void write(String dat) {

        //   Toast.makeText(getApplicationContext(),"enter",Toast.LENGTH_LONG).show();
        File sdcard = Environment.getExternalStorageDirectory();
        File f = new File(sdcard, filename);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            String data = dat;

            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void bui() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Whiteboard.this);
        builder1.setMessage("Are you sure to start whiteboard session");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent LaunchIntent = getPackageManager()
                                .getLaunchIntentForPackage("air.com.greenusys.Main.debug");
                        startActivity(LaunchIntent);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private boolean iseritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;

        } else {
            return false;
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("wa","Permission is granted");
                return true;
            } else {

                Log.v("wa","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("wa","Permission is granted");
            return true;
        }
    }

}


