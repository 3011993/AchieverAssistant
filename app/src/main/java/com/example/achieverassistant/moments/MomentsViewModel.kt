package com.example.achieverassistant.moments

import android.app.Application
import androidx.lifecycle.*
import com.example.achieverassistant.moments.data.MomentDatabase
import com.example.achieverassistant.moments.data.TheMoment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MomentsViewModel @Inject constructor(private val database: MomentDatabase, application: Application) :
    AndroidViewModel(application) {


    private val _allMoments = MutableLiveData<List<TheMoment>>()

    val allMoments: LiveData<List<TheMoment>> = _allMoments

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMoments()
        }
    }

    fun insertMoment(theMoment: TheMoment) {
        viewModelScope.launch(Dispatchers.IO) {
            database.momentDAO().insertMoment(theMoment)
        }
    }

    fun deleteMoment(theMoment: TheMoment) {
        viewModelScope.launch(Dispatchers.IO) {
            database.momentDAO().deleteMoment(theMoment)
        }
    }

    fun updateMoment(theMoment: TheMoment) {
        viewModelScope.launch(Dispatchers.IO) {
            database.momentDAO().updateMoment(theMoment)
        }
    }

    fun deleteAllMoments() {
        viewModelScope.launch(Dispatchers.IO) {

            database.momentDAO().deleteAllMoments()

        }
    }

    private fun getAllMoments() {
        viewModelScope.launch(Dispatchers.IO) {
            database.momentDAO().getAllMoments().collect {
                _allMoments.postValue(it)
            }
        }
    }

    class MomentsFactory(val database: MomentDatabase, val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MomentsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MomentsViewModel(database,app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}