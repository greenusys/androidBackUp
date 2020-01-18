package com.example.currencyconverter.room_db_package.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.currencyconverter.modal.History;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(History note);



    @Query("SELECT * FROM History order by id desc limit 10")
    LiveData<List<History>> fetchAllTasks();






    @Query("SELECT * FROM History WHERE country_code2_letter =:code")
    LiveData<History> getTask(String  code);








    @Query("DELETE FROM History")
    void deleteTask();
}
