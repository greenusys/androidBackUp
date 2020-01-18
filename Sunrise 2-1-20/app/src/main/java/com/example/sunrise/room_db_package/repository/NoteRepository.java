package com.example.sunrise.room_db_package.repository;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;


import com.example.sunrise.Modal.Favourite_Modal;
import com.example.sunrise.Modal.History_Modal;
import com.example.sunrise.room_db_package.db.NoteDatabase;

import java.util.ArrayList;
import java.util.List;


public class NoteRepository {

    private String DB_NAME = "sunrise_db";

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
    }


   /* public void Delete_ALL_History_Record() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().delete_History();
                return null;
            }
        }.execute();
    }*/

  public void Delete_One_History_Record(final String address ) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().delete_One_History(address);
                return null;
            }
        }.execute();
    }

    public void Delete_One_Favourite_Record(final String latlong ) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().delete_One_Favourite(latlong);
                return null;
            }
        }.execute();
    }


    public void insert_Favourite_Record(String address,
                                      String latlong) {

        Favourite_Modal note = new Favourite_Modal(address, latlong);
        insertTask(note);
    }

    public void insert_History_Record(String address,
                                      String sunrise_time,
                                      String sunset_time) {

        History_Modal note = new History_Modal(address,sunrise_time,sunset_time);
        insert_history_task(note);
    }


    public void insertTask(final Favourite_Modal note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    noteDatabase.daoAccess().insertTask(note);

                } catch (Exception e) {

                }
                return null;
            }
        }.execute();
    }


public void insert_history_task(final History_Modal note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    noteDatabase.daoAccess().insertHistory(note);

                } catch (Exception e) {

                }
                return null;
            }
        }.execute();
    }




    public LiveData<List<Favourite_Modal>> get_All_Favourite_Record() {
        return noteDatabase.daoAccess().fetch_All_Favourite();
    }


 public LiveData<List<History_Modal>> getALL_History_Record() {
        return noteDatabase.daoAccess().fetch_All_History();
    }


}
