package com.example.achieverassistant.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.achieverassistant.R
import com.example.achieverassistant.achieverGoal.*
import com.example.achieverassistant.achieverGoal.adapters.RecyclerAdapterForAchieverGoal
import com.example.achieverassistant.achieverGoal.dialogs.DialogRemoveGoal
import com.example.achieverassistant.achieverGoal.dialogs.DialogShowNewStep
import com.example.achieverassistant.achieverGoal.interfaces.ItemListenerInterface
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.Steps
import com.example.achieverassistant.databinding.AchieverGoalsLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AchieverGoalsFragment : Fragment(), ItemListenerInterface {


    private lateinit var recyclerAdapterForAchieverGoal: RecyclerAdapterForAchieverGoal
    private lateinit var binding: AchieverGoalsLayoutBinding


    private val achieverGoalViewModel by viewModels<AchieverGoalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.achiever_goals_layout, container, false)


        binding.buttonAddNewGoal.setOnClickListener {
            val intent = Intent(activity, AddEditGoal::class.java)
            startActivity(intent)
        }

        //use this function to submit list inside child recycler view
        val clickListener =
            RecyclerAdapterForAchieverGoal.OnAchieverGoalListener({
                val showNewStep = DialogShowNewStep()
                showNewStep.show(childFragmentManager, "add new Step dialog")


            }, { achieverGoal ->
                val intent = Intent(activity, AddEditGoal::class.java)
                intent.putExtra(getString(R.string.requestCodeAchiever), edit_goal_request)
                intent.putExtra(AddEditGoal.EXTRA_DATA_ID_GOAL, achieverGoal.achieverGoalId)
                intent.putExtra(AddEditGoal.EXTRA_DATA_GOAL, achieverGoal.achieverGoal)
                intent.putExtra(
                    AddEditGoal.EXTRA_DATA_DURATION_GOAl,
                    achieverGoal.achieverGoalDuration
                )
                //intent.putExtra(AddEditGoal.EXTRA_DATA_STEP_GOAl, achieverGoal.steps)
                editActivityLauncher.launch(intent)

            }, { achieverGoal ->
                Toast.makeText(requireContext(), "DeleteGoal", Toast.LENGTH_SHORT).show()
                val dialogRemoveGoal = DialogRemoveGoal(achieverGoal)
                dialogRemoveGoal.show(childFragmentManager, "Delete Goal!")

            })

        recyclerAdapterForAchieverGoal = RecyclerAdapterForAchieverGoal(
            clickListener,
            this@AchieverGoalsFragment
        )

        binding.recyclerAchieverGoal.adapter = recyclerAdapterForAchieverGoal
        binding.lifecycleOwner = this
        binding.viewModel = achieverGoalViewModel

        achieverGoalViewModel.achieverGoals.observe(viewLifecycleOwner) {
            recyclerAdapterForAchieverGoal.submitList(it)
        }


        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.achiever_goal_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.deleteAllGoals -> {
                        achieverGoalViewModel.deleteAllGoals()
                    }
                }
                return true
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)


        return binding.root
    }



    private val editActivityLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.data == null) {
                return@registerForActivityResult
            } else {
                val data = result.data
                val requestGoalEdit = result.data!!.getIntExtra(
                    getString(R.string.requestCodeAchiever),
                    edit_goal_request
                )
                if (result.resultCode == requestGoalEdit && result.resultCode == Activity.RESULT_OK) {
                    val id = data!!.getIntExtra(AddEditGoal.EXTRA_DATA_ID_GOAL, -1)
                    if (id == -1) {
                        Toast.makeText(activity, "Goal Not Added", Toast.LENGTH_SHORT).show()
                    }
                    val goal = data.getStringExtra(AddEditGoal.EXTRA_DATA_GOAL).toString()
                    val durationGoal =
                        data.getStringExtra(AddEditGoal.EXTRA_DATA_DURATION_GOAl).toString()
                    val stepGoal = data.getStringExtra(AddEditGoal.EXTRA_DATA_STEP_GOAl).toString()
                    val list = ArrayList<Steps?>()
                    val achieverGoal = AchieverGoal(
                        achieverGoal = goal,
                        achieverGoalDuration = durationGoal,
                        steps = list
                    )
                    achieverGoal.achieverGoalId = id
                    achieverGoalViewModel.updateGoal(achieverGoal)
                    Toast.makeText(activity, "Goal Edited", Toast.LENGTH_SHORT).show()
                }
            }
        }


    companion object {
        const val edit_goal_request = 22
    }

    override fun editStep(position: Int) {
        Log.i("nested", "Edit Step From Achiever Fragment")

    }

    override fun deleteStep(position: Int) {
        Log.i("nested", "delete Step From Achiever Fragment")
    }
}