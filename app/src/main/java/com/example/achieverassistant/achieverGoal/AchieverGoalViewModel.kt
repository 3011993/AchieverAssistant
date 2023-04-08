package com.example.achieverassistant.achieverGoal


import android.app.Application
import androidx.lifecycle.*
import com.example.achieverassistant.achieverGoal.data.AchieverGoalDatabase
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.Steps
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class AchieverGoalViewModel(private val database: AchieverGoalDatabase, application: Application) :
    AndroidViewModel(application) {


    private val _achieverGoals = MutableLiveData<List<AchieverGoal>>()

    val achieverGoals: LiveData<List<AchieverGoal>>
        get() = _achieverGoals

    private val _allSteps = MutableLiveData<List<Steps>>()

    val allSteps: LiveData<List<Steps>>
        get() = _allSteps

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllGoals()
            getAllSteps()
        }
    }


    fun insertGoal(achieverGoal: AchieverGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().insertGoal(achieverGoal)
        }
    }

    fun deleteGoal(achieverGoal: AchieverGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().deleteGoal(achieverGoal)
        }
    }

    fun updateGoal(achieverGoal: AchieverGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().updateGoal(achieverGoal)
        }
    }

    fun deleteAllGoals() {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().deleteAll()
        }
    }

    private fun getAllGoals() {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().getAllGoals().collect {
                _achieverGoals.postValue(it)
            }
        }

    }

    fun insertStep(steps: Steps) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().insertStep(steps)
        }
    }

    fun deleteStep(steps: Steps) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().deleteStep(steps)
        }
    }

    fun updateStep(steps: Steps) {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().updateStep(steps)
        }
    }

    fun deleteAllSteps() {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().deleteAllSteps()
        }
    }

    private fun getAllSteps() {
        viewModelScope.launch(Dispatchers.IO) {
            database.achieverGoalDAO().getAllSteps().collect {
                _allSteps.postValue(it)
            }
        }

    }


    //this function for open dialog and get new step


    class AchieverGoalFactory(val database: AchieverGoalDatabase, val app: Application) :
        ViewModelProvider.Factory {

        //maybe will be something wrong here
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AchieverGoalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AchieverGoalViewModel(database, app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}

