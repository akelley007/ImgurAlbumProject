package com.ajkelley.imguralbumproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.extensions.getViewModel
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navHostFragment: NavHostFragment
    lateinit var inflater: NavInflater
    var viewModel: AlbumsListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = nav_host_fragment as NavHostFragment
        inflater = navHostFragment.navController.navInflater

        //Setup the navGraph for this activity
        val graph = inflater.inflate(R.navigation.navigation)
        navHostFragment.navController.graph = graph
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navHostFragment.navController, null)
    }
}
