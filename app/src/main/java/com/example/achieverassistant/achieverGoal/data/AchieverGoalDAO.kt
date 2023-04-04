package com.example.achieverassistant.achieverGoal.data

import androidx.room.*
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
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
    }

