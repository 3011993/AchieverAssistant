package com.example.achieverassistant.dailyPlan.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.achieverassistant.dailyPlan.ADDEDITDailyTasks
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.dailyPlan.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {

        val dailyTasks =
            intent.getParcelableExtra(ADDEDITDailyTasks.EXTRA_DAILY_TASK_ALARM,DailyTasks::class.java) as DailyTasks
        val notificationManager =
            ContextCompat.getSystemService(context,
                NotificationManager::class.java) as NotificationManager

        notificationManager.sendNotification(dailyTasks,context)
    }


}