package com.example.digitalforgeco.room_db_package.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.digitalforgeco.activity.Temperature_Calculator_3;
import com.example.digitalforgeco.modal.Distance_Calc_History;
import com.example.digitalforgeco.modal.Length_Calc_History;
import com.example.digitalforgeco.modal.Scienfic_Calc_History;
import com.example.digitalforgeco.modal.Simple_Calc_History;
import com.example.digitalforgeco.modal.Temperature_Calc_History;
import com.example.digitalforgeco.modal.Volume_Calc_History;
import com.example.digitalforgeco.modal.Weight_Calc_History;
import com.example.digitalforgeco.room_db_package.dao.DaoAccess;


@Database(entities = {Simple_Calc_History.class,
        Scienfic_Calc_History.class,
        Temperature_Calc_History.class,
        Distance_Calc_History.class,
        Length_Calc_History.class,
        Weight_Calc_History.class,
        Volume_Calc_History.class}, version = 1, exportSchema = false)

public abstract class NoteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
