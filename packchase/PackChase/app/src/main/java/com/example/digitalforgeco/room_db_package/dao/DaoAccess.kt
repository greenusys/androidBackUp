package com.example.digitalforgeco.room_db_package.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.digitalforgeco.modal.*

@Dao
interface DaoAccess {


    //Simple Calculator History
    @Insert
    fun insert_Tracking_History(note: Tracking_History_Modal): Long?

    @Query("SELECT * FROM Tracking_History_Modal")
    fun fetch_All_Tracking_History(): LiveData<List<Tracking_History_Modal>>

    @Query("SELECT * FROM Tracking_History_Modal where tracking_no=:tracking_no")
    fun fetch_Tracking_History_By_Specific(tracking_no: String): LiveData<List<Tracking_History_Modal>>

    @Query("UPDATE Tracking_History_Modal SET completed=:completed AND json_data=:jsonData WHERE tracking_no = :tracking_no")
    fun update_Tracking_History(completed: String, jsonData: String, tracking_no:String)


    @Query("DELETE FROM Tracking_History_Modal")
    fun delete_Tracking_History()


    @Query("DELETE FROM Tracking_History_Modal where tracking_no=:tracking_no")
    fun delete_One_Tracking_History(tracking_no:String)



}

