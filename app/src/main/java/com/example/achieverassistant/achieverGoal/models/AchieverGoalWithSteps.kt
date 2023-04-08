package com.example.achieverassistant.achieverGoal.models

import androidx.room.Embedded
import androidx.room.Relation

data class AchieverGoalWithSteps(
    @Embedded val achieverGoal: AchieverGoal,
    @Relation(
        parentColumn = "achieverGoalId",
        entityColumn = "stepId"
    )
    val steps: List<Steps>? = null
)