package com.example.achieverassistant.dailyPlan

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.SyncStateContract
import androidx.core.app.NotificationCompat
import com.example.achieverassistant.MainActivity
import com.example.achieverassistant.R
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.dailyPlan.receivers.ActionReceiver
import java.text.SimpleDateFormat
import java.util.*

const val channelDailyNotificationID = "Daily Task Notification ID"
const val channelDailyNotificationName = "Daily Task Notifications"
const val CODE_REQUEST_DAILY_NOTIFICATION_ID = 202
const val NOTIFICATION_DAILY_ID = 77

const val MAX_TIME_STAMP = 8640000000000000


fun convertDateToString(date: Date): String {
    val format1 = "hh:mm aaa"
    val format2 = "hh:mm aaa"
    val dateInfinity = Date(MAX_TIME_STAMP)
    return if (dateInfinity.compareTo(date) == 0) "N/A"
    else if (date.seconds == 0) {
        val df = SimpleDateFormat(format1)
        df.format(date)
    } else {
        val df = SimpleDateFormat(format2)
        df.format(date)
    }
}


fun NotificationManager.sendNotification(dailyTask: DailyTasks, context: Context) {

    val actionIntent = Intent(context, ActionReceiver::class.java)
    val actionPending = PendingIntent.getBroadcast(
        context, 88, actionIntent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
    )


    val intent = Intent(context, MainActivity::class.java)
    val contentIntent =
        PendingIntent.getActivity(
            context, NOTIFICATION_DAILY_ID, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
        )

    val builder = NotificationCompat.Builder(context, channelDailyNotificationID)
        .setContentTitle("Achiever!")
        .setContentText("Here We are Go ;)")
        .setSmallIcon(R.drawable.ic_favorite_border_black_24dp)
        .setAutoCancel(true)
        .setContentIntent(contentIntent)
        .addAction(R.drawable.ic_baseline_done_24, "task completed!", actionPending)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


    notify(dailyTask.id, builder.build())

}

fun NotificationManager.cancelAllNotifications() {
    cancelAll()
}