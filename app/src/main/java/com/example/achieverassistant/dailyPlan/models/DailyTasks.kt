package com.example.achieverassistant.dailyPlan.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity
@Parcelize
data class DailyTasks(var currentTask: String, var currentTextTime: Date) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}