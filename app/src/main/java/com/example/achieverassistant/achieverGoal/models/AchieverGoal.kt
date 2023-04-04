package com.example.achieverassistant.achieverGoal.models


import android.os.Parcelable
import androidx.room.Embedded

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AchieverGoal(
    @PrimaryKey(autoGenerate = true) var achieverGoalId: Int = 0,
    var achieverGoal: String,
    var achieverGoalDuration: String,
    val steps : ArrayList<Steps?>?

) : Parcelable {

}




