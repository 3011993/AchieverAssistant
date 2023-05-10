package com.example.achieverassistant.achieverGoal.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.achieverassistant.R
import com.example.achieverassistant.achieverGoal.AchieverGoalViewModel
import com.example.achieverassistant.achieverGoal.models.Steps
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogShowNewStep : AppCompatDialogFragment() {

    private val achieverGoalViewModel by viewModels<AchieverGoalViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(activity)
        val layoutInflater = requireActivity().layoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_newstep, null)

        val editText = view.findViewById<EditText>(R.id.edittext_newstep)


        builder.setView(view).setTitle("Add New Step")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dismiss()
            })
            .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                if (editText.text != null) {
                    val step = editText.text.toString()
                    achieverGoalViewModel.updateStep(Steps(step))
                    Toast.makeText(context, "Step Added", Toast.LENGTH_SHORT).show()
                }
            })



        return builder.create()
    }

}