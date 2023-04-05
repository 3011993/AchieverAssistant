package com.example.achieverassistant.achieverGoal.adapters


import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.achieverassistant.achieverGoal.AchieverGoalViewModel
import com.example.achieverassistant.achieverGoal.interfaces.ItemListenerInterface
import com.example.achieverassistant.achieverGoal.models.AchieverGoal
import com.example.achieverassistant.achieverGoal.models.Steps
import com.example.achieverassistant.databinding.CardviewLifegoalBinding

class RecyclerAdapterForAchieverGoal(val clickListener: OnAchieverGoalListener,
                                     val itemListenerInterface: ItemListenerInterface) :
    ListAdapter<AchieverGoal, RecyclerAdapterForAchieverGoal.ViewHolder>(DiffCallBack) {




    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
       return ViewHolder.from(viewGroup,itemListenerInterface)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achieverGoal = getItem(position)
        holder.bind(clickListener, achieverGoal)
    }


    fun getItemAt(position: Int): AchieverGoal {
        return getItem(position)
    }


    class ViewHolder(private val binding: CardviewLifegoalBinding, val itemListenerInterface: ItemListenerInterface) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnAchieverGoalListener, achieverGoal: AchieverGoal) {

            binding.achieverGoal = achieverGoal
            binding.textviewLifeGoal.text = achieverGoal.achieverGoal
            binding.textviewDurationAchiever.text = achieverGoal.achieverGoalDuration


            val adapter = RecyclerAdapterForSteps(itemListenerInterface)


            binding.recyclerSteps.adapter = adapter
            adapter.submitList(achieverGoal.steps)
            adapter.notifyDataSetChanged()

            binding.addStepImageView.setOnClickListener{
                clickListener.addStep(achieverGoal.steps)
            }

            binding.editGoalImageView.setOnClickListener{
                clickListener.editGoal(achieverGoal)
            }
            binding.deleteGoalImageView.setOnClickListener{
                clickListener.removeGoal(achieverGoal)
            }
            binding.executePendingBindings()




        }


        companion object {
            fun from(viewGroup: ViewGroup, itemListenerInterface: ItemListenerInterface): ViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = CardviewLifegoalBinding.inflate(layoutInflater, viewGroup, false)
                return ViewHolder(binding,itemListenerInterface)
            }

        }


    }

    companion object {
        private val DiffCallBack: DiffUtil.ItemCallback<AchieverGoal> =
            object : DiffUtil.ItemCallback<AchieverGoal>() {
                override fun areItemsTheSame(
                    oldgoal: AchieverGoal,
                    newgoal: AchieverGoal
                ): Boolean {
                    return oldgoal.achieverGoalId == newgoal.achieverGoalId
                }

                override fun areContentsTheSame(
                    oldgoal: AchieverGoal,
                    newgoal: AchieverGoal
                ): Boolean {
                    return oldgoal.achieverGoal == newgoal.achieverGoal && oldgoal.achieverGoalDuration == newgoal.achieverGoalDuration && oldgoal.steps == newgoal.steps
                }
            }


    }

    class OnAchieverGoalListener(
        val clickListenerAddStep: (steps: ArrayList<Steps?>?) -> Unit,
        val clickListenerEditGoal: (achieverGoal: AchieverGoal) -> Unit,
        val clickListenerDeleteGoal: (achieverGoal: AchieverGoal) -> Unit,

    ) {
        fun addStep(steps: ArrayList<Steps?>?) = clickListenerAddStep(steps)
        fun removeGoal(achieverGoal: AchieverGoal) = clickListenerDeleteGoal(achieverGoal)
        fun editGoal(achieverGoal: AchieverGoal) = clickListenerEditGoal(achieverGoal)


    }


}