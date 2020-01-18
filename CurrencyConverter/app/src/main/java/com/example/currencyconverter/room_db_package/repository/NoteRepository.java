package com.example.currencyconverter.room_db_package.repository;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.currencyconverter.modal.History;
import com.example.currencyconverter.room_db_package.db.NoteDatabase;

import java.util.List;

public class NoteRepository {

    private String DB_NAME = "currency_db";

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
    }


    public void Delete_ALL_History_Record() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteTask();
                return null;
            }
        }.execute();
    }


    public void insert_History_Record(String country_name,
                                      String country_code2_letter,
                                      String country_code3_letter,
                                      byte image[]) {

        History note = new History(country_name, country_code2_letter,country_code3_letter, image);
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


    public LiveData<History> getTask(String country_code) {
        return noteDatabase.daoAccess().getTask(country_code);
    }


    public LiveData<List<History>> getALL_History_Record() {
        return noteDatabase.daoAccess().fetchAllTasks();
    }


}
