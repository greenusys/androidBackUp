package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.Login_Register_Package.Login;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Quiz_Category extends AppCompatActivity {

    //String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

    RecyclerView rv_gd;
    AppController appController;
    All_Quiz_adapter_Subjects solider_gd_adapter;
    All_Quiz_adapter_Topic all_quiz_adapter_topic;

    Subject_Category_Model all_quiz_model;
    Topic_Category_Model topic_category_model;


    ArrayAdapter<String> arrayAdapter;
    MaterialBetterSpinner materialDesignSpinner;

    ArrayList<String> subject;
    ArrayList<String> subject_id;
    TextView topictext;


    List<Subject_Category_Model> soldire_gd_models = new ArrayList<>();
    List<Topic_Category_Model> topicCategoryModels = new ArrayList<>();
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__category);

        getSupportActionBar().setTitle("QuiZ Category");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        subject = new ArrayList<String>();
        subject_id = new ArrayList<String>();

        subject = getIntent().getStringArrayListExtra("subject");
        subject_id = getIntent().getStringArrayListExtra("subject_id");


        materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, subject);
        materialDesignSpinner.setAdapter(arrayAdapter);


        materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String spinner_value = adapterView.getItemAtPosition(position).toString();//get subject name from list

                id = subject_id.get(position);//get Subject id

                Toast.makeText(getApplicationContext(), spinner_value, Toast.LENGTH_LONG).show();

                System.out.println("Subjectkaif" + spinner_value);
                System.out.println("idkaif" + id);


                topic_fetch(id);


            }
        });

        rv_gd = (RecyclerView) findViewById(R.id.rv);
        appController = (AppController) getApplicationContext();


    }

    private void topic_fetch(final String id) {
        final ProgressDialog pdLoading = new ProgressDialog(Quiz_Category.this);


        RequestBody body = new FormBody.Builder().add("id", id).build();

        Request request = new Request.Builder().url(URL.single_quiz_topic).post(body).build();

        pdLoading.setMessage("Loading...");
        pdLoading.show();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Quiz_Category.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse: " + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaa", "onResponse: " + ja);
                    if (ja.has("status")) {
                        Quiz_Category.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("1")) {

                                        JSONArray aaa = ja.getJSONArray("data");

                                        Log.e("dsf", "sd" + aaa);
                                        for (int i = 0; i < aaa.length(); i++) {
                                            JSONObject item = aaa.getJSONObject(i);
                                            Log.d("topic_id", item.getString("topic_id"));
                                            Log.d("topic_name", item.getString("topic_name"));
                                            Log.d("date", item.getString("date"));

                                            topicCategoryModels.clear();
                                            topic_category_model = new Topic_Category_Model(item.getString("topic_id"), item.getString("topic_name"), item.getString("date"));
                                            topicCategoryModels.add(topic_category_model);
                                        }


                                        all_quiz_adapter_topic = new All_Quiz_adapter_Topic(topicCategoryModels);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(all_quiz_adapter_topic);
                                        all_quiz_adapter_topic.notifyDataSetChanged();


                                    } else {
                                        Toast.makeText(appController, "No List Found", Toast.LENGTH_SHORT).show();
                                        topicCategoryModels.clear();
                                        rv_gd.setAdapter(all_quiz_adapter_topic);
                                        all_quiz_adapter_topic.notifyDataSetChanged();
                                        // Intent intent=new Intent(Login.this, Login.class);
                                        //startActivity(intent);
                                    }
                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void kaif() {

        Toast.makeText(getApplication(), "yes", Toast.LENGTH_SHORT).show();

    }
}
