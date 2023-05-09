package com.example.achieverassistant.dailyPlan

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.databinding.CardviewForDailyplanBinding

class RecyclerViewForDailyPlan(private val clickListener: OnDailyTasksListener) :
    ListAdapter<DailyTasks, RecyclerViewForDailyPlan.DailyViewHolder>(DIFF_CALL_BACK) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DailyViewHolder {
        return DailyViewHolder.from(viewGroup)
    }

    override fun onBindViewHolder(dailyViewHolder: DailyViewHolder, position: Int) {
        val currentTask = getItem(position)
        dailyViewHolder.bind(clickListener, currentTask)

    }

    class DailyViewHolder(private val binding: CardviewForDailyplanBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(clickListener: OnDailyTasksListener, dailyTasks: DailyTasks) {
            binding.dailyTask = dailyTasks
            binding.clickListener = clickListener
            binding.textOfCurrenttask.text = dailyTasks.currentTask
            binding.textOfTimeOfCurrenttask.text = convertDateToString(dailyTasks.currentTextTime)

            binding.editImageTask.setOnClickListener {
                clickListener.onEDitClicked(dailyTasks)
            }
            binding.deleteImageTask.setOnClickListener {
                clickListener.onDeleteClicked(dailyTasks)
            }

            binding.executePendingBindings()

        }

        companion object {
            fun from(viewGroup: ViewGroup): DailyViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = CardviewForDailyplanBinding.inflate(layoutInflater, viewGroup, false)
                return DailyViewHolder(binding)
            }
        }
    }

    fun getItemAt(position: Int): DailyTasks {
        return getItem(position)
    }


    class OnDailyTasksListener(
        val clickListener: (dailyTasks: DailyTasks) -> Unit,
        val clickListener1: (dailyTasks: DailyTasks) -> Unit
    ) {
        fun onEDitClicked(dailyTasks: DailyTasks) = clickListener(dailyTasks)
        fun onDeleteClicked(dailyTasks: DailyTasks) = clickListener1(dailyTasks)

    }

    companion object {
        private val DIFF_CALL_BACK: DiffUtil.ItemCallback<DailyTasks> =
            object : DiffUtil.ItemCallback<DailyTasks>() {
                override fun areItemsTheSame(olditem: DailyTasks, newitem: DailyTasks): Boolean {
                    return olditem.id == newitem.id
                }

                override fun areContentsTheSame(olditem: DailyTasks, newitem: DailyTasks): Boolean {
                    return olditem.currentTask == newitem.currentTask && olditem.currentTextTime == newitem.currentTextTime
                }
            }


    }
}