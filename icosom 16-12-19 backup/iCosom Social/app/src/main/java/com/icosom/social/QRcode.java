package com.icosom.social;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.icosom.social.activity.MainActivity;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcode extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(QRcode.this, DashboardRecharge.class));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ImageView imageView =findViewById(R.id.barcd);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String friend_id=getIntent().getStringExtra("id");
        String frnd_name= MainActivity.User_Name;
        String frnd_email=sp.getString("email", "");
        String frnd_phone=sp.getString("phone", "");
       // Log.e("sadfs","asdf"+text);

        // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(friend_id+","+frnd_name+","+frnd_email+","+frnd_phone, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
