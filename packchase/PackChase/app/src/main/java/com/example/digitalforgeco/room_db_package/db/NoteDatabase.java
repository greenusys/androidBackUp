package com.example.digitalforgeco.room_db_package.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.digitalforgeco.modal.Tracking_History_Modal;
import com.example.digitalforgeco.room_db_package.dao.DaoAccess;


@Database(entities = {Tracking_History_Modal.class,
}, version = 1, exportSchema = false)

public abstract class NoteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
