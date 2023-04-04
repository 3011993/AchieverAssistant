package com.example.achieverassistant.achieverGoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.achieverassistant.R
import android.content.Intent

import android.widget.Toast

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.achieverassistant.databinding.ActivityAddeditgoalBinding

class AddEditGoal : AppCompatActivity() {



    private lateinit var binding : ActivityAddeditgoalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_addeditgoal)

        binding.setGoalButton.setOnClickListener {  setButtonMethod() }


        //responsible for get intent and switch  between Add Task Or Edit task
        val intent = intent
        if (intent.hasExtra(EXTRA_DATA_ID_GOAL)) {
            title = "Change your Goal"
            binding.edittextGoal.isEnabled = false
            binding.edittextGoal.setText(intent.getStringExtra(EXTRA_DATA_GOAL))
            binding.edittextDuration.setText(intent.getStringExtra(EXTRA_DATA_DURATION_GOAl))
            binding.edittextFirstStep.setText(intent.getStringExtra(EXTRA_DATA_STEP_GOAl))
        } else {
            title = "Add your Goal"
        }
    }

    private fun setButtonMethod() {
        val goal = binding.edittextGoal.text.toString()
        val durationGoal = binding.edittextDuration.text.toString()
        val stepsGoal = binding.edittextFirstStep.text.toString()
        if (goal.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(applicationContext, "Hey Achiever Set your Goal", Toast.LENGTH_LONG)
                .show()
            return
        }
        val saveGoal = Intent()
        saveGoal.putExtra(EXTRA_DATA_GOAL, goal)
        saveGoal.putExtra(EXTRA_DATA_DURATION_GOAl, durationGoal)
        saveGoal.putExtra(EXTRA_DATA_STEP_GOAl, stepsGoal)

        //this code for managing ID
        val id = intent.getIntExtra(EXTRA_DATA_ID_GOAL, -1)
        if (id != -1) {
            saveGoal.putExtra(EXTRA_DATA_ID_GOAL, id)
        }
        setResult(RESULT_OK, saveGoal)
        finish()
    }

    companion object {
        const val EXTRA_DATA_ID_GOAL = "com.mooth.achieverassistant.achievergoal.idgoal"
        const val EXTRA_DATA_GOAL = "com.mooth.achieverassistant.achievergoal.goal"
        const val EXTRA_DATA_DURATION_GOAl = "com.mooth.achieverassistant.achievergoal.durationgoal"
        const val EXTRA_DATA_STEP_GOAl = "com.mooth.achieverassistant.achievergoal.stepgoal"
    }
}