package com.example.achieverassistant.achieverGoal.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Steps (@PrimaryKey(autoGenerate = true) var stepId : Int = 0 , var step : String) : Parcelable