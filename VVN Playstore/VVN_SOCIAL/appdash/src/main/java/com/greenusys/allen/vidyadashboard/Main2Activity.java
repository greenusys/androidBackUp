package com.greenusys.allen.vidyadashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main2Activity extends AppCompatActivity {
    String url = "https://vvn.city/apps/jain/new_video.php";
    String type,str,link;
    ListView listView;
    int img;
    int images[]= new int[1];
    // String[] a =null ;
    List<String> a ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        type = (String) extras.get("type");

        img = extras.getInt("image");

        if(img==1){
            images[0]=R.drawable.sci;
        }
        if(img==2){
            images[0]=R.drawable.math;
        }
        if(img==3){
            images[0]=R.drawable.chem;
        }
        if(img==4){
            images[0]=R.drawable.bio;
        }

        if(img==5){
            images[0]=R.drawable.comp;
        }

        if(img==6){
            images[0]=R.drawable.phys;
        }


        a = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        str = jsonObject.getString("file");
                        // a[i]=str;
                        a.add(str);
                        Log.e("DA", "onResponse: "+str );

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView=(ListView)findViewById(R.id.pdf_list);
                Adapter myAdapter=new Adapter(getApplicationContext(),a ,images);
                listView.setAdapter(myAdapter);
                jain();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main2Activity.this, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("type", type);
                return params;
            }
        };


        MySingleton.getInstance(Main2Activity.this).addToRequestque(stringRequest);
        //     String data[]={"abhisheck.pdf","dataformat.pdf","Image","anjali.pdf","dataformat.pdf","Image","alok.pdf","saumya.pdf","Image"};



    }

    private void jain() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                link = a.get(i);
                next(link);


            }
        });


    }

    public void next(String lin) {
        Intent n=new Intent(Main2Activity.this,Main3Activity.class);
        n.putExtra("link", lin);
        startActivity(n);
    }


    class Adapter extends ArrayAdapter {

        int []imagear;
        List<String> namear;
        public Adapter(Context context, List<String>  name, int image[])
        {
            super(context,R.layout.videolist,R.id.titlelist,name);
            this.imagear=image;
            this.namear=name;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView , ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.videolist,parent,false);
            ImageView im=(ImageView) row.findViewById(R.id.image);
            TextView t=(TextView)row.findViewById(R.id.titlelist);

            im.setImageResource(imagear[0]);
            t.setText(a.get(position));


            return row;

        }

    }
}

