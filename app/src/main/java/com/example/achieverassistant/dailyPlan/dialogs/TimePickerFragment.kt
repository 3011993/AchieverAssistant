package com.example.achieverassistant.dailyPlan.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import java.util.Calendar

class TimePickerFragment(val dailyTasks: DailyTasks) : DialogFragment(),TimePickerDialog.OnTimeSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        val seconds = c.get(Calendar.SECOND)

        dailyTasks.currentTextTime = c.time

        return TimePickerDialog(context,this,hour,minute,false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {


    }
}