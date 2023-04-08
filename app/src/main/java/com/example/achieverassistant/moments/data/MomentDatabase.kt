package com.example.achieverassistant.moments.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.Room
import com.example.achieverassistant.moments.data.MomentDAO

@Database(entities = [TheMoment::class], version = 1, exportSchema = false)
abstract class MomentDatabase : RoomDatabase() {
    abstract fun momentDAO(): MomentDAO

    }

private lateinit var INSTANCE : MomentDatabase

fun getMomentsDatabase(context: Context) : MomentDatabase {
    synchronized(MomentDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(
                    context.applicationContext,
                    MomentDatabase::class.java,
                    "MomentsDatabase"
                ).build()
        }
        return INSTANCE
    }
}





