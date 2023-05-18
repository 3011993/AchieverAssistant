package com.example.achieverassistant.dailyPlan

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.achieverassistant.R
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.databinding.ActivityAddeditdailyTasksBinding
import com.example.achieverassistant.databinding.DailyTasksLayoutBinding
import com.example.achieverassistant.databinding.FragmentAddEditDailyTaskScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date


@AndroidEntryPoint
class AddEditDailyTaskScreen : Fragment() {

    private lateinit var binding: FragmentAddEditDailyTaskScreenBinding

    private val dailyTasksLiveModel by viewModels<DailyTasksLiveModel>()
    private var dailyTasks = DailyTasks("", Date(MAX_TIME_STAMP))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_add_edit_daily_task_screen,container,false)

        binding.buttonSaveDailytask.setOnClickListener { saveDailyTasks(it) }


        binding.edittextTimeCurenttext.isFocusable = false
        binding.edittextCurenttext.isClickable = true
        binding.edittextTimeCurenttext.setOnClickListener { createCalendar() }


        return binding.root
    }

    private fun createCalendar() {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        val tP = TimePickerDialog(
            requireContext(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3,

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

    private fun saveDailyTasks(v : View) {

        val currentTask = binding.edittextCurenttext.text.toString()
        val timeOfTask = binding.edittextTimeCurenttext.text.toString()
        if (currentTask.trim { it <= ' ' }.isEmpty() or timeOfTask.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "please write details of your task!",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        dailyTasks.currentTask = currentTask

        dailyTasksLiveModel.insertDailyTask(dailyTasks)

        v.findNavController().navigate(AddEditDailyTaskScreenDirections.actionAddEditDailyTaskScreenToDailyTasksFragment())


//        val saveData = Intent()
//        saveData.putExtra(EXTRA_DATA_CURRENT_TEXT, currentTask)
//        //need to change to send date not string
//        saveData.putExtra(EXTRA_DATA_TIME_CURRENT_TEXT, timeOfTask)

        //this code for managing ID
//        val id = intent.getIntExtra(EXTRA_DATA_ID_CURRENT_TASK, -1)
//        if (id != -1) {
//            saveData.putExtra(EXTRA_DATA_ID_CURRENT_TASK, id)
//        }
//        setResult(FragmentActivity.RESULT_OK, saveData)
//        finish()
    }





    companion object {
        //Variables For Intent and Save Data
        const val EXTRA_DAILY_TASK_ALARM = "com.mooth.achieverassistant.dailytasks.alarm"
        const val EXTRA_DATA_ID_CURRENT_TASK = "com.mooth.achieverassistant.dailytasks.idtask"
        const val EXTRA_DATA_CURRENT_TEXT = "com.mooth.achieverassistant.dailytasks.currenttext"
        const val EXTRA_DATA_TIME_CURRENT_TEXT = "com.mooth.achieverassistant.dailytasks.timetask"

    }

}