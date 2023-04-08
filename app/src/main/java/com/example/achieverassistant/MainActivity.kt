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
import androidx.viewpager2.widget.ViewPager2
import com.example.achieverassistant.moments.Moments
import com.example.achieverassistant.quotes.Quotes
import com.example.achieverassistant.habits.Habits
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Variable for creating drawer layout and organize it
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    //Variable for toolbar and tablayout
    lateinit var toolbar: Toolbar
    lateinit var tabLayout: TabLayout

    //Variables for view Pager and switching between fragments
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //define the variables for Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_for_app)

        //define the variables for view pager
        viewPager = findViewById(R.id.viewpager)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter


        //define the toolbar varaibles and tabs
        toolbar = findViewById(R.id.tool_bar_id)
        tabLayout = findViewById(R.id.tabs)
        val dailyTasks = tabLayout.newTab()
        val achieverGoals = tabLayout.newTab()
        tabLayout.addTab(dailyTasks, 0)
        tabLayout.addTab(achieverGoals, 1)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        setActionBar(toolbar)
        //customize the tabs and their font and icons
        TabLayoutMediator(tabLayout,viewPager){ currentTab, currentPosition ->
            currentTab.text = when(currentPosition){
                0 -> "Daily Tasks"
                1 -> "Achiever Goals"
                else -> "Default Text"
            }
        }.attach()







        //it's responsible for open and close the navigation view menu
        actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.opened, R.string.closed) {
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
        drawerLayout.addDrawerListener(actionBarDrawerToggle)


        // this is responsible for open the activities when the user choose from navigation view menu
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            drawerLayout.closeDrawers()
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