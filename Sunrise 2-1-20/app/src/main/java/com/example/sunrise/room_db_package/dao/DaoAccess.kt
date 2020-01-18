package com.example.digitalforgeco.room_db_package.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


import com.example.sunrise.Modal.Favourite_Modal
import com.example.sunrise.Modal.History_Modal

@Dao
interface DaoAccess {

    @Insert
    fun insertTask(note: Favourite_Modal): Long?

    @Insert
    fun insertHistory(note: History_Modal): Long?


    @Query("SELECT * FROM Favourite_Modal")
    fun fetch_All_Favourite(): LiveData<List<Favourite_Modal>>

    @Query("SELECT * FROM History_Modal")
    fun fetch_All_History(): LiveData<List<History_Modal>>



    @Query("DELETE FROM Favourite_Modal WHERE latlong= :latlong")
    fun delete_One_Favourite(latlong: String)



    @Query("DELETE FROM History_Modal WHERE address= :address")
    fun delete_One_History(address: String)
}

