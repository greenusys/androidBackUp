package com.icosom.social;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.QrPayment;
import com.icosom.social.activity.VerifyKyc;

import java.io.IOException;

public class ScannedBarcode extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView txtBarcodevalue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    boolean isEmail = false;
    String act;
    SharedPreferences sp;
    String User_Name="",User_Id="";
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ScannedBarcode.this, MainActivity.class));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        act = getIntent().getStringExtra("act");

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        User_Name=(sp.getString("firstName", "") + " " + sp.getString("lastName", ""));
        User_Id=sp.getString("userId", "");



        initViews();

    }

    private void initViews() {
        txtBarcodevalue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);


    }

    private void initialiseDetectorsAndSources() {
     //   Toast.makeText(getApplicationContext(), "Barcode scanner Stared", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(2000, 1080)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcode.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
              //  Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodevalue.post(new Runnable() {

                        @Override
                        public void run() {

                            if(barcodes.valueAt(0)!= null) {

                                intentData = barcodes.valueAt(0).displayValue;

                                String barcodevalue[]=intentData.split(",");
                                if(User_Id.equalsIgnoreCase(barcodevalue[0])) {
                                    Log.e("same_id_kaif","ksjd");
                                    Toast.makeText(ScannedBarcode.this, "You cannot access self QR Code", Toast.LENGTH_SHORT).show();
                                 finish();

                                    //  Snackbar.make(surfaceView, "You cannot access self QR Code", Snackbar.LENGTH_LONG)
                                            //.setAction("Action", null).show();
                                }
                                    else {
                                    Log.e("diff_id_kaif","ksjd");
                                    Log.e("scan", "run: " + intentData);//name and id
                                    if (act.equalsIgnoreCase("rech")) {
                                        String user_name = MainActivity.User_Name;//user name
                                        Log.e("user_kaif_name2", "run: " + user_name);

                                        startActivity(new Intent(ScannedBarcode.this, QrPayment.class).putExtra("qr_code_data", intentData)/*.putExtra("friend_id", "1024").*//*.putExtra("name", user_name)*/);
                                    }
                                    if (act.equalsIgnoreCase("kyc")) {
                                        startActivity(new Intent(ScannedBarcode.this, VerifyKyc.class).putExtra("userId", intentData));
                                    }
                                    txtBarcodevalue.setText(intentData);
                                }
                            }
                            else
                            {
                                Toast.makeText(ScannedBarcode.this, "no user id found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}

