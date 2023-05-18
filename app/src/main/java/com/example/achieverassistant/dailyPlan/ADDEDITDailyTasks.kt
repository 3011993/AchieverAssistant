package com.example.achieverassistant.dailyPlan

import android.os.Bundle
import com.example.achieverassistant.R
import android.content.Intent
import android.app.TimePickerDialog

import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import android.graphics.Color
import androidx.activity.viewModels

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.databinding.ActivityAddeditdailyTasksBinding

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ADDEDITDailyTasks : FragmentActivity() {

    private lateinit var binding: ActivityAddeditdailyTasksBinding

    private val dailyTasksLiveModel by viewModels<DailyTasksLiveModel>()
    private var dailyTasks = DailyTasks("", Date(MAX_TIME_STAMP))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addeditdaily_tasks)

        binding.buttonSaveDailytask.setOnClickListener { saveDailyTasks() }


        binding.edittextTimeCurenttext.isFocusable = false
        binding.edittextCurenttext.isClickable = true
        binding.edittextTimeCurenttext.setOnClickListener { createCalendar() }

        if (intent.hasExtra(EXTRA_DATA_ID_CURRENT_TASK)) {
            title = "Edit Your Task"
            val currentText = intent.getStringExtra(EXTRA_DATA_CURRENT_TEXT)
            val currentTime = intent.getSerializableExtra(EXTRA_DATA_TIME_CURRENT_TEXT) as Date
            val timeString = convertDateToString(currentTime)
            binding.edittextCurenttext.setText(currentText)
            binding.edittextTimeCurenttext.setText(timeString)
        } else {
            title = "Add New Task"
        }
    }


    //methods for edit text for time onclickListener
    private fun createCalendar() {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        val tP = TimePickerDialog(
            this,
            com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Spinner,

            { view, hourOfDay, minute ->
                val c = Calendar.getInstance()
                c[Calendar.HOUR_OF_DAY] = hourOfDay
                c[Calendar.MINUTE] = minute
                c[Calendar.SECOND] = 0
                //this for show am or pm
                val AM_PM: String = if (hourOfDay < 12) {
                    "AM"
                } else {
                    "PM"
                }
                // this for show hours in 12 hours mode
                val hours = if (c[Calendar.HOUR] == 0) "12" else Integer.toString(
                    c[Calendar.HOUR]
                )
                dailyTasks.currentTextTime = c.time
                //this is make edittext get the time
                binding.edittextTimeCurenttext.setText("$hours:$minute $AM_PM")
                //this for start alarm
            },
            hour,
            minute,
            false
        )

        tP.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tP.show()
    }

    //methods for save data for Button Save onclickListener
    private fun saveDailyTasks() {

        val currentTask = binding.edittextCurenttext.text.toString()
        val timeOfTask = binding.edittextTimeCurenttext.text.toString()
        if (currentTask.trim { it <= ' ' }.isEmpty() or timeOfTask.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "please write details of your task!",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        dailyTasks.currentTask = currentTask




        val saveData = Intent()
        saveData.putExtra(EXTRA_DATA_CURRENT_TEXT, dailyTasks.currentTask)
        //need to change to send date not string
        saveData.putExtra(EXTRA_DATA_TIME_CURRENT_TEXT, dailyTasks.currentTextTime)

        //this code for managing ID
        val id = intent.getIntExtra(EXTRA_DATA_ID_CURRENT_TASK, -1)
        if (id != -1) {
            saveData.putExtra(EXTRA_DATA_ID_CURRENT_TASK, id)
        }
        setResult(RESULT_OK, saveData)
        finish()
    }


    companion object {
        //Variables For Intent and Save Data
        const val EXTRA_DAILY_TASK_ALARM = "com.mooth.achieverassistant.dailytasks.alarm"
        const val EXTRA_DATA_ID_CURRENT_TASK = "com.mooth.achieverassistant.dailytasks.idtask"
        const val EXTRA_DATA_CURRENT_TEXT = "com.mooth.achieverassistant.dailytasks.currenttext"
        const val EXTRA_DATA_TIME_CURRENT_TEXT = "com.mooth.achieverassistant.dailytasks.timetask"

    }
}