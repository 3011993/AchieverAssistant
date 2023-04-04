package com.example.achieverassistant.dailyPlan.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.achieverassistant.dailyPlan.DailyTasksLiveModel
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.dailyPlan.data.getDatabaseDailyDatabase

class DialogForDeleteTask(val dailyTasks: DailyTasks) : DialogFragment() {


    private val dailyTasksViewModel by viewModels<DailyTasksLiveModel> {
        val database = getDatabaseDailyDatabase(requireActivity().application)
        DailyTasksLiveModel.DailyTasksFactory(database,requireActivity().application)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val alert = AlertDialog.Builder(activity)
        alert.setMessage("are you sure to delete this task?")
        alert.setCancelable(true)
        alert.setTitle("Delete your task")
        alert.setPositiveButton("yes") { dialog, which ->
            dailyTasksViewModel.deleteDailyTask(dailyTasks)
            Toast.makeText(activity, "Task is Deleted", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
            Toast.makeText(activity, "Task is Not Deleted", Toast.LENGTH_SHORT).show()
            //here to observe daily tasks
        }
        return  alert.create()
            }
}