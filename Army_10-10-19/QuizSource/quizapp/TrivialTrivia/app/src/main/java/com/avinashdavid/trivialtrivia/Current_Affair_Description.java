package com.avinashdavid.trivialtrivia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Current_Affair_Description extends AppCompatActivity {

    String news_title, news_date, news_link, news_description, language;
    TextView news_title2, news_date2, news_link2, news_description2;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__affair__description);

        appController = (AppController) getApplicationContext();

        getSupportActionBar().setTitle("Daily Current Affair");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // news_title=getIntent().getStringExtra("news_title");
        news_date = getIntent().getStringExtra("news_date");
        language = getIntent().getStringExtra("language");

        System.out.println("language" + language);


        news_title2 = findViewById(R.id.heading);
        news_date2 = findViewById(R.id.date);
        news_link2 = findViewById(R.id.link);
        news_description2 = findViewById(R.id.description);

        news_title2.setText(news_date);
        // news_date2.setText(news_date);
        // news_link2.setText(Html.fromHtml(news_link));

        fetch_News_Description(news_date);


    }


    private void fetch_News_Description(String news_date) {
        final ProgressDialog pdLoading = new ProgressDialog(Current_Affair_Description.this);

        pdLoading.setMessage("Loading...");
        pdLoading.show();
        RequestBody body = null;
        Request request = null;
        body = new FormBody.Builder().add("date", news_date).add("language", language).build();


        request = new Request.Builder().url("http://4army.in/indianarmy/androidapi/apifetch_allenglishcurrentaffairs.php").post(body).build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Current_Affair_Description.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                        pdLoading.dismiss();
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
                        Current_Affair_Description.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("aaaa1", "onResponse: " + ja);
                                try {

                                    if (ja.getString("status").equalsIgnoreCase("0")) {

                                        JSONArray message = ja.getJSONArray("message");


                                        if (ja.getString("status").equalsIgnoreCase("0")) {
                                            // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                                            JSONArray aaa = ja.getJSONArray("message");

                                            Log.e("dsf", "sd" + aaa);
                                            for (int i = 0; i < aaa.length(); i++) {

                                                JSONObject item = aaa.getJSONObject(i);

                                                Log.d("details_kaif", item.getString("details"));
                                                String details = item.getString("details");
                                                String detail_result = Html.fromHtml(details).toString();
                                                news_description2.setText(detail_result);

                                            }


                                        }


                                    }

                                    pdLoading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return;
                    } else {
                        //if(list!=null)
                        // list.clear();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
