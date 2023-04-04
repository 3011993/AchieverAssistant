package com.example.achieverassistant.achieverGoal.data

import androidx.room.*
import com.example.achieverassistant.achieverGoal.models.Steps
import kotlinx.coroutines.flow.Flow


@Dao
interface StepsDao {
    @Insert
    fun insertStep(steps: Steps)

    @Update
    fun updateStep(steps: Steps)

    @Delete
    fun deleteStep(steps: Steps)

    @Query("DELETE FROM Steps")
    fun deleteAllSteps()

    @Query("SELECT * FROM Steps")
    fun getAllSteps(): Flow<List<Steps>>

}