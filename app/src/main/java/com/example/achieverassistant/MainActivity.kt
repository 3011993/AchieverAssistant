package com.example.achieverassistant

import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import android.content.Intent
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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
        binding.navigationForApp.setNavigationItemSelectedListener(
            NavigationView.OnNavigationItemSelectedListener
            { menuItem: MenuItem ->
                binding.drawerLayout.closeDrawers()
                if (menuItem.itemId == R.id.moments_item) {
                    val intent = Intent(this, Moments::class.java)
                    startActivity(intent)
                }
                if (menuItem.itemId == R.id.quotes_item) {
                    val intent = Intent(this, Quotes::class.java)
                    startActivity(intent)
                }
                if (menuItem.itemId == R.id.habits_item) {
                    val intent = Intent(this, Habits::class.java)
                    startActivity(intent)
                }
                false
            })

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val navController = this.findNavController(R.id.navHostManager)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostManager)
        return NavigationUI.navigateUp(navController,binding.drawerLayout)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }
}