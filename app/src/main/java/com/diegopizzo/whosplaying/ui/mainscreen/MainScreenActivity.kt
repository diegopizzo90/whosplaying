package com.diegopizzo.whosplaying.ui.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.databinding.ActivityMainBinding
import com.diegopizzo.whosplaying.ui.base.ActivityViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityViewBinding<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav_controller) as NavHostFragment).navController
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            // Prevent to recreate the fragment if the item is already selected
            setOnItemReselectedListener {}
        }
    }
}