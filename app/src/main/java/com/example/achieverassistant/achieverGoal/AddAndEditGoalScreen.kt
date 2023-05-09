package com.example.achieverassistant.achieverGoal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.achieverassistant.R
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.databinding.ActivityAddeditgoalBinding
import com.example.achieverassistant.databinding.FragmentAddAndEditGoalScreenBinding
import com.example.achieverassistant.databinding.FragmentAddEditDailyTaskScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAndEditGoalScreen : Fragment() {


    private lateinit var binding: FragmentAddAndEditGoalScreenBinding

    private val achieverGoalViewModel by viewModels<AchieverGoalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_and_edit_goal_screen,container,false)


        binding.setGoalButton.setOnClickListener { setButtonMethod() }


        //responsible for get intent and switch  between Add Task Or Edit task
//        val intent = intent
//        if (intent.hasExtra(AddEditGoal.EXTRA_DATA_ID_GOAL)) {
//            title = "Change your Goal"
//            binding.edittextGoal.isEnabled = false
//            binding.edittextGoal.setText(intent.getStringExtra(AddEditGoal.EXTRA_DATA_GOAL))
//            binding.edittextDuration.setText(intent.getStringExtra(AddEditGoal.EXTRA_DATA_DURATION_GOAl))
//            binding.edittextFirstStep.setText(intent.getStringExtra(AddEditGoal.EXTRA_DATA_STEP_GOAl))
//        } else {
//            title = "Add your Goal"
//        }


        return binding.root
    }

    private fun setButtonMethod() {
        val goal = binding.edittextGoal.text.toString()
        val durationGoal = binding.edittextDuration.text.toString()
        val stepsGoal = binding.edittextFirstStep.text.toString()
        if (goal.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(requireContext(), "Hey Achiever Set your Goal", Toast.LENGTH_LONG)
                .show()
            return
        }


        val achieverGoal = AchieverGoal(achieverGoal = goal, achieverGoalDuration = durationGoal)
        achieverGoalViewModel.insertGoal(achieverGoal)
        Toast.makeText(requireContext(), "Goal Added", Toast.LENGTH_SHORT).show()


//        val saveGoal = Intent()
//        saveGoal.putExtra(EXTRA_DATA_GOAL, goal)
//        saveGoal.putExtra(EXTRA_DATA_DURATION_GOAl, durationGoal)
//        saveGoal.putExtra(EXTRA_DATA_STEP_GOAl, stepsGoal)
//
//        //this code for managing ID
//        val id = intent.getIntExtra(EXTRA_DATA_ID_GOAL, -1)
//        if (id != -1) {
//            saveGoal.putExtra(EXTRA_DATA_ID_GOAL, id)
//        }
//        setResult(AppCompatActivity.RESULT_OK, saveGoal)
//        finish()
    }

    companion object {
        const val EXTRA_DATA_ID_GOAL = "com.mooth.achieverassistant.achievergoal.idgoal"
        const val EXTRA_DATA_GOAL = "com.mooth.achieverassistant.achievergoal.goal"
        const val EXTRA_DATA_DURATION_GOAl = "com.mooth.achieverassistant.achievergoal.durationgoal"
        const val EXTRA_DATA_STEP_GOAl = "com.mooth.achieverassistant.achievergoal.stepgoal"
    }

}