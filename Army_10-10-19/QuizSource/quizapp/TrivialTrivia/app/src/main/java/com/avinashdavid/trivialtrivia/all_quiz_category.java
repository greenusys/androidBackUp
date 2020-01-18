package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Name;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Section;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Subjects;
import com.avinashdavid.trivialtrivia.Adapter.All_Quiz_adapter_Topic;
import com.avinashdavid.trivialtrivia.Model.Name_Wise_Quiz;
import com.avinashdavid.trivialtrivia.Model.Subject_Category_Model;
import com.avinashdavid.trivialtrivia.Model.Topic_Category_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class all_quiz_category extends AppCompatActivity {
    ArrayList subject_array;
    ArrayList topic_array;
    ArrayList section_array;
    ArrayList category_list = new ArrayList();

    int image = R.drawable.ic_arrow_downward_black_24dp;

    RecyclerView rv_gd;
    AppController appController;
    All_Quiz_adapter_Subjects all_quiz_adapter_subjects;
    All_Quiz_adapter_Topic all_quiz_adapter_topic;
    All_Quiz_adapter_Section all_quiz_adapter_section;
    All_Quiz_adapter_Name all_quiz_adapter_name;
    Name_Wise_Quiz name_wise_quiz;


    //Subject_Category_Model all_quiz_model;
    Topic_Category_Model topic_category_model;


    // Topic_Category_Model topic_category_model;


    ArrayAdapter<String> arrayAdapter;
    Spinner materialDesignSpinner;

    ArrayList<String> subject;
    ArrayList<String> subject_id;
    TextView topictext;

    RadioGroup radioGroup;
    RadioButton subject_radiobutton, topic_radiobutton, section_radiobutton;

    public static String spinner_value2;
    public static String spinner_id2;


    List<Subject_Category_Model> soldire_gd_models = new ArrayList<>();
    List<Topic_Category_Model> topicCategoryModels = new ArrayList<>();
    List<Name_Wise_Quiz> name_wise_quizs_array = new ArrayList<>();

    String id = "";
    public static String category;
    String spinner_value = "";
    RelativeLayout anim_layout;
    LinearLayout data_layout;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quiz_category);

        category_list.add("All Quizzes");
        category_list.add("Subject Wise Quizzes");
        category_list.add("Topic Wise Quizzes");
        category_list.add("Section Wise Quizzes");

        anim_layout = findViewById(R.id.anim_layout);
        data_layout = findViewById(R.id.data_layout);
        lottieAnimationView = findViewById(R.id.lotti);






       /* radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        section_radiobutton = (RadioButton) findViewById(selectedId);*/


        appController = (AppController) getApplicationContext();
        subject_array = new ArrayList();
        topic_array = new ArrayList();
        section_array = new ArrayList();


        getSupportActionBar().setTitle("Online Quizzes");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        subject = new ArrayList<String>();
        subject_id = new ArrayList<String>();

        API("All Quiz");


        /* materialDesignSpinner = (Spinner) findViewById(R.id.android_material_design_spinner);*/
        materialDesignSpinner = findViewById(R.id.android_material_design_spinner);


        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, category_list);
        materialDesignSpinner.setAdapter(arrayAdapter);



/*        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), category_list, image);
        materialDesignSpinner.setAdapter(customAdapter);*/


        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner_value = parent.getItemAtPosition(position).toString();//get subject name from list


                if (name_wise_quizs_array != null)
                    name_wise_quizs_array.clear();

                if (topicCategoryModels != null)
                    topicCategoryModels.clear();


                API(spinner_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void API(final String selected_value) {

        anim_layout.setVisibility(View.VISIBLE);
        data_layout.setVisibility(View.GONE);
        lottieAnimationView.playAnimation();


        Request request = null;
        RequestBody requestBody = null;
        rv_gd = (RecyclerView) findViewById(R.id.rv);


        if (selected_value.equalsIgnoreCase("subject wise quizzes"))

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allquizsubject.php").build();

        else if (selected_value.equalsIgnoreCase("topic wise quizzes"))

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allquiztopic.php").build();

        else if (selected_value.equalsIgnoreCase("section wise quizzes"))

            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allquizsection.php").build();

        else/* if(selected_value.equalsIgnoreCase("name"))*/
            request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allquiz.php").build();



        /*final ProgressDialog pdLoading = new ProgressDialog(all_quiz_category.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();*/

        // Request request = new Request.Builder().url("http://greenusys.website/indianarmy/indianarmy/androidapi/all_quiz_category_list.php").build();

        //RequestBody body = new FormBody.Builder().add("subject", subject_id).build();

        //Request request = new Request.Builder().url("http://greenusys.website/indianarmy/indianarmy/androidapi/apifetch_quizbysubject.php").post(body).build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                all_quiz_category.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        anim_layout.setVisibility(View.GONE);
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
                        all_quiz_category.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {
                                    String a = ja.getString("status");
                                    if (ja.getString("status").equalsIgnoreCase("0")) {

                                        JSONArray aaa = ja.getJSONArray("message");

                                        Log.e("dsf", "sd" + aaa);
                                        for (int i = 0; i < aaa.length(); i++) {
                                            JSONObject item = aaa.getJSONObject(i);

                                            if (selected_value.equalsIgnoreCase("subject wise quizzes")) {
                                                Log.d("subject_id", item.getString("subject_id"));
                                                Log.d("subject_name", item.getString("subject_name"));
                                                Log.d("date", item.getString("date"));


                                                //  topicCategoryModels.clear();
                                                topic_category_model = new Topic_Category_Model(item.getString("subject_id"), item.getString("subject_name"), item.getString("date"));
                                                topicCategoryModels.add(topic_category_model);

                                                System.out.println("kaifsak" + topicCategoryModels);
                                            } else if (selected_value.equalsIgnoreCase("topic wise quizzes")) {
                                                Log.d("topic_id", item.getString("topic_id"));
                                                Log.d("topic_name", item.getString("topic_name"));
                                                Log.d("date", item.getString("date"));


                                                //  topicCategoryModels.clear();
                                                topic_category_model = new Topic_Category_Model(item.getString("topic_id"), item.getString("topic_name"), item.getString("date"));
                                                topicCategoryModels.add(topic_category_model);
                                            } else if (selected_value.equalsIgnoreCase("section wise quizzes")) {
                                                Log.d("section_id", item.getString("section_id"));
                                                Log.d("section_name", item.getString("section_name"));
                                                Log.d("date", item.getString("date"));


                                                //  topicCategoryModels.clear();
                                                topic_category_model = new Topic_Category_Model(item.getString("section_id"), item.getString("section_name"), item.getString("date"));
                                                topicCategoryModels.add(topic_category_model);
                                            } else/* if(selected_value.equalsIgnoreCase("name"))*/ {

                                                Log.d("name", item.getString("name"));


                                                name_wise_quiz = new Name_Wise_Quiz(item.getString("name"));
                                                name_wise_quizs_array.add(name_wise_quiz);


                                            }


                                        }

                                     /*   for (int i = 0; i < aaa.length(); i++)
                                        {
                                            JSONObject item = aaa.getJSONObject(i);

                                            name_wise_quiz = new Name_Wise_Quiz(item.getString("name"));
                                            name_wise_quizs_array.add(name_wise_quiz);

                                        }*/


                                        if (selected_value.equalsIgnoreCase("subject wise quizzes")) {
                                            //materialDesignSpinner.setHint("");
                                            //  Toast.makeText(appController, "called", Toast.LENGTH_SHORT).show();

                                            List<Topic_Category_Model> result = new ArrayList<Topic_Category_Model>();
                                            Set<String> titles = new HashSet<String>();

                                            for (Topic_Category_Model item : topicCategoryModels) {
                                                if (titles.add(item.getTopic_name())) {
                                                    result.add(item);
                                                }
                                            }


                                            all_quiz_adapter_subjects = new All_Quiz_adapter_Subjects(result);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            rv_gd.setLayoutManager(mLayoutManager);
                                            rv_gd.setItemAnimator(new DefaultItemAnimator());
                                            rv_gd.setAdapter(all_quiz_adapter_subjects);
                                            all_quiz_adapter_subjects.notifyDataSetChanged();
                                        } else if (selected_value.equalsIgnoreCase("topic wise quizzes")) {

                                            List<Topic_Category_Model> result = new ArrayList<Topic_Category_Model>();
                                            Set<String> titles = new HashSet<String>();

                                            for (Topic_Category_Model item : topicCategoryModels) {
                                                if (titles.add(item.getTopic_name())) {
                                                    result.add(item);
                                                }
                                            }
                                            //materialDesignSpinner.setHint("");
                                            all_quiz_adapter_topic = new All_Quiz_adapter_Topic(result);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            rv_gd.setLayoutManager(mLayoutManager);
                                            rv_gd.setItemAnimator(new DefaultItemAnimator());
                                            rv_gd.setAdapter(all_quiz_adapter_topic);
                                            all_quiz_adapter_topic.notifyDataSetChanged();
                                        } else if (selected_value.equalsIgnoreCase("section wise quizzes")) {

                                            List<Topic_Category_Model> result = new ArrayList<Topic_Category_Model>();
                                            Set<String> titles = new HashSet<String>();

                                            for (Topic_Category_Model item : topicCategoryModels) {
                                                if (titles.add(item.getTopic_name())) {
                                                    result.add(item);
                                                }
                                            }
                                            //materialDesignSpinner.setHint("");
                                            all_quiz_adapter_section = new All_Quiz_adapter_Section(result);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            rv_gd.setLayoutManager(mLayoutManager);
                                            rv_gd.setItemAnimator(new DefaultItemAnimator());
                                            rv_gd.setAdapter(all_quiz_adapter_section);
                                            all_quiz_adapter_section.notifyDataSetChanged();
                                        } else/* if(selected_value.equalsIgnoreCase("name"))*/ {


                                            List<Name_Wise_Quiz> result = new ArrayList<Name_Wise_Quiz>();
                                            Set<String> titles = new HashSet<String>();

                                            for (Name_Wise_Quiz item : name_wise_quizs_array) {
                                                if (titles.add(item.getName())) {
                                                    result.add(item);
                                                }
                                            }


                                            all_quiz_adapter_name = new All_Quiz_adapter_Name(result);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            rv_gd.setLayoutManager(mLayoutManager);
                                            rv_gd.setItemAnimator(new DefaultItemAnimator());
                                            rv_gd.setAdapter(all_quiz_adapter_name);
                                            all_quiz_adapter_name.notifyDataSetChanged();


                                        }


                                    } else {

                                        name_wise_quizs_array.clear();
                                        topicCategoryModels.clear();

                                        clearAdapterData();


                                      /*  all_quiz_adapter_name.notifyDataSetChanged();
                                        all_quiz_adapter_section.notifyDataSetChanged();
                                        all_quiz_adapter_subjects.notifyDataSetChanged();
                                        all_quiz_adapter_topic.notifyDataSetChanged();
*/


                                        Toast.makeText(appController, "No List Found", Toast.LENGTH_SHORT).show();

                                    }

                                    //pdLoading.dismiss();

                                    anim_layout.setVisibility(View.GONE);
                                    data_layout.setVisibility(View.VISIBLE);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    anim_layout.setVisibility(View.GONE);

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

    private void clearAdapterData() {

        if (spinner_value.equalsIgnoreCase("subject wise quizzes")) {
            //materialDesignSpinner.setHint("");
            //  Toast.makeText(appController, "called", Toast.LENGTH_SHORT).show();
            all_quiz_adapter_subjects = new All_Quiz_adapter_Subjects(topicCategoryModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_gd.setLayoutManager(mLayoutManager);
            rv_gd.setItemAnimator(new DefaultItemAnimator());
            rv_gd.setAdapter(all_quiz_adapter_subjects);
            all_quiz_adapter_subjects.notifyDataSetChanged();
        } else if (spinner_value.equalsIgnoreCase("topic wise quizzes")) {
            //materialDesignSpinner.setHint("");
            all_quiz_adapter_topic = new All_Quiz_adapter_Topic(topicCategoryModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_gd.setLayoutManager(mLayoutManager);
            rv_gd.setItemAnimator(new DefaultItemAnimator());
            rv_gd.setAdapter(all_quiz_adapter_topic);
            all_quiz_adapter_topic.notifyDataSetChanged();
        } else if (spinner_value.equalsIgnoreCase("section wise quizzes")) {
            //materialDesignSpinner.setHint("");
            all_quiz_adapter_section = new All_Quiz_adapter_Section(topicCategoryModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_gd.setLayoutManager(mLayoutManager);
            rv_gd.setItemAnimator(new DefaultItemAnimator());
            rv_gd.setAdapter(all_quiz_adapter_section);
            all_quiz_adapter_section.notifyDataSetChanged();
        } else/* if(selected_value.equalsIgnoreCase("name"))*/ {


            List<Name_Wise_Quiz> result = new ArrayList<Name_Wise_Quiz>();
            Set<String> titles = new HashSet<String>();


            all_quiz_adapter_name = new All_Quiz_adapter_Name(result);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_gd.setLayoutManager(mLayoutManager);
            rv_gd.setItemAnimator(new DefaultItemAnimator());
            rv_gd.setAdapter(all_quiz_adapter_name);
            all_quiz_adapter_name.notifyDataSetChanged();


        }
    }


}
