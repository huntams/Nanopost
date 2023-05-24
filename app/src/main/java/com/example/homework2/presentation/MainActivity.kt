package com.example.homework2.presentation


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var prefs: PrefsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.Feed,
                R.id.Profile
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.authFragment){
                binding.appbarMainActivity.visibility = View.GONE
            }
            else if(destination.id == R.id.Profile||destination.id == R.id.Feed)
                binding.appbarMainActivity.visibility = View.VISIBLE
            when (destination.id) {
                R.id.authFragment, R.id.profileEditFragment ->{
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.Profile, R.id.Feed, R.id.postFragment, R.id.imageFragment -> binding.bottomNavigation.visibility =
                    View.VISIBLE
            }
        }
    }


    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu,menu)
        menu?.findItem(R.id.actionAccept)?.isVisible = true
        menu?.findItem(R.id.actionDelete)?.isVisible = true
        menu?.findItem(R.id.actionExit)?.isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.actionAccept){

        }
        return super.onOptionsItemSelected(item)
    }

     */
}