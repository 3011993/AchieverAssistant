package com.example.achieverassistant.dailyPlan.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.achieverassistant.dailyPlan.DailyTasksLiveModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogForDeleteAllTasks : DialogFragment() {


    private val dailyTasksViewModel by viewModels<DailyTasksLiveModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alert = AlertDialog.Builder(context)
        alert.setMessage("are you sure to delete All tasks?")
        alert.setCancelable(true)
        alert.setTitle("Delete All tasks")
        alert.setPositiveButton("yes") { dialog, which ->
            dailyTasksViewModel.deleteAllDailyTasks()
            Toast.makeText(context, "All Tasks are Deleted!", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
            Toast.makeText(context, "Task is Not Deleted", Toast.LENGTH_SHORT).show()
            dailyTasksViewModel.allDailyTasks.value
        }

        return alert.create()
    }
}