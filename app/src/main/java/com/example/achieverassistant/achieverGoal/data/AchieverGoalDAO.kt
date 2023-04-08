package com.example.achieverassistant.achieverGoal.data

import androidx.room.*
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.AchieverGoalWithSteps
import com.example.achieverassistant.achieverGoal.models.Steps
import kotlinx.coroutines.flow.Flow

@Dao
interface AchieverGoalDAO {
    @Insert
    fun insertGoal(achieverGoal: AchieverGoal)

    @Update
    fun updateGoal(achieverGoal: AchieverGoal)

    @Delete
    fun deleteGoal(achieverGoal: AchieverGoal)

    @Query("DELETE FROM AchieverGoal")
    fun deleteAll()

    @Query("SELECT * FROM AchieverGoal")
    fun getAllGoals(): Flow<List<AchieverGoal>>

    @Transaction
    @Query("SELECT * FROM AchieverGoal WHERE achieverGoalId == :id")
    suspend fun getAllGoals(id: Int): List<AchieverGoalWithSteps>


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

