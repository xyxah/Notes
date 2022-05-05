package com.geekbrains.androidstart.notes

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController)
        initToolbarAndDrawer()
    }

    private fun initToolbarAndDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initDrawer(toolbar)
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_drawer_notes -> {
                    navController.navigate(R.id.notesFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_add_note -> {
                    navController.navigate(R.id.addNoteFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_about -> {
                    navController.navigate(R.id.aboutFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_exit -> {
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}