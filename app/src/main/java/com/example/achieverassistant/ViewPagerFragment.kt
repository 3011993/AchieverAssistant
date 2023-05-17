package com.example.achieverassistant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.databinding.DataBindingUtil
import com.example.achieverassistant.databinding.FragmentViewPagerBinding
import com.example.achieverassistant.habits.Habits
import com.example.achieverassistant.moments.Moments
import com.example.achieverassistant.quotes.Quotes
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding


    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager, container, false)

        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.viewpager.adapter = viewPagerAdapter

        val dailyTasksTab = binding.tabs.newTab()
        val achieverGoalTab = binding.tabs.newTab()

        binding.tabs.addTab(dailyTasksTab,0)
        binding.tabs.addTab(achieverGoalTab,1)


        TabLayoutMediator(binding.tabs, binding.viewpager) { currentTab, currentPosition ->
            currentTab.text = when (currentPosition) {
                0 -> "Daily Tasks"
                1 -> "Achiever Goals"
                else -> "Default Text"
            }
        }.attach()






        return binding.root
    }


}