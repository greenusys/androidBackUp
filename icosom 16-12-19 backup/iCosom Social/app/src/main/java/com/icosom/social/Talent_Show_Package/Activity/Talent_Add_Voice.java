package com.icosom.social.Talent_Show_Package.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.icosom.social.Interface.RuntimePermissionsActivity;
import com.icosom.social.R;
import com.icosom.social.Talent_Show_Package.Adapter.Show_Video_Adapter;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.ImageFilePath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Talent_Add_Voice extends RuntimePermissionsActivity {

    private static final int SELECT_AUDIO = 2;
    public static int RECORD_REQUEST = 0;
    ArrayList<String> rv_voice_list = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();
    boolean video = false;
    Uri audioFileUri;
    String value = "";
    String audio_filename = "";
    private EditText text_editbox;
    private String selectedPath;
    private AppController appController;
    private RecyclerView rv_voice;
    private TextView txt_done;
    private ProgressBar pb_loading;
    private Show_Video_Adapter adapter;
    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent__add__voice);
        value = getIntent().getStringExtra("value");
        System.out.println("talent_add_voice" + value);

        initViews();
        settxt_done_setListener();


    }

    private void initViews() {
        text_editbox = findViewById(R.id.edit_text);
        rv_voice = findViewById(R.id.rv_voice);
        txt_done = findViewById(R.id.txt_done);
        pb_loading = findViewById(R.id.pb_loading);


        appController = (AppController) getApplicationContext();
        //show selected voice in recycler view
        rv_voice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Show_Video_Adapter(Talent_Add_Voice.this, rv_voice_list, video, false);
        rv_voice.setAdapter(adapter);

    }

    private void settxt_done_setListener() {
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send_Post_API(String talent_option, String user_id,String post_text)

                try {
                    System.out.println("file_kaif" + files);

                    for (int i = 0; i < files.size(); i++) {
                        File f = files.get(i);
                        String filePath = f.getAbsolutePath();
                        System.out.println("audio_name" + filePath.substring(filePath.lastIndexOf("/") + 1));


                        if (audio_filename.equals(""))
                            audio_filename = filePath.substring(filePath.lastIndexOf("/") + 1) + "split";
                        else
                            audio_filename = audio_filename.concat(filePath.substring(filePath.lastIndexOf("/") + 1)) + "split";

                    }

                    System.out.println("audio_file_name" + audio_filename);


                    if (files.size() != 0)
                        send_Post_API(audio_filename, value, MainActivity.user_id, text_editbox.getText().toString());
                    else
                        snackbar.make(findViewById(R.id.root), "Please upload video file", Snackbar.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //for upload voice
    //1
    public void upload_voice(View view) {


        if (ContextCompat.checkSelfPermission(Talent_Add_Voice.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Talent_Add_Voice.super.requestAppPermissions(new String[]
                    {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, R.string.runtimepermission_txt, SELECT_AUDIO);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Audio "), SELECT_AUDIO);

        }
    }


    //2
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        try {

            System.out.println("onactivity_voice");
            System.out.println("REQUEST_CODE" + requestCode);
            System.out.println("check_code" + requestCode);
            System.out.println("resultCode" + resultCode);
            System.out.println("data" + data);


            if (resultCode == RESULT_OK) {

                if (requestCode == SELECT_AUDIO) {

                    String name = data.getData().getEncodedPath();


                    System.out.println("file_name" + name);
                    Uri audioFileUri = data.getData();

                    final Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Audio/abc.mp3");


                    System.out.println("sayed" + uri);


                    System.out.println("SELECT_AUDIO");
                    ClipData cd = data.getClipData();
                    System.out.println("ClipData_kaif" + cd);
                    System.out.println("audioFileUri" + audioFileUri.getPath());


                    //for multiple files
                    if (cd != null) {
                        System.out.println("first");
                        for (int i = 0; i < cd.getItemCount(); i++) {
                            rv_voice_list.add("" + cd.getItemAt(i).getUri());
                            files.add(new File(audioFileUri.getPath()));
                        }
                    }
                    //for single files
                    else {
                        System.out.println("second");
                        rv_voice_list.add("" + data.getData());
                        if (files.size() != 0)
                            files.clear();

                        for (int i = 0; i < rv_voice_list.size(); i++)
                            files.add(new File(ImageFilePath.getPath(getApplicationContext(), Uri.parse(rv_voice_list.get(i)))));
                    }

                    adapter.notifyDataSetChanged();
                    System.out.println("rv" + rv_voice_list);
                    System.out.println("file_data" + files);


                } else if (requestCode == RECORD_REQUEST) {
                    // audioFileUri = data.getData();
                    ClipData cd = data.getClipData();

                    if (cd != null) {
                        for (int i = 0; i < cd.getItemCount(); i++) {
                            rv_voice_list.add("" + cd.getItemAt(i).getUri());
                            files.add(new File(ImageFilePath.getPath(getApplicationContext(), Uri.parse(rv_voice_list.get(i)))));
                        }
                    } else {
                        rv_voice_list.add("" + data.getData());
                        files.add(new File(ImageFilePath.getPath(getApplicationContext(), Uri.parse(rv_voice_list.get(0)))));
                    }

                    adapter.notifyDataSetChanged();
                    //  System.out.println("audio_kaif"+audioFileUri);
                    //playRecording.setEnabled(true);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void record_voice(View view) {
        System.out.println("record_voice_called");
        /*Intent intent = new Intent(
                MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, RECORD_REQUEST);*/


    }


    private void send_Post_API(String file_name, String talent_option, String user_id, String post_text) {


        System.out.println("send_post_api_called");
        System.out.println("talent_option" + talent_option);
        System.out.println("user_id" + user_id);
        System.out.println("post_text" + post_text);
        System.out.println("file_size" + files.size());

        pb_loading.setVisibility(View.VISIBLE);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("talent_option", talent_option);
        builder.addFormDataPart("user_id", user_id);
        builder.addFormDataPart("post_text", post_text);
        builder.addFormDataPart("file_name", file_name);


        for (File f : files) {
            String filePath = f.getAbsolutePath();


            MediaType mediaType = MediaType.parse("audio/" + filePath.substring(filePath.lastIndexOf(".") + 1));
            builder.addFormDataPart("files[]", filePath.substring(filePath.lastIndexOf("/") + 1),
                    RequestBody.create(mediaType, f));


            System.out.println("file_path" + filePath);
            System.out.println("file_path_substring" + filePath.substring(filePath.lastIndexOf(".") + 1));
            System.out.println("mediaType" + mediaType);
            System.out.println("files[]_audio_name" + filePath.substring(filePath.lastIndexOf("/") + 1));
            System.out.println("sallu\n");


        }

        System.out.println("kaif_file_name" + audio_filename);
        Request request = new Request.Builder().
                url("https://icosom.com/social/main/new_api/talent_show_api.php").
                post(builder.build()).
                build();


        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Talent_Add_Voice.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("failed_to_voice");

                        pb_loading.setVisibility(View.INVISIBLE);
                        Log.e("kaif_Excetpion-3", "skdj" + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Talent_Add_Voice.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jo = new JSONObject(myResponse);

                            System.out.println("kaif_uploaded" + jo);

                            if (jo.getString("status").equals("1")) {
                                pb_loading.setVisibility(View.INVISIBLE);

                                showAlertBox("Voice successfully uploaded");

                            } else {
                                Talent_Add_Voice.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        pb_loading.setVisibility(View.INVISIBLE);
                                        showAlertBox("Failed to upload voice");
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void showAlertBox(final String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                Talent_Add_Voice.this);
        builder.setTitle("Icosom Talent Show");
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (!message.contains("failed")) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            rv_voice_list.clear();
                            files.clear();
                            adapter.notifyDataSetChanged();

                        }

                    }
                });
        builder.show();
    }

    @Override

    public void onPermissionsGranted(int requestCode) {
        if (requestCode == SELECT_AUDIO) {
            upload_voice(null);
            // isImage = false;
        }
       /* else if (requestCode == REQ_PER_CAMERA) {
            openCameraImages();
            //isImage = true;
        }*/
    }


}