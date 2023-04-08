package com.example.achieverassistant.achieverGoal.data

import android.content.Context
import androidx.room.*

import com.example.achieverassistant.achieverGoal.StepsConverter
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.Steps

@Database(entities = [AchieverGoal::class, Steps::class], version = 4, exportSchema = false)
@TypeConverters(StepsConverter::class)
abstract class AchieverGoalDatabase : RoomDatabase() {

    abstract fun achieverGoalDAO(): AchieverGoalDAO

}

private lateinit var INSTANCE: AchieverGoalDatabase

fun getAchieverGoalsDatabase(context: Context): AchieverGoalDatabase {
    synchronized(AchieverGoalDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AchieverGoalDatabase::class.java, "achieverGoalDatabase"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

