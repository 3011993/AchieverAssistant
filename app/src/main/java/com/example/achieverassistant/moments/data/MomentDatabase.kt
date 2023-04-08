package com.example.achieverassistant.moments.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TheMoment::class], version = 1, exportSchema = false)
abstract class MomentDatabase : RoomDatabase() {
    abstract fun momentDAO(): MomentDAO

}





