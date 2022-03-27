package com.example.shortmovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.shortmovieapp.databinding.ActivityMainBinding
import com.example.shortmovieapp.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        navController = findNavController(R.id.main_fragment)
        setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()
    }

    //Top option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    //Top option menu click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Toast.makeText(this,"Logget Out!", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Bottom navigation menu setup
    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }

    //For back proccess
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}