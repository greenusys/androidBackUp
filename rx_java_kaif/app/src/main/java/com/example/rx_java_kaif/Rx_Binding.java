package com.example.rx_java_kaif;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Rx_Binding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx__binding);


       /* Disposable d = RxView.clicks(findViewById(R.id.button)).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        Toast.makeText(getApplicationContext(),"Button clicked",Toast.LENGTH_SHORT).show();
                    }
                });*/


        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        EditText editText = findViewById(R.id.editText);


        Disposable d2 = RxTextView.textChanges((TextView) findViewById(R.id.editText))
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        System.out.println(charSequence);
                    }
                });

        SparseArray<String> map = new SparseArray<String>();
map.put(1,"kaif");
map.put(2,"sayed");






    }
}
