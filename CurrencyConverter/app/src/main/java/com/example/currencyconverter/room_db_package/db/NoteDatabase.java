package com.example.currencyconverter.room_db_package.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.currencyconverter.modal.History;
import com.example.currencyconverter.room_db_package.dao.DaoAccess;


@Database(entities = {History.class}, version = 1, exportSchema = false)

public abstract class NoteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
