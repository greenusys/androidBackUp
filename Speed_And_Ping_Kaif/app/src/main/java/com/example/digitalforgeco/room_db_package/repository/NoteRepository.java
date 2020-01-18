package com.example.digitalforgeco.room_db_package.repository;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.digitalforgeco.modal.History;
import com.example.digitalforgeco.room_db_package.db.NoteDatabase;

import java.util.List;

public class NoteRepository {

    private String DB_NAME = "speed_and_ping_db";

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
    }


    public void Delete_ALL_History_Record() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().delete_History();
                return null;
            }
        }.execute();
    }


    public void insert_History_Record(String date,
                                      String time,
                                      String ping,
                                      String download,
                                      String upload) {

        History note = new History(date, time,ping, download,upload);
        insertTask(note);
    }


    public void insertTask(final History note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().insertTask(note);
                return null;
            }
        }.execute();
    }


  /*  public LiveData<History> getTask(String country_code) {
        return noteDatabase.daoAccess().getTask(country_code);
    }*/


    public LiveData<List<History>> getALL_History_Record() {
        return noteDatabase.daoAccess().fetch_All_History();
    }


}
