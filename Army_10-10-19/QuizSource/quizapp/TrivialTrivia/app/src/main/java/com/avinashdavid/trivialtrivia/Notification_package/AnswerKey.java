package com.avinashdavid.trivialtrivia.Notification_package;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.AppController;
import com.avinashdavid.trivialtrivia.Model.soldire_gd_model;
import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class AnswerKey extends AppCompatActivity {
    AppController appController;
    WebView answerkey_wb;
    List<soldire_gd_model> soldire_gd_models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_key);

        final ProgressDialog pdLoading = new ProgressDialog(AnswerKey.this);
        pdLoading.show();

        getSupportActionBar().setTitle("Answer Key");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        answerkey_wb = (WebView) findViewById(R.id.answerkey_webView);
        WebSettings webSettings = answerkey_wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        appController = (AppController) getApplicationContext();

        Request request = new Request.Builder().url(URL.answerkey).build();
        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrr"+e.getMessage());
                AnswerKey.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"Sorry! Not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                Log.e("hhh", "onResponse" + myResponse);
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    Log.e("aaaqaa", "onResponse:" + ja);
                    try {
                        String a = ja.getString("status");
                        if (ja.getString("status").equalsIgnoreCase("0")) {
                            // startActivity(new Intent(Soldier_Sample_Paper.this, soldier_category.class));

                            JSONArray aaa = ja.getJSONArray("message");

                            Log.e("dsf", "sd" + aaa);
                            for (int i = 0; i < aaa.length(); i++) {
                                JSONObject item = aaa.getJSONObject(i);


                                Log.d("link", item.getString("link"));
                                Log.d("date", item.getString("date"));
                                /*soldire_gd_model soldire_gd_model1 = new soldire_gd_model(item.getString("admit_card_name"),item.getString("date"),item.getString("link"));*/
                                soldire_gd_model soldire_gd_model1 = new soldire_gd_model("", item.getString("date"), item.getString("link"));
                                soldire_gd_models.add(soldire_gd_model1);

                                final String link = item.getString("link");
                                if (AnswerKey.this != null) {
                                    AnswerKey.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            answerkey_wb.loadUrl(link);
                                            pdLoading.dismiss();
                                        }
                                    });
                                }
                                            /*  String url = soldire_gd_model1.getLink();
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(url));
                                            startActivity(intent);*/

                            }

                                       /* solider_gd_adapter = new Solider_GD_adapter(soldire_gd_models);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        rv_gd.setLayoutManager(mLayoutManager);
                                        rv_gd.setItemAnimator(new DefaultItemAnimator());
                                        rv_gd.setAdapter(solider_gd_adapter);
                                        solider_gd_adapter.notifyDataSetChanged();*/


                            // Toast.makeText(appController, "Welcome "+email, Toast.LENGTH_SHORT).show();

                        } else {
                            //Toast.makeText(appController, "Please Enter Correct Email id and Password", Toast.LENGTH_SHORT).show();
                        }
                        pdLoading.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        return;
    }


}
   /*         }
        });
    }
}
*/