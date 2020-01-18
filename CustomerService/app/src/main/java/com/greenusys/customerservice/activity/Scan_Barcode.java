package com.greenusys.customerservice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.greenusys.customerservice.R;
import com.greenusys.customerservice.utility.AppController;
import com.greenusys.customerservice.utility.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Scan_Barcode extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView txtBarcodevalue;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    boolean isEmail = false;
    AppController appController;
    String gid;
    String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__barcode);
        val = getIntent().getStringExtra("val");
        appController = (AppController) getApplicationContext();
        initViews();
    }

    private void initViews() {
        txtBarcodevalue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);

        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Scan_Barcode.this, "this is ", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void initialiseDetectorsAndSources() {
        Toast.makeText(this, "Barcode scanner started", Toast.LENGTH_SHORT).show();
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
                    if (ActivityCompat.checkSelfPermission(Scan_Barcode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(Scan_Barcode.this, new
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
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodevalue.post(new Runnable() {

                        @Override
                        public void run() {
                            if(barcodes.valueAt(0)!= null) {
                            intentData = barcodes.valueAt(0).toString();

                                intentData = barcodes.valueAt(0).displayValue;
                               /* Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentData));
                                startActivity(myIntent);*/

                                Toast.makeText(Scan_Barcode.this, "hello" + intentData, Toast.LENGTH_SHORT).show();
                                loginPlease(intentData);

                                txtBarcodevalue.setText(intentData);
                            }
                            else
                            {
                                Toast.makeText(Scan_Barcode.this, "here is not data found", Toast.LENGTH_SHORT).show();
                            }
                            /* if (barcodes.valueAt(0) != null) {
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodevalue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                                btnAction.setText("LAUNCH URL");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodevalue.setText(intentData);

                            }*/
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

    private void loginPlease(String gid) {

//        pb_login.setVisibility(View.VISIBLE);

        RequestBody body = new FormBody.Builder().
                add("query_id", gid).
                add("workstatus", val).
                build();

        Request request = new Request.Builder().
                url(UrlHelper.stauts).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Scan_Barcode.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(myResponse);
                    String code = jsonObject.getString("Code");
                    if(code.equalsIgnoreCase("1")){
                        startActivity(new Intent(Scan_Barcode.this,EnginerDashboard.class));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}

