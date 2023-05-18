package com.example.achieverassistant

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.achieverassistant.dailyPlan.DailyTasksFragment
import com.example.achieverassistant.achieverGoal.AchieverGoalsFragment

class ViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DailyTasksFragment()
            1 -> AchieverGoalsFragment()
            else -> DailyTasksFragment()
        }
    }
}