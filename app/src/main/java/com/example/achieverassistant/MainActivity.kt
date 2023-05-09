package com.example.achieverassistant

import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.achieverassistant.databinding.ActivityMainBinding
import com.example.achieverassistant.moments.Moments
import com.example.achieverassistant.quotes.Quotes
import com.example.achieverassistant.habits.Habits
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Variable for creating drawer layout and organize it
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    //Variables for view Pager and switching between fragments
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewpager.adapter = viewPagerAdapter

        // tool bar tabs
        val dailyTasks = binding.tabs.newTab()
        val achieverGoals = binding.tabs.newTab()
        binding.tabs.addTab(dailyTasks, 0)
        binding.tabs.addTab(achieverGoals, 1)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //customize the tabs and their font and icons
        TabLayoutMediator(binding.tabs, binding.viewpager) { currentTab, currentPosition ->
            currentTab.text = when (currentPosition) {
                0 -> "Daily Tasks"
                1 -> "Achiever Goals"
                else -> "Default Text"
            }
        }.attach()


        //it's responsible for open and close the navigation view menu
        actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, binding.drawerLayout, R.string.opened, R.string.closed) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    invalidateOptionsMenu()
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    invalidateOptionsMenu()
                }
            }
        actionBarDrawerToggle.syncState()
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)


        // this is responsible for open the activities when the user choose from navigation view menu
        binding.navigationForApp.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener
        { menuItem: MenuItem ->
            binding.drawerLayout.closeDrawers()
            if (menuItem.itemId == R.id.moments_item) {
                val intent = Intent(this@MainActivity, Moments::class.java)
                startActivity(intent)
            }
            if (menuItem.itemId == R.id.quotes_item) {
                val intent = Intent(this@MainActivity, Quotes::class.java)
                startActivity(intent)
            }
            if (menuItem.itemId == R.id.habits_item) {
                val intent = Intent(this@MainActivity, Habits::class.java)
                startActivity(intent)
            }
            false
        })
    }


    // both of methods necessary for open menu
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle!!.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(
            item
        )
    }


}