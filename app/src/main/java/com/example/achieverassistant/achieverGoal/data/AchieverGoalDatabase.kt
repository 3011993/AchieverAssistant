package com.example.achieverassistant.achieverGoal.data

import android.content.Context
import androidx.room.*

import com.example.achieverassistant.achieverGoal.StepsConverter
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.Steps

@Database(entities = [AchieverGoal::class, Steps::class], version = 1, exportSchema = false)
@TypeConverters(StepsConverter::class)
abstract class AchieverGoalDatabase : RoomDatabase() {

    abstract fun achieverGoalDAO(): AchieverGoalDAO

}

