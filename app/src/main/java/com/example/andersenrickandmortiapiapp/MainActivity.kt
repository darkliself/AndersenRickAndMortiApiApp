package com.example.andersenrickandmortiapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.andersenrickandmortiapiapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        var navigationView : NavigationView? = null
//        var drawerLayout: DrawerLayout? = null
        super.onCreate(savedInstanceState)

//        val context = applicationContext
//        context.deleteDatabase("RickAndMortyDB")

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavBar = binding.bottomNavigation
//        bottomNavBar.visibility = View.INVISIBLE
        bottomNavBar.setupWithNavController(navController)

//        navigationView = binding.testNavView
//        drawerLayout = binding.drawerLayout
//        val  toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.characters,  R.string.characters)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }
}