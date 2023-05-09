package com.example.achieverassistant.dailyPlan

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.dailyPlan.data.DailyTasksDatabase
import com.example.achieverassistant.dailyPlan.receivers.AlarmReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DailyTasksLiveModel @Inject constructor(
    private val database: DailyTasksDatabase,
    application: Application
) :
    AndroidViewModel(application) {


    private val context = getApplication<Application>().applicationContext as Application

    private val _allDailyTasks = MutableLiveData<List<DailyTasks>>()

    val allDailyTasks: LiveData<List<DailyTasks>>
        get() = _allDailyTasks

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getALlDailyTasks()
        }
    }


    fun insertDailyTask(dailyTasks: DailyTasks) {
        viewModelScope.launch(Dispatchers.IO) {
            database.dailyDAO().insert(dailyTasks)
            startAlarm(dailyTasks)
        }

    }

    fun updateDailyTask(dailyTasks: DailyTasks) {
        viewModelScope.launch(Dispatchers.IO) {
            database.dailyDAO().update(dailyTasks)
        }
    }

    fun deleteDailyTask(dailyTasks: DailyTasks) {
        viewModelScope.launch(Dispatchers.IO) {
            database.dailyDAO().delete(dailyTasks)
            cancelNotification(dailyTasks)
        }

    }

    fun deleteAllDailyTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            database.dailyDAO().deleteAll()
        }
    }

    private fun getALlDailyTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            database.dailyDAO().getAllTasks().collect() {
                _allDailyTasks.postValue(it)
            }

        }
    }

    private fun startAlarm(dailyTasks: DailyTasks) {
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(ADDEDITDailyTasks.EXTRA_DAILY_TASK_ALARM, dailyTasks)
        val pendingIntent = PendingIntent.getBroadcast(
            context, dailyTasks.id, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
        )


        val infoClock = AlarmManager.AlarmClockInfo(dailyTasks.currentTextTime.time, pendingIntent)
        alarmManager.setAlarmClock(infoClock, pendingIntent)
    }

    //we use it when the user delete task to cancel alarm cause we don't need to remind him for deleted task
    private fun cancelNotification(dailyTask: DailyTasks) {
        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, dailyTask.id, intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
            )
        alarmService.cancel(pendingIntent)

    }


    class DailyTasksFactory @Inject constructor(val database: DailyTasksDatabase, val app: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DailyTasksLiveModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DailyTasksLiveModel(database, app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}

