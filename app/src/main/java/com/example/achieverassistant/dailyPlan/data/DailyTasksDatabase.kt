package com.example.achieverassistant.dailyPlan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.achieverassistant.dailyPlan.DateConverters
import com.example.achieverassistant.dailyPlan.models.DailyTasks

@Database(entities = [DailyTasks::class], version = 2, exportSchema = false)
abstract class DailyTasksDatabase : RoomDatabase() {

    abstract fun dailyDAO(): DailyDAO

    }

private lateinit var INSTANCE : DailyTasksDatabase


fun getDatabaseDailyDatabase(context: Context) : DailyTasksDatabase {
    synchronized(DailyTasksDatabase::class){
        if (!::INSTANCE.isInitialized){
            INSTANCE = databaseBuilder(context.applicationContext,
                DailyTasksDatabase::class.java,"dailyTasksDatabase").
            fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

