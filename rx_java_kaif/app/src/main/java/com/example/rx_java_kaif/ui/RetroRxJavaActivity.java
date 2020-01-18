package com.example.rx_java_kaif.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.rx_java_kaif.R;
import com.example.rx_java_kaif.model.Gist;
import com.example.rx_java_kaif.model.GistRepo;
import com.example.rx_java_kaif.service.GistServiceFactory;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RetroRxJavaActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView responseView;
    ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_output);
        responseView = (TextView) findViewById(R.id.txt_hello_world);
        progressView = (ProgressBar) findViewById(R.id.progress_bar);
        //showProgress();

     /*   GistServiceFactory serviceFactory = new GistServiceFactory();

        // start service call using RxJava2
        serviceFactory.getGistService().fetchGistInformation()
                .subscribeOn(Schedulers.io()) //Asynchronously subscribes Observable to perform action in I/O Thread.
                .observeOn(AndroidSchedulers.mainThread()) // To perform its emissions and response on UiThread(or)MainThread.
                .subscribe(new DisposableObserver<Gist>() { // It would dispose the subscription automatically. If you wish to handle it use io.reactivex.Observer
                    @Override
                    public void onNext(Gist gist) {
                        StringBuilder sb = new StringBuilder();
                        // Output
                        for (Map.Entry<String, GistRepo> entry : gist.files.entrySet()) {
                            sb.append(entry.getKey());
                            sb.append(" - ");
                            sb.append("Length of file ");
                            sb.append(entry.getValue().content.length());
                            sb.append("\n");
                        }
                        hideProgress();
                        responseView.setText(sb.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        responseView.setText("Error occurred! Check your Logcat!");
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace(); // Just to see complete log information. we can comment if not necessary!
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "The Gist service Observable has ended!");
                    }
                });
    }

    private void hideProgress() {
        progressView.setVisibility(View.GONE);
        responseView.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        progressView.setVisibility(View.VISIBLE);
        responseView.setVisibility(View.GONE);
    }
*/
    }
}
