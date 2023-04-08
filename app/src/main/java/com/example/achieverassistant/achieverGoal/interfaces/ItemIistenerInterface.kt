package com.example.achieverassistant.achieverGoal.interfaces

import com.example.achieverassistant.achieverGoal.models.Steps

interface ItemListenerInterface {
    fun editStep(position: Int)
    fun deleteStep(position: Int)

}