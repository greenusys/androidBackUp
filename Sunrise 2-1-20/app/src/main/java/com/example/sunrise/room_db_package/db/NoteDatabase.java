package com.example.sunrise.room_db_package.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.digitalforgeco.room_db_package.dao.DaoAccess;
import com.example.sunrise.Modal.Favourite_Modal;
import com.example.sunrise.Modal.History_Modal;


@Database(entities = {Favourite_Modal.class, History_Modal.class}, version = 1, exportSchema = false)

public abstract class NoteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
