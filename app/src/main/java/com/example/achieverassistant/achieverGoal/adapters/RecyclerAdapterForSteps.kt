package com.example.achieverassistant.achieverGoal.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.achieverassistant.R
import com.example.achieverassistant.achieverGoal.interfaces.ItemListenerInterface

import com.example.achieverassistant.achieverGoal.models.Steps
import com.example.achieverassistant.databinding.CardViewStepsBinding
import java.util.stream.Collectors

class RecyclerAdapterForSteps(private val itemListenerInterface: ItemListenerInterface) : ListAdapter<Steps,RecyclerAdapterForSteps.StepsViewHolder>(
    DiffCallBackStep), ItemListenerInterface{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder.from(parent,this)
    }



    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val currentStep = getItem(position)
        holder.bind(currentStep)

    }

    override fun editStep(position: Int) {
        Log.i("child","edit step clicked")
        itemListenerInterface.editStep(position)

    }

    override fun deleteStep(position: Int) {

        currentList.removeAt(position)
        itemListenerInterface.deleteStep(position)
        Log.i("child","delete step clicked")

    }

    class StepsViewHolder(val binding : CardViewStepsBinding,private val itemListenerInterface: ItemListenerInterface) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {


        fun bind(step: Steps){

            binding.userStep.text = step.step
            binding.executePendingBindings()


            binding.editStepButton.setOnClickListener(this)
            binding.deleteStepButton.setOnClickListener(this)




        }
        companion object{
            fun from(viewGroup: ViewGroup,itemListenerInterface: ItemListenerInterface): StepsViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = CardViewStepsBinding.inflate(layoutInflater, viewGroup, false)
                return StepsViewHolder(binding,itemListenerInterface)
            }

        }

        override fun onClick(v: View?) {
            when(v?.id){
                R.id.edit_step_button -> itemListenerInterface.editStep(absoluteAdapterPosition)
                R.id.delete_step_button -> itemListenerInterface.deleteStep(absoluteAdapterPosition)
            }
        }


    }

    companion object {
        private val DiffCallBackStep: DiffUtil.ItemCallback<Steps> =
            object : DiffUtil.ItemCallback<Steps>() {
                override fun areItemsTheSame(
                    oldStep: Steps,
                    newStep: Steps
                ): Boolean {
                    return oldStep.stepId == newStep.stepId
                }

                override fun areContentsTheSame(
                    oldStep: Steps,
                    newStep: Steps
                ): Boolean {
                    return oldStep.step == newStep.step
                }
            }
    }



}