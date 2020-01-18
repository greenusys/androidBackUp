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
    fun insert_Simple_Calc(note: Simple_Calc_History): Long?

    @Query("SELECT * FROM Simple_Calc_History")
    fun fetch_All_Simple_Calc_History(): LiveData<List<Simple_Calc_History>>

    @Query("SELECT * FROM Simple_Calc_History where date=:date")
    fun fetch_Simple_Cal_By_Specific(date: String): LiveData<List<Simple_Calc_History>>

    @Query("UPDATE Simple_Calc_History SET data=:data WHERE date = :date")
    fun update_Simple_Calc(date: String, data: String)

    @Query("DELETE FROM Simple_Calc_History")
    fun delete_Simple_History()


    //Scienfic Calculator History
    @Insert
    fun insert_Scienfic_Calc(note: Scienfic_Calc_History): Long?

    @Query("SELECT * FROM Scienfic_Calc_History")
    fun fetch_All_Scienfic_Calc_History(): LiveData<List<Scienfic_Calc_History>>

    @Query("SELECT * FROM Scienfic_Calc_History where date=:date")
    fun fetch_Scienfic_Cal_By_Specific(date: String): LiveData<List<Scienfic_Calc_History>>

    @Query("UPDATE Scienfic_Calc_History SET data=:data WHERE date = :date")
    fun update_Scienfic_Calc(date: String, data: String)

    @Query("DELETE FROM Scienfic_Calc_History")
    fun delete_Scienfic_History()


    //Temperature Calculator History
    @Insert
    fun insert_Temperature_Calc(note: Temperature_Calc_History): Long?

    @Query("SELECT * FROM Temperature_Calc_History")
    fun fetch_All_Temperature_Calc_History(): LiveData<List<Temperature_Calc_History>>


    @Query("DELETE FROM Temperature_Calc_History")
    fun delete_Temperature_History()


    //Distance Calculator History
    @Insert
    fun insert_Distance_Calc(note: Distance_Calc_History): Long?

    @Query("SELECT * FROM Distance_Calc_History")
    fun fetch_All_Distance_Calc_History(): LiveData<List<Distance_Calc_History>>


    @Query("DELETE FROM Distance_Calc_History")
    fun delete_Distance_History()


    //Weight Calculator History
    @Insert
    fun insert_Weight_Calc(note: Weight_Calc_History): Long?

    @Query("SELECT * FROM weight_calc_history")
    fun fetch_All_Weight_Calc_History(): LiveData<List<Weight_Calc_History>>


    @Query("DELETE FROM Weight_Calc_History")
    fun delete_Weight_History()



    //Length Calculator History
    @Insert
    fun insert_Length_Calc(note: Length_Calc_History): Long?

    @Query("SELECT * FROM Length_Calc_History")
    fun fetch_All_Length_Calc_History(): LiveData<List<Length_Calc_History>>


    @Query("DELETE FROM Length_Calc_History")
    fun delete_Length_History()


 //Length Volume History
    @Insert
    fun insert_Volume_Calc(note: Volume_Calc_History): Long?

    @Query("SELECT * FROM Volume_Calc_History")
    fun fetch_All_Volume_Calc_History(): LiveData<List<Volume_Calc_History>>


    @Query("DELETE FROM Volume_Calc_History")
    fun delete_Volume_History()


}

