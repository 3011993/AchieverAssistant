package com.example.achieverassistant.dailyPlan

import androidx.room.TypeConverter
import java.util.*

class DateConverters {
    @TypeConverter
    fun fromTimeStamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimeStamp(date: Date): Long = date.time
}