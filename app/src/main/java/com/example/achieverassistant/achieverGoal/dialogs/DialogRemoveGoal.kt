package com.example.achieverassistant.achieverGoal.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.achieverassistant.achieverGoal.AchieverGoalViewModel
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRemoveGoal(val achieverGoal: AchieverGoal) : DialogFragment() {

    private val achieverGoalViewModel by viewModels<AchieverGoalViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alert = AlertDialog.Builder(activity)
        alert.setMessage("are you sure to delete this task?")
        alert.setCancelable(true)
        alert.setTitle("Delete your task")
        alert.setPositiveButton("yes") { dialog, which ->
            achieverGoalViewModel.deleteGoal(achieverGoal)
            Toast.makeText(activity, "Task is Deleted", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
            Toast.makeText(activity, "Task is Not Deleted", Toast.LENGTH_SHORT).show()
            //here to observe daily tasks
        }
        return alert.create()
    }

}
