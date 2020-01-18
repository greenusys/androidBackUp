package com.example.digitalforgeco.room_db_package.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


import com.example.digitalforgeco.modal.History

@Dao
interface DaoAccess {

    @Insert
    fun insertTask(note: History): Long?


    @Query("SELECT * FROM History ORDER BY id desc")
    fun fetch_All_History(): LiveData<List<History>>


    @Query("DELETE FROM History")
     fun delete_History()
}
