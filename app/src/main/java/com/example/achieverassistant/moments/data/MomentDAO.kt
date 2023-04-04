package com.example.achieverassistant.moments.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MomentDAO {
    @Insert
    fun insertMoment(theMoment: TheMoment?)

    @Update
    fun updateMoment(theMoment: TheMoment?)

    @Delete
    fun deleteMoment(theMoment: TheMoment?)

    @Query("DELETE FROM moments_table")
    fun deleteAllMoments()

    @Query("SELECT * FROM moments_table")
    fun getAllMoments(): Flow<List<TheMoment>>
}