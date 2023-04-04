package com.example.achieverassistant.achieverGoal

import androidx.room.TypeConverter
import com.example.achieverassistant.achieverGoal.models.Steps
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StepsConverter {

    @TypeConverter
    fun stepsToString(value : ArrayList<Steps?>?) : String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToSteps(value: String): ArrayList<Steps?>? {
        val listType = object : TypeToken<ArrayList<Steps?>?>(){}.type
        return Gson().fromJson(value,listType)    }
}