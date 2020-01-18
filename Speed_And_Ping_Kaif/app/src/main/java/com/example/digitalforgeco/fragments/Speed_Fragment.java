package com.example.digitalforgeco.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.digitalforgeco.Network_Receiver.MyReceiver;
import com.example.digitalforgeco.R;
import com.example.digitalforgeco.activity.Speed_History_Data;
import com.example.digitalforgeco.core.Speedtest;
import com.example.digitalforgeco.core.config.SpeedtestConfig;
import com.example.digitalforgeco.core.config.TelemetryConfig;
import com.example.digitalforgeco.core.serverSelector.TestPoint;
import com.example.digitalforgeco.room_db_package.repository.NoteRepository;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Speed_Fragment extends Fragment {


    private static Speedtest st = null;
    LottieAnimationView anim;
    TextView b, check_history;
    TextView dlText, ulText, pingText;
    RelativeLayout start_layout, root;
    TestPoint server2;
    boolean first_time = true;
    private int TRANSITION_LENGTH = 300;
    private MyReceiver myReceiver;
    private boolean reinitOnResume = false;
    private NoteRepository noteRepository;
    TextView mstext,mbps_text_1,mbps_text_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);


        if (!isNetworkAvailable(getContext())) {
            Snackbar snackbar = Snackbar
                    .make(root, "Please check your internet connection!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else
            page_init();


    }

    private void initViews(View view) {

        noteRepository = new NoteRepository(getContext());


        mstext = view.findViewById(R.id.mstext);
        mbps_text_1 = view.findViewById(R.id.mbps_text_1);
        mbps_text_2 = view.findViewById(R.id.mbps_text_2);


        anim = view.findViewById(R.id.anim);
        b = view.findViewById(R.id.start_test);
        root = view.findViewById(R.id.root);
        check_history = view.findViewById(R.id.check_history);

        myReceiver = new MyReceiver(this);


        dlText = view.findViewById(R.id.dlText);
        ulText = view.findViewById(R.id.ulText);
        pingText = view.findViewById(R.id.pingText);
        start_layout = view.findViewById(R.id.start_layout);


        check_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), Speed_History_Data.class));
            }
        });
    }

    public void broadcast_Receiver() {
        if (first_time == false) {
            System.out.println("broadcast_Receiver_method_called");
            page_init();
        }
        first_time = false;
    }

    public boolean isNetworkAvailable(Context context)//check internet of device
    {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private String readFileFromAssets(String name) throws Exception {
        BufferedReader b = new BufferedReader(new InputStreamReader(getContext().getAssets().open(name)));
        String ret = "";
        try {
            for (; ; ) {
                String s = b.readLine();
                if (s == null) break;
                ret += s;
            }
        } catch (EOFException e) {
        }
        return ret;
    }

    private void page_init() {

        SpeedtestConfig config = null;
        TelemetryConfig telemetryConfig = null;
        TestPoint[] servers = null;
        try {
            String c = readFileFromAssets("SpeedtestConfig.json");
            JSONObject o = new JSONObject(c);
            config = new SpeedtestConfig(o);
            c = readFileFromAssets("TelemetryConfig.json");
            o = new JSONObject(c);
            telemetryConfig = new TelemetryConfig(o);


            c = readFileFromAssets("ServerList.json");
            JSONArray a = new JSONArray(c);
            if (a.length() == 0) throw new Exception("No test points");
            ArrayList<TestPoint> s = new ArrayList<>();
            for (int i = 0; i < a.length(); i++) s.add(new TestPoint(a.getJSONObject(i)));
            servers = s.toArray(new TestPoint[0]);
            if (st != null) {
                try {
                    st.abort();
                } catch (Throwable e) {
                }
            }
            st = new Speedtest();
            st.setSpeedtestConfig(config);
            st.setTelemetryConfig(telemetryConfig);
            st.addTestPoints(servers);
            final String testOrder = config.getTest_order();

        } catch (final Throwable e) {
            st = null;
            // transition(R.id.page_fail,TRANSITION_LENGTH);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    anim.pauseAnimation();
                    System.out.println("exception" + e.getMessage());
                    b.setText("Retry");
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            anim.playAnimation();
                            start_layout.setVisibility(View.GONE);
                            page_init();
                            b.setOnClickListener(null);
                        }
                    });
                }
            });
            return;
        }


        st.selectServer(new Speedtest.ServerSelectedHandler() {
            @Override
            public void onServerSelected(final TestPoint server) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (server == null) {

                            System.out.println("server_is_null");
                            b.setText("Retry");
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    anim.playAnimation();
                                    start_layout.setVisibility(View.GONE);
                                    page_init();
                                    b.setOnClickListener(null);
                                }
                            });
                        } else {
                            server2 = server;
                            page_serverSelect(server, st.getTestPoints());
                        }
                    }
                });
            }
        });
    }

    private void page_serverSelect(TestPoint selected, TestPoint[] servers) {
        System.out.println("page_serverSelect_Caledddddddd");
        reinitOnResume = true;
        final ArrayList<TestPoint> availableServers = new ArrayList<>();
        for (TestPoint t : servers) {
            if (t.getPing() != -1) availableServers.add(t);
        }
        ArrayList<String> options = new ArrayList<String>();
        for (TestPoint t : availableServers) {
            options.add(t.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, options.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        if (ulText.getText().toString().contains("Mbps")) {

            b.setText("START AGAIN");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isNetworkAvailable(getContext())) {
                    Snackbar snackbar = Snackbar
                            .make(root, "Please check your internet connection!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {


                    if (mstext.getText().toString().contains("ms")) {
                        ulText.setText("");
                        dlText.setText("");
                        pingText.setText("");

                        mbps_text_1.setVisibility(View.GONE);
                        mbps_text_2.setVisibility(View.GONE);
                        mstext.setVisibility(View.GONE);

                    }

                    anim.playAnimation();
                    start_layout.setVisibility(View.GONE);

                    System.out.println("clicked_1");
                    reinitOnResume = false;

                    try {
                        page_test(availableServers.get(0));

                    } catch (Exception e) {

                    }

                    System.out.println("kaif" + availableServers.get(0));
                    b.setOnClickListener(null);
                }
            }
        });


    }

    private String format(double d) {
        Locale l = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            l = getResources().getConfiguration().getLocales().get(0);
        } else {
            l = getResources().getConfiguration().locale;
        }
        if (d < 10) return String.format(l, "%.2f", d);
        if (d < 100) return String.format(l, "%.1f", d);
        return "" + Math.round(d);
    }

    private void page_test(final TestPoint selected) {
        st.setSelectedServer(selected);
        st.start(new Speedtest.SpeedtestHandler() {
            @Override
            public void onDownloadUpdate(final double dl, final double progress) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mbps_text_1.setVisibility(View.VISIBLE);
                        dlText.setText(progress == 0 ? "..." : format(dl));

                    }
                });
            }

            @Override
            public void onUploadUpdate(final double ul, final double progress) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mbps_text_2.setVisibility(View.VISIBLE);
                        ulText.setText(progress == 0 ? "..." : format(ul));
                    }
                });

            }

            @Override
            public void onPingJitterUpdate(final double ping, final double jitter, final double progress) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mstext.setVisibility(View.VISIBLE);
                        pingText.setText(progress == 0 ? "..." : format(ping));
                    }
                });
            }

            @Override
            public void onIPInfoUpdate(final String ipInfo) {

            }

            @Override
            public void onTestIDReceived(final String id, final String shareURL) {
                if (shareURL == null || shareURL.isEmpty() || id == null || id.isEmpty()) return;

            }

            @Override
            public void onEnd() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("Test_COmpletedddddddddddd");



                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {



                                b.setText("START AGAIN");
                                anim.pauseAnimation();
                                start_layout.setVisibility(View.VISIBLE);

                                page_init();
                            }
                        }, 2000);




                        String download = dlText.getText().toString().replace("Mbps", "");
                        String upload = ulText.getText().toString().replace("Mbps", "");
                        String ping = pingText.getText().toString().replace("ms", "");

                        System.out.println("download" + download);
                        System.out.println("upload" + upload);
                        System.out.println("ping" + ping);


                        insert_data_To_History(ping, download, upload);


                    }
                });
                final long startT = System.currentTimeMillis(), endT = startT + TRANSITION_LENGTH;
                new Thread() {
                    public void run() {
                        while (System.currentTimeMillis() < endT) {
                            final double f = (double) (System.currentTimeMillis() - startT) / (double) (endT - startT);

                            try {
                                sleep(10);
                            } catch (Throwable t) {
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onCriticalFailure(String err) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        b.setText("Try again");
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                anim.playAnimation();
                                start_layout.setVisibility(View.GONE);
                                page_init();
                                b.setOnClickListener(null);
                            }
                        });
                    }
                });
            }
        });
    }

    private void insert_data_To_History(final String ping, final String download, final String upload) {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //  noteRepository.daoAccess().insertTodoList(params[0]);

                Date date2 = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String today_date = dateFormat.format(date2);

                String date_arr[] = today_date.split(" ");

                String date = date_arr[0];
                String time = date_arr[1];


                noteRepository.insert_History_Record(date, time, ping, download, upload);


                return null;

            }

            @Override
            protected void onPostExecute(Void voids) {
                System.out.println("inserted_data");
                super.onPostExecute(voids);
            }
        }.execute();


    }


    @Override
    public void onResume() {
        System.out.println("resume_called");

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(myReceiver, filter);


        super.onResume();
        if (reinitOnResume) {
            reinitOnResume = false;
            page_init();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(myReceiver);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            st.abort();
        } catch (Throwable t) {
        }
    }


}
